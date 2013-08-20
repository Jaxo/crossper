<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Crossper </title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- styles -->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,700' rel='stylesheet' type='text/css'>
<link href="../min/css/web/bootstrap.css" rel="stylesheet">
<link href="../min/css/web/bootstrap-responsive.css" rel="stylesheet">
<link href="../min/css/web/retune.css" rel="stylesheet" type="text/css">
<link href="../min/css/web/crossper-web.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../min/css/crosper-styles.css"/>
<link rel="stylesheet" href="../min/css/web/jquery.sb.css"/>
<link href="../min/css/web/check-box-radiobutton.css" rel="stylesheet" type="text/css">
<link href="../min/css/web/datepicker.css" rel="stylesheet" type="text/css">
<link href="../min/css/web/examples.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

</head>

<body>
	<div id="containerDiv" class="main-wrapper"> 
	</div>
</body>

<script  src="../min/js/require.js"></script>
<script>
  require.config({
    baseUrl: "../min/js/web",
    waitSeconds: 0
  });
  require( ["main"],
    function() {
        console.log("loaded main.js");
    }
  );
</script>
</html>
