package bombgame.controller.gamehandler.impl;

public final class GameToString {

	private GameHandler handler;
	
	protected GameToString(GameHandler gameHandler) {
		handler = gameHandler;
	}
	
	/**
	 * Returns string representation of the game.
	 * @return - String representation
	 */
	@Override
	public String toString() {
		return "";
	}

	protected String playerToString() {
		return "";
	}
	
	protected String aiToString() {
		return "";
	}
	
	protected String bombsToString() {
		return "";
	}
	
	protected String explosionToString() {
		return "";
	}
	
	protected String fieldToString() {
		return "";
	}
}
