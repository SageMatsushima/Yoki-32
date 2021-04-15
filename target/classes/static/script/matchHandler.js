let matchMap = new Map();
let currUser;
window.onload = getNextMatch();


function onMatchPressed() {
    getNextMatch();
    console.log(currUser);
    matchMap.set(currUser.firstName, currUser);
    console.log(matchMap);
    setBackMatch();
}

function setBackMatch() {
    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        id: currUser.id,
        first: currUser.firstName,
        last: currUser.lastName
    };

    fetch('http://localhost:4567/sendmatch', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
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
            matchName.innerHTML = data.user.firstName;
            matchGrade.innerHTML = "Class of " + data.user.year;
            topInterests.innerHTML = ""
            currUser = data.user;
            for (var i in data.topCommonInterests) {
                let interest = data.topCommonInterests[i]
                let intDiv = '<div className="interest"><ul>' + interest.name + '</ul>'
                    + '<progress className="interestBar" value="' + interest.score + '" max="10"></progress></div>';
                topInterests.innerHTML += intDiv;
            }


            let matchList = document.getElementById('match-list');
            Object.keys(matchMap).map(function(key) {
                matchList.innerHTML = "<li> " + matchMap[key].firstName + " </li>";
            });

            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}