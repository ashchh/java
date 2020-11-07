<%@page import="com.chhillar.entities.HR_Manager"%>
<%
    HR_Manager hr=(HR_Manager)session.getAttribute("hr");
    if(hr!=null){
%>
<html>
    <head>
        <%@include file="./base.jsp"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="">Employee Management</a>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><div class="navbar-text"><p>Welcome: ${hr.name}</p></div></li>
                        <li><a href="">Home</a></li>
                        <li><a href="logout">Logout</a><li>
                    </ul>			
                </div>
            </div>
        </nav>
        <div class="container mt-3">

            <div class="row">

                <div class="col-md-12" style="padding-top: 50px;">

                    <h1 class="text-center mb-3">Welcome</h1>

                    <!--<table class="table">-->
                    <form action="${pageContext.request.contextPath }/delete" data-toggle="validator" method="post">
                        <div class="container">
                            <input class="btn btn-primary" onclick="return confirm('Are you sure you want to delete?')" type="submit" value="Delete"/>
                            <a href="add-Employee" class="btn btn-primary">Add Employee</a>

                        </div>
                        <br>
                        <table id="example" class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col"><input type="checkbox" id="select-all" /></th>
                                    <th scope="col" colspan="">Id</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Mobile No.</th>
                                    <th scope="col">DOB</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach items="${employee}" var="p">
                                    <tr>
                                        <td><input type="checkbox" name="empId" value="${p.pid}" id="checkbox-3" /></td>
                                        <th scope="row">${p.pid}</th>
                                        <td>${p.name}</td>
                                        <td>${p.email}</td>
                                        <td>${p.phone}</td>
                                        <td>${p.dob} </td>
                                        <!--<td>${p.status}</td>-->
                                        <td>
                                            <c:choose>
                                                <c:when test="${p.status=='InActive'}">
                                                    <div style="color:red;">InActive</div>
                                                </c:when>    
                                                <c:otherwise>
                                                    <div style="color:green;">Active</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><a href="update/${p.pid}" class="btn btn-primary">Edit</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!--                    <div class="container text-center">
                                                <input class="btn btn-primary" onclick="return confirm('Are you sure you want to delete?')" type="submit" value="Delete"/>
                                                <a href="add-Employee" class="btn btn-primary">Add Employee</a>
                        
                                            </div>-->
                    </form>

                </div>


            </div>

        </div>
        <script>
            $(document).ready(function () {
                $('#example').DataTable();
            });

        </script>
        <script>
            $('#select-all').click(function (event) {
                if (this.checked) {
                    // Iterate each checkbox
                    $(':checkbox').each(function () {
                        this.checked = true;
                    });
                } else {
                    $(':checkbox').each(function () {
                        this.checked = false;
                    });
                }
            });

        </script>
    </body>
</html>
<%
    }else{
        session.setAttribute("msg", "Plz login First!");
        response.sendRedirect("login");
    }
%>