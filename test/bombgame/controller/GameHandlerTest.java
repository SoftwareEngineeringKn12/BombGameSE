package bombgame.controller;

import java.util.ArrayList;
import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public final class GameHandlerTest extends TestCase {

	GameHandler gh1;
	GameHandler gh2;
	GameHandler gh3;
	GameHandler gh4;
	GameHandler gh5;
	
	public void setUp() {
		
		gh1 = new GameHandler(new GameObject[10][10]);
		gh2 = new GameHandler(new GameObject[2][3]);
		gh3 = new GameHandler(2, 3);
		gh4 = new GameHandler();
		gh5 = new GameHandler(new GameObject[3][3]);
	}
	
	public void testGetField() {
		assertNotNull(gh1.getField());
	}
	
	public void testGetMen() {
		assertNotNull(gh1.getMen());
	}
	
	public void testGetBombs() {
		assertNotNull(gh1.getBombs());
	}
	
	public void testGetExplosion() {
		Explosion exp = new Explosion(3,4);
		assertNull(gh1.getExplosion(exp));
		gh1.addObject(exp);
		assertNotNull(gh1.getExplosion(exp));
		exp = new Explosion(2,1);
		assertNull(gh1.getExplosion(exp));
	}
	
	public void testGetExplosionList() {
		assertNotNull(gh1.getExplosionList());
	}
	
	public void testAddObject() {
		GameObject o1 = new Wall(3,4);
		gh1.addObject(o1);
		assertSame(o1, gh1.getField()[3][4]);
		
		GameObject o2 = new Man(2,7);
		gh1.addObject(o2);
		assertSame(o2, gh1.getField()[2][7]);
		
		GameObject o3 = new Bomb(1,8);
		gh1.addObject(o3);
		assertSame(o3, gh1.getField()[1][8]);
		
		
		GameObject o4 = new Man(3,4);
		gh1.addObject(o4);
		assertSame(o4, gh1.getField()[4][4]);
		
		gh1.addObject(new Wall(9,9));
		
		GameObject o5 = new Man(9,9);
		gh1.addObject(o5);
		assertSame(o5, gh1.getField()[8][9]);
		
		gh1.addObject(new Wall(9,5));
		gh1.addObject(new Wall(8,5));
		
		GameObject o6 = new Man(9,5);
		gh1.addObject(o6);
		assertSame(o6, gh1.getField()[9][6]);
		
		gh1.addObject(new Wall(0,9));
		gh1.addObject(new Wall(1,9));
		
		GameObject o7 = new Man(0,9);
		gh1.addObject(o7);
		assertSame(o7, gh1.getField()[0][8]);
		//###########################
		
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				gh5.addObject(new Wall(i,j));
			}
		}
		GameObject o9 = new Man(1,1);
		gh5.addObject(o9);
		assertSame(o9, gh5.getField()[1][1]);
		//because (3/4) already used by o1, don't add 
		//GameObject o4 = new Explosion(3,4);
		//gh1.addObject(o4);
		//assertNotSame(o4, gh1.getField()[3][4]);
		
		GameObject o27 = new Explosion(2,4);
		gh1.addObject(o27);
		assertSame(o27, gh1.getField()[2][4]);
		
		
	}
	
	public void testRemoveObject() {
		GameObject o1 = new Wall(3,4);
		gh1.addObject(o1);
		gh1.removeObject(o1);
		assertNull(gh1.getField()[3][4]);
		
		GameObject o2 = new Man(6,3);
		gh1.addObject(o2);
		gh1.removeObject(o2);
		assertNull(gh1.getField()[6][3]);
		
		GameObject o3 = new Bomb(2,7);
		gh1.addObject(o3);
		gh1.removeObject(o3);
		assertNull(gh1.getField()[2][7]);
		
		GameObject o4 = new Explosion(1,5);

		gh1.addObject(o4);
		gh1.removeObject(o4);
		assertNull(gh1.getField()[1][5]);
	}
	
	public void testMoveMan() {
		
		Man man = new Man(0,1);
		
		gh2.addObject(man);
		gh2.addObject(new Wall(0,0));
		gh2.addObject(new Wall(0,2));
		
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.NO_DIR);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		Wall w = new Wall(0,1);
		gh2.addObject(w);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		gh2.removeObject(w);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		gh2.addObject(new Wall(1,1));
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(-1);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		
		//#############
		man = new Man(1,1);
		gh1.addObject(man);
		gh1.addObject(new Man(0,1));
		gh1.addObject(new Man(1,0));
		gh1.addObject(new Man(1,2));
		gh1.addObject(new Man(2,1));
		
		
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh1.moveMan(man);
		man.setDirection(Man.RIGHT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.RIGHT);
		gh1.moveMan(man);
		man.setDirection(Man.DOWN);
		gh1.moveMan(man);
		assertEquals(man.getX(), 2);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh1.moveMan(man);
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		man.setDirection(Man.UP);
		gh1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
	}
	
	public void testNextExplosion() {
		gh1.addObject(new Wall(3,4));
		ArrayList<Explosion> al = new ArrayList<Explosion>();
		assertFalse(gh1.nextExplosion(-1, 0, true, al));
		assertFalse(gh1.nextExplosion(15, 0, true, al));
		assertFalse(gh1.nextExplosion(-0, -1, true, al));
		assertFalse(gh1.nextExplosion(0, 15, true, al));
	}
	
	public void testMoveAll() {
		gh1.addObject(new Man(1,1));
		Man m = new Man(1,2);
		m.setDirection(Man.LEFT);
		gh1.addObject(m);
		gh1.addObject(new Explosion(0,0));
		gh1.moveAll();
		gh1.moveAll();
		assertEquals(gh1.getMen().size(), 1);
	}
	
	public void testPlaceBombs() {
		Man m = new Man(0,0);
		m.setPlaceBomb(true);
		gh1.addObject(new Man(1,1));
		gh1.addObject(m);
		gh1.placeBombs();
		assertEquals(gh1.getBombs().size(),1);
		
	}
	
	public void testUpdateBombs() {
		Bomb b = new Bomb(1,1);
		gh1.addObject(b);
		while(b.getTimer() > 0) {
			gh1.updateBombs();
		}
		gh1.updateBombs();
		
		assertEquals(gh1.getBombs().size(),0);
	}
	
	public void testUpdateExplosion() {
		
		Explosion exp = new Explosion(1,1);
		gh1.addObject(exp);
		
		while(exp.getTimer() > 0) {
			gh1.updateExplosion();
		}
		
		assertEquals(gh1.getExplosionList().size(),0);
		
		
	}
	
	public void testExplosionToString() {
		gh1.addObject(new Explosion(0,0));
		gh1.addObject(new Explosion(9,9));
		String s1 = gh1.explosionListToString();
		String s2 = "->Explosion: { [0] [0], [1] [0], [0] [1], [2] [0], [0] [2], [3] [0], [0] [3] }\n->Explosion: { [9] [9], [8] [9], [9] [8], [7] [9], [9] [7], [6] [9], [9] [6] }\n";
		assertEquals(s1,s2);
	}
}
