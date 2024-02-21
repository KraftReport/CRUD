<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="views/css/test.css">
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Type your new password</h1>
          </div>
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
        <form class="login-form" action="changePassword" method="post" name="confirm">
        <b>Old Password</b>
        <input type="hidden" value="${foundUser.id}" name="id" />
       <input type="text"  value="${foundUser.password }" disabled="disabled" />
        <b>New Password</b>
          <input type="text" placeholder="New Password" name="password" required="required"/>
          <button>Submit</button>
        </form>
      </div>
    </div>
</body>

</html>