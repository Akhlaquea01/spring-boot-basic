package in.masti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Employee Entity Class
 * 
 * This class represents the Employee entity in the database. It uses JPA annotations
 * to map the Java object to a database table. The @Entity annotation tells JPA that
 * this class should be persisted to the database.
 * 
 * Key Features:
 * - Uses sequence generator for ID generation
 * - Includes validation constraints
 * - Has audit fields (creation time)
 * - Follows JPA best practices
 */
@Entity
@Table(name = "employee")
public class Employee {
    
    /**
     * Primary key for the employee
     * 
     * Uses a sequence generator for better performance and control over ID generation.
     * The sequence starts from 101 and increments by 1 for each new employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_id_generator")
    @SequenceGenerator(name = "emp_id_generator", sequenceName = "emp_id_seq", initialValue = 101, allocationSize = 1)
    private Integer empId;
    
    /**
     * Employee name - Required field with length constraint
     * 
     * @NotNull ensures the field cannot be null
     * @Size ensures the name is between 2 and 20 characters
     * @Pattern ensures only letters, spaces, and hyphens are allowed
     */
    @Column(length = 20, nullable = false)
    @NotNull(message = "Employee name is required")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Name can only contain letters, spaces, and hyphens")
    private String name;
    
    /**
     * Employee designation/role
     * 
     * @Size ensures the designation is not too long
     */
    @Size(max = 50, message = "Designation cannot exceed 50 characters")
    private String designation;
    
    /**
     * Date of birth
     * 
     * @Past ensures the date is in the past
     */
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;
    
    /**
     * Company name
     * 
     * @Size ensures the company name is not too long
     */
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String company;
    
    /**
     * Creation timestamp - Transient field (not persisted to database)
     * 
     * This field is automatically set when the object is created and is used
     * for audit purposes. The @Transient annotation prevents this field from
     * being mapped to the database.
     */
    @Transient
    private LocalDateTime creationTime = LocalDateTime.now();

    // Default constructor required by JPA
    public Employee() {
    }

    // Parameterized constructor for convenience
    public Employee(String name, String designation, LocalDate dob, String company) {
        this.name = name;
        this.designation = designation;
        this.dob = dob;
        this.company = company;
    }

    // Getter and Setter methods
    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Override toString method for better debugging and logging
     */
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", dob=" + dob +
                ", company='" + company + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }

    /**
     * Override equals method for proper object comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empId != null && empId.equals(employee.empId);
    }

    /**
     * Override hashCode method to maintain contract with equals
     */
    @Override
    public int hashCode() {
        return empId != null ? empId.hashCode() : 0;
    }
}
