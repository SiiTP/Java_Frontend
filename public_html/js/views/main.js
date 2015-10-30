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
        JQmsg: undefined,
        jqueryCash: {
            'doNotLoggedMessage': undefined
        },

        events: {
            "click .container-game-btn__button":  "onClick",
            "click .button_logout":  "onClick",
            "mousedown .container-game-btn__button": "onMouseDown",
            "mouseout .container-game-btn__button": "onMouseUp",
            "mouseover .button.container-game-btn__button.container-game-btn__button_disabled": "printMessage", //выдача сообщения о том что игрок не залогинен.
            "mouseout .button.container-game-btn__button.container-game-btn__button_disabled": "deleteMessage"
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
            this.JQmsg = $('.container-game-btn>.container-game-btn__msg');
            this.$el.hide();
        },
        show: function () {
            console.log("show main");
            var data = {"isLogged": auth_user.isLogged(), "name": auth_user.getName(), "score" :auth_user.getScore()};
            this.$el.html(this.template(data));
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
            //console.log("data-href : " + href);
            //#disabled у кнопки game когда игрок не залогинен. если указать disabled в prop jquery,
            // кнопка перестает реагировать на события.
            if (!$(targetElement).hasClass('container-game-btn__button_disabled')) {
                location.href = href;
            }

        },
        onMouseDown: function(event) {
            var targetElement =$(event.currentTarget);
            if (!targetElement.hasClass('container-game-btn__button_disabled')) {
                targetElement.addClass("container-game-btn__button_active");
            }
        },
        onMouseUp: function(event) {
            var targetElement =$(event.currentTarget);
            targetElement.removeClass("container-game-btn__button_active");
        },
        printMessage: function(event) {
            //this.JQmsg = $('.container-game-btn>.container-game-btn__msg');
            //console.log(this.JQmsg);
            //this.jqueryCash.doNotLoggedMessage
            console.log(this.JQmsg);
            console.log($('.container-game-btn>.container-game-btn__msg'));
            //console.log(this.JQmsg);
            //this.JQmsg = $('.container-game-btn>.container-game-btn__msg');
            //this.JQmsg = $('.container-game-btn>.container-game-btn__msg');
            this.JQmsg.text("Please, login if you want play");
        },
        deleteMessage: function(event) {
            this.JQmsg.text("");
        }
    });

    return new View();
});