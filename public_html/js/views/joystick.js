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
        JQ_container: null,
        JQ_hyroscopeCheckbox: false,

        radius: 100,
        radiusCursor: 20,
        rightDevice: false,
        orientation: "portrait",
        minScale: null,
        events: {
            "touchmove .joystick__canvas-cursor": "onTouchMove",
            "touchstart .joystick__canvas-cursor": "onTouchStart",
            "touchend .joystick__canvas-cursor": "onTouchEnd",
            "touchcancel .joystick__canvas-cursor": "onTouchCancel",
            "change .js-hyroscope-checkbox": "onHyroscopeCheckbox"
        },

        initialize: function () {
            this.rightDevice = modernizr.devicemotion && modernizr.deviceorientation && modernizr.touchevents;

            if (this.rightDevice) {
                window.addEventListener('deviceorientation', this.onHyroscopeEvent.bind(this));

                window.screen.orientation.addEventListener("change", this.onOrientationChange.bind(this));
                if (window.screen.orientation.angle % 180 === 0) {
                    this.orientation = "portrait";
                } else {
                    this.orientation = "landscape";
                }
                this.defineMinScale();
            }


        },
        show: function () {
            this.$el.show();
            this.trigger('show');
            this.render();
        },

        hide: function () {
            console.log("joystick hide");
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

        defineMinScale: function () {
            if (window.screen.width < window.screen.height) {
                this.minScale = window.screen.width;
            } else {
                this.minScale = window.screen.height;
            }
            this.radius = this.minScale / 2 - 22;
            console.log("minScale : " + this.minScale);
        },

        draw: function () {
            this.context.beginPath();
            this.context.fillStyle = "#f90";
            this.context.arc(this.radius, this.radius, this.radius, 0, Math.PI * 2, true);
            this.context.fill();
        },

        render: function () {
            console.log("joystick render");
            this.JQ_msg = this.$(".joystick__info__message");
            if(this.rightDevice) {

                this.canvasCursor = this.$('.joystick__canvas-cursor')[0];
                this.canvasCursor.width  = this.minScale;
                this.canvasCursor.height = this.minScale;
                this.contextCursor = this.canvasCursor.getContext('2d');

                this.canvas = this.$('.joystick__canvas')[0];
                this.canvas.width  = this.minScale;
                this.canvas.height = this.minScale;
                this.context = this.canvas.getContext('2d');

                this.draw();

                this.JQ_container = this.$(".joystick__info");
                if (this.orientation == "landscape") {
                    this.JQ_container.css("left", this.minScale + "px");
                    //this.JQ_container.css("top", 0 + "px");
                } else {
                    //this.JQ_container.css("left", 0 + "px");
                    this.JQ_container.css("top", this.minScale + "px");
                }
            } else {
                this.JQ_msg.text("Неподходящее устройство для джойстика");
            }
        },

        onOrientationChange: function(event) {
            if (event.target.angle % 180 === 0) {
                this.orientation = "portrait";
            } else {
                this.orientation = "landscape";
            }
            console.log(this.orientation);
            this.render();
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
            this.trigger("startMove");
        },

        onTouchEnd: function() {
            this.trigger("stopMove");
        },

        onTouchCancel: function() {
            this.trigger("stopMove");
        },

        onHyroscopeCheckbox: function() {
            this.JQ_hyroscopeCheckbox = !this.JQ_hyroscopeCheckbox;
            console.log("on hyroscope : " + this.JQ_hyroscopeCheckbox);
        },

        onHyroscopeEvent: function (event) {
            //console.log("A : " + Math.sin(event.alpha * Math.PI / 180));
            var angleY = -Math.sin(event.beta * Math.PI / 180);
            var angleX = Math.sin(event.gamma * Math.PI / 180);
            var angle = Math.atan(angleY/angleX) * 180 / Math.PI;
            if (event.gamma < 0) {
                angle += 180;
            }
            if (angle < 0) {
                angle += 360;
            }
            //console.log("B : " + angleY);
            console.log("angle : " + angle);
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
            this.contextCursor.clearRect(0, 0, this.minScale, this.minScale);
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
            this.trigger("setAngle", {"angle": angle});
        }
    });
    return new View();
});