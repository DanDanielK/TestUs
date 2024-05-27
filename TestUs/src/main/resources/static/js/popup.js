document.getElementById("show-add").addEventListener('click', function() {
    document.querySelector(".popup").classList.add("active");
    document.querySelector(".overlay").style.display = "block"; // Show the overlay
});

document.querySelector(".popup .close-btn").addEventListener('click', function() {
    document.querySelector(".popup").classList.remove("active");
    document.querySelector(".overlay").style.display = "none"; // Hide the overlay
});

document.querySelector(".overlay").addEventListener('click', function() {
    document.querySelector(".popup").classList.remove("active");
    document.querySelector(".overlay").style.display = "none"; // Hide the overlay
});

function filterByRole(){
    var dropdown = document.getElementById("role");
    var selectedValue= dropdown.value;
    var table= document.getElementById("usersTable");
    var rows= table.getElementsByTagName("tr");

    for(var i=1; i<rows.length; i++) {
        var row = rows[i];
        var role = row.cells[4].textContent.trim();
        if (selectedValue === "ALL" || role === selectedValue) {
            row.style.display = "";
        }
        else {
            row.style.display = "none";
        }
    }

}




function filterByDath(){

const now = new Date();
const currentTimeString = now.toLocaleTimeString();

var dropdown = document.getElementById("dath");
var selectedValue= dropdown.value;
var table= document.getElementById("TestsTable");
var rows= table.getElementsByTagName("tr");

for(var i=1; i<rows.length; i++) {
    var row = rows[i];
    var dateStr = row.cells[3].textContent.trim();
    console.log("------------------------------------------"+ dateStr);
    if (selectedValue === "ALL") {
        row.style.display = "none";
    }
    else if (selectedValue === "COMPLETED" ){//&& new date(dateStr) > currentTimeString) {
        row.style.display = "";
    }
    else if (selectedValue === "TODO" ){//&& new date(dateStr) < currentTimeString) {
        row.style.display = "";
    }
    else {
        row.style.display = "none";
    }
}

}

