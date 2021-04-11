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
            for (var i in data) {
                const interest = document.createElement("button");
                interest.className = "subject";
                interest.id = "match-button";
                interest.innerHTML =
                document.getElementById("interest").appendChild(interest);
                console.log("here")
            }
            //matchMajor.innerHTML = response.data.major;
            return data;
        })
        .catch(function (error) {
            console.log(error);
        });

}