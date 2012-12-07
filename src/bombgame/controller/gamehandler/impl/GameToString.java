package bombgame.controller.gamehandler.impl;

import bombgame.controller.ai.ManAI;
import bombgame.entities.Bomb;

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
		StringBuilder sb = new StringBuilder("Players:\n");
		sb.append(handler.getPlayer());
		return sb.toString();
	}
	
	protected String aiToString() {
		StringBuilder sb = new StringBuilder("AI:\n");
		for( ManAI ai : handler.getAIs()) {
			sb.append(ai);
		}
		return sb.toString();
	}
	
	protected String bombsToString() {
		StringBuilder sb = new StringBuilder("Bombs:\n");
		for(Bomb b : handler.getBombs()) {
			sb.append(b);
		}
		return sb.toString();
	}
	
	protected String explosionToString() {
		return "";
	}
	
	protected String fieldToString() {
		return "";
	}
}
