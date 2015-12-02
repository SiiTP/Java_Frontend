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
            document.getElementById('page').appendChild(this.el);
            this.canvas = this.el;
            this.canvas.width = args.width;
            this.canvas.height = args.height;
            this.context = this.canvas.getContext('2d');
            this.model = new MyCharacterModel();
            //console.log("created player charater, cid : " + this.model.cid);
        },
        onMouseMove: function(event) {
            var x = event.pageX;
            var y = event.pageY;
            this.model.calculateAngle(x, y);
            this.model.setMouseCoordinate(x, y);
        }
    });
});