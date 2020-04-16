"use strict";

let users = ["abc", "def", "ghi", "ijk", "lmn", "opq", "axy"];

let currentUser = "abc";
let currentUserMessages = {
    "def" : [
        ['abc', "Hey how are you def?"],
        ['def', "I am fine what about you?"],
        ['abc', "I am doing good"], 
        ['abc', "Just watching movies all day."]
    ],
    "ghi" : [
        ['abc', "Hey how are you ghi?"],
        ['ghi', "I am fine what about you?"],
        ['abc', "I am doing good"], 
        ['abc', "Just watching movies all day."]
    ],
    "axy" : [
        ['abc', "Hey how are you axy?"],
        ['axy', "I am fine what about you?"],
        ['abc', "I am doing good"], 
        ['abc', "Just watching movies all day."]
    ]
};


$(document).ready(setup);

function setup() {
    let messageList = $('.messageList');
    let currentUser = localStorage.getItem('user');
    let messages = currentUserMessages[currentUser];
    let html = '<p class = "messagesp">Messages</p>'; 
    for (let message of messages) {
        let classOfMsg = (message[0] == currentUser) ? 'currentUser' : 'otherUser';
        let msg = message[1];
        let background_color = (classOfMsg == "currentUser") ? "black" : "grey";
        html += `<div class = "msg" style = "background-color: ${background_color};"><p class = "messagesp"  style = "color : white;" class = "${classOfMsg}">${msg}<p></div>`;
    }
    messageList.html(html);
    
}