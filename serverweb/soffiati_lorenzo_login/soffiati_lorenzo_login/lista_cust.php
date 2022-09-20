<?php
// x debug 
ini_set('display_errors', 'On');
error_reporting(E_ALL);

//include database connection
include 'libs/db_connect.php';
?>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8" />
        <title> MYSQL </title>
    </head>
<body>

<?php
	
	// Controllo esistenza cookie di sessione
	session_start();
	if(!$_SESSION["username"]){
		header("location: index.php");
	}

    $query = "SELECT distinct cli.customerName, cli.customerNumber as custumer, cli.customerName, cli.city, cli.country, pay.customerNumber   
		FROM customers AS cli left JOIN payments AS pay  ON cli.customerNumber = pay.customerNumber
		";	
    try {
        $num=0;
        $stmt = $con->prepare( $query );	// $con arriva da include 
        $stmt->execute();
		
        //Lettura numero righe risultato 
        $num = $stmt->rowCount();
    } catch(PDOException $ex) {
        print("Errore !".$ex->getMessage());
    }
	
	// Link log-out
	print("<a href='libs/log_out.php' style='font-size: 25px'> log-out </a>");
	print("<p></p>");
	
          
    //se num > 0 recordset vuoto o errore 
    if($num>0){
        print("<table border='1'>");
        print("<tr>");
        print("<th>N.</th>");
        print("<th>id </th>");
        print("<th>Customer</th>");
		print("<th>City</th>");
		print("<th>Country</th>");
        print("</tr>");
        $n=1;
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            print("<tr>");
            print("<td>".$n."</td>");
			$idcust=$row['custumer'];
            print("<td>".$row['custumer']."</td>");
            print("<td>".$row['customerName']."</td>");
            print("<td>".$row['city']."</td>");
            print("<td>".$row['country']."</td>");
			
			// genera pseudo form per ordini
			if($row['customerNumber'] != null){
				print("
					<td>
					<form  method='post' action='cust_ord.php'> 
					<input type='hidden' name='idcust' value='$idcust'>
					<input type ='submit' value='ordini'>
					</form>
					</td>");
			}
			else{
				print("<td> </td>");
			}
						
            print("</tr>");
		    $n++;
        }
        print("</table>");
    }
    else{
        print("No records found.");
    }
?> 
</body>
</html>
