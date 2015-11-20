define([
    'backbone'
], function(
    Backbone
){
    var Model = Backbone.Model.extend({
        url: "",
        initialize: function() {
            this.set({'username'    : null});
            this.set({'password'    : null});
            this.set({'logged'      : false});
            this.set({validUsername : false});
            this.set({validPassword : false});
            this.set({'score'       : 0});
            console.log("context before fetch : ");
            console.log(this);
            this.fetch();
        },
        patterns: {
            loginAvailableSymbols: '^[a-zA-Z]+$',
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
        validate: function(attrs) {
            var error = [];
            if (attrs.username == "") {
                error.push({
                    'field':'username',
                    'status': 'empty',
                    'message': "Введите ваш логин. От 4 до 9 латинских букв"
                });
            }
            if (attrs.password == "") {
                error.push({
                    'field':'password',
                    'status': 'empty',
                    'message': "Введите ваш пароль. От 4 до 16 латинских букв, цифр и символов нижнего подчеркивания"
                });
            }
            if (attrs.username) {
                if (!Model.prototype.validators.correctLogin(attrs.username)) {
                    error.push({
                        'field':'username',
                        'status': 'error',
                        'message': "В поле Username содержатся некорректные символы"
                    });
                } else
                if (!Model.prototype.validators.minLength(attrs.username, 4)) {
                    error.push({
                        'field':'username',
                        'status': 'error',
                        'message': "Поле Username должно содержать минимум 4 символа"
                    });
                } else
                if (!Model.prototype.validators.maxLength(attrs.username, 9)) {
                    error.push({
                        'field': 'username',
                        'status': 'error',
                        'message': "Поле Username должно содержать максимум 9 символов"
                    });
                }
            }
            if (attrs.password) {
                if (!Model.prototype.validators.correctPassword(attrs.password)) {
                    error.push({
                        'field': 'password',
                        'status': 'error',
                        'message': "В поле Password содержатся некорректные символы"
                    });
                } else
                if (!Model.prototype.validators.minLength(attrs.password, 4)) {
                    error.push({
                        'field': 'password',
                        'status': 'error',
                        'message': "Поле Password должно содержать минимум 4 символа"
                    });
                } else
                if (!Model.prototype.validators.maxLength(attrs.password, 16)) {
                    error.push({
                        'field': 'password',
                        'status': 'error',
                        'message': "Поле Password должно содержать максимум 16 символов"
                    });
                }
            }
            if (error.length != 0) {
                return error;
            }
        },
        isValid: function() {
            return this.get('validUsername') && this.get('validPassword')
        },
        onLogout: function() {
            console.log("---> logout");
            $.ajax({
                type: "POST",
                url: "/logout",
                context: this
            }).done(function(obj) {
                console.log("<--- SERVER ANSWER : " + obj);
                var answer = JSON.parse(obj);
                if (answer.success) {
                    this.uninitialize();
                    this.trigger('toMain');
                } else {
                    console.log(answer.message);
                }
            });
        },
        onLogin: function() {
            console.log("---> login");
            var data = this.toJSON();
            $.ajax({
                type: "POST",
                url: "/signin",
                data: data,
                context: this
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                console.log("<--- login ");
                console.log(answer);
                if (answer.success) {
                    this.set({'logged': true});
                    this.set({'password': null});
                    this.trigger('toMain');
                } else {
                    $(".login__validation-info-common").text(answer.message);
                }
                //TODO get user score from database in the future
            });
        },
        onRegistration: function() {
            var data = this.toJSON();
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/signup",
                data: data
            }).done(function(obj) {
                var answer = JSON.parse(obj);
                console.log("SERVER ANSWER : ");
                console.log(answer);
                if (answer.success) {
                    location.href = "#login";
                } else {
                    //TODO надо выводить через вьюху, наверное надо хранить объект вьюхи в моделе
                    $(".registration__validation-info-common").text(answer.message);
                }
            });
        },
        uninitialize: function() {
            console.log("user model uninitialize");
            this.set({'username'      : null});
            this.set({'password'      : null});
            this.set({'validUsername' : false});
            this.set({'validPassword' : false});
            this.set({'logged'        : false});
            this.set({'score'         : 0});
        }
    });
    return new Model();
});