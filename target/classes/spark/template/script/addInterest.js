//make it create a new interest
function addInterest() {
    const div = document.createElement("div");
    div.style.width = "55vw";
    div.style.height = "5vw";
    div.style.backgroundColor = "#3F5FBF";
    div.style.marginLeft = "6vw";
    div.style.marginTop = "1vw";

    const name = document.createElement("h3");
    name.innerHTML = "Interest";
    const input = document.createElement("input");
    input.className = "slider";

    document.div.appendChild(name);
    document.div.appendChild(input);
    document.getElementById("interest").appendChild(div);

    //post request to the profile edit page
    // const interest = document.getElementsByClassName('slider');
    //
    // fetch('/yoki', {
    //     method: 'post',
    //     body: JSON.stringify(postParameters),
    //     headers: {
    //         'Content-type': 'application/json; charset=UTF-8',
    //     },
    // })
    //     .then(response => {
    //         //send new info about interest back to front end
    //     //    info should send slider value to front end
    //     })
    //     .catch(function (error) {
    //         console.log(error);
    //     });

}

// Needs post request!!! to update the interests!!! ^^