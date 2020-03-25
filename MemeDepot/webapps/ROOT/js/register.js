"use strict";

let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

//Setup the dropdowns with data
$(document).ready(setup);

//Allows using ajax to submit, but still uses the default
//html validation first
$("#registerForm").submit(function(e) {
    //Prevents the form submission, but allows default validation
    e.preventDefault();

    let username = $("#usernameInput").val();
    let password = $("#passInput").val();
    let email = $("#emailInput").val();
    let phoneNumber = $("#phoneInput").val();

    let day = $("#daySelect").val();
    let month = $("#monthSelect").val();
    let year = $("#yearSelect").val();

    console.log("Username: " + username + " Password: " + password);

    let formData = new FormData();

    formData.append("username", username);
    formData.append("password", password);
    formData.append("email", email);
    formData.append("phone", phoneNumber);

    formData.append("day", day);
    formData.append("month", month);
    formData.append("year", year);

    $.ajax({

        type: "POST",
        url: "/srv/register",
        data: formData,
        contentType: false, 
        processData: false,
        success: (data) => {
            //$("#statusdiv").html("Server said this: "+data);
            signupSuccess(data);
        },
        error: (xhr,status,err) => {
            if(xhr.responseText.includes("duplicate username")) {
                duplicateUsername();
            }
            else {
                $("#statusdiv").html("Something went wrong: status="+status+" err="+err+" resp=");
            }
        }

    });
});

function duplicateUsername() {
    $("#dialog").dialog({title : "Username taken"});
    $("#dialog").html("That username is already taken.");
}

function signupSuccess(data) {
    $("#dialog").dialog({title : "Success!"});
    $("#dialog").html(data);
}

function setup() {
    //Setup the birth date selection
    let monthDropdown = $("#monthSelect");
    let dayDropdown = $("#daySelect");
    let yearDropdown = $("#yearSelect");

    //Setup days
    for (let day = 1; day <= 31; day++) {
        let opt = new Option(day, day);

        dayDropdown.append(opt);
    }

    //Setup months
    months.forEach(month => {
        let opt = new Option(month, month.toLowerCase());

        monthDropdown.append(opt);
    });

    //Setup years
    let maxYear = new Date().getFullYear();

    for(let year = maxYear; year >= 1900; year--) {
        let opt = new Option(year, year);

        yearDropdown.append(opt);
    }
}

function hidePassword() {
    $("#passInput").attr("type", "password");
}

function showPassword() {
    $("#passInput").attr("type", "text");
}

function validateDate() {
    let dayDropdown = $("#daySelect");
    let day = parseInt(dayDropdown.val());

    let monthString = $("#monthSelect").val();
    let month = months.findIndex(name => {
        return name.toLowerCase() == monthString;
    }); 

    let year = parseInt($("#yearSelect").val());

    //Only can fully validate after all 3 have been selected cause of leap years
    if(isNaN(day) || isNaN(month) || isNaN(year)) {
        return;
    }
    
    //If this is false, they selected a day not in the month. That includes leap years
    //This will be true for feb 29 if a leap year is selected
    //https://stackoverflow.com/questions/16353211/check-if-year-is-leap-year-in-javascript
    let valid = new Date(year, month, day).getDate() == day;

    if(!valid) {
        dayDropdown.get(0).setCustomValidity("Invalid day for that month");
    }
    else {
        dayDropdown.get(0).setCustomValidity("");
    }
}

