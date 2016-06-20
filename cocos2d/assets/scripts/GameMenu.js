cc.Class({
    extends: cc.Component,

    properties: {
    },

    onLoad: function () {
    },
    
    endGame: function() {
        cc.director.loadScene("Main Menu");
    }
});
