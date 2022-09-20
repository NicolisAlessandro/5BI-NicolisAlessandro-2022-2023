
function initMap() {
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 12,
      center: { lat: 34.84555, lng: -111.8035 },
    });
   
    // Creazione delgi items che diventerano i marker
    const tourStops = [
      [{ lat: 34.8791806, lng: -111.8265049 }, "Boynton Pass"],
      [{ lat: 34.8559195, lng: -111.7988186 }, "Airport Mesa"],
      [{ lat: 34.832149, lng: -111.7695277 }, "Chapel of the Holy Cross"],
      [{ lat: 34.823736, lng: -111.8001857 }, "Red Rock Crossing"],
      [{ lat: 34.800326, lng: -111.7665047 }, "Bell Rock"],
    ];
    
    const infoWindow = new google.maps.InfoWindow();
  
    // Create the markers.
    tourStops.forEach(([position, title], i) => {
      const marker = new google.maps.Marker({
        position,
        map,
        title: `${i + 1}. ${title}`,
        label: `${i + 1}`,
        optimized: false,
      });
  
    
      // Add a click listener for each marker, and set up the info window.
      marker.addListener("click", () => {
        infoWindow.close();
        infoWindow.setContent(marker.getTitle());
        infoWindow.open(marker.getMap(), marker);
      });
    });
  }
  
  window.initMap = initMap;