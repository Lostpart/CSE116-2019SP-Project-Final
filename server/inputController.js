var keyStates = {
    "moveX": 0,
    "moveY": 0,
};

function setState(key, toSet){
        keyStates[key] = toSet;
}

function handleEvent(event, toSet){
    if(event.key === "w" || event.key === "ArrowUp"){
        setState("moveY", toSet);
    }else if(event.key === "a" || event.key === "ArrowLeft"){
        setState("moveX",  toSet);
    }else if(event.key === "s" || event.key === "ArrowDown"){
        setState("moveY", -1 * toSet);
    }else if(event.key === "d" || event.key === "ArrowRight"){
        setState("moveX", -1 *toSet);
    }
}

document.addEventListener("keydown", function (event) {
    handleEvent(event, 1);
});

document.addEventListener("keyup", function (event) {
    handleEvent(event, 0);
});
