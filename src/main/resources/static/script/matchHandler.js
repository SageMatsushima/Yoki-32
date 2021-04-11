let matchMap = new Map();
let currUser;


function onMatchPressed() {
    getNextMatch();
    //console.log(currUser);
    matchMap.set(currUser.firstName, currUser);
    console.log(matchMap);
}

function setBackMatches() {
    fetch('http://localhost:4567/setmatch', {
        method: 'post',
        body: JSON.stringify(matchMap),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            matchMap = data;
        })
        .catch(function (error) {
            console.log(error);
        });
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
            for (var i in data.topCommonInterests) {
                let interest = data.topCommonInterests[i]
                let intDiv = '<div className="interest"><ul>' + interest.name + '</ul>'
                    + '<progress className="interestBar" value="' + interest.score + '" max="10"></progress></div>';
                topInterests.innerHTML += intDiv;
            }
            let matchList = document.getElementById('match-list');
            setBackMatches();
            Object.keys(matchMap).map(function(key) {
                matchList.innerHTML = "<li> " + matchMap[key].firstName + " </li>";
            });
            console.log()
            //matchMajor.innerHTML = response.data.major;
            currUser = data.user;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}