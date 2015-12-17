define(['views/joystick'],function(joystick) {
    return function JoystickTransport(args) {
        this.address   = args.address;
        this.session   = args.session;
        this.constants = args.constants;
        this.angle = 1;
        this.socket = null;
        this.queryInterval = null;
        this.initialize = function() {
            joystick.on("setAngle", this.onSetAngle.bind(this));
            this.socket = new WebSocket("ws://localhost:8000/" + this.address);
            this.socket.onopen = function(event) {
                console.log("____ open socket");
                this.setQueryInterval();
            }.bind(this);
            this.socket.onmessage = function(event) {
                console.log("getted : ");
                console.log(event.data);
            }.bind(this);
            this.socket.onclose = function(event) {
                console.log("____ close socket");
            };
        };
        this.onSetAngle = function(data) {
            this.angle = data.angle;
            console.log("socket angle : " + this.angle);
        };
        this.setQueryInterval = function() {
            this.queryInterval = setInterval(this.sendMessage.bind(this), this.constants.get('INTERVAL_SHORT') - 10);
        };
        this.sendMessage = function() {
            var data = JSON.stringify({"direction": this.angle, "isMoving": true, "session": this.session});
            console.log("SENDED : ");
            console.log(data);
            this.socket.send(data);
        }

    }
});