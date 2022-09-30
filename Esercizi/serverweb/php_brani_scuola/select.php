<?php 
include "db_connect.php";
?>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8" />
        <title> MYSQL </title>
    </head>
<body>
<?php
    $query = "SELECT * from brani ";
    try {
            $stmt = $con->query( $query );	// $con arriva da include
          
		print("<table border='1'>
             <tr>
             <th> N. </th>
             <th>id </th>
             <th>autore</th>
             <th>titolo</th>
             <th>Data Inserimento</th>
             <th>Ascolti</th>
             <th>durata</th>
             </tr>");
             $nb=1;
			 while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                print("<tr><td>$nb</td>");
                print("<td>".$row['id']."</td>");
                print("<td>".$row['autore']."</td>");
                print("<td>".$row['titolo']."</td>");
                print("<td>".$row['dataIs']."</td>");
                print("<td>".$row['numAscolti']."</td>");
                print("<td>".$row['durata']."</td>");
                print("</tr>\n");
                $nb++;
			 }
			echo "</table>";
	} catch(PDOException $ex) {
            echo "Errore !".$ex->getMessage();
    }

?> 

</body>
</html>
