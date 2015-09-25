define([
    'backbone',
    'tmpl/login',
    'models/login'
], function(
    Backbone,
    tmpl,
    login
){

    var View = Backbone.View.extend({
        el: 'div',
        template: tmpl,
        model: login,
        events: {
            "click .button_login" : "onLogin",
            "click .button_logout" : "onLogout",
            "change" : "onChange"
        },
        initialize: function () {
            console.log("login initialize");
        },
        render: function () {
            console.log("login render");
            this.$el.html(this.template());
        },
        show: function () {

        },
        hide: function () {

        },
        onLogin: function(event) {
            console.log("backbone view login click event");
            this.model.onSubmit();
        },
        onLogout: function() {
            console.log("backbone view logout click event");
            $.ajax({
                type: "POST",
                url: "/logout"
            }).done(function(obj) {
                console.log("OBJECT : " + obj);
            });
        }

    });

    return new View();
});