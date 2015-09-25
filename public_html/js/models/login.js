define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        username: "user",
        password: "user",
        events: {
            "submit": "onSubmit"
        },
        onSubmit: function() {
            console.log("backbone model submit event" + $("#login-form").serialize() + " |");
            $.ajax({
                type: "POST",
                url: "/signin",
                data: $("#login-form").serialize()
            }).done(function(obj) {
                console.log("OBJECT : " + obj);
            });

        }
    });
    return new Model();
});
