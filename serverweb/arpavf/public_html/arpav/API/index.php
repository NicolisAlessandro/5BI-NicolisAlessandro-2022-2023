<?php
require 'flight/Flight.php';


ini_set('display_errors', 'On');
error_reporting(E_ALL);


//########################### funzioni di base ######################


// Conessione al database
Flight::register('db', 'PDO', array('mysql:host=localhost;dbname=ardos','ardos','Ardos2022'));


//########################### API base ##################################
// #### Centraline #####
// Centraline
Flight::route('GET /centraline(/@id)', function($id){
	
	if(isset($id)) $squery = "select * from centraline where stazioni_codseqst=:id";
	else $squery = "select * from centraline";
	
	
	$db = Flight::db();
	$query = $db->prepare($squery);
	if(isset($id)) $query->bindParam(':id', $id);
	
	try{
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Centraline
Flight::route('GET /info(/@id)', function($id){
	
	if(isset($id)) $squery = "select stazioni_codseqst,stazioni_nome,stazioni_comune,stazioni_provincia from centraline where stazioni_codseqst=:id";
	else $squery = "select stazioni_codseqst,stazioni_nome,stazioni_comune,stazioni_provincia from centraline";
	
	
	$db = Flight::db();
	$query = $db->prepare($squery);
	if(isset($id)) $query->bindParam(':id', $id);
	
	try{
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Centraline coordinate
Flight::route('GET /coordinate(/@id)', function($id){
	
	if(isset($id)) $squery = "select stazioni_codseqst,stazioni_nome,stazioni_lat,stazioni_lon from centraline where stazioni_codseqst=:id";
	else $squery = "select stazioni_codseqst,stazioni_nome,stazioni_lat,stazioni_lon from centraline";
	
	
	$db = Flight::db();
	$query = $db->prepare($squery);
	if(isset($id)) $query->bindParam(':id', $id);
	
	try{
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});

//##### Rilevazioni #####
// Rilevazioni generale
Flight::route('GET /rilevazioni(/@anno)', function($anno){
	
	if(isset($anno)){
		//$squeryo = "select 'ozono' as type,stazioni_codseqst,stazioni_misurazioni_ozono_data,stazioni_misurazioni_ozono_mis from ozono where year(stazioni_misurazioni_ozono_data)=:anno";
		$squeryp = "select 'pm10' as type,stazioni_codseqst,stazioni_misurazioni_pm10_data,stazioni_misurazioni_pm10_mis from pm10 where year(stazioni_misurazioni_pm10_data)=:anno";
	}else{
		//$squeryo = "select 'ozono' as type,stazioni_codseqst,stazioni_misurazioni_ozono_data,stazioni_misurazioni_ozono_mis from ozono";
		$squeryp = "select 'pm10' as type,stazioni_codseqst,stazioni_misurazioni_pm10_data,stazioni_misurazioni_pm10_mis from pm10";
	}
	//$squeryo.=" order by stazioni_misurazioni_ozono_data ";
	$squeryp.=" order by stazioni_misurazioni_pm10_data ";
	

	try{
		/*// Ozono
		$db = Flight::db();
		$query = $db->prepare($squeryo);
		
		if(isset($anno)) $query->bindParam(':anno', $anno);	
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		
		$array = $res;*/
		
		// Pm10
		$db = Flight::db();
		$query = $db->prepare($squeryp);
		
		if(isset($anno)) $query->bindParam(':anno', $anno);
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		
		//$array = array_merge($array, $res);
		$array = $res;
		
		Flight::json($array, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Ozono
Flight::route('GET /ozono(/@id)', function($id){
	
	if(isset($id)) $squery = "select * from ozono where stazioni_codseqst=:id order by stazioni_misurazioni_ozono_data desc limit 10 ";
	else $squery = "select * from ozono order by stazioni_misurazioni_ozono_data desc limit 10";
	
	
	$db = Flight::db();
	$query = $db->prepare($squery);
	if(isset($id)) $query->bindParam(':id', $id);
	
	try{
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Pm10
Flight::route('GET /pm10(/@id)', function($id){
	
	if(isset($id)) $squery = "select * from pm10 where stazioni_codseqst=:id order by stazioni_misurazioni_pm10_data limit 10";
	else $squery = "select * from pm10 order by stazioni_misurazioni_pm10_data desc limit 10";
	
	
	$db = Flight::db();
	$query = $db->prepare($squery);
	if(isset($id)) $query->bindParam(':id', $id);
	
	try{
		$query->execute();
		$res=$query->fetchall(PDO::FETCH_ASSOC);
		
		if(count($res)==0){
			Flight::json(["code"=>404, "error"=>"object not found or invalid"], $code=404);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


//############################## Test ####################
Flight::route('GET /', function(){
		
	print("test");
});

Flight::start();


?>
