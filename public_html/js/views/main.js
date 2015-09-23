define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        initialize: function () {
            console.log("main initialized.");
            //this.listenTo(this.model, "change", this.render);
            this.render();
        },
        render: function () {
            console.log("main render");
            this.$el.html(this.template());
        },
        show: function () {
            console.log(" show main");
        },
        hide: function () {
            this.$el.hide();
            console.log(" hide main");
        }

    });

    return new View();
});