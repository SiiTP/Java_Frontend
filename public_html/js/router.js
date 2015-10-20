define(['backbone',
        'models/viewManager',
        'views/main',
        'views/game',
        'views/login',
        'views/logout',
        'views/rooms',
        'views/registration',
        'views/scoreboard'],
    function(Backbone,
             ViewManager,
             mainView,
             gameView,
             loginView,
             logoutView,
             roomsView,
             registrationView,
             scoreboardView) {

    //manager.add(mainView, slkjfldsView);
        manager = new ViewManager();
        /*manager.addInViews(MainView, {'typeView' : 1});
        manager.addInViews(GameView, {'typeView' : 1});
        manager.addInViews(MainView, {'typeView' : 1});
        manager.addInViews(MainView, {'typeView' : 1});
        manager.addInViews(LoginView, {'typeView' : 1});
        manager.addInViews(GameView, {'typeView' : 1});
        manager.addInViews(LoginView, {'typeView' : 1});
        manager.info();*/
        manager.add(mainView);
        manager.add(scoreboardView);
        manager.add(loginView);
        manager.add(registrationView);
        manager.add(gameView);
        manager.add(roomsView);
        //manager.setListeners();
        var views = manager.getViews();

        manager.trigger('showing');
        var Router = Backbone.Router.extend({
            routes: {
                'scoreboard': 'scoreboardAction',
                'game': 'gameAction',
                'rooms': 'roomsAction',
                'login': 'loginAction',
                'logout': 'logoutAction',
                'registration': 'registrationAction',
                '*main': 'mainAction',
                'default': 'defaultActions'
            },
            mainAction: function () {
                mainView.show();
            },
            scoreboardAction: function () {
                scoreboardView.show();
                console.log("scoreboard action");
            },
            gameAction: function () {
                gameView.show();
                console.log("game action");
            },
            roomsAction: function () {
                roomsView.show();
                console.log("game action");
            },
            loginAction: function () {
                loginView.show();
                console.log("login action");
            },
            logoutAction: function () {
                logoutView.onSubmit();
                console.log("logout action");
            },
            registrationAction: function () {
                registrationView.show();
                console.log("registration action");
            },
            defaultAction: function () {
                console.log("default");
            }
        });
        return new Router();
    });