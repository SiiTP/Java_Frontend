define([
    'backbone',
    'views/characters/character',
    'models/characters/myCharacter'
], function(
    Backbone,
    CharacterView,
    MyCharacterModel
) {
    return CharacterView.extend({
        model: null,
        events: {
            'mousemove': 'onMouseMove'
        },
        initialize: function(args) {
            this.initCanvas(args);
            this.model = new MyCharacterModel();
        },
        onMouseMove: function(event) {
            var x = event.pageX;
            var y = event.pageY;
            this.model.setMouseCoordinate(x, y);
            this.model.calculateAngle(x, y);
            this.trigger("mouseMove")
        }
    });
});