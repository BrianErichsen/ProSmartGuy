const socket = new WebSocket('ws://' + location.host);

//Display messages in the chat window
function displayMessage(message) {
    const chatMessages = document.getElementById('chat-messages');
    const messageDiv = document.createElement('div');
    messageDiv.textContent = '${message.user} ${message.message}';
    chatMessages.appendChild(messageDiv);
}
//Sends message to server
function sendMessage() {
    const messageInput = document.getElementById('message-input');
    const message = messageInput.value;

    socket.send('message ${message}');
    messageInput.value = '';
}
//Function to join a room
function JoinRoom() {
    const roomName = document.getElementById('room-name').value;
    socket.send('join <username> ${roomName}');
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
const sendButton = document.getElementById('send-button');
sendButton.addEventListener('click', sendMessage);

const joinButton = document.getElementById('join-button');
joinButton.addEventListener('click', () => {
    const usernameInput = docume.getElementById('username');
    const RoomNameInput = document.getElementById('roomname');
    const username = usernameInput.value;
    const roomName = roomNameInput.value;

    socket.send('join ${username} ${roomName}');

    document.getElementById('user-name-display').textContent = 'User: ${username}';
    document.getElementById('room-name-display').textContent = 'Room: ${roomName}';
});

const leaveButton = document.getElementById('leave-button');
leaveButton.addEventListener('click', leaveRoom);



