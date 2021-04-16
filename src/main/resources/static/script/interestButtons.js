const addInterest = new Map();

/**
 * Adds an interest to the user.
 * @param value for dictionary
 * @param key for dictionary
 */
function addInterestDiv(value, key) {
    if (!addInterest.has(key)) {
        const interest = document.createElement("div");
        interest.className = "interests";
        interest.id = key + "remove";

        const nameButton = document.createElement("div");
        nameButton.id = "nameButton";
        nameButton.style.display = "flex";
        const name = document.createElement("h4");
        name.innerHTML = value.name;
        const remove = document.createElement("button");
        remove.className = "remove";
        remove.innerHTML = "Remove";
        remove.onclick = function() { removes(key); };

        const input = document.createElement("input");
        input.className = "slider interestValue";
        input.id = value.id;
        input.type = "range";
        input.min = "0";
        input.max = "10";
        input.value = "5";

        //ocument.getElementById("interest").appendChild(interest);
        interest.appendChild(nameButton);
        document.getElementById("interestList").appendChild(interest);
        nameButton.appendChild(name);
        nameButton.appendChild(remove);
        interest.appendChild(input);
        addInterest.set(key,  input.value);
    }

}

/**
 * Makes the list of clickable buttons of the interests in the database
 */
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
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * Updates the interests based on the users input.
 */
function updateInterest() {
    for (let i of document.getElementsByClassName("interestValue")) {
        addInterest.set(i.id, i.value);
    }
}

/**
 * Saves the interests of the user and sends it to the backend.
 */
function save() {
    updateInterest();

    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        interests: Object.fromEntries(addInterest)
    };
    console.log(JSON.stringify(Array.from(addInterest)));
    fetch('http://localhost:4567/updateInterests', {
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

/**
 * Removes the interest from the users data.
 */
function removes(key) {
    removeInterest(key);
    console.log(addInterest);
    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        interests: addInterest
    };
    fetch('http://localhost:4567/listInterests', {
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

function removeInterest(key) {
    console.log(key);
    let interest = document.getElementById(key + "remove");
    interest.innerHTML = '';
    interest.textContent = '';
    // while (interest.firstChild) {
    //     interest.removeChild(interest.lastChild);
    // }
    interest.remove();

    addInterest.delete(key);
}