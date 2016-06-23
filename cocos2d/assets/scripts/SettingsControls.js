var settings = require('Settings');

cc.Class({
    extends: cc.Component,
    
    back: function() {
        cc.director.loadScene("Game");
    },
    
    increaseMaxInterventionProbability: function() {
        settings.maxInterventionProbability = Math.max(settings.maxInterventionProbability + 0.1, 1);
        this._updateDisplay();
    },
    
    decreaseMaxInterventionProbability: function() {
        settings.maxInterventionProbability = Math.max(settings.maxInterventionProbability - 0.1, 0);
        this._updateDisplay();
    },
    
    _updateDisplay: function () {
        // this.uncertaintyDisplay.string = settings.stochasticity;
    }
});
