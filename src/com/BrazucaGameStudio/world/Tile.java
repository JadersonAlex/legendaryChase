package com.BrazucaGameStudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.BrazucaGameStudio.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR =  Game.spritesheet.getSprite(0, 0, 16, 16); // ch?o
	public static BufferedImage TILE_WALL =  Game.spritesheet.getSprite(16, 0, 16, 16); // parede
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) 
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	public void render(Graphics g) 
	{
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
	}
} 
