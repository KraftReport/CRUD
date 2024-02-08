<%@page import="java.util.Base64"%>
<%@page import="com.ace.connection.ConnectorCaller"%>
<%@page import="com.ace.repository.StudentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="layout/header.jsp" %>
      <div class="main_contents">
      
	<%
	var token1 = request.getSession();
	if (token.getAttribute("tokenName") == null) {
	response.sendRedirect("index.jsp");
	}else{
	%>
    <div id="sub_content">
    <% 	var id =(String) request.getParameter("id");
	var studentService = new StudentService(ConnectorCaller.getConnection());
	var student = studentService.findById(id).get(0);
	System.out.println(student.getName());
	System.out.println(student.getPhoto());
%>
        <form>

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Details</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 text-dark col-form-label">Student ID</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" value=<%=student.getId() %> id="studentID" disabled>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="name" value=<%=student.getName() %> disabled="disabled">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label text-dark">DOB</label>
                <div class="col-md-4">
                    <input type="date" class="form-control" id="dob" value=<%=student.getDob() %> disabled="disabled">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0" text-dark>Gender</legend>
                <div class="col-md-4">
                       <input type="text" class="form-control" id="gender" value=<%=student.getGender() %> disabled="disabled">
                    </div>
                   
    
                </div>
           
    
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label text-dark">Phone</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="phone" value=<%=student.getPhone() %> disabled="disabled">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label text-dark">Education</label>
                   <div class="col-md-4">
                    <input type="text" class="form-control" id="education" value=<%=student.getEducation() %> disabled="disabled">
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0 text-dark">Attend</legend>
    
                <div class="col-md-4">
                      <div class="col-md-4">
                    <input type="text" class="form-control" id="attend" value=<%=student.getAttend() %> disabled="disabled">
                </div>
                     
    
                </div>
            </fieldset>
            <% var imgData = student.getPhoto();
            String encode = Base64.getEncoder().encodeToString(imgData);
            request.setAttribute("imgBase", encode); %>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                 <img style="width: 120px; height: 100px;" src="data:image/jpeg;base64,<%=encode %>" />
                </div>
            </div>
    
             
    
    
    
    
            </form>
    </div>
</div>
</div>
        
        <script>
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
