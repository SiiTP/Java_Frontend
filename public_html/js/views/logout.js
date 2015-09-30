//форма выхода из учентной записи
define([
    'backbone',
    'tmpl/logout',
    'models/logout'
], function(
    Backbone,
    tmpl,
    logout
){

    var View = Backbone.View.extend({
        el: 'div#page',
        template: tmpl,
        model: new logout(),
        events: {
            "click .button_logout" : "onSubmit",
            "click .logout__on-main-page" : "onMain"
        },
        initialize: function () {
            console.log("logout initialize");
        },
        render: function () {
            console.log("logout render");
            var data = {"name": auth_user.name, "score": auth_user.score};
            this.$el.html(this.template(data));
        },
        show: function () {
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();

        },

        onSubmit: function() {
            console.log("logout-view logout click event");
            this.model.trigger("submit")
        },
        onMain: function() {
            location.href = $(".logout__on-main-page").attr('data-href');
        }
        //onLogout: function() {
        //    console.log("logout-view success logout");
        //    this.user.trigger("successLogout",{"param" : "myParam"});
        //}
    });

    return new View();
});