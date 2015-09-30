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

        },
        uninitialize: function() {
            console.log("user model uninitialize");
            this.name = "username_def";
            this.password = "password_def";
            this.score = 0;
            this.logged = false;
            //console.log("user name : " + this.name);

        },
        isLogged: function() {
            return this.logged;
        }
    });
    return Model;
});