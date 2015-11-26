define([
    'backbone',
    'tmpl/logout'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        template: tmpl,
        model: null,
        events: {
            "click .button_logout" : "onSubmit"
        },
        show: function () {
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();

        },
        onSubmit: function() {
            console.log("logout-view logout click event");
            this.model.destroy();
        }
    });

    return View;
});