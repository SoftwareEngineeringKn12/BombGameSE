package bombgame.controller.gamehandler;

import java.util.List;

import bombgame.entities.IBomb;
import bombgame.entities.IGameObject;
import bombgame.entities.IMan;

public interface IGameHandler2D {
	
	IGameObject[][] getField();
	List<IBomb> getBombs();
	List<IMan> getMen();

}
