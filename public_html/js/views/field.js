define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: 'canvas',
        className: 'field',
        template: tmpl,
        show: function () {
            console.log("field show");
            this.$el.show();
            this.$el.on('click', this.onClick.bind(this));
            this.$el.on('mousemove', this.onMouseMove.bind(this));
            this.trigger('show');
            //this.startGame();
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
            //previous = now;
            requestAnimationFrame(this.loop.bind(this));
        },
        hide: function () {
            console.log("game hide");
            this.$el.hide();
        },
        onMouseMove: function(event) {
        },
        onClick: function() {
            this.trigger('clicked');
        }
    });
    return View;
});