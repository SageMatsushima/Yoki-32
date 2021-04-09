<#assign nav>
<nav>
    <button class="sidebar">Home</button> <br>
    <button class="sidebar">Matches</button> <br>
    <button class="sidebar">Learn</button> <br>
    <button class="sidebar">Teach</button> <br>
    <button class="sidebar">My Profile</button> <br>
    <button class="sidebar">Settings</button> <br>
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
    <link rel="stylesheet" href="profileEdit.css">
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
                <img src="">
                <button id="changePic">Picture icon</button><br>
                ${save}
            </div>
        </div>
    </div>


</body>
</html>