const matchName = document.getElementById('match-name');
const matchGrade = document.getElementById('match-grade');
const matchMajor = document.getElementById('match-major');
const matchInterests = document.getElementById("top_interests_list");

const getNextMatch = () => {
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            console.log(response.data.name)
            matchName.innerHTML = response.data.name;
            matchGrade.innerHTML = "Class of " + response.data.grade;
            matchMajor.innerHTML = response.data.major;
        })
        .catch(function (error) {
            console.log(error);
        });
}