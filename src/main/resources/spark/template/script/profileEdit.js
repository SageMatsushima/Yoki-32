const name = document.getElementById('name');
const pronouns = document.getElementById('pronouns');
const major = document.getElementById('major');
const gradYear = document.getElementById("gradYear");
const email = document.getElementById('email');
const bio = document.getElementById('bioBox');

const save = () => {
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            text: name.value
            text: pronouns.value
            text: major.value
            text: gradYear.value
            text: email.value
            text: bio.value
        })
        .catch(function (error) {
            console.log(error);
        });
}