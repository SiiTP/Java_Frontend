// главное меню игры
define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        name: "main",
        tagName: 'div',
        template: tmpl,

        events: {
            "click .menu__button":  "onClick",
            "click .button_logout":  "onClick",
            "mousedown .menu__button": "onMouseDown",
            "mouseout .menu__button": "onMouseUp",
            "mouseover .button.menu__button.menu__button_disabled": "printMessage", //выдача сообщения о том что игрок не залогинен.
            "mouseout .button.menu__button.menu__button_disabled": "deleteMessage"
        },
        initialize: function () {
            console.log("main initialized.");
            this.render();
        },
        render: function () {
            console.log("main render.");
            var data = {"isLogged": false, "name": 'undefined', "score" : 0};
            this.$el.html(this.template(data));
            var elem = document.getElementById('page');
            elem.appendChild(this.el);
            this.$el.hide();
        },
        show: function () {
            console.log("show main");
            var data = {"isLogged": auth_user.isLogged(), "name": auth_user.getName(), "score" :auth_user.getScore()};
            this.$el.html(this.template(data));
            $(".menu__button_disabled").attr('data-href', "#disabled"); //при нажатии этой кнопки перехода по ссылке не будет
            this.trigger('show', {'name' : this.name});
            //console.log("after trigger");
            this.$el.show();
        },
        hide: function () {
            console.log("main view hide");
            this.$el.hide();
        },
        onClick: function(event) {
            //console.log(event.toElement.attributes.getNamedItem('data-href').nodeValue);
            event.preventDefault();
            var targetElement = event.currentTarget;
            var href = targetElement.attributes.getNamedItem('data-href').value;
            console.log("data-href : " + href);
            //#disabled у кнопки game когда игрок не залогинен. если указать disabled в prop jquery,
            // кнопка перестает реагировать на события.
            if (href != "#disabled") {
                location.href = href;
            }

        },
        onMouseDown: function(event) {
            var targetElement =$(event.currentTarget);
            if (event.currentTarget.attributes.getNamedItem('data-href').value != "#disabled") {
                targetElement.addClass("menu__button_active");
            }
        },
        onMouseUp: function(event) {
            var targetElement =$(event.currentTarget);
            targetElement.removeClass("menu__button_active");
        },
        printMessage: function(event) {
            $('.menu__container-game-btn>.menu__container-game-btn__msg').text("Please, login if you want play");
        },
        deleteMessage: function(event) {
            $('.menu__container-game-btn>.menu__container-game-btn__msg').text("");
        }
    });

    return new View();
});