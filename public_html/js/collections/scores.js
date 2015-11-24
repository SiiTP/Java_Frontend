define([
    'backbone',
    'models/score'
], function(
    Backbone,
    ScoreModel
){

    var Collection = Backbone.Collection.extend({
        model: ScoreModel,
        initialize: function() {
            this.push(new this.model({'name': 'Ivan', 'score': 200}));
            this.push(new this.model({'name': 'Dima', 'score': 98}));
            this.push(new this.model({'name': 'Jenya', 'score': 120}));
            this.push(new this.model({'name': 'Masha', 'score': 10}));
            this.push(new this.model({'name': 'Victor', 'score': 50}));
            this.push(new this.model({'name': 'Oleg', 'score': 70}));
            this.push(new this.model({'name': 'Gena', 'score': 312}));
            this.push(new this.model({'name': 'Ylrich', 'score': 1120}));
            this.push(new this.model({'name': 'Fedor', 'score': 640}));
            this.push(new this.model({'name': 'Alena', 'score': 15}));
        },
        comparator: function(m) {
            return -m.get('score');
        }
    });
    return new Collection();
});