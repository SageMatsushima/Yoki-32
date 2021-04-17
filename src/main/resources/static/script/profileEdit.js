window.onload(getUserInfo());

function getUserInfo() {
    console.log("load profile")
    fetch('/profileInfo', {
        method: 'post',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            setUser(data.user);

        })
        .catch(function (error) {
            console.log(error);
        });
}

function setUser(user) {
    document.getElementById("firstInput").value = user.firstName;
    document.getElementById("lastInput").value = user.lastName;
    document.getElementById("pronounInput").value = user.pronouns;
    document.getElementById("majorInput").value = user.major;
    document.getElementById("gradYearInput").value = user.year;
    document.getElementById("emailInput").value = user.email;
    document.getElementById("bioBox").innerText = user.bio;
}

function updateProfile() {
    console.log(document.getElementById("firstInput").value);
    const postParameters = {
        first: document.getElementById("firstInput").value,
        last: document.getElementById("lastInput").value,
        pronouns: document.getElementById("pronounInput").value,
        major: document.getElementById("majorInput").value,
        gradYear: document.getElementById("gradYearInput").value,
        email: document.getElementById("emailInput").value,
        bio: document.getElementById("bioBox").innerText
    };
    fetch('http://localhost:4567/updateProfile', {
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