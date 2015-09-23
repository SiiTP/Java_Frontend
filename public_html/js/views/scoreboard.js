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
        el: 'div',
        template: tmpl,
        collection: scores,
        model: score,
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
            this.collection.comparator = function(m) {
                return -m.get('score');
            };

            this.collection.sort('score');
            this.render();
            //JSON.stringify(this.collection)
            //console.log(this.collection.toJSON());
        },
        render: function () {
            console.log("render scoreboard");
            this.el = '#page';
            this.$el.html(this.template(this.collection.toJSON()));
        },


        show: function () {
            console.log("show scoreboard");
            this.$el.show();
        },
        hide: function () {
            console.log("hide scoreboard");
            this.$el.hide();
        }

    });

    return new View();
});