cc.Class({
    extends: cc.Component,

    properties: {
    },

    // use this for initialization
    onLoad: function () {
        
    },
    
    endGame: function() {
        cc.director.loadScene("Main Menu");
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
