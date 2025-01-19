
//Renders AFTER EVERYTHING
// - 200 Pixels of the bottom of screen (-Height of window + 100(half height))

import org.lwjgl.opengl.GL11;

public class Text {
	
	//This shows the bottom text box :3
	public Text() {
		
	}
	
	public void update() {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	GL11.glLoadIdentity();
    
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f); //Lighter Grey color
    	GL11.glVertex2f(Main.translateF(-900, "width"), Main.translateF(-900, "height"));
    	GL11.glVertex2f(Main.translateF(-900, "width"), Main.translateF(-400, "height"));
    	GL11.glVertex2f(Main.translateF(900, "width"), Main.translateF(-400, "height"));
    	GL11.glVertex2f(Main.translateF(900, "width"), Main.translateF(-900, "height"));
    	GL11.glEnd();
    	
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glColor4f(0.8f, 0.1f, 0.1f, 1.0f); //Lighter Grey color
    	GL11.glVertex2f(Main.translateF(-Main.playerOne.getHP()*3, "width"), Main.translateF(-800, "height"));
    	GL11.glVertex2f(Main.translateF(-Main.playerOne.getHP()*3, "width"), Main.translateF(-830, "height"));
    	GL11.glVertex2f(Main.translateF(Main.playerOne.getHP()*3, "width"), Main.translateF(-830, "height"));
    	GL11.glVertex2f(Main.translateF(Main.playerOne.getHP()*3, "width"), Main.translateF(-800, "height"));
    	GL11.glEnd();
    	
    	System.out.println("Player health: "+Main.playerOne.getHP());
	}
	public void render() {
		
	}
}
