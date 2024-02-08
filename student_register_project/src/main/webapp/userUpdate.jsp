<%@page import="com.ace.repository.StudentService"%>
<%@page import="com.ace.model.User"%>
<%@page import="com.ace.connection.ConnectorCaller"%>
<%@page import="com.ace.repository.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="layout/header.jsp"%>
    
 
      <div class="main_contents">
       <%
	var token1 = request.getSession();
	if (token.getAttribute("tokenName") == null) {
	response.sendRedirect("index.jsp");
	}else{
		var userService = new UserService(ConnectorCaller.getConnection());
		 var user = userService.searchById(request.getParameter("id")).get(0);
	%>
    <div id="sub_content">
       <form action="userUpdate" method="post" onsubmit="return validateForm()">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4 text-dark">User Update</h2>
            <input type="hidden" name="id" value="<%=request.getParameter("id") %>"  />
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label text-dark">Email</label>
                <div class="col-md-4">
                    <input type="email" class="form-control" value="<%=user.getEmail() %>" id="email" name="email" required="required">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="name" value="<%=user.getName() %>" required="required" name="name">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="Passowrd" class="col-md-2 col-form-label text-dark">Passowrd</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" value="<%=user.getPassword() %>" id="password" name="password" required="required">
                </div>
                    <div id="passwordError" class="error"></div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label text-dark">Confirm Passowrd</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" value="<%=user.getPassword() %>" name="confirmPassword" required="required">
                </div>
                     
            </div>
            <% if(token.getAttribute("loginRole").equals("admin")){ %>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="userRole" class="col-md-2 col-form-label text-dark">User Role</label>
                <div class="col-md-4">
                    <select class="form-select" aria-label="Education" id="role" name="role" >
                        <option selected value="admin">Admin</option>
                        <option value="user">User</option>
    
    
                    </select>
                </div>
            </div>
            
            <%}else{ %><%} %>
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                   
    
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>
 
    
            </div>
            </form>
    </div>
</div>
</div>
         <%@ include file="layout/footer.jsp" %>
        <script>
        
        function validateForm() {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        var passwordError = document.getElementById('passwordError');

        if (password !== confirmPassword) {
          passwordError.innerHTML = 'Passwords do not match';
          return false; // Prevent form submission
        } else {
          passwordError.innerHTML = ''; // Clear any previous error message
          return true; // Allow form submission
        }
      }
              
        
        
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
</body>

</html>
 <%} %>

    


    
