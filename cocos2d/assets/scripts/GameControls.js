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
        this._updateUncertainty();
    },

    decrease: function() {
        this._uncertainty = Math.max(this._uncertainty - 10, 0);
        this._updateUncertainty();
    },
    
    _updateUncertainty: function () {
        this.uncertaintyDisplay.string = this._uncertainty;
    }
});
