define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        url: "rooms",
        optionsFetch: ({
            success: function(model, response, parse) {
                model.set({"rooms": response.rooms});
            }
        }),
        optionsCreate: ({
            success: function(model, response, parse) {
                model.trigger('onGame');
            }
        }),
        optionsJoin: ({
            success: function(model, response, parse) {
                console.log("update onGame");
                model.trigger('onGame');
            }
        }),
        initialize: function() {
            this.set({'rooms': []});
            this.set({'roomName': null});
            this.set({'password': null});
        }
    });
    return new Model();
});
