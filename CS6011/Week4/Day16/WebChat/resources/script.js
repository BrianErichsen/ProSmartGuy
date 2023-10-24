const socket = new WebSocket('ws://' + location.host);

//Tracks whether the use is in a room
let inRoom = false;

//Display messages in the chat window
function displayMessage(message) {
    const chatMessages = document.getElementById('chat-messages');
    const messageDiv = document.createElement('div');
    messageDiv.textContent = `${message.user} ${message.message}`;
    chatMessages.appendChild(messageDiv);
} //'`
//Sends message to server
function sendMessage() {
    
    if (inRoom) {
    const messageInput = document.getElementById('message-input');
    const message = messageInput.value;

    socket.send(`message ${message}`);
    messageInput.value = '';
    } else {
        alert("You are not in a room. Please join a room before sending messages.");
    }
}
//Function to join a room
function joinRoom() {
    const roomNameInput = document.getElementById('room-name');
    const roomName = roomNameInput.value;

    //Gets the username from the input
    const usernameInput = document.getElementById('username');
    const username = usernameInput.value;

    //if value is different than 0; then it means that it is true
    if (roomName && username) {
    socket.send(`join ${username} ${roomName}`);
    //Sets user to be in a room to be true
    inRoom = true;
    //Update the room name display in the span id
    document.getElementById('room-name-display').textContent = `room: ${roomName}`;
    document.getElementById('user-name-display').textContent = `user: ${username}`;
    } else {
        alert("Get in it a room now!");
    }
}
//Function to leave current room
function leaveRoom() {
    if (inRoom) {
        inRoom = false;
        document.getElementById('room-name-display').textContent = 'Room: None';
        socket.send('leave');
    } else {
        alert("You are not in a room.");
    }
}

//Handles incoming webSocket messages
socket.onmessage = (event) => {
    const message = JSON.parse(event.data);
    if (message.type === 'message') {
        displayMessage(message);
    } else if (message.type === 'join') {
        //
    } else if (message.type === 'leave') {
        //
    }
}
//Displays messages in the chat window
const sendButton = document.getElementById('send-button');
sendButton.addEventListener('click', sendMessage);

const joinButton = document.getElementById('join-button');
joinButton.addEventListener('click', joinRoom);

const leaveButton = document.getElementById('leave-button');
leaveButton.addEventListener('click', leaveRoom);



