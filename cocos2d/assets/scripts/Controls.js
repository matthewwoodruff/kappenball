cc.Class({
    extends: cc.Component,

    properties: {
        uncertaintyDisplay: {
            default: null,
            type: cc.Label
        }
    },

    // use this for initialization
    onLoad: function () {
        this._uncertainty = 0;
    },
    
    increase: function() {
        this._uncertainty = Math.min(this._uncertainty + 10, 100);
        this.uncertaintyDisplay.string = this._uncertainty;
    },

    decrease: function() {
        this._uncertainty = Math.max(this._uncertainty - 10, 0);
        this.uncertaintyDisplay.string = this._uncertainty;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
