//Created by Brian Erichsen Fagundes, on 10-18-2023

//gets a reference to the canvas elements and its 2d rendering context
let canvas=document.getElementById("canvasDrawing");
let context = canvas.getContext("2d");

//Defines the canvas height and width
let cWidth = canvas.width;
let cHeight = canvas.height;

//Creates a new object for the player's bee
let queenBee=new Image();
queenBee.src = "images/bee.png";

// Sets the initial position for player's bee
queenBee.xPos = 10;
queenBee.yPos = 10;

//Array to store the chasing bees
let bees = [];

//Function that create a chasing bee
function createChasingBees() {
    let bee = new Image();
    bee.src = "images/bee.png";

    //Spawns bees from random spots
    bee.xPos = Math.random() * cWidth;
    bee.yPos = Math.random() * cHeight;
    //Adds new bee into bees array
    bees.push(bee);
}
//Creates 10 chasing bees
for (let i = 0; i < 10; i++) {
    createChasingBees();
}
//Function to animate the chasing bees
function animateChasingBees() {
    for (let bee of bees) {
        //Calculates the direction from the bee to the player's bee
        /*Using pythagorean theorem to calculate shortest distance */
        let dx = queenBee.xPos - bee.xPos;//represents horizontal distance
        let dy = queenBee.yPos - bee.yPos;//represents the vertical distance
        let distance = Math.sqrt(dx * dx + dy * dy);

        //Sets the speed for the chasing bees
        let speed = 2;
        //Updates the speed for the chasing bees
        /*dx/dy over distance represents unit vector pointing from the chasing
        bee to the player's bee and speed asssures that it moves at const speed */
        bee.xPos += (dx / distance) * speed;
        bee.yPos += (dy / distance) * speed;

        //Draws the chasing bees at its updated position
        context.drawImage(bee, bee.xPos, bee.yPos);
    }
}
function animateImg() {
    //Clears the canvas
    eraseOld();
    //Draws the player's bees
    context.drawImage(queenBee, queenBee.xPos, queenBee.yPos);
    //Updates and draws the chasing bees
    animateChasingBees();
    //Resquests the next animation frame
    window.requestAnimationFrame(animateImg);
}
function mainDrawing(){
    //Starts the animation loop
    window.requestAnimationFrame(animateImg);
}
function handleClick(e) {//e represents mouse event
//updates the xpos and ypos of the image based on the mouse's position
//returns the x and y coordinates of the top left corner of the canvas
//we subtract because we want the mouse's position relative to the canvas
queenBee.xPos = e.clientX - canvas.getBoundingClientRect().left;
queenBee.yPos = e.clientY - canvas.getBoundingClientRect().top;
}
function eraseOld(){
    //Clears the canvas
    context.clearRect (0,0,cWidth,cHeight);
}
// Starts the main drawing loop when the page loads
window.onload = mainDrawing;
//Tracks the mouse's position within the canvas
document.onmousemove = handleClick;
