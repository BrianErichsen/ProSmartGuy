//Initialyzes socket server connection
let ws = "ws//localhost:8080/"
let socket = new WebSocket(ws);
console.log("Web socket created");

const jsonObject = {
    type: 'message',
    user: 'Brian',
    room: 'dragon',
    message: 'Hello, world!',
    timestamp: new Date().toISOString()
};
const jsonString = JSON.stringify(jsonObject);
const parsedObject = JSON.parse(jsonString);

messages.appendChild(jsonString);
messages.apprendChild(parsedObject);

socket.addEventListener('open', (event) => {
    console.log('Websocket connection is open.')
});
socket.addEventListener('message', (event) => {
    const message = JSON.parse(event.data);
    console.log('Received message:', message);
});
socket.addEventListener('close', (event) => {
    if (event.wasClean) {
        console.log('Websocket connection closed cleanly.');
    } else {
        console.error('Websocket connection closed unexpectedly.');
    }
})
socket.onopen = function(event) {
    console.log("WebSocket is now open");
};
socket.onmessage = (event) => {
    const message = event.data;
    console.log('Received message:', message);
};
socket.onerror = (event) => {
    console.error('WebSocket error:', event);
};
socket.onclose = (event) => {
    if (event.wasClean) {
    console.log('WebSocket connection closed cleanly', event);
} else {
    console.error('Websocket connection closed unexpectedly')
}
};