<%@page import="com.home.dto.Student"%>
<%@page import="com.home.connection.GetConnection"%>
<%@page import="com.home.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Student</title>
<%@ include file="css.jsp"%>
</head>
<body class="bg-light">

	<%@ include file="navbar.jsp"%>

	<div class="container p-4">
		<div class="row">
			<div class="col-md-6 offset-md-3">
				<div class="card">
					<div class="card-body">
						<p class="fs-3 text-center">Update student</p>

						<%
						int id = Integer.parseInt(request.getParameter("id"));
						StudentDao studentDao = new StudentDao(GetConnection.getConnection());
						Student s = studentDao.searchById(id);
						%>

						<form action="updatestudent" method="post">
							<div class="mb-3">
								<label class="form-label">Student Id</label> <input type="text"
									name="id" class="form-control" value="<%=s.getId()%>">
							</div>
							<div class="mb-3">
								<label class="form-label">Student Name</label> <input
									type="text" name="name" class="form-control"
									value="<%=s.getName()%>">
							</div>
							<div class="mb-3">
								<label class="form-label">Student DOB</label> <input type="date"
									name="dob" class="form-control" value="<%=s.getDob()%>">
							</div>
							<div class="mb-3">
								<label class="form-label">Student Address</label> <input
									type="text" name="address" class="form-control"
									value="<%=s.getAddress()%>">
							</div>
							<div class="mb-3">
								<label class="form-label">Student Qualification</label> <input
									type="text" name="qualification" class="form-control"
									value="<%=s.getQualification()%>">
							</div>
							<div class="mb-3">
								<label class="form-label">Student Email</label> <input
									type="email" name="email" class="form-control"
									value="<%=s.getEmail()%>">
							</div>

							<!-- hidden input -->
							<input type="hidden" name="id" value="<%=s.getId()%>">

							<button type="submit" class="btn btn-primary col-md-12">Update</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>