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
        settings.increaseStochasticity();
        this._updateUncertainty();
    },

    decrease: function() {
        settings.decreaseStochasticity();
        this._updateUncertainty();
    },

    _updateUncertainty: function () {
        this.uncertaintyDisplay.string = settings.getStochasticity().toFixed(1);
    }
});
