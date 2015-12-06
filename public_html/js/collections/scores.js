define([
    'backbone',
    'models/score'
], function(
    Backbone,
    ScoreModel
){

    var Collection = Backbone.Collection.extend({
        url: "score?limit=10",
        model: ScoreModel,
        optionsFetch: ({
            success: function(collection, response, parse) {
                collection.models = response.scores;
                collection.trigger('change');
            },
            error: function(model, response, parse) {
                console.log("error score");
            }
        }),
        toJSON: function() {
            return this.models;
        }
    });
    return new Collection();
});