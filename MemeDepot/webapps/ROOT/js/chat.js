"use strict";

let users = ["abc", "def", "ghi", "ijk", "lmn", "opq", "axy"];

let currentUser;
let currentUserMessages = {
//    "def" : [
//        ['abc', "Hey how are you def?"],
//        ['def', "I am fine what about you?"],
//        ['abc', "I am doing good"], 
//        ['abc', "Just watching movies all day."]
//    ],
//    "ghi" : [
//        ['abc', "Hey how are you ghi?"],
//        ['ghi', "I am fine what about you?"],
//        ['abc', "I am doing good"], 
//        ['abc', "Just watching movies all day."]
//    ],
//    "axy" : [
//        ['abc', "Hey how are you axy?"],
//        ['axy', "I am fine what about you?"],
//        ['abc', "I am doing good"], 
//        ['abc', "Just watching movies all day."]
//    ]
};


$(document).ready(setup);


function showMessages() {
    let html = ''; 
    for (let user in currentUserMessages) {
        let classOfMsg = (currentUserMessages[user][0][0] == currentUser) ? 'currentUser' : 'otherUser';
        let msg = currentUserMessages[user][0][1];
        if (msg.length > 48) {
            msg = msg.substring(0, 25);
            msg += '...';
        }
        html += `<div id = "${user}" class = "msg"> <p class = "userOfMsg">${user}</p><p class = "${classOfMsg}">${msg}<p></div>`;
    }
    return html;
}

function setup() {
    console.log(window.localStorage.getItem("currentUser"));
    let submitMessage = $('#submitMessage');
    currentUser = localStorage.getItem('currentUser');
    submitMessage.click(()=>{
        let to = $('#to').val();
        let message = $("#message").val();
        if (to != "") {
            if (message != "") {
                console.log(to+':'+message);
                let formData = new FormData();

                formData.append("to", to);
                formData.append("message", message);
                $.ajax({
                    type: "POST",
                    url: "/srv/addmessage",
                    data: formData,
                    contentType: false, 
                    processData: false,
                    success: (data) => {
                        if (data == "False") {
                            $("#statusdiv").html("Error");
                        } else {
                            $("#statusdiv").html("Successfully sent the message");
                        }
                    },
                    error: (xhr,status,err) => {
                        $("#statusdiv").html("Something went wrong: status="+status+" err="+err+" resp=");
                    }

                });
            }
        }
    });
    $.get("/srv/getmessages", function(response) {   
        let lines = response.split("\n");
        let addNewUser = false; 
        let addMessages = false;
        
        for (let line of lines) {
            if (line[0] === '#') {
                addNewUser = true;
                addNewMessages = false;
                currentUser = ""; 
                continue;
            }
            if (addNewUser) {
                currentUserMessages[line] = [];
                currentUser = line;
                addNewUser = false; 
                addMessages = true;
                continue;
            }
            if (addNewMessages) {
                let msgData = line.split(":");
                currentUserMessages[currentUser].push([msgData[0], msgData[1]]);
            }
        }
        localStorage.setItem("currentUserMessages", currentUserMessages);
    });
    let userSearch = $('.usersearch');
    let messagesList = $('.messageList');
    messagesList.html(showMessages(messagesList));
    $('.messageList').on('click', `.msg`, (e) => {
        let userOfMsg = e.target.id;
        //console.log(e.target);
        //console.log(userOfMsg);
        if (userOfMsg == "") {
            userOfMsg = $(e.target).parent('.msg').attr("id");
        } 
        localStorage.setItem('user', userOfMsg);
        window.document.location = 'messages.html';
    });
    
    userSearch.on('input', (e)=> {
        let searchValue = e.target.value;
        let matches = users.filter(user => {
            const regex = new RegExp(`^${searchValue}`, 'gi');
            return user.match(regex);
        });
        if (searchValue == "") {
            matches = [];
            $('#userSearchList').html('');
        }
        show(matches);
    });
}

function show(matches) {
    if (matches.length > 0) {
        let html = matches.map(match => `
            <div class="card card-body mb-1 suggestions">
            <h4>${match}</h4>
            </div>
        `).join('');
        $('#userSearchList').html(html);
    }
    
    $('.suggestions').click(()=>{
        let str = ('#userSearchList').children().html();
        for (let i = 8; i < str.length; i++) {
            console.log(str[i]);
        }
    });
}
