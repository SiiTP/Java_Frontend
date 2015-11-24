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
        model: new EnemyCharacterModel(),
    });
});