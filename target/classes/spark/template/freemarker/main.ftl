<#assign card>
  <div id = "potential_card">
    <div id = "card_content">
      <div id = "left-content">
        <div id = "name_info">
          <h1>Name</h1>
          <h3>Class</h3>
          <h3>Concentration</h3>
        </div>
        <div id = "top_interests">
          <h1>Interests</h1>
          <ul id = "top_interests_list"></ul>
        </div>
      </div>
      <div id = "right-content">
        <img id = "match_image" src="./images/kendall.jpg">
      </div>
    </div>
  </div>
</#assign>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
   <#-- <title>${title}</title>-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/matching.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  </head>
  <body>
     ${card}
     <!-- Again, we're serving up the unminified source for clarity. -->
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
