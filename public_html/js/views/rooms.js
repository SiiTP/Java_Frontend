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
        JQ_msg: null,
        events: {
            'click .rooms__lines__line__button': 'onConnect',
            'click .rooms__create__button': 'onCreateRoom'
        },
        initialize: function () {
            this.model.on('change', this.render.bind(this));
            this.model.on('serverError', this.printMessage.bind(this));
        },
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            this.JQ_msg = $('.rooms__create__message').first();
        },
        printMessage: function(args) {
            this.JQ_msg.text(args.message);
        },
        onConnect: function(event) {
            var roomID = event.currentTarget.attributes.getNamedItem('data-roomid').value;
            this.model.onJoin(roomID);
        },
        onCreateRoom: function(event) {
            event.preventDefault();
            this.model.onCreate();
        },
        show: function() {
            this.model.fetch();
            this.trigger('show');
            this.$el.show();
        },
        hide: function() {
            this.$el.hide();
        }
    });

    return View;
});