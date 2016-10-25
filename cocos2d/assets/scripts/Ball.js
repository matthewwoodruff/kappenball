var settings = require('Settings');

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
        interventionVelocity: 100
    },

    onLoad: function () {
        this._energy = 0;
        this._initialPosition = this.node.getPosition();
        this._direction = Direction.NONE;
        this._intervention = Direction.NONE;
    },

    _randomDirection: function() {
      return Math.random() >= 0.5 ? Direction.LEFT : Direction.RIGHT;
    },
    
    _getNewIntervention: function(intervention) {
        if (intervention === Direction.NONE && settings.shouldIntervene()) {
          return this._randomDirection();
        } else if (settings.shouldStabilise()) {
          return Direction.NONE;
        } 
        return intervention
    },

    update: function (dt) {
        this._setIntervention(this._getNewIntervention(this._intervention));
        
        var manualdx = this.xVelocity * this._direction * dt;
        var interventiondx = this.interventionVelocity * this._intervention * dt;
        
        this.node.x += manualdx + interventiondx;
        this.node.y += this.yVelocity * dt;
        this._energy += Math.abs(manualdx);
    },

    spike: function() {
        this._reset();
    },

    succeed: function() {
        this._reset();
    },

    wallEnter: function() {
        this._blocked = this._movingDirection();
        this._setDirection(Direction.NONE);
        this._setIntervention(Direction.NONE);
    },
    
    _movingDirection: function() {
        return this._direction == Direction.NONE ? this._intervention : this._direction;
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
    
    _setIntervention: function(direction) {
        if(this._blocked !== direction) {
            this._intervention = direction;
        }
    }
});
