//Created by Brian Erichsen Fagundes, on 10-18-2023

//gets a reference to the canvas elements and its 2d rendering context
let canvas=document.getElementById("canvasDrawing");
let context = canvas.getContext("2d");

//Defines the canvas height and width
let cWidth = canvas.width;
let cHeight = canvas.height;

//Creates a new image to be used in the background of the game
let backgroundImage = new Image();
backgroundImage.src = "images/background.jpeg";

let animationFrameId;
let gameIsRunning = true;

//Wait for the background image to load
backgroundImage.onload = function() {
    //Draws at 0, 0 to cover whole background
    context.drawImage(backgroundImage, 0, 0);
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

        //Introduces some randomness to the bee's movement
        //random angle between 0 and 2 * PI
        let randomAngle = Math.random() * Math.PI * 2;
        dx += Math.cos(randomAngle) * 20;
        dy += Math.sin(randomAngle) * 20;

        //Updates the speed for the chasing bees
        /*dx/dy over distance represents unit vector pointing from the chasing
        bee to the player's bee and speed asssures that it moves at const speed */
        bee.xPos += (dx / distance) * speed;
        bee.yPos += (dy / distance) * speed;

        // Ensure minimum separation between bees
        for (let otherBee of bees) {
            //returns true if both the data type and value are not equal
            if (otherBee !== bee) {
                let separation = 50;
                //Using c^2 = a^2 + b^2;; solving for c
                let distanceBetweenBees = Math.sqrt(
                (otherBee.xPos - bee.xPos) ** 2 + (otherBee.yPos - bee.yPos) ** 2
                );
                if (distanceBetweenBees < separation) {
                // Repel the bee from the other bee
                bee.xPos += (bee.xPos - otherBee.xPos) / distanceBetweenBees * separation;
                bee.yPos += (bee.yPos - otherBee.yPos) / distanceBetweenBees * separation;
                }
            }
        }
        //Draws the chasing bees at its updated position
        context.drawImage(bee, bee.xPos, bee.yPos);
    }
}
function animateImg() {
    //If gameIsRunnin is not true, then exits the animate loop
    if (!gameIsRunning) {
        return;
    }
    //Clears the canvas
    eraseOld();
    //Checks for collisions
    checkCollisions();
    //Draws the player's bees
    context.drawImage(queenBee, queenBee.xPos, queenBee.yPos);
    //Updates and draws the chasing bees
    animateChasingBees();
    animationFrameId = requestAnimationFrame(animateImg);
    //Resquests the next animation frame
    // window.requestAnimationFrame(animateImg);
}
//Function to check for collisions
    function checkCollisions() {
        for (let bee of bees) {
            let dx = queenBee.xPos - bee.xPos;
            let dy = queenBee.yPos - bee.yPos;
            let distance = Math.sqrt(dx * dx + dy * dy);

            let collisionThreshold = queenBee.width / 2 + bee.width / 2;

            if (distance < collisionThreshold) {
                stopGame();
                return;
            }
        }
    }
    function stopGame() {
        gameIsRunning = false;
        cancelAnimationFrame(animationFrameId);

        context.fillStyle = "black";
        context.font = "36px Arial";
        context.fillText("You lost the game", cWidth / 2 - 120, cHeight / 2);
    }
function mainDrawing(){
    //Starts the animation loop
    animationFrameId = requestAnimationFrame(animateImg);
    // window.requestAnimationFrame(animateImg);
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
    //Redraws background image
    context.drawImage(backgroundImage, 0, 0);
}
// Starts the main drawing loop when the page loads
window.onload = mainDrawing;
//Tracks the mouse's position within the canvas
document.onmousemove = handleClick;
};
