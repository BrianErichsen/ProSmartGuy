"use strict";

// Declare global variables
let ws; // WebSocket connection

let userName; // User's name
let roomName; // Room's name

// Event listener for the "Connect" button
document.getElementById("connect-btn").addEventListener("click", function() {
    // Get values from input fields
    userName = document.getElementById("username").value;
    roomName = document.getElementById("roomname").value;

    // Check if the room name is in lowercase and without spaces
    if (!/^[a-z]+$/.test(roomName)) {
        document.getElementById("error-message").innerText = "Invalid input for room name. Room must be in lowercase and without space!";
        return;
    }

    // Create a new WebSocket connection to the server
    ws = new WebSocket("ws://localhost:8080");

    // Event handler for the WebSocket connection open event
    ws.onopen = function() {
        // Prepare a JSON message for joining the room
        let messageJSON = {
            type: "join",
            user: userName,
            room: roomName,
        }

        // Send the join message to the server
        ws.send(JSON.stringify(messageJSON));

        // Update the room information displayed
        document.getElementById("room-info").innerText = `Room: ${roomName}, User: ${userName}`;
    };

    // Event handler for incoming messages from the server
    ws.onmessage = function(event) {
        // Parse the JSON data from the server
        const data = JSON.parse(event.data);

        // Get HTML elements for displaying messages and users
        const messagesDiv = document.getElementById("messages");
        const usersList = document.getElementById("users-list");

        // Handle different types of messages from the server
        if (data.type === "message") {
            // Display regular messages
            messagesDiv.innerHTML += `<p><strong>${data.user}:</strong> ${data.message}</p>`;
        } else if (data.type === "join") {
            // Display messages for user joining
            messagesDiv.innerHTML += `<p><em>${data.user} has joined the room</em></p>`;
            // Update the user list
            usersList.innerHTML += `<li id="user-${data.user}">${data.user}</li>`;
        } else if (data.type === "leave") {
            // Display messages for user leaving
            messagesDiv.innerHTML += `<p><em>${data.user} has left the room</em></p>`;
            // Remove the user from the user list
            const userLi = document.getElementById(`user-${data.user}`);
            if (userLi) userLi.remove();
        }
    };

    // Event handler for WebSocket errors
    ws.onerror = function(error) {
        // Log WebSocket errors and display an error message to the user
        console.error("WebSocket Error: ${error}");
        document.getElementById("error-message").innerText = "An error occurred with the WebSocket connection. Please try again later."
    };
});

// Event listener for the "Send" button
document.getElementById("send-btn").addEventListener("click", function() {
    // Get the message from the input field
    const message = document.getElementById("message-input").value;

    // Prepare a JSON message for sending regular messages
    let messageJSON = {
        type: "message",
        user: userName,
        room: roomName,
        message: message,
    }

    // Send the message to the server
    ws.send(JSON.stringify(messageJSON));
});

// Event listener for the "Leave" button
document.getElementById("leave-btn").addEventListener("click", function() {
    // Prepare a JSON message for leaving the room
    let messageJSON = {
        type: "leave",
        user: userName,
        room: roomName,
    }

    // Send the leave message to the server
    ws.send(JSON.stringify(messageJSON));

    // Clear the room information displayed
    document.getElementById("room-info").innerText = "";
});