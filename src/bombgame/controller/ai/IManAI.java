package bombgame.controller.ai;

import bombgame.entities.IMan;

public interface IManAI {

	void calcNextStep();
	IMan getMan();
}
