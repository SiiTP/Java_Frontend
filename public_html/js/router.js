define([
    'backbone',
    'views/viewManager',
    'views/main',
    'views/field',
    'views/login',
    'views/logout',
    'views/rooms',
    'views/registration',
    'views/scoreboard',
    'models/user',
    'collections/scores',
    'models/rooms',
    'models/gameMediator',
    'models/characters/myCharacter',
    'models/characters/enemyCharacter',
    'constants'
], function (
    Backbone,
    manager,
    MainView,
    GameView,
    LoginView,
    LogoutView,
    RoomsView,
    RegistrationView,
    ScoreboardView,
    user,
    scores,
    rooms,
    GameMediator,
    MyCharacter,
    EnemyCharacter,
    constants
) {
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

    var gameMediator = new GameMediator({
        MyCharacter    : MyCharacter,
        EnemyCharacter :EnemyCharacter,
        roomsView      : roomsView,
        constants      : constants
    });
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
        },
        gameAction: function () {
            //gameView.show();
        },
        roomsAction: function () {
            roomsView.show();
        },
        loginAction: function () {
            loginView.show();
        },
        registrationAction: function () {
            registrationView.show();
        }
    });
    return new Router();
});