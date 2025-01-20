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

import org.lwjgl.stb.STBImage;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Voxel {

    public static int loadTexture(String path) throws IOException {
        IntBuffer width = MemoryStack.stackMallocInt(1);
        IntBuffer height = MemoryStack.stackMallocInt(1);
        IntBuffer comp = MemoryStack.stackMallocInt(1);

        ByteBuffer image = STBImage.stbi_load(path, width, height, comp, 4);
        if (image == null) {
            throw new IOException("Failed to load texture file");
        }

        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(), height.get(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        STBImage.stbi_image_free(image);
        GL11.glGetError();
        if (textureID == 0) {
            System.out.println("Texture failed to load.");
        } else {
            System.out.println("Texture loaded successfully.");
        }

        return textureID;
    }

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

    public static int textureID;

    public static boolean[] render_face = {true, true, true, true, true, true};
    public int[] textured_face = {0, 0, 0, 0, 0, 0};

    public Voxel (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        distance = 0;
        voxel_width = 0.5f; //1x1x1 Cube (assuming 0.5f
        voxel_transparency = 1f;

    }

    public void renderf() {

        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        glBegin(GL_QUADS);

        float distanceMultiplier = Math.max(Math.min(1.0f, 1.0f - (distance / render_distance)), 0.0f);
        float face_red = 1f;
        float face_green = 1f;
        float face_blue = 1f;

        face_red = face_red * distanceMultiplier + red_fog_color * (1 - distanceMultiplier);
        face_red = Math.min(Math.max(0.0f, face_red), 1.0f);

        face_green = face_green * distanceMultiplier + green_fog_color * (1 - distanceMultiplier);
        face_green = Math.min(Math.max(0.0f, face_green), 1.0f);

        face_blue = face_blue * distanceMultiplier + blue_fog_color * (1 - distanceMultiplier);
        face_blue = Math.min(Math.max(0.0f, face_blue), 1.0f);

        glColor4f(face_red, face_green, face_blue, voxel_transparency);

        // Front face
        if (render_face[0]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[0]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-voxel_width + x, -voxel_width + y, -voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(voxel_width + x, -voxel_width + y, -voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(voxel_width + x, voxel_width + y, -voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(-voxel_width + x, voxel_width + y, -voxel_width + z);
        }

        // Back face
        if (render_face[1]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[1]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(-voxel_width + x, voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(voxel_width + x, voxel_width + y, voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(voxel_width + x, -voxel_width + y, voxel_width + z);
        }

        // Top face
        if (render_face[2]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[2]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-voxel_width + x, voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(-voxel_width + x, voxel_width + y, -voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(voxel_width + x, voxel_width + y, -voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(voxel_width + x, voxel_width + y, voxel_width + z);
        }

        // Bottom face
        if (render_face[3]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[3]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(voxel_width + x, -voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(voxel_width + x, -voxel_width + y, -voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(-voxel_width + x, -voxel_width + y, -voxel_width + z);
        }

        // Right face
        if (render_face[4]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[4]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(voxel_width + x, -voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(voxel_width + x, voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(voxel_width + x, voxel_width + y, -voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(voxel_width + x, -voxel_width + y, -voxel_width + z);
        }

        // Left face
        if (render_face[5]) {
            glBindTexture(GL_TEXTURE_2D, textured_face[5]);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-voxel_width + x, -voxel_width + y, voxel_width + z);
            glTexCoord2f(1.0f, 0.0f); glVertex3f(-voxel_width + x, -voxel_width + y, -voxel_width + z);
            glTexCoord2f(1.0f, 1.0f); glVertex3f(-voxel_width + x, voxel_width + y, -voxel_width + z);
            glTexCoord2f(0.0f, 1.0f); glVertex3f(-voxel_width + x, voxel_width + y, voxel_width + z);
        }

        glEnd();
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