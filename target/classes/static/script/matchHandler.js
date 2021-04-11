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
        method: 'get',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
<<<<<<< HEAD:target/classes/static/script/matchHandler.js
            console.log(response.data);
=======
            console.log(response.data)
>>>>>>> 140dcf647d50f667da5f69628dbe0838744e2f1c:src/main/resources/spark/template/script/matchHandler.js
            matchName.innerHTML = response.data.firstName;
            matchGrade.innerHTML = "Class of " + response.data.year;
            //matchMajor.innerHTML = response.data.major;
            return response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}