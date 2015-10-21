define([
    'backbone'
], function(
    Backbone
){

    var View = Backbone.View.extend({
        radius: 20,
        color: '#ff0',
        borderColor: '#002',
        draw: function(context, character /*posX, posY, radius, color, name, gameScore*/) {
            console.log("character draw");
            var posX = character.model.getX();
            var posY = character.model.getY();
            var radius = character.radius;
            var color = character.color;
            var borderColor = character.borderColor;
            var name = character.model.getName();
            var score = character.model.getScore();
            //тень____________________
            context.shadowBlur = radius;
            context.shadowColor = color;
            //________________________
            //основной круг___________
            context.fillStyle = color;
            context.arc(posX, posY, radius, 0, 2*Math.PI, true);
            context.fill();
            //________________________
            //граница круга___________
            context.strokeStyle = borderColor;
            context.arc(posX, posY, radius, 0, 2*Math.PI, true);
            context.stroke();
            //________________________
            //имя_____________________
            context.shadowBlur = 18;
            context.shadowColor = '#f5f5f5';
            context.fillStyle = '#f5f5f5';
            context.font = 'normal 18px NunitoNormal';
            context.fillText(name, posX + radius + 5, posY + 9);
            //________________________
            //счет____________________
            context.shadowBlur = 18;
            context.shadowColor = '#f5f5f5';
            context.fillStyle = '#f5f5f5';
            context.font = 'normal 24px NunitoNormal';
            context.fillText(score, posX - radius - 25, posY + 9);
            //________________________
        },
        show: function() {
            this.$el.show();
        },
        hide: function() {
            this.$el.hide();
        }
    });
    return View;
});