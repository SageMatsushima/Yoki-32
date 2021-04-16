function onLoginPressed() {
    let email = document.getElementById("login-email").value
    let password = document.getElementById("login-password").value
    console.log(email)
    console.log(password)
    requestLogin(email, password)
}

function requestLogin(email, password) {
    const postPara = {
        email: email,
        password: password
    };
    fetch('http://localhost:4567/login', {
        method: 'post',
        body: JSON.stringify(postPara),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
        .then((response) =>
            response.json())
        .then((data) => {
            console.log(data);
            if (data.authenticated == "true") {
                window.location.href = "/main";
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}