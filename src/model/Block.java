package model;

public class Block extends GameObject {
	private boolean isEmpty;

	public Block(int x, int y, int height, int width, String imagePath,
			boolean isEmpty) {
		super(x, y, height, width, imagePath);
		this.isEmpty = isEmpty;
	}

	public boolean isBlockLevelBorder() {
		if(this.x <= 16 || this.x >= 224 || this.y == 0 || this.y == 240){
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}
}
