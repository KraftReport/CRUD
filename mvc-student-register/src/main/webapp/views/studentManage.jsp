<%@page import="java.util.Base64"%>
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="header.jsp" %>
      <div class="main_contents">
      
	 
    <div id="sub_content">
      <form class="row g-3 mt-3 ms-2" action="studentSearch" method="post">
        <div class="col-auto">
          <label for="staticEmail2" class="visually-hidden">studentID</label>
          <input type="text" name="id"  class="form-control" id="staticEmail2" placeholder="Student ID">
        </div>
        <div class="col-auto">
          <label for="inputPassword2" class="visually-hidden">studentName</label>
          <input type="text" name="name" class="form-control" id="inputPassword2" placeholder="Student Name">
        </div>
        <div class="col-auto">
            <label for="inputPassword2" class="visually-hidden">Course</label>
            <input type="text" name="course" class="form-control" id="inputPassword2" placeholder="Course">
          </div>
        <div class="col-auto">
          <button type="submit" class="btn btn-success mb-3">Search</button>
        </div>
         
      </form>
<div>
      <table class="table table-striped" id="stduentTable">
        <thead>
          <tr>
            
            <th scope="col">Student ID</th>
            <th scope="col">Name</th>
            <th scope="col">Course Name</th>
            <th scope="col">Details</th>
             <th scope="col">Photo</th>
          </tr>
        </thead>
        <tbody>
	
	 
	        
         <tr>
          <c:forEach items="${allStudent }" var="student">
            
          
            <td>${student.id} </td>
            <td>${student.name} </td>
             <td>
               
             <c:forEach items="${student.course }" var="course">
          		
          		
          		
          		<c:if test="${list.contains(course) }">
          		${course }<b>(closed)</b></br>
          		</c:if>
          		 <c:if test="${!list.contains(course) }">
          		 ${course}</br>
          		 </c:if>
            
            
            
 
             </c:forEach>
             
             </td>
              <td>
              <a href="studentUpdate?id=${student.id} "><button type="button" class="btn btn-secondary mb-2">Update</button></a> 
            
              <a href="studentDelete?id=${student.id} "><button type="submit" class="btn btn-secondary mb-2">Delete</button></a> 
           
              <a href="studentDetail?id=${student.id} "><button type="button" class="btn btn-secondary mb-2">See More</button></a> 
            </td>
         
          
			
			
		 
	   		<td>
             <img style="width: 120px; height: 100px;"
						src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(student.photo.getBytes())}" />
            </td>
             
          
            </tr>
             </c:forEach>
             
            
        
	
              
          
 
          
        </tbody>
      </table>
    </div>
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
 
