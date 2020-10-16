<%--
  Created by IntelliJ IDEA.
  User: hbx
  Date: 2020/9/28
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%-- 该项目中没有用到该文件，使用的是Ajax --%>
<head>
    <title>List Employee</title>
    <!-- !!!这里要注意三者导入的顺序，
        1.bootstrap的css
        2.导入jQuery
        3.导入bootstrap的js -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <!-- 标题 -->
        <div class="row">
            <div class="col-md-12">
                <h1>SSM-CRUD</h1>
            </div>
        </div>
        <!-- 按钮 -->
        <div class="row">
            <div class="col-md-4 col-md-offset-8">
                <button class="btn btn-primary">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>
        <!-- 显示表格数据 -->
        <div class="table table-hover">
            <div class="col-md-12">
                <table class="table">
                    <tr>
                        <th>员工id</th>
                        <th>员工姓名</th>
                        <th>性别</th>
                        <th>Email</th>
                        <th>所在部门</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="emp">
                        <tr>
                            <td>${emp.empId}</td>
                            <td>${emp.empName}</td>
                            <td>${emp.gender}</td>
                            <td>${emp.email}</td>
                            <td>${emp.department.deptName}</td>
                            <td>
                                <!-- 在按钮前加个小图标 -->
                                <button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil"></span>编辑</button>
                                <button class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!-- 分页信息 -->
        <div class="row">
            <!-- 分页相关信息 -->
            <div class="col-md-6">
                当前第${pageInfo.pageNum}页，总共${pageInfo.pages}页，共${pageInfo.total}条记录
            </div>
            <!-- 分页条 -->
            <div class="col-md-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <%-- 若是没有前一页，则让首页连接跟前一页连接不可用，且设置a的href为javascript:void(0)，
                        这是因为li设置为disabled时，超链接仍可以点击 --%>
                        <c:if test="${!pageInfo.hasPreviousPage}">
                            <li class="disabled"><a href="javascript:void(0)">首页</a></li>
                            <li class="disabled">
                                <a href="javascript:void(0)" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <%-- 若有前一页，正常显示 --%>
                        <c:if test="${pageInfo.hasPreviousPage}">
                            <li><a href="emps?page=1">首页</a></li>
                            <li>
                                <a href="emps?page=${pageInfo.pageNum-1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <%-- 获取分页栏显示页数（navigatepageNums），遍历，依次放进li --%>
                        <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                            <%-- 若数字等于当前页，添加active类名（被选中样式） --%>
                            <li <c:if test="${pageNum==pageInfo.pageNum}">class="active"</c:if>><a href="emps?page=${pageNum}">${pageNum}</a></li>
                        </c:forEach>
                        <%-- 若没有下一页，与首页同处理 --%>
                        <c:if test="${!pageInfo.hasNextPage}">
                            <li class="disabled">
                                <a href="javascript:void(0)" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            <li class="disabled"><a href="javascript:void(0)">尾页</a></li>
                        </c:if>
                        <c:if test="${pageInfo.hasNextPage}">
                            <li>
                                <a href="emps?page=${pageInfo.pageNum+1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            <li ><a href="emps?page=${pageInfo.pages}">尾页</a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
