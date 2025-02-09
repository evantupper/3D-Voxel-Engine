import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.awt.Robot;
import java.awt.MouseInfo;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public class Player3D {
    public float x, y, z;
    private double yaw, pitch;
    private float vel_x, vel_y, vel_z;

    private float turn_speed = 2.0f;

    private float hoz_speed  = 0.05f;
    private float vert_speed = 0.05f;

    private float hoz_damp   = 0.005f;
    private float vert_damp  = 0.002f;

    private float max_vel_x  = 0.075f;
    private float max_vel_y  = 0.2f;
    private float max_vel_z  = 0.075f;

    public static final float player_width = 0.4f;
    public static final float player_height = 0.9f;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private int sky_color = 0;
    private int sky_color_max = 3;
    private int buffer_color = 0;

    private boolean isGrounded = false;

    private HashMap<String, Boolean> key_map = new HashMap<>();

    public Player3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0.0f;
        this.pitch = 0.0f;

        vel_x = 0;
        vel_y = 0;
        vel_z = 0;

        glfwSetCursorPosCallback(Main3D.window, (window, xpos, ypos) -> {
                float dx = (float) xpos - WIDTH / 2;
                float dy = (float) ypos - HEIGHT / 2;
                yaw += dx * 0.1f;
                pitch += dy * 0.1f;
                glfwSetCursorPos(window, WIDTH / 2, HEIGHT / 2);
            });
        glfwSetInputMode(Main3D.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void update() {
        input();

        glRotatef((float)pitch, 1.0f, 0.0f, 0.0f);  
        glRotatef((float)yaw, 0.0f, 1.0f, 0.0f);   

        glTranslatef(x, y - 1.6f, z); 
    }

    public void input() {
        float radian_yaw = (float) Math.toRadians(this.yaw);

        if (glfwGetKey(Main3D.window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetInputMode(Main3D.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            Main3D.pam.wipe();
            System.exit(0);
        }

        if (glfwGetKey(Main3D.window, GLFW_KEY_S) == GLFW_PRESS) {
            vel_z += -Math.cos(radian_yaw) * hoz_speed * 0.7f;
            vel_x += Math.sin(radian_yaw) * hoz_speed * 0.7f;
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_A) == GLFW_PRESS) {
            vel_z += Math.sin(radian_yaw) * hoz_speed * 0.95f;  
            vel_x += Math.cos(radian_yaw) * hoz_speed * 0.95f; 
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_D) == GLFW_PRESS) {
            vel_z += -Math.sin(radian_yaw) * hoz_speed * 0.95f; 
            vel_x += -Math.cos(radian_yaw) * hoz_speed * 0.95f;
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_W) == GLFW_PRESS) {
            vel_z += Math.cos(radian_yaw) * hoz_speed;
            vel_x += -Math.sin(radian_yaw) * hoz_speed;
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            vel_z *= 1.5f;
            vel_x *= 1.5f;
        }

        if (isGrounded && glfwGetKey(Main3D.window, GLFW_KEY_SPACE) == GLFW_PRESS ) {
            jump();
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_TAB) == GLFW_PRESS) {
            vel_y += -vert_speed;
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            vel_y += vert_speed;
        }

        if (-y < -10)
            sky_color = 0;
        else if (-y < 50)
            sky_color = 1;
        else
            sky_color = 2;

        
        if (glfwGetKey(Main3D.window, GLFW_KEY_MINUS) == GLFW_PRESS) {
            Voxel.red_sky_color = 0 / 255.0f;
            Voxel.green_sky_color = 0 / 255.0f;
            Voxel.blue_sky_color = 0 / 255.0f;

            Voxel.red_fog_color = 0 / 255.0f;
            Voxel.green_fog_color = 0 / 255.0f;
            Voxel.blue_fog_color = 0 / 255.0f;
        }
        if (glfwGetKey(Main3D.window, GLFW_KEY_EQUAL) == GLFW_PRESS) {
            Voxel.red_sky_color = 182 / 255.0f;
            Voxel.green_sky_color = 228 / 255.0f;
            Voxel.blue_sky_color = 240 / 255.0f;

            Voxel.red_fog_color = 217 / 255.0f;
            Voxel.green_fog_color = 236 / 255.0f;
            Voxel.blue_fog_color = 241 / 255.0f;
        }

        isGrounded = false;
        for (Voxel vox : Main3D.voxelSet) {
            //Updating distance for rendering
            float dy = y - -vox.y;
            float dx = x - -vox.x;
            float dz = z - -vox.z;
            float sqr1 = dz * dz + dx * dx;
            float sqr2 = (float) Math.sqrt(sqr1 + dy * dy);
            vox.distance =  sqr2;

            if (vox.distance > 2.5f) 
                continue;

            if (!( //Y Checks
                (y + vel_y < -vox.y + vox.voxel_width + player_height * 2 &&
                    y + vel_y > -vox.y - vox.voxel_width) &&
                    //X Checks
                (x + vel_x < -vox.x + vox.voxel_width + player_width &&
                    x + vel_x > -vox.x - vox.voxel_width - player_width) &&
                    //Z Checks
                (z + vel_z < -vox.z + vox.voxel_width + player_width &&
                    z + vel_z > -vox.z - vox.voxel_width - player_width) 
            )
            ) {
                continue;   
            }

            //Collision testing
            boolean shouldTestSides = (y < -vox.y + vox.voxel_width + player_height * 2 && y > -vox.y - vox.voxel_width);
            if ( (!shouldTestSides) && !(z >= -vox.z + vox.voxel_width + player_width || z <= -vox.z - vox.voxel_width - player_width)
            && !(x >= -vox.x + vox.voxel_width + player_width || x <= -vox.x - vox.voxel_width - player_width) ) {
                if (y > -vox.y) {
                    y = -vox.y + player_height * 2 + vox.voxel_width;
                }
                else {
                    y = -vox.y - vox.voxel_width;
                    isGrounded = true;
                }
                vel_y = 0;

                continue;
            }

            if (x >= -vox.x + vox.voxel_width + player_width || x <= -vox.x - vox.voxel_width - player_width
            &&!(z >= -vox.z + vox.voxel_width + player_width || z <= -vox.z - vox.voxel_width - player_width)
            ) { //Testing x
                //                 if (y == -vox.y && vox.voxel_width <= 0.6f)
                //                     jump();
                //                 System.out.println(y + " " + (-vox.y) + " " + vox.voxel_width);

                if (x >= -vox.x + vox.voxel_width + player_width) {
                    x = -vox.x + vox.voxel_width + player_width;
                }
                else { //x < -vox.x - vox.voxel_width - player_width 
                    x = -vox.x - vox.voxel_width - player_width;
                }
                vel_x = 0;
            }
            if (z >= -vox.z + vox.voxel_width + player_width || z <= -vox.z - vox.voxel_width - player_width
            &&!(x >= -vox.x + vox.voxel_width + player_width || x <= -vox.x - vox.voxel_width - player_width)
            ) {
                //                 if (y == -vox.y && vox.voxel_width <= 0.5f)
                //                     jump();

                if (z >= -vox.z + vox.voxel_width + player_width) {
                    z = -vox.z + vox.voxel_width + player_width;
                }
                else { //x < -vox.x - vox.voxel_width - player_width 
                    z = -vox.z - vox.voxel_width - player_width;
                }
                vel_z = 0;
            }

        }
        x += vel_x;
        y += vel_y;
        z += vel_z;

        if (!isGrounded) {
            vel_y += 0.01;
        }

        vel_y = Math.max(Math.min(vel_y, max_vel_y * 3), -max_vel_y);
        vel_x = 0;
        vel_z = 0;

        if (glfwGetKey(Main3D.window, GLFW_KEY_UP) == GLFW_PRESS) pitch -= turn_speed;
        if (glfwGetKey(Main3D.window, GLFW_KEY_DOWN) == GLFW_PRESS) pitch += turn_speed;
        if (glfwGetKey(Main3D.window, GLFW_KEY_LEFT) == GLFW_PRESS) yaw -= turn_speed;
        if (glfwGetKey(Main3D.window, GLFW_KEY_RIGHT) == GLFW_PRESS) yaw += turn_speed;

        pitch = Math.max(-89.0f, Math.min(89.0f, pitch));
    }

    public void jump() {
        vel_y += -vert_speed * 3;
        vel_x *= 1.5f;
        vel_z *= 1.5f;

    }
}
