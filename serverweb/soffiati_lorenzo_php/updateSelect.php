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
	$query = "SELECT id,autore,titolo,dataIns,numAscolti,durata from brani ";
    try {
        $stmt = $con->query( $query );
          
		print("<table border='1'>
             <tr>
             <th> N. </th>
             <th>id </th>
             <th>autore</th>
             <th>titolo</th>
             <th> update</th>
             </tr>");
             $nb=1;
			 while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                print("<tr><td>".$nb."</td>");
                print("<td>".$row['id']."</td>");
                print("<td>".$row['autore']."</td>");
                print("<td>".$row['titolo']."</td>");
                print("<td><a href='update.php?id=".$row['id']."'>update</a></td>");
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
