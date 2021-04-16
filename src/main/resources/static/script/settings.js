function login() {
    const pop = document.getElementById("login");
    const popp = document.getElementById("popup");
    if (pop !== null) {
        pop.remove();
    } else if (popp !== null) {
        popp.remove();
    }

    const login = document.createElement("div");
    login.class = "options";
    login.id = "login";

    const name = document.createElement("h3");
    name.innerHTML = "Are you sure you want to logout?";

    const buttons = document.createElement("div");
    buttons.id = "buttonDiv";
    const yes = document.createElement("button");
    yes.id = "match-button";
    yes.innerHTML = "Yes"
    yes.onclick = function() { logout(); };

    document.getElementById("main").appendChild(login);
    login.appendChild(name);
    login.appendChild(buttons);
    buttons.appendChild(yes);
}

function logout() {

}

function lightDark() {
    const pop = document.getElementById("login");
    const popp = document.getElementById("popup");
    if (pop !== null) {
        pop.remove();
    } else if (popp !== null) {
        popp.remove();
    }

    const login = document.createElement("div");
    login.class = "options";
    login.id = "login";

    const buttons = document.createElement("div");
    buttons.id = "buttonDiv";
    const light = document.createElement("button");
    light.id = "match-button";
    light.innerHTML = "Light"
    light.onclick = function() { logout(); };

    const dark = document.createElement("button");
    dark.id = "match-button";
    dark.innerHTML = "Dark"
    dark.onclick = function() { logout(); };

    document.getElementById("main").appendChild(login);
    login.appendChild(buttons);
    buttons.appendChild(light);
    buttons.appendChild(dark);
}

function termsConditions() {
    const pop = document.getElementById("login");
    const popp = document.getElementById("popup");
    if (pop !== null) {
        pop.remove();
    } else if (popp !== null) {
        popp.remove();
    }

    const popup = document.createElement("div");
    popup.id = "popup";

    document.getElementById("main").appendChild(popup);

}

function report() {
    const pop = document.getElementById("login");
    const popp = document.getElementById("popup");
    if (pop !== null) {
        pop.remove();
    } else if (popp !== null) {
        popp.remove();
    }

    const popup = document.createElement("div");
    popup.id = "popup";

    const nameDiv = document.createElement("div");
    nameDiv.id = "name"
    const name = document.createElement("h3");
    name.innerHTML = "Username: ";
    const nameInput = document.createElement("input");

    const gradDiv = document.createElement("div");
    gradDiv.id = "grad";
    const gradYear = document.createElement("h3");
    gradYear.innerHTML = "Graduation Year: ";
    const year = document.createElement("input");

    const reasonDiv = document.createElement("div");
    reasonDiv.id = "reason";
    const reason = document.createElement("h3");
    reason.innerHTML = "Reason: ";
    const reasonInput = document.createElement("input");

    document.getElementById("main").appendChild(popup);
    popup.appendChild(nameDiv);
    nameDiv.appendChild(name);
    nameDiv.appendChild(nameInput);
    popup.appendChild(gradDiv);
    gradDiv.appendChild(gradYear);
    gradDiv.appendChild(year);
    popup.appendChild(reasonDiv);
    reasonDiv.appendChild(reason);
    reasonDiv.appendChild(reasonInput);
}



