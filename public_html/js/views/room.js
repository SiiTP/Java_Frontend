//форма выхода из учентной записи
define([
    'backbone',
    'tmpl/room',
    'models/room',
    'views/game'
], function(
    Backbone,
    tmpl,
    Room,
    field
){

    var View = Backbone.View.extend({
        name: 'room',
        tagName: 'div',
        gameField: field,
        template: tmpl,
        model: new Room(),
        events: {
        },
        initialize: function () {
            console.log("room initialize");
            this.render();
        },
        render: function() {
            console.log("room render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
            this.$el.hide();
            //var data = {"name": auth_user.name, "score": auth_user.score};
        },
        show: function() {
            console.log("show room");
            this.$el.html(this.template());
            this.trigger('show',{'name': this.name});
            this.$el.show();
        },
        hide: function() {
            console.log("room view hide");
            this.$el.hide();
        }
    });

    return new View();
});