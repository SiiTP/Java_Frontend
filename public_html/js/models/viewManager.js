define (['backbone'], function(Backbone) {
    var Model = Backbone.Model.extend({
        views: [],
        events: {
            'showing' : 'hideAllOther'
        },
        initialize: function() {
        },
        getViews: function() {
            return this.views;
        },
        //скрытие всех вью кроме той у которой имя name
        hideAllOther: function(data) {
            //TODO this в данной ситуации это вью. почему???
            _.each(manager.getViews(), function(item) {
                if (item.name != data.name) {
                    item.hide();
                }
            });
            //console.log("show event in manager with name : " + data.name);
        },
        add: function(view) {
            console.log("view with name : " + view.name + " added in manager");
            this.views.push(view);
            view.on('show', this.hideAllOther);
        },
        info: function() {
            console.log("INFO : ");
            _.each(this.views, function(view) {
                console.log("view.name : " + view.name);
            });
            console.log("===========");
        }
        /*hasElement: function(array, element) {
            var isNotUnique = false;
            console.log("element : ");
            console.log(element.name);
            array.forEach(function(item, i, arr){
                console.log("item : " + item.view.name);
                console.log("i : " + i);
                console.log("========");
                if (item.view.name == element.name) {
                    console.log("element with name : " + element.name + " is already added");
                    isNotUnique = true;
                    return;
                }
            });
            return isNotUnique;
        },
        hasInViews: function(element) {
            return this.hasElement(this.views, element);
        },
        info: function() {
            console.log("length of array : " + this.views.length);
            this.views.forEach(function(item, i, array) {
                console.log("name of el : " + item.view.name + "; index : " + i);
            });
        }*/
    });
    return Model;
});
