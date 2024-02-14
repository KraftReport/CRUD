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
            <h1>Welcome!</h1>
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
        <form class="login-form" action="login" method="post" name="confirm">
          <input type="text" placeholder="Email" name="email" required="required"/>
          <input type="password" placeholder="Password" name="password" required="required"/>
          <button>login</button>
          <p class="message">Not registered? <a href="userRegister">Create an account</a></p>
          <p><a href="forgetPassword">Forget password ?</a></p>
        </form>
      </div>
    </div>
</body>

</html>