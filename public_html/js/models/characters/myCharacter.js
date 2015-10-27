define([
    'backbone',
    'models/characters/character'
], function(
    Backbone,
    CharacterModel
){

    var Model = CharacterModel.extend({
        mouseX: 0,
        mouseY: 0,
        /*initialize: function() {
            console.log("my character model initialize");
        },*/
        calculateAngle: function(x, y) {
            var angle = Math.atan((this.posY-y)/(x-this.posX));
            angle = angle * (180/Math.PI);
            if (x < this.posX) {
                angle += 180;
            }
            angle = (angle + 360) % 360;
            this.setAngle(angle);
            //console.log("angle : " + angle);
        },
        setMouseCoordinate: function(x, y) {
            this.mouseX = x;
            this.mouseY = y;
        },
        calculateDistanceToMouse: function(x, y) {
            return Math.sqrt((this.posX - x)*(this.posX - x) + (this.posY - y)*(this.posY - y));
        },
        myMove: function(dt) {
            //console.log("mouseX : " + this.mouseX + "; mouseY : " + this.mouseY);
            //console.log("MYMOVE : X : " + this.posX + "; Y :  " + this.posY + "; Angle : " + this.angle);
            if (this.calculateDistanceToMouse(this.mouseX, this.mouseY) > this.getRadius()) {
                this.move(dt);
            }
        },
        toJSON: function() {
            return {'posX': this.getX(), 'posY': this.getY(), 'angle': this.getAngle()}
        }
    });

    return Model;
});