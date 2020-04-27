function changePrivacy() {
    let selection = $("#privacySelect").val();
    let username = $("#username").text();


    let formData = new FormData();

    formData.append("username", username);
    formData.append("setting", selection);

    $.ajax({
        type: "POST",
        url: "/srv/privacy",
        data: formData,
        contentType: false, 
        processData: false
    });
}