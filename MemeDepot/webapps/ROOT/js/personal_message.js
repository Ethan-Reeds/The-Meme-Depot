"use strict";

function sendMessage(){
    let to = $("#username_to").val();
    let from = $("#username_from").val();
    let message = $("#message").val();
    
    let fdata = new FormData();

    fdata.append("to",to);
    fdata.append("from", from);
    fdata.append("message",message);

    $.ajax({
        type: "POST",
        url: "/srv/pm_txt",
        data: fdata,
        contentType: false, 
        processData: false,
        success: (data) => {
            $("#statusdiv").html("Response: "+data);
        },
        error: (xhr,status,err) => {
            $("#statusdiv").html("Something went wrong: status="+status+" err="+err);
        }

    });

}

"use strict";

function sendImage(){

    let to = $("#username_to").val();
    let from = $("#username_from").val();
    let message = $("#message").prop("files")[0];
    
    let fdata = new FormData();

    fdata.append("to",to);
    fdata.append("from", from);
    fdata.append("message",message);

    $.ajax({
        type: "POST",
        url: "/srv/pm_txt",
        data: fdata,
        contentType: false, 
        processData: false,
        success: (data) => {
            $("#statusdiv").html("Server said this: "+data);
        },
        error: (xhr,status,err) => {
            $("#statusdiv").html("Something went wrong: status="+status+" err="+err);
        }

    });

}

// somehow make the accounts show up in the unorganized list
