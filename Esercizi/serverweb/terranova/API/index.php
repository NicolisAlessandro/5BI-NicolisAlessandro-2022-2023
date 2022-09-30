<?php
require 'flight/Flight.php';
require 'library.php';


ini_set('display_errors', 'On');
error_reporting(E_ALL);

/*===============================================
	funzioni di base
=================================================*/

// Conessione al database
Flight::register('db', 'PDO', array('mysql:host=localhost;dbname=ardos','ardos','Ardos2022'));


/*===============================================
	funzioni per il get
=================================================*/
// Restituisce l'elenco di tutti i clienti con anche la possibilità di selezionarli per id
Flight::route('GET /clienti(/@id)', function($id){
	
	
	
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
		if(isset($id)){
			if($id != $decodifica->data->id) Flight::json(["code"=>401, "error"=>"operation not permitted"], $code=401);
		}
		else $id = $decodifica->data->id;	
	}
	else Flight::redirect('../page/login.html');
	
	
	try{
		$db = Flight::db();
		if(isset($id)){
			$query = $db->prepare("SELECT CLID, nome, cognome, ragione_sociale, email, numero_telefono FROM Clienti where CLID = :id");
			$query->bindParam(':id', $id);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}else{
			$query = $db->prepare("SELECT CLID, nome, cognome, ragione_sociale, email, numero_telefono FROM Clienti");
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Restituisce l'elenco di tutte le fatture con anche la possibilità di selezionarle per id e di
// fare una query string per visualizzare quelle pagate e non (?pagate = <true/false>)
Flight::route('GET /fatture(/@id)', function($id){
	
	try{
		$db = Flight::db();
		$squery = "SELECT f.* FROM Fatture as f join Sedi as s on f.SID = s.SID";
		$pagato = Flight::request()->query->pagato;
		
		// Controllo jwt
		$jwt = Flight::request()->query->jwt;
		
		if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
		$clid = $decodifica->data->id;	
		}
		else Flight::redirect('../page/login.html');
			
		
		if(isset($id)){
			$squery.=" where s.CLID = :clid and f.FID = :id";
			if(isset($pagato)){
				if($pagato == "false"){ $squery.=" and f.data_pagamento is null"; }
				else if($pagato == "true"){ $squery.=" and f.data_pagamento is not null"; }
			}
			$query = $db->prepare($squery);
			$query->bindParam(':id', $id);
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}	
		}else{
			$squery.= " where s.CLID = :clid";
			if(isset($pagato)){
				if($pagato == "false"){ $squery.=" and f.data_pagamento is null"; }
				else if($pagato == "true"){ $squery.=" and f.data_pagamento is not null"; }
			}
			$query = $db->prepare($squery);
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Restituisce l'elenco di tutte le sedi con anche la possibilità di selezionarle per id
Flight::route('GET /sedi(/@id)', function($id){
	
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
		$clid = $decodifica->data->id;	
	}
	else Flight::redirect('../page/login.html');
	
	try{
		$db = Flight::db();
		if(isset($id)){
			$query = $db->prepare("SELECT * FROM Sedi where CLID = :clid and SID = :id");
			$query->bindParam(':id', $id);
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}else{
			$query = $db->prepare("SELECT * FROM Sedi where CLID = :clid");
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Restituisce l'elenco di tutti i contratti con anche la possibilità di selezionarli per id della sede associata
Flight::route('GET /contratti(/@id)', function($id){
	
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	$sid = Flight::request()->query->sede;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}	
		
		$clid = $decodifica->data->id;	
	}
	else Flight::redirect('../page/login.html');
	
	
	$squery = "SELECT c.* FROM Contratti as c join Sedi as s on c.SId = s.SID where s.ClID = :clid";
	
	try{
		$db = Flight::db();
		if(isset($id)){
			$squery.= " and c.CID = :id";
			if(isset($sid)) $squery .= " and c.SID = :sid";
			
			$query = $db->prepare($squery );
			if(isset($sid)) $query->bindParam(':sid', $sid);
			
			$query->bindParam(':id', $id);
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}else{
			if(isset($sid)) $squery.= " and c.SID = :sid";
			$query = $db->prepare($squery);
			if(isset($sid)) $query->bindParam(':sid', $sid);
			
			$query->bindParam(':clid', $clid);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Restituisce l'elenco di tutti i prodotti con anche la possibilità di selezionarli per id
Flight::route('GET /prodotti(/@id)', function($id){
	
	try{
		$db = Flight::db();
		if(isset($id)){
			$query = $db->prepare("SELECT * FROM Prodotti where PID = :id");
			$query->bindParam(':id', $id);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}else{
			$query = $db->prepare("SELECT * FROM Prodotti");
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});


// Restituisce i consumi totali/annuali o mensili di un determinato utente
Flight::route('GET /consumi(/@anno(/@mese))', function($anno, $mese){
	
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
		if(isset($id)){
			if($id != $decodifica->data->id) Flight::json(["code"=>401, "error"=>"operation not permitted"], $code=401);
		}
		else $id = $decodifica->data->id;	
	}
	else Flight::redirect('../page/login.html');

	$db = Flight::db();
	$squery = "select f.consumo, f.data_inizio, p.type
				from Fatture as f join Sedi as s on f.SID=s.SID join Contratti as co on co.CID=f.CID 
					join Prodotti as p on p.PID=co.PID
				where s.CLID = :id";
	
	if(isset($anno)){ $squery.=" and year(f.data_inizio)=:anno";}
	if(isset($mese)){ $squery.=" and month(f.data_inizio)=:mese";}
	
	
	$query = $db->prepare($squery);
	$query->bindParam(':id', $id);
	if(isset($anno)){ $query->bindParam(':anno', $anno);}
	if(isset($mese)){ $query->bindParam(':mese', $mese);}
	
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


// Restituisce l'elenco delle richieste
Flight::route('GET /richieste(/@id)', function($id){

	try{
		$db = Flight::db();
		if(isset($id)){
			$query = $db->prepare("SELECT * FROM Contatti where COID = :id");
			$query->bindParam(':id', $id);
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
			if(count($res)==0){
				Flight::json(["code"=>404, "error"=>"ID not found or invalid"], $code=404);
			}
		}else{
			$query = $db->prepare("SELECT * FROM Contatti");
			$query->execute();
			$res=$query->fetchall(PDO::FETCH_ASSOC);
		}
		Flight::json($res, $code=200);
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});

/*===============================================
	funzioni per il post
=================================================*/

// Singup (creazione account)
Flight::route('POST /singup', function(){
	
	/*
		struttura json ricevuto
		"email" : "prova@gmail.com"
		"password" : "12345678"
	*/
	
	// Recupero dei dati + hash della password
	$par = Flight::request()->data;
	$email = $par->email;
	$pssw = password_hash($par->password, PASSWORD_DEFAULT);
	
	// Inserimento dell'utente
	$db = Flight::db();
	$query = $db->prepare("INSERT INTO Clienti (email, password) VALUES (:email, :pssw)");
	$query->bindParam(':email', $email);
	$query->bindParam(':pssw', $pssw);
	$query->execute();
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	
	
	// Selezione dell'ID dell'utente
	$db = Flight::db();
	$query = $db->prepare("select CLID from Clienti where email=:email");
	$query->bindParam(':email', $email);
	$query->execute();
	$id=$query->fetch(PDO::FETCH_ASSOC);
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	
	$res = "https://local.marconivr.it/ardos/API/clienti/".$id['CLID'];

		
	Flight::json(["location"=>$res], $code=201);
		
});


// Login (controllo usernamen e password)
Flight::route('POST /login', function(){
	/*
		struttura json ricevuto
		"email" : "prova@gmail.com"
		"password" : "12345678"
	*/
	
	// Recupero dei dati + hash della password
	$par = Flight::request()->data;
	$email = $par->email;
	$pssw = $par->password;
	
	// Controllo dell'utente
	$db = Flight::db();
	$query = $db->prepare("select password, CLID from Clienti where email=:email");
	$query->bindParam(':email', $email);
	$query->execute();
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	$res=$query->fetch(PDO::FETCH_ASSOC);
	
	if(password_verify($pssw, $res["password"])){
		Flight::json(["code"=>201, "jwt"=>creajwt($res["CLID"], $email)], $code=201);
	}else{
		Flight::json(["code"=>404, "error"=>"username o password non valido"], $code=404);
	}
	
});


// Salvataggio delle richieste fatte dalla pagina contact
Flight::route('POST /addRichiesta', function(){
	
	/*
		struttura json
		"nome": "stringa",
		"email": "string",
		"messaggio": "stringa molto lunga"		
	*/
	
	// Recupero dei dai
	$par = Flight::request()->data;
	$nome = $par->nome;
	$email = $par->email;
	$messaggio = $par->messaggio;
	
	
	// Query aggiunta
	$db = Flight::db();
	$query = $db->prepare("INSERT INTO Contatti (nome, email, messaggio) VALUES (:nome, :email, :messaggio)");
	$query->bindParam(':nome', $nome);
	$query->bindParam(':email', $email);
	$query->bindParam(':messaggio', $messaggio);
	$query->execute();
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	
	// Selezione dell'ID della richiesta
	$db = Flight::db();
	$query = $db->prepare("select COID from Contatti where email=:email and nome=:nome and messaggio=:messaggio");
	$query->bindParam(':nome', $nome);
	$query->bindParam(':email', $email);
	$query->bindParam(':messaggio', $messaggio);
	$query->execute();
	$id=$query->fetch(PDO::FETCH_ASSOC);
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	
	$res = "https://local.marconivr.it/ardos/API/richieste/".$id['COID'];

		
	Flight::json(["location"=>$res], $code=201);
	
	
});

/*===============================================
	funzioni per il put
=================================================*/

// Segnare fatture pagate 
Flight::route('POST /pagato/@id', function($id){
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
	}
	else Flight::redirect('../page/login.html');
	
	
	$db = Flight::db();
	$query = $db->prepare("UPDATE Fatture SET data_pagamento = :data WHERE SID = :id and data_pagamento is null");
	$now = date("Y-m-d");
	$query->bindParam(':data', $now);
	$query->bindParam(':id', $id);
	$query->execute();
	$res=$query->fetchall(PDO::FETCH_ASSOC);
	
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	Flight::json(["code"=>204], $code=204);
	
});


// Modifica di un utente
Flight::route('POST /modifica(/@id)', function($id){
	/*
		struttura json ricevuta (tutti i campi sono facoltativi eccetto jwt)
		
		"nome": "prova",
		"cognome": "prova",
		"ragione_sociale": "???",
		"email": "prova@gmail.com",
		"numero_telefono": "348 510 78547",
		"password" : "nuova password",
	*/
	
	// Controllo jwt
	$jwt = Flight::request()->query->jwt;
	
	if(isset($jwt)){
		try {$decodifica = valida($jwt);}
		catch(Exception $e) {Flight::json(["code"=>403, "error"=>$e], $code=403);}
		
		if(isset($id)){
			if($id != $decodifica->data->id) Flight::json(["code"=>401, "error"=>"operation not permitted"], $code=401);
		}
		else $id = $decodifica->data->id;	
	}
	else Flight::redirect('../page/login.html');
	
	
	$querys = "UPDATE Clienti SET";
	$prima = false;
	$variabili = array(1=> false, 2=> false, 3=> false, 4=> false, 5=> false, 6=> false);
	$campi = array(1=> "nome", 2=> "cognome", 3=> "ragione_sociale", 4=> "email", 5=> "numero_telefono", 6=> "password");
	$valori = array(1=> "", 2=> "", 3=> "", 4=> "", 5=> "", 6=> "");
	
	// Recuepero dati
	$par = Flight::request()->data;
	if(isset($par->nome)){
		$valori[1] = $par->nome;
		$variabili[1]=true;
		$prima = true;
		$querys.=" nome = :nome";
	}
	if(isset($par->cognome)){
		$valori[2] = $par->cognome;
		$variabili[2]=true;
		if($prima) $querys.=",";
		$prima = true;
		$querys.=" cognome = :cognome";
	}
	if(isset($par->ragione_sociale)){
		$valori[3] = $par->ragione_sociale;
		$variabili[3] = true;
		if($prima) $querys.=",";
		$prima = true;
		$querys.=" ragione_sociale = :ragione_sociale";
	}
	if(isset($par->email)){
		$valori[4] = $par->email;
		$variabili[4]=true;
		if($prima) $querys.=",";
		$prima = true;
		$querys.=" email = :email";
	}
	if(isset($par->numero_telefono)){
		$valori[5] = $par->numero_telefono;
		$variabili[5]=true;
		if($prima) $querys.=",";
		$prima = true;
		$querys.=" numero_telefono = :numero_telefono";
	}
	if(isset($par->password)){
		$valori[6] = password_hash($par->password, PASSWORD_DEFAULT);
		$variabili[6]=true;
		if($prima) $querys.=",";
		$prima = true;
		$querys.=" password = :password";
	}
	
	$querys.=" WHERE CLID = :id";
	echo($querys);
	
	// Inserimento dei parametri
	$db = Flight::db();
	$query = $db->prepare($querys);
	
	$query->bindParam(':id', $id);
	for($i=1 ; $i<7; $i++){
		if($variabili[$i] == true){
			$temp = ':'.$campi[$i];
			$query->bindParam($temp, $valori[$i]);
		}
	}

	// Esecuzione
	$query->execute();
	$res=$query->fetchall(PDO::FETCH_ASSOC);
	
	if($query->errorInfo()[2] != null){
		Flight::json(["code"=>404, "error"=>$query->errorInfo()[2]], $code=404);
	}
	Flight::json(["code"=>204], $code=204);
	
});



/*===============================================
	funzioni di test e varie
=================================================*/

Flight::route('GET /test', function(){
	try{
		print("test ok");
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>$e], $code=500);
	}
});

Flight::route('GET /test/jwt', function(){
	try{
		$json = creajwt(1, "prova@gmail.com");
		$token = $json["jwt"];

		print(json_encode($json));
		print("<br><br>");
		print(json_encode(valida($token)));
		print("<br><br>");
		
		
		
	}catch(Exception $e){
		Flight::json(["code"=>500, "error"=>json_encode($e)], $code=500);
	}
});


Flight::route('GET /', function(){
		
	// Da mettere redirect a descrizione API 
	
	print("Esempio di fornitore dati ajax<br>
			/test    --> api di test <br>
			/fatture --> elenco di tutte le fatture <br>
			/clienti --> elenco di tutti i clienti <br>
			/sedi	 --> elenco delle sedi dei clienti <br>
			/contratti --> elenco dei contratti fatti <br>
			/prodotti --> elenco di tutti i prodotti <br>
			/consumi/@id --> elenco dei consumi per id ");
});

Flight::start();


?>
