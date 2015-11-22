// форма входа в уч. запись
define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    return Backbone.View.extend({
        name: "login",
        tagName: 'div',
        template: tmpl,
        model: null,
        JQ_cacheText: null,
        JQ_cacheLine: null,
        JQ_cacheInput: null,
        events: {
            "click .login__buttons-container__button": "onSubmit",
            "input .login__input-field-username__input-line__input": "validateUsername",
            "input .login__input-field-password__input-line__input": "validatePassword"
        },
        initialize: function () {
            this.model.on("toMain", function() {
                location.href = '#';
            });
            this.model.on("error", function(model, message) {
                $(".login__validation-info-common").text(message);
            })
        },
        JQ_cashing: function () {
            if (this.JQ_cacheInput == null) {
                this.JQ_cacheInput = {
                    'username': $(".login__input-field-username__input-line__input"),
                    'password': $(".login__input-field-password__input-line__input")
                };
            }
            if (this.JQ_cacheText == null) {
                this.JQ_cacheText = {
                    'username': $(".login__input-field-username__validation-info"),
                    'password': $(".login__input-field-password__validation-info")
                };
            }
            if (this.JQ_cacheLine == null) {
                this.JQ_cacheLine = {
                    'username': $(".login__input-field-username__line"),
                    'password': $(".login__input-field-password__line")
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
        },
        focusOnErrorField: function () {
            if (!this.model.get('validUsername')) {
                this.JQ_cacheInput['username'].focus();
            } else if (!this.model.get('validPassword')) {
                this.JQ_cacheInput['password'].focus();
            }
        },
        onSubmit: function (event) {
            event.preventDefault();
            this.JQ_cashing();
            if (this.model.isValid()) {
                this.model.set({id: 1});
                this.model.save(null, this.model.optionsLog);
            } else {
                $(".login__validation-info-common").text("Не все поля заданы корректно.");
                this.focusOnErrorField();
            }
        },
        show: function () {
            this.trigger('show');
            this.$el.show();
            if (this.JQ_cacheInput) {
                _.each(this.JQ_cacheInput, function (inputField) {
                    inputField.trigger('input');
                });
            }
        },
        hide: function () {
            this.$el.hide();
        }
    });
});