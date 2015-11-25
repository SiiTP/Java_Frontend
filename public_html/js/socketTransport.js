define(function() {
    var socket = new WebSocket("ws://localhost:8000/gameplay");//todo localhost в константы
    socket.onopen = function(event) {
        console.log("____ open socket");
    }.bind(this);
    socket.onmessage = function(event) {

    }.bind(this);
    socket.onclose = function(event) {
        console.log("____ close socket");
    };
    return socket;
});