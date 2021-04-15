window.onload = addMatches();

let matchSet = new Set();

function addMatchDiv(matched) {
    const match = document.createElement("div");
    match.className = "match";
    match.id = "matchCard";
    const image = document.createElement("img");
    image.src = "./images/kendall.jpg";
    image.id = "match_image";

    const name = document.createElement("h3");
    name.innerHTML = matched.firstName;

    // const card = document.createElement("div");
    // card.className = "card";

    match.appendChild(image);
    match.appendChild(name);

    document.getElementById("match-list").appendChild(match);
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