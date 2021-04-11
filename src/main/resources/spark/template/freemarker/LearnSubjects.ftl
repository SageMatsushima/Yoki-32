<script>
    function subjectClick() {
        document.getElementById("subjectButton").style.color = "green";
    }
</script>

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

<#assign subject>
    <button class = "subject" id="subjectButton" onclick="subjectClick">
        subject
    </button>
</#assign>

<#assign request>
    <button class="request">Request</button>
</#assign>

<#assign user>
    <div class="user">
        <div class="idPhoto">
            <img src="">
        </div>
        <div class="info">
            <h5>Name</h5>
            <h5>concentration</h5>
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
    <link rel="stylesheet" href="css/learnSubjects.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<body>
    <div id="profile">
        ${nav}
        <h2>Learn</h2><br>
        <div id="main">
            <div id="subjects">
                <h3>Subjects</h3>
                ${search}
                ${subject}
                ${subject}
            </div>
            <div id="results">
                ${user}
                ${request}
            </div>

        </div>
    </div>

</body>
</html>