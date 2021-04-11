//make it create new card
// <div id = "potential_card">
//     <div id = "card_content">
//         <div id = "left-content">
//             <div id = "name_info">
//                 <h1 id="match-name">Kendall</h1>
//                 <h3 id="match-grade">Class of 2023</h3>
//                 <h3 id="match-major">Cognitive Science</h3>
//             </div>
//             <div id = "top_interests">
//                 <h3>Interests</h3>
//                 <ul id = "top_interests_list">
//                     <li>
//                         modeling
//                     </li>
//                     <li>
//                         being rich
//                     </li>
//                     <li>
//                         Asap Rocky
//                     </li>
//                 </ul>
//             </div>
//         </div>
//         <div id = "right-content">
//             <img id = "match_image" src="./images/kendall.jpg">
//         </div>
//     </div>
// </div>

function createCard() {
    const div = document.createElement("div");
    const divContent = document.createElement("div");
    const info = document.createElement("div");
    const divLeft = document.createElement("div");
    const interests = document.createElement("div");
    const divRight = document.createElement("div");

    div.id = "potential_card";
    divContent.id = "card_content";
    info.id = "name_info";
    divLeft.id = "left-content";
    interests.id = "top_interests";
    divRight.id = "right-content";

    document.getElementById("interest").appendChild(div);
}

function matchInfo() {
    // post request to the profile edit page

    fetch('/match', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            //send new info about interest back to front end
        //    info should send slider value to front end
        })
        .catch(function (error) {
            console.log(error);
        });

}

// Needs post request!!! to update the interests!!! ^^