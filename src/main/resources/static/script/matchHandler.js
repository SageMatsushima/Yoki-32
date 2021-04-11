const matchName = document.getElementById('match-name');
const matchGrade = document.getElementById('match-grade');
const matchMajor = document.getElementById('match-major');
const matchInterests = document.getElementById("top_interests_list");
const matchList = document.getElementById('matchList');
const matchMap = new Map();


function onMatchPressed() {
    let response = getNextMatch();
    //matchMap.append(response.data., response);
}


function getNextMatch(){
    fetch('http://localhost:4567/yokimatch', {
        method: 'get',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            console.log(data);
            matchName.innerHTML = data.firstName;
            matchGrade.innerHTML = "Class of " + data.year;
            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}