define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        rooms: [],
        createName: undefined,
        createPassword: undefined,
        initialize: function() {
            console.log("rooms model initialize function");
        },
        createJSON: function() {
            var data = {'roomName' : this.createName, 'password' : this.createPassword};
            return data;
        },
        onCreate: function() {
            console.log("on create rooms model");
            this.createName = $('#roomName').val();
            this.createPassword = $('#roomPassword').val();
            $.ajax({
                type: "POST",
                url: "/create",
                data: this.createJSON()
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                console.log(answer.name);
                location.href = "#game";
            });
        },
        getRooms: function() {
            console.log("rooms model getRooms()");
            $.ajax({
                type: "POST",
                url: "/getRoomList",
                context: this
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                if (answer.status == 200) {
                     this.rooms = answer.rooms;
                } else {
                    this.rooms = null;
                }
            });
            return this.rooms;
        },
        onJoin: function(roomName) {
            console.log("name joining room : " + roomName);
            console.log("rooms model onJoin()");
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
    return Model;
});
