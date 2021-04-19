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

    const emailDiv = document.createElement("div");
    emailDiv.id = "nameDiv"
    const email = document.createElement("h3");
    email.innerHTML = "User's Email: ";
    const emailInput = document.createElement("input");
    emailInput.className = "inputs";
    emailInput.id = "report-email";

    const reasonDiv = document.createElement("div");
    reasonDiv.id = "reasonDiv";
    const reason = document.createElement("h3");
    reason.innerHTML = "Reason: ";
    const reasonInput = document.createElement("textarea");
    reasonInput.id = "reasonInput";
    reasonInput.className = "inputs";

    const errMsg = document.createElement("div")
    errMsg.innerText = "No user with that email!"
    errMsg.id = "report-status"

    const reportButton = document.createElement("button");
    reportButton.innerHTML = "Report";
    reportButton.id = "match-button";
    reportButton.className = "reportButton"
    reportButton.onclick = function() { sendReport(); };

    document.getElementById("main").appendChild(popup);
    popup.appendChild(emailDiv);
    emailDiv.appendChild(email);
    emailDiv.appendChild(emailInput);
    popup.appendChild(reasonDiv);
    reasonDiv.appendChild(reason);
    reasonDiv.appendChild(reasonInput);
    popup.append(errMsg)
    popup.appendChild(reportButton);
}

function sendReport() {
    const postParameters = {
        email: document.getElementById("report-email").value,
        report: document.getElementById("reasonInput").value
    };
    fetch('http://localhost:4567/report', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            let reportStatus = document.getElementById("report-status");
            reportStatus.style.opacity = 100;
            if (data.reported == "true") {
                reportStatus.style.color = "#C97800";
                reportStatus.innerText = "Report sent!";
            } else {
                reportStatus.style.color = "#b50f0f";
                reportStatus.innerText = "No user with that email!";
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}
