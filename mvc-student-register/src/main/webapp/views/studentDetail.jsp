<%@page import="java.util.Base64"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="header.jsp" %>
      <div class="main_contents">
      
	 
    <div id="sub_content">
   
        <form>

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Details</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 text-dark col-form-label">Student ID</label>
                <div class="col-md-4">
                    <b>${student.id }</b>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <b>${student.name }</b>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label text-dark">DOB</label>
                <div class="col-md-4">
                    <b>${student.dob }</b>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0" text-dark>Gender</legend>
                <div class="col-md-4">
                       <b>${student.gender }</b>
                    </div>
                   
    
                </div>
           
    
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label text-dark">Phone</label>
                <div class="col-md-4">
                   <b>${student.phone }</b>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label text-dark">Education</label>
                   <div class="col-md-4"><b>${student.education } </b>
                    
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0 text-dark">Course</legend>
    
                <c:forEach items="${student.course}" var="c">
                <div class="col-md-4">
                      <div class="col-md-4">
                     <b>${c }</b>
                </div>
                     
                
                </c:forEach>
    
                </div>
            </fieldset>
           
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                 <img style="width: 120px; height: 100px;" src="data:image/jpeg;base64,${Base64.getEncoder().encodeToString(student.photo.getBytes())}" />
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
 
