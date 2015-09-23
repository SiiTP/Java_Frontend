define([
    'backbone',
    'tmpl/registration'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        initialize: function () {
            console.log("registration initialize");
            this.render();
        },
        render: function () {
            console.log("registration render");
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