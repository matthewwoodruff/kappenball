var stochasticity = 1;
var maxInterventionProbability = 0;
var maxStabilityProbability = 0;

function increase(value) {
  return Math.min(value + 0.1, 1);
}

function decrease(value) {
  return Math.max(value - 0.1, 0);
}

function likelihoodFor(value) {
  return Math.random() <= stochasticity * value * 0.02;
}

module.exports = {
    increaseMaxInterventionProbability: function() {
        return maxInterventionProbability = increase(maxInterventionProbability);
    },

    decreaseMaxInterventionProbability: function() {
        return maxInterventionProbability = decrease(maxInterventionProbability);
    },

    increaseMaxStabilityProbability: function() {
        return maxStabilityProbability = increase(maxStabilityProbability);
    },

    decreaseMaxStabilityProbability: function() {
        return maxStabilityProbability = decrease(maxStabilityProbability);
    },

    increaseStochasticity: function() {
       return stochasticity = increase(stochasticity);
    },

    decreaseStochasticity: function() {
      return stochasticity = decrease(stochasticity);
    },

    shouldIntervene: function() {
      return likelihoodFor(maxInterventionProbability);
    },

    shouldStabilise: function() {
      return likelihoodFor(maxStabilityProbability);
    },

    getStochasticity: function() {
      return stochasticity;
    },

    getMaxStabilityProbability: function() {
      return maxStabilityProbability;
    },

    getMaxInterventionProbability: function() {
      return maxInterventionProbability;
    }
}
