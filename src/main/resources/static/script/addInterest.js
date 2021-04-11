//make it create a new interest
function addInterest() {
    var div = document.createElement("div");
    div.style.width = "55vw";
    div.style.height = "5vw";
    div.style.backgroundColor = "#3F5FBF";
    div.style.marginLeft = "6vw";
    div.style.marginTop = "1vw";
    div.innerHTML = "";

    var name = document.createElement("h3");
    name.innerHTML = "Interest";
    var input = document.createElement("input");
    input.className = "slider";

    document.div.appendChild(name);
    document.div.appendChild(input);
    document.getElementById("interest").appendChild(div);
}

// Needs post request!!! to update the interests!!!