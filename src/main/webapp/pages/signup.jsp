<%-- 
    Document   : signup
    Created on : May 29, 2013, 11:14:58 PM
    Author     : Shubhda
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>  
<head>  
  <title>Signup</title>  
   <link rel="stylesheet" href="../css/crosper-styles.css"/>
   <!--<script type="text/javascript" src="http://connect.facebook.net/en_US/all.js"></script>-->
   <!--<script type="text/javascript" src="../js/app/fblogin.js"></script> -->
   <script src="../js/jquery-1.8.2.min.js"></script>
        <script>
            $(document).ready(function(){
                $('#register').click(function(){
                   
                    var email = $('#email').val();
                    var password = $('#password').val();
                    
                    if(email != '' && password != '' ){
                        
                            $.ajax({
                                url : "http://localhost:8080/crossper/api/mysignup"
                                ,data: JSON.stringify({
                                                    "email" : email,
                                                    "password" : password})
                                ,type: "post"
                                ,processData:true
                                ,contentType : "application/json; charset=utf-8"
                                ,success : function(r){
                                    if(r.success){
                                        window.location.href = "http://localhost:8080/crossper/api/login";
                                    } else {
                                        //alert(r.errmsg);
                                    }
                                //  alert(r.data.response);
                                },
                                error : function(e){
                                //alert("error");
                                //  alert(e);
                                }
                            });
                       
                    }else{
                        alert('Please enter all values.');
                    }
                });
            });
        </script>

</head>  
<body>  
<div>
<label style="background-color: #23487E;color: #D8DFEA;width:300px;font-size: 30px; font-weight: bolder;">
	Signup with Crossper  
</label>

</div>
 
  <table>  
    <tr>  
      <td align='right'>Username</td>  
      <td><input type='text' id="email" /></td>  
    </tr>  
    <tr>  
      <td align='right'>Password</td>  
      <td><input type='password' id="password" /></td>  
    </tr>  
    <tr>  
      <td  align='right'>  
          <button id="register" >Register</button> 
        
      </td>  
     <!--<td>
		<input type="button" class="fblogin" value="Login using Facebook" onClick="loginToFacebook();">
     </td>-->
    </tr>  
  </table>  
</body>  
</html>