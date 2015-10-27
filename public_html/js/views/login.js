// форма входа в уч. запись
define([
    'backbone',
    'tmpl/login',
    'models/login'
], function(
    Backbone,
    tmpl,
    login
){

    var View = Backbone.View.extend({
        name: "login",
        tagName: 'div',
        template: tmpl,
        model: new login(),
        events: {
            "click .login__buttons-container__button" : "onSubmit",
            "input .login__input-line__input__username": "validateUsername",
            "input .login__input-line__input__password": "validatePassword"
        },
        initialize: function () {
            console.log("login view initialize");
            this.render();
        },
        render: function () {
            console.log("login view render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
            this.$el.hide();
        },
        show: function () {
            console.log("login view show");
            //стираем старые данные, передаваемые на валидацию
            //this.model.set({'username': undefined, 'password': undefined}, {validate: true});
            //this.$el.html(this.template());
            this.trigger('show', {'name' : this.name});
            this.$el.show();
        },
        hide: function () {
            console.log("login view hide");
            this.$el.hide();
        },
        validateUsername: function(event) {
            $(".validation-info-common").text("");
            //console.log("login view validate username");
            this.model.set({'username': $(event.currentTarget).val()}, {validate: true});
            var usernameInfoField = $(".login__username__validation-info");
            var usernameInfoLine = $(".login__username__line");
            if (this.model.usernameStatus.status == 'empty') {
                usernameInfoField.removeClass("validation-info_error");
                usernameInfoField.removeClass("validation-info_correct");
                usernameInfoField.text(this.model.usernameStatus.message);
                usernameInfoLine.removeClass("line_red");
                usernameInfoLine.removeClass("line_green");
            }
            if (this.model.usernameStatus.status == 'error') {
                usernameInfoField.addClass("validation-info_error");
                usernameInfoField.removeClass("validation-info_correct");
                usernameInfoField.text(this.model.usernameStatus.message);
                usernameInfoLine.addClass("line_red");
                usernameInfoLine.removeClass("line_green");
            }
            if (this.model.usernameStatus.status == 'correct') {
                usernameInfoField.removeClass("validation-info_error");
                usernameInfoField.addClass("validation-info_correct");
                usernameInfoField.text(this.model.usernameStatus.message);
                usernameInfoLine.removeClass("line_red");
                usernameInfoLine.addClass("line_green");
            }
        },
        validatePassword: function(event) {
            $(".validation-info-common").text("");
            //console.log("login view validate password");
            this.model.set({'password': $(event.currentTarget).val()}, {validate: true});
            var passwordInfoField = $(".login__password__validation-info");
            var passwordInfoLine = $(".login__password__line");
            if (this.model.passwordStatus.status == 'empty') {
                passwordInfoField.removeClass("validation-info_error");
                passwordInfoField.removeClass("validation-info_correct");
                passwordInfoField.text(this.model.passwordStatus.message);
                passwordInfoLine.removeClass("line_red");
                passwordInfoLine.removeClass("line_green");
            }
            if (this.model.passwordStatus.status == 'error') {
                passwordInfoField.addClass("validation-info_error");
                passwordInfoField.removeClass("validation-info_correct");
                passwordInfoField.text(this.model.passwordStatus.message);
                passwordInfoLine.addClass("line_red");
                passwordInfoLine.removeClass("line_green");
            }
            if (this.model.passwordStatus.status == 'correct') {
                passwordInfoField.removeClass("validation-info_error");
                passwordInfoField.addClass("validation-info_correct");
                passwordInfoField.text(this.model.passwordStatus.message);
                passwordInfoLine.removeClass("line_red");
                passwordInfoLine.addClass("line_green");
            }
        },
        focusOnErrorField: function() {
            if (this.model.usernameStatus.status != 'correct') {
                $(".login__input-line__input__username").focus();
            } else
            if (this.model.passwordStatus.status != 'correct') {
                $(".login__input-line__input__password").focus();
            }

        },
        onSubmit: function(event) {
            event.preventDefault();
            console.log("backbone view login click event");
            if (this.model.isValid()) {
                console.log("data is valid");
                this.model.onSubmit();
            } else {
                console.log("data is not valid");
                $(".login__validation-info-common").text("Не все поля заданы корректно.");
                this.focusOnErrorField();
            }
        }
    });

    return new View();
});