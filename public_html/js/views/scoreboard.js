// топ 10 игроков
define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores',
    'models/score'
], function(
    Backbone,
    tmpl,
    scores,
    score
){

    var View = Backbone.View.extend({
        name: "scoreboard",
        tagName: 'div',
        template: tmpl,
        collection: scores,
        model: score,
        events: {
            "click button.scoreboard__button" : 'onClick'
        },
        initialize: function () {
            console.log("initialization of scoreboard");

            this.collection.push(new this.model({'name': 'Ivan', 'score': 200}));
            this.collection.push(new this.model({'name': 'Dima', 'score': 98}));
            this.collection.push(new this.model({'name': 'Jenya', 'score': 120}));
            this.collection.push(new this.model({'name': 'Masha', 'score': 10}));
            this.collection.push(new this.model({'name': 'Victor', 'score': 50}));
            this.collection.push(new this.model({'name': 'Oleg', 'score': 70}));
            this.collection.push(new this.model({'name': 'Gena', 'score': 312}));
            this.collection.push(new this.model({'name': 'Ylrich', 'score': 1120}));
            this.collection.push(new this.model({'name': 'Fedor', 'score': 640}));
            this.collection.push(new this.model({'name': 'Alena', 'score': 15}));

            //эта функция возвращает тот элемент объекта по которому объекты будут сортироваться


            this.collection.sort('score');
            this.render();
        },
        render: function () {
            console.log("render scoreboard");
            var data = this.collection.toJSON();
            this.$el.html(this.template(data));
            document.getElementById('page').appendChild(this.el);
            this.$el.hide();
        },
        onClick: function(event) {
            console.log("scoreboard view on click");
            location.href = event.currentTarget.attributes.getNamedItem('data-href').value;
        },

        show: function () {
            var data = this.collection.toJSON();
            this.$el.html(this.template(data));
            this.$el.show();
            this.trigger('show', {'name':this.name});
            console.log("show scoreboard");
        },
        hide: function () {
            console.log("scoreboard view hide");
            this.$el.hide();
        }

    });

    return new View();
});