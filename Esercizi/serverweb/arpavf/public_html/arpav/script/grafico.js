
// fare il grafico di un anno diviso in mesi totale ultimo anno


let chart;

function grafico(pm10, ozono, anno){
	document.getElementById("Anno").innerHTML = anno;
	document.getElementById("loader").innerHTML = "";
	
	//var pm10= [0,0,0,0,0,0,0,0,0,0,0,0];
	//var ozono = [0,0,0,0,0,0,0,0,0,0,0,0];
	
    chart = new Chart(document.getElementById("mixed-chart"), {
        type: 'line',
        data: {
        labels: ["gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"],
        datasets: [{
            label: "PM10",
            type: "line",
            borderColor: "#878686",
            data: pm10,
            fill: false
            }, {
            label: "OZONO",
            type: "line",
            borderColor: "#009485",
            data: ozono,
            fill: false
            }, /*{
            label: "PM10",
            type: "bar",
            backgroundColor: "#5c5c5c",
            data: [408,547,675,734],
            }, {
            label: "OZONO",
            type: "bar",
            backgroundColor: "#009485",
            data: [133,221,783,2478]
            }*/
        ]
        },
        options: {
            responsive: true,
            plugins: {
                legend:{
                    labels:{
                        color: "#cccccc",
                        font:{size: 15,}
                    }
                }
            },
            scales: {
                y: {
                    ticks: {color: '#cccccc',},
                    grid: {
						drawBorder: false,
						color: function(context) {
							if (context.tick.value == 50) {
							  return '#ff0000';
							}
							if (context.tick.value == 120) {
							  return '#ff0000';
							}

							return '#cccccc';
						}
					}
                },
                x: {
                    ticks: {color: '#cccccc',},
                    grid: {color: '#cccccc',}
                }
            }
        }
    }
	);
}

function getMonthCon(anno){
	
	var loader = '<div class="loader--small grafico" style="margin-top:5%"><div class="loader--small__circle"></div><div class="loader--small__circle"></div><div class="loader--small__circle"></div></div>';
	document.getElementById("loader").innerHTML = loader;
	
	var pm10 = [0,0,0,0,0,0,0,0,0,0,0,0];
	var ozono = [0,0,0,0,0,0,0,0,0,0,0,0];
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() { 
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
			
			var r2=JSON.parse(xmlHttp.responseText);
			
			var pm10l = [0,0,0,0,0,0,0,0,0,0,0,0];
			var ozonol = [0,0,0,0,0,0,0,0,0,0,0,0];
			
			for (var i = 0; i < r2.length; i++){
				if (r2[i].type == "ozono"){
					var mese = parseInt(r2[i].stazioni_misurazioni_ozono_data.split("-")[1])-1;
					ozono[mese] += parseFloat(r2[i].stazioni_misurazioni_ozono_mis);
					ozonol[mese] += 1;
				}else if (r2[i].type == "pm10"){
					var mese = parseInt(r2[i].stazioni_misurazioni_pm10_data.split("-")[1])-1;
					pm10[mese] += parseFloat(r2[i].stazioni_misurazioni_pm10_mis);
					pm10l[mese] += 1;
				}
			}
			
			for (var i=0; i<pm10.length; i++){
				pm10[i] = pm10[i]/pm10l[i];
			}
			for (var i=0; i<ozono.length; i++){
				ozono[i] = ozono[i]/ozonol[i];
			}
			//document.getElementById("mixed-chart").update()
			grafico(pm10,ozono,anno);
		}
		
		if (xmlHttp.readyState == 4 && xmlHttp.status == 404){
			grafico(pm10,ozono,anno);
		}
	}
	xmlHttp.open("GET", "https://local.marconivr.it/ardos/API/rilevazioni/"+anno, true); // true for asynchronous 
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.send(null);
}

function switchAnno(anno){
	chart.destroy();
	getMonthCon(anno);
}