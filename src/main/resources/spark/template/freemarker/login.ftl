<#assign login>
    <img src="/images/whiteLogo.png" id="logo-login"><br>
    <div class="container">
        <label for="uname"><b>Username</b></label><br>
        <input type="text" placeholder="Enter Username" name="uname" required id="login-email" value="ian_henry_acosta@brown.edu"><br>

        <label for="psw"><b>Password</b></label><br>
        <input type="password" placeholder="Enter Password" name="psw" required id="login-password" value="123"><br>

        <div id="login-status">Incorrect login information!</div>

        <button type="submit" class="button" id="login-button" onclick="onLoginPressed()">Login</button>
    </div>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="css/matching.css">
</head>
<script src="script/login.js"></script>
<body>
    <div id="login-div">
        ${login}
    </div>


</body>
</html>