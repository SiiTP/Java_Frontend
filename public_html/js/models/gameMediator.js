define (['backbone'], function(Backbone) {
    return Backbone.Model.extend({
        defaults: {
            user : null,
            field : null,
            myPlayer : null,
            enemyPlayers : [],
            MyCharacter : null,
            EnemyCharacter : null,
            socket: null,
            constants: null,
            gameBegin: false,
            waitingInterval: null
        },
        initialize: function() {
            console.log("mediator initialized");
            this.get('field').on('show', this.joinToRoom.bind(this));
            this.get('field').on('clicked', this.sendMessageWaiting.bind(this));
            this.get('field').on('mouseMove', this.mouseMove.bind(this));
            this.get('field').on('exit', this.exit.bind(this));
        },
        initializeSocket: function() {
            var socket = new WebSocket("ws://g14.javaprojects.tp-dev.ru/gameplay");//todo localhost в константы
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
                    this.set({'gameBegin': false});
                }

                if (answer.status == 200) {
                    //console.log("___@ game in proccess");

                    this.erasePlayers();
                    this.parsePlayers(answer.players);
                    if (!this.get('gameBegin')) {
                        this.startGame();
                    }

                    this.set({'gameBegin': true});
                }

                if (answer.status == 228) {
                    console.log("___@ winner is : " + answer.winner);
                    this.set({'gameBegin': false});
                }
            }.bind(this);
            socket.onclose = function(event) {
                console.log("____ close socket");
            };
            this.set({'socket': socket});
        },
        beginningGameWaiting: function() {
            this.set({'waitingInterval': setInterval(this.sendMessageWaiting.bind(this), 200)});
        },
        sendMessageWaiting: function() {
            //console.log("<___ send message waiting");
            var data = {'direction': -1};
            if (this.get('myPlayer') != null) {
                data = {'direction': this.get('myPlayer').model.get('angle')};
            }
            console.log("direction : " + data.direction);
            this.get('socket').send(JSON.stringify(data));
        },
        parsePlayers: function(answerPlayers) {
            var players = [];
            var Enemy = this.get('EnemyCharacter');
            var My = this.get('MyCharacter');
            _.each(answerPlayers, function(player) {
                if (this.get('user').get('username') != player.name) {
                    var item = new Enemy({
                        className: "character character_" + player.name,
                        'width': 1000,
                        'height': 700
                    });
                    item.model.set({
                        posX  : player.posX,
                        posY  : player.posY,
                        name  : player.name,
                        angle : player.direction,
                        score : player.score
                    });
                    players.push(item);
                } else {
                    item = new My({
                        className: "character character_" + player.name,
                        'width': 1000,
                        'height': 700});
                    item.model.set({
                        posX  : player.posX,
                        posY  : player.posY,
                        name  : player.name,
                        angle : player.direction,
                        score : player.score
                    });
                    this.set({'myPlayer': item});
                }
            }, this);
            this.set({'enemyPlayers': players});
        },
        erasePlayers: function() {
            if (this.get('myPlayer') != null) {
                this.get('myPlayer').$el.remove();
            }
            if (this.get('enemyPlayers') != []) {
                _.each(this.get('enemyPlayers'), function(player) {
                    player.$el.remove();
                });
            }
            this.set({'enemyPlayers': []});
            this.set({'myPlayer': null});
        },
        startGame: function() {
            /*console.log("enemies : ");
             console.log(enemies);
             character.model.setName(auth_user.getName());
             var time1 = Date.now();
             var time2 = Date.now() + 10000;
             var previous = Date.now();
             var dt;
             character.show();*/
            /*function loop() {
             var now = Date.now();
             //if (now > time1 && now < time2) {
             //    model.sendMessage();
             //    time1 += 5000;
             //    time2 += 5000;
             //}
             dt = (now - previous)/1000;
             console.log("dt : " + dt);
             character.model.myMove(dt);
             character.draw();
             previous = now;
             requestAnimationFrame(loop);
             }*/
            requestAnimationFrame(this.loop.bind(this));
        },
        loop: function() {
            this.get('myPlayer').model.myMove(0.02);
            this.get('myPlayer').draw();
            if (this.get('gameBegin')) {
                requestAnimationFrame(this.loop.bind(this));
            }
        },
        joinToRoom: function() {
            this.initializeSocket();
        },
        mouseMove: function(args) {
            if (this.get('myPlayer') != null) {
                this.get('myPlayer').model.setMouseCoordinate(args.x, args.y);
            }
        },
        exit: function() {
            console.log("exit from game");
            clearInterval(this.get('waitingInterval'));
            this.set({'gameBegin': false});
            if (this.get('socket')) {
                this.get('socket').close();
            }
            this.erasePlayers();
        }
    });
});
