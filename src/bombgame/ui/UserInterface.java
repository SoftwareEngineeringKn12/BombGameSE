package bombgame.ui;

public interface UserInterface {
	/**
	 * Updates everything in the UserInterface.
	 * @return - true if game should continue
	 */
	boolean update(String str); //!! Do we need this?? because GUI update is not called in loop!
}
