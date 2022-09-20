<?php
// legge da un array senza errori, al limite stringa vuota 
function getVal($Arr,$key){
   $ret="";
   if( isset( $Arr[$key] ) ) 
	   $ret=$Arr[$key];
   return $ret;
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>
