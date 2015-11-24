define([
    'backbone',
    'views/characters/myCharacter',
    'views/characters/enemyCharacter'
], function(
    Backbone,
    MyCharacter,
    EnemyCharacter
){
    var Model = Backbone.Model.extend({
        myCharacter: undefined,
        enemyCharacters: [],
        socket: null,
        initialize: function() {
            console.log("game model initialize function");
            this.myCharacter = new MyCharacter();

            this.socket = new WebSocket("ws://localhost:8000/gameplay");
            this.socket.onopen = function () {
                console.log("opening socket");
            };
            this.socket.onmessage = function (event) {
                console.log("getted message socket");
                _.each(this.enemyCharacters, function(item) {
                    console.log("hiding : ");
                    console.log(item.name);
                    console.log("---------");
                    item.hide();
                });
                var enemies = [];
                var json = JSON.parse(event['data']);
                console.log("ANSWER : ");
                console.log(json);
                _.each(json.players, function(item) {
                    console.log("name : " + item.name);
                });
                _.each(json.players, function(item) {
                    var enemy = new EnemyCharacter();
                    enemy.model.parseAnswer(item);
                    console.log("COMARE : " + enemy.model.getName() + " AND " + auth_user.getName());
                    if (enemy.model.getName() != auth_user.getName()) {
                        enemies.push(enemy);
                    }
                });
                this.enemyCharacters = enemies;

                _.each(this.enemyCharacters, function(item) {
                    console.log("drawing : ");
                    console.log(item.name);
                    item.show();
                    item.draw();
                    console.log("---------");
                });
                console.log("ENEMIES : ");
                console.log(this.enemyCharacters);
                /*var mesg = json['message'];
                if (mesg == null) {
                    mesg = json['players'];
                    if (mesg == null) {
                        var arr = json['players'];
                        arr.forEach(function (i) {
                            if (i['name'] === user) {
                                mesg = i['score'];
                            }
                        });
                    }
                }*/
            };
            this.socket.onclose = function () {
                console.log("closing socket");
            };
        },
        sendMessage: function () {
            var data = this.myCharacter.model.toJSON();
            console.log("sending websocket message : ");
            console.log(data);
            var message = {name:'ivan', 'player': data};
            this.socket.send(JSON.stringify(message));
        },
        getOtherCharacters: function() {

        }
    });
    return Model;
});
