<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="header.jsp"%>
   
<div>
<form action="p" method="post" enctype="multipart/form-data" >
<input type="text" name="name">
<input type="file" name="photo" size="50">
<input type="submit" value="send" />
</form>
</div>
   
   
    <!-- <div id="content">
  
    <form">

        <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="id" class="col-md-2 col-form-label"> ID</label>
            <div class="col-md-4">
                <input type="email" class="form-control" id="id">
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-2"></div>
            <label for="name" class="col-md-2 col-form-label">Name</label>
            <div class="col-md-4">
                <input type="email" class="form-control" id="name">
            </div>
        </div>
      
       
        <div class="row mb-4">
            <div class="col-md-4"></div>

            <div class="col-md-6">
               

                <button type="submit" class="btn btn-primary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Course Registration</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                               Succesfully Register
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary col-md-2" data-bs-dismiss="modal">Ok</button>
                               
                            </div>
                        </div>
                    </div>
            </div>

        </div>
        </form>
    </div> -->

    <div class="main-contents">
      <div id="contents">
        <h3>Welcome Back...! <br><br>
        Enjoy this project and try your best in your own!</h3>
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
 


      
        
