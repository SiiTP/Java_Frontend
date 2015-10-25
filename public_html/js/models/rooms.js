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
            //TODO получить список комнат с серва
            this.rooms.push({'roomID': 1, 'name':'RoomFirst', 'players':5, 'maxPlayers':12});
            this.rooms.push({'roomID': 2, 'name':'TheBestRoom', 'players':0, 'maxPlayers':32});
            this.rooms.push({'roomID': 3, 'name':'Ivan-room', 'players':24, 'maxPlayers':24});
            this.rooms.push({'roomID': 4, 'name':'MyKomnata', 'players':19, 'maxPlayers':20});
        },
        createJSON: function() {
            var data = {'name' : this.createName, 'password' : this.createPassword};
            return data;
        },
        onCreate: function() {
            console.log("on create rooms model");
            this.createName = $('#roomName').val();
            this.createPassword = $('#roomPassword').val();
            $.ajax({
                type: "POST",
                url: "/create",
                data: data
            }).done(function(obj) {

            });
        },
        getRooms: function() {

            return this.rooms;
        }
    });
    return Model;
});
