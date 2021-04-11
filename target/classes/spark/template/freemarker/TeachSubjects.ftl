<#assign nav>
<nav>
    <a href="/yoki"><div class="sidebar">Home</div></a> <br>
    <a href="/match"><div class="sidebar">Matches</div></a> <br>
    <a href="/learn"><div class="sidebar">Learn</div></a> <br>
    <a href="/teach"><div class="sidebar">Teach</div></a> <br>
    <a href="/profileEdit"><div class="sidebar">My Profile</div></a> <br>
    <a href="/settings"><div class="sidebar">Settings</div></a> <br>
</nav>
</#assign>

<#assign subject>
    <button class = "subject" id="match-button">
        subject
    </button>
</#assign>

<#assign request>
    <button id="request">Request</button>
</#assign>

<#assign user>
    <div class="user">
        <div id="idPhoto">
            <img src="">
        </div>
        <div id="info">
            <h5>Name</h5>
            <h5>subject to learn</h5>
            <h5>Email</h5>
        </div><br>
    </div>
</#assign>

<#assign search>
    <div>
        <img src="">
        <input id="search" name= "search" type="text">
    </div>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/teachSubjects.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<body>
    <div id="profile">
        ${nav}
        <h2>Requests</h2><br>
        <div id="main">
            <div id="subjects">
                <h3>Subjects</h3>
                ${search}
                <div id="options">
                    ${subject}
                    ${subject}
                </div>
            </div>
            <div id="results">
                ${user}
                ${request}
            </div>
        </div>
    </div>

</body>
</html>