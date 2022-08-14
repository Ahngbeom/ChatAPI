// Chat
let socket = null;
let stompClient = null;

const chatModal = new bootstrap.Modal("#chatModal");
const chatModalElement = document.getElementById("chatModal");
const sendMessageBtn = document.getElementById("chat-send-message-btn");

