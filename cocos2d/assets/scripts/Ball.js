cc.Class({
    extends: cc.Component,

    properties: {
        yVelocity: -100,
        xVelocity: 200
    },

    onLoad: function () {
        this.currentXVelocity = 0;
    },

    update: function (dt) {
        this.node.y += this.yVelocity * dt;
        this.node.x += this.currentXVelocity * dt;
    },
    
    spike: function() {
        this.node.destroy();
        this.node.emit('spiked');
    },
    
    wallEnter: function() {
        this.currentXVelocity *= -0.3;
    },
    
    wallExit: function() {
        this.currentXVelocity = 0;
    },
    
    succeed: function() {
        this.node.destroy(); 
        this.node.emit('succeeded');
    },
    
    forceRight: function() {
        this.currentXVelocity = this.xVelocity;
    },
    
    forceLeft: function() {
        this.currentXVelocity = -this.xVelocity;
    },
    
    stopForce: function() {
        this.currentXVelocity = 0;
    }
});
