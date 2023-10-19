const xValue = document.getElementById("xValue");
const yValue = document.getElementById("yValue");
const resultInput = document.getElementById("result");

xValue.addEventListener("keypress", handleKeyPress);
yValue.addEventListener("keypress", handleKeyPress);

function handleKeyPress(event) {
    if (event.code =="Enter") {
        let x = parseFloat(xValue.value);
        let y = parseFloat(yValue.value);
        // if (NaN(x)) {
        //     alert("X should be a number");
        //     xValue.value = "Enter a number";
        //     xValue.ariaSelected();
        //     return;
        // }
        // if (NaN(y)) {
        //     alert("Y should be a number");
        //     xValue.value = "Enter a number";
        //     xValue.ariaSelected();
        //     return;
        // }

        let xhr = new XMLHttpRequest();
        xhr.open ("GET", "http://localhost:8080/calculate?x=" + x + "&y" + y);
        xhr.onerror = handleError;
        xhr.onload = handleAjax;
        xhr.send();

        resultInput.value = (x + y);
        console.log("x value", x);
    }
}
function handleAjax() {
    resultInput.value = this.responseText;
}
function handleError () {

}
