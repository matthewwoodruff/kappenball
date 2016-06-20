cc.Class({
    extends: cc.Component,

    properties: {
    },
    
    onCollisionExit: function (ball) {
        ball.node.getComponent('Ball').succeed();
    }
});
