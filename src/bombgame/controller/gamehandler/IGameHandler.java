package bombgame.controller.gamehandler;

import java.util.List;
import java.util.Observer;

import bombgame.controller.ai.IManAI;
import bombgame.controller.player.IPlayer;
import bombgame.entities.IGameObject;

public interface IGameHandler {

	public void updateAll();
	public IPlayer getPlayer();
	public List<IManAI> getAIs();
	public void addObserver(Observer observer);
	public IGameObject[][] getField();

}
