define([
    'backbone'
], function(
    Backbone
){

    return Backbone.View.extend({
        tagName: 'canvas',
        color: '#f90',
        borderColor: '#002',
        context: null,
        canvas: null,

        initCanvas: function(args) {
            document.getElementById('page').appendChild(this.el);
            this.canvas = this.el;
            this.canvas.width = args.width;
            this.canvas.height = args.height;
            this.context = this.canvas.getContext('2d');
        },

        draw: function() {
            if (this.context != null) {
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
                this.context.shadowBlur = radius;
                this.context.shadowColor = color;
                //________________________
                //основной круг___________
                this.context.fillStyle = color;
                this.context.arc(posX, posY, radius, 0, 2 * Math.PI, true);
                this.context.fill();
                //________________________
                //граница круга___________
                this.context.strokeStyle = borderColor;
                this.context.arc(posX, posY, radius, 0, 2 * Math.PI, true);
                this.context.stroke();
                //________________________
                //имя_____________________
                this.context.shadowBlur = 18;
                this.context.shadowColor = '#f5f5f5';
                this.context.fillStyle = '#f5f5f5';
                this.context.font = 'normal 18px NunitoNormal';
                this.context.fillText(name, posX + radius + 5, posY - 3);
                //________________________
                //счет____________________
                this.context.font = 'normal 24px NunitoNormal';
                this.context.fillText(score, posX + radius + 5, posY + 18);
                //________________________
            }
        },

        clear: function() {
            //посреди игрового цикла может обнулиться
            if (this.context != null) {
                this.context.beginPath();
                var x = this.model.get('posX');
                var y = this.model.get('posY');
                var r = this.model.get('radius');
                var s = this.model.get('speed');
                this.context.clearRect(x - r * 2 - 20, y - r * 2 - 30, 6 * r + 100, 6 * r + 30);
            }
        },

        clearAll: function(fieldWidth, fieldHeight) {
            this.context.clearRect(0, 0, fieldWidth, fieldHeight);
        },

        deleteCanvas: function() {
            if (this.el.parentNode != null) {
                document.getElementById("page").removeChild(this.el);
            }
            this.canvas = null;
            this.context = null;
        }
    });
});