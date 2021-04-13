<<<<<<< HEAD
window.onload = addMatches();

let matchSet = new Set();

=======
let matchSet = new Set();

>>>>>>> 8ac62b43ba94ba2ae066e9db57f99c0414de0848
function addMatchDiv(matched) {
    const match = document.createElement("div");
    match.className = "match";
    match.id = "matchCard";
    const name = document.createElement("h3");
    name.innerHTML = matched.firstName;

<<<<<<< HEAD

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
=======

    document.getElementById("match-list").appendChild(match);
}

function addMatches() {

>>>>>>> 8ac62b43ba94ba2ae066e9db57f99c0414de0848
}