function onLoginPressed() {
    let email = document.getElementById("login-email").value
    let password = document.getElementById("login-password").value
    requestLogin(email, encrypt(password))
}

function encrypt(text) {
    const key = 'gudetama';
    const keyutf = CryptoJS.enc.Utf8.parse(key);
    const iv = CryptoJS.enc.Base64.parse(key);

    const enc = CryptoJS.AES.encrypt(text, keyutf, { iv: iv });
    const encStr = enc.toString();
    return encStr;
}
//
// function decrypt(text) {
//     const key = 'gudetama';
//     const keyutf = CryptoJS.enc.Utf8.parse(key);
//     const iv = CryptoJS.enc.Base64.parse(key);
//
//     const dec = CryptoJS.AES.decrypt(
//         { ciphertext: CryptoJS.enc.Base64.parse(text) },
//         keyutf,
//         {
//             iv: iv
//         });
//     const decStr = CryptoJS.enc.Utf8.stringify(dec)
//     return decStr;
// }

// function printout(t) {
//     var arr = t.split("\n")
//     // console.log(arr)
//     var str = "";
//     for (i = 0; i < arr.length; i++) {
//         // console.log(arr[i])
//         console.log(encrypt(arr[i]))
//         str += encrypt(arr[i]) + "\n"
//         //console.log(CryptoJS.AES.encrypt(arr[i], "gudetama").toString())
//     }
//     console.log(str)
// }
//
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
                let loginBtn = document.getElementById("login-button")
                loginBtn.getElementsByTagName("p")[0].style.opacity = 0;
                setTimeout(function(){
                    loginBtn.style.width = "60px";
                    setTimeout(function() {
                        window.location.href = "/main";
                    }, 1500);
                }, 500);
            } else {
                document.getElementById("login-status").style.opacity = 100;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}