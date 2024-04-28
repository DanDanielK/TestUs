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
