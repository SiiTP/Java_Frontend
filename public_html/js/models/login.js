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
                console.log("SERVER : " + obj);
                var loginData = {"username" : $("#username").val(), "password":$("#password").val()};
                auth_user.name = loginData.username;
                auth_user.password = loginData.password;
                auth_user.logged = true;
                location.href = "#logout";
                //TODO get user score from database in the future
                //Костыль
                //$("#for-event").trigger("successLogin", [loginData]);
                //TODO  IF SUCCESS, INITIALIZE USER MODEL
                //you successfully have been logined in!
            });
        }
    });
    return Model;
});
