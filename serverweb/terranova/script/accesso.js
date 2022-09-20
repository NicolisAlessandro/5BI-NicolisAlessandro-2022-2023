//<script type="text/javascript" src="../script/accesso.js"></script>

function controlloValidità(){
	
	if ( sessionStorage.getItem("token") == null || sessionStorage.getItem("endTime") == null){
		
		sessionStorage.clear();
		return false;
	}
	else {
		var token = sessionStorage.getItem("token");
		var endtime = sessionStorage.getItem("endTime");
		if( endtime - (Date.now()/1000) <= 0) return false;
		else return true;
	}
}

function logout(){
	sessionStorage.clear();
	window.location.href = "../index.html";
}