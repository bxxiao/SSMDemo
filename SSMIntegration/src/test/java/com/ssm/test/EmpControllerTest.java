package com.ssm.test;

import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 使用Spring测试单元测试Controller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//需要加载两个应用上下文
@ContextConfiguration(locations = {"classpath:ssm/rootApplicationContext.xml", "classpath:ssm/spring-mvc.xml"})
public class EmpControllerTest {

    /**
     * 创建MockMvc对象需要传入servlet ApplicationContext，即springMVC的应用上下文
     * 为了能自动注入该上下文，需要使用@WebAppConfiguration注解
     */
    @Autowired
    private WebApplicationContext context;

    //虚拟mvc请求，通过该对象获取到请求处理结果
    private MockMvc mockMvc;

    @Before
    public void initMockMvc(){
        //通过servlet ApplicationContext创建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟一个uri为emps的get请求，通过param()方法传递参数
        //PS：！！！！！！！！！！这里的uri开头需要有 /
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.
                get("/emps").param("page", "3");
        //perform方法模拟发送请求
        MvcResult result = mockMvc.perform(builder)
                .andReturn();
        //从MvcResult中获取ModelAndView对象
        ModelAndView modelAndView = result.getModelAndView();
        //获取Map，从中得到PageInfo对象
        Map<String, Object> model = modelAndView.getModel();
        PageInfo<Employee> pageInfo = (PageInfo)model.get("pageInfo");

        System.out.println("当前页码：" + pageInfo.getPageNum());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("总记录树：" + pageInfo.getTotal());
        List<Employee> list = pageInfo.getList();
        for (Employee e : list){
            System.out.println(e);
        }
    }
}
