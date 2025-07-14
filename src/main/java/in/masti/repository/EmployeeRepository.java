package in.masti.repository;

import in.masti.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//    @Query("select e from Employee e where e.designation=:empDesignation")
//    @Query(value = "select * from employee e where e.designation=?", nativeQuery = true)
    @NativeQuery(value = "select * from employee where designation=:empDesignation")
    List<Employee> getEmployeesByDesignation(@Param("empDesignation") String designation);

    List<Employee> findEmployeesByDesignation(String empDesignation);
}
