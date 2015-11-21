define(['backbone'], function(Backbone) {
    Backbone.sync = function(method, model, options) {
        var url = model.url;
        console.log("<--- query : " + url + " CRUD : " + method);
        switch (method) {
            case "create":
                var data = model.toJSON();
                console.log(data);
                $.ajax({
                    type: "POST",
                    url: url + "/create",
                    data: data
                }).done(function(obj) {
                    console.log("SERVER ANSWER : ");
                    console.log(obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        location.href = "#login";
                    } else {
                        $(".registration__validation-info-common").text(answer.message);
                    }
                });
                break;
            case "read":
                $.ajax({
                    type: "POST",
                    url: url + "/read",
                    context: model
                }).done(function(obj) {
                    console.log("---> SERVER ANSWER : " + obj);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        this.set({id:1});
                        this.set({'username': answer.username});
                        this.set({'logged': true});
                        //TODO присваивать счет
                    }
                });
                break;
            case "update":
                var data = model.toJSON();
                console.log("data : " + JSON.stringify(data));
                $.ajax({
                    type: "POST",
                    url: url + "/update",
                    data: data
                }).done(function(obj) {
                    var answer = JSON.parse(obj);
                    console.log("---> login ");
                    console.log(answer);
                    if (answer.success) {
                        model.set({'logged': true});
                        model.set({'password': null});
                        model.trigger('toMain');
                    } else {
                        $(".login__validation-info-common").text(answer.message);
                    }
                    //TODO get user score from database in the future
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
                        model.uninitialize();
                        model.trigger('toMain');
                    } else {
                        console.log(answer.message);
                    }
                });
                break;
            default:
                console.log("unresolved operation");
                break;
        }
    };
});