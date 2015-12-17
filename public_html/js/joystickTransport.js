define(['views/joystick'],function(joystick) {
    return function JoystickTransport(args) {
        this.address = args.address;
        this.socket = null;
        this.initialize = function() {
            debugger;
            joystick.on("setAngle", function() {console.log("on socket event")});
            this.socket = new WebSocket("ws://localhost:8000/" + this.address);
            this.socket.onopen = function(event) {
                console.log("____ open socket");
            }.bind(this);
            this.socket.onmessage = function(event) {

            }.bind(this);
            this.socket.onclose = function(event) {
                console.log("____ close socket");
            };
        };
    }
});