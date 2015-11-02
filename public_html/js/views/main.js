// главное меню игры
define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){
    return Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        JQmsg: undefined,
        events: {
            "click .container-game-btn__button": "onClick",
            "click .button_logout": "onClick",
            "mousedown .container-game-btn__button": "onMouseDown",
            "mouseout .container-game-btn__button": "onMouseUp",
            "mouseover .button.container-game-btn__button.container-game-btn__button_disabled": "printMessage", //выдача сообщения о том что игрок не залогинен.
            "mouseout .button.container-game-btn__button.container-game-btn__button_disabled": "deleteMessage"
        },
        initialize: function () {
            this.model.on('change', this.checkChanges.bind(this));
        },
        checkChanges: function () {
            if (this.model.hasChanged('logged') || this.model.hasChanged('score')) {
                this.render();
            }
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.JQmsg = $('.container-game-btn__msg');
        },
        show: function () {
            this.trigger('show');
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        onClick: function (event) {
            event.preventDefault();
            var targetElement = event.currentTarget;
            if (!$(targetElement).hasClass('container-game-btn__button_disabled')) {
                var href = targetElement.attributes.getNamedItem('data-href').value;
                if (href == "#logout") {
                    this.model.onLogout();
                } else {
                    location.href = href;
                }
            }

        },
        onMouseDown: function (event) {
            var targetElement = $(event.currentTarget);
            if (!targetElement.hasClass('container-game-btn__button_disabled')) {
                targetElement.addClass('container-game-btn__button_active');
            }
        },
        onMouseUp: function (event) {
            var targetElement = $(event.currentTarget);
            targetElement.removeClass("container-game-btn__button_active");
        },
        printMessage: function () {
            this.JQmsg.text("Please, login if you want play");
        },
        deleteMessage: function () {
            this.JQmsg.text("");
        }
    });
});