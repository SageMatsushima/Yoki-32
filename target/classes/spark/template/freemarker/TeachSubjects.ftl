<#assign nav>
<nav>
    <a href="/yoki"><img src="/images/whiteLogo.png" id="logo"></a>
    <a href="/yoki"><div class="sidebar">Home</div></a>
    <a href="/match"><div class="sidebar">Matches</div></a>
    <a href="/profileOverview"><div class="sidebar">My Profile</div></a>
    <a href="/settings"><div class="sidebar">Settings</div></a>
</nav>
</#assign>

<#assign subject>
    <button class = "subject" id="match-button">
        subject
    </button>
</#assign>

<#assign accept>
    <button class="request" id="match-button">Accept</button>
</#assign>

<#assign user>
    <div class="user">
        <div class="idPhoto">
            <img src="">
        </div>
        <div class="info">
            <h5>Name</h5>
            <h5>subject to learn</h5>
            <h5>Email</h5>
        </div>
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
                ${accept}
            </div>
        </div>
    </div>

</body>
</html>