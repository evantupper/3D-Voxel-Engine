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

public class Voxel {
    public float x;
    public float y;
    public float z;

    public float distance;
    public float render_distance = 50f;

    public static float red_sky_color    = 182 / 255.0f;
    public static float green_sky_color  = 228 / 255.0f;
    public static float blue_sky_color   = 240 / 255.0f;

    public static float red_fog_color    = 217 / 255.0f;
    public static float green_fog_color  = 236 / 255.0f;
    public static float blue_fog_color   = 241 / 255.0f;

    public static float voxel_width;
    public static float voxel_transparency;

    public static boolean[] render_face = {true, true, true, true, true, true};

    public Voxel (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        distance = 0;
        voxel_width = 0.5f; //1x1x1 Cube (assuming 0.5f
        voxel_transparency = 1f;
    }

    public void update() {
        render();
    }

    public void render() {
        glBegin(GL_QUADS);

        float distanceMultiplier = Math.max(Math.min(1.0f, 1.0f - (distance / render_distance)), 0.0f);

        // Front face
        if (render_face[0]) {
            float face_red = 1.0f;
            float face_green = 0.0f;
            float face_blue = 0.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(-voxel_width + x, -voxel_width + y, -voxel_width + z);
            glVertex3f( voxel_width + x, -voxel_width + y, -voxel_width + z);
            glVertex3f( voxel_width + x,  voxel_width + y, -voxel_width + z);
            glVertex3f(-voxel_width + x,  voxel_width + y, -voxel_width + z);
        }

        // Back face
        if (render_face[1]) {
            float face_red = 0.0f;
            float face_green = 1.0f;
            float face_blue = 0.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glVertex3f(-voxel_width + x,  voxel_width + y, voxel_width + z);
            glVertex3f( voxel_width + x,  voxel_width + y, voxel_width + z);
            glVertex3f( voxel_width + x, -voxel_width + y, voxel_width + z);
        }

        // Top face
        if (render_face[2]) {
            float face_red = 0.0f;
            float face_green = 0.0f;
            float face_blue = 1.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(-voxel_width + x, voxel_width + y, voxel_width + z);
            glVertex3f(-voxel_width + x, voxel_width + y,  -voxel_width + z);
            glVertex3f( voxel_width + x, voxel_width + y,  -voxel_width + z);
            glVertex3f( voxel_width + x, voxel_width + y, voxel_width + z);
        }
        
        // Bottom face
        if (render_face[3]) {
            float face_red = 1.0f;
            float face_green = 1.0f;
            float face_blue = 0.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glVertex3f( voxel_width + x, -voxel_width + y, voxel_width + z);
            glVertex3f( voxel_width + x, -voxel_width + y,  -voxel_width + z);
            glVertex3f(-voxel_width + x, -voxel_width + y,  -voxel_width + z);
        }

        // Right face
        if (render_face[4]) {
            float face_red = 1.0f;
            float face_green = 0.0f;
            float face_blue = 1.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(voxel_width + x, -voxel_width + y, voxel_width + z);
            glVertex3f(voxel_width + x,  voxel_width + y, voxel_width + z);
            glVertex3f(voxel_width + x,  voxel_width + y,  -voxel_width + z);
            glVertex3f(voxel_width + x, -voxel_width + y,  -voxel_width + z);
        }

        // Left face
        if (render_face[5]) {
            float face_red = 0.0f;
            float face_green = 1.0f;
            float face_blue = 1.0f;
            
            face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
            face_red = Math.min(Math.max(0.0f, face_red), 1.0f);
            
            face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
            face_green = Math.min(Math.max(0.0f, face_green), 1.0f);
            
            face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
            face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);
            
            glColor4f(face_red, face_green, face_blue, voxel_transparency);
            glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glVertex3f(-voxel_width + x, -voxel_width + y,  -voxel_width + z);
            glVertex3f(-voxel_width + x,  voxel_width + y,  -voxel_width + z);
            glVertex3f(-voxel_width + x,  voxel_width + y, voxel_width + z);
        }

        glEnd();
    }

    public float[] getLocation() {
        return new float[] {x,y,z};
    }

}