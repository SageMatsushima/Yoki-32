const name = document.getElementById('name');
const pronouns = document.getElementById('pronouns');
const major = document.getElementById('major');
const gradYear = document.getElementById("gradYear");
const email = document.getElementById('email');
const bio = document.getElementById('bioBox');

function save() {
    const postParameters = {
        //TODO: get the text inside the input box (hint: use input.value to get the value of the input field)
        name: name.value,
        pronouns: pronouns.value,
        major: major.value,
        gradYear: gradYear.value,
        email: email.value,
        bio: bio.value
    };
    fetch('http://localhost:4567/yoki', {
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