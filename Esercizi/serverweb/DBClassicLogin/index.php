<?php
include 'libs/util.php';
session_start();
// ci sarà uno user?
$user=getVal($_SESSION,"username");
//print_r($_SESSION);
?>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8" />
        <title> MYSQL </title>
    </head>
<body>
<?php
if ($user!=""){
	print("
	<h2> Benvenuto $user <a href='logout.php'>logout</A></h2>
	");
}else{
    print(" 
    <h2> <a href='login.php'>login</A></h2>
    ");
}
?>
<ul>
<li> <a href="lista_cust.php">Lista Clienti </a> </li>

</ul>


</body>
</html>
