<%@page import="java.util.List"%>
 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="header.jsp" %>
      <div class="main_contents">
    
    <div id="sub_content">
        <form action="studentRegister" onsubmit="" method="post" enctype="multipart/form-data">
 <input type="text" id="name"   value="name"   name="name" >
 <input type="date" required="required" class="form-control" id="dob" name="dob">
            
             
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="radio1" value="male"
                            checked>
                        <label class="form-check-label text-dark" for="gridRadios1">
                            Male
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="radio2" value="female">
                        <label class="form-check-label text-dark" for="gridRadios2">
                            Female
                        </label>
                    </div>
    
            
     
                    <input type="text" required="required" class="form-control" id="phone" name="phone">
           
                    <select class="form-select" aria-label="Education" id="education" name="education">
                        <option value="Bachelor of Information Technology" selected="selected">Bachelor of Information Technology</option>
                        <option value="Diploma in IT">Diploma in IT</option>
                        <option value="Bachelor of Computer Science">Bachelor of Computer Science</option>
    
                    </select>
           
         <div class="col-md-4">
                    <c:forEach items="${allCourse}" var="course">
                    
                    <div class="form-check-inline col-md-2">
                        <c:if test="${course.status != 'closed' }">
                        <input class="form-check-input text-dark" type="checkbox" name="course" id="radio3" value="${course.name}" >
                        <label class="form-check-label text-dark" >  </label>
                     <p>${course.name}</p>
                      
                        </c:if>
                    </div>
                    </c:forEach>
               
        
                
  <input type="file" size="50" id="photo" name="photo"  />
  <input type="submit" value="send">
  </form>
             
             <!-- 
                    <input type="date" required="required" class="form-control" id="dob" name="dob">
            
             
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
    
            
     
                    <input type="text" required="required" class="form-control" id="phone" name="phone">
           
                    <select class="form-select" aria-label="Education" id="education" name="education">
                        <option value="Bachelor of Information Technology" selected="selected">Bachelor of Information Technology</option>
                        <option value="Diploma in IT">Diploma in IT</option>
                        <option value="Bachelor of Computer Science">Bachelor of Computer Science</option>
    
                    </select>
            -->
    <%-- 
                <div class="col-md-4">
                    <c:forEach items="${allCourse}" var="course">
                    
                    <div class="form-check-inline col-md-2">
                        <input class="form-check-input text-dark" type="checkbox" name="course" id="gridRadios1" value="${course.name}" >
                        <label class="form-check-label text-dark" >  </label>
                     <p>${course.name}</p>
                      
                    </div>
                    </c:forEach>
               --%>
        
                
                   <!--  <input type="file"   required="required" class="form-control" name="photo" id="name"> -->
             
      
                    
                    <button type="submit"  class="btn btn-secondary col-md-2" >
                        Add
                    </button>
               

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
 
