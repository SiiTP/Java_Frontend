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
            "click .field-container__button-exit": 'onExit'
        },
        show: function () {
            this.$el.show();
            this.on('win', this.onWin);
            this.trigger('show');
            this.canvas = this.$('.field')[0];
            this.canvas.width  = this.options.width;
            this.canvas.height = this.options.height;
            this.context = this.canvas.getContext('2d');
        },
        hide: function () {
            if (this.canvas) {
                this.canvas.width  = 0;
                this.canvas.height = 0;
            }
            this.trigger('exit');
            this.$el.hide();
        },
        onWin: function(winner) {
            // TODO Вывод победителя
            //debugger;
        },
        onExit: function() {
            //this.trigger('exit');
            location.href = "#rooms";
        }
    });
    return View;
});