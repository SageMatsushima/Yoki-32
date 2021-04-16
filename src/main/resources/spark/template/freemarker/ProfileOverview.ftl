<#assign nav>
<nav>
    <a href="/yoki"><img src="/images/whiteLogo.png" id="logo"></a>
    <a href="/yoki"><div class="sidebar">Home</div></a>
    <a href="/match"><div class="sidebar">Matches</div></a>
    <a href="/learn"><div class="sidebar">Learn</div></a>
    <a href="/teach"><div class="sidebar">Teach</div></a>
    <a href="/profileOverview"><div class="sidebar">My Profile</div></a>
    <a href="/settings"><div class="sidebar">Settings</div></a>
</nav>
</#assign>

<#assign save>
    <button class="interestBtn" id="match-button" onclick="save()">Save</button>
</#assign>

<#assign editProfile>
    <button id="match-button" class="request" onclick="">Edit Profile</button>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/profileOverview.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<script src="script/interestButtons.js"></script>
<body onload="allInterests()">
<div id="profile">
    ${nav}
    <h2>My Profile</h2> <br>
    <div id="main">
        <div id="side">
            <div id="profileTag">
                <div id="info">
                    <h4>Sage</h4> <h4>pronouns</h4><br>
                    <h5>grad Year</h5><br>
                    <h5>concentration</h5>
                    <h5>Email</h5>
                    <a href="/profileEdit"><button id="match-button" onclick="">Edit Profile</button></a>
                </div>
                <div id="picture"><img src="">Picture</div>
            </div>
            <div id="subjects">
                <h3>Interest Search</h3>
            </div>
        </div>

        <h2 id="title">Interests</h2>
        <div id="interestList">
            ${save}
        </div>
    </div>
</div>
</body>
</html>