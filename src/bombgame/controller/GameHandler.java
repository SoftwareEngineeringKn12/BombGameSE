package bombgame.controller;

import java.util.ArrayList;

import bombgame.entities.Bomb;
import bombgame.entities.GameObject;
import bombgame.entities.Man;

public final class GameHandler {
	
	private GameObject field[][];
	private ArrayList<Man> men;
	private ArrayList<Bomb> bombs;
	
	public GameHandler() {
		field = new GameObject[10][10];
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
	}
	
	public void addObject(GameObject obj) {
		if (field[obj.getX()][obj.getY()] != null) { 
			return;
		}
		
		if (obj instanceof Man) {
			men.add((Man) obj);
		}
		
		if (obj instanceof Bomb) {
			bombs.add((Bomb) obj);
		}
		
		field[obj.getX()][obj.getY()] = obj;
	}
	
	public void removeObject(GameObject obj) {
		if (obj instanceof Man) {
			men.remove((Man) obj);
		}
		
		if (obj instanceof Bomb) {
			bombs.remove((Bomb) obj);
		}
		
		field[obj.getX()][obj.getY()] = null;
	}
	
	public GameObject[][] getField() {
		return field;
	}
	
	public ArrayList<Man> getMen() {
		return men;
	}
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
}
