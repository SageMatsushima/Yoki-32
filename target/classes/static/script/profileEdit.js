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
<<<<<<< HEAD
            console.log(data.user);
=======
>>>>>>> 85422cf049b43f8da2556c5ec67c955b49e799ad

        })
        .catch(function (error) {
            console.log(error);
        });
}

function setUser(user) {
    document.getElementById("firstInput").value = user.firstName;
    document.getElementById("lastInput").value = user.lastName;
<<<<<<< HEAD
=======
    document.getElementById("pronounInput").value = user.pronouns;
>>>>>>> 85422cf049b43f8da2556c5ec67c955b49e799ad
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
<<<<<<< HEAD
=======
        pronouns: document.getElementById("pronounInput").value,
>>>>>>> 85422cf049b43f8da2556c5ec67c955b49e799ad
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