// главное меню игры
define([
    'backbone',
    'tmpl/main',
    'getCookie',
    'constants'
], function(
    Backbone,
    tmpl,
    getCookie,
    constants
){
    return Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        JQmsg: undefined,
        events: {
            "click .game-btn-container__button": "onClick",
            "click .logout__button": "onClick",
            "click .dontLogged__button": "onClick"
        },
        initialize: function () {
            this.model.on('change', this.checkChanges.bind(this));
            this.model.on('error', function() {});
        },
        checkChanges: function () {
            if (this.model.hasChanged('logged') || this.model.hasChanged('score')) {
                this.render();
            }
        },
        render: function () {
            //console.log("main render");
            this.$el.html(this.template(this.model.toJSON()));
            this.qrcode();
        },

        show: function () {
            this.trigger('show');
            this.$el.show();
            if (this.model.get('logged')) {
                this.qrcode();
            }
        },

        hide: function () {
            this.$el.hide();

        },

        onClick: function (event) {
            event.preventDefault();
            var targetElement = event.currentTarget;
            if (!$(targetElement).hasClass('game-btn-container__button_disabled')) {
                var href = targetElement.attributes.getNamedItem('data-href').value;
                if (href == "#logout") {
                    this.model.destroy(this.model.optionsDestroy);
                } else {
                    location.href = href;
                }
            }
        },

        qrcode: function() {
            var sess = getCookie("JSESSIONID");
            var qr_codeElement = $(".container-main__qrcode__code:empty")[0];
            if (qr_codeElement) {
                new QRCode(qr_codeElement, {
                    text: constants.get("HOST") + "/#mobile/" + sess,
                    width: 256,
                    height: 256,
                    colorDark : "#ffffff",
                    colorLight : "#000000",
                    correctLevel : QRCode.CorrectLevel.H
                });
            }
        }
    });
});