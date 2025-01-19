import org.lwjgl.opengl.GL11;

public class Shape {
	int width;
	int height;
	int x;
	int y;
	int velocityX;
	int velocityY;
	int type;
	boolean collidable = true;
	
	public Shape(int inputtedWidth, int inputtedHeight, int x, int y, int inputtedType) {
		this.width = inputtedWidth;
		this.height = inputtedHeight;
		this.x = x;
		this.y = y;
		this.type = inputtedType;
		//Should I add rotation in the future :3? [Y/N] >>> 
	}
	public Shape(int inputtedWidth, int inputtedHeight, int x, int y, int inputtedType, boolean inputtedCollidable) {
		this.width = inputtedWidth;
		this.height = inputtedHeight;
		this.x = x;
		this.y = y;
		this.type = inputtedType;
		this.collidable = inputtedCollidable;
	}
	
	
	public void render(int px, int py) {
		if (type == 1) {
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
        	GL11.glLoadIdentity();
        
        	GL11.glBegin(GL11.GL_QUADS);
        	GL11.glColor4f(0.25f, 0.25f, 0.25f, 1.0f); //Lighter Grey color
        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
        	GL11.glEnd();
		}
		else if (type == 2) {
			collidable = false;
			if (Math.sqrt(Math.pow(px-x,2)+Math.pow(py-y,2)) > 220 && Math.sqrt(Math.pow(px-x,2)+Math.pow(py-y,2)) < 320) {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        	GL11.glLoadIdentity();
	        
	        	GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.8f); //Lighter Grey color
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glEnd();
			}
			else if (Math.sqrt(Math.pow(px-x,2)+Math.pow(py-y,2)) > 120 && Math.sqrt(Math.pow(px-x,2)+Math.pow(py-y,2)) < 220) {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        	GL11.glLoadIdentity();
	        
	        	GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.625f); //Lighter Grey color
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glEnd();
			}
			else if (Math.sqrt(Math.pow(px-x,2)+Math.pow(py-y,2)) < 120 ) {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        	GL11.glLoadIdentity();
	        
	        	GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.5f); //Lighter Grey color
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glEnd();
			}
			else {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        	GL11.glLoadIdentity();
	        
	        	GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f); //Lighter Grey color
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
	        	GL11.glEnd();
			}
			
		}
		else {
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
        	GL11.glLoadIdentity();
        
        	GL11.glBegin(GL11.GL_QUADS);
        	GL11.glColor4f(0.25f, 0.25f, 0.25f, 1.0f); //Lighter Grey color
        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(-this.height+y, "height"));
        	GL11.glColor4f(0.13f, 0.13f, 0.13f, 1.0f); //Darker grey color
        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(-this.height+y, "height"));
        	GL11.glVertex2f(Main.translateF(this.width+x, "width"), Main.translateF(this.height+y, "height"));
        	GL11.glColor4f(0.25f, 0.25f, 0.25f, 1.0f); //Lighter Grey color
        	GL11.glVertex2f(Main.translateF(-this.width+x, "width"), Main.translateF(this.height+y, "height"));
        	GL11.glEnd();
		}
	}
	public void update() {
		
	}
}
