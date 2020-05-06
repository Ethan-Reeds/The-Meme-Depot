"use strict";
let numLoaded = 0;
let loading = false;

$(document).ready(loadUsers);

//Triggers when scrolled to the bottom (Having issues with being called to quickly)
// $(window).scroll(function() {
//     if($(window).scrollTop() + $(window).height() > $(document).height() - 20) {
//         console.log("Loading users!");
//         loadUsers();
//     }
//  });

function loadUsers() {
   let offset = numLoaded;
   let numToLoad = 4;

   $.ajax({
       type: "GET",
       url: "/srv/browse",
       data: {
           offset: offset,
           loadNum: numToLoad
       },
       success: (data) => { load(data); },
       error: (xhr,status,err) => { console.log(xhr.responseText); } 
   });
}

function load(data) {
    if(data.Error != null) {
        console.log(data.Error)
        return;
    }

    let users = data["Users"];

    if(users.length == 0)
        return;

    loading = true;
    //Use to not where we need to start to load more
    numLoaded += users.length;

    for(let i = 0; i < users.length; i+=2) {
        let rowT = document.getElementById("rowTemplate").content.querySelector("div");
        let colT = document.getElementById("colTemplate").content.querySelector("div");
        colT.querySelector("a").href = "http://localhost:2020/srv/user/" + users[i];
        colT.querySelector("img").src = "http://localhost:2020/srv/image/" + users[i];
        colT.querySelector("div").innerHTML = users[i];
        let colClone = document.importNode(colT, true);
        let rowClone = document.importNode(rowT, true);
        rowClone.appendChild(colClone);
        if(i + 1 >= users.length) {
            document.querySelector("#container").appendChild(rowClone);
            break;
        }
        //Add the second column
        colT.querySelector("a").href = "http://localhost:2020/srv/user/" + users[i + 1];
        colT.querySelector("img").src = "http://localhost:2020/srv/image/" + users[i + 1];
        colT.querySelector("div").innerHTML = users[i + 1];
        colClone = document.importNode(colT, true);     
        rowClone.appendChild(colClone);        
        document.querySelector("#container").appendChild(rowClone);      
    }
}