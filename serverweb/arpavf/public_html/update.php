<?php
	$myfile = fopen("updatesql.txt", "r") or die("Unable to open file!");
	
	$host     = "localhost";   
	$user     = "ardos";	
	$pass     = "Ardos2022";
	$dbname   = "ardos";
	
	
	try {
		$con = new PDO("mysql:host={$host};dbname={$dbname}", $user, $pass,array(PDO::ATTR_PERSISTENT => true));
		$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		print("conesso <br>");
	}
	
	// to handle connection error
	catch(PDOException $exception){
		echo "Connection error: " . $exception->getMessage() . "<br>";
	}

	while(!feof($myfile)) {
		
		$riga = fgets($myfile);
		$query = $riga;
		$stmt = $con->query($query);
		
		print ($riga . "    ok <br>") ;
	}
	fclose($myfile);

	//https://developers.google.com/maps/documentation/javascript/examples/marker-accessibility
?>
