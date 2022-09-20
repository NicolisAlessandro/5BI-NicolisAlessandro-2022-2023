<!DOCTYPE HTML>
<html>
    <head>
        <title>Modifica Record</title>
  
    </head>
<body>

<?php
ini_set('display_errors', 'On');
error_reporting(E_ALL);

include 'db_connect.php';
 
if($_POST){
    try{
  
        $query = "update brani set autore=?, titolo=? ,dataIs=? ,numAscolti=? ,durata=? where id=?";  
  
        //prepare query for excecution
        $stmt = $con->prepare($query);
		$par=array($_POST["autore"],
		          $_POST["titolo"],
		          $_POST["data"],
				  $_POST["ascolti"],
				  $_POST["durata"],
				  $_POST["id"]);
		            
  
        // Execute the query
        if($stmt->execute($par)){
            echo "Tupla Modificata.";
        }else{
            echo ('errore...');
        }
  
    }catch(PDOException $exception){ 
        echo "Error: " . $exception->getMessage();
    }
}
?> 

</body>
</html>
