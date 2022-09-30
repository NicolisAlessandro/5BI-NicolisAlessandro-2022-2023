<?php
// legge da un array senza errori, al limite stringa vuota 
function getVal($Arr,$key){
   $ret="";
   if( isset( $Arr[$key] ) ) 
	   $ret=$Arr[$key];
   return $ret;
}
?>
