define(['backbone'], function(Backbone) {
        var Constants = Backbone.Model.extend({
            defaults: {
                //GAME FIELD SIZES__________
                FIELD_WIDTH: 1000,
                FIELD_HEIGHT: 700,
                //__________________________
                // OFFSETS BEFORE CANVAS_____
                X_OFFSET_TO_CANVAS: 10,
                Y_OFFSET_TO_CANVAS: 20,
                //__________________________
                //INTERVALS_________________
                INTERVAL_SHORT: 50,
                INTERVAL_LARGE: 3000,
                //__________________________
                SOCKET_ADDRESS: "ws://localhost:8000/gameplay"
            }
        });
    return new Constants();
    });