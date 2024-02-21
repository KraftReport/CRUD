<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<div class="main_contents">
	<div id="sub_content">
		<form action="userRegister" method="post"
			onsubmit="return validateForm()">

			<h2 class="col-md-6 offset-md-2 mb-5 mt-4 text-dark">User
				Registration</h2>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="email" class="col-md-2 col-form-label text-dark">Email</label>
				<div class="col-md-4">
					<input type="email" required="required" class="form-control"
						id="email" name="email">
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="name" class="col-md-2 col-form-label text-dark">Name</label>
				<div class="col-md-4">
					<input type="text" required="required" class="form-control"
						id="name" name="name">
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="Passowrd" class="col-md-2 col-form-label text-dark">Passowrd</label>
				<div class="col-md-4">
					<input type="password" required="required" class="form-control"
						id="password" name="password">
				</div>
				<div id="passwordError" class="error"></div>
			</div>
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="confirmPassword"
					class="col-md-2 col-form-label text-dark">Confirm Passowrd</label>
				<div class="col-md-4">
					<input type="password" required="required" class="form-control"
						id="confirmPassword" name="confirmPassword">
				</div>

			</div>
	 
			<div class="row mb-4">
				<div class="col-md-2"></div>
				<label for="userRole" class="col-md-2 col-form-label text-dark">User
					Role</label>
				<div class="col-md-4">
					<c:if test="${loginUser.role == 'admin' }">
					
					<select class="form-select" aria-label="Education" id="role"
						name="role">
						
						<option   value="admin">Admin</option>
						<option selected="selected" value="user">User</option>
					</select>
					
					
					
					</c:if>
					
					<c:if test="${loginUser.role == 'user' }">
					<select class="form-select" aria-label="Education" disabled="disabled" id="role"
						name="role">
						
						<option   value="admin">Admin</option>
						<option selected="selected" value="user">User</option>
			</select>
			
					
					</c:if>
					
					<c:if test="${loginUser == null }">
					<select class="form-select" aria-label="Education" disabled="disabled" id="role"
						name="role">
						
						<option  name ="role"  value="admin">Admin</option>
						<option selected="selected" name="role" value="user">User</option>
			</select>
			
					
					</c:if>
				</div>
			</div>
					 
			
			<div class="row mb-4">
				<div class="col-md-4"></div>

				<div class="col-md-6">


					<button type="submit" class="btn btn-secondary col-md-2"
						data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>


				</div>
		</form>
	</div>
</div>
</div>
 
<script>
	/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */

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


