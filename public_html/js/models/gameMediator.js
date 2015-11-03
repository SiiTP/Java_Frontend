define (['backbone'], function(Backbone) {
    return Backbone.Model.extend({
        defaults: {
            field : null,
            players : [],
            roomsView : null, //поймать момент присоединения игрока к комнате и узнать размер комнаты
            maxPlayers : 1,
            socket: null,
            constants: null,
            gameBegin: false
        },
        initialize: function() {
            console.log("mediator initialized");
            this.get('roomsView').on('joiningToRoom', this.joinToRoom.bind(this));
            this.get('field').on('clicked', this.sendMessageWaiting.bind(this));
            //this.joinToRoom();
        },
        initializeSocket: function() {
            console.log("initializing socket");
            var socket = new WebSocket("ws://localhost:8000/gameplay");
            socket.onopen = function(event) {
                console.log("____ open socket");
                this.beginningGameWaiting();
            }.bind(this);
            socket.onmessage = function(event) {
                console.log("___> get message");
                console.log(event.data);
                if (event.data.status == 301) {
                    console.log("waiting answer");
                }
            };
            socket.onclose = function(event) {
                console.log("____ close socket");
            };
            this.set({'socket': socket});
        },
        joinToRoom: function(args) {
            this.initializeSocket();
        },
        beginningGameWaiting: function() {
            this.sendMessageWaiting();
        },
        sendMessageWaiting: function() {
            console.log("<___ send message waiting");
            var data = {'status': 100};
            this.get('socket').send(JSON.stringify(data));
        }
    });
});
