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
            var x = event.pageX - constants.X_OFFSET_TO_CANVAS;
            var y = event.pageY - constants.Y_OFFSET_TO_CANVAS;
            this.model.calculateAngle(x, y);
            this.model.setMouseCoordinate(x, y);
            //console.log("mouse X : " + x + "; mouse Y : " + y);
            //console.log("mouse move event");
        }
    });
});