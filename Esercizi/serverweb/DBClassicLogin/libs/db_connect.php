<?php

$host = "172.16.1.98";
$db_name = "classicmodels";
$username = "ut99999";
$password = "pw99999";
 
try {
    $con = new PDO("mysql:host={$host};dbname={$db_name}", $username, $password, array(PDO::ATTR_PERSISTENT => true));
	$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

}
catch(PDOException $exception){
    echo "Connection error: " . $exception->getMessage();
}
?>
