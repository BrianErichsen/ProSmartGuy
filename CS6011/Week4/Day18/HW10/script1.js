"use strict";
let ws;

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
    wsOpenPar.innerText = "WS Error!"
}

function main() {
    console.log("main()");
    ws = new WebSocket("ws://localhost:8080/");
    ws.onopen = handleWsOpen
    ws.onmessage = handleWsMessage;
    ws.onerror = handleWsError;

    wsCreatedPar.innerText = "WS has been created..."
}

window.onload = main;
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