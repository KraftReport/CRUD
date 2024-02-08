<%@page import="com.ace.model.Course"%>
<%@page import="com.ace.repository.CourseService"%>
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
		var courseService = new CourseService(ConnectorCaller.getConnection());
		var course_1 = new Course();
		course_1.setId((String)request.getParameter("old_id"));
		var old_course = courseService.findById(course_1);
		 
	%>
    <div id="sub_content">
       <form action="courseUpdate" method="post"  ">

     
            <input type="hidden" name="old_id"  value="<%=old_course.getId() %>"  />
             <input type="hidden" name="old_name"  value="<%=old_course.getName() %>"   />
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label text-dark">Id</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" value="<%=old_course.getId() %>" disabled="disabled"  name="id" >
                </div>
            </div>
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" value="<%=old_course.getName() %>"   name="name" >
                </div>
            </div>
         
             
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                   
    
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Update</button>
 
    
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

    


    
