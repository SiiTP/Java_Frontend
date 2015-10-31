define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        rooms: [],
        onCreate: function() {
            console.log("---> create room");
            console.log("on create rooms model");
            this.set({'createName' : $('#roomName').val()});
            this.set({'password' : $('#roomPassword').val()});
            $.ajax({
                type: "POST",
                url: "/create",
                data: this.toJSON()
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                console.log(answer);
                location.href = "#game";
            });
        },
        fetch: function() {
            console.log("---> get room list");
            $.ajax({
                type: "POST",
                url: "/getRoomList",
                context: this
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                debugger;
                if (answer.status == 200) {
                     this.rooms = answer.rooms;
                } else {
                    this.rooms = null;
                }
            });
            return this.rooms;
        },
        onJoin: function(roomName) {
            console.log("---> Join");
            $.ajax({
                type:'POST',
                url: '/join',
                data: {'roomName':roomName, 'password':null} //TODO пароль отправляется как null
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                console.log(answer);
                location.href = '#game';
            });
        }
    });
    return new Model();
});
