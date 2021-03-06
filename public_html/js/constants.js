define(['backbone'], function(Backbone) {
        var Constants = Backbone.Model.extend({
            defaults: {
                //GAME FIELD SIZES__________
                FIELD_WIDTH: 1300,
                FIELD_HEIGHT: 600,
                //__________________________
                // OFFSETS BEFORE CANVAS_____
                X_OFFSET_TO_CANVAS: 10,
                Y_OFFSET_TO_CANVAS: 20,
                //__________________________
                //INTERVALS_________________
                INTERVAL_SHORT: 50,
                INTERVAL_LARGE: 3000,
                //__________________________
                HOST: "localhost:8000",
                SOCKET_HOST: "ws://localhost:8000",
                SOCKET_ADDRESS: "/gameplay"
            }
        });
    return new Constants();
    });