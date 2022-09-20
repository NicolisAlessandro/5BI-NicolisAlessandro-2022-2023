/*
	Script per recuperare i dati dalle API
*/

function centraline(){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) return JSON.parse(xmlHttp.responseText);
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/centraline" , false); 
	
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}


function info(){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			insertCentraline(JSON.parse(xmlHttp.responseText));
		}
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/info" , false); 
	
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}


function coordinate(){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) return JSON.parse(xmlHttp.responseText);
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/cordinate" , false); 
	
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}


function rilevazioni(){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) return xmlHttp.responseText;
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/rilevazioni" , false); 
	
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}


function ozono(id){
	try{
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function() { 
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) insertOzono(JSON.parse(xmlHttp.responseText));
			if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
		}
		xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/ozono/" + id , false); 
		
		xmlHttp.setRequestHeader("Content-Type", "application/json");
		xmlHttp.send(null);
	}catch (e){}
}


function pm10(id){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) insertpm(JSON.parse(xmlHttp.responseText));
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404 && xmlHttp.responseText != null) return null;
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/pm10/" + id , false); 
	
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}



let elemento = ""



function insertCentraline(inf){
	elemento += "<main class='row title'><ul><li>Nome</li><li>Comune</li><li>Provincia</li></ul></main>";
	for(var i=0; i<inf.length; i++){
		elemento += "<article class='row nfl'> <ul><li><a href='#'>";
		elemento += inf[i].stazioni_nome;
		elemento += "</a></li><li>"
		elemento += inf[i].stazioni_comune;
		elemento +="</li><li>";
		elemento += inf[i].stazioni_provincia;
		elemento +="</li></ul></article>";
		//sottotabelle
		elemento +="<section class='content' style='padding:2%; background-color:#1c152b;'>";
		elemento +="<div class='left'>";		//tabella sinistra
		elemento +="<h3>OZONO</h3>";
		elemento +="<main class='row title'><ul><li>Data</li><li>Misurazione</li></ul></main>";
		
		try {ozono(inf[i].stazioni_codseqst);}
		catch (e){}
		
		elemento +="</div>";
		elemento +="<div class='right'>";		//tabella destra
		elemento +="<h3>PM-10</h3>";
		elemento +="<main class='row title'><ul><li>Data</li><li>Misurazione</li></ul></main>";
		
		
		try {pm10(inf[i].stazioni_codseqst);}
		catch (e){}
		
		elemento +="</div>";
		elemento +="</section>";
	}
	//loader per prova
	//elemento += '<div class="loader--container"> <div class="loader--small">   <div class="loader--small__circle"></div>    <div class="loader--small__circle"></div><div class="loader--small__circle"></div> </div> </div>';
	
	document.getElementById("tab").innerHTML = elemento;
	
}



function insertOzono(o){
	//120/100
	for (var j=0;j<o.length;j++){
		try {
			if (o[j].stazioni_misurazioni_ozono_mis<100){
				elemento += "<article class='row mlb'>";
			}else if  (o[j].stazioni_misurazioni_ozono_mis<120){
				elemento += "<article class='row org'>";
			}else if  (o[j].stazioni_misurazioni_ozono_mis>=120){
				elemento += "<article class='row red'>";
			}
			elemento += "<ul><li style='width: 35%'>";
			elemento += o[j].stazioni_misurazioni_ozono_data;
			elemento +="</li><li style='width: 35%'>";
			elemento += o[j].stazioni_misurazioni_ozono_mis;
			elemento +="</li></ul></article>";
		}catch (e){elemento +="</li></ul></article>";}
	}
	
}



function insertpm(p){
	//50/45
	try{
		for (var j=0;j<p.length;j++){
			if (p[j].stazioni_misurazioni_pm10_mis<45){
				elemento += "<article class='row mlb'>";
			}else if  (p[j].stazioni_misurazioni_pm10_mis<50){
				elemento += "<article class='row org'>";
			}else if  (p[j].stazioni_misurazioni_pm10_mis>=50){
				elemento += "<article class='row red'>";
			}
				elemento += "<ul><li style='width: 35%'>";
				elemento += p[j].stazioni_misurazioni_pm10_data;
				elemento +="</li><li style='width: 35%'>";
				elemento += p[j].stazioni_misurazioni_pm10_mis;
				elemento +="</li></ul></article>";
		}
	}catch (e){elemento +="</li></ul></article>";}

}