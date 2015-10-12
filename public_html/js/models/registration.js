define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
        username: undefined,
        password: undefined,
        confirm: undefined,
        usernameStatus: {"status":null, "message":null},
        passwordStatus: {"status":null, "message":null},
        confirmStatus: {"status":null, "message":null},
        events: {
            "sync": 'syncronize'
        },
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
            console.log("registration model initialize function ");
            this.attributes.username = undefined;
            this.attributes.password = undefined;
            this.attributes.confirm = undefined;
            this.attributes.usernameStatus = {"status":null, "message":null};
            this.attributes.passwordStatus = {"status":null, "message":null};
            this.attributes.confirmStatus = {"status":null, "message":null};
        },
        validate: function(attrs) {
            //console.log("model validate function. Attrs : ");
            //console.log(attrs);
            if (attrs.username == "") {
                this.usernameStatus.status = "empty";
                this.usernameStatus.message = "Введите ваш логин. От 4 до 9 латинских букв";
            }
            if (attrs.password == "") {
                this.passwordStatus.status = "empty";
                this.passwordStatus.message = "Введите ваш пароль. От 4 до 16 латинских букв, " +
                    "цифр и символов нижнего подчеркивания";
            }
            if (attrs.confirm == "") {
                this.confirmStatus.status = "empty";
                this.confirmStatus.message = "Повторите введенный пароль";
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
                    this.confirm = undefined;
                }
            }
            if (attrs.confirm) {
                if (attrs.confirm != attrs.password) {
                    this.confirmStatus.status = "error";
                    this.confirmStatus.message = "Пароли не совпадают";
                } else {
                    this.confirmStatus.status = "correct";
                    this.confirmStatus.message = "Подтверждение пароля корректно";
                    this.confirm = attrs.confirm;
                }
                if (this.confirmStatus.status != "correct") {
                    this.confirm = undefined;
                }
            }
        },
        isValid: function() {
            return this.username && this.password && this.confirm;
        },
        onSubmit: function() {
            var data = this.toJSON();
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/signup",
                data: data
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                if (answer.success) {
                    location.href = "#login";
                } else {
                    console.log(answer.message);
                    //TODO вывод на экран сообщения об ошибке на сервере
                }
            });
        }
    });
    return Model;
});
