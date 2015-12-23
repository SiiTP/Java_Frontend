define(['views/joystick'],function(joystick) {
    return function JoystickTransport(args) {
        this.address   = args.address;
        this.session   = args.session;
        this.constants = args.constants;

        this.angle = 1;
        this.socket = null;
        this.queryInterval = null;
        this.isMoving = false;

        this.initialize = function() {
            joystick.on("setAngle", this.onSetAngle.bind(this));
            joystick.on("stopMove", this.onStop.bind(this));
            joystick.on("startMove", this.onStart.bind(this));
            this.socket = new WebSocket(this.constants.get("SOCKET_HOST") + "/" + this.address);
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
        };

        this.onStop = function() {
            this.isMoving = false;
        };

        this.onStart = function() {
            this.isMoving = true;
        };

        this.setQueryInterval = function() {
            this.queryInterval = setInterval(this.sendMessage.bind(this), this.constants.get('INTERVAL_SHORT'));
        };

        this.sendMessage = function() {
            console.log("____send");
            var data = JSON.stringify({"direction": this.angle, "isMoving": this.isMoving, "session": this.session});
            this.socket.send(data);
        };

        this.exit = function() {
            console.log("joystick transport exit");
            clearInterval(this.queryInterval);
            this.socket.close();
        }

    }
});