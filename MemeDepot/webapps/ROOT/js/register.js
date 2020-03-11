"use strict";

let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

$(document).ready(function() {
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

    //Validate the whole date whenever one changes
    monthDropdown.change(validateDate);
    dayDropdown.change(validateDate);
    yearDropdown.change(validateDate);
    
    
    //Password hiding
    let passwordButton = $("#showPassword");

    passwordButton.mousedown(function() {
        $("#passInput").attr("type", "text");
    });

    passwordButton.mouseleave(hidePassword);
    passwordButton.mouseup(hidePassword);
    


    passwordButton.rele
});

function hidePassword() {
    $("#passInput").attr("type", "password");
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

