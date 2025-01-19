import org.lwjgl.opengl.GL11;

public class Enemy {
	private final int MAX_HP = 100;
	int hp = MAX_HP;
	
	public int x = 100;
	public int y = 100;
	public int velocityX = 0;
	public int velocityY = 0;
	public int airTime = 0;
	public boolean collidable = true;
	
	public int ENEMY_WIDTH = 35;
	public int ENEMY_HEIGHT = 35;
	
	boolean onSurface = false;
	boolean alive = true;
	
	public Enemy() {
		
	}
	public Enemy(int hpInstead) {
		hp = hpInstead;
	}
	
	public void update() {
		if (hp <= 0) {
			alive = false;
		}
		shapeCollisions();
	}
	
	public void render() {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 0.5f, 0.5f, 1.0f); // White color
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(Main.translateF(-ENEMY_WIDTH+this.x, "width"), Main.translateF(-ENEMY_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(ENEMY_WIDTH+this.x, "width"), Main.translateF(-ENEMY_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(ENEMY_WIDTH+this.x, "width"), Main.translateF(ENEMY_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(-ENEMY_WIDTH+this.x, "width"), Main.translateF(ENEMY_HEIGHT+y, "height"));
        GL11.glEnd();
	}
	
	public void shapeCollisions() {
		move();
		
		//Velocity Limiters (Decrease over time)
		if (velocityX > 0)
			velocityX--;
		else if (velocityX < 0)
			velocityX++;
		
		
		
		boolean gravitySwitch = true;
		if (gravitySwitch) {
			if (velocityY > 0) {
				velocityY-=(int) 1+(airTime*0.1);
				airTime++;
			}
			else if (y>Player.TEST_FLOOR) {
				velocityY-=(int) 1+(airTime*0.1);
				airTime++;
			}
			else {
				onSurface = true;
				airTime = 0;
				velocityY = 0;
				//if (y <= TEST_FLOOR)
					//y = TEST_FLOOR;
			}
		} 
		for (Shape childShape : Main.Shapes) {
			
			
			
			

			
			if (childShape.collidable&&((x+velocityX + ENEMY_WIDTH > childShape.x - childShape.width &&
				x+velocityX + ENEMY_WIDTH < childShape.x + childShape.width) ||
				(x+velocityX - ENEMY_WIDTH > childShape.x - childShape.width && //Its cool how I can place this here innit?
				x+velocityX - ENEMY_WIDTH < childShape.x + childShape.width)) &&
				((y+velocityY + ENEMY_HEIGHT > childShape.y - childShape.height &&
				y+velocityY + ENEMY_HEIGHT < childShape.y + childShape.height) ||
				(y+velocityY - ENEMY_HEIGHT > childShape.y - childShape.height &&
				y+velocityY - ENEMY_HEIGHT < childShape.y + childShape.height))
					) {
				
				
				//System.out.println("Inside shape collider!!");//!(y - PLAYER_HEIGHT < childShape.y + childShape.height) &&
				
				//Y Shape collisions
				if ((y - ENEMY_WIDTH >= childShape.y + childShape.height &&  (x + ENEMY_WIDTH >= childShape.x - childShape.width && x - ENEMY_WIDTH <= childShape.x + childShape.width) 
						&& (x - ENEMY_WIDTH != childShape.x + childShape.width && x + ENEMY_WIDTH != childShape.x - childShape.width)  
						&& !(x + ENEMY_WIDTH <= childShape.x - childShape.width && x - ENEMY_WIDTH >= childShape.x + childShape.width)) 
						) {
					y = childShape.y + childShape.height + ENEMY_HEIGHT;
					velocityY = 0;
					airTime = 0;
					onSurface = true;
					//System.out.println("Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				else if (y + ENEMY_HEIGHT < childShape.y - childShape.height) {
					y = childShape.y - childShape.height - ENEMY_HEIGHT;
					velocityY = 0;
					//System.out.println("REVERSE Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				
				//X Shape Collisions
				else if (x - ENEMY_WIDTH > childShape.x + childShape.width/2 && velocityX < 0 && !(y - ENEMY_HEIGHT >= childShape.y+ childShape.height) ) { //  && (!(y - PLAYER_HEIGHT >= childShape.height))
					x = childShape.x + childShape.width + ENEMY_WIDTH;
					velocityX = 0;
					//System.out.println("X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				else if (x + ENEMY_WIDTH < childShape.x - childShape.width/2 && velocityX > 0  && !(y - ENEMY_HEIGHT >= childShape.y+ childShape.height) ) {  // 
					x = childShape.x - childShape.width - ENEMY_WIDTH;
					velocityX = 0;
					//System.out.println("REVERSE X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				
				
				

				
				
				
			}
			
			
			
		}
		
		
		//Player Collisions
		if (((x+velocityX + ENEMY_WIDTH > Main.playerOne.x - Main.playerOne.PLAYER_WIDTH &&
				x+velocityX + ENEMY_WIDTH < Main.playerOne.x + Main.playerOne.PLAYER_WIDTH) ||
				(x+velocityX - ENEMY_WIDTH > Main.playerOne.x - Main.playerOne.PLAYER_WIDTH && //Its cool how I can place this here innit?
				x+velocityX - ENEMY_WIDTH < Main.playerOne.x + Main.playerOne.PLAYER_WIDTH)) &&
				((y+velocityY + ENEMY_HEIGHT > Main.playerOne.y - Main.playerOne.PLAYER_HEIGHT &&
				y+velocityY + ENEMY_HEIGHT < Main.playerOne.y + Main.playerOne.PLAYER_HEIGHT) ||
				(y+velocityY - ENEMY_HEIGHT > Main.playerOne.y - Main.playerOne.PLAYER_HEIGHT &&
				y+velocityY - ENEMY_HEIGHT < Main.playerOne.y + Main.playerOne.PLAYER_HEIGHT))
					) {
				
				
				//System.out.println("Inside shape collider!!");//!(y - PLAYER_HEIGHT < childShape.y + childShape.height) &&
				
				//Y Shape collisions
				if ((y - ENEMY_WIDTH >= Main.playerOne.y + Main.playerOne.PLAYER_HEIGHT &&  (x + ENEMY_WIDTH >= Main.playerOne.x - Main.playerOne.PLAYER_WIDTH && x - ENEMY_WIDTH <= Main.playerOne.x + Main.playerOne.PLAYER_WIDTH) 
						&& (x - ENEMY_WIDTH != Main.playerOne.x + Main.playerOne.PLAYER_WIDTH && x + ENEMY_WIDTH != Main.playerOne.x - Main.playerOne.PLAYER_WIDTH)  
						&& !(x + ENEMY_WIDTH <= Main.playerOne.x - Main.playerOne.PLAYER_WIDTH && x - ENEMY_WIDTH >= Main.playerOne.x + Main.playerOne.PLAYER_WIDTH)) 
						) {
					y = Main.playerOne.y + Main.playerOne.PLAYER_HEIGHT + ENEMY_HEIGHT;
					velocityY = 0;
					airTime = 0;
					onSurface = true;
					//System.out.println("Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				else if (y + ENEMY_HEIGHT < Main.playerOne.y - Main.playerOne.PLAYER_HEIGHT) {
					y = Main.playerOne.y - Main.playerOne.PLAYER_HEIGHT - ENEMY_HEIGHT;
					velocityY = 0;
					//System.out.println("REVERSE Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				
				//X Shape Collisions
				else if (x - ENEMY_WIDTH > Main.playerOne.x + Main.playerOne.PLAYER_WIDTH/2 && velocityX < 0 && !(y - ENEMY_HEIGHT >= Main.playerOne.y+ Main.playerOne.PLAYER_HEIGHT) ) { //  && (!(y - PLAYER_HEIGHT >= childShape.height))
					x = Main.playerOne.x + Main.playerOne.PLAYER_WIDTH + ENEMY_WIDTH;
					velocityX = 0;
					//System.out.println("X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				else if (x + ENEMY_WIDTH < Main.playerOne.x - Main.playerOne.PLAYER_WIDTH/2 && velocityX > 0  && !(y - ENEMY_HEIGHT >= Main.playerOne.y+ Main.playerOne.PLAYER_HEIGHT) ) {  // 
					x = Main.playerOne.x - Main.playerOne.PLAYER_WIDTH - ENEMY_WIDTH;
					velocityX = 0;
					//System.out.println("REVERSE X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
		
		
		x += velocityX;
		
		if (velocityY <= -50) {
			velocityY = -50;
			y += velocityY;
		}
			
		else if (velocityY >= 50) {
			velocityY = 50;
			y += velocityY;
		}
		else {
			y += velocityY;
		}
		
		
		
	}
	
	public void move() {
		if (onSurface) {
			if (y < Main.playerOne.y-10 && x-Main.playerOne.x > 20) {
				velocityY +=30;
				onSurface = false;
			}
			if (x > Main.playerOne.x) {
				x-=3;
			}
			else
				x+=3;
		}
		else
			if (x > Main.playerOne.x) {
				x--;
			}
			else
				x++;
		
	}
}
