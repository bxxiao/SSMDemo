package com.ssm.service;

import com.ssm.bean.Employee;
import com.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    private EmployeeMapper mapper;

    public List<Employee> getAllEmps(){
        return mapper.selectAll();
    }

    public void save(Employee employee){
        mapper.insert(employee);
    }

    //判断用户名是否存在
    public boolean isEmpNameExist(String name){
        Employee employee = mapper.selectByName(name);
        return employee!=null;
    }

    public Employee getById(Integer id){
        return mapper.selectByPrimaryKey(id);
    }

    public void updateById(Employee employee){
        mapper.updateByPrimaryKey(employee);
    }

    public void deleteById(Integer id){
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids){
        mapper.deleteBatch(ids);
    }
}
