//форма регистрации
define([
    'backbone',
    'tmpl/registration'
], function(
    Backbone,
    tmpl
){

    return Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        events: {
            "click .registration__button": "onSubmit",
            "input .registration__input-field-username__input-line__input": "validateUsername",
            "input .registration__input-field-password__input-line__input": "validatePassword",
            "input .registration__input-field-confirm__input-line__input": "validateConfirm"
        },
        JQ_cashing: function () {
            if (this.JQ_cacheInput == null) {
                this.JQ_cacheInput = {
                    'username': $(".registration__input-field-username__input-line__input"),
                    'password': $(".registration__input-field-password__input-line__input"),
                    'confirm': $(".registration__input-field-confirm__input-line__input")
                };
            }
            if (this.JQ_cacheText == null) {
                this.JQ_cacheText = {
                    'username': $(".registration__input-field-username__validation-info"),
                    'password': $(".registration__input-field-password__validation-info"),
                    'confirm': $(".registration__input-field-confirm__validation-info")
                };
            }
            if (this.JQ_cacheLine == null) {
                this.JQ_cacheLine = {
                    'username': $(".registration__input-field-username__line"),
                    'password': $(".registration__input-field-password__line"),
                    'confirm': $(".registration__input-field-confirm__line")
                };
            }
        },
        renderValidationText: function (errors) {
            _.each(errors, function (error) {
                if (error.status == 'empty') {
                    this.JQ_cacheText[error.field].removeClass("validation-info_error");
                    this.JQ_cacheText[error.field].removeClass("validation-info_correct");
                    this.JQ_cacheText[error.field].text(error.message);
                }
                if (error.status == 'error') {
                    this.JQ_cacheText[error.field].addClass("validation-info_error");
                    this.JQ_cacheText[error.field].removeClass("validation-info_correct");
                    this.JQ_cacheText[error.field].text(error.message);
                }
            }, this);
        },
        renderValidationLine: function (errors) {
            _.each(errors, function (error) {
                if (error.status == 'empty') {
                    this.JQ_cacheLine[error.field].removeClass("line_red");
                    this.JQ_cacheLine[error.field].removeClass("line_green");
                }
                if (error.status == 'error') {
                    this.JQ_cacheLine[error.field].addClass("line_red");
                    this.JQ_cacheLine[error.field].removeClass("line_green");
                }
            }, this);
        },
        validateUsername: function (event) {
            this.JQ_cashing();
            $(".validation-info-common").text("");
            this.model.set({'username': $(event.currentTarget).val()}, {validate: true});
            var errors = this.model.validationError;
            if (!errors) {
                this.model.set({'validUsername': true});
                this.JQ_cacheText['username'].removeClass("validation-info_error");
                this.JQ_cacheText['username'].addClass("validation-info_correct");
                this.JQ_cacheText['username'].text("Поле Username задано корректно");
                this.JQ_cacheLine['username'].removeClass("line_red");
                this.JQ_cacheLine['username'].addClass("line_green");
            } else {
                this.model.set({'validUsername': false});
                this.renderValidationText(errors);
                this.renderValidationLine(errors);
            }

        },
        validatePassword: function (event) {
            this.JQ_cashing();
            $(".validation-info-common").text("");
            this.model.set({'password': $(event.currentTarget).val()}, {validate: true});
            var errors = this.model.validationError;
            if (!errors) {
                this.model.set({'validPassword': true});
                this.JQ_cacheText['password'].removeClass("validation-info_error");
                this.JQ_cacheText['password'].addClass("validation-info_correct");
                this.JQ_cacheText['password'].text("Поле Password задано корректно");
                this.JQ_cacheLine['password'].removeClass("line_red");
                this.JQ_cacheLine['password'].addClass("line_green");
            } else {
                this.model.set({'validPassword': false});
                this.renderValidationText(errors);
                this.renderValidationLine(errors);
            }
            this.validateConfirm();
        },
        validateConfirm: function () {
            this.JQ_cashing();
            var errors = [];
            if (!this.JQ_cacheInput['confirm'].val()) {
                errors.push({
                    'field': 'confirm',
                    'status': 'empty',
                    'message': "Подтвердите введенный пароль"
                });
            } else if (this.JQ_cacheInput['password'].val() != this.JQ_cacheInput['confirm'].val()) {
                errors.push({
                    'field': 'confirm',
                    'status': 'error',
                    'message': "Пароли не совпадают"
                });
            }
            if (errors.length == 0) {
                this.JQ_cacheText['confirm'].removeClass("validation-info_error");
                this.JQ_cacheText['confirm'].addClass("validation-info_correct");
                this.JQ_cacheText['confirm'].text("Поле Confirm задано корректно");
                this.JQ_cacheLine['confirm'].removeClass("line_red");
                this.JQ_cacheLine['confirm'].addClass("line_green");
                return true;
            } else {
                this.renderValidationText(errors);
                this.renderValidationLine(errors);
                return false;
            }
        },
        focusOnErrorField: function () {
            if (!this.model.get('validUsername')) {
                this.JQ_cacheInput['username'].focus();
            } else if (!this.model.get('validPassword')) {
                this.JQ_cacheInput['password'].focus();
            } else {
                this.JQ_cacheInput['confirm'].focus();
            }
        },
        onSubmit: function (event) {
            event.preventDefault();
            if (this.validateConfirm() && this.model.isValid()) {
                this.model.onRegistration();
            } else {
                $(".registration__validation-info-common").text("Не все поля заданы корректно.");
                this.focusOnErrorField();
            }
        },
        show: function () {
            this.trigger('show');
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }

    });
});