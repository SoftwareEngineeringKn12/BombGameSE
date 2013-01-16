package bombgame.entities.impl;

import java.util.LinkedList;
import java.util.List;

import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IField;
import bombgame.entities.IGameObject;
import bombgame.entities.IMan;

public class Field implements IField{
	
	/**
	 * matrix holding all GameObjects in the game.
	 * The array indices specify the position on the field.
	 */
	private IGameObject field[][];
	
	/**
	 * List holding all Man-objects in the game.
	 */
	private List<IMan> men;
	
	/**
	 * List holding all Bomb-objects in the game.
	 */
	private List<IBomb> bombs;
	
	/**
	 * list of explosions
	 */
	private List<List<IExplosion>> explosions;
	
	public Field(int width, int height) {
		this.field = new IGameObject[width][height];
		this.bombs = new LinkedList<IBomb>();
		this.men = new LinkedList<IMan>();
		this.explosions = new LinkedList<List<IExplosion>>();
		
	}
	
	public Field(final IGameObject[][] f) {
		this.field = new GameObject[f.length][f[0].length];
		for( int i = 0; i < f.length; i++) {
			for( int j = 0; j < f[0].length; j++) {
				field[i][j] = f[i][j];
			}
		}	
		this.bombs = new LinkedList<IBomb>();
		this.men = new LinkedList<IMan>();
		this.explosions = new LinkedList<List<IExplosion>>();
	}
	
	/**
	 * Returns the matrix of the field.
	 * @return - matrix of the field
	 */
	@Override
	public IGameObject[][] getField() {
		return field;
	}

	/**
	 * Returns the List of Bomb-objects.
	 * @return - List of Bomb-objects
	 */
	@Override
	public List<IBomb> getBombs() {
		return bombs;
	}

	/**
	 * Returns the List of Man-objects.
	 * @return - List of Man-objects
	 */
	@Override
	public List<IMan> getMen() {
		return men;
	}

	/**
	 * Returns the List of Lists of Explosions.
	 * @return - List of Lists of Explosions
	 */
	@Override
	public List<List<IExplosion>> getExplosionList() {
		return explosions;
	}
	
	@Override
	public int getWidth() {
		return field.length;
	}
	@Override
	public int getHeight() {
		return field[0].length;
	}

}
