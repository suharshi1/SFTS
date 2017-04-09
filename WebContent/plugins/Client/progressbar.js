function progress(percent, $el) {
                 $el.css('visibility', 'visible');
                 var progressBarWidth = percent * $el.width() / 100;

                 $el.find('div').animate({ width: progressBarWidth }, 500).html(percent + "%&nbsp;");

             }
   

         var globalP = 0;
         
             var startProgressBar = function () {
                 if (globalP < 100) {
                   //Ensure that it is visible
                   $("#progressBar").show();
                     globalP = globalP + 4;
                     progress(globalP, $('#progressBar'));
                     setTimeout(startProgressBar, 500);
                 }
                 else {
                     //It's fininshed
                     alert("Done");
                     //Set your progress to zero and hide the bar
                     globalP = 0;
                     progress(globalP, $('#progressBar'));
                     $("#progressBar").hide();
                 } 
             }