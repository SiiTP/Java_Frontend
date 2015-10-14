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
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    auth_user.uninitialize();
                    console.log("auth_user uninitializing. " + auth_user.name);
                    location.href = "#main";
                } else {
                    console.log(answer.message);
                }
            });
        }
    });
    return Model;
});
