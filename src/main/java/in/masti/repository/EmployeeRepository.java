package in.masti.repository;

import in.masti.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Employee Repository Interface
 * 
 * This interface extends JpaRepository to provide CRUD operations for the Employee entity.
 * JpaRepository provides basic CRUD operations like save, findById, findAll, delete, etc.
 * 
 * Key Features:
 * - Extends JpaRepository for basic CRUD operations
 * - Custom query methods using method naming conventions
 * - Custom JPQL queries for complex operations
 * - Native SQL queries for performance-critical operations
 * 
 * Spring Data JPA automatically creates implementations of this interface at runtime.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    /**
     * Find employees by designation using native SQL query
     * 
     * This method uses a native SQL query for better performance when dealing with
     * large datasets. The @Param annotation binds the method parameter to the query parameter.
     * 
     * @param designation The designation to search for
     * @return List of employees with the specified designation
     */
    @Query(value = "SELECT * FROM employee WHERE designation = :empDesignation", nativeQuery = true)
    List<Employee> getEmployeesByDesignation(@Param("empDesignation") String designation);

    /**
     * Find employees by designation using method naming convention
     * 
     * Spring Data JPA automatically creates the query based on the method name.
     * This is equivalent to: SELECT e FROM Employee e WHERE e.designation = ?1
     * 
     * @param empDesignation The designation to search for
     * @return List of employees with the specified designation
     */
    List<Employee> findEmployeesByDesignation(String empDesignation);

    /**
     * Find employees by company name
     * 
     * @param company The company name to search for
     * @return List of employees working in the specified company
     */
    List<Employee> findByCompany(String company);

    /**
     * Find employees by name (case-insensitive)
     * 
     * @param name The name to search for
     * @return List of employees with the specified name
     */
    List<Employee> findByNameIgnoreCase(String name);

    /**
     * Find employees by name containing the given string (case-insensitive)
     * 
     * @param name The partial name to search for
     * @return List of employees whose name contains the specified string
     */
    List<Employee> findByNameContainingIgnoreCase(String name);

    /**
     * Find employees born after a specific date
     * 
     * @param date The date to compare against
     * @return List of employees born after the specified date
     */
    List<Employee> findByDobAfter(LocalDate date);

    /**
     * Find employees by designation and company
     * 
     * @param designation The designation to search for
     * @param company The company to search for
     * @return List of employees with the specified designation and company
     */
    List<Employee> findByDesignationAndCompany(String designation, String company);

    /**
     * Find employee by name and designation
     * 
     * @param name The name to search for
     * @param designation The designation to search for
     * @return Optional containing the employee if found
     */
    Optional<Employee> findByNameAndDesignation(String name, String designation);

    /**
     * Count employees by designation
     * 
     * @param designation The designation to count
     * @return Number of employees with the specified designation
     */
    long countByDesignation(String designation);

    /**
     * Check if an employee exists by name
     * 
     * @param name The name to check
     * @return true if an employee with the specified name exists
     */
    boolean existsByName(String name);

    /**
     * Delete employees by designation
     * 
     * @param designation The designation of employees to delete
     */
    void deleteByDesignation(String designation);
}
