//Initialyzes websocket server connection
const socket = new WebSocket('ws://' + location.host);

socket.onopen = (event) => {
    console.log('WebSocket connection opened:', event);
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
