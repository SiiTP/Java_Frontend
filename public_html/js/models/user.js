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
            name = "username_def";
            password = "password_def";
            score = 0;
            logged = false;
        },
        isLogged: function() {
            return this.logged;
        }
    });
    return Model;
});