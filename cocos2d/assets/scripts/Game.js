cc.Class({
    extends: cc.Component,

    properties: {
        ball: {
            default: null,
            type: cc.Node
        }
    },

    // use this for initialization
    onLoad: function () {
        var Manager = cc.director.getCollisionManager();
        Manager.enabled = true;
        // Manager.enabledDebugDraw = true;
        // this.spawnNewBall();
        
        this.ball.on('spiked', this.ballSpiked, this);
        this.ball.on('succeeded', this.ballSucceeded, this);
    },

    // spawnNewBall: function() {
    //     this.ball = cc.instantiate(this.ballPrefab);
    //     this.node.addChild(this.ball);
        
    //     this.ball.on('spiked', this.ballSpiked, this);
    //     this.ball.on('succeeded', this.ballSucceeded, this);
    // },
    
    ballSpiked: function() {
        // this.spawnNewBall();
    },
    
    ballSucceeded: function() {
        // this.spawnNewBall();
    },
    
    touchStart: function(event) {
        var x = this.node.convertToNodeSpace(event.getLocation()).x;
        var direction = x >= this.node.getContentSize().width * 0.5 ? "left" : "right"; 
        this.ball.getComponent('Ball')[direction]();
    },
    
    touchEnd: function(event) {
        this.ball.getComponent('Ball').stopForce();
    }
});
