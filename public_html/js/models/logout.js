define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        initialize: function() {
            this.on('submit', function(){
                this.onSubmit();
            });
        },
        onSubmit: function() {
            console.log("backbone view logout click event");
            $.ajax({
                type: "POST",
                url: "/logout"
            }).done(function(obj) {
                console.log("SERVER : " + obj);
                auth_user.initialize(); // сброс данных в моделе

                //TODO UNINITIALIZE USER WITH SUCCESS
            });
        }
    });
    return Model;
});
