define(['backbone'], function(Backbone) {
    Backbone.sync = function(method, model, options) {
        var url = model.url;
        console.log("<--- query : /" + url + " CRUD : " + method);
        switch (method) {
            case "create":
                var data = model.toJSON();
                console.log(data);
                $.ajax({
                    type: "POST",
                    url: url + "/create",
                    data: data
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        options.success(answer);
                    } else {
                        options.error(answer.message);
                    }
                });
                break;

            case "read":
                $.ajax({
                    type: "POST",
                    url: url + "/read"
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        options.success(answer);
                    }
                });
                break;

            case "update":
                var data = model.toJSON();
                $.ajax({
                    type: "POST",
                    url: url + "/update",
                    data: data
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        options.success(answer);

                    } else {
                        options.error(answer.message);
                    }
                });
                break;

            case "delete":
                $.ajax({
                    type: "POST",
                    url: url + "/delete"
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        options.success(model, answer);
                    } else {
                        options.error(answer.message);
                    }
                });
                break;
            default:
                console.log("unresolved operation");
                break;
        }
    };
});