<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="test.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" href="test.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
        <script type="text/javascript">
        function displayDateTime() {
            var currentDate = new Date();
            var day = currentDate.getDate();
            var month = currentDate.getMonth() + 1; // Month is zero-based
            var year = currentDate.getFullYear();
            var hours = currentDate.getHours();
            var minutes = currentDate.getMinutes();
            var seconds = currentDate.getSeconds();

            // Format the date and time as needed
            var formattedDateTime ='Current Date : '+ day + '/' + month + '/' + year;

            // Display the formatted date and time in an HTML element
            document.getElementById('datetimeDisplay').innerHTML = formattedDateTime;
        }

        // Call the function when the page loads
        window.onload = displayDateTime;
        </script>
</head>

<body>
	<%
	var token = request.getSession();
	 
	 
	%>

  <div id="testheader">
    <div class="container">
        <div class=row>        
            <div class="col-md-5 ">
        <a href="welcome.jsp"><h3>Student Registration</h3></a>
    </div>  
    <div class="col-md-6"> 
        <p class="top text-dark"><%= token.getAttribute("tokenId") %> : <%= token.getAttribute("tokenName") %></p>
        <p class="top text-dark" id="datetimeDisplay"> </p>
    </div>  
    <div class="col-md-1" >
        <a href="logout"><input type="button" class="btn-basic" id="lgnout-button" value="Log Out"></a>
    </div>        
</div>
</div>

</div>
    <!-- <div id="testsidebar">Hello World </div> -->
    <div class="container">
    <div class="sidenav">
        
        <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>
        
            <div class="dropdown-container">
            <%if(token.getAttribute("loginRole").equals("admin")){ %>
          <a href="courseRegister.jsp">Course Registration </a>
          <a href="courseManage.jsp">Course Manage</a>
          <%}else{ %><%} %>
          <a href="studentRegister.jsp">Student Registration </a>
          <a href="studentManage.jsp">Student Search </a>
          
        </div>
        <a href="userManage.jsp">Users Management</a>
      </div>
       <div class="card-body">
				 
				<c:if test="${not empty succMsg}">
					<p class="text-center text-success">${succMsg}</p>
					<c:remove var="succMsg" />
				</c:if>

				<c:if test="${not empty errorMsg}">
					<p class="text-center text-success">${errorMsg}</p>
					<c:remove var="errorMsg" />
				</c:if>
          </div>