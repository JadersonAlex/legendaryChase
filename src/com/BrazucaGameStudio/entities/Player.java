package com.BrazucaGameStudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.BrazucaGameStudio.graficos.Spritesheet;
import com.BrazucaGameStudio.main.Game;
import com.BrazucaGameStudio.world.Camera;
import com.BrazucaGameStudio.world.World;

public class Player extends Entity{
	
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1;
	public int up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private boolean arma = false;
	//private BufferedImage playerDamage;
	
	//public boolean isDamaged = false;
	//private int damageFrames = 0;
	
	public int ammo = 0;
	
	public double life = 100, maxlife =100; // vida do jogador
	
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		
		//playerDamage = Game.spritesheet.getSprite(0,16,16,16);
		
		for(int i =0; i < 4; i++){
		rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
     	}
		
		for(int i =0; i < 4; i++){
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
	     	}
		
		for(int i =0; i < 3; i++){
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
	     	}
		for(int i =0; i < 3; i++){
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
	     	}
		
		
	}

	public void tick() 
	{
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())){
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		
		else if (left && World.isFree((int)(x-speed),this.getY()))
		{
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			dir = up_dir;
			moved = true;
			y-=speed;
		}
		else if (down && World.isFree(this.getX(),(int)(y+speed))){
			dir = down_dir;
			moved = true;
			y+=speed;
		}
		
		if(moved) 
		{

         if(dir == right_dir || dir == left_dir){
          maxIndex = 3;
          
          }else{
           maxIndex = 2;
          }

		    frames++;
			if(frames == maxFrames) 
			{
				frames = 0;
				index++;
			}

			if(index > maxIndex)
				index = 0;
			
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		/*if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
			  this.damageFrames = 0;
			  isDamaged = false;
			
			  }
			*/
	
		//}
		
		if(life<= 0) {
			/*Game.entities.clear();
			Game.enemies.clear();
			Game.entities = new ArrayList<Entity>();
	    	Game.enemies = new ArrayList<Enemy>();
	    	Game.spritesheet = new Spritesheet("/spritesheet.png");
	    	Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0,16, 16));
	    	Game.entities.add(Game.player);
	    	Game.world = new World("/map.png");
	    	
	    	return;
	    	*/
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
public void checkCollisionGun(){
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon ) {
				if(Entity.isColidding(this, atual)) {
					arma = true; // estou segurando a arma
					//System.out.println("pegou a arma");
					Game.entities.remove(atual);
				}
			}
		}
		
	}

	public void checkCollisionAmmo(){
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet ) {
				if(Entity.isColidding(this, atual)) {
					ammo+=10;
					//System.out.println("Municao atual:" + ammo );
					Game.entities.remove(atual);
				}
			}
		}
		
	}
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack ) {
				if(Entity.isColidding(this, atual)) {
					life+=10;
					if(life > 100)
						life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void render(Graphics g) 
	{
	 //if(!isDamaged) {
		if(dir == right_dir) 
		{
		g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
       	if(arma) {
       		
       		// desenhar arma para a direita
       		g.drawImage(Entity.GUN_RIGHT, this.getX() + 8 - Camera.x, this.getY() - Camera.y, null);
       	}
		}
		else if(dir == left_dir) 
		{
			g.drawImage(leftPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			if(arma) {
				// desenhar arma para a esquerda
				g.drawImage(Entity.GUN_LEFT, this.getX() -8 - Camera.x, this.getY() - Camera.y, null);
			}
		}
		
	//	}else {
			
		//	g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
	//	}
		
		
		if(dir == up_dir) 
		{
			g.drawImage(upPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			if(arma) {
				// desenhar arma para a esquerda
				g.drawImage(Entity.GUN_UP, this.getX() +5 - Camera.x, this.getY() -6 - Camera.y, null);
			}
		}
		
		if(dir == down_dir) 
		{
		g.drawImage(downPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
       	
		if(arma) {
			// desenhar arma para a esquerda
			g.drawImage(Entity.GUN_DOWN, this.getX() - Camera.x, this.getY() +6 - Camera.y, null);
		}
		}
	}
}
