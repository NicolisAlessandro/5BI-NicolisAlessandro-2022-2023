<?php
include 'libs/util.php';


// se esiste il parametro ok altrimenti via
$user=test_input(getVal($_POST,"user"));
$pas1=getVal($_POST,"pas1");
$pas2=getVal($_POST,"pas2");
$mail=getVal($_POST,"mail");

$hspsw=password_hash($pas1,PASSWORD_DEFAULT);
print_r($_POST); 

if ($user=="" || $pas1 !=$pas2 || $pas1=="" ||  !filter_var($mail, FILTER_VALIDATE_EMAIL)){
	
	
	//header('Location: index.php');
	//exit;
}


include 'libs/db_connect.php';

    $query = "insert into user (username,password,mail,lastupdate) values (?,?,?,now())";
    try {
        $stmt = $con->prepare( $query );	// $con arriva da include 
        $stmt->execute(array($user,$hspsw,$mail));
        print("$user $pas1 $hspsw");
        
    } catch(PDOException $ex) {
        print("Errore !".$ex->getMessage());
    }

?>
