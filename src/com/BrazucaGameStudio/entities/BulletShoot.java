package com.BrazucaGameStudio.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.BrazucaGameStudio.main.Game;
import com.BrazucaGameStudio.world.Camera;

public class BulletShoot extends Entity {

	private int dx;
	private int dy;
	
	private double spd = 4;
	
	private int life = 50, curLife = 0; 
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
	  super(x, y, width, height, sprite);
	  this.dx = dx;
	  this.dy = dy;
	 
	}
	
	public void tick() {
		x+=dx * spd;
		x+=dy * spd;
		curLife++;
		
		if(curLife == life)
		{
			Game.bullets.remove(this);
			return;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width,height);
	}
}
