package bombgame.entities;

public interface IBomb extends IGameObject {

	IExplosion decrementTimer();

}
