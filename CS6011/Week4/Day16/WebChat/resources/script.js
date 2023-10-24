//Initialyzes websocket server connection
const socket = new WebSocket('ws://' + location.host);

//Tracks whether the use is in a room
let inRoom = false;
//When using `${}` the expression (string) is being evaluated and result inserted
//into string
//Display messages in the chat window
function displayMessage(message) {
    //Gets the messages container element
    const chatMessages = document.getElementById('chat-messages');

    //Creates new message div and set it's content
    const messageDiv = document.createElement('div');
    messageDiv.textContent = `${message.user} ${message.message}`;
    //Appends message
    chatMessages.appendChild(messageDiv);
} //'`
//Sends message to server
function sendMessage() {
    //Checkes if the user is in a room
    if (inRoom) {
    const messageInput = document.getElementById('message-input');
    const message = messageInput.value;
    //Sends the message to the server
    socket.send(`message ${message}`);
    //Sets value to empty to populate new messages
    messageInput.value = '';
    } else {
        //Alert the user if not in a room
        alert("You are not in a room. Please join a room before sending messages.");
    }
}
//Function to join a room
function joinRoom() {
    //Gets the room name input and username input
    const roomNameInput = document.getElementById('room-name');
    const roomName = roomNameInput.value;

    //Gets the username from the input
    const usernameInput = document.getElementById('username');
    const username = usernameInput.value;

    //if value is different than 0; then it means that it is true
    if (roomName && username) {
        //Sends a join message to the server
    socket.send(`join ${username} ${roomName}`);
    //Sets user to be in a room to be true
    inRoom = true;
    //Update the room name display in the span id; in the HTML
    document.getElementById('room-name-display').textContent = `room: ${roomName}`;
    document.getElementById('user-name-display').textContent = `user: ${username}`;
    } else {
        //Alers the user to enter a room and username
        alert("Get in it a room now!");
    }
}
//Function to leave current room
function leaveRoom() {
    //Checks if user is in a room
    if (inRoom) {
        inRoom = false;
        //Clears the room name display in the HTML
        document.getElementById('room-name-display').textContent = 'Room: None';
        //Sends leave message to the server
        socket.send('leave');
    } else {
        //Alerts that user is not in a room
        alert("You are not in a room.");
    }
}

//Handles incoming webSocket messages
socket.onmessage = (event) => {
    const message = JSON.parse(event.data);
    //Displays the received messaged in the chat window
    if (message.type === 'message') {
        displayMessage(message);
    } else if (message.type === 'join') {
    //----------Still working------
    } else if (message.type === 'leave') {
    //Still working--------
    }
}
// socket.onmessage = function(event) {
//     var data = JSON.parse(event.data);
//         if (message.type === 'message') {
//         displayMessage('div class="message"> + ' + message + '</div>');
//         }
// };


//Adds the event listener to the buttons for sending, joining, and leaving
const sendButton = document.getElementById('send-button');
sendButton.addEventListener('click', sendMessage);

const joinButton = document.getElementById('join-button');
joinButton.addEventListener('click', joinRoom);

const leaveButton = document.getElementById('leave-button');
leaveButton.addEventListener('click', leaveRoom);



