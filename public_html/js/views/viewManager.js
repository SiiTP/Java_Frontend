define (['backbone'], function(Backbone) {
    var View = Backbone.View.extend({
        views: [],
        hideAllOther: function (view) {
            _.each(this.views, function(item) {
                if (item != view) {
                    if (item.$el.css("display") != "none") {
                        item.hide();
                    }
                } else {
                    if (item.$el.is(':empty')) {
                        if (item.model) {
                            item.$el.html(item.template(item.model.toJSON()));
                        } else {
                            item.$el.html(item.template());
                        }

                    }
                    if (document.getElementsByClassName(item.$el.children(1).attr('class')).length == 0) {
                        document.getElementById('page').appendChild(item.el);
                    }
                }
            });
        },
        add: function (view) {
            this.views.push(view);
            view.on('show', this.hideAllOther.bind(this, view));
        }
    });
    return new View()
});
