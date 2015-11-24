// топ 10 игроков
define([
    'backbone',
    'tmpl/scoreboard'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        events: {
            "click .js-button-onMain" : 'onClick'
        },
        initialize: function () {
            this.model.on('change', this.render.bind(this));
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            this.$el.hide();
        },
        onClick: function(event) {
            location.href = event.currentTarget.attributes.getNamedItem('data-href').value;
        },
        show: function () {
            this.$el.show();
            this.trigger('show');
        },
        hide: function () {
            this.$el.hide();
        }

    });
    return View;
});