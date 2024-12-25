package com.crm.controller;

import com.crm.payload.EmployeeDto;
import com.crm.entity.Employee;
import com.crm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    //http://localhost:8080/api/v1/employee/add

    private EmployeeService employeeService;
    //dependency injection used by constructor methods
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
   public ResponseEntity<?> addEmployee(
          @Valid @RequestBody EmployeeDto dto,
          BindingResult result
          ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmployeeDto employeeDto = employeeService.saveData(dto);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
   }

   //http://localhost:8080/api/v1/employee?id=2
   @DeleteMapping
   public ResponseEntity<String> deleteEmployee(
           @RequestParam Long id
   ){
        employeeService.deleteById(id);
       return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
   }
   @PutMapping
   public ResponseEntity<?> updateEmployee(
           @RequestParam Long id,
           @Valid  @RequestBody EmployeeDto dto ,
           BindingResult result
   ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        EmployeeDto employeeDto = employeeService.updateById(id, dto);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
   }
   @GetMapping()
   //http://localhost:8080/api/v1/employee?pageSize=3&pageNo=1
    public ResponseEntity<List<EmployeeDto >> getEmployees(
            @RequestParam(name="pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name="pageSize",required = false,defaultValue = "5") int pageSize,
            @RequestParam(name="sortBy",required = false,defaultValue = "name") String sortBy,
            @RequestParam(name="sortDir",required = false,defaultValue = "asc") String sortDir
   ){
       List<EmployeeDto> employeeDto = employeeService.getEmployees(pageNo,pageSize,sortBy,sortDir);
       return new ResponseEntity<>(employeeDto, HttpStatus.OK);
   }
   @GetMapping("/getId/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id){
       EmployeeDto dto = employeeService.getById(id);
       return new ResponseEntity<>(dto,HttpStatus.OK);
   }
}


