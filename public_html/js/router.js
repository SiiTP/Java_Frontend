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
    'gameMediator',
    'views/characters/myCharacter',
    'views/characters/enemyCharacter',
    'constants'
], function (
    Backbone,
    manager,
    MainView,
    FieldView,
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
    var fieldView =        new FieldView        ({width: constants.get('FIELD_WIDTH'), height: constants.get('FIELD_HEIGHT')});
    manager.add(mainView);
    manager.add(scoreboardView);
    manager.add(loginView);
    manager.add(logoutView);
    manager.add(registrationView);
    manager.add(roomsView);
    manager.add(fieldView);
    var gameMediator = new GameMediator({
        user           : user,
        MyCharacter    : MyCharacter,
        EnemyCharacter : EnemyCharacter,
        constants      : constants,
        field          : fieldView
    });
    gameMediator.initialize();
    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'rooms': 'roomsAction',
            'login': 'loginAction',
            'registration': 'registrationAction',
            '': 'mainAction'
        },
        mainAction: function () {
            mainView.show();
        },
        scoreboardAction: function () {
            scoreboardView.show();
        },
        gameAction: function () {
            fieldView.show();
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