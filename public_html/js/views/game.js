define([
    'backbone',
    'tmpl/game',
    'views/characters/myCharacter'
], function(
    Backbone,
    tmpl,
    MyCharacter
){

    var View = Backbone.View.extend({
        name: "game",
        tagName: 'div',
        context: undefined,
        myCharacter: undefined,
        enemyCharacters: [],
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
            this.myCharacter = new MyCharacter();
            this.$el.hide();
        },
        show: function () {
            this.trigger('show',{'name' : this.name});
            var canva = $('canvas.field')[0];
            this.context = canva.getContext('2d');
            canva.width = constants.FIELD_WIDTH;
            canva.height = constants.FIELD_HEIGHT;
            //this.myCharacter.model.info();
            this.myCharacter.draw();
            var character = this.myCharacter;
            //this.context.fillRect(0, 0, 300, 300);
            //this.context.fill();
            function loop() {
                character.model.myMove();
                character.draw();
                requestAnimationFrame(loop);
            }
            requestAnimationFrame(loop);
            this.$el.show();
            //console.log("X : " + myCharacter.model.posX + "; Y : " + myCharacter.model.posY);
                //setTimeout(alert('!'), 10000)
        },
        hide: function () {
            this.$el.hide();
        },
        onMouseMove: function(event) {
            //console.log("mouse move game");
            this.myCharacter.onMouseMove(event);
        }
    });
    return new View();
});