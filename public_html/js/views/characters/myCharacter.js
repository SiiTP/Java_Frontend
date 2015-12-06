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
            //document.getElementById('page').appendChild(this.el);
            //this.canvas = this.el;
            //this.canvas.width = args.width;
            //this.canvas.height = args.height;
            //this.context = this.canvas.getContext('2d');
            this.initCanvas(args);
            this.model = new MyCharacterModel();
        },
        onMouseMove: function(event) {
            var x = event.pageX;
            var y = event.pageY;
            this.model.setMouseCoordinate(x, y);
            this.model.calculateAngle(x, y);
        }
    });
});