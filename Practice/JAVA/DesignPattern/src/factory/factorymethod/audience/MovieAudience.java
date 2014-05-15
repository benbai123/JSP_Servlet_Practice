package factory.factorymethod.audience;

import factory.factorymethod.player.*;

/** Movie audience, have a movie player that can play movie
 * 
 * @author benbai123
 *
 */
public class MovieAudience extends Audience {
	protected Player getPlayer () {
		return new MoviePlayer();
	}
}
