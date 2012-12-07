package bombgame.controller.gamehandler.impl;

import java.util.List;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

public final class GameToString {

	/**
	 * GameHandler reference.
	 */
	private GameHandler handler;

	/**
	 * Creates the GameToString object with the gamehandler.
	 * @param gameHandler
	 */
	protected GameToString(GameHandler gameHandler) {
		handler = gameHandler;
	}

	/**
	 * Returns string representation of the game.
	 * @return - String representation
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(playerToString());
		sb.append(aiToString());
		sb.append(bombsToString());
		sb.append(explosionsToString());
		sb.append(fieldToString());
		
		return sb.toString();
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

	/**
	 * Returns a String-representation of the explosionlist of this GameHandler-object.
	 * @return - String of explosions
	 */
	protected String explosionsToString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Explosions:\n");
		
		for(List<Explosion> list : handler.getExplosionList()) {
			sb.append("->Explosion: { ");
			int i = 0;
			for(Explosion exp : list) {
				if(i < list.size() - 1) {
					sb.append("[").append(exp.getX()).append("] [").append(exp.getY()).append("], ");
				} else {
					sb.append("[").append(exp.getX()).append("] [").append(exp.getY()).append("] }\n");
				}
				i++;
			}
		}
		
		return sb.toString();
	}

	/**
	 * Return a String-representation of the game field.
	 * @return - String of game field (GameObject)
	 */
	protected String fieldToString() {
		StringBuilder sb = new StringBuilder();
		GameObject[][] field = handler.getField();

		for (int i = 0; i < field[0].length; i++) {

			for (int j = 0; j < field.length; j++) {

				if (field[j][i] instanceof Wall) {

					sb.append(" # ");

				} else if (field[j][i] instanceof Man) {

					sb.append(" M ");

				} else if (field[j][i] instanceof Bomb) {

					sb.append(" O ");

				} else if (field[j][i] instanceof Explosion) {

					sb.append(" X ");

				} else {

					sb.append(" - ");

				}
			}

			// next line
			sb.append("\n");
		}
		return sb.toString();
	}
}
