var Direction = {
    NONE: 0,
    RIGHT: 1,
    LEFT: -1
};

cc.Class({
    extends: cc.Component,

    properties: {
        yVelocity: -100,
        xVelocity: 200
    },

    onLoad: function () {
        this._energy = 0;
        this._initialPosition = this.node.getPosition();
        this._direction = Direction.NONE;
    },
    
    update: function (dt) {
        this.node.y += this.yVelocity * dt;
        var dx = this.xVelocity * this._direction * dt;
        this.node.x += dx;
        this._energy += Math.abs(dx);
    },
    
    spike: function() {
        this._reset();
    },
    
    succeed: function() {
        this._reset();
    },
    
    wallEnter: function() {
        this._blocked = this._direction;
        this._setDirection(Direction.NONE);
    },
    
    wallExit: function() {
        delete this._blocked;
    },
    
    getEnergy: function() {
        return this._energy;
    },
    
    left: function() {
        this._setDirection(Direction.LEFT);
    },
    
    right: function() {
        this._setDirection(Direction.RIGHT);
    },
    
    stopForce: function() {
        this._setDirection(Direction.NONE);
    },
    
    _reset: function () {
        this._energy = 0;
        this.node.setPosition(this._initialPosition.x, this._initialPosition.y);
    },
    
    _setDirection: function(direction) {
        if(this._blocked !== direction) this._direction = direction;
    },
});
