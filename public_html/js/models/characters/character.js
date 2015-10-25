define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        posX: undefined,
        posY: undefined,
        radius: 20,
        name: undefined,
        gameScore: 500,
        speed: 3,
        angle: 0, //направление движения
        initialize: function() {
            this.setX(100);
            this.setY(100);
            this.setRadius(20);
            this.setName(auth_user.getName());
            this.setScore(5000);
        },
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
        getRadius: function() {
            return this.radius;
        },
        setRadius: function(r) {
            this.radius = r;
        },
        getSpeed: function() {
            return this.speed;
        },
        setSpeed: function(s) {
            this.speed = s;
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
            //console.log("X : " + Math.cos(this.angle*(Math.PI/180)));
            //console.log("Y : " + -Math.sin(this.angle*(Math.PI/180)));
            this.posX = this.posX + this.speed * Math.cos(this.angle*(Math.PI/180));
            this.posY = this.posY - this.speed * Math.sin(this.angle*(Math.PI/180));
        },
        info: function() {
            console.log("CHARACTER " + this.name + " INFO : ");
            console.log("position : (" + this.posX + ";" + this.posY + ")");
            console.log("radius : " + this.radius);
            console.log("direction angle : " + this.angle);
            console.log("=====================");
        }
    });

    return Model;
});