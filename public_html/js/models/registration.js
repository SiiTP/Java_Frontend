define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        username: "user",
        password: "user",
        onSubmit: function() {
            var data =  $("#registration-form").serialize();
            //data.remove("confirm");
            console.log("backbone model submit event " + data + " ||");
            $.ajax({
                type: "POST",
                url: "/signup",
                data: data
            }).done(function(obj) {
                console.log("OBJECT : " + obj);
            });
        }
    });
    return Model;
});
