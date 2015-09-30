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
            console.log("backbone model submit event");
            var loginForm = $("#login-form");
            $.ajax({
                type: "POST",
                url: "/signin",
                data: loginForm.serialize()
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    var loginData = {"username" : $("#username").val(), "password":$("#password").val()};
                    auth_user.name = loginData.username;
                    auth_user.password = loginData.password;
                    auth_user.logged = true;
                    location.href = "#logout";
                } else {
                    console.log(answer.message);
                }
                //TODO get user score from database in the future
            });
        }
    });
    return Model;
});
