cc.Class({
    extends: cc.Component,

    properties: {},

    onCollisionEnter: function (ball, other) {
        ball.node.getComponent('Ball').wallEnter();
    },
    
    onCollisionExit: function (ball) {
        ball.node.getComponent('Ball').wallExit();
    }
});
