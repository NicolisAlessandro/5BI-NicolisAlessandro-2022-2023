<?php
include 'libs/util.php';
session_start();
// ci sarà uno user?
//$user=getVal($_SESSION,"username");
//print_r($_SESSION);


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
if ($user!=""){
	print("
	<h2> Benvenuto $user <a href='logout.php'>logout</A></h2>
	");
}else{
    print(" 
    <h2> <a href='login.php'>login</A></h2>
    ");
}


    $query = "SELECT customerNumber,customerName,city,country from customers";
    try {
        $num=0;
        $stmt = $con->prepare( $query );	// $con arriva da include 
        $stmt->execute();
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
        print("<th>Customer</th>");
		print("<th>City</th>");
		print("<th>Country</th>");
        print("</tr>");
        $n=1;
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            print("<tr>");
            print("<td>".$n."</td>");
			$idcust=$row['customerNumber'];
            print("<td>$idcust</td>");
            print("<td>".$row['customerName']."</td>");
            print("<td>".$row['city']."</td>");
            print("<td>".$row['country']."</td>");
			
			// genera pseudo form per ordini
			print("
				<td>
				<form  method='post' action='cust_ord.php'> 
				<input type='hidden' name='idcust' value='$idcust'>
				<input type ='submit' value='ordini'>
				</form>
				</td>"); 
						
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
