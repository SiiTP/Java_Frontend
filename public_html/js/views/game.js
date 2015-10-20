define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        name: "game",
        tagName: 'div',
        template: tmpl,
        initialize: function () {
            console.log("game initialize");
            this.render();
        },
        render: function () {
            console.log("game render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
        },
        show: function () {
            this.trigger('show',{'name' : this.name});
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });
    return new View();
});