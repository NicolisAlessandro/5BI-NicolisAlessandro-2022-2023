<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <title> MYSQL </title>
		
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">	
	
	<link rel="stylesheet" href="css/css.css">
	
	
<?php
	ini_set('display_errors', 'On');
	error_reporting(E_ALL);
	
		
	// Se username e password sono valori
	if(isset($_POST["username"]) && isset($_POST["password"])){
		
		
		$user = $_POST["username"];
		$pssw = $_POST["password"];
		
		
		if($user == "admin" && $pssw == "admin"){
			
			// Aggiunta cookie di sessione
			session_start();
			$_SESSION["username"] = $user;
			
			header("location: lista_cust.php");
		}
		
		print("
		<script>
		  console.log('username o password errato');
		</script>
		
		");
	}
	
?>
</head>
<body>
	<div class="centro contenitore">

	<form action="index.php" method="POST">
		<p class="titolo">Log-in</p>
		
		<input type='text' class="spazio" placeholder='Username' name='username'>
		<br>
		<input type='password' class="spazio" autocomplete=cc-number placeholder='Password' name='password'>
		<br>
		<input type ='submit' class="spazio invio" value="Invio"> 
		
	</form>
	</div>
	
</html>
