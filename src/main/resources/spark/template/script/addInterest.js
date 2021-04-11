const interest = document.getElementById('subject');
const matchMap = new Map();

function onInterestPressed() {
    let response = addInterest();
    matchMap.append(response.data.name, response);
}

function addInterest() {
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            console.log(response.data.name)
            interest.innerHTML = response.data.name;
            return response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}

function addInterestDiv() {
    const interest = document.createElement("div");
    interest.className = "interests";
    interest.id = "subject";
    const name = document.createElement("h3");
    name.innerHTML = "Interest";
    const input = document.createElement("input");
    input.className = "slider";

    document.div.appendChild(name);
    document.div.appendChild(input);
    document.getElementById("interest").appendChild(div);
    console.log("here")

}

function nameValue() {
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
    .then(response => {
        console.log(response.data.name)
        matchName.innerHTML = response.data.name;
        matchGrade.innerHTML = "Class of " + response.data.grade;
        matchMajor.innerHTML = response.data.major;
        return response.data;
    })
        .catch(function (error) {
            console.log(error);
        });
}


// Needs post request!!! to update the interests!!! ^^