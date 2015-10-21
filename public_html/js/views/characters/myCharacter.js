define([
    'backbone',
    'views/characters/character',
    'models/characters/myCharacter'
], function(
    Backbone,
    CharacterView,
    MyCharacterModel
){

    var View = CharacterView.extend({
        model: new MyCharacterModel(),
        events: {
            'mousemove': 'onMouseMove'
        },
        initialize: function() {
            console.log("my character view initialize");
        },
        onMouseMove: function(event) {
            var x = event.pageX - constants.X_OFFSET_TO_CANVAS;
            var y = event.pageY - constants.Y_OFFSET_TO_CANVAS;
            this.model.calculateAngle(x,y);
            //console.log("mouse X : " + x + "; mouse Y : " + y);
            //console.log("mouse move event");
        }
    });
    return new View();
});