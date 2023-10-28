//Initialyzes websocket server connection
const socket = new WebSocket('ws://' + location.host);

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