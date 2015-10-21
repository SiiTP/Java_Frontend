//форма регистрации
define([
    'backbone',
    'tmpl/registration',
    'models/registration'
], function(
    Backbone,
    tmpl,
    registration
){

    var View = Backbone.View.extend({
        name: "registration",
        tagName: 'div',
        template: tmpl,
        model: new registration(),
        events: {
            "click .registration__button": "onSubmit",
            "input .registration__input-line__input__username": "validateUsername",
            "input .registration__input-line__input__password": "validatePassword",
            "input .registration__input-line__input__confirm": "validateConfirm"
        },
        initialize: function() {
            console.log("registration initialize");
            this.render();
        },
        render: function() {
            console.log("registration render");
            this.$el.html(this.template());
            document.getElementById('page').appendChild(this.el);
        },
        validateUsername: function(event) {
            $(".validation-info-common").text("");
            //console.log("username validation. registration view");
            this.model.set({'username': $(event.currentTarget).val()}, {validate: true});
            var usernameInfoField = $(".registration__username__validation-info");
            var usernameInfoLine = $(".registration__username__line");
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
            this.model.set({'password':$(event.currentTarget).val()}, {validate: true});
            var passwordInfoField = $(".registration__password__validation-info");
            var passwordInfoLine = $(".registration__password__line");
            //console.log("pass status : " + this.model.passwordStatus.status + "; message : " + this.model.passwordStatus.message);
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
            this.updateConfirmView();
        },
        validateConfirm: function(event) {
            $(".validation-info-common").text("");
            //console.log("confirm validation. registration view. VALUE : " + $(event.currentTarget).val());
            this.model.set({'confirm':$(event.currentTarget).val()}, {validate: true});
            this.updateConfirmView();
        },
        //нужна чтобы корректно отображать валидацию поля повторения при изменении поля пароля
        updateConfirmView: function() {
            var confirmInfoField = $(".registration__confirm__validation-info");
            var confirmInfoLine = $(".registration__confirm__line");
            if (this.model.confirmStatus.status == 'empty') {
                confirmInfoField.removeClass("validation-info_error");
                confirmInfoField.removeClass("validation-info_correct");
                confirmInfoField.text(this.model.confirmStatus.message);
                confirmInfoLine.removeClass("line_red");
                confirmInfoLine.removeClass("line_green");
            }
            if (this.model.confirmStatus.status == 'error') {
                confirmInfoField.addClass("validation-info_error");
                confirmInfoField.removeClass("validation-info_correct");
                confirmInfoField.text(this.model.confirmStatus.message);
                confirmInfoLine.addClass("line_red");
                confirmInfoLine.removeClass("line_green");
            }
            if (this.model.confirmStatus.status == 'correct') {
                confirmInfoField.removeClass("validation-info_error");
                confirmInfoField.addClass("validation-info_correct");
                confirmInfoField.text(this.model.confirmStatus.message);
                confirmInfoLine.removeClass("line_red");
                confirmInfoLine.addClass("line_green");
            }
        },
        focusOnErrorField: function() {
            if (this.model.usernameStatus.status != 'correct') {
                $(".registration__input-line__input__username").focus();
            } else
            if (this.model.passwordStatus.status != 'correct') {
                $(".registration__input-line__input__password").focus();
            } else
            if (this.model.confirmStatus.status != 'correct') {
                $(".registration__input-line__input__confirm").focus();
            }
        },
        onSubmit: function() {
            console.log("on submit registration view");
            if (this.model.isValid()) {
                this.model.onSubmit();
            } else {
                $(".validation-info-common").text("Не все поля заданы корректно.");
                this.focusOnErrorField();
            }
        },
        show: function () {
            console.log("registration view show");
            //очистка старых значений моделей при прорисовке страницы
            //this.model.set({'username':undefined, 'password':undefined, 'confirm':undefined}, {validate:true});
            //this.$el.html(this.template());
            this.trigger('show', {'name' : this.name});
            this.$el.show();
        },
        hide: function () {
            console.log("registration view hide");
            this.$el.hide();
        }

    });

    return new View();
});