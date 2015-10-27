define([
    'backbone',
    'tmpl/game',
    'models/game'
], function(
    Backbone,
    tmpl,
    Game
){

    var View = Backbone.View.extend({
        name: "game",
        tagName: 'canvas',
        className: 'field',
        context: undefined,
        model: new Game(),
        template: tmpl,
        events: {
            'mousemove canvas.field': 'onMouseMove'
        },
        initialize: function () {
            console.log("game initialize");
            this.render();
        },
        render: function () {
            console.log("game render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
            this.context = this.el.getContext('2d');
            this.$el.hide();
        },
        show: function () {
            console.log("game show");
            this.trigger('show',{'name' : this.name});
            this.el.width = constants.FIELD_WIDTH;
            this.el.height = constants.FIELD_HEIGHT;
            this.model.myCharacter.draw();
            this.startGame();
            this.$el.show();
        },
        startGame: function() {
            var character = this.model.myCharacter;
            //console.log("enemies : ");
            //console.log(enemies);
            character.model.setName(auth_user.getName());
            var time1 = Date.now();
            var time2 = Date.now() + 10000;
            var previous = Date.now();
            var dt;
            var model = this.model;
            character.show();
            function loop() {
                var now = Date.now();
                if (now > time1 && now < time2) {
                    model.sendMessage();
                    time1 += 5000;
                    time2 += 5000;
                }
                dt = (now - previous)/1000;
                character.model.myMove(dt);
                character.draw();
                previous = now;
                requestAnimationFrame(loop);
            }
            requestAnimationFrame(loop);
        },
        hide: function () {
            console.log("game hide");
            this.el.width = 0;
            this.el.height = 0;
            this.model.myCharacter.hide();
            this.$el.hide();
        },
        onMouseMove: function(event) {
            //console.log("mouse move game");
            this.myCharacter.onMouseMove(event);
        }
    });
    return new View();
});