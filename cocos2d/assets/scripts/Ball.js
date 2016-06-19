cc.Class({
    extends: cc.Component,

    properties: {
    },

    onLoad: function () {
        this.ySpeed = -100;
        this.xSpeed = 0;
        this.acceleration = 0;
        this.setInputControl();
        this.inFreeSpace = true;
    },
    
    setInputControl: function () {
        var self = this;
        cc.eventManager.addListener({
            event: cc.EventListener.KEYBOARD,
            onKeyPressed: function(keyCode, event) {
                switch(keyCode) {
                    case cc.KEY.a:
                        self.acceleration = -100;
                        break;
                    case cc.KEY.d:
                        self.acceleration = 100;
                        break;
                }
            },
            onKeyReleased: function(keyCode, event) {
                switch(keyCode) {
                    case cc.KEY.a:
                        self.acceleration = 0;
                        break;
                    case cc.KEY.d:
                        self.acceleration = 0;
                        break;
                }
            }
        }, self.node);
    },

    update: function (dt) {
        this.node.y += this.ySpeed * dt;
        if (this.inFreeSpace) {
            this.xSpeed += this.acceleration * dt;
        }
        this.node.x += this.xSpeed * dt; 
    },
    
    spike: function() {
        this.node.destroy();
        this.listener.ballSpiked();
    },
    
    wallEnter: function() {
        this.inFreeSpace = false;
        this.xSpeed *= -0.3;
    },
    
    wallExit: function() {
        this.inFreeSpace = true;
    },
    
    succeed: function() {
        this.node.destroy(); 
        this.listener.ballSucceeded();
    },
    
    setListener: function(listener) {
        this.listener = listener;
    }
    
});
