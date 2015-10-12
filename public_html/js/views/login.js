// форма входа в уч. запись
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
        el: 'div#page',
        template: tmpl,
        model: new login(),
        events: {
            "click .login__buttons-container__button" : "onSubmit"
        },
        initialize: function () {
            console.log("login view initialize");
            //костыль
            //$("#for-event").on("successLogin", this.onLogin);
        },
        render: function () {
            console.log("login view render");
            this.$el.html(this.template());
        },
        show: function () {
            console.log("login view show");
            this.$el.show();
        },
        hide: function () {
            console.log("login view hide");
            this.$el.hide();
        },
        onSubmit: function() {
            console.log("backbone view login click event");
            this.model.trigger("submit");
        }
    });

    return new View();
});