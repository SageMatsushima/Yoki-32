const addInterest = new Map();

function addInterestDiv(value) {
    const interest = document.createElement("div");
    interest.className = "interests";
    interest.id = "subject";
    const name = document.createElement("h3");
    name.innerHTML = value.name;
    const input = document.createElement("input");
    input.className = "slider";
    input.type = "range";
    input.min = "0";
    input.max = "10";
    input.value = "5";


    document.getElementById("interest").appendChild(interest);
    document.getElementById("subject").appendChild(name);
    document.getElementById("subject").appendChild(input);

    addInterest.append(value.name, input.value);
}

function allInterests() {
    fetch('http://localhost:4567/listInterests', {
        method: 'post',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            console.log(data)
            for (const [key, value] of Object.entries(data.interestsList)) {
                console.log(`${key}: ${value.name}`);
                const interest = document.createElement("button");
                interest.addEventListener("click", () => addInterestDiv(value));
                interest.className = "subject";
                interest.id = key;
                interest.innerHTML = value.name;
                document.getElementById("subjects").appendChild(interest);
            }
            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}

function onSavePressed(value) {
    save();
    matchMap.set(currUser.firstName, currUser);
}

function save() {
    fetch('http://localhost:4567/listInterests', {
        method: 'post',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            console.log(addInterest);
            console.log(value);
            // matchName.innerHTML = response.data.name;
            // matchGrade.innerHTML = "Class of " + response.data.grade;
            // matchMajor.innerHTML = response.data.major;
            // return response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}