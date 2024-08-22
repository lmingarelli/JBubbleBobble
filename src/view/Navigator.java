package view;

import java.util.Observable;

import model.GameState;

@SuppressWarnings("deprecation")
public class Navigator extends Observable {
	private static Navigator instance;

	private Navigator() {}

	public static Navigator getInstance() {
		if(instance == null) instance = new Navigator();
		return instance;
	}

	public void navigate(GameState state) {
		setChanged();
		notifyObservers(state);
	}
}
