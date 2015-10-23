define(['backbone'], function(Backbone) {
        var Constants = Backbone.Model.extend({
            //GAME FIELD SIZES__________
            FIELD_WIDTH: 1000,
            FIELD_HEIGHT: 700,
            //__________________________
            // OFFSETS BEFOR CANVAS_____
            X_OFFSET_TO_CANVAS: 20,
            Y_OFFSET_TO_CANVAS: 30
            //__________________________
        });
    constants = new Constants();
    return Constants;
    });