window.onload = addMatches();

let matchSet = new Set();

function addMatchDiv(matched) {
    const match = document.createElement("div");
    match.className = "match";
    match.id = "matchCard";
    const name = document.createElement("h3");
    name.innerHTML = matched.firstName;


    document.getElementById("match-list").appendChild(match);
    match.appendChild(name);
}

function addMatches() {
    fetch('http://localhost:4567/getmatch', {
        method: 'get',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            matchSet = data.matchSet;
            for (const v of matchSet) {
                console.log(v);
                addMatchDiv(v);
            }
            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}