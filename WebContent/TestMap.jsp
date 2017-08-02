<!DOCTYPE html>
             <html>
              <head>
                     <title>Localizing the Map</title>
                    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<style>
    html, body, #map-canvas {
        margin: 0;
        padding: 0;
        height: 100%;
    }
</style><!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="plugins/iCheck/flat/blue.css">
  <!-- Morris chart -->
  <link rel="stylesheet" href="plugins/morris/morris.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
                
<script src="http://maps.google.com/maps?file=api&v=2&key=AIzaSyBP5wM7FziIOyjASsYufkUSHrw2cXkdrtA"
	type="text/javascript"></script>
                  <script>


                  function load() { 
              		if (GBrowserIsCompatible()) { 
              			
              			var map = new GMap2(document.getElementById("world-map2")); 
              			map.setCenter(new GLatLng(7.8731, 80.7718), 7); 
              			map.addControl(new GSmallMapControl()); 
              			map.addControl(new GOverviewMapControl());              			
              			
              			 var myLatLng =  new GLatLng(6.9271, 79.8612) ;
              			 console.log(myLatLng);
              		
              			 var marker = new GMarker(new GLatLng(6.9271, 79.8612), { title:"My Title" });
              			map.addOverlay(marker);
              			
              		} 	
              		
              	} 
     </script>
   
  
  </head>
  

<body onload ="load()">
<div id="world-map2" style="width:500px;height:380px;"></div>
</body>
   </html>