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
        },
        initialize: function () {
            this.model.on('change', this.checkChanges.bind(this));
            this.model.on('error', function() {
                console.log("ERROR");
            }.bind(this));
            this.render();
        },
        checkChanges: function () {
            if (this.model.hasChanged('logged') || this.model.hasChanged('score')) {
                this.render();
            }
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
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
        }
    });
});