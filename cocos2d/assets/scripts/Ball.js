var Direction = {
    NONE: 0,
    RIGHT: 1,
    LEFT: -1
};

cc.Class({
    extends: cc.Component,

    properties: {
        yVelocity: -100,
        xVelocity: 200,
        gameArea: {
            default: null,
            type: cc.Node
        }
    },

    onLoad: function () {
        this._initialPosition = this.node.getPosition();
        this._direction = Direction.NONE;
        this.gameArea.on(cc.Node.EventType.TOUCH_START, this._touchStart, this);
        this.gameArea.on(cc.Node.EventType.TOUCH_END, this._stopForce, this);
    },
    
    update: function (dt) {
        this.node.y += this.yVelocity * dt;
        this.node.x += this.xVelocity * this._direction * dt;
    },
    
    spike: function() {
        this._reset();
        this.node.emit('spiked');
    },
    
    succeed: function() {
        this._reset();
        this.node.emit('succeeded');
    },
    
    wallEnter: function() {
        this._blocked = this._direction;
        this._setDirection(Direction.NONE);
    },
    
    wallExit: function() {
        delete this._blocked;
    },
    
    _touchStart: function(event) {
        var inRightHalf = this.gameArea.convertToNodeSpace(event.getLocation()).x >= this.gameArea.getContentSize().width * 0.5;
        this._setDirection(inRightHalf ? Direction.LEFT : Direction.RIGHT);
    },
    
    _reset: function () {
        this.node.setPosition(this._initialPosition.x, this._initialPosition.y);
    },
    
    _stopForce: function() {
        this._setDirection(Direction.NONE);
    },
    
    _setDirection: function(direction) {
        if(this._blocked !== direction) this._direction = direction;
    },
});
