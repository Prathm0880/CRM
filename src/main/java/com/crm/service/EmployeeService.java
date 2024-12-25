package com.crm.service;

import com.crm.Exception.ResourceNotFound;
import com.crm.payload.EmployeeDto;
import com.crm.entity.Employee;
import com.crm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    //There are two ways to create Dependencies Injection
    //1. Constructor Injection
    //2.@Autowired //This annotation is used for constructor injection

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    public EmployeeDto saveData(EmployeeDto dto){
        Employee employee = mapToEntity(dto);
        Employee save = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(save);
        return employeeDto;
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateById(Long id, EmployeeDto dto) {
        /*
        Optional<Employee> opEmp = employeeRepository.findById(id);
        Employee emp = opEmp.get();
        emp.setName(dto.getName());
        emp.setEmailId(dto.getEmailId());
        emp.setMobile(dto.getMobile());
        employeeRepository.save(emp);

        */

        Employee employee = mapToEntity(dto);
        employee.setId(id);
        Employee save = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(save);
        return employeeDto;
    }

    //used stream api concept --->functional function interface
    public List<EmployeeDto> getEmployees(int pageNo,int pageSize,String sortBy,String sortDir) {
        //use tertiary operator
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable page =  PageRequest.of(pageNo,pageSize,sort);
        Page<Employee> pg = employeeRepository.findAll(page);
        List<Employee> content = pg.getContent();
        List<EmployeeDto> employeeDto = content.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
        return employeeDto;
    }
    EmployeeDto mapToDto(Employee emp){
        EmployeeDto dto = modelMapper.map(emp, EmployeeDto.class);

        // EmployeeDto dto = new EmployeeDto();
        // dto.setId(emp.getId());
        // dto.setName(emp.getName());
        // dto.setEmailId(emp.getEmailId());
        // dto.setMobile(emp.getMobile());

        return dto;
    }
    Employee mapToEntity(EmployeeDto dto){
        //using model mapper we can reduce the line of code
        Employee emp = modelMapper.map(dto, Employee.class);

        /*
        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setName(dto.getName());
        emp.setEmailId(dto.getEmailId());
        emp.setMobile(dto.getMobile());
         */
        return emp;
    }

        public EmployeeDto getById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Record no found with id "+id)
        );
        EmployeeDto employeeDto = mapToDto(employee);
        return employeeDto;
    }
}

