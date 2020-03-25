"use strict";

function submitForm(){
    let name = $("#user").val();
    let pobox = $("#pobox").prop("checked");
    let state = $("#state").val();
    
    let fdata = new FormData();

    fdata.append("name",name);
    fdata.append("pobox",pobox);
    fdata.append("state",state);

    $.ajax({
        type: "POST",
        url: "/srv/formtest",
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
