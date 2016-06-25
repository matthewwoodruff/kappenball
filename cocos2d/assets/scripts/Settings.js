var stochasticity = 0;
var maxInterventionProbability = 1;
var maxStabilityProbability = 1;

function increase(value) {
  return Math.min(value + 0.1, 1);
}

function decrease(value) {
  return Math.max(value - 0.1, 0);
}

module.exports = {
    increaseMaxInterventionProbability: function() {
        maxInterventionProbability = increase(maxInterventionProbability);
    },

    decreaseMaxInterventionProbability: function() {
        maxInterventionProbability = decrease(maxInterventionProbability);
    },

    increaseMaxStabilityProbability: function() {
        maxStabilityProbability = increase(maxStabilityProbability);
    },

    decreaseMaxStabilityProbability: function() {
        maxStabilityProbability = decrease(maxStabilityProbability);
    },

    increaseStochasticity: function() {
       stochasticity = increase(stochasticity);
    },

    decreaseStochasticity: function() {
      stochasticity = decrease(stochasticity);
    },

    getMaxStabilityProbability: function() {
      return maxStabilityProbability;
    },

    getMaxInterventionProbability: function() {
      return maxInterventionProbability;
    },

    getStochasticity: function() {
      return stochasticity;
    }
}
