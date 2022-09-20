

	var fr_piu = document.getElementsByName("frecciaPiu");
	var i;

	for (i = 0; i < fr_piu.length; i++) {
	  fr_piu[i].addEventListener("click", function() {
		getMonthCon(anno+1);
	  });
	}
	
	var fr_meno = document.getElementsByName("frecciaMeno");
	var i;

	for (i = 0; i < fr_meno.length; i++) {
	  fr_meno[i].addEventListener("click", function() {
		getMonthCon(anno-1);
	  });
	}