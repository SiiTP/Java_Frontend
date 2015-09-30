define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        name: "username_def",
        password: "password_def",
        score: 0,
        logged: false,
        initialize: function() {
            console.log("user model initialize");
            $.ajax({
                type: "POST",
                url: "/logininfo"
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    //TODO ссылка на конкретный объект в классе, пока user-singleton нормально
                    auth_user.logged = true;
                    auth_user.name = answer.username;
                    console.log(this.logged);
                    //TODO присваивать счет
                }
            });
        },
        uninitialize: function() {
            console.log("user model uninitialize");
            this.name = "username_def";
            this.password = "password_def";
            this.score = 0;
            this.logged = false;

        },
        isLogged: function() {
            return this.logged;
        }
    });
    return Model;
});