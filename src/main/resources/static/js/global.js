// Chat
let socket = null;
let stompClient = null;

const mainContainer = document.getElementById("mainContainer");
const navContainer = document.getElementById("navContainer");

if (document.querySelector("#chatModal")) {
    const chatModal = new bootstrap.Modal("#chatModal");
    const chatModalElement = document.getElementById("chatModal");
    const sendMessageBtn = document.getElementById("chat-send-message-btn");
}


