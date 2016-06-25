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
        settings.maxInterventionProbability = Math.min(settings.maxInterventionProbability + 0.1, 1);
        this._updateDisplay();
    },
    
    decreaseMaxInterventionProbability: function() {
        settings.maxInterventionProbability = Math.max(settings.maxInterventionProbability - 0.1, 0);
        this._updateDisplay();
    },
    
    increaseMaxStabilityProbability: function() {
        settings.maxStabilityProbability = Math.min(settings.maxStabilityProbability + 0.1, 1);
        this._updateDisplay();
    },
    
    decreaseMaxStabilityProbability: function() {
        settings.maxStabilityProbability = Math.max(settings.maxStabilityProbability - 0.1, 0);
        this._updateDisplay();
    },
    
    _updateDisplay: function () {
        this.maxInterventionDisplay.string = settings.maxInterventionProbability.toFixed(1);
        this.maxStabilityDisplay.string = settings.maxStabilityProbability.toFixed(1);
    }
});
