define(['backbone'], function(Backbone){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'default': 'defaultActions'
        },
        defaultActions: function () {
            require(['tmpl/main'],function(main){
                var data = {name: 'Ivan'};
                console.log(main(data));
            });
            console.log("default");
        },
        scoreboardAction: function () {
            require(['views/scoreboard'],function(view){
                console.log(view);
            });
            console.log("score");
        },
        gameAction: function () {
            console.log("game");
        },
        loginAction: function () {
            console.log("login");
        }
    });
    return new Router();
});