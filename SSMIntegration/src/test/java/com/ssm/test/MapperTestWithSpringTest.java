package com.ssm.test;

import com.ssm.bean.Department;
import com.ssm.bean.Employee;
import com.ssm.dao.DepartmentMapper;
import com.ssm.dao.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用spring的单元测试进行测试
 * 1.使用@ContextConfiguration修饰测试类，通过其location属性指定配置文件，从而让Spring测试单元完成Application的创建
 * 2.使用@Autowired注入
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ssm/rootApplicationContext.xml"})
public class MapperTestWithSpringTest {

    @Autowired
    private DepartmentMapper deptMapper;

    @Autowired
    private EmployeeMapper empMapper;

    @Test
    public void testDeptInsert(){
        Department department = new Department();
        department.setDeptName("策划部");
        deptMapper.insert(department);
    }

    @Test
    public void testEmpInsert(){

    }

    @Test
    public void testEmpSelect(){
        Employee employee = empMapper.selectByIdWithDept(1);
        System.out.println(employee);
    }
}
