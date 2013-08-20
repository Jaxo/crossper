<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>  
<head>  
  <title>Login</title>  
   <link rel="stylesheet" href="../css/crosper-styles.css"/>
   <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>"
   <script type="text/javascript" src="../js/app/fblogin.js"></script>
</head>  
<body>  
<div>
<label style="background-color: #23487E;color: #D8DFEA;width:300px;font-size: 30px; font-weight: bolder;">
	Login To Crossper  
</label>

</div>
<c:if test='${not empty param.error}'>  
  <font color='red'>  
    Login error. <br />  
    Reason : ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}  
  </font>  
</c:if>  
<form method='POST' action='<c:url value='/j_spring_security_check' />'>  
  <table>  
    <tr>  
      <td align='right'>Username</td>  
      <td><input type='text' name='j_username' /></td>  
    </tr>  
    <tr>  
      <td align='right'>Password</td>  
      <td><input type='password' name='j_password' /></td>  
    </tr>  
    <tr>  
      <td  align='right'>  
        <input type='submit' value='Login' />  
        
      </td>  
     <td>
		<input type="button" class="fblogin" value="Login using Facebook" onClick="loginToFacebook();">
     </td>
    </tr>  
  </table>  
</form>  

<script>
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '139903926198344', // App ID
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });

  FB.Event.subscribe('auth.statusChange', function(response) {
      if (response.authResponse) {                        
            FB.api('/me', function(me){
                if (me.name) {
                  
                }
            });
        } else {
            
        }
  });
  };

  // Load the SDK asynchronously
  (function(d){
	   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	   if (d.getElementById(id)) {return;}
	   js = d.createElement('script'); js.id = id; js.async = true;
	   js.src = "//connect.facebook.net/en_US/all.js";
	   ref.parentNode.insertBefore(js, ref);
  }(document));

  
</script>
</body>  
</html>