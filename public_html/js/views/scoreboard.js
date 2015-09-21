define([
    'backbone',
    'tmpl/scoreboard'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: "div",
        className: "score__item",
        template: tmpl,
        initialize: function () {
            console.log("initialization of scoreboard");
        },
        render: function () {
            console.log("render scoreboard");
            this.$el.html(this.template());
        },


        show: function () {

        },
        hide: function () {
            // TODO
        }

    });

    return new View();
});