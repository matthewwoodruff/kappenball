package uk.ac.sheffield.dcs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.ac.sheffield.dcs.KappenballGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Kappenball";
        config.width = 900;
        config.height = 450;
        config.resizable = true;
        new LwjglApplication(new KappenballGame(), config);
	}
}
