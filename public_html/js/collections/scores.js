define([
    'backbone',
    'models/score'
], function(
    Backbone,
    scoreModel
){

    var Collection = Backbone.Collection.extend({
        model: scoreModel,
        comparator: function(m) {
            return -m.get('score');
        }
    });

    return new Collection();
});