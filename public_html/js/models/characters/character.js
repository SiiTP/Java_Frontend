define([
    'backbone'
], function(
    Backbone
){

    return Backbone.Model.extend({
        defaults: {
            posX: undefined,
            posY: undefined,
            radius: 20,
            name: undefined,
            gameScore: 500,
            speed: 200,
            angle: 0 //направление движения
        },
        move: function (dt) {
            //console.log("MOVE : X : " + this.get('posX') + "; Y :  " + this.get('posY') + "; Angle : " + this.get('angle'));
            //console.log("X : " + Math.cos(this.angle*(Math.PI/180)));
            //console.log("Y : " + -Math.sin(this.angle*(Math.PI/180)));
            //console.log(this.get('angle'));
            var dl = this.get('speed') * dt;
            //при отсутствии направления персонаж не двигается
            if (this.get('angle') >= 0) {
                this.set({'posX': this.get('posX') + Math.cos(this.get('angle') * (Math.PI / 180)) * dl});
                this.set({'posY': this.get('posY') - Math.sin(this.get('angle') * (Math.PI / 180)) * dl});
            }
        }
    });
});