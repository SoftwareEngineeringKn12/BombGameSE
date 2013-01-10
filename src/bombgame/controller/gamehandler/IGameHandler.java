package bombgame.controller.gamehandler;

import java.util.List;
import java.util.Observer;

import bombgame.controller.ai.IManAI;
import bombgame.controller.player.IPlayer;

public interface IGameHandler {

	void updateAll();
	IPlayer getPlayer();
	List<IManAI> getAIs();
	void addObserver(Observer observer);

}
