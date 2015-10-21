define(['backbone'], function(Backbone) {
        var Constants = Backbone.Model.extend({
            // OFFSETS BEFOR CANVAS_____
            X_OFFSET_TO_CANVAS: 20,
            Y_OFFSET_TO_CANVAS: 30
            //__________________________
        });
    constants = new Constants();
    return Constants;
    });