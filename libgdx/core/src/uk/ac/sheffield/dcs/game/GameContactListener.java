package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import uk.ac.sheffield.dcs.world.FixtureType;

import static uk.ac.sheffield.dcs.world.FixtureType.BALL;
import static uk.ac.sheffield.dcs.world.FixtureType.END;
import static uk.ac.sheffield.dcs.world.FixtureType.SPIKE;

public class GameContactListener implements Contact§Listener {

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
