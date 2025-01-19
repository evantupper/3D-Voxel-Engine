
/* Absolute Sin BETA
 * Authors: Evan Tupper, Chiron Bradford
 * Contributors:
 *
 * Notes:
 * - For any contributors: Please leave as many comments as you can to best explain your code.
 *
 *
 * To-Do:
 * - Collisions
 * - Basic stuff
 * 
 *  */
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;


import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.*;

public class Main {
	    // Standard Initialization
		public static long window;
		public static int WIDTH = 900;
		public static int HEIGHT = 900;
		public static ArrayList<Shape> Shapes = new ArrayList<>();
		public ArrayList<Light> BGLights = new ArrayList<>();
		public ArrayList<Light> Lights = new ArrayList<>(); 
		public static ArrayList<Enemy> Enemies = new ArrayList<>();
		public static Player playerOne = new Player(0,0,"void");
		
		// World
		public static final int WORLD_WIDTH = 3000;
		public static final int WORLD_HEIGHT = 3000;
		
		public static int cameraX = 0;
		public static int cameraY = 0;
		
		
		public static void main(String[] args) {
			new Main().run();
		}
		
		public void run() {
			System.out.println("[LOG] Running LWJGL version " + Version.getVersion());
			
			System.out.println("[LOG] Initializing");
			init(); //Initializing the window and setting up most of what we need.
			
			System.out.println("[LOG] Loop Entered");
			loop(); //Normal Tick Process
			
			

			//End of Program
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);

			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}

		
		private void init() {
			// Setup an error callback. The default implementation
			// will print the error message in System.err.
			GLFWErrorCallback.createPrint(System.err).set();

			// Initialize GLFW. Most GLFW functions will not work before doing this.
			if ( !glfwInit() )
				throw new IllegalStateException("Unable to initialize GLFW");

			// Configure GLFW
			glfwDefaultWindowHints(); // optional, the current window hints are already the default
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

			// Create the window
			window = glfwCreateWindow(WIDTH, HEIGHT, "Absolute Sin", NULL, NULL);
			if ( window == NULL )
				throw new RuntimeException("Failed to create the GLFW window");

			// Setup a key callback. It will be called every time a key is pressed, repeated or released.
			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			});

			// Get the thread stack and push a new frame
			try ( MemoryStack stack = stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1); // int*
				IntBuffer pHeight = stack.mallocInt(1); // int*

				// Get the window size passed to glfwCreateWindow
				glfwGetWindowSize(window, pWidth, pHeight);

				// Get the resolution of the primary monitor
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

				// Center the window
				glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			}

			// Make the OpenGL context current
			glfwMakeContextCurrent(window);
			// Enable v-sync
			glfwSwapInterval(1);


			glfwShowWindow(window);
		}

		private void loop() {
			GL.createCapabilities();
			glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
			
			Text maintxt = new Text();
			
			Light torch = new Light(0,120,2,0.03f, "player", 0, 0, 0.2f); //Last param is RAS
			//Lights.add(torch);
			torch = null; //Set it to null so SYS.GC can collect it :3
			
			Light wallTorchOne = new Light(1,100,10,0.15f, "", -400, 400, 0.1f);
			BGLights.add(wallTorchOne);
			//wallTorchOne = null; //Read the comment above this >:(
			
			//Light wallTorchTwo = new Light(1,100,3,0.15f, "", 400, 400, 0.1f);
			//Lights.add(wallTorchTwo);
			//wallTorchTwo = null;
			
			Shape shape = new Shape(100,300,-500,0, 1);
			//Shapes.add(shape);
			Shape shape2 = new Shape(100,300,-300,0, 1);
			//Shapes.add(shape2);
			
			//Shape downShape = new Shape(300,100,500,200);
			//Shapes.add(downShape);
			
			Shape floorShape = new Shape(2000, 100, 0, -100-Player.PLAYER_HEIGHT-50, 1);
			//Shapes.add(floorShape);
			
			TileManager.run();
			
			while ( !glfwWindowShouldClose(window) ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //Clear the frame buffer.
				glEnable(GL_BLEND); //Opacity!!!
	            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	            
				
				double[] xPos = new double[1];
	            double[] yPos = new double[1];
	            glfwGetCursorPos(window, xPos, yPos); //This gets the mouse position. Pretty much useless right now
	            //The function above returns an int, so use main.transf to make it into float!
	            
	            
	            cameraX = playerOne.x;
	            cameraY = playerOne.y;
	            
	     
	            //General lights, excludes player's light
	            for (Light childLight : BGLights) 
	            	if (!childLight.target.equalsIgnoreCase("player")) {
	            		childLight.update(childLight.sx, childLight.sy);
		            	childLight.render();
	            	}
	            
	            //Interactable shapes, no textures just gradient
	            
	            
	            
	            for (Light childLight : Lights) {
	            	if (!childLight.target.equalsIgnoreCase("player")) {
	            		childLight.update(childLight.sx, childLight.sy);
	            		childLight.render();
	            	}
	            }
	            
	            for (Enemy childEnemy : Enemies) {
	            	if (!childEnemy.alive) {
	            		Enemies.remove(childEnemy);
	            	}
	            	else {
	            		childEnemy.update();
	            		childEnemy.render();
	            		System.out.println("Enemy updated!");
	            		break;
	            	}
	            }
	            
	            //Player updates (For multiplayer, add networking and local player object arraylist)
	            playerOne.keyBinds();
	            playerOne.update();
	            playerOne.render();
	            
	            
	            for (Shape childShape : Shapes) {
	            	childShape.update();
	            	childShape.render(playerOne.x, playerOne.y);
	            }
	            
	            //Player oriented light
	            for (Light childLight : Lights) {
	            	if (childLight.target.equalsIgnoreCase("player")) {
	            		childLight.update(playerOne.x, playerOne.y);
	            		childLight.render();
	            	}
	            	
	            }
	            
	            
	            
	            maintxt.update();
	            
	            
				
				glDisable(GL_BLEND); //Disable blending for opacity.
				glfwSwapBuffers(window);
				glfwPollEvents(); // Poll for window events.
				try {Thread.sleep(10);} catch (Exception e) {}
			}
		}

		
		public static float translateF(double x, String type) {
			if (type.equalsIgnoreCase("width"))
				return (float) (x/Main.WIDTH);
			else if (type.equalsIgnoreCase("height"))
				return (float) (x/Main.HEIGHT);
			else
				return (float) (-1);
		}
}


/* Extra code from StackOver flow for mouse pointer position:
 * DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
	DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
	glfwGetCursorPos(windowID, xBuffer, yBuffer);
	double x = xBuffer.get(0);
	double y = yBuffer.get(0);
	
	
	//System.out.println(xPos[0]);
	            //System.out.println("XPos >>> "+(float) xPos[0]);
	            
				//drawCube((float) xPos[0], (float) yPos[0]);
				
				//drawSemiTransparentSquare(400, 300, 0.5f);
 * 
 *  */
