cc.Class({
    extends: cc.Component,

    properties: {
        ball: {
            default: null,
            type: cc.Node
        },
        energy: {
            default: null,
            type: cc.Label
        }
    },

    onLoad: function () {
        cc.director.getCollisionManager().enabled = true;
        // cc.director.getCollisionManager().enabledDebugDraw = true;
        
        this._ball = this.ball.getComponent('Ball');
        this.node.on(cc.Node.EventType.TOUCH_START, this._touchStart, this);
        this.node.on(cc.Node.EventType.TOUCH_END, this._stopForce, this);
    },
    
    _touchStart: function(event) {
        var inRightHalf = this.node.convertToNodeSpace(event.getLocation()).x >= this.node.getContentSize().width * 0.5;
        this._ball[inRightHalf ? 'left' : 'right']();
    },
    
    _stopForce: function() {
        this._ball.stopForce();
    },
    
    update: function() {
        this.energy.string = 'Energy: ' + Math.ceil(this._ball.getEnergy());
    }
});
