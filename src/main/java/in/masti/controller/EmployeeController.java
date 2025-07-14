package in.masti.controller;

import in.masti.constants.RouteConstants;
import in.masti.entity.Employee;
import in.masti.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RouteConstants.EMPLOYEE_BASE)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(RouteConstants.EMPLOYEE_CREATE)
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployees());
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<Employee> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/{designation}")
    public ResponseEntity<List<Employee>> getByDesignation(@PathVariable String designation) {
        return ResponseEntity.ok(employeeService.getEmployeesByDesignation(designation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.accepted().build();
    }

    /*
    * TODO: @PutMapping
    * */

    /*
     * TODO: @PatchMapping
     * */
}
