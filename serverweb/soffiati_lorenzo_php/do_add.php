<!DOCTYPE HTML>
<html>
    <head>
        <title>Modifica Record</title>
  
    </head>
<body>

<?php
ini_set('display_errors', 'On');
error_reporting(E_ALL);
//include database connection
include 'libs/db_connect.php';
 
if($_POST){
    try{
  
        $query = "insert brani (autore,titolo,dataIns,numAscolti,durata) values(?,?,?,?,?)";  
  
        //prepare query for excecution
        $stmt = $con->prepare($query);
		$par=array($_POST["aut"],
		          $_POST["tit"],
		          date ("Y/m/d"),
		          1,
		          $_POST["dur"]);  
  
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
