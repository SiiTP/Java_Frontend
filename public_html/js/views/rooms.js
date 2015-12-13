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
            'click .rooms__create__button': 'onCreateRoom',
            'click .js-button-refresh': 'onRefresh',
            'click .js-button-onMain': 'onMain'
        },
        initialize: function () {
            this.model.on('change', this.render.bind(this));
            this.model.on('error', this.printMessage.bind(this));
            this.model.on('onGame', function() {
                location.href = "#game";
            })
        },
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            this.JQ_msg = $('.rooms__create__message').first();
        },
        printMessage: function(model, response) {
            if (response.status == 300) {
                model.set({"rooms": []});
            }
            if (!this.JQ_msg) {
                this.JQ_msg = $('.rooms__create__message').first();
            }
            this.JQ_msg.text(response.message);
        },
        onConnect: function(event) {
            console.log("error message");
            var roomName = event.currentTarget.attributes.getNamedItem('data-roomid').value;
            this.model.set({'roomName' : roomName});
            this.model.set({'password' : null});
            this.model.set({'id'       : 1});
            this.model.save(null, this.model.optionsJoin);
            this.model.unset("id")
        },
        onCreateRoom: function(event) {
            event.preventDefault();
            this.model.set({'roomName' : $('#roomName').val()});
            this.model.set({'password' : null});
            this.model.unset("id");
            this.model.save(null, this.model.optionsCreate);
        },
        onRefresh: function() {
            this.model.fetch(this.model.optionsFetch);
        },
        onMain: function() {
            location.href = "#"
        },
        show: function() {
            this.model.fetch(this.model.optionsFetch);
            this.trigger('show');
            this.$el.show();
        },
        hide: function() {
            this.$el.hide();
        }
    });

    return View;
});