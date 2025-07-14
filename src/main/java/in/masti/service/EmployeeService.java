package in.masti.service;

import in.masti.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Employee Service Interface
 * 
 * This interface defines the business logic operations for Employee management.
 * It acts as a contract for the service implementation and provides a clean
 * separation between the controller and data access layers.
 * 
 * Key Responsibilities:
 * - Business logic validation
 * - Data transformation
 * - Transaction management
 * - Error handling
 * - Business rules enforcement
 */
public interface EmployeeService {
    
    /**
     * Save a new employee or update an existing one
     * 
     * This method handles both creation and updates. If the employee has an ID,
     * it will update the existing record; otherwise, it will create a new one.
     * 
     * @param employee The employee object to save
     * @return The saved employee with generated ID
     * @throws IllegalArgumentException if employee data is invalid
     */
    Employee save(Employee employee);

    /**
     * Retrieve all employees
     * 
     * @return List of all employees in the system
     */
    List<Employee> getEmployees();

    /**
     * Retrieve an employee by their ID
     * 
     * @param id The employee ID to search for
     * @return The employee if found
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    Employee getEmployeeById(Integer id);

    /**
     * Retrieve an employee by their ID (returns Optional)
     * 
     * @param id The employee ID to search for
     * @return Optional containing the employee if found
     */
    Optional<Employee> findEmployeeById(Integer id);

    /**
     * Delete an employee by their ID
     * 
     * @param id The employee ID to delete
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    void deleteEmployeeById(Integer id);

    /**
     * Retrieve employees by designation
     * 
     * @param designation The designation to search for
     * @return List of employees with the specified designation
     */
    List<Employee> getEmployeesByDesignation(String designation);

    /**
     * Retrieve employees by company name
     * 
     * @param company The company name to search for
     * @return List of employees working in the specified company
     */
    List<Employee> getEmployeesByCompany(String company);

    /**
     * Search employees by name (partial match, case-insensitive)
     * 
     * @param name The partial name to search for
     * @return List of employees whose name contains the specified string
     */
    List<Employee> searchEmployeesByName(String name);

    /**
     * Retrieve employees born after a specific date
     * 
     * @param date The date to compare against
     * @return List of employees born after the specified date
     */
    List<Employee> getEmployeesBornAfter(LocalDate date);

    /**
     * Retrieve employees by designation and company
     * 
     * @param designation The designation to search for
     * @param company The company to search for
     * @return List of employees with the specified designation and company
     */
    List<Employee> getEmployeesByDesignationAndCompany(String designation, String company);

    /**
     * Check if an employee exists by name
     * 
     * @param name The name to check
     * @return true if an employee with the specified name exists
     */
    boolean employeeExistsByName(String name);

    /**
     * Count employees by designation
     * 
     * @param designation The designation to count
     * @return Number of employees with the specified designation
     */
    long countEmployeesByDesignation(String designation);

    /**
     * Update an existing employee
     * 
     * @param id The employee ID to update
     * @param employee The updated employee data
     * @return The updated employee
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    Employee updateEmployee(Integer id, Employee employee);

    /**
     * Partially update an employee (PATCH operation)
     * 
     * @param id The employee ID to update
     * @param employee The partial employee data
     * @return The updated employee
     * @throws NoSuchElementException if no employee is found with the given ID
     */
    Employee patchEmployee(Integer id, Employee employee);
}
