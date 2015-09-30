//форма регистрации
define([
    'backbone',
    'tmpl/registration',
    'models/registration'
], function(
    Backbone,
    tmpl,
    registration
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        model: new registration(),
        events: {
            "click .button_registration": "onSubmit"
        },
        initialize: function () {
            console.log("registration initialize");
        },
        render: function () {
            console.log("registration render");
            this.$el.html(this.template());
        },
        onSubmit: function() {
            console.log("on submit registration view");
            if ($("#confirm").val() == $("#password").val()) {
                this.model.onSubmit();
            } else {
                console.log("пароли не совпадают");
            }
        },
        show: function () {

        },
        hide: function () {
        }

    });

    return new View();
});