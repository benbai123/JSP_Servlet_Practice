package factory.factorymethod.audience;

import factory.factorymethod.player.Player;

/** Basic Audience class, have a player that can play something to enjoy.
 * The exact class of player will be created by subclass
 * 
 * @author benbai123
 *
 */
public abstract class Audience {
	private Player _player;
	public void enjoy () {
		_player = getPlayer();
		_player.play();
		System.out.println("Enjoy!");
	}
	protected abstract Player getPlayer () ;
}
