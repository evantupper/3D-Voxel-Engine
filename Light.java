import org.lwjgl.opengl.GL11;

public class Light {
    public int x;
    public int y;
    public int sx; //This and sy are where the Light is to be set if it is NOT set around another object's position.
    public int sy;
    public int color;
    public int size;
    public int layers;
    public float strength;
    public String target;
    public float rotationAngle = 0.0f;
    public float rotationAngleSpeed = 0.1f;
    
    public Light(int colorPassed, int sizePassed, int layers, float strength, String target, int x, int y, float ras) {
        this.color = colorPassed;
        this.size = sizePassed;
        this.layers = layers;
        this.strength = strength;
        this.target = target;
        this.sx = x;
        this.sy = y;
        this.rotationAngleSpeed = ras;
    }

    public void update(int targetX, int targetY) {
    	this.x = targetX;
        this.y = targetY;
        this.rotationAngle += this.rotationAngleSpeed; // Increment rotation angle (default: 0.1f)
    }

    public void render() {
        for (int i = 1; i <= layers; i++) {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            
            GL11.glTranslatef((float) ((double) x/Main.WIDTH), (float) ((double) y/Main.HEIGHT), 0); //Centers the rotation point at player's x and y
            //GL11.glTranslatef((float) ((double) x/900.0), (float) ((double) y/1500.0), 0);
            GL11.glRotatef(this.rotationAngle, 0, 0, 1); // Rotate around Z-axis
            
            
            //testing to see if it saved :D
            
            if (color == 0)
                GL11.glColor4f(1.0f, 1.0f, 1.0f, strength); // White
            else
                GL11.glColor4f(1.0f, 0.7f, 0.0f, strength); // Yellow
            GL11.glBegin(GL11.GL_QUADS);
            
            GL11.glVertex2f((float) ((double) -size*i/Main.WIDTH), (float) ((double) -size*i/Main.HEIGHT));
            GL11.glVertex2f(Main.translateF((size * i), "width"), Main.translateF((-size * i), "height"));
            GL11.glVertex2f(Main.translateF((size * i), "width"), Main.translateF((size * i), "height"));
            GL11.glVertex2f(Main.translateF((-size * i), "width"), Main.translateF((size * i), "height"));
            GL11.glEnd();
        }
    }
}
