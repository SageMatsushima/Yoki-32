function login() {
    const login = document.createElement("div");
    login.class = "options";
    login.id = "login";

    const name = document.createElement("h3");
    name.innerHTML = "Are you sure you want to logout?";
    const yes = document.createElement("button");
    yes.className = "match-button";
    yes.innerHTML = "Yes"
    yes.onclick = function() { logout(); };

    const no = document.createElement("button");
    no.className = "match-button";
    no.innerHTML = "No"
    no.onclick = function() { logout(); };

    document.getElementById("interest").appendChild(interest);
}

function logout() {

}

function lightDark() {
    const interest = document.createElement("div");
    interest.class = "options";
}

function termsConditions() {
    const popup = document.createElement("div");
    popup.class = "options";
    // popup.id = "";

}

function report() {
    const popup = document.createElement("div");
    popup.class = "options";
    popup.id = "";
}



