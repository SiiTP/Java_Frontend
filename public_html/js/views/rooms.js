//форма выхода из учентной записи
define([
    'backbone',
    'tmpl/rooms',
    'models/rooms'
], function(
    Backbone,
    tmpl,
    Rooms
){

    var View = Backbone.View.extend({
        name: 'rooms',
        tagName: 'div',
        template: tmpl,
        model: new Rooms(),
        events: {
        },
        initialize: function () {
            console.log("rooms initialize");
            this.render();
        },
        render: function () {
            console.log("rooms render");
            this.$el.html(this.template(this.model.getRooms()));
            document.getElementById('page').appendChild(this.el);
            //var data = {"name": auth_user.name, "score": auth_user.score};
        },
        show: function () {
            console.log("show rooms");
            console.log(this.model.getRooms());
            this.$el.html(this.template(this.model.getRooms()));
            this.trigger('show',{'name': this.name});
            this.$el.show();
        },
        hide: function () {
            console.log("rooms view hide");
            this.$el.hide();
        }
    });

    return new View();
});