//TODO viewManager
//без js ошибок
define (function() {
    return function GameMediator(args) {
        this.user = args.user;
        this.MyCharacter = args.MyCharacter;
        this.EnemyCharacter = args.EnemyCharacter;
        this.constants= args.constants;
        this.field = args.field;

        this.myPlayer = null;
        this.enemyPlayers = [];
        this.socket = null;
        this.gameBegin= false;
        this.waitingInterval= null;

        this.now  = null;
        this.prev = null;

        this.initialize = function() {
            console.log("mediator initialized!!!");
            this.field.on('show', this.joinToRoom.bind(this));
            this.field.on('clicked', this.sendMessageWaiting.bind(this));
            this.field.on('mouseMove', this.mouseMove.bind(this));
            this.field.on('exit', this.exit.bind(this));
        };
        this.initializeSocket = function() {
            var socket = new WebSocket("ws://localhost:8000/gameplay");//todo localhost в константы
            socket.onopen = function(event) {
                console.log("____ open socket");
                this.beginningGameWaiting();
            }.bind(this);
            socket.onmessage = function(event) {
                console.log("___> get message");
                console.log(event.data);
                var answer = JSON.parse(event.data);

                if (answer.status == 301) {
                    console.log("___@ waiting answer");
                    this.gameBegin = false;
                }

                if (answer.status == 200) {
                    if (true) { // TODO условие answer.limitPlayers
                        var EnemyPlayerView = this.EnemyCharacter;
                        var enemyPlayers = this.enemyPlayers;
                        if (enemyPlayers.length == 0) {
                            for(var i = 0; i < 9; i += 1) { // TODO i < limitPlayers
                                var player = new EnemyPlayerView({
                                    className: "character character_enemy_" + i,
                                    'width': 1000,
                                    'height': 700
                                });
                                enemyPlayers.push(player);
                            }
                        }
                        var myPlayer = this.myPlayer;
                        var MyPlayerView = this.MyCharacter;
                        if (myPlayer == null) {
                            myPlayer = new MyPlayerView({
                                className: "character character_my",
                                'width': 1000,
                                'height': 700
                            });
                            this.myPlayer = myPlayer;
                        }
                    }
                    //this.erasePlayers();
                    this.parsePlayers(answer.players);
                    if (!this.gameBegin) {
                        this.startGame();
                    }
                    this.gameBegin = true;
                }

                if (answer.status == 228) {
                    console.log("___@ winner is : " + answer.winner);
                    this.gameBegin = false;
                }
            }.bind(this);
            socket.onclose = function(event) {
                console.log("____ close socket");
            };
            this.socket = socket;
        };
        this.beginningGameWaiting = function() {
            this.waitingInterval = setInterval(this.sendMessageWaiting.bind(this), 50);
        };
        this.sendMessageWaiting = function() {
            var data = {'direction': -1};
            if (this.myPlayer != null) {
                data = {'direction': this.myPlayer.model.get('angle')};
            }
            this.socket.send(JSON.stringify(data));
        };
        this.parsePlayers = function(answerPlayers) {
            var enemies = this.enemyPlayers;
            var myPlayer = this.myPlayer;

            myPlayer.model.set({'visible': false});
            var amountEnemies = answerPlayers.length - 1;
            //очистка рисунка, на случай если хозяин канваса изменился
            for(var i = 0; i < amountEnemies; i += 1) {
                enemies[i].clear()
            }
            for(var i = amountEnemies; i < enemies.length; i += 1) {
                enemies[i].model.set({'visible': false})
            }

            var j = 0;
            for(var i = 0; i < answerPlayers.length; i += 1) {
                if (this.user.get('username') != answerPlayers[i].name) {
                    enemies[j].model.set({
                        posX  : answerPlayers[i].posX,
                        posY  : answerPlayers[i].posY,
                        name  : answerPlayers[i].name,
                        angle : answerPlayers[i].direction,
                        score : answerPlayers[i].score,
                        visible : true
                    });
                    //console.log("setted enemy (" + enemies[j].model.get('name') + ") pos in array : " + j);
                    j += 1;
                } else {
                    myPlayer.model.set({
                        posX  : answerPlayers[i].posX,
                        posY  : answerPlayers[i].posY,
                        name  : answerPlayers[i].name,
                        angle : answerPlayers[i].direction,
                        score : answerPlayers[i].score,
                        visible : true
                    });
                    //console.log("setted my player (" + myPlayer.model.get('name') + ")");
                }
            }
        };
        this.erasePlayers = function() {
            if (this.myPlayer != null) {
                this.myPlayer.$el.remove();
            }
            if (this.enemyPlayers != []) {
                _.each(this.enemyPlayers, function(player) {
                    player.$el.remove();
                });
            }
            this.enemyPlayers = [];
            this.myPlayer = null;
        };
        this.startGame = function() {
            var now = Date.now();
            var previousMy = now - 20;
            var previousEnemies = now - 20;
            requestAnimationFrame(this.loop.bind(this, previousMy, previousEnemies));
        };
        this.loop = function(previousMy, previousEnemies) {
            if (this.myPlayer != null) {
                if (this.myPlayer.model.get('visible')) {
                    now = Date.now();
                    var dt = (now - previousMy) / 1000;
                    this.myPlayer.model.myMove(dt);
                    previousMy = now;
                    this.myPlayer.draw();
                } else {
                    this.myPlayer.clear();
                }
            }
            _.each(this.enemyPlayers, function(enemy) {
                if (enemy.model.get('visible')) {
                    enemy.draw();
                    var now = Date.now();
                    var dt = (now - previousEnemies)/1000;
                    enemy.model.move(dt);
                    previousEnemies = now;
                } else {
                    enemy.clear();
                }
            });
            if (this.gameBegin) {
                requestAnimationFrame(this.loop.bind(this, previousMy, previousEnemies));
            }
        };
        this.joinToRoom = function() {

            this.initializeSocket();
        };
        this.mouseMove = function(args) {
            if (this.myPlayer != null) {
                this.myPlayer.model.setMouseCoordinate(args.x, args.y);
            }
        };
        this.exit = function() {
            console.log("exit from game");
            clearInterval(this.waitingInterval);
            this.gameBegin = false;
            if (this.socket) {
                this.socket.close();
            }
            this.erasePlayers();
        }
    }
});
