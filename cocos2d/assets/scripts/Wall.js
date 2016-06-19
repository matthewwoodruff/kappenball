cc.Class({
    extends: cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function () {

    },
    
    onCollisionEnter: function (ball, other) {
        ball.node.getComponent('Ball').wallEnter();
    },
    
    onCollisionExit: function (ball) {
        ball.node.getComponent('Ball').wallExit();
    }
});
