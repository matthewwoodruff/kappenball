package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.physics.box2d.*;

import static uk.ac.sheffield.dcs.game.FixtureType.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        FixtureType fixtureTypeA = (FixtureType) fixtureA.getUserData();
        FixtureType fixtureTypeB = (FixtureType) fixtureB.getUserData();

        Ball ball = (Ball) (fixtureTypeA == BALL ? fixtureA : fixtureB).getBody().getUserData();
        FixtureType fixtureType = fixtureTypeA == BALL ? fixtureTypeB : fixtureTypeA;

        if (fixtureType == SPIKE)
            ball.spike();
        else if (fixtureType == END)
            ball.end();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
