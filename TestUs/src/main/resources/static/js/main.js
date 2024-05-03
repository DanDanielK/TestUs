$(document).ready(function () {

    // get the server url
    let serverUrl = window.location.protocol + "//" + window.location.host;



    $(".custom-file-input").on("change", function () {
        let fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });




    // setup gridView
    $('#grid').DataTable({
        "autoWidth": true,
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
    });



});