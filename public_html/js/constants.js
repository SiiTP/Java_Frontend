define(['backbone'], function(Backbone) {
        var Constants = Backbone.Model.extend({
            defaults: {
                //GAME FIELD SIZES__________
                FIELD_WIDTH: 1000,
                FIELD_HEIGHT: 700,
                //__________________________
                // OFFSETS BEFORE CANVAS_____
                X_OFFSET_TO_CANVAS: 20,
                Y_OFFSET_TO_CANVAS: 30
                //__________________________
            }
        });
    return new Constants();
    });