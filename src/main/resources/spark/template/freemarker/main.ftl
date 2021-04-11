<#assign nav>
  <nav>
    <a href="/yoki"><img src="/images/whiteLogo.png" id="logo"></a> <br>
    <a href="/yoki"><div class="sidebar">Home</div></a> <br>
    <a href="/match"><div class="sidebar">Matches</div></a> <br>
    <a href="/learn"><div class="sidebar">Learn</div></a> <br>
    <a href="/teach"><div class="sidebar">Teach</div></a> <br>
    <a href="/profileOverview"><div class="sidebar">My Profile</div></a> <br>
    <a href="/settings"><div class="sidebar">Settings</div></a> <br>
  </nav>
</#assign>

<#assign card>
  <div id = "potential_card">
    <div id = "card_content">
      <div id = "left-content">
        <div id = "name_info">
          <h1 id="match-name">Kendall</h1>
          <h3 id="match-grade">Class of 2023</h3>
        </div>
        <div id = "top_interests">
          <h3>Interests</h3>
          <ul id = "top_interests_list">
            <li>
              modeling
            </li>
            <li>
              being rich
            </li>
            <li>
              Asap Rocky
            </li>
          </ul>
        </div>
      </div>
      <div id = "right-content">
        <img id = "match_image" src="./images/kendall.jpg">
      </div>
    </div>
  </div>
</#assign>

<#assign matchButton>
 <button class = "button" id="match-button" onclick="onMatchPressed()">
   match
 </button>
</#assign>

<#assign passButton>
  <button class = "button" id="pass-button" onclick="getNextMatch()">
    pass
  </button>
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
  <div id="profile">
    ${nav}
    <script src="script/matchHandler.js"></script>
    <div id = "card-div">
      ${card}
      <div id = "button-div">
        ${passButton}
        ${matchButton}
      </div>
    </div>
  </div>
     <!-- Again, we're serving up the unminified source for clarity. -->
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
