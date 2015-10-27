define([
    'backbone',
    'models/characters/character'
], function(
    Backbone,
    CharacterModel
){

    var Model = CharacterModel.extend({
        toJSON: function() {
            return {'posX': this.getX(), 'posY': this.getY(), 'angle': this.getAngle()}
        },
        parseAnswer: function(item) {
            this.setX(item.x);
            this.setY(item.y);
            this.setScore(item.score);
            this.info();
            this.setName(item.name);
        }
    });

    return Model;
});