<?php

$host = "192.168.1.200";
$db_name = "generaledb";
$username = "root";
$password = "@L1o2p3i4";
 
try {
    
     $con = new PDO("mysql:host =$host ;dbname=$db_name", $username, $password, array(PDO::ATTR_PERSISTENT => true));
	 $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
     
     //$con = new PDO("pgsql:host={$host};port=3306;dbname={$db_name}", $username, $password);
     
}
  
// to handle connection error
catch(PDOException $exception){
    echo "Connection error: " . $exception->getMessage();
}
?>
