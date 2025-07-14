package in.masti.controller;

import in.masti.constants.RouteConstants;
import in.masti.entity.Employee;
import in.masti.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Employee Controller
 * 
 * This class handles HTTP requests related to employee management operations.
 * It provides RESTful endpoints for CRUD operations on Employee entities.
 * 
 * Key Features:
 * - RESTful API design following best practices
 * - Input validation using Bean Validation
 * - Proper HTTP status codes
 * - Comprehensive error handling
 * - Logging for debugging and monitoring
 * - Support for various query parameters
 * 
 * REST Endpoints:
 * - POST /employees - Create a new employee
 * - GET /employees - Get all employees
 * - GET /employees/{id} - Get employee by ID
 * - PUT /employees/{id} - Update employee completely
 * - PATCH /employees/{id} - Partially update employee
 * - DELETE /employees/{id} - Delete employee
 * - GET /employees/designation/{designation} - Get employees by designation
 * - GET /employees/company/{company} - Get employees by company
 * - GET /employees/search?name={name} - Search employees by name
 * - GET /employees/born-after?date={date} - Get employees born after date
 * - GET /employees/count/designation/{designation} - Count employees by designation
 * - GET /employees/exists?name={name} - Check if employee exists by name
 */
@RestController
@RequestMapping(RouteConstants.EMPLOYEE_BASE)
@Validated
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    private final EmployeeService employeeService;

    /**
     * Constructor-based dependency injection
     * 
     * @param employeeService The employee service dependency
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Create a new employee
     * 
     * This endpoint accepts a POST request with employee data in the request body
     * and creates a new employee record in the database.
     * 
     * @param employee The employee data to create (validated)
     * @return ResponseEntity containing the created employee with HTTP 201 status
     */
    @PostMapping(RouteConstants.EMPLOYEE_CREATE)
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        logger.info("Creating new employee: {}", employee.getName());
        
        try {
            Employee createdEmployee = employeeService.save(employee);
            logger.info("Successfully created employee with ID: {}", createdEmployee.getEmpId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get all employees
     * 
     * This endpoint retrieves all employees from the database.
     * 
     * @return ResponseEntity containing list of all employees with HTTP 200 status
     */
    @GetMapping(RouteConstants.EMPLOYEE_GET_ALL)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Retrieving all employees");
        
        try {
            List<Employee> employees = employeeService.getEmployees();
            logger.info("Retrieved {} employees", employees.size());
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get employee by ID
     * 
     * This endpoint retrieves a specific employee by their ID.
     * 
     * @param id The employee ID (path variable)
     * @return ResponseEntity containing the employee with HTTP 200 status, or 404 if not found
     */
    @GetMapping(RouteConstants.EMPLOYEE_GET_BY_ID)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        logger.info("Retrieving employee with ID: {}", id);
        
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (NoSuchElementException e) {
            logger.warn("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error retrieving employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Update employee completely (PUT operation)
     * 
     * This endpoint updates an existing employee with complete data replacement.
     * 
     * @param id The employee ID to update
     * @param employee The complete employee data
     * @return ResponseEntity containing the updated employee with HTTP 200 status
     */
    @PutMapping(RouteConstants.EMPLOYEE_UPDATE)
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, 
                                                   @Valid @RequestBody Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            logger.info("Successfully updated employee with ID: {}", id);
            return ResponseEntity.ok(updatedEmployee);
        } catch (NoSuchElementException e) {
            logger.warn("Employee not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Partially update employee (PATCH operation)
     * 
     * This endpoint updates only the provided fields of an existing employee.
     * 
     * @param id The employee ID to update
     * @param employee The partial employee data
     * @return ResponseEntity containing the updated employee with HTTP 200 status
     */
    @PatchMapping(RouteConstants.EMPLOYEE_PATCH)
    public ResponseEntity<Employee> patchEmployee(@PathVariable Integer id, 
                                                  @RequestBody Employee employee) {
        logger.info("Patching employee with ID: {}", id);
        
        try {
            Employee patchedEmployee = employeeService.patchEmployee(id, employee);
            logger.info("Successfully patched employee with ID: {}", id);
            return ResponseEntity.ok(patchedEmployee);
        } catch (NoSuchElementException e) {
            logger.warn("Employee not found for patch with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error patching employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete employee by ID
     * 
     * This endpoint deletes an employee from the database.
     * 
     * @param id The employee ID to delete
     * @return ResponseEntity with HTTP 204 status if successful
     */
    @DeleteMapping(RouteConstants.EMPLOYEE_DELETE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        logger.info("Deleting employee with ID: {}", id);
        
        try {
            employeeService.deleteEmployeeById(id);
            logger.info("Successfully deleted employee with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            logger.warn("Employee not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get employees by designation
     * 
     * @param designation The designation to search for
     * @return ResponseEntity containing list of employees with the specified designation
     */
    @GetMapping(RouteConstants.EMPLOYEE_BY_DESIGNATION)
    public ResponseEntity<List<Employee>> getEmployeesByDesignation(@PathVariable String designation) {
        logger.info("Retrieving employees with designation: {}", designation);
        
        try {
            List<Employee> employees = employeeService.getEmployeesByDesignation(designation);
            logger.info("Retrieved {} employees with designation: {}", employees.size(), designation);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees by designation {}: {}", designation, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get employees by company
     * 
     * @param company The company name to search for
     * @return ResponseEntity containing list of employees from the specified company
     */
    @GetMapping(RouteConstants.EMPLOYEE_BY_COMPANY)
    public ResponseEntity<List<Employee>> getEmployeesByCompany(@PathVariable String company) {
        logger.info("Retrieving employees from company: {}", company);
        
        try {
            List<Employee> employees = employeeService.getEmployeesByCompany(company);
            logger.info("Retrieved {} employees from company: {}", employees.size(), company);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees by company {}: {}", company, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Search employees by name (partial match, case-insensitive)
     * 
     * @param name The partial name to search for
     * @return ResponseEntity containing list of employees matching the name
     */
    @GetMapping(RouteConstants.EMPLOYEE_SEARCH)
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        logger.info("Searching employees by name: {}", name);
        
        try {
            List<Employee> employees = employeeService.searchEmployeesByName(name);
            logger.info("Found {} employees matching name: {}", employees.size(), name);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error searching employees by name {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get employees born after a specific date
     * 
     * @param date The date to compare against (format: yyyy-MM-dd)
     * @return ResponseEntity containing list of employees born after the specified date
     */
    @GetMapping(RouteConstants.EMPLOYEE_BORN_AFTER)
    public ResponseEntity<List<Employee>> getEmployeesBornAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.info("Retrieving employees born after: {}", date);
        
        try {
            List<Employee> employees = employeeService.getEmployeesBornAfter(date);
            logger.info("Retrieved {} employees born after: {}", employees.size(), date);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees born after {}: {}", date, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get employees by designation and company
     * 
     * @param designation The designation to search for
     * @param company The company name to search for
     * @return ResponseEntity containing list of employees with the specified designation and company
     */
    @GetMapping(RouteConstants.EMPLOYEE_FILTER)
    public ResponseEntity<List<Employee>> getEmployeesByDesignationAndCompany(
            @RequestParam String designation, @RequestParam String company) {
        logger.info("Retrieving employees with designation: {} and company: {}", designation, company);
        
        try {
            List<Employee> employees = employeeService.getEmployeesByDesignationAndCompany(designation, company);
            logger.info("Retrieved {} employees with designation: {} and company: {}", 
                       employees.size(), designation, company);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees by designation {} and company {}: {}", 
                        designation, company, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Count employees by designation
     * 
     * @param designation The designation to count
     * @return ResponseEntity containing the count of employees with the specified designation
     */
    @GetMapping(RouteConstants.EMPLOYEE_COUNT_BY_DESIGNATION)
    public ResponseEntity<Long> countEmployeesByDesignation(@PathVariable String designation) {
        logger.info("Counting employees with designation: {}", designation);
        
        try {
            long count = employeeService.countEmployeesByDesignation(designation);
            logger.info("Found {} employees with designation: {}", count, designation);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            logger.error("Error counting employees by designation {}: {}", designation, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Check if employee exists by name
     * 
     * @param name The name to check
     * @return ResponseEntity containing boolean indicating if employee exists
     */
    @GetMapping(RouteConstants.EMPLOYEE_EXISTS)
    public ResponseEntity<Boolean> employeeExistsByName(@RequestParam String name) {
        logger.info("Checking if employee exists with name: {}", name);
        
        try {
            boolean exists = employeeService.employeeExistsByName(name);
            logger.info("Employee with name {} exists: {}", name, exists);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            logger.error("Error checking if employee exists with name {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Health check endpoint
     * 
     * @return ResponseEntity indicating the service is healthy
     */
    @GetMapping(RouteConstants.EMPLOYEE_HEALTH)
    public ResponseEntity<String> health() {
        logger.info("Health check requested");
        return ResponseEntity.ok("Employee service is healthy");
    }
}
