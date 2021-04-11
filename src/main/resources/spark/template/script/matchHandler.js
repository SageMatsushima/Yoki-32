const matchName = document.getElementById('match-name');
const matchGrade = document.getElementById('match-grade');
const matchMajor = document.getElementById('match-major');
const matchInterests = document.getElementById("top_interests_list");
const matchList = document.getElementById('matchList');
const matchMap = new Map();


function onMatchPressed() {
    let response = getNextMatch();
    matchMap.append(response.data.firstName, response);
}


function getNextMatch(){
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            console.log(response.data)
            matchName.innerHTML = response.data.firstName;
            matchGrade.innerHTML = "Class of " + response.data.year;
            //matchMajor.innerHTML = response.data.major;
            return response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}