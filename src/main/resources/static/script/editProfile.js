//post request to the profile edit page
const interest = document.getElementsByClassName('slider');

const interestUpdate = () => {
    fetch('/yoki', {
        method: 'post',
        body: JSON.stringify(postParameters),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then(response => {
            text: interest.value
        })
        .catch(function (error) {
            console.log(error);
        });
}