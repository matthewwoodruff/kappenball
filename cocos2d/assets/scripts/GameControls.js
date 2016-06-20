var settings = require('Settings');

cc.Class({
    extends: cc.Component,

    properties: {
        uncertaintyDisplay: {
            default: null,
            type: cc.Label
        }
    },
    
    onLoad: function() {
        this._updateUncertainty();
    },

    increase: function() {
        settings.stochasticity = Math.min(settings.stochasticity + 10, 100);
        this._updateUncertainty();
    },

    decrease: function() {
        settings.stochasticity = Math.max(settings.stochasticity - 10, 0);
        this._updateUncertainty();
    },
    
    _updateUncertainty: function () {
        this.uncertaintyDisplay.string = settings.stochasticity;
    }
});
