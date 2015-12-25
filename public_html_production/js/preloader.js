new function Preloader() {
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("page").removeChild(html);
    });
    var html = document.createElement('div');
    html.innerHTML = "<div class=\"preloader\"> " +
                        "<img src=\"img/preloader.GIF\"> " +
                        "<div class=\"preloader__text\">Подождите, страница загружается</div> " +
                     "</div>";
    document.getElementById("page").appendChild(html);
};