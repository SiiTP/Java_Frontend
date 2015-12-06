define([
    'backbone',
    'views/characters/character',
    'models/characters/enemyCharacter'
], function(
    Backbone,
    CharacterView,
    EnemyCharacterModel
){

    return CharacterView.extend({
        //model: new EnemyCharacterModel()
        initialize: function(args) {
            //document.getElementById('page').appendChild(this.el);
            //this.canvas = this.el;
            //this.canvas.width = args.width;
            //this.canvas.height = args.height;
            //this.context = this.canvas.getContext('2d');
            this.initCanvas(args);
            this.model = new EnemyCharacterModel();
            //console.log("created enemy charater, cid : " + this.model.cid);
        }
    });
});