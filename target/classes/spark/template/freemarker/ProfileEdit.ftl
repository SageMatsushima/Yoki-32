<#assign nav>
<nav>
    <a href="/yoki"><img src="/images/whiteLogo.png" id="logo"></a>
    <a href="/yoki"><div class="sidebar">Home</div></a>
    <a href="/match"><div class="sidebar">Matches</div></a>
    <a href="/profileOverview"><div class="sidebar">My Profile</div></a>
    <a href="/settings"><div class="sidebar">Settings</div></a>
</nav>
</#assign>

<#assign save>
    <button id="save">Save</button>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/profileEdit.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<body>
    <div id="profile">
        ${nav}
        <h2>Edit Profile</h2><br>
        <div id="main">
            <div id="inputs">
                <div id="name">Name<br><input name= "name" type="text"></div>
                <div id="pronouns">Pronouns<br><label>
                    <input name= "pronouns" type="text">
                </label></div><br>
                <div id="major">Major<br><input name= "major" type="text"></div>
                <div id="gradYear">Grad Year<br><input name= "gradYear" type="text"></div><br>
                <div id="email">Email<br><input name= "email" type="text"></div> <br>
                <div id="bio">Bio<br><input id= "bioBox" type="text"></div>
            </div>
            <div id="picture">
                <img id = "profilePic" src="./images/kendall.jpg">
                <button id="changePic">Picture icon</button><br>
                ${save}
            </div>
        </div>
    </div>


</body>
</html>