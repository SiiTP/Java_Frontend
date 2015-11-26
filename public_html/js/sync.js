define(['backbone'], function(Backbone) {
    Backbone.sync = function(method, model, options) {
        var url = model.url;
        console.log("<--- query : /" + url + " CRUD : " + method);
        switch (method) {
            case "create":
                var data = model.toJSON();
                console.log(JSON.stringify(data));
                $.ajax({
                    type: "POST",
                    url: url,
                    data: data
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    //TODO непродуманность АПИ
                    if (answer.status == 200) {
                        answer["success"] = true;
                    }
                    if (answer.success) {
                        options.success(answer);
                    } else {
                        options.error(answer);
                    }
                });
                break;

            case "read":
                $.ajax({
                    type: "GET",
                    url: url
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (!("success" in answer)) {
                        answer["success"] = answer.status == 200;
                    }
                    if (answer.success) {
                        options.success(answer);
                    } else {
                        options.error(answer);
                    }
                });
                break;

            case "update":
                var data = model.toJSON();
                console.log(JSON.stringify(data));
                $.ajax({
                    type: "PUT",
                    url: url,
                    data: data
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (!("success" in answer)) {
                        answer["success"] = answer.status == 200;
                    }
                    if (answer.success) {
                        options.success(answer);
                    } else {
                        options.error(answer);
                    }
                });
                break;

            case "delete":
                $.ajax({
                    type: "DELETE",
                    url: url
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