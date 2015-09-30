define(['backbone'], function(Backbone){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'logout': 'logoutAction',
            'registration': 'registrationAction',
            '*main': 'mainAction',
            'default': 'defaultActions'
        },
        mainAction: function () {
            require(['views/main'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("main action");
        },
        scoreboardAction: function () {
            require(['views/scoreboard'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("scoreboard action");
        },
        gameAction: function () {
            require(['views/game'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("game action");
        },
        loginAction: function () {
            require(['views/login'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("login action");
        },
        logoutAction: function () {
            require(['views/logout'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("logout action");
        },
        registrationAction: function () {
            require(['views/registration'],function(view){
                //view.el = '#page';
                view.render();
            });
            console.log("registration action");
        },
        defaultAction: function () {
            console.log("default");
        }
    });
    return new Router();
});