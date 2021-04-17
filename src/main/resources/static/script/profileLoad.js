//post request to the profile edit page
const interest = document.getElementsByClassName('slider');

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
            console.log(data);
            console.log(data.user);
            loadInfoDiv(data.user);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function loadInfoDiv(user) {
    const info = document.getElementById("user-info");
    const name = document.createElement("h4");
    name.innerText = user.firstName;
    const year = document.createElement("h5");
    year.innerText = user.year;

    info.append(name);
    info.append(year);
}