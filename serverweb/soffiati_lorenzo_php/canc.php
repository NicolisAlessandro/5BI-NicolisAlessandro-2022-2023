<?php 
include "libs/util.php";
include "libs/db_connect.php";
?>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8" />
        <title> MYSQL </title>
    </head>
<body>
<?php

		if(isset( $_GET["id"])){
			$id=$_GET["id"];
			$query = "delete from brani where id = ?";  
			//prepare query for excecution
			$stmt = $con->prepare($query);
			$par=array($id);
			// Execute the query
			if($stmt->execute($par)){
				echo "Tupla $id eliminata.";
			}else{
				echo ('errore...');
			}
  
		}

    $query = "SELECT id,autore,titolo,dataIns,numAscolti,durata from brani ";
    try {
        $stmt = $con->query( $query );	// $con arriva da include
          
		print("<table border='1'>
             <tr>
             <th> N. </th>
             <th>id </th>
             <th>autore</th>
             <th>titolo</th>
             <th> del</th>
             </tr>");
             $nb=1;
			 while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                print("<tr><td>".$nb."</td>");
                print("<td>".$row['id']."</td>");
                print("<td>".$row['autore']."</td>");
                print("<td>".$row['titolo']."</td>");
                print("<td><a href='canc.php?id=".$row['id']."'>DEL</a></td>");
                print("</tr>");
                $nb++;
			 }
			echo "</table>";
	} catch(PDOException $ex) {
            echo "Errore !".$ex->getMessage();
    }

?> 

</body>
</html>
