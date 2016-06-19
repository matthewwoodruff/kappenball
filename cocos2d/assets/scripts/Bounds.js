cc.Class({
    extends: cc.Component,

    properties: {
    },

    // use this for initialization
    onLoad: function () {

    },
    
    onCollisionExit: function (ball) {
        ball.node.getComponent('Ball').boundsExit();
    }
});
