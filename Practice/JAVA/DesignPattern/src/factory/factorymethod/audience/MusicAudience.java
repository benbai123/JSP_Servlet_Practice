package factory.factorymethod.audience;

import factory.factorymethod.player.*;

/** Music audience, has a music player that can play music
 * 
 * @author benbai123
 *
 */
public class MusicAudience extends Audience {
	protected Player getPlayer () {
		return new MusicPlayer();
	}
}
