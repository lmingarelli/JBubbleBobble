package controller;

import model.GameModel;
import view.GameView;

@SuppressWarnings("deprecation")
public class JBubbleBobble {
	public static void main(String[] args) {
		// Creating the view instance
		GameView gameView = GameView.getInstance();

		// Creating the model instance
		GameModel gameModel = GameModel.getInstance();

		// Creating the controller instance
		GameController gameController = GameController.getInstance();

		// Post main constructors operations
		gameView.buildCardLayout();

		// Gameloop
		while (true){
			gameView.setVisible(true);

			try{
				Thread.sleep(1000 / 60);
			}catch (InterruptedException e){
				e.printStackTrace();
			}

			if(gameModel.getGameState() == model.GameState.GAME){
				gameController.updateController();
				gameModel.updateModel();
			}
		}
	}
}
