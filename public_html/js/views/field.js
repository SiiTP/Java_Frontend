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

        gameStage: 0, //0-нет, 1-ожидание, 2-игра, 3-победитель

        events: {
            "click .field-container__button-exit": 'onExit'
        },
        show: function () {
            this.$el.show();

            this.on('win', this.onWin);
            this.on('waiting', this.onWaiting);
            this.on('gameProccess', this.onGameProccess);

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
            this.gameStage = 0;
            this.trigger('exit');
            this.$el.hide();
        },

        drawMessage: function(message) {
            this.context.beginPath();
            this.context.shadowBlur = 32;
            this.context.shadowColor = '#f5f5f5';
            this.context.fillStyle = '#f5f5f5';
            this.context.font = 'normal 32px NunitoNormal';
            this.context.fillText(message, 20, 40);
        },

        clearField: function() {
            this.context.clearRect(0, 0, this.options.width, this.options.height);
        },

        onWaiting: function() {
            if (this.gameStage != 1) {
                this.clearField();
                this.drawMessage("Подождите второго игрока, пожалуйста");
                this.gameStage = 1;
            }
        },

        onGameProccess: function() {
            if (this.gameStage != 2) {
                this.clearField();
                //this.drawMessage("Игра началась");
                this.gameStage = 2;
            }
        },

        onWin: function(winner) {
            if (this.gameStage != 3) {
                this.clearField();
                this.drawMessage("Победитель игры : " + winner);
                this.gameStage = 3;
            }
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