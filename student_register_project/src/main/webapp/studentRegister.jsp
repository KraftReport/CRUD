<%@page import="java.util.List"%>
<%@page import="com.ace.model.Course"%>
<%@page import="com.ace.connection.ConnectorCaller"%>
<%@page import="com.ace.repository.CourseService"%>
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
        <form action="studentRegister" method="post" enctype="multipart/form-data">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4 text-dark">Student Registration</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 col-form-label text-dark">Student ID</label>
                <div class="col-md-4">
                    <input type="text" required="required" class="form-control" name="id" id="studentID" >
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <input type="text" required="required" class="form-control" id="name" name="name">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label text-dark">DOB</label>
                <div class="col-md-4">
                    <input type="date" required="required" class="form-control" id="dob" name="dob">
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0 text-dark">Gender</legend>
                <div class="col-md-4">
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="gridRadios1" value="male"
                            checked>
                        <label class="form-check-label text-dark" for="gridRadios1">
                            Male
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="gridRadios2" value="female">
                        <label class="form-check-label text-dark" for="gridRadios2">
                            Female
                        </label>
                    </div>
    
                </div>
            </fieldset>
    
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label text-dark">Phone</label>
                <div class="col-md-4">
                    <input type="text" required="required" class="form-control" id="phone" name="phone">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label text-dark">Education</label>
                <div class="col-md-4">
                    <select class="form-select" aria-label="Education" id="education" name="education">
                        <option value="Bachelor of Information Technology" selected="selected">Bachelor of Information Technology</option>
                        <option value="Diploma in IT">Diploma in IT</option>
                        <option value="Bachelor of Computer Science">Bachelor of Computer Science</option>
    
                    </select>
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0 text-dark">Attend</legend>
                
                <% CourseService courseService = new CourseService(ConnectorCaller.getConnection());
                		List<Course> courseList = courseService.getAllCourse();
                		for (Course course : courseList){
                		%>
    
                <div class="col-md-4">
                    <div class="form-check-inline col-md-2">
                        <input class="form-check-input text-dark" type="checkbox" name="attend" id="gridRadios1" value=<%=course.getName()%>>
                        <label class="form-check-label text-dark" >
                         <%= course.getName() %>
                        </label>
                    </div>
                    
                    <%} %>
                    
    
                </div>
            </fieldset>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Photo</label>
                <div class="col-md-4">
               
                
                    <input type="file" size="50" required="required" class="form-control" name="photo" id="name">
                </div>
            </div>
    
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-4">
                    
                    <button type="submit"  class="btn btn-secondary col-md-2" >
                        Add
                    </button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <h5 style="color: rgb(127, 209, 131);">Registered Succesfully !</h5>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
                               
                            </div>
                        </div>
                    </div>
            </div>
                </div>

    
            </div>
    
    
    
    
    
            </form>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
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
