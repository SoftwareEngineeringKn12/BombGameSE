package bombgame.controller.player;

import bombgame.entities.IGameObject;

public interface IPlayer {

	IGameObject getMan();
	String toString();
	void move(char c);

}
