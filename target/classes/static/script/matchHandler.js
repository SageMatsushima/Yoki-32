let matchMap = new Map();
let currUser;
window.onload = getNextMatch();


function onMatchPressed(isMatch) {
    getNextMatch();
    console.log(currUser);
    matchMap.set(currUser.firstName, currUser);
    console.log(matchMap);
    setBackMatch(isMatch);
}

function setBackMatch(isMatch) {
    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        id: currUser.id,
        first: currUser.firstName,
        last: currUser.lastName,
        isMatch: isMatch
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
            let matchImage = document.getElementById("match_image");
            matchImage.onload = function() {
                matchImage.style.opacity = 100;
                
                let matchName = document.getElementById('match-name');
                let matchGrade = document.getElementById('match-grade');
                let topInterests = document.getElementById('top_interests_list')
                let matchMajor = document.getElementById('match-major')

                matchName.innerHTML = data.user.firstName;
                matchGrade.innerHTML = "Class of " + data.user.year;
                topInterests.innerHTML = ""
                matchMajor.innerHTML = data.user.major;
                currUser = data.user;
                for (var i in data.topCommonInterests) {
                    let interest = data.topCommonInterests[i]
                    let intDiv = '<div className="interest"><ul>' + interest.name + '</ul>'
                        + '<progress className="interestBar" value="0" max="10"></progress></div>';
                    topInterests.innerHTML += intDiv;
                }

<<<<<<< HEAD
                let matchList = document.getElementById('match-list');
                Object.keys(matchMap).map(function(key) {
                    matchList.innerHTML = "<li> " + matchMap[key].firstName + " </li>";
                });

                let progressBars = document.getElementsByTagName('progress');
                for (i = 0; i < progressBars.length; i++) {
                    console.log(progressBars[i])
                    move(progressBars[i],  data.topCommonInterests[i].score)
                }
            }

=======
<<<<<<< HEAD
<<<<<<< HEAD
            let matchImage = document.getElementById("match_image");
            matchImage.src = data.user.images;
=======
=======
            let matchImage = document.getElementById("match_image");
            matchImage.src = data.user.images;

>>>>>>> c6a63e7c995f1edbab6fe418658a685e2c739f1b
            let progressBars = document.getElementsByTagName('progress');
            for (i = 0; i < progressBars.length; i++) {
                console.log(progressBars[i])
                move(progressBars[i],  data.topCommonInterests[i].score)
            }

>>>>>>> e0e617a2e5de20cc7f7f8d155a299d6dabd97a36
            let matchList = document.getElementById('match-list');
            Object.keys(matchMap).map(function(key) {
                matchList.innerHTML = "<li> " + matchMap[key].firstName + " </li>";
            });

>>>>>>> fe0024bc2ceb3b66adb5f8381b9c7eeb5b14024e
            //matchMajor.innerHTML = response.data.major;
            matchImage.src = data.user.images;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}

const move = (progressBar, interestScore) => {
    progressBar.value = 0
    let i = 0;
    if (i == 0) {
        i = 1;
        var width = 0;
        var id = setInterval(frame, 10);
        function frame() {
            if (width >= interestScore) {
                clearInterval(id);
                i = 0;
            } else {
                width += 0.1;
                progressBar.value = width
            }
        }
    }
}