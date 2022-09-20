<!DOCTYPE HTML>
<html>
    <head>
        <title> APP </title>
  
    </head>
<body>
<?php

include 'libs/util.php';
$ref=getVal($_SERVER,'HTTP_REFERER'); // pagina da cui si proviene
$err=getVal($_GET,"ERR");
if ($err!="")
print("
<h3> Login Errato</h3>
");
?>	

<form action='chklogin.php' method='post' >
            Username<input type='text' name='username' /><br>
            Password <input type='password' name='password' /><br>
            <input type='hidden' name="referer" value='<?=$ref ?>' />
			<input type ='submit' value='ok'>
</form>

 
</body>
</html>

   
