define([
    'backbone',
    'models/score'
], function(
    Backbone,
    scoreModel
){

    var Collection = Backbone.Collection.extend({
        model: scoreModel
    });

    return new Collection();
});