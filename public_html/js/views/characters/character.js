define([
    'backbone'
], function(
    Backbone
){

    var View = Backbone.View.extend({
        tagName: 'canvas',
        className: 'character',
        color: '#ff0',
        borderColor: '#002',
        context: undefined,
        initialize: function() {
            console.log("character view initialize");
            document.getElementById('page').appendChild(this.el);
            var canvas = this.el;
            this.context = canvas.getContext('2d');
        },
        draw: function() {
            this.clear();
            //console.log("character draw");
            var posX = this.model.getX();
            var posY = this.model.getY();
            var radius = this.model.getRadius();
            var color = this.color;
            var borderColor = this.borderColor;
            var name = this.model.getName();
            var score = this.model.getScore();

            this.context.beginPath();
            //тень____________________
            //context.shadowBlur = radius;
            //context.shadowColor = color;
            //________________________
            //основной круг___________
            this.context.fillStyle = color;
            this.context.arc(posX, posY, radius, 0, 2*Math.PI, true);
            this.context.fill();
            //________________________
            //граница круга___________
            this.context.strokeStyle = borderColor;
            this.context.arc(posX, posY, radius, 0, 2*Math.PI, true);
            this.context.stroke();
            //________________________
            //имя_____________________
            //context.shadowBlur = 18;
            //context.shadowColor = '#f5f5f5';
            this.context.fillStyle = '#f5f5f5';
            this.context.font = 'normal 18px NunitoNormal';
            this.context.fillText(name, posX + radius + 5, posY - 3);
            //________________________
            //счет____________________
            this.context.font = 'normal 24px NunitoNormal';
            this.context.fillText(score, posX + radius +5, posY + 18);
            //________________________
        },
        clear: function() {
            this.context.beginPath();
            var x = this.model.getX();
            var y = this.model.getY();
            var r = this.model.getRadius();
            var s = this.model.getSpeed();
            //this.context.fillStyle = '#555';
            this.context.clearRect(x - r * 2, y - r * 2, 6 * r + 80, 6 * r);
        },
        show: function() {
            this.el.width = constants.FIELD_WIDTH;
            this.el.height = constants.FIELD_HEIGHT;
            this.$el.show();
        },
        hide: function() {
            this.el.width = 0;
            this.el.height = 0;
            this.$el.hide();
        }
    });
    return View;
});