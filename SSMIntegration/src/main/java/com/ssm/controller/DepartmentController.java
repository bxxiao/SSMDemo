package com.ssm.controller;

import com.ssm.bean.Department;
import com.ssm.service.DepartmentService;
import com.ssm.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @ResponseBody
    public Msg getAllDept(){
        List<Department> departments = service.getAll();
        return Msg.success().add("departments", departments);
    }
}
