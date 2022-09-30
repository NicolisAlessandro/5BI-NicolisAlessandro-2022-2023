<?php
require "vendor/autoload.php";
use \Firebase\JWT\JWT;
use Firebase\JWT\Key;


ini_set('display_errors', 'On');
error_reporting(E_ALL);



/*
	Creazione della jwt inserendo l'id e email dell'utente
*/
function creajwt($id, $email){
	$secret_key = "%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y@B&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3t6w9z@C&F)H@McQfTjWnZr";
	$issuer_claim = "local.marconivr.it/ardos"; 
	$issuedat_claim = time(); 
	$notbefore_claim = $issuedat_claim; 
	$expire_claim = $issuedat_claim + 28800;
	$token = array(
		"iss" => $issuer_claim,
		"iat" => $issuedat_claim,
		"nbf" => $notbefore_claim,
		"exp" => $expire_claim,
		"data" => array(
			"id" => $id,
			"email" => $email
	));


	$jwt = JWT::encode($token, $secret_key, 'HS256');
	return array(
			"message" => "Successful_login.",
			"jwt" => $jwt,
			"expireAt" => $expire_claim
		);

	
}


/*
	Validazione jwt
*/
function valida($token){
	$secret_key = "%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y@B&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3t6w9z@C&F)H@McQfTjWnZr";
	try{
		$decoded = JWT::decode($token, new key($secret_key, 'HS256'));
		return $decoded;
	}catch(Exception $e){
		return json_encode(array(
			"error" => $e
		));
	}
	
	
}


?>
