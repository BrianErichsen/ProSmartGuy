// Get references to the table, paragraph, and table rows
const table = document.getElementById("multiplicationTable"); // Get a reference to the HTML table with the ID "multiplicationTable"
const animationToggle = document.getElementById("animationToggle"); // Get a reference to the HTML element with the ID "animationToggle"
const tableRows = []; // Create an array to store references to table rows

// Generate the multiplication table and add mouse and click event listeners
for (let i = 1; i <= 10; i++) { // Iterate through rows (1 to 10)
    const row = document.createElement("tr"); // Create a new table row element
    tableRows.push(row); // Store the reference to the row in the array
    for (let j = 1; j <= 10; j++) { // Iterate through columns (1 to 10)
        const cell = document.createElement("td"); // Create a new table cell (data cell) element
        cell.textContent = i * j; // Set the cell's text content to the product of row and column values
        cell.addEventListener("mouseenter", function () { // Add a mouseenter event listener to the cell
            cell.classList.add("highlighted"); // Add a "highlighted" class to the cell when the mouse enters
        });
        cell.addEventListener("mouseleave", function () { // Add a mouseleave event listener to the cell
            cell.classList.remove("highlighted"); // Remove the "highlighted" class from the cell when the mouse leaves
        });
        cell.addEventListener("click", function () { // Add a click event listener to the cell
            for (const row of tableRows) {
                for (const cell of row.children) {
                    cell.classList.remove("clicked"); // Remove the "clicked" class from all cells in the table
                }
            }
            cell.classList.add("clicked"); // Add a "clicked" class to the clicked cell
        });
        row.appendChild(cell); // Add the cell to the current row
    }
    table.appendChild(row); // Add the row to the table
}

// Background color animation
let isAnimating = false; // Initialize a flag to track animation state
animationToggle.addEventListener("click", function () { // Add a click event listener to the "animationToggle" element
    if (isAnimating) {
        clearInterval(animationInterval); // Stop the animation interval if it's currently running
    } else {
        const animationInterval = setInterval(function () { // Start a new animation interval
            document.body.style.backgroundColor = getRandomColor(); // Change the background color with a random color
        }, 500);
    }
    isAnimating = !isAnimating; // Toggle the animation state flag
});

// Function to generate a random color
function getRandomColor() {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)]; // Generate a random hexadecimal color
    }
    return color; // Return the random color
}