//БЭМ
//<p
//options sync
//2 index
define([
    'backbone',
    'views/viewManager',
    'views/main',
    'views/field',
    'views/login',
    'views/rooms',
    'views/registration',
    'views/scoreboard',
    'views/joystick',
    'models/user',
    'collections/scores',
    'models/rooms',
    'gameMediator',
    'joystickTransport',
    'views/characters/myCharacter',
    'views/characters/enemyCharacter',
    'constants'
], function (
    Backbone,
    manager,
    MainView,
    FieldView,
    LoginView,
    RoomsView,
    RegistrationView,
    ScoreboardView,
    joystickView,
    user,
    scores,
    rooms,
    GameMediator,
    JoystickTransport,
    MyCharacter,
    EnemyCharacter,
    constants
) {
    var mainView =         new MainView         ({model: user});
    var registrationView = new RegistrationView ({model: user});
    var loginView =        new LoginView        ({model: user});
    var scoreboardView =   new ScoreboardView   ({model: scores});
    var roomsView =        new RoomsView        ({model: rooms});
    var fieldView =        new FieldView        ({width: constants.get('FIELD_WIDTH'), height: constants.get('FIELD_HEIGHT')});

    manager.add(mainView);
    manager.add(scoreboardView);
    manager.add(loginView);
    manager.add(registrationView);
    manager.add(roomsView);
    manager.add(fieldView);
    manager.add(joystickView);

    var gameMediator = new GameMediator({
        user           : user,
        MyCharacter    : MyCharacter,
        EnemyCharacter : EnemyCharacter,
        constants      : constants,
        field          : fieldView
    });

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'rooms': 'roomsAction',
            'login': 'loginAction',
            'registration': 'registrationAction',
            'mobile/:session': 'joystickAction',
            '': 'mainAction'
        },
        mainAction: function () {
            mainView.show();
        },
        scoreboardAction: function () {
            scoreboardView.show();
        },
        gameAction: function () {
            if (user.get('logged')) {
                fieldView.show();
            } else {
                location.href = "#";
            }
        },
        roomsAction: function () {
            if (user.get('logged')) {
                roomsView.show();
            } else {
                location.href = "#";
            }
        },
        loginAction: function () {
            loginView.show();
        },
        registrationAction: function () {
            registrationView.show();
        },
        joystickAction: function (session) {
            var joystickTransport = new JoystickTransport({
                address : "gameplay/mobile",
                session : session,
                constants: constants
            });
            joystickView.show();
        }
    });
    return new Router();
});