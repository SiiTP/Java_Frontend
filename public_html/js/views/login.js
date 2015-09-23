define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        initialize: function () {
            console.log("login initialize");
            this.render();
        },
        render: function () {
            console.log("login render");
            this.$el.html(this.template());
        },
        show: function () {
            // TODO
        },
        hide: function () {
            // TODO
        }

    });

    return new View();
});