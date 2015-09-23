define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        name: "def_name",
        score: 0
    });

    return Model;
});