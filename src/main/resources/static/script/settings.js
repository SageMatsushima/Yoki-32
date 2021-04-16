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
    name.id = "logout"

    const buttons = document.createElement("div");
    buttons.id = "buttonDiv";
    const yes = document.createElement("button");
    yes.id = "match-button";
    yes.className = "yes";
    yes.innerHTML = "Yes";
    yes.onclick = function() { logout(); };
    const aLink = document.createElement("a");
    aLink.href = "/yoki";

    document.getElementById("main").appendChild(login);
    login.appendChild(name);
    login.appendChild(aLink);
    aLink.append(buttons);
    buttons.appendChild(yes);
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
    light.className = "LD";
    light.innerHTML = "Light";
    light.onclick = function() { logout(); };

    const dark = document.createElement("button");
    dark.id = "match-button";
    dark.innerHTML = "Dark";
    dark.className = "LD";
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
    nameDiv.id = "nameDiv"
    const name = document.createElement("h3");
    name.innerHTML = "Username: ";
    const nameInput = document.createElement("input");
    nameInput.className = "inputs";

    const gradDiv = document.createElement("div");
    gradDiv.id = "gradDiv";
    const gradYear = document.createElement("h3");
    gradYear.innerHTML = "Graduation Year: ";
    const year = document.createElement("input");
    year.className = "inputs";

    const reasonDiv = document.createElement("div");
    reasonDiv.id = "reasonDiv";
    const reason = document.createElement("h3");
    reason.innerHTML = "Reason: ";
    const reasonInput = document.createElement("input");
    reasonInput.id = "reasonInput";
    reasonInput.className = "inputs";

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

// function logout() {
//     <
// }


