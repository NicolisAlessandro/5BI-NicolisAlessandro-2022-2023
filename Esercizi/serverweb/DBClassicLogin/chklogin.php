<?php
include 'libs/util.php';
$user=test_input(getVal($_POST,"username"));
$pass=getVal($_POST,"password");

if ($user=="" ||  $pass=="" ){
	header('Location: index.php');
	exit();
}
session_start();
include 'libs/db_connect.php';
	$log=0;
	
	
    $query = "select * from user where username = ? ";
    try {
        $stmt = $con->prepare( $query );	// $con arriva da include 
		$stmt -> bindParam(1,$user);
        $stmt->execute();
		
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		
		
		
		
		
        $num = $stmt->rowCount();
        if ($num>0){
			$row = $stmt->fetch(PDO::FETCH_ASSOC);
			$hashpsw=$row["password"];
			if (password_verify($pass, $hashpsw)) {
				$log=1;
			}
		}
        
    } catch(PDOException $ex) {
    }
    
    if ($log==1){
		// Set session variables
		$_SESSION["iduser"] = $row["id"];
		$_SESSION["username"] = $row["username"];
		$_SESSION["usermail"] = $row["mail"];
		$ref=getVal($_POST,"referer");
		if ($ref=="")
			header('Location: index.php');
		else
			header('Location:'. $ref);
	}else {

		session_destroy();
		header('Location: login.php?ERR=1');
	}
	
    

?>
