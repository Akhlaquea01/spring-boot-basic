package in.masti.service;

import in.masti.entity.Employee;
import in.masti.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Employee Service Implementation
 * 
 * This class implements the EmployeeService interface and contains the business logic
 * for employee management operations. It acts as an intermediary between the controller
 * and repository layers.
 * 
 * Key Features:
 * - Transaction management with @Transactional
 * - Comprehensive logging for debugging and monitoring
 * - Input validation and business rule enforcement
 * - Proper exception handling
 * - Data transformation and business logic
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private final EmployeeRepository employeeRepo;

    /**
     * Constructor-based dependency injection
     * 
     * @param employeeRepo The employee repository dependency
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * Save a new employee or update an existing one
     * 
     * This method validates the employee data before saving and logs the operation
     * for audit purposes.
     * 
     * @param employee The employee object to save
     * @return The saved employee with generated ID
     * @throws IllegalArgumentException if employee data is invalid
     */
    @Override
    @Transactional
    public Employee save(Employee employee) {
        logger.info("Saving employee: {}", employee.getName());
        
        // Validate employee data
        validateEmployee(employee);
        
        try {
            Employee savedEmployee = employeeRepo.saveAndFlush(employee);
            logger.info("Successfully saved employee with ID: {}", savedEmployee.getEmpId());
            return savedEmployee;
        } catch (Exception e) {
            logger.error("Error saving employee: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save employee", e);
        }
    }

    /**
     * Retrieve all employees
     * 
     * @return List of all employees in the system
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployees() {
        logger.info("Retrieving all employees");
        try {
            List<Employee> employees = employeeRepo.findAll();
            logger.info("Retrieved {} employees", employees.size());
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employees", e);
        }
    }

    /**
     * Retrieve an employee by their ID
     * 
     * @param id The employee ID to search for
     * @return The employee if found
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Integer id) {
        logger.info("Retrieving employee with ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        
        try {
            return employeeRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("No employee found with ID: {}", id);
                        return new NoSuchElementException("No employee found for ID: " + id);
                    });
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employee", e);
        }
    }

    /**
     * Retrieve an employee by their ID (returns Optional)
     * 
     * @param id The employee ID to search for
     * @return Optional containing the employee if found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Integer id) {
        logger.info("Finding employee with ID: {}", id);
        
        if (id == null) {
            return Optional.empty();
        }
        
        try {
            return employeeRepo.findById(id);
        } catch (Exception e) {
            logger.error("Error finding employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to find employee", e);
        }
    }

    /**
     * Delete an employee by their ID
     * 
     * @param id The employee ID to delete
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    @Override
    @Transactional
    public void deleteEmployeeById(Integer id) {
        logger.info("Deleting employee with ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        
        try {
            Employee employee = employeeRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("No employee found with ID {} for deletion", id);
                        return new NoSuchElementException("No employee found for ID: " + id + " for deletion");
                    });
            
            employeeRepo.delete(employee);
            logger.info("Successfully deleted employee with ID: {}", id);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    /**
     * Retrieve employees by designation
     * 
     * @param designation The designation to search for
     * @return List of employees with the specified designation
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDesignation(String designation) {
        logger.info("Retrieving employees with designation: {}", designation);
        
        if (!StringUtils.hasText(designation)) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }
        
        try {
            List<Employee> employees = employeeRepo.getEmployeesByDesignation(designation);
            logger.info("Retrieved {} employees with designation: {}", employees.size(), designation);
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees by designation {}: {}", designation, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employees by designation", e);
        }
    }

    /**
     * Retrieve employees by company name
     * 
     * @param company The company name to search for
     * @return List of employees working in the specified company
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByCompany(String company) {
        logger.info("Retrieving employees from company: {}", company);
        
        if (!StringUtils.hasText(company)) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        
        try {
            List<Employee> employees = employeeRepo.findByCompany(company);
            logger.info("Retrieved {} employees from company: {}", employees.size(), company);
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees by company {}: {}", company, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employees by company", e);
        }
    }

    /**
     * Search employees by name (partial match, case-insensitive)
     * 
     * @param name The partial name to search for
     * @return List of employees whose name contains the specified string
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        logger.info("Searching employees by name: {}", name);
        
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        try {
            List<Employee> employees = employeeRepo.findByNameContainingIgnoreCase(name);
            logger.info("Found {} employees matching name: {}", employees.size(), name);
            return employees;
        } catch (Exception e) {
            logger.error("Error searching employees by name {}: {}", name, e.getMessage(), e);
            throw new RuntimeException("Failed to search employees by name", e);
        }
    }

    /**
     * Retrieve employees born after a specific date
     * 
     * @param date The date to compare against
     * @return List of employees born after the specified date
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBornAfter(LocalDate date) {
        logger.info("Retrieving employees born after: {}", date);
        
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        try {
            List<Employee> employees = employeeRepo.findByDobAfter(date);
            logger.info("Retrieved {} employees born after: {}", employees.size(), date);
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees born after {}: {}", date, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employees born after date", e);
        }
    }

    /**
     * Retrieve employees by designation and company
     * 
     * @param designation The designation to search for
     * @param company The company to search for
     * @return List of employees with the specified designation and company
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDesignationAndCompany(String designation, String company) {
        logger.info("Retrieving employees with designation: {} and company: {}", designation, company);
        
        if (!StringUtils.hasText(designation)) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }
        if (!StringUtils.hasText(company)) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        
        try {
            List<Employee> employees = employeeRepo.findByDesignationAndCompany(designation, company);
            logger.info("Retrieved {} employees with designation: {} and company: {}", 
                       employees.size(), designation, company);
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees by designation {} and company {}: {}", 
                        designation, company, e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve employees by designation and company", e);
        }
    }

    /**
     * Check if an employee exists by name
     * 
     * @param name The name to check
     * @return true if an employee with the specified name exists
     */
    @Override
    @Transactional(readOnly = true)
    public boolean employeeExistsByName(String name) {
        logger.info("Checking if employee exists with name: {}", name);
        
        if (!StringUtils.hasText(name)) {
            return false;
        }
        
        try {
            boolean exists = employeeRepo.existsByName(name);
            logger.info("Employee with name {} exists: {}", name, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking if employee exists with name {}: {}", name, e.getMessage(), e);
            throw new RuntimeException("Failed to check if employee exists", e);
        }
    }

    /**
     * Count employees by designation
     * 
     * @param designation The designation to count
     * @return Number of employees with the specified designation
     */
    @Override
    @Transactional(readOnly = true)
    public long countEmployeesByDesignation(String designation) {
        logger.info("Counting employees with designation: {}", designation);
        
        if (!StringUtils.hasText(designation)) {
            throw new IllegalArgumentException("Designation cannot be null or empty");
        }
        
        try {
            long count = employeeRepo.countByDesignation(designation);
            logger.info("Found {} employees with designation: {}", count, designation);
            return count;
        } catch (Exception e) {
            logger.error("Error counting employees by designation {}: {}", designation, e.getMessage(), e);
            throw new RuntimeException("Failed to count employees by designation", e);
        }
    }

    /**
     * Update an existing employee
     * 
     * @param id The employee ID to update
     * @param employee The updated employee data
     * @return The updated employee
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    @Override
    @Transactional
    public Employee updateEmployee(Integer id, Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee data cannot be null");
        }
        
        // Validate employee data
        validateEmployee(employee);
        
        try {
            Employee existingEmployee = employeeRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("No employee found with ID {} for update", id);
                        return new NoSuchElementException("No employee found for ID: " + id + " for update");
                    });
            
            // Update fields
            existingEmployee.setName(employee.getName());
            existingEmployee.setDesignation(employee.getDesignation());
            existingEmployee.setDob(employee.getDob());
            existingEmployee.setCompany(employee.getCompany());
            
            Employee updatedEmployee = employeeRepo.saveAndFlush(existingEmployee);
            logger.info("Successfully updated employee with ID: {}", id);
            return updatedEmployee;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update employee", e);
        }
    }

    /**
     * Partially update an employee (PATCH operation)
     * 
     * @param id The employee ID to update
     * @param employee The partial employee data
     * @return The updated employee
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    @Override
    @Transactional
    public Employee patchEmployee(Integer id, Employee employee) {
        logger.info("Patching employee with ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee data cannot be null");
        }
        
        try {
            Employee existingEmployee = employeeRepo.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("No employee found with ID {} for patch", id);
                        return new NoSuchElementException("No employee found for ID: " + id + " for patch");
                    });
            
            // Update only non-null fields
            if (StringUtils.hasText(employee.getName())) {
                existingEmployee.setName(employee.getName());
            }
            if (StringUtils.hasText(employee.getDesignation())) {
                existingEmployee.setDesignation(employee.getDesignation());
            }
            if (employee.getDob() != null) {
                existingEmployee.setDob(employee.getDob());
            }
            if (StringUtils.hasText(employee.getCompany())) {
                existingEmployee.setCompany(employee.getCompany());
            }
            
            Employee patchedEmployee = employeeRepo.saveAndFlush(existingEmployee);
            logger.info("Successfully patched employee with ID: {}", id);
            return patchedEmployee;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error patching employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to patch employee", e);
        }
    }

    /**
     * Validate employee data
     * 
     * @param employee The employee to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        
        if (!StringUtils.hasText(employee.getName())) {
            throw new IllegalArgumentException("Employee name is required");
        }
        
        if (employee.getName().length() < 2 || employee.getName().length() > 20) {
            throw new IllegalArgumentException("Employee name must be between 2 and 20 characters");
        }
        
        if (StringUtils.hasText(employee.getDesignation()) && employee.getDesignation().length() > 50) {
            throw new IllegalArgumentException("Designation cannot exceed 50 characters");
        }
        
        if (StringUtils.hasText(employee.getCompany()) && employee.getCompany().length() > 100) {
            throw new IllegalArgumentException("Company name cannot exceed 100 characters");
        }
        
        if (employee.getDob() != null && employee.getDob().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
    }
}
