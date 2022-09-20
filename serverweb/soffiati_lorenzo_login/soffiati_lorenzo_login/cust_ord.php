<?php
// x debug 
ini_set('display_errors', 'On');
error_reporting(E_ALL);
//include database connection
include 'libs/util.php';
// se esiste il parametro ok altrimenti via
$id=getVal($_POST,"idcust");
if ($id==""){
	header('Location: index.php');
	exit;
}


include 'libs/db_connect.php';
?>

<!DOCTYPE HTML>
<html>
    <head>
        <title>Ordini</title>
  
    </head>
<body>

<?php
	
	// Controllo esistenza cookie di sessione
	session_start();
	if(!$_SESSION["username"]){
		header("location: index.php");
	}
	
	// Link log-out
	print("<a href='libs/log_out.php' style='font-size: 25px'> log-out </a>");
	print("<p></p>");

// 1 visualizza i dati del customer 
    $query = "SELECT * from customers where customerNumber=?";
    try {
        $num=0;
        $stmt = $con->prepare( $query );	// $con arriva da include 
        $stmt->execute(array($id));
        //Lettura numero righe risultato 
    } catch(PDOException $ex) {
        print("Errore !".$ex->getMessage());
    }

    // speriamo carichi un record!!!
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
	if (!$row){
            print("<h2> Customer inesistente <h2>");
    }else
	{
		print("<h3>");
		print($row['customerName']." ");
		print($row['contactLastName']." ");
		print($row['contactFirstName']." ");
		print($row['city']." ");
		print("</h3>");
		
		
		
		$query = "SELECT * from orders where customerNumber =?";
		try {
			$num=0;
			$stmt = $con->prepare( $query );	// $con arriva da include 
			$stmt->execute(array($id));
			//Lettura numero righe risultato 
			$num = $stmt->rowCount();
		} catch(PDOException $ex) {
			print("Errore !".$ex->getMessage());
		}
			  
		//se num > 0 recordset vuoto o errore 
		if($num>0){
			print("<table border='1'>");
			print("<tr>");
			print("<th>N.</th>");
			print("<th>id </th>");
			print("<th>order date</th>");
			print("<th>status</th>");
			print("</tr>");
			$n=1;
			while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
				print("<tr>");
				print("<td>".$n."</td>");
				print("<td>".$row['orderNumber']."</td>");
				print("<td>".$row['orderDate']."</td>");
				print("<td>".$row['status']."</td>");
				print("</tr>");
				$n++;
			}
			print("</table>");
		}
		else{
			print("Nessun ordine!");
		}
	}
?>
</body>
</html>
