<%@page import="java.util.ArrayList"%> 
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<div class="main_contents">

 
	<div id="sub_content">
		<form class="row g-3 mt-3 ms-2" action="userSearch" method="post">
			<div class="col-auto">
				<label for="staticEmail2" class="visually-hidden">User Id</label> <input
					type="text" class="form-control" id="staticEmail2" name="id"
					placeholder="User ID">
			</div>
			<div class="col-auto">
				<label for="inputPassword2" class="visually-hidden">User
					Name</label> <input type="text" class="form-control" id="inputPassword2"
					name="name" name="name" placeholder="User Name">
			</div>

			<div class="col-auto">
				<button type="submit" class="btn btn-primary mb-3">Search</button>
			</div>
		 
			<div class="col-auto">
				<button type="button" class="btn btn-secondary "
					onclick="location.href = 'userRegister';">Add</button>

			</div>
		 
		 
		</form>

		<table class="table table-striped" id="stduentTable">

			<thead>
				<tr>

					<th scope="col">User ID</th>
					<th scope="col">User Name</th>
					<th scope="col">Details</th>

				</tr>
			</thead>
			<tbody>
		 

				<tr>

					<c:forEach var="user" items="${sessionScope.allUser}">
					
					<td> ${user.id} </td>
					<td> ${user.name }</td>
				
					
					 <c:if test="${loginUser.role == 'admin' }">
					 
					 	<td><a href="userUpdate?id=${user.id}"><button
								type="button" class="btn btn-secondary mb-3">Update</button></a></td>
					<td><a href="userDelete?id=${user.id}"><button
								type="submit" class="btn btn-secondary mb-3">Delete</button></a></td>
					 
					 
					 </c:if>
					 
					 <c:if test="${loginUser.role == 'user' }">
					 
					 <c:if test="${user.id == loginUser.id }">
					 
					 <td><a href="userUpdate?id=${user.id}"><button
								type="button" class="btn btn-secondary mb-3">Update</button></a></td>
					<td><a href="userDelete?id=${user.id}"><button
								type="submit" class="btn btn-secondary mb-3">Delete</button></a></td>
					 </c:if>
					 
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
 
 