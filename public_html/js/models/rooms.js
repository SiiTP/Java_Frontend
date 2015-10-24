define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        rooms: [],
        initialize: function() {
            console.log("rooms model initialize function");
            //TODO получить список комнат с серва
            this.rooms.push({'roomID': 1, 'name':'RoomFirst', 'players':5, 'maxPlayers':12});
            this.rooms.push({'roomID': 2, 'name':'TheBestRoom', 'players':0, 'maxPlayers':32});
            this.rooms.push({'roomID': 3, 'name':'Ivan-room', 'players':24, 'maxPlayers':24});
            this.rooms.push({'roomID': 4, 'name':'MyKomnata', 'players':19, 'maxPlayers':20});
        },
        getRooms: function() {
            return this.rooms;
        }
    });
    return Model;
});
