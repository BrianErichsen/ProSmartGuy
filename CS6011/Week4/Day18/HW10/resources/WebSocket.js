// // Initialize an array to store the list of users in the room
// const usersInRoom = [];
// //JavaScript code should only execute after the HTML document has been fully loaded and is ready for manipulation
// // JavaScript code doesn't run until the HTML document is fully loaded.
// /* "DOMContentLoaded" is the name of the event that the function is listening for.
// It occurs when the HTML document has been fully loaded, and the DOM is ready for
// manipulation. */
// document.addEventListener("DOMContentLoaded", function () {
//     //when the page is fully loaded, it creates a WebSocket connection to a server.
//     const socket = new WebSocket("ws://localhost:8080");
// /*Here, an event listener is added to the WebSocket's open event. When the WebSocket
// connection is established, it gets the username and roomname from the corresponding
// input elements.*/
//     socket.addEventListener("open", function () {
//         // Handle WebSocket connection opened
//         const username = document.getElementById("usnerNameBox").value;
//         let roomname = document.getElementById("roomNameBox").value;
//         /*This if statement checks if username has a value and calls a function
//         isRoomNameValid which is supposed to validate the roomname.*/
//             // Join the room when WebSocket is open
//             const joinMessage = `join ${username}: ${roomname}`;
//             socket.send(joinMessage);
        
//     });

//     socket.addEventListener("message", function (event) {
//         // Handle incoming WebSocket messages
//         const messageData = JSON.parse(event.data);
//         if (messageData.type === "message") {
//             displayMessage(messageData.user, messageData.room, messageData.message);
//         } else if (messageData.type === "join") {
//             userJoinedRoom(messageData.user, messageData.room);
//         } else if (messageData.type === "leave") {
//             userLeftRoom(messageData.user, messageData.room);
//         }
//     });
// /*This function is called when a user joins a room. It adds the username to the
// usersInRoom array if it's not already in there, and then updates the UI to display
// the list of users in the room. */
//     function userJoinedRoom(username, roomname) {
//            // Check if roomname contains uppercase letters or spaces
//             if (/[A-Z\s]/.test(roomname)) {
//             alert("Room name should be in lowercase and without spaces.");
//             return; // Exit the function
//         }
//         //displayMessage(username, roomname, message);
//         // Add the username to the list of users in the room
//         if (!usersInRoom.includes(username)) {
//             usersInRoom.push(username);
//             // Update the UI to display the list of users in the room
//             updateUsersInRoomUI();
//         }

//         const mainChatDiv = document.querySelector(".mainChatDiv");
//         // Assuming you have a message you want to add to the chat
//         const message = username + " joined the chat room!";

//         // Create a new paragraph element
//         const messageElement = document.createElement("p");

//         // Set the text content of the paragraph
//         messageElement.textContent = message;

//         // Append the paragraph to the mainChatDiv
//         mainChatDiv.appendChild(messageElement);
//     }


//     // Function to handle when a user leaves a room
//     function userLeftRoom(username, roomname) {
//         // Remove the username from the list of users in the room
//         const index = usersInRoom.indexOf(username);
//         if (index !== -1) {
//             usersInRoom.splice(index, 1);
//             // Update the UI to display the updated list of users in the room
//             updateUsersInRoomUI();
//         }

//     }

//     // Function to update the UI with the list of users in the room
//     function updateUsersInRoomUI() {
//         const peopleInRoomDiv = document.querySelector(".peopleInRoomDiv");
//         peopleInRoomDiv.innerHTML = "<h2>People in Chat</h2>";
// /*This line starts a forEach loop on the usersInRoom array, which contains the list
// of users in a chat room. */
//         usersInRoom.forEach((user) => {
//             /*For each user in the usersInRoom array, create a new HTML
//             paragraph (<p>) element. This element will be used to display
//             the username of the user in the chat room.*/
//             const userElement = document.createElement("p");
//             //Sets the text content of the userElement to the value of the user variable
//             userElement.textContent = user;
//             peopleInRoomDiv.appendChild(userElement);
//         });
//     }

//     // Function to send a chat message to the server
//     function sendMessage(message) {
//         const username = document.getElementById("usnerNameBox").value;
//         const roomname = document.getElementById("roomNameBox").value;

//         if (username && roomname) {
//         // Check if roomname contains uppercase letters or spaces
//         if (/[A-Z\s]/.test(roomname)) {
//             alert("Room name should be in lowercase and without spaces.");
//             return; // Exit the function
//         }
//             if (socket.readyState === WebSocket.OPEN) {
//                 const messageText = `message ${message}`;
//                 socket.send(messageText);
//                 // Display the sent message in the mainChatDiv
//                 const chatMessages = document.getElementById('mainChatDiv');
//                 const messageDiv = document.createElement('div');
//                 //
//                 messageDiv.textContent = `${message.user} ${message.message}`;
//                 chatMessages.appendChild(messageDiv);
//                 //displayMessage(username, roomname, message);
//                 // Clear the input field after sending
//                 document.getElementById("messageBox").value = "";
//             } else {
//                 console.error("WebSocket connection is not open.");
//             }
//         } else {
//             console.error("Username and room name are required to send a message.");
//         }
//     }
    
//     // Event listener for pressing Enter in the message input field
//     document.getElementById("messageBox").addEventListener("keyup", function (event) {
//         if (event.keyCode === 13) {
//             const message = event.target.value;
//             sendMessage(message);
//         }
//     });

