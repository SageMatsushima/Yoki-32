const addInterest = new Map();
<<<<<<< HEAD
=======

function addInterestDiv(value, key) {
    const interest = document.createElement("div");
    interest.className = "interests";
    interest.id = "subject";
    const name = document.createElement("h3");
    name.innerHTML = value.name;
    const input = document.createElement("input");
    input.className = "slider interestValue";
    input.id = value.id;
    input.type = "range";
    input.min = "0";
    input.max = "10";
    input.value = "5";
>>>>>>> 890b11e34e8d62496df397e12301925bba43eb96

function addInterestDiv(value, key) {
    if (!addInterest.has(key)) {
        const interest = document.createElement("div");
        interest.className = "interests";
        interest.id = "subject";
        const name = document.createElement("h3");
        name.innerHTML = value.name;
        const input = document.createElement("input");
        input.className = "slider interestValue";
        input.id = value.id;
        input.type = "range";
        input.min = "0";
        input.max = "10";
        input.value = "5";


        document.getElementById("interest").appendChild(interest);
        document.getElementById("subject").appendChild(name);
        document.getElementById("subject").appendChild(input);
        addInterest.set(key,  input.value);
    }

<<<<<<< HEAD
=======
    document.getElementById("interest").appendChild(interest);
    document.getElementById("subject").appendChild(name);
    document.getElementById("subject").appendChild(input);
    addInterest.set(key,  input.value);
>>>>>>> 890b11e34e8d62496df397e12301925bba43eb96
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
                interest.addEventListener("click", () => addInterestDiv(value, key));
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

function updateInterest() {
    for (let i of document.getElementsByClassName("interestValue")) {
        addInterest.set(i.id, i.value);
    }
}

function save() {
    updateInterest();
<<<<<<< HEAD
    console.log(addInterest);
    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        interests: addInterest
    };
    fetch('http://localhost:4567/listInterests', {
=======

    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        interests: Object.fromEntries(addInterest)
    };
    console.log(JSON.stringify(Array.from(addInterest)));
    fetch('http://localhost:4567/updateInterests', {
>>>>>>> 890b11e34e8d62496df397e12301925bba43eb96
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