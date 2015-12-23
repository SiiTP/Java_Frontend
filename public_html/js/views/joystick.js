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

                this.on("setAngle", this.onSetAngle);

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
        },

        draw: function () {
            this.context.beginPath();

            //тень____________________
            this.context.shadowBlur = 10;
            this.context.shadowColor = "#0f0";
            //________________________

            this.context.fillStyle = "#ff5";
            this.context.arc(this.radius, this.radius, this.radius - 10, 0, Math.PI * 2, true);
            this.context.fill();

            this.context.strokeStyle = "#0f0";
            this.context.strokeWidth = 5;
            this.context.arc(this.radius, this.radius, this.radius - 10, 0, Math.PI * 2, true);
            this.context.stroke();


        },

        drawCursor: function(angle) {
            var r = this.radius;
            var x = r + (r - 10) * Math.cos(angle * Math.PI / 180);
            var y = r - (r - 10) * Math.sin(angle * Math.PI / 180);
            this.clearCursor();
            this.contextCursor.beginPath();
            this.contextCursor.moveTo(r, r);
            this.contextCursor.lineTo(x, y);
            this.contextCursor.lineWidth = 2;
            this.contextCursor.strokeStyle = "#005";
            this.contextCursor.stroke();

            this.contextCursor.beginPath();
            this.contextCursor.moveTo(r, r);
            this.contextCursor.lineTo(2 * r, r);
            this.contextCursor.lineWidth = 3;
            this.contextCursor.strokeStyle = "#002";
            this.contextCursor.stroke();

            this.contextCursor.beginPath();
            this.contextCursor.fillStyle = "#0f0";
            this.contextCursor.arc(x, y, 10, 0, Math.PI / 180, true);
            this.contextCursor.fill();
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
                } else {
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
            if (!this.JQ_hyroscopeCheckbox) {
                var x = event.originalEvent.touches[0].clientX;
                var y = event.originalEvent.touches[0].clientY;
                var r = this.radius;
                var length = Math.sqrt((x-r)*(x-r) + (y-r)*(y-r));
                if (length < r) { // если курсор в джойстике
                    this.setAngleByMouse(x, y);
                } else {
                    this.clearCursor();
                }
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
            if (this.JQ_hyroscopeCheckbox) {
                var angleY = -Math.sin(event.beta * Math.PI / 180);
                var angleX = Math.sin(event.gamma * Math.PI / 180);
                var angle  = Math.atan(angleY/angleX) * 180 / Math.PI;
                if (event.gamma < 0) {
                    angle += 180;
                }
                if (angle < 0) {
                    angle += 360;
                }

                // при маленьких углах наклона останавливаться
                if (Math.abs(angleX) < 0.1 && Math.abs(angleY) < 0.1) {
                    this.trigger("stopMove")
                } else {
                    this.trigger("startMove")
                }
                //console.log("angle : " + angle);
                if (isNaN(angle)) {
                    angle = 0;
                }
                this.trigger("setAngle", {"angle": angle})
            }
        },

        clearCursor: function() {
            this.contextCursor.beginPath();
            this.contextCursor.clearRect(0, 0, this.minScale, this.minScale);
        },

        setAngleByMouse: function(x, y) {
            var centerX = this.radius;
            var centerY = this.radius;
            var angle = Math.atan((centerY - y) / (x - centerX));
            angle = angle * (180 / Math.PI);
            if (x < centerX) {
                angle += 180;
            }
            angle = (angle + 360) % 360;
            this.trigger("setAngle", {"angle": angle});
        },

        onSetAngle: function(event) {
            this.drawCursor(event.angle);
            this.JQ_msg.text("angle : " + parseFloat(event.angle).toFixed(2));
        }
    });
    return new View();
});