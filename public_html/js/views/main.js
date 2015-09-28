// главное меню игры
define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
        el: 'div#page',
        template: tmpl,
        events: {
            "click .menu__button":  "onClick"
        },
        initialize: function () {
            console.log("main initialized.");
            //this.listenTo(this.model, "change", this.render);
        },
        render: function () {
            console.log("main render.");
            var data = {"isLogged": auth_user.isLogged()};
            this.$el.html(this.template(data));
            //location.href = $("menu__button").attr('href-data');
            //console.log("set locations");
        },
        show: function () {
            this.$el.show();
            console.log("show main");
        },
        hide: function () {
            this.$el.hide();
            console.log("hide main");
        },
        onClick: function(event) {
            //console.log(event.toElement.attributes.getNamedItem('data-href').nodeValue);
            location.href = event.toElement.attributes.getNamedItem('data-href').nodeValue
        }

    });

    return new View();
});