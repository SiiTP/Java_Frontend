//форма выхода из учентной записи
define([
    'backbone',
    'tmpl/rooms'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        events: {
            'click .rooms__lines__line__button': 'onConnect',
            'click .rooms__create__button': 'onCreateRoom'
        },
        render: function() {
            console.log("rooms render");
            console.log(this.model.toJSON().rooms);
            this.$el.html(this.template(this.model.toJSON()));
            this.$el.hide();
        },
        onConnect: function(event) {
            var roomID = event.currentTarget.attributes.getNamedItem('data-roomid').value;
            console.log("you connect in room with id : " + roomID);
            this.model.onJoin(roomID);
        },
        onCreateRoom: function(event) {
            event.preventDefault();
            this.model.onCreate();
        },
        show: function() {
            console.log("show rooms");
            //console.log(this.model.getRooms());
            this.model.fetch();
            this.$el.html(this.template(this.model.toJSON()));
            this.trigger('show',{'name': this.name});
            this.$el.show();
        },
        hide: function() {
            this.$el.hide();
        }
    });

    return View;
});