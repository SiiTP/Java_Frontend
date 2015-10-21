define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        posX: undefined,
        posY: undefined,
        name: undefined,
        gameScore: 0,
        speed: 1,
        angle: 0, //направление движения
        getX: function() {
            return this.posX;
        },
        getY: function() {
            return this.posY;
        },
        setX: function(x) {
            this.posX = x;
        },
        setY: function(y) {
            this.posY = y;
        },
        getName: function() {
            return this.name;
        },
        setName: function(n) {
            this.name = n;
        },
        getScore: function() {
            return this.gameScore;
        },
        setScore: function(s) {
            this.gameScore = s;
        },
        setAngle: function(a) {
            this.angle = a;
        },
        move: function() {
            //console.log("MOVE : X : " + this.posX + "; Y : " + this.posX + "; Angle : " + this.angle);
            console.log("X : " + Math.cos(this.angle*(Math.PI/180)));
            console.log("Y : " + -Math.sin(this.angle*(Math.PI/180)));
            this.posX = this.posX + this.speed * Math.cos(this.angle*(Math.PI/180));
            this.posY = this.posY - this.speed * Math.sin(this.angle*(Math.PI/180));
        }
    });

    return Model;
});