let matchSet = new Set();

function addMatchDiv(matched) {
    const match = document.createElement("div");
    match.className = "match";
    match.id = "matchCard";
    const name = document.createElement("h3");
    name.innerHTML = matched.firstName;


    document.getElementById("match-list").appendChild(match);
}

function addMatches() {

}