define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        room: [],
        initialize: function() {
            console.log("room model initialize function");
        }
    });
    return Model;
});
