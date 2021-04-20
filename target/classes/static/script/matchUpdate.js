window.onload = addMatches();

let matchSet = new Set();
let matchInterests;
let currMatch;

function addMatchDiv(matched) {
    const match = document.createElement("div");
    //match.addEventListener('click', openMatchInfo);
    match.className = "match";
    match.id = "matchCard";
    const image = document.createElement("img");
    image.src = matched.images;
    image.alt = "image of " + matched.firstName;
    // image.src = "https://i.pinimg.com/originals/59/af/39/59af39192d3f0cbf7a89bcaf534ccd82.png"
    image.id = "match_image";

    const name = document.createElement("h3");
    name.innerHTML = matched.firstName;

    match.onclick = function() {
        currMatch = matched;
        getInterests()
    };
    // const card = document.createElement("div");
    // card.className = "card";

    match.appendChild(image);
    match.appendChild(name);

    document.getElementById("match-list").appendChild(match);
}


function getInterests() {
    const postPara = {
        id: currMatch.id
    };
    fetch('http://localhost:4567/getinterests', {
        method: 'post',
        body: JSON.stringify(postPara),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            console.log(data);
            matchInterests = data.topCommonInterests;
            openMatchInfo();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function openMatchInfo(){
    console.log(currMatch);
    const grayDiv = document.createElement("div");
    grayDiv.id = "grayDiv";
    grayDiv.className = "matchCardOverlay";
    grayDiv.onclick = function() {grayDiv.style.display = "none"};

    const card = document.createElement("div");
    card.id = "potential_card";

    const cardContent = document.createElement("div");
    cardContent.id = "card_content";

    const leftContent = document.createElement("div");
    leftContent.id = "left-content";

    const nameDiv = document.createElement("div");
    nameDiv.id = "name_info";

    const nameText = document.createElement("h1");
    nameText.id = "match-name";
    nameText.innerHTML = currMatch.firstName;

    const major = document.createElement("h3");
    major.id = "match-major";
    major.innerHTML = currMatch.major;

    const nameYear = document.createElement("h3");
    nameYear.id = "match-grade";
    nameYear.innerHTML = "Class of " + currMatch.year;

    //need to get interests to show
    const topInterests = document.createElement("ul");
    topInterests.id = 'top_interests_list';

    console.log("match interests" + matchInterests);

    for (var i in matchInterests) {
        let interest = matchInterests[i]
        let intDiv = '<div className="interest"><ul>' + interest.name + '</ul>'
            + '<progress className="interestBar" value="0" max="10"></progress></div>';
        topInterests.innerHTML += intDiv;
    }

    // let progressBars = document.getElementsByTagName("progress")
        // document.getElementsByTagName('progress');
    const bio = document.createElement('p');
    bio.innerHTML = currMatch.bio;
    bio.id = "user-bio";

    const rightContent = document.createElement("div");
    rightContent.id = "right-content";

    const image = document.createElement("img");
    image.src = currMatch.images;
    image.id = "match_image";
    image.alt = "image of " + currMatch.firstName;

    const emailDiv = document.createElement('div');
    emailDiv.id = "emaildiv";

    const emailtext = document.createElement('textarea');
    emailtext.value = currMatch.email;
    emailDiv.id = "emailtext";

    const copyEmail = document.createElement('button');
    copyEmail.innerHTML = "Copy Email";
    copyEmail.id = 'match-button';
    copyEmail.onclick = function() {
        const copyText = document.createElement('textarea');
        copyText.value = currMatch.email;
        document.body.appendChild(copyText);
        copyText.select();

        /* Copy the text inside the text field */
        document.execCommand("copy");

        /* Alert the copied text */
        alert("Copied the text: " + copyText.value);
        console.log("copied");
        document.body.removeChild(copyText);
    }

    const deleteButton = document.createElement('button');
    deleteButton.innerHTML = "Unmatch";
    deleteButton.id = 'match-button';
    deleteButton.onclick = removeMatch;



    grayDiv.appendChild(card);
    card.appendChild(cardContent);
    cardContent.appendChild(leftContent);
    leftContent.appendChild(nameDiv);
    leftContent.appendChild(topInterests);
    nameDiv.appendChild(nameText);
    nameDiv.appendChild(major);
    nameDiv.appendChild(nameYear);
    cardContent.appendChild(rightContent);
    rightContent.appendChild(image);
    rightContent.appendChild(bio);
    leftContent.appendChild(emailDiv);
    leftContent.appendChild(deleteButton);
    emailDiv.appendChild(emailtext);
    emailDiv.appendChild(copyEmail);

    if (document.getElementById("grayDiv") != null) {
        document.getElementById("grayDiv").remove();
    }
    document.getElementById("main").appendChild(grayDiv);

    // start progress bar animations
    let progressBars = document.getElementsByTagName("progress")
    for (var i in matchInterests) {
        let interest = matchInterests[i]
        console.log(progressBars[i])
        console.log(interest.score)
        move(progressBars[i], interest.score)
    }
}

function removeMatch() {
    const postPara = {
        id: currMatch.id
    };
    fetch('http://localhost:4567/deleteMatch', {
        method: 'post',
        body: JSON.stringify(postPara),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            window.location.reload('true');
        })
        .catch(function (error) {
            console.log(error);
        });
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
            let count = 0;
            matchSet = data.matchSet;
            for (const v of matchSet) {
                count++;
                addMatchDiv(v);
            }
            console.log(count);
            if (count > 0) {
                document.getElementById("noMatchText").style.display = "none";
            }
            //matchMajor.innerHTML = response.data.major;
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