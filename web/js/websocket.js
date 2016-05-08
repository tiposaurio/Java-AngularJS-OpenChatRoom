/**
 * Created by Girtoone on 22/04/2016.
 */
window.onload = init;
window.onunload = removeUser;
var socket = new WebSocket("ws://localhost:8080/ChatRoom/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var user = JSON.parse(event.data);
    if (user.action === "add") {
        var content = document.getElementById("content");
            //timeout to load DOM
            setTimeout(function() {
                printUserElement(user);
            }, 10);


    }
    if (user.action === "remove") {
        document.getElementById(user.id).remove();
    }
    if (user.action === "addMessage"){
        printMessage(user.author, user.message);
    }
}
function emptyFunction(){

}
function addUser(name, type, description) {
    var UserAction = {
        action: "add",
        name: name,
    };
    socket.send(JSON.stringify(UserAction));
}


function removeUser() {
    var UserAction = {
        action: "removeUser"
    };
    socket.send(JSON.stringify(UserAction));
}

function addMessage(message) {
    var UserAction = {
        action: "addMessage",
        message: message,
    };
    socket.send(JSON.stringify(UserAction));
}


function printUserElement(user) {
    var content = document.getElementById("content");

    var userDiv = document.createElement("div");
    userDiv.setAttribute("id", user.id);
    content.appendChild(userDiv);

    var userName = document.createElement("span");
    userName.setAttribute("class", "users");
    userName.innerHTML = user.name;
    userDiv.appendChild(userName);

}

function printMessage(author, message) {
    var content = document.getElementById("all_messages");

    var messageDiv = document.createElement("div");
    messageDiv.setAttribute("class", "message");
    content.appendChild(messageDiv);

    var messageTime = document.createElement("span");
    messageTime.setAttribute("class", "time");
    var d = new Date();
    messageTime.innerHTML = "[" + d.getHours().toString() + ":" + d.getMinutes().toString() + "]";
    messageDiv.appendChild(messageTime);

    var messageAuthor = document.createElement("span");
    messageAuthor.setAttribute("class", "author");
    messageAuthor.innerHTML = author + ":";
    messageDiv.appendChild(messageAuthor);

    var messageText = document.createElement("span");
    messageText.setAttribute("class", "text");
    messageText.innerHTML = message;
    messageDiv.appendChild(messageText);
}


function init() {
}

function formMessageSubmit(){
    var form = document.getElementById("messageForm");
    var message = form.elements["text"].value;
    if (message != ""){
        form.elements["text"].value="";
        addMessage(message);
    }

}

function formAddUserSubmit(name) {
    addUser(name);

}
