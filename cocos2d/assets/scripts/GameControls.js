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
        this._updateUncertainty(settings.getStochasticity());
    },

    increase: function() {
      this._updateUncertainty(settings.increaseStochasticity());
    },

    decrease: function() {
      this._updateUncertainty(settings.decreaseStochasticity());
    },

    _updateUncertainty: function (value) {
      this.uncertaintyDisplay.string = value.toFixed(1);
    }
});
