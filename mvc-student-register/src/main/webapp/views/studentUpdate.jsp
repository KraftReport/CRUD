<%@page import="java.io.InputStream"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayInputStream"%> 
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<div class="main_contents">

 
	<div id="sub_content">
		<form action="studentUpdate" method="post"
			enctype="multipart/form-data">

			<h2 class="col-md-6 offset-md-2 mb-5 mt-4 text-dark">Student Registration</h2>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="studentID" class="col-md-2 col-form-label text-dark">Student
					ID</label>
				<div class="col-md-4">
					<input type="text" readonly="readonly" class="form-control" 
						value= "${student.id }" name="id" id="studentID">
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="name" class="col-md-2 col-form-label text-dark">Name</label>
				<div class="col-md-4">
					<input type="text" class="form-control" required="required" id="name" name="name"
						value="${student.name }" >
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="dob" class="col-md-2 col-form-label text-dark">DOB</label>
				<div class="col-md-4">
					<input type="date" class="form-control"  required="required" name="dob" id="dob"
						value="${student.dob }">
				</div>
			</div>
			<fieldset class="row mb-4">
				<div class="col-md-2"></div>
				<legend class="col-form-label col-md-2 pt-0 text-dark">Gender</legend>
				<div class="col-md-4">
					<c:if test="${student.gender == 'male' }">
					<div class="form-check-inline">
						<input class="form-check-input text-dark"   type="radio" name="gender"
							id="gridRadios1" checked="checked" value="male"> <label
							class="form-check-label"  for="gridRadios1"> Male </label>
					</div>
					<div class="form-check-inline">
						<input class="form-check-input text-dark" type="radio" name="gender"
							id="gridRadios2"   value="female"> <label
							class="form-check-label" for="gridRadios2"> Female </label>
					</div>
					</c:if>
					<c:if test="${student.gender == 'female' }">
					<div class="form-check-inline">
						<input class="form-check-input text-dark"   type="radio" name="gender"
							id="gridRadios1"  value="male"> <label
							class="form-check-label" for="gridRadios1"> Male </label>
					</div>
					<div class="form-check-inline">
						<input class="form-check-input text-dark" type="radio" name="gender"
							id="gridRadios2" checked="checked"  value="female"> <label
							class="form-check-label" for="gridRadios2"> Female </label>
					</div>
					</c:if>
				</div>
			</fieldset>

			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="phone" class="col-md-2 col-form-label text-dark">Phone</label>
				<div class="col-md-4">
					<input type="text" class="form-control" required="required" name="phone" id="phone"
						value="${student.phone }" >
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="education" class="col-md-2 col-form-label text-dark">Education</label>
				<div class="col-md-4">
				<c:if test="${ student.education == 'Bachelor of Information Technology'}">
					<select class="form-select" name="education" aria-label="Education"
						id="education">
						<option name="education" selected="selected"
							value="Bachelor of Information Technology"
							 >Bachelor of Information
							Technology</option>
						<option name="education" value="Diploma in IT"
							 >Diploma in IT</option>
						<option name="education" value="Bachelor of Computer Science"
						 >Bachelor of Computer Science</option>

					</select>
				</c:if>
				<c:if test="${ student.education == 'Diploma in IT'}">
				<select class="form-select" name="education" aria-label="Education"
						id="education">
						<option name="education"  
							value="Bachelor of Information Technology"
							 >Bachelor of Information
							Technology</option>
						<option name="education" selected="selected" value="Diploma in IT"
							 >Diploma in IT</option>
						<option name="education" value="Bachelor of Computer Science"
						 >Bachelor of Computer Science</option>

					</select>
				</c:if>
				<c:if test="${ student.education == 'Bachelor of Computer Science'}">
				<select class="form-select" name="education" aria-label="Education"
						id="education">
						<option name="education" selected="selected"
							value="Bachelor of Information Technology"
							 >Bachelor of Information
							Technology</option>
						<option name="education"  value="Diploma in IT"
							 >Diploma in IT</option>
						<option name="education" selected="selected" value="Bachelor of Computer Science"
						 >Bachelor of Computer Science</option>

					</select>
				</c:if>
				</div>
			</div>
			<fieldset class="row mb-4">
				<div class="col-md-2"></div>
				<legend class="col-form-label col-md-2 pt-0 text-dark" >Attend</legend>
				<c:forEach items="${allCourse}" var="course">
				
				 
				<div class="col-md-4">
					 
				
					<div class="text-dark">

					 
						
						<c:if test="${course.status != 'closed' }">
						<c:if test="${stuCourseList.contains(course.name)}">
						<input type="checkbox" value="${course.name }" checked="checked" name="course" class="text-dark">
						</c:if>
						<c:if test="${!stuCourseList.contains(course.name)}">
						<input type="checkbox" value="${course.name }"   name="course" class="text-dark">
						</c:if>
						</c:if>
					 
							 <p>${course.name }</p>
					</div>
				
				
				</c:forEach>

					 
				</div>

			</fieldset>
			 
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="name" class="col-md-2 text-dark col-form-label">Photo</label>
				<div class="col-md-4">
				   <input type="file" size="50"  value="C:\Users\KraftWork\Pictures\9a16492f403886e14343e071e49cb4a0.jpg" class="form-control" name="photo"
						id="name"/><img style="width: 120px; height: 100px;"
						src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(student.photo.getBytes())}"/>  
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

const preselectedFile = document.getElementById('preselected-file');
const fileInput = document.getElementById('name');
const previewImage = document.getElementById('preview-image');

if (preselectedFile.value) {
  // Decode base64 data and create temporary URL
  const decodedData = atob(preselectedFile.value.split(',')[1]);
  const blob = new Blob([decodedData], { type: 'image/jpeg' });
  const imageUrl = URL.createObjectURL(blob);

  // Set image preview and trigger click event (optional)
  previewImage.src = imageUrl;
  fileInput.click(); // Simulate user interaction (uncomment if needed)
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
 



