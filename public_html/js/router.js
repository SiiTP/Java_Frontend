define([
        'backbone',
        'models/viewManager',
        'views/main',
        'views/game',
        'views/login',
        'views/logout',
        'views/rooms',
        'views/registration',
        'views/scoreboard',
        'models/user',
        'collections/scores',
        'models/rooms'
], function (
    Backbone,
    ViewManager,
    MainView,
    GameView,
    LoginView,
    LogoutView,
    RoomsView,
    RegistrationView,
    ScoreboardView,
    user,
    scores,
    rooms
) {
    var manager =          new ViewManager();
    var mainView =         new MainView         ({model: user});
    var registrationView = new RegistrationView ({model: user});
    var loginView =        new LoginView        ({model: user});
    var logoutView =       new LogoutView       ({model: user});
    var scoreboardView =   new ScoreboardView   ({model: scores});
    var roomsView =        new RoomsView        ({model: rooms});
    manager.add(scoreboardView);
    manager.add(loginView);
    manager.add(logoutView);
    manager.add(registrationView);
    manager.add(mainView);
    manager.add(roomsView);
    //manager.add(gameView);
    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'room': 'roomAction',
            'rooms': 'roomsAction',
            'login': 'loginAction',
            'logout': 'logoutAction',
            'registration': 'registrationAction',
            '': 'mainAction',
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
            //gameView.show();
        },
        roomsAction: function () {
            roomsView.show();
        },
        roomAction: function () {
            //roomView.show();
            console.log("room action");
        },
        loginAction: function () {
            loginView.show();
            console.log("login action");
        },
        registrationAction: function () {
            registrationView.show();
            console.log("registration action");
        }
    });
    return new Router();
});