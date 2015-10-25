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
        tagName: 'canvas',
        className: 'field',
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
            this.context = this.el.getContext('2d');
            this.myCharacter = new MyCharacter();
            this.$el.hide();
        },
        show: function () {
            console.log("game show");
            this.trigger('show',{'name' : this.name});
            this.el.width = constants.FIELD_WIDTH;
            this.el.height = constants.FIELD_HEIGHT;
            this.myCharacter.draw();
            var character = this.myCharacter;
            character.show();
            function loop() {
                character.model.myMove();
                character.draw();
                requestAnimationFrame(loop);
            }
            requestAnimationFrame(loop);
            this.$el.show();
        },
        hide: function () {
            console.log("game hide");
            this.el.width = 0;
            this.el.height = 0;
            this.myCharacter.hide();
            this.$el.hide();
        },
        onMouseMove: function(event) {
            //console.log("mouse move game");
            this.myCharacter.onMouseMove(event);
        }
    });
    return new View();
});