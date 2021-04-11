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