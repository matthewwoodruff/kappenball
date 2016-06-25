var settings = require('Settings');

cc.Class({
    extends: cc.Component,

    properties: {
        maxInterventionDisplay: {
            default: null,
            type: cc.Label
        },
        maxStabilityDisplay: {
            default: null,
            type: cc.Label
        }
    },

    onLoad: function() {
      this._updateDisplay(this.maxInterventionDisplay, settings.getMaxInterventionProbability());
      this._updateDisplay(this.maxStabilityDisplay, settings.getMaxStabilityProbability());
    },

    back: function() {
      cc.director.loadScene("Game");
    },

    increaseMaxInterventionProbability: function() {
      this._updateDisplay(this.maxInterventionDisplay, settings.increaseMaxInterventionProbability());
    },

    decreaseMaxInterventionProbability: function() {
      this._updateDisplay(this.maxInterventionDisplay, settings.decreaseMaxInterventionProbability());
    },

    increaseMaxStabilityProbability: function() {
      this._updateDisplay(this.maxStabilityDisplay, settings.increaseMaxStabilityProbability());
    },

    decreaseMaxStabilityProbability: function() {
      this._updateDisplay(this.maxStabilityDisplay, settings.decreaseMaxStabilityProbability());
    },

    _updateDisplay: function(display, value) {
      display.string = value.toFixed(1);
    }
});
