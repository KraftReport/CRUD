 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="header.jsp"%>
    
 
      <div class="main_contents">
   
    <div id="sub_content">
       <form action="courseUpdate" method="post"  ">
<input type="hidden" value="${id}" name = "id" />
     
            <input type="hidden" name="old_id"  value=" "  />
             <input type="hidden" name="old_name"  value=" "   />
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label text-dark">Id</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" value=" ${id}" disabled="disabled"   >
                </div>
            </div>
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" value="${name} "   name="name" >
                </div>
            </div>
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label text-dark">Name</label>
                <div class="col-md-4">
                     
                    <select class="form-select" aria-label="Education" id="role"
						name="status">
						
						<c:if test="${status =='open' }">
						
						<option name="status" selected="selected" value="open">Open</option>
						<option  name="status" value="closed">Closed</option>
						</c:if>
						
						<c:if test="${status =='closed' }">
						
						<option  name="status" value="open">Open</option>
						<option name="status" selected="selected" value="closed">Closed</option>
						</c:if>
					</select>
					
                </div>
            </div>
         
             
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                   
    
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Update</button>
 
    
            </div>
            </form>
    </div>
</div>
</div>
        
        <script>
        
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
 

    


    
