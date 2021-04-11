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