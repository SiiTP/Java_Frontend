require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        jquery: "lib/jquery-1.11.3.min",
        underscore: "lib/underscore-min",
        backbone: "lib/backbone-min",
        modernizr: "lib/modernizr-custom",
        qrcode: "lib/qrcode"
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
        },
        'qrcode': {
            exports: 'QRCode'
        }
    }
});

define(['backbone', 'sync', 'router', 'modernizr', 'qrcode'], function(
    Backbone,
    sync,
    router,
    m,
    qr
){
    Backbone.history.start();
});
