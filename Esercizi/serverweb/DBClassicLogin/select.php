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
    $query = "SELECT * from actor where fname = ?";
    try {
            $num=0;
            
            $stmt = $con->prepare( $query );	// $con arriva da include 
            $stmt -> binde
            $stmt->execute();
            //Lettura numero righe risultato 
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            
            
            $num = $stmt->rowCount();
          
    } catch(PDOException $ex) {
            echo "Errore !".$ex->getMessage();
    }
          
          
    echo "<table border='1'>";
        echo "<tr>";
            echo "<th> N. </th>";
            echo "<th>id </th>";
            echo "<th>nome</th>";
            echo "<th>cognome</th>";
        echo "</tr>";
  
        $np=1;
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            echo "<tr>";
                echo "<td>".$np."</td>";
                echo "<td>".$row['actor_id']."</td>";
                echo "<td>".$row['first_name']."</td>";
                echo "<td>".$row['last_name']."</td>";
            echo "</tr>\n";
            $np++;
        }
    echo "</table>";
?> 

</body>
</html>
