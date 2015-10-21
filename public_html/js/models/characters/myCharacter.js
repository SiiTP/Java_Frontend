define([
    'backbone',
    'models/characters/character'
], function(
    Backbone,
    CharacterModel
){

    var Model = CharacterModel.extend({
        initialize: function() {
            console.log("my character model initialize");
        },
        calculateAngle: function(x, y) {
            var angle = Math.atan((this.posY-y)/(x-this.posX));
            angle = angle * (180/Math.PI);
            if (x < this.posX) {
                angle += 180;
            }
            angle = (angle + 360) % 360;
            this.setAngle(angle);
            console.log("angle : " + angle);

        }
    });

    return Model;
});