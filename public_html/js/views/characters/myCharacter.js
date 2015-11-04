define([
    'backbone',
    'views/characters/character',
    'models/characters/myCharacter'
], function(
    Backbone,
    CharacterView,
    MyCharacterModel
){

    return CharacterView.extend({
        model: new MyCharacterModel(),
        events: {
            'mousemove': 'onMouseMove'
        },
        onMouseMove: function (event) {
            var x = event.pageX;
            var y = event.pageY;
            this.model.calculateAngle(x, y);
            this.model.setMouseCoordinate(x, y);
            //console.log("mouse X : " + x + "; mouse Y : " + y);
            //console.log("mouse move event");
        }
    });
});