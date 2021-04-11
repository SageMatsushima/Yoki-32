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

<#assign logout>
    <button class="settings">Logout</button><br>
</#assign>

<#assign LD>
    <button class="settings">Light/Dark Mode</button><br>
</#assign>

<#assign TC>
    <button class="settings">Terms and Conditions</button><br>
</#assign>

<#assign report>
    <button class="settings">Report</button>
</#assign>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/settings.css">
</head>
<body>
    <div id="profile">
        ${nav}
        <h2>Settings</h2><br>
        <div id="main">
            <div id="options">
                ${logout}
                ${LD}
                ${TD}
                ${report}
            </div>
        </div>
    </div>


</body>
</html>