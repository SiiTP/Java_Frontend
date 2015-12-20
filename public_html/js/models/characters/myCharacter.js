define([
    'backbone',
    'models/characters/character',
    'constants'
], function(
    Backbone,
    CharacterModel,
    constants
){

    return CharacterModel.extend({
        mouseX: 0,
        mouseY: 0,
        calculateAngle: function (x, y) {
            var angle = Math.atan((this.get('posY') - y) / (x - this.get('posX')));
            angle = angle * (180 / Math.PI);
            if (x < this.get('posX')) {
                angle += 180;
            }
            angle = (angle + 360) % 360;
            this.set({"angle": angle});
        },
        setMouseCoordinate: function (x, y) {
            this.mouseX = x - constants.get("X_OFFSET_TO_CANVAS");
            this.mouseY = y - constants.get("Y_OFFSET_TO_CANVAS");
            //console.log("setted mouseX : " + this.mouseX + "; mouseY : " + this.mouseY + "  offsetX : ");

        },
        calculateDistanceToMouse: function (x, y) {
            return Math.sqrt((this.get('posX') - x) * (this.get('posX') - x) + (this.get('posY') - y) * (this.get('posY') - y));
        },
        myMove: function (dt) {
            //console.log("MYMOVE : X : " + this.posX + "; Y :  " + this.posY + "; Angle : " + this.angle);
            //this.set({'isMoving': true});
            if (this.calculateDistanceToMouse(this.mouseX, this.mouseY) > this.get('radius')) {
                //this.calculateAngle(this.mouseX, this.mouseY);
                this.move(dt);
            } else {
                this.set({'isMoving': false});
            }
        }
    });
});