<script>
    function subjectClick() {
<<<<<<< HEAD
        document.getElementById("subjectButton").style.color = "green";
=======
        document.getElementById("subjectButton").style.backgroundColor = "green";

>>>>>>> 1d3e20a621aba6c4875b8f3e30fdc4f28429a014
    }
</script>

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