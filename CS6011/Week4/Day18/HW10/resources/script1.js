//Initialyzes websocket server connection
let ws = "ws//localhost:8080/"
let socket = new WebSocket(ws);
console.log("Web websocket created");

const jsonObject = {
    user: 'Brian',
    room: 'dragon',
    message: 'Hello, world!',
    timestamp: new Date().toISOString()
};
const jsonString = JSON.stringify(jsonObject);
const parsedObject = JSON.parse(jsonString);

websocket.addEventListener('open', (event) => {
    console.log('Websocket connection is open.')
});
websocket.addEventListener('message', (event) => {
    const message = JSON.parse(event.data);
    console.log('Received message:', message);
});
websocket.addEventListener('close', (event) => {
    if (event.wasClean) {
        console.log('Websocket connection closed cleanly.');
    } else {
        console.error('Websocket connection closed unexpectedly.');
    }
})
websocket.onopen = function(event) {
    console.log("WebSocket is now open");
};
websocket.onmessage = (event) => {
    const message = event.data;
    console.log('Received message:', message);
};
websocket.onerror = (event) => {
    console.error('WebSocket error:', event);
};
websocket.onclose = (event) => {
    if (event.wasClean) {
    console.log('WebSocket connection closed cleanly', event);
} else {
    console.error('Websocket connection closed unexpectedly')
}
};