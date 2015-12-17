require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery-1.11.3.min",
        underscore: "lib/underscore-min",
        backbone: "lib/backbone-min",
        modernizr: "lib/modernizr-custom"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'modernizr': {
            exports: 'Modernizr'
        }
    }
});

define(['backbone', 'sync', 'router', 'modernizr'], function(
    Backbone,
    sync,
    router
){
    console.log(Modernizr);
    Backbone.history.start();
});
