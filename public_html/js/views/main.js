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
            //"mousedown .game-btn-container__button": "onMouseDown",
            //"mouseout .game-btn-container__button": "onMouseUp",
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
            this.render(); //TODO убрать
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
        //onMouseDown: function (event) {
        //    var targetElement = $(event.currentTarget);
        //    if (!targetElement.hasClass('game-btn-container__button_disabled')) {
        //        targetElement.addClass('game-btn-container__button_active');
        //    }
        //},
        //onMouseUp: function (event) {
        //    var targetElement = $(event.currentTarget);
        //    targetElement.removeClass("game-btn-container__button_active");
        //},
        printMessage: function () {
            this.JQmsg.text("Пожалуйста, авторизуйтесь, если хотите сыграть");
        },
        deleteMessage: function () {
            this.JQmsg.text("");
        }
    });
});