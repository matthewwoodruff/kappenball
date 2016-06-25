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
      this._updateDisplay();
    },

    back: function() {
        cc.director.loadScene("Game");
    },

    increaseMaxInterventionProbability: function() {
        settings.increaseMaxInterventionProbability();
        this._updateDisplay();
    },

    decreaseMaxInterventionProbability: function() {
        settings.decreaseMaxInterventionProbability();
        this._updateDisplay();
    },

    increaseMaxStabilityProbability: function() {
        settings.increaseMaxStabilityProbability();
        this._updateDisplay();
    },

    decreaseMaxStabilityProbability: function() {
        settings.decreaseMaxStabilityProbability();
        this._updateDisplay();
    },

    _updateDisplay: function () {
        this.maxInterventionDisplay.string = settings.getMaxInterventionProbability().toFixed(1);
        this.maxStabilityDisplay.string = settings.getMaxStabilityProbability().toFixed(1);
    }
});
