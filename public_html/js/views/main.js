// главное меню игры
define([
    'backbone',
    'tmpl/main',
    'getCookie'
], function(
    Backbone,
    tmpl,
    getCookie
){
    return Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        JQmsg: undefined,
        events: {
            "click .game-btn-container__button": "onClick",
            "click .logout__button": "onClick"
        },
        initialize: function () {
            this.model.on('change', this.checkChanges.bind(this));
            this.model.on('error', function() {
                console.log("ERROR");
            }.bind(this));
        },
        checkChanges: function () {
            if (this.model.hasChanged('logged') || this.model.hasChanged('score')) {
                this.render();
                if (this.model.get('logged')) {
                    this.qrcode();
                }
            }
        },
        render: function () {
            console.log("main render");
            this.$el.html(this.template(this.model.toJSON()));
        },
        show: function () {
            this.trigger('show');
            this.$el.show();
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
           new QRCode(document.getElementsByClassName("container-main__qrcode")[0], {
                text: "http://localhost:8000/#mobile/" + sess,
                width: 256,
                height: 256,
                colorDark : "#ffffff",
                colorLight : "#000000",
                correctLevel : QRCode.CorrectLevel.H
            });
            //new QRCode(document.getElementsByClassName("container-main__qrcode")[0], "http://localhost:8000/gameplay/mobile/qfdsfgsfhgdf");
        }
    });
});