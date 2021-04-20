loadLightDark();

function loadLightDark() {
    if(localStorage['lightDark'] == "dark") {
        darkMode();
    } else {
        lightMode();
    }
}

function darkMode() {
    localStorage['lightDark'] = 'dark';
    document.documentElement.style.setProperty('--first', "#1d2d50");
    document.documentElement.style.setProperty('--second', "#1d2d50");
    document.documentElement.style.setProperty('--third', "#133b5c");
    document.documentElement.style.setProperty('--fourth', "#1e5f74");
    document.documentElement.style.setProperty('--fifth', "#fcdab7");
    document.documentElement.style.setProperty('--sixth', "#dbdbdb");
}

function lightMode() {
    localStorage['lightDark'] = 'light';
    document.documentElement.style.setProperty('--first', "white");
    document.documentElement.style.setProperty('--second', "#dbdbdb");
    document.documentElement.style.setProperty('--third', "#fff9ef");
    document.documentElement.style.setProperty('--fourth', "#ffe0a4");
    document.documentElement.style.setProperty('--fifth', "#FFB84F");
    document.documentElement.style.setProperty('--sixth', "#804C00");
}