define (['backbone'], function(Backbone) {
    return Backbone.Model.extend({
        views: [],
        hideAllOther: function (view) {
            _.each(this.views, function (item) {
                if (item != view) {
                    item.hide();
                }
            });
        },
        add: function (view) {
            this.views.push(view);
            view.$el.html(view.template(view.model.toJSON()));
            document.getElementById('page').appendChild(view.el);
            view.on('show', this.hideAllOther.bind(this, view));
        }
    });
});
