define([
    'backbone',
    'tmpl/game',
    'views/characters/myCharacter'
], function(
    Backbone,
    tmpl,
    myCharacter
){

    var View = Backbone.View.extend({
        name: "game",
        tagName: 'div',
        myCharacter: myCharacter,
        enemyCharacters: [],
        template: tmpl,
        events: {
            'mousemove canvas.field': 'onMouseMove'
        },
        initialize: function () {
            console.log("game initialize");
            myCharacter.model.setX(100);
            myCharacter.model.setY(100);
            myCharacter.model.setName(auth_user.getName());
            myCharacter.model.setScore(0);
            this.render();
        },
        render: function () {
            console.log("game render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
        },
        show: function () {
            this.trigger('show',{'name' : this.name});
            console.log("before interaval");
            setTimeout(function(){console.log("in interval")}, 5000);
            console.log("after interaval");
            var fieldCanvas = $("canvas.field")[0];
            var context = fieldCanvas.getContext("2d");
            this.$el.show();
            myCharacter.draw(context, myCharacter);
            //console.log("X : " + myCharacter.model.posX + "; Y : " + myCharacter.model.posY);
            myCharacter.model.move();
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