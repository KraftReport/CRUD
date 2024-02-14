
<%@page import="java.util.ArrayList"%> 
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<div class="main_contents">
 
	<div id="sub_content">
		<form class="row g-3 mt-3 ms-2" action="courseSearch" method="post">
			<div class="col-auto">
				<label for="staticEmail2" class="visually-hidden">Course Id</label>
				<input type="text" class="form-control" id="staticEmail2" name="id"
					placeholder="Course Id">
			</div>
			<div class="col-auto">
				<label for="inputPassword2" class="visually-hidden">Course
					Name</label> <input type="text" class="form-control" id="inputPassword2"
					name="name" name="name" placeholder="Course Name">
			</div>

			<div class="col-auto">
				<button type="submit" class="btn btn-primary mb-3">Search</button>
			</div>
			 
			<div class="col-auto">
				<button type="button" class="btn btn-secondary "
					onclick="location.href = 'courseRegister';">Add</button>

			</div>
		 
		</form>

		<table class="table table-striped" id="stduentTable">

			<thead>
				<tr>

					<th scope="col">Course ID</th>
					<th scope="col">Course Name</th>
					<th scope="col">Options</th>

				</tr>
			</thead>
			<tbody>
		<c:forEach items="${sessionScope.allCourse}" var="course">
		 	 
				<tr>
 					<td>${course.id}</td>
					<td>${course.name}</td>
					<td><a href="courseUpdate?id=${course.id}"><button
								type="button" class="btn btn-secondary mb-3">
								<c:if test="${course.status == 'closed' }">Open</c:if>
								<c:if test="${course.status == 'open' }">Update</c:if>
								</button></a></td>
					<c:if test="${course.status != 'closed'}">
					<td><a href="courseDelete?id=${course.id}"><button
								type="submit" class="btn btn-secondary mb-3">Close</button></a></td>
					</c:if> 
				</tr>
		
		
		</c:forEach>
			</tbody>
		</table>

		<div class="modal fade" id="exampleModal" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Student
							Deletion</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">

						<h5 style="color: rgb(127, 209, 131);">Are you sure want to
							delete !</h5>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success col-md-2"
							data-bs-dismiss="modal">Ok</button>

					</div>
				</div>
			</div>
		</div>
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







<body>

</body>
 