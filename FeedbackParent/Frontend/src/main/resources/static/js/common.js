$(document).ready(function(){
	$("#facultyImage").change(function(){
		
		fileSize = this.files[0].size;
		if(fileSize > 4194304){
			confirm("Please choose image less than 4MB!");
			this.setCustomValidity("You have to choose an image less than 1MB!");
			this.reportValidity();
		}else{
			this.setCustomValidity("");
			showImageThumbnail(this);
		}
		
	});
	
});

function showImageThumbnail(fileInput){
	
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e){
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
}
/*Front end*/

 function setPasswordConfirmValidity() {
          const password1 = document.getElementById('password1');
          const password2 = document.getElementById('password2');

          if (password1.value === password2.value) {
              password2.setCustomValidity('');
          } else {
              password2.setCustomValidity('Passwords must match');
          }
      }
      
 function validateStudentForm(form){
      
      const formData = new FormData(form);
      const message = document.getElementById("message");
        
      if(formData.get("firstName").trim()==null || formData.get("firstName").trim()==""){
	      message.innerHTML = "first name can't be empty!";
	      return false;
        }
        if(formData.get("lastName").trim()==null || formData.get("lastName").trim()==""){
	      message.innerHTML = "last name can't be empty!";
	      return false;
        }
        if(formData.get("rollNumber").trim()==null || formData.get("rollNumber").trim()==""){
	      message.innerHTML = "Roll Number can't be empty!";
	      return false;
        }
        
        if((Number(formData.get("id")) < 0) && (formData.get("password").trim()==null || formData.get("password").trim()=="")){
	      message.innerHTML = "Password can't be empty!";
	      return false;
        }
      
      return true;
    }

 function validateFacultyForm(form){
      const formData = new FormData(form);
      const message = document.getElementById("message");
      if(formData.get("firstName").trim()==null || formData.get("firstName").trim()==""){
	      message.innerHTML = "first name can't be empty!";
	      return false;
        }
        if(formData.get("lastName").trim()==null || formData.get("lastName").trim()==""){
	      message.innerHTML = "last name can't be empty!";
	      return false;
        }
        
        if(formData.get("regId").trim()==null || formData.get("regId").trim()==""){
	      message.innerHTML = "Roll Number can't be empty!";
	      return false;
        }
        
        if(formData.get("department").trim()==null || formData.get("department").trim()==""){
	      message.innerHTML = "Department can't be empty!";
	      return false;
        }
        
        if(formData.get("designation").trim()==null || formData.get("designation").trim()==""){
	      message.innerHTML = "Designation can't be empty!";
	      return false;
        }
        if((Number(formData.get("id")) < 0) && (formData.get("password").trim()==null || formData.get("password").trim()=="")){
	      message.innerHTML = "Password can't be empty!";
	      return false;
        }
        	
      
      return true;
    }
    
    
    
    
    
    
    

/*front end*/



/*Backend*/
function logoutButton(){
  var result = confirm("Are you sure to logout?");
  if (result==true) {
	document.getElementById("logoutForm").submit();
   return true;
  } else {
   return false;
  }
}



function displayEditor(){
document.getElementById("card").style.display="none";
document.getElementById("editor").style.display="block";
}
function checkAdminFields(form){
	const formData = new FormData(form);
	if(formData.get("enabled")==null){
		document.getElementById("adminMessageTag").innerText="Enable check box!";
		return false;
	}
		document.getElementById("adminMessageTag").innerText="";
		formData.set('regId', formData.get("regId").trim());
		formData.set('password', formData.get("password").trim());
		if(formData.get("password")== null || formData.get("password")==""){
			document.getElementById("adminMessageTag").innerText="Spaces as password is not allowed!";
			return false;
		}
		if(!confirm('Are you sure to login with new credentials?')){
			return false;
		}
    return true;
}

function deleteConfirm() {
  var result = confirm("Are you sure to delete?");
  if (result==true) {
   return true;
  } else {
   return false;
  }
}
 function submitButton(){
 const formData =  document.getElementById("regId").value;
 if(formData==null || formData==""){
 document.getElementById("message").innerText="it's empty!";
 return;
 }
 document.getElementById("searchForm").submit();
 }
 
  function submitButtonForSearch(){
 const formData =  document.getElementById("key").value;
 if(formData==null || formData==""){
 document.getElementById("message").innerText="it's empty!";
 return;
 }
 document.getElementById("searchForm").submit();
}