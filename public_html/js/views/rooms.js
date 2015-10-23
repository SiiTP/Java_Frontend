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
            'click .rooms__lines__line__button': 'onConnect'
        },
        initialize: function () {
            console.log("rooms initialize");
            this.render();
        },
        render: function() {
            console.log("rooms render");
            this.$el.html(this.template(this.model.getRooms()));
            document.getElementById('page').appendChild(this.el);
            this.$el.hide();
            //var data = {"name": auth_user.name, "score": auth_user.score};
        },
        onConnect: function(event) {
            var roomID = event.currentTarget.attributes.getNamedItem('data-roomid').value;
            console.log("you connect in room with id : " + roomID);
            //TODO запрос на сервер о входе в комнату
            location.href = '#room';
        },
        show: function() {
            console.log("show rooms");
            console.log(this.model.getRooms());
            this.$el.html(this.template(this.model.getRooms()));
            this.trigger('show',{'name': this.name});
            this.$el.show();
        },
        hide: function() {
            console.log("rooms view hide");
            this.$el.hide();
        }
    });

    return new View();
});