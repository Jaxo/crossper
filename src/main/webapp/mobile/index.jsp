<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Crossper</title>
<link rel="shortcut icon" href="../min/images/mobile/favicon.ico" type="image/x-icon">
<link rel="icon" href="../min/images/mobile/favicon.ico" type="image/x-icon">

<link rel="shortcut icon" type="image/x-icon" href="../min/images/mobile/logo.png">
<link rel="stylesheet" href="../min/css/mobile/retune.css" />
<link rel="stylesheet" href="../min/css/jquery.mobile-1.2.0.css" />
<link rel="stylesheet" href="../min/css/jqm-datebox-1.1.0.css" />
<link rel="stylesheet" href="../min/css/mobile/crossper-mobile.css" />
</head>
<body>


</body>

<script>
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '<c:out value="${fbClientId}"/>', // App ID
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
<script src="../min/js/require.js"></script>
<script>
  require.config({
    baseUrl: "../min/js/mobile",
    paths: {
        'jquerymobile':'../mobile/jquery.mobile-1.2.0',
        'datebox'	: '../mobile/jqm-datebox-1.1.0.core',
        'calbox'	: '../mobile/jqm-datebox-1.1.0.mode.calbox',
   },
   shim: {
       
       'datebox': {
           deps: ['jquerymobile']
       },

       'calbox': {
           deps: ['datebox']
       }

   },
    waitSeconds: 0
  });
  require( ["main"],
    function() {
        console.log("loaded main.js");
    }
  );
</script>

</html>
