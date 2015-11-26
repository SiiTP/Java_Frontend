define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        url: "rooms",
        optionsFetch: ({
            success: function(model, response, parse) {
                model.set({"rooms": response.rooms});
            }
        }),
        optionsCreate: ({
            success: function(model, response, parse) {
                model.trigger('onGame');
            }
        }),
        optionsJoin: ({
            success: function(model, response, parse) {
                console.log("update onGame");
                model.trigger('onGame');
            }
        }),
        initialize: function() {
            this.set({'rooms': []}) ;
            this.set({'roomName': null}) ;
            this.set({'password': null}) ;
        },
        //onCreate: function() {
        //    console.log("<--- create room");
        //    this.set({'roomName' : $('#roomName').val()});
        //    this.set({'password' : $('#roomPassword').val()});
        //    $.ajax({
        //        type: "POST",
        //        url: "/create",
        //        data: this.toJSON(),
        //        context: this
        //    }).done(function(obj) {
        //        var answer = JSON.parse(obj);
        //        if (answer.status == 200) {
        //            this.trigger('joiningToRoom', answer);
        //            location.href = "#game";
        //        } else {
        //            this.trigger('error', {'message': answer.message});
        //        }
        //    });
        //},
        //getRooms: function() {
        //    this.fetch();
        //    //console.log("<--- get room list");
        //    //$.ajax({
        //    //    type: "POST",
        //    //    url: "/getRoomList",
        //    //    context: this
        //    //}).done(function(obj) {
        //    //    console.log("---> get room list");
        //    //    var answer = JSON.parse(obj);
        //    //    console.log(answer);
        //    //    if (answer.status == 200) {
        //    //         this.set({'rooms' : answer.rooms});
        //    //    } else {
        //    //        this.set({'rooms' : []});
        //    //    }
        //    //});
        //},
        //onJoin: function(roomName) {
        //    console.log("<--- Join");
        //    $.ajax({
        //        type:'POST',
        //        url: '/join',
        //        data: {'roomName':roomName, 'password':null}, //TODO пароль отправляется как null
        //        context: this
        //    }).done(function(obj) {
        //        var answer = JSON.parse(obj);
        //        console.log("---> Join");
        //        console.log(answer);
        //        if (answer.status == 200) {
        //            location.href = '#game';
        //        } else {
        //            this.trigger('serverError', {'message': answer.message});
        //        }
        //    });
        //}
    });
    return new Model();
});
