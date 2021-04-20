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
            getPicture(data.user);
            console.log(data.user);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function setUser(user) {
    document.getElementById("firstInput").value = user.firstName;
    document.getElementById("lastInput").value = user.lastName;
    document.getElementById("majorInput").value = user.major;
    document.getElementById("gradYearInput").value = user.year;
    document.getElementById("emailInput").value = user.email;
    document.getElementById("bioBox").innerText = user.bio;
}

function updateProfile() {
    updatePic();
    const postParameters = {
        firstName: document.getElementById("firstInput").value,
        lastName: document.getElementById("lastInput").value,
        major: document.getElementById("majorInput").value,
        year: document.getElementById("gradYearInput").value,
        email: document.getElementById("emailInput").value,
        bio: document.getElementById("bioBox").value,
        image: document.getElementById("picture").src
    };
    fetch('http://localhost:4567/updateUser', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })  .then((response) =>
            response.json())
        .then((data) => {
            window.location.href = "/profileEdit";
        })
        .catch(function (error) {
            console.log(error);
        });
}

function getPicture(user) {
    const div = document.getElementById("picDiv");
    div.id = "div";
    const picture = document.createElement("img");
    picture.id = "picture"
    picture.src = user.images;

    const change = document.createElement("button");
    change.id = "match-button";
    change.className = "changePic";
    const pic = document.createElement("img");
    pic.src = "images/camera.png"
    pic.id = "camera";
    change.append(pic);
    picture.append(change);
    change.onclick = function() { inputText(user); };

    const saveButton = document.createElement("button");
    saveButton.className = "save";
    saveButton.innerText = "Save";
    saveButton.id = "match-button";
    saveButton.onclick = function() { updateProfile(); };

    div.append(change);
    div.append(picture);
    div.append(saveButton);
}

function updatePic() {
    const text = document.getElementById("text");
    document.getElementById("picture").src = text.value;

}

function inputText(user) {
    const inputElement = document.getElementById("text");
    if (inputElement !== null) {
        inputElement.remove();
    }

    const picture = document.getElementById("div");
    const text = document.createElement("input");
    text.id = "text";
    console.log(user.images);
    text.value = user.images;
    picture.src = text.value;
    picture.append(text);
}

