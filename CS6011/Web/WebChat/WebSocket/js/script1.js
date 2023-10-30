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
