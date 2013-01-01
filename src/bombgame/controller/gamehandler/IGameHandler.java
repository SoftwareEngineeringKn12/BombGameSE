package bombgame.controller.gamehandler;

import java.util.List;
import bombgame.controller.ai.IManAI;
import bombgame.controller.player.IPlayer;

public interface IGameHandler {

	public void updateAll();
	public IPlayer getPlayer();
	public List<IManAI> getAIs();

}
