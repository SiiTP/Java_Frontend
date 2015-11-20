define(['backbone'], function(Backbone) {
    console.log("!");
    Backbone.sync = function(method, model, options) {
        console.log("context after fetch : ");
        console.log(this);
        console.log(model);
        switch (method) {
            case "create":
                console.log("<-- creating query");
                break;
            case "read":
                console.log("---> fetch user model : " + this.username);
                $.ajax({
                    type: "POST",
                    url: model.url + "/logininfo",
                    context: model
                }).done(function(obj) {
                    console.log("SERVER ANSWER : " + obj);
                    console.log("context in ajax : ");
                    console.log(this);
                    var answer = JSON.parse(obj);
                    if (answer.success) {
                        //TODO если ответ от аякса приходит позже отрисовки, отрисовываются данные без учета ответа
                        this.set({'username': answer.username});
                        this.set({'logged': true});
                        //TODO присваивать счет
                    }
                });
                break;
            case "update":
                console.log("<-- updating query");
                break;
            case "delete":
                console.log("<-- deleting query");
                break;
            default:
                console.log("unresolved operation");
                break;
        }
    };
});