cc.Class({
    extends: cc.Component,

    properties: {
    },

    onCollisionEnter: function (ball) {
        ball.node.getComponent('Ball').spike();
    }
});
