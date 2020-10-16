package com.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import com.ssm.service.EmpService;
import com.ssm.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmpController {

    @Autowired
    private EmpService service;

    /**
     * 根据指定页数查询，若无指定页数，默认为1（访问首页时）
     * 以json形式返回PageInfo对象
     * @param page 指定第几页
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmps(@RequestParam(value = "page",defaultValue = "1") Integer page){
        //指定第page页，每页5条数据
        PageHelper.startPage(page, 5);
        //查询所有employee
        List<Employee> employees = service.getAllEmps();
        //使用PageInfo对象封装结果集，其中传入5表示在导航条显示5个导航页；
        //PageInfo对象是对查询出的结果进行分页处理，而不是在数据库查询中使用分页查询
        PageInfo<Employee> pageInfo = new PageInfo<>(employees, 3);

        //PageInfo对象提供了很多获取分页信息的方法：
        // pageInfo.getSize();//当前页的数量
        // pageInfo.getPageSize();//每页的数量
        // pageInfo.getTotal();//所有数量
        // pageInfo.getList();//分页后的结果集

        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * get请求-获取操作
     * 根据id获取指定员工信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmpById(@PathVariable Integer id){
        Employee employee = service.getById(id);

        return Msg.success().add("emp", employee);
    }

    /**
     * post请求-保存操作
     * 一般对输入数据的校验不止只有前端校验，还应有后端校验（前端校验被破解的可能性很大，需要在后台再进行校验）
     * 这里使用jsr303校验@Valid修饰要校验的值，并通过BindingResult对象封装校验结果
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult bindingResult){
        //若校验不通过
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            //获取所有错误信息
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                System.out.println("错误的字段：" + error.getField());
                System.out.println("错误信息：" + error.getDefaultMessage());
                //将错误信息放入map
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            //返回给前端处理失败的结果，并把错误信息添加到其中
            return Msg.fail().add("errorFields", errorMap);
        }

        service.save(employee);
        return Msg.success();
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    @ResponseBody
    public Msg checkEmpName(String empName){
        if(service.isEmpNameExist(empName)){
            return Msg.fail();
        }
        else {
            return Msg.success();
        }
    }

    /**
     * 更新员工信息的请求可以使用POST方法，带上_method参数让HiddenHttpMethodFilter进行请求转换
     * 也可以在前端通过ajax直接发送PUT请求，但tomcat对于post之外的请求，并不会将其中的参数数据封装进request对象
     * （对于post请求，tomcat会把参数数据封装在一个map中，通过request的getParameter方法可以获取到这些参数）
     * springMVC是使用getParameter方法来获取数据封装进JavaBean，这就导致了封装不了JavaBean的信息，从而执行sql语句时出现语法错误
     *
     * 解决方法之一是使用springMVC提供的FormContentFilter过滤器，它会将POST之外的请求的参数数据进行处理。
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmpById(Employee employee){
        System.out.println(employee);
        service.updateById(employee);
        return Msg.success();
    }

    /**
     * 删除，该方法根据传进的参数判断是批量还是单个删除
     * 若是批量删除，则多个id值以1-2-3的形式传递
     * @param ids
     * @return
     */
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteById(@PathVariable String ids){
        List<Integer> idsInt = new ArrayList<>();
        //批量删除
        if(ids.contains("-")){
            String[] idsStr = ids.split("-");
            for(String idStr : idsStr){
                idsInt.add(Integer.parseInt(idStr));
            }
        //单个删除
        }else {
            idsInt.add(Integer.parseInt(ids));
        }

        service.deleteBatch(idsInt);
        return Msg.success();
    }
}
