package com.ssm.dao;

import com.ssm.bean.Employee;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    Employee selectByPrimaryKey(Integer empId);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    Employee selectByIdWithDept(Integer id);

    List<Employee> selectByCondition(Employee employee);

    Employee selectByName(String name);

    void deleteBatch(List<Integer> ids);
}