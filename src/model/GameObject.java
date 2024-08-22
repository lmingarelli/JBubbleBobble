package model;

import java.awt.Rectangle;

public class GameObject {
	protected int x;
	protected int y;
	protected int height;
	protected int width;
	protected String imagePath;

	public GameObject(int x, int y, int height, int width, String imagePath) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.imagePath = imagePath;
	}

	// Getter and setter methods -------------------------------------------------

	public Rectangle getBorders() {
		return new Rectangle(this.x, this.y, width, height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
