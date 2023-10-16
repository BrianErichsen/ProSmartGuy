function mainFunc() {
document.writeln("Hello World");
document.onplaying("Hello World");
//the difference between the 2 is that writeln writes
// directly at the HTML page and console writes to the
//console

let myArray = ["Hello", false, 41, 3.15, {key : "value"}];
for (let i of myArray) {
    console.log("elements: ", i);
}
myArray[0] = "Oi em portugues";
myArray.push("69420");

for (let i of myArray) {
    console.log("elements: ", i);
}
console.log(myArray);
//c++ syntax
function add(a, b) {
    return a + b;
}
//Literal syntax
let myFunction = function(a, b) {
    return a + b;
}
console.log(add(3, 5));
console.log(myFunction(6, 9));
console.log(myFunction("hi", 5)); //here the output concatenates having hi5 as 
//result;

}//end of main braket