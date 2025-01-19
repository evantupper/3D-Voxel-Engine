import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;

public class ExternalPlayer3D {
    public float x;
    public float y;
    public float z;

    public ExternalPlayer3D() {

    }

    public void render() {
        glPushMatrix();
        glBegin(GL_QUADS);

        glColor4f(1, 0, 0, 0.5f);
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y, -Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, -Player3D.player_height + y, -Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x,  Player3D.player_height + y, -Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x,  Player3D.player_height + y, -Player3D.player_width + z);

        // Back face
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x,  Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x,  Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);

        // Top face
        glVertex3f(-Player3D.player_width + x, Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x, Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, Player3D.player_height + y, Player3D.player_width + z);

        // Bottom face
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f( Player3D.player_width + x, -Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y,  -Player3D.player_width + z);

        // Right face
        glVertex3f(Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f(Player3D.player_width + x,  Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f(Player3D.player_width + x,  Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f(Player3D.player_width + x, -Player3D.player_height + y,  -Player3D.player_width + z);

        // Left face
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y, Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x, -Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x,  Player3D.player_height + y,  -Player3D.player_width + z);
        glVertex3f(-Player3D.player_width + x,  Player3D.player_height + y, Player3D.player_width + z);

        glEnd();
        glPopMatrix();
    }

}
