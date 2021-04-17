function onLoginPressed() {
    let email = document.getElementById("login-email").value
    let password = document.getElementById("login-password").value
    let encrypt = CryptoJS.AES
    encrypt.salt = 1
    encrypt.salt.iv = 5
    let output = encrypt.encrypt(password, "gudetama")
    console.log(output.toString(), output.salt, output.iv)
    requestLogin(email, CryptoJS.AES.encrypt(password, "gudetama").toString())
}

// function printout(t) {
//     var arr = t.split("\n")
//     var str = "";
//     for (i = 0; i < arr.length; i++) {
//         // console.log(i)
//         str += CryptoJS.AES.encrypt(arr[i], "gudetama").toString() + "\n"
//         //console.log(CryptoJS.AES.encrypt(arr[i], "gudetama").toString())
//     }
//     console.log(str)
// }

// function convert(t) {
//     var arr = "";
//     t.then(t => printout(t))
//     // console.log(arr)
//     // for (i = 0; i < arr.length; i++) {
//     //     console.log(i)
//     //     console.log(CryptoJS.AES.encrypt(arr[i], "gudetama").toString())
//     // }
//     // console.log(CryptoJS.AES.encrypt(t, "gudetama").toString())
// }

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
            } else {
                document.getElementById("login-status").style.opacity = 100;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}