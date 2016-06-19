cc.Class({
    extends: cc.Component,

    properties: {
        startButton: {
            default: null,
            type: cc.Button
        }
    },

    // use this for initialization
    onLoad: function () {
        
    },
    
    startGame: function() {
        cc.director.loadScene("Game");
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
