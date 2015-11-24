define([
    'backbone',
    'models/characters/character'
], function(
    Backbone,
    CharacterModel
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
            this.set({'angle': angle});
            //console.log("angle : " + angle);
        },
        setMouseCoordinate: function (x, y) {
            console.log(x + " : " + y);
            this.mouseX = x;
            this.mouseY = y;
        },
        calculateDistanceToMouse: function (x, y) {
            return Math.sqrt((this.get('posX') - x) * (this.get('posX') - x) + (this.get('posY') - y) * (this.get('posY') - y));
        },
        myMove: function (dt) {
            //console.log("mouseX : " + this.mouseX + "; mouseY : " + this.mouseY);
            //console.log("MYMOVE : X : " + this.posX + "; Y :  " + this.posY + "; Angle : " + this.angle);
            if (this.calculateDistanceToMouse(this.mouseX, this.mouseY) > this.get('radius')) {
                this.calculateAngle(this.mouseX, this.mouseY);
                this.move(dt);
            } else {
                this.set({'angle': -1});
            }
        }
    });
});