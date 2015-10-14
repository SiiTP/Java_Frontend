define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        username: undefined,
        password: undefined,
        usernameStatus: {"status":null, "message":null},
        passwordStatus: {"status":null, "message":null},
        patterns: {
            loginAvailableSymbols: '^[a-zA-Z]+$',
            //Пароль (Строчные и прописные латинские буквы, цифры, нижнее подчеркивание)
            passwordAvailableSymbols: '^[a-zA-Z0-9_]+$'
        },
        validators: {
            minLength: function (value, minLength) {
                return value.length >= minLength;
            },
            maxLength: function (value, maxLength) {
                return value.length <= maxLength;
            },
            correctLogin: function(str) {
                var expr = new RegExp(Model.prototype.patterns.loginAvailableSymbols);
                return expr.test(str);
            },
            correctPassword: function(str) {
                var expr = new RegExp(Model.prototype.patterns.passwordAvailableSymbols);
                return expr.test(str);
            }
        },
        initialize: function() {
            console.log("login model initialize function");
        },
        validate: function(attrs) {
            //console.log("login model validate function");
            if (attrs.username == "") {
                this.usernameStatus.status = "empty";
                this.usernameStatus.message = "Введите ваш логин.";
            }
            if (attrs.password == "") {
                this.passwordStatus.status = "empty";
                this.passwordStatus.message = "Введите ваш пароль.";
            }
            if (attrs.username) {
                var username = attrs.username;
                if (!Model.prototype.validators.correctLogin(username)) {
                    this.usernameStatus.status = "error";
                    this.usernameStatus.message = "В поле Username содержатся некорректные символы";
                } else
                if (!Model.prototype.validators.minLength(username, 4)) {
                    this.usernameStatus.status = "error";
                    this.usernameStatus.message = "Поле Username должно содержать минимум 4 символа";
                } else
                if (!Model.prototype.validators.maxLength(username, 9)) {
                    this.usernameStatus.status = "error";
                    this.usernameStatus.message = "Поле Username должно содержать максимум 9 символов";
                } else {
                    this.username = username;
                    this.usernameStatus.status = "correct";
                    this.usernameStatus.message = "Поле Username задано корректно";
                }
                if (this.usernameStatus.status != "correct") {
                    this.username = undefined;
                }
            }
            if (attrs.password) {
                var password = attrs.password;
                if (!Model.prototype.validators.correctPassword(password)) {
                    this.passwordStatus.status = "error";
                    this.passwordStatus.message = "В поле Password содержатся некорректные символы";
                } else
                if (!Model.prototype.validators.minLength(password, 4)) {

                    this.passwordStatus.status = "error";
                    this.passwordStatus.message = "Поле Password должно содержать минимум 4 символа";
                } else
                if (!Model.prototype.validators.maxLength(password, 16)) {
                    this.passwordStatus.status = "error";
                    this.passwordStatus.message = "Поле Password должно содержать максимум 16 символов";
                } else {
                    this.password = password;
                    this.passwordStatus.status = "correct";
                    this.passwordStatus.message = "Поле Password задано корректно";
                }
                if (this.passwordStatus.status != "correct") {
                    this.password = undefined;
                }
            }
        },
        isValid: function() {
            return this.usernameStatus.status == "correct" && this.passwordStatus.status == "correct";
        },
        onSubmit: function() {
            console.log("backbone model submit event");
            //var loginForm = $("#login-form");
            var data = this.toJSON();
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/signin",
                data: data
            }).done(function(obj) {
                console.log("SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    var loginData = {"username" : $("#username").val(), "password":$("#password").val()};
                    auth_user.name = loginData.username;
                    auth_user.password = loginData.password;
                    auth_user.logged = true;
                    location.href = "#main";
                } else {
                    $(".validation-info-common").text(answer.message);
                    //console.log(answer.message);
                }
                //TODO get user score from database in the future
            });
        }
    });
    return Model;
});
