import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class Player {
    public int x, y, velocityX, velocityY, airTime = 0, storedVelocityY = 0;
    public Random rng = new Random();
    public boolean onSurface = false;
    //Player Configuration (Yippee!!)
    public final static int PLAYER_WIDTH = 25;
    public final static int PLAYER_HEIGHT = 25;
    public final static int PLAYER_JUMP_FORCE = 33;

    public final static int TEST_FLOOR = -800;
    public final static int MAX_HP = 100;
    public static int hp = MAX_HP;

    private boolean godMode = false;
    private boolean buildMode = false;

    public String identifier;
    public Player(int x, int y, String identifier) {
        this.x = x;
        this.y = y;
        this.identifier = identifier;

    }

    public int getHP() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update() {

        if (hp <= 0) {
            hp = MAX_HP;
            ////AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\CrowdAww.wav");
            //song.setUp();
            //song.play();
            x=0;y=0;
        }

        //Velocity Limiters (Decrease over time)
        if (velocityX > 0)
            velocityX--;
        else if (velocityX < 0)
            velocityX++;

        //Velocity Caps
        if (velocityX > 18)
            velocityX = 18;
        else if (velocityX < -18)
            velocityX = -18;

        boolean gravitySwitch = true;
        if (gravitySwitch) {
            if (velocityY > 0) {
                velocityY-=(int) 1+(airTime*0.1);
                airTime++;
            }
            else if (y>TEST_FLOOR) {
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

        if (y <= TEST_FLOOR) {
            hp-=25;
        }

        //Velocity Application
        shapeCollisions();
    }

    public boolean spaceHeld = false, shiftHeld = false, f1Held = false, spawnHeld = false;
    public int stepCooldown = 0;
    public void keyBinds() {
        //Walking
        if (glfwGetKey(Main.window, GLFW_KEY_D) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_RIGHT) == GL_TRUE) {
            if (!(velocityX > 9))
                this.velocityX += 9;
            if (stepCooldown == 0 && onSurface) {
                int ran = rng.nextInt(3);
                if (ran==0) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy1.wav");
                    //song.setUp();
                    //song.play();
                }
                else if (ran==1) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy2.wav");
                    //song.setUp();
                    //song.play();
                }
                else if (ran==2) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy3.wav");
                    //song.setUp();
                    //song.play();
                }
                else {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy4.wav");
                    //song.setUp();
                    //song.play();
                }

				
                stepCooldown += 20;
            }
        }
        if (glfwGetKey(Main.window, GLFW_KEY_A) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_LEFT) == GL_TRUE) {
            if (!(velocityX < -6))
                this.velocityX -= 6;
            if (stepCooldown == 0 && onSurface) {
                int ran = rng.nextInt(3);
                if (ran==0) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy1.wav");
                    //song.setUp();
                    //song.play();
                }
                else if (ran==1) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy2.wav");
                    //song.setUp();
                    //song.play();
                }
                else if (ran==2) {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy3.wav");
                    //song.setUp();
                    //song.play();
                }
                else {
                    //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\footstep_heavy4.wav");
                    //song.setUp();
                    //song.play();
                }

				
                stepCooldown += 20;
            }
        }
        if (stepCooldown > 0)
            stepCooldown--;

        //Dash
        if (glfwGetKey(Main.window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE && (glfwGetKey(Main.window, GLFW_KEY_D) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_RIGHT) == GL_TRUE)) 
            this.velocityX ++;
        if (glfwGetKey(Main.window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE && (glfwGetKey(Main.window, GLFW_KEY_A) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_LEFT) == GL_TRUE))
            this.velocityX --;

        //Jumping
        if (velocityY == 0 &&!spaceHeld &&onSurface && (glfwGetKey(Main.window, GLFW_KEY_SPACE) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_UP) == GL_TRUE)) {
            if (onSurface) {
                this.velocityY += PLAYER_JUMP_FORCE; 
                onSurface = false;
                //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\Dodge2.wav");
                //song.setUp();
                //song.play();
            }
            spaceHeld = true;
        }
        else if ((!(glfwGetKey(Main.window, GLFW_KEY_SPACE) == GL_TRUE && glfwGetKey(Main.window, GLFW_KEY_UP) == GL_TRUE)))
            spaceHeld = false;

        if (!f1Held&&glfwGetKey(Main.window, GLFW_KEY_F1)==GL_TRUE ) {
            f1Held = true; buildMode = true;
        }
        else
            f1Held = false;

        //if (glfwGetKey(Main.window, GLFW_MOUSE_BUTTON_1)==GL_TRUE) { 

        //}

        if ((glfwGetKey(Main.window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE || glfwGetKey(Main.window, GLFW_KEY_E) == GL_TRUE)&&!spawnHeld) {
            Main.Enemies.add(new Enemy());
            spawnHeld = true;
        }
        else
            spawnHeld = false;

		
    }

    public void render() {
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // White color
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(Main.translateF(-PLAYER_WIDTH+this.x, "width"), Main.translateF(-PLAYER_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(PLAYER_WIDTH+this.x, "width"), Main.translateF(-PLAYER_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(PLAYER_WIDTH+this.x, "width"), Main.translateF(PLAYER_HEIGHT+y, "height"));
        GL11.glVertex2f(Main.translateF(-PLAYER_WIDTH+this.x, "width"), Main.translateF(PLAYER_HEIGHT+y, "height"));
        GL11.glEnd();

    }

    public void shapeCollisions() {
        for (Shape childShape : Main.Shapes) {

            if (childShape.collidable&&((x+velocityX + PLAYER_WIDTH > childShape.x - childShape.width &&
                    x+velocityX + PLAYER_WIDTH < childShape.x + childShape.width) ||
                (x+velocityX - PLAYER_WIDTH > childShape.x - childShape.width && //Its cool how I can place this here innit?
                    x+velocityX - PLAYER_WIDTH < childShape.x + childShape.width)) &&
            ((y+velocityY + PLAYER_HEIGHT > childShape.y - childShape.height &&
                    y+velocityY + PLAYER_HEIGHT < childShape.y + childShape.height) ||
                (y+velocityY - PLAYER_HEIGHT > childShape.y - childShape.height &&
                    y+velocityY - PLAYER_HEIGHT < childShape.y + childShape.height))
            ) {

                //System.out.println("Inside shape collider!!");//!(y - PLAYER_HEIGHT < childShape.y + childShape.height) &&
                //Y Shape collisions
                if ((y - PLAYER_HEIGHT >= childShape.y + childShape.height &&  (x + PLAYER_WIDTH >= childShape.x - childShape.width && x - PLAYER_WIDTH <= childShape.x + childShape.width) 
                    && (x - PLAYER_WIDTH != childShape.x + childShape.width && x + PLAYER_WIDTH != childShape.x - childShape.width)  
                    && !(x + PLAYER_WIDTH <= childShape.x - childShape.width && x - PLAYER_WIDTH >= childShape.x + childShape.width)) 
                ) {
                    if (velocityY <= -50) {
                        //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\Impacts_SIMPLE_003.wav");
                        //song.setUp();
                        //song.play();
                    }
                    y = childShape.y + childShape.height + PLAYER_HEIGHT;
                    velocityY = 0;
                    airTime = 0;
                    onSurface = true;
                    //System.out.println("Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else if (y + PLAYER_HEIGHT < childShape.y - childShape.height) {
                    y = childShape.y - childShape.height - PLAYER_HEIGHT;
                    velocityY = 0;
                    //System.out.println("REVERSE Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
                }

                //X Shape Collisions
                else if (x - PLAYER_WIDTH > childShape.x + childShape.width/2 && velocityX < 0 && !(y - PLAYER_HEIGHT >= childShape.y+ childShape.height) ) { //  && (!(y - PLAYER_HEIGHT >= childShape.height))
                    x = childShape.x + childShape.width + PLAYER_WIDTH;
                    velocityX = 0;
                    //System.out.println("X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else if (x + PLAYER_WIDTH < childShape.x - childShape.width/2 && velocityX > 0  && !(y - PLAYER_HEIGHT >= childShape.y+ childShape.height) ) {  // 
                    x = childShape.x - childShape.width - PLAYER_WIDTH;
                    velocityX = 0;
                    //System.out.println("REVERSE X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
                }

				
				
				
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

		
		
		
		
		
		
        for (Enemy childShape : Main.Enemies) {

            if (childShape.collidable&&((x+velocityX + PLAYER_WIDTH > childShape.x - childShape.ENEMY_WIDTH &&
                    x+velocityX + PLAYER_WIDTH < childShape.x + childShape.ENEMY_WIDTH) ||
                (x+velocityX - PLAYER_WIDTH > childShape.x - childShape.ENEMY_WIDTH && //Its cool how I can place this here innit?
                    x+velocityX - PLAYER_WIDTH < childShape.x + childShape.ENEMY_WIDTH)) &&
            ((y+velocityY + PLAYER_HEIGHT > childShape.y - childShape.ENEMY_HEIGHT &&
                    y+velocityY + PLAYER_HEIGHT < childShape.y + childShape.ENEMY_HEIGHT) ||
                (y+velocityY - PLAYER_HEIGHT > childShape.y - childShape.ENEMY_HEIGHT &&
                    y+velocityY - PLAYER_HEIGHT < childShape.y + childShape.ENEMY_HEIGHT))
            ) {

                //System.out.println("Inside shape collider!!");//!(y - PLAYER_HEIGHT < childShape.y + childShape.height) &&
                //Y Shape collisions
                if ((y - PLAYER_HEIGHT >= childShape.y + childShape.ENEMY_HEIGHT &&  (x + PLAYER_WIDTH >= childShape.x - childShape.ENEMY_WIDTH && x - PLAYER_WIDTH <= childShape.x + childShape.ENEMY_WIDTH) 
                    && (x - PLAYER_WIDTH != childShape.x + childShape.ENEMY_WIDTH && x + PLAYER_WIDTH != childShape.x - childShape.ENEMY_WIDTH)  
                    && !(x + PLAYER_WIDTH <= childShape.x - childShape.ENEMY_WIDTH && x - PLAYER_WIDTH >= childShape.x + childShape.ENEMY_WIDTH)) 
                ) {
                    if (velocityY <= -50) {
                        //AudioManager //song = new //AudioManager(":\\AbsRes\\Audio\\Impacts_SIMPLE_003.wav");
                        //song.setUp();
                        //song.play();
                    }
                    y = childShape.y + childShape.ENEMY_HEIGHT + PLAYER_HEIGHT;
                    velocityY = 0;
                    airTime = 0;
                    onSurface = true;
                    //System.out.println("Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else if (y + PLAYER_HEIGHT < childShape.y - childShape.ENEMY_HEIGHT) {
                    y = childShape.y - childShape.ENEMY_HEIGHT - PLAYER_HEIGHT;
                    velocityY = 0;
                    //System.out.println("REVERSE Y COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!");
                }

                //X Shape Collisions
                else if (x - PLAYER_WIDTH > childShape.x + childShape.ENEMY_WIDTH/2 && velocityX < 0 && !(y - PLAYER_HEIGHT >= childShape.y+ childShape.ENEMY_HEIGHT) ) { //  && (!(y - PLAYER_HEIGHT >= childShape.height))
                    x = childShape.x + childShape.ENEMY_WIDTH + PLAYER_WIDTH;
                    velocityX = 0;
                    //System.out.println("X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else if (x + PLAYER_WIDTH < childShape.x - childShape.ENEMY_WIDTH/2 && velocityX > 0  && !(y - PLAYER_HEIGHT >= childShape.y+ childShape.ENEMY_HEIGHT) ) {  // 
                    x = childShape.x - childShape.ENEMY_WIDTH - PLAYER_WIDTH;
                    velocityX = 0;
                    //System.out.println("REVERSE X COLLISION RAN!!!!!!!!!!!!!!!!!!!!!!!!!");
                }

				
				
				
            }

			
        }

    }

}
