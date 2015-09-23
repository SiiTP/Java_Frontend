define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        initialize: function () {
            console.log("game initialize");
            this.render();
        },
        render: function () {
            console.log("game render");
            this.$el.html(this.template());
        },
        show: function () {
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });
    return new View();
});