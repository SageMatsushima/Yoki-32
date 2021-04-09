const matchName = document.getElementById('match-name');
const matchGrade = document.getElementById('match-grade');
const matchMajor = document.getElementById('match-major');
const matchInterests = document.getElementById("top_interests_list");

fetch( '/yoki', {
    method: 'post',
    body: JSON.stringify(postParameters),
    headers: {
        'Content-type': 'application/json; charset=UTF-8',
    },
})

    .then((response) =>
        response.json())
    .then((data) => {
        for (i=0; i<data.message.length; i++) {
            let msg = data.message[i];
            suggestionList.innerHTML += "<li tabindex=\"0\">" + msg + "<\li>";
        }
        let listItems = document.querySelectorAll("li");
        for (let i = 0; i < listItems.length; i++) {
            listItems[i].addEventListener("click", () => {
                let msg = data.message[i];
                input.value = msg;
                loading.style.display = "";
            });
        }
    });
