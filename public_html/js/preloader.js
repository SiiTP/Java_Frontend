new function Preloader() {
    console.log("on preloader");
    document.addEventListener("DOMContentLoaded", function() {
        console.log("EVENT");
        document.getElementById("page").removeChild(html);
    });
    var html = document.createElement('div');
    html.innerHTML = "<div class=\"preloader\"> " +
                        "<img src=\"img/preloader.GIF\"> " +
                        "<div class=\"preloader__text\">Подождите, страница загружается</div> " +
                     "</div>";
    document.getElementById("page").appendChild(html);
};