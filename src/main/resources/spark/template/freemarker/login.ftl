<#assign title>
    <a href="/yoki"><img src="/images/whiteLogo.png" id="logo"></a>
</#assign>

<#assign login>
    <div>
        <h3>Login</h3><br>
        <input>
    </div>
    <button class="settings" id="match-button" onclick="login()">Logout</button><br>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/settings.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<script src="script/settings.js"></script>
<body>
<div id="profile">
    ${title}
    <h2>Settings</h2><br>
    <div class="options">
        ${login}
        </div>
</div>


</body>
</html>