package bombgame.entities;

public interface IExplosion extends IGameObject {

	void decrementTimer();

	int getTimer();

}
