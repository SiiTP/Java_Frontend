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
            "click .js-button-onMain"   : 'onMain',
            "click .js-button-onUpdate" : 'onUpdate'
        },
        initialize: function () {
            this.model.on('change', this.render.bind(this));
        },
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
        },
        onMain: function(event) {
            location.href = event.currentTarget.attributes.getNamedItem('data-href').value;
        },
        onUpdate: function() {
            this.model.fetch(this.model.optionsFetch);
        },
        show: function () {
            this.$el.show();
            this.model.fetch(this.model.optionsFetch);
            this.trigger('show');
        },
        hide: function () {
            this.$el.hide();
        }

    });
    return View;
});