//     // Event listener for clicking the "sendButton"
//     document.getElementById("sendButton").addEventListener("click", function () {
//         const message = document.getElementById("messageBox").value;
//         sendMessage(message);
//     });

//     // Function to display a chat message in the mainChatDiv
//     function displayMessage(username, roomname, message) {
//         const mainChatDiv = document.querySelector("mainChatDiv");
//         const messageElement = document.createElement("p");
//         const messageText = `${username} ${message}`;
//         messageElement.textContent = messageText;
//         mainChatDiv.appendChild(messageElement);
//         //To ensure the user sees the latest message
//         mainChatDiv.scrollTop = mainChatDiv.scrollHeight;
//     }

//     socket.addEventListener("close", function () {
//     });

//     socket.addEventListener("error", function (event) {
//         console.error("WebSocket Error:", event);
//     });

//     const joinButton = document.getElementById('joinButton');
//     joinButton.addEventListener('click', joinRoom);

//     function joinRoom() {
//         const roomNameInput = document.getElementById('roomNameBox');
//         const roomName = roomNameInput.value;
    
//         //Gets the username from the input
//         const usernameInput = document.getElementById('usnerNameBox');
//         const username = usernameInput.value;


//         for (const char of roomName) {
//             if (char >= 'a' && char >= 'z') {
//                 alert("invalid room name.");;
//             }
//         }

//         //if value is different than 0; then it means that it is true
//         if (roomName && username) {
//         socket.send(`join ${username} ${roomName}`);
//         //Sets user to be in a room to be true
//         let inRoom = true;
//         //Update the room name display in the span id
//         document.getElementById('room-name-display').textContent = `room: ${roomName}`;
//         document.getElementById('user-name-display').textContent = `user: ${username}`;
//         } else {
//             alert("invalid");
//         }
//     }

//     // Event listener for clicking the "leaveRoomButton"
//     document.getElementById("leaveRoomButton").addEventListener("click", function () {
//         const username = document.getElementById("usnerNameBox").value;
//         const roomname = document.getElementById("roomNameBox").value;

//         if (username && isRoomNameValid) {
//             // Send a leave message to the server
//             const leaveMessage = `leave ${username} ${roomname}`;
//             socket.send(leaveMessage);
//             // Clear the chat
//             clearMainChatDiv();
//         } else {
//             console.error("Username and room name are required to leave the room.");
//         }
//     });

//     // Function to clear the mainChatDiv
//     function clearMainChatDiv() {
//         const mainChatDiv = document.querySelector(".mainChatDiv");
//         const peopleInRoomDiv = document.querySelector(".peopleInRoomDiv");
//         mainChatDiv.innerHTML = "<h2>Main Chat</h2>";
//         peopleInRoomDiv.innerHTML = "<h2>People in Chat<h2>";
//     }
    
// });
"use strict";
let ws;
// document.addEventListener("DOMContentLoaded", function() {
//     const socket = new WebSocket("ws://" + location.host);
// });

let wsCreatedPar = document.getElementById("wsCreatedPar");
let wsOpenPar = document.getElementById("wsOpenPar");

function handleWsOpen() {
    console.log("ws is now open");
    wsCreatedPar.innerText = "WS is now open!";
    wsOpenPar.innerText = "No message received yet...";
    ws.send("Hello");
}
function handleWsMessage ( e ) {
    console.log("got message from ws: " + e.data);
    wsOpenPar.innerText = "Got message: '" + e.data + "'"
}
function handleWsError() {
    // wsOpenPar.innerText = "WS Error!"
}

function main() {
    console.log("main()");
    ws = new WebSocket("ws://localhost:8090");
    ws.onopen = handleWsOpen
    ws.onmessage = handleWsMessage;
    ws.onerror = handleWsError;

    // wsCreatedPar.innerText = "WS has been created..."
}
window.onload = main;

// window.onload = main;
// //Initialyzes socket server connection
// ws = "ws://localhost:8080/"
// let socket = new WebSocket(ws);
// console.log("Web socket created");

// const jsonObject = {
//     type: 'message',
//     user: 'Brian',
//     room: 'dragon',
//     message: 'Hello, world!',
//     timestamp: new Date().toISOString()
// };
// const jsonString = JSON.stringify(jsonObject);
// const parsedObject = JSON.parse(jsonString);

// messages.appendChild(jsonString);
// messages.apprendChild(parsedObject);

// socket.addEventListener('open', (event) => {
//     console.log('Websocket connection is open.')
// });
// socket.addEventListener('message', (event) => {
//     const message = JSON.parse(event.data);
//     console.log('Received message:', message);
// });
// socket.addEventListener('close', (event) => {
//     if (event.wasClean) {
//         console.log('Websocket connection closed cleanly.');
//     } else {
//         console.error('Websocket connection closed unexpectedly.');
//     }
// })
// socket.onopen = function(event) {
//     console.log("WebSocket is now open");
// };
// socket.onmessage = (event) => {
//     const message = event.data;
//     console.log('Received message:', message);
// };
// socket.onerror = (event) => {
//     console.error('WebSocket error:', event);
// };
// socket.onclose = (event) => {
//     if (event.wasClean) {
//     console.log('WebSocket connection closed cleanly', event);
// } else {
//     console.error('Websocket connection closed unexpectedly')
// }
// };