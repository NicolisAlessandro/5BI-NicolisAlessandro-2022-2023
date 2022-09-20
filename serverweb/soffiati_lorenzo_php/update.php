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
		$query = "SELECT * from brani FROM" + $id;
		
		try {
			$stmt = $con->query( $query );
			$row = $stmt->fetch(PDO::FETCH_ASSOC);
			
			print("<form method='post' action='do_update.php'>");
			
			print("<lable name='id' value='" + $_GET["id"] + "'/>");
			print("<p>Autore</p><input type='text' name='autore' value='".$row['autore']."'/><br>");
			print("<p>titolo</p><input type='text' name='titolo' value='".$row['titolo']."'/><br>");
			print("<p>data</p><input type='text' name='data' value='".$row['dataIns']."'/><br>");
			print("<p>ascolti</p><input type='text' name='ascolti' value='".$row['numAscolti']."'/><br>");
			print("<p>durata</p><input type='text' name='durata' value='".$row['durata']."'/><br>");
			print("<input type ='submit' value='update'>");
			
			print("</form>");
		}
	}
		
?>

</body>
</html>
		