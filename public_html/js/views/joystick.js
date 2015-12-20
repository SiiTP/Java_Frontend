define([
    'backbone',
    'modernizr',
    'tmpl/joystick'
], function(
    Backbone,
    modernizr,
    tmpl
){

    var View = Backbone.View.extend({
        tagName: 'div',
        template: tmpl,
        canvas: null,
        canvasCursor: null,
        context: null,
        contextCursor: null,
        JQ_msg: null,
        radius: 100,
        radiusCursor: 20,
        rightDevice: false,
        events: {
            "touchmove .joystick__canvas-cursor": "onTouchMove",
            "touchstart .joystick__canvas-cursor": "onTouchStart",
            "touchend .joystick__canvas-cursor": "onTouchEnd",
            "touchcancel .joystick__canvas-cursor": "onTouchCancel"
        },
        initialize: function () {
            this.rightDevice = modernizr.devicemotion && modernizr.deviceorientation && modernizr.touchevents;
        },
        show: function () {
            this.$el.show();
            this.trigger('show');
            if(this.rightDevice) {
                this.canvasCursor = this.$('.joystick__canvas-cursor')[0];
                this.canvasCursor.width  = 300;
                this.canvasCursor.height = 300;
                this.contextCursor = this.canvasCursor.getContext('2d');

                this.canvas = this.$('.joystick__canvas')[0];
                this.canvas.width  = 300;
                this.canvas.height = 300;
                this.context = this.canvas.getContext('2d');

                this.JQ_msg = this.$(".joystick__message").css("position", "absolute");
                this.JQ_msg = this.$(".joystick__message").css("top", "230px");
                this.draw();
            } else {
                this.JQ_msg = this.$(".joystick__message").text("Неподходящее устройство");
            }
        },
        hide: function () {
            if (this.canvas) {
                this.canvas.width  = 0;
                this.canvas.height = 0;
            }
            if (this.canvasCursor) {
                this.canvasCursor.width = 0;
                this.canvasCursor.height = 0;
            }
            this.$el.hide();
        },
        draw: function () {
            console.log("drawing joystick");
            this.context.beginPath();
            this.context.fillStyle = "#f90";
            this.context.arc(this.radius, this.radius, this.radius, 0, Math.PI * 2, true);
            this.context.fill();
        },
        onTouchMove: function(event) {
            event.preventDefault();
            var x = event.originalEvent.touches[0].clientX;
            var y = event.originalEvent.touches[0].clientY;
            //console.log("X : " + x + "Y : " + y);
            var r = this.radius;
            var r_c = this.radiusCursor;
            var length = Math.sqrt((x-r)*(x-r) + (y-r)*(y-r));
            if (length < r - r_c) { // если курсор в джойстике
                this.drawCursor(x, y);
                this.setAngle(x, y);
            } else {
                this.clearCursor();
            }

        },
        onTouchStart: function() {
            console.log("touchstart");
            this.trigger("startMove");
        },
        onTouchEnd: function() {
            console.log("touchend");
            this.trigger("stopMove");
        },
        onTouchCancel: function() {
            console.log("touchcancel");
            this.trigger("stopMove");
        },
        drawCursor: function(x, y) {
            this.clearCursor();
            this.contextCursor.beginPath();
            this.contextCursor.fillStyle = "#ff0";
            this.contextCursor.arc(x, y, this.radiusCursor, 0, Math.PI * 2, true);
            this.contextCursor.fill();
        },
        clearCursor: function() {
            this.contextCursor.beginPath();
            this.contextCursor.clearRect(0, 0, 300, 300);
        },
        setAngle: function(x, y) {
            var centerX = this.radius;
            var centerY = this.radius;
            var angle = Math.atan((centerY - y) / (x - centerX));
            angle = angle * (180 / Math.PI);
            if (x < centerX) {
                angle += 180;
            }
            angle = (angle + 360) % 360;
            console.log(angle);
            this.trigger("setAngle", {"angle": angle});
        }
    });
    return new View();
});