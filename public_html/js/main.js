require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery-1.11.3.min",
        underscore: "lib/underscore-min",
        backbone: "lib/backbone-min"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        }
    }
});

define(['backbone', 'sync', 'router'], function(
    Backbone,
    sync,
    router
){
    Backbone.history.start();
});
