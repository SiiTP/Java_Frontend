define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        canvas: null,
        context: null,
        events: {
            "click .field__button-exit": 'onExit',
            "click .field": 'onClick',
            "mousemove .field": 'onMouseMove'
        },
        show: function () {
            this.$el.show();
            this.trigger('show');
            this.canvas = $('.field')[0];
            this.canvas.width  = 1000;
            this.canvas.height = 700;
            this.context = this.canvas.getContext('2d');
            // тут можно нарисовать бекграунд
        },
        hide: function () {
            if (this.canvas) {
                this.canvas.width  = 0;
                this.canvas.height = 0;
            }
            //this.trigger('exit');
            this.$el.hide();
        },
        onMouseMove: function(event) {
            this.trigger('mouseMove', {'x' : event.pageX, 'y' : event.pageY});
        },
        onClick: function() {
            this.trigger('clicked');
        },
        onExit: function() {
            this.trigger('exit');
            location.href = "#rooms";
        }
    });
    return View;
});