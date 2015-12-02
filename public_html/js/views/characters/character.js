define([
    'backbone'
], function(
    Backbone
){

    var View = Backbone.View.extend({
        tagName: 'canvas',
        color: '#ff0',
        borderColor: '#002',
        context: null,
        canvas: null,
        //initialize: function(args) {
        //    document.getElementById('page').appendChild(this.el);
        //    this.canvas = this.el;
        //    this.canvas.width = args.width;
        //    this.canvas.height = args.height;
        //    this.context = this.canvas.getContext('2d');
        //},
        draw: function() {
            this.clear();
            var posX = this.model.get('posX');
            var posY = this.model.get('posY');
            var radius = this.model.get('radius');
            var color = this.color;
            var borderColor = this.borderColor;
            var name = this.model.get('name');
            var score = this.model.get('score');
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
            var x = this.model.get('posX');
            var y = this.model.get('posY');
            var r = this.model.get('radius');
            var s = this.model.get('speed');
            //this.context.fillStyle = '#555';
            this.context.clearRect(x - r * 2, y - r * 2, 6 * r + 80, 6 * r);
        },
        show: function() {
            // TODO задать константы для размеров
            this.el.width = 1000;
            this.el.height = 700;
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