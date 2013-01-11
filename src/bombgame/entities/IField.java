package bombgame.entities;

import java.util.List;

public interface IField {

	IGameObject[][] getField();
	List<IBomb> getBombs();
	List<IMan> getMen();
	List<List<IExplosion>> getExplosionList();
	int getHeight();
	int getWidth();
}
