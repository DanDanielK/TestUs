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