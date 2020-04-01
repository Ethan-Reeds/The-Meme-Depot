"use strict";

function sendMessage(){
    let to = $("#username").val();
    // from will be handled by the servlet by checking the session to see who is logged in
    let message = $("#message").val();
    
    let fdata = new FormData();

    fdata.append("to",to);
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

// somehow make the accounts show up in the unorganized list
