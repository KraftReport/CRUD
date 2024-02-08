<%@page import="java.io.InputStream"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="com.ace.repository.StudentService"%>
<%@page import="java.util.List"%>
<%@page import="com.ace.model.Course"%>
<%@page import="com.ace.connection.ConnectorCaller"%>
<%@page import="com.ace.repository.CourseService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>
<div class="main_contents">

	<%
	var token1 = request.getSession();
	if (token.getAttribute("tokenName") == null) {
		response.sendRedirect("index.jsp");
	} else {

		var studentService = new StudentService(ConnectorCaller.getConnection());
		var student = studentService.findById((String) request.getParameter("id")).get(0);
		System.out.print(student.getGender());
		var studentName = student.getName().replace(" ", "_");
	%>
	<div id="sub_content">
		<form action="studentUpdate" method="post"
			enctype="multipart/form-data">

			<h2 class="col-md-6 offset-md-2 mb-5 mt-4 text-dark">Student Registration</h2>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="studentID" class="col-md-2 col-form-label text-dark">Student
					ID</label>
				<div class="col-md-4">
					<input type="text" class="form-control"
						value=<%=request.getParameter("id")%> name="id" id="studentID">
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="name" class="col-md-2 col-form-label text-dark">Name</label>
				<div class="col-md-4">
					<input type="text" class="form-control" required="required" id="name" name="name"
						value=<%=studentName%>>
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="dob" class="col-md-2 col-form-label text-dark">DOB</label>
				<div class="col-md-4">
					<input type="date" class="form-control"  required="required" name="dob" id="dob"
						value=<%=student.getDob()%>>
				</div>
			</div>
			<fieldset class="row mb-4">
				<div class="col-md-2"></div>
				<legend class="col-form-label col-md-2 pt-0 text-dark">Gender</legend>
				<div class="col-md-4">
					<div class="form-check-inline">
						<input class="form-check-input text-dark"   type="radio" name="gender"
							id="gridRadios1" <%if (student.getGender().equals("male")) {%>
							checked="checked" <%} else {%> <%}%> value="male"> <label
							class="form-check-label" for="gridRadios1"> Male </label>
					</div>
					<div class="form-check-inline">
						<input class="form-check-input text-dark" type="radio" name="gender"
							id="gridRadios2" <%if (student.getGender().equals("female")) {%>
							checked="checked" <%} else {%> <%}%> value="female"> <label
							class="form-check-label" for="gridRadios2"> Female </label>
					</div>

				</div>
			</fieldset>

			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="phone" class="col-md-2 col-form-label text-dark">Phone</label>
				<div class="col-md-4">
					<input type="text" class="form-control" required="required" name="phone" id="phone"
						value=<%=student.getPhone()%>>
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="education" class="col-md-2 col-form-label text-dark">Education</label>
				<div class="col-md-4">
					<select class="form-select" name="education" aria-label="Education"
						id="education">
						<option name="education"
							value="Bachelor of Information Technology"
							<%if (student.getEducation() == "Bachelor of Information Technology") {%>
							selected <%} else%> <%%>>Bachelor of Information
							Technology</option>
						<option name="education" value="Diploma in IT"
							<%if (student.getEducation() == "Diploma in IT") {%> selected
							<%} else {%> <%}%>>Diploma in IT</option>
						<option name="education" value="Bachelor of Computer Science"
							<%if (student.getEducation() == "Bachelor of Computer Science") {%>
							selected <%} else {%> <%}%>>Bachelor of Computer Science</option>

					</select>
				</div>
			</div>
			<fieldset class="row mb-4">
				<div class="col-md-2"></div>
				<legend class="col-form-label col-md-2 pt-0 text-dark" >Attend</legend>
				<div class="col-md-4">
					<%
					var courseService = new CourseService(ConnectorCaller.getConnection());
					List<Course> courseList = courseService.getAllCourse();
					for (Course c : courseList) {
						var name = c.getName();
					%>

					<div class="text-dark">

						<input type="checkbox" name="attend" class="text-dark"
							<%if (student.getAttend().contains(name)) {%> checked="checked"
							<%} else {%> <%}%> value=<%=name%>>

						<%=name%>

					</div>

					<%
					}
					%>
				</div>

			</fieldset>
			<%
			var imgData = student.getPhoto();
			String encode = Base64.getEncoder().encodeToString(imgData);
			request.setAttribute("imgBase", encode);
			%>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="name" class="col-md-2 text-dark col-form-label">Photo</label>
				<div class="col-md-4">
					<input type="file" size="50"   class="form-control" name="photo"
						id="name"    > <img style="width: 120px; height: 100px;"
						src="data:image/jpeg;base64,<%=encode%>" />
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-md-4"></div>

				<div class="col-md-4">
					<button type="submit" class="btn btn-success">Update</button>
					<div class="modal fade" id="exampleModal" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Student
										Update</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">

									<h5 style="color: rgb(127, 209, 131);">Succesfully Update!</h5>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-bs-dismiss="modal">Ok</button>

								</div>
							</div>
						</div>
					</div>

					<button type="button" class="btn btn-danger  "
						onclick="location.href = 'studentManage.jsp';">Cancel</button>

				</div>

			</div>


			<!--Modal-->

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
<%
}
%>




