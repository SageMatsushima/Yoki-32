const matchInterests = document.getElementById("top_interests_list");
const matchList = document.getElementById('matchList');
const matchMap = new Map();


function onMatchPressed() {
    let data = getNextMatch();
    matchMap.append(data.user.firstName, data.user);
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
            let matchName = document.getElementById('match-name');
            let matchGrade = document.getElementById('match-grade');
            let topInterests = document.getElementById('top_interests_list')
            console.log(data);
            matchName.innerHTML = data.user.firstName;
            matchGrade.innerHTML = "Class of " + data.user.year;
            topInterests.innerHTML = ""
            for (var interest in data.topCommonInterests) {
                topInterests.innerHTML += "<li> " + data.topCommonInterests[interest].name + " </li>";
            }
            console.log()
            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}