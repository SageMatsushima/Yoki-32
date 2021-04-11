<#assign nav>
<nav>
    <a href="/yoki"><div class="sidebar">Home</div></a> <br>
    <a href="/match"><div class="sidebar">Matches</div></a> <br>
    <a href="/learn"><div class="sidebar">Learn</div></a> <br>
    <a href="/teach"><div class="sidebar">Teach</div></a> <br>
    <a href="/profileOverview"><div class="sidebar">My Profile</div></a> <br>
    <a href="/settings"><div class="sidebar">Settings</div></a> <br>
</nav>
</#assign>

<#assign interest>
    <div class="interest">
        <h3>Interest</h3>
        <input type="range" min="1" max="100" value="50" class="slider">
    </div>
</#assign>

<#assign interestButton>
    <button id="interestBtn">Add Interest</button>
</#assign>

<#assign editProfile>
    <button id="editProfile" onclick="">Edit Profile</button>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/profileOverview.css">
</head>
<body>
<div id="profile">
    ${nav}
    <h2>My Profile</h2> <br>
    <div id="main">
        <div id="profileTag">
            <div id="info">
                <h4>Sage</h4> <h4>pronouns</h4><br>
                <h5>grad Year</h5><br>
                <h5>concentration</h5>
                <h5>Email</h5>
                ${editProfile}
            </div>
            <div id="picture"><img src="">Picture</div>
        </div>
        <h2 id="title">Interests</h2>
        <div id="interest">
            ${interest}
            ${interest}
            ${interestButton}
        </div>
    </div>
</div>
</body>
</html>