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
            "click .game-btn-container__button": "onClick",
            "click .logout__button": "onClick",
            "mouseover .button.game-btn-container__button.game-btn-container__button_disabled": "printMessage", //выдача сообщения о том что игрок не залогинен.
            "mouseout .button.game-btn-container__button.game-btn-container__button_disabled": "deleteMessage"
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
            this.JQmsg = $('.menu__msg-game').first();
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
            if (!$(targetElement).hasClass('game-btn-container__button_disabled')) {
                var href = targetElement.attributes.getNamedItem('data-href').value;
                if (href == "#logout") {
                    this.model.destroy(this.model.optionsDestroy);
                } else {
                    location.href = href;
                }
            }
        },
        printMessage: function () {
            this.JQmsg.text("Пожалуйста, авторизуйтесь, если хотите сыграть");
        },
        deleteMessage: function () {
            this.JQmsg.text("");
        }
    });
});