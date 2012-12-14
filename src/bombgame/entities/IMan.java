package bombgame.entities;

public interface IMan extends IGameObject {

	int getDirection();

	IBomb placeBomb();

	boolean getPlaceBomb();

	void setPlaceBomb(boolean b);

	void setDirection(int noDir);

}
