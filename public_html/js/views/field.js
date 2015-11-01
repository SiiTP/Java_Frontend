define([
    'backbone',
    'tmpl/game',
    '../models/field'
], function(
    Backbone,
    tmpl,
    Game
){

    var View = Backbone.View.extend({
        tagName: 'canvas',
        className: 'field',
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
            this.trigger('show');
        },
        startGame: function() {

            /*console.log("enemies : ");
            console.log(enemies);
            character.model.setName(auth_user.getName());
            var time1 = Date.now();
            var time2 = Date.now() + 10000;
            var previous = Date.now();
            var dt;
            character.show();*/
            /*function loop() {
                var now = Date.now();
                //if (now > time1 && now < time2) {
                //    model.sendMessage();
                //    time1 += 5000;
                //    time2 += 5000;
                //}
                dt = (now - previous)/1000;
                console.log("dt : " + dt);
                character.model.myMove(dt);
                character.draw();
                previous = now;
                requestAnimationFrame(loop);
            }*/
            requestAnimationFrame(this.loop.bind(this));
        },
        loop: function() {
            //console.log(this);
            //var previous = Date.now();
            //var dt;
            //var now = Date.now();
            //dt = (now - previous)/1000;
            this.model.myCharacter.model.myMove(0.02);
            this.model.myCharacter.draw();
            //previous = now;
            requestAnimationFrame(this.loop.bind(this));
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
    return View;
});