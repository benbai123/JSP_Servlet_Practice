package factory.factorymethod.player;

/** Movie player, can play movie.
 * 
 * @author benbai123
 *
 */
public class MoviePlayer implements Player {
	public void play () {
		System.out.println("Playing interesting movie...");
	}
}
