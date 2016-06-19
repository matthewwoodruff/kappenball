cc.Class({
    extends: cc.Component,

    properties: {
        ballPrefab: {
            default: null,
            type: cc.Prefab
        }
    },

    // use this for initialization
    onLoad: function () {
        var Manager = cc.director.getCollisionManager();
        Manager.enabled = true;
        // Manager.enabledDebugDraw = true;
        this.spawnNewBall();
        
        this.node.on(cc.Node.EventType.TOUCH_START, this.touchStart, this);
        this.node.on(cc.Node.EventType.TOUCH_END, this.touchEnd, this);
    },

    spawnNewBall: function() {
        this.ball = cc.instantiate(this.ballPrefab);
        this.node.addChild(this.ball);
        
        this.ball.on('spiked', this.ballSpiked, this);
        this.ball.on('succeeded', this.ballSucceeded, this);
    },
    
    ballSpiked: function() {
        this.spawnNewBall();
    },
    
    ballSucceeded: function() {
        this.spawnNewBall();
    },
    
    touchStart: function(event) {
        var x = this.node.convertToNodeSpace(event.getLocation()).x;
        var forceDirection = x >= this.node.getContentSize().width * 0.5 ? "forceLeft" : "forceRight"; 
        this.ball.getComponent('Ball')[forceDirection]();
    },
    
    touchEnd: function(event) {
        this.ball.getComponent('Ball').stopForce();
    }
});
