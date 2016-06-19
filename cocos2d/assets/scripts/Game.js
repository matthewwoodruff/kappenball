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
        Manager.enabledDebugDraw = true;
        this.spawnNewBall();
    },

    spawnNewBall: function() {
        var newBall = cc.instantiate(this.ballPrefab);
        this.node.addChild(newBall);
        newBall.getComponent('Ball').setListener(this);
    },
    
    ballSpiked: function() {
        this.spawnNewBall();
    },
    
    ballExited: function() {
        this.spawnNewBall();
    }

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
