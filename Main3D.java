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

import java.util.LinkedHashSet;

import tupper.tsdn.PacketManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class Main3D {
    private int frames;
    private long lastTime;

    private static final int WIDTH = 2000;
    private static final int HEIGHT = 1200;

    private static final int TARGET_FPS = 60;
    private static final int TARGET_TIME = 1000 / TARGET_FPS;

    public static long window;
    public static LinkedHashSet<Voxel> voxelSet;
    public Player3D player;

    public static LinkedHashSet<ExternalPlayer3D> playerSet;

    public static PacketManager pam;

    public static void main(String[] args) {
        new Main3D().run();
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); //Basically sets the settings of the window to all defaults.
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); //Doesn't show the window by default, does this later :3 
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); //Allows or disallows resizing of the window.

        
        window = glfwCreateWindow(WIDTH, HEIGHT, "", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                window,
                (vidMode.width() - pWidth.get(0)) / 2,
                (vidMode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        //glfwSwapInterval(1); //Enables V-Sync basically
        glfwShowWindow(window); //Shows the window, obv
        GL.createCapabilities(); //initializes openGL

        //My init
        //pam = new PacketManager("S:\\Student Work\\CS McCoy\\TupperLand\\3D\\", true);
        pam = new PacketManager("C:\\Users\\evanj\\OneDrive\\Desktop\\3DTesting\\", true);

        playerSet = new LinkedHashSet<ExternalPlayer3D>();
        voxelSet = new LinkedHashSet<Voxel>();
        player = new Player3D(0.0f, 0.0f, -6.0f);
        try {
            voxelSet.add(new Voxel(0,0,0));
            voxelSet.add(new Voxel(2,2,2));
            voxelSet.add(new Voxel(2,-4.5f,2));

            for (int i = -50; i < 50; i++) {
                for (int j = -50; j < 50; j++) {
                    if (i > 30 && j > 30) {
                        voxelSet.add(new Voxel(i,i-35,j));
                    }
                    else
                        voxelSet.add(new Voxel(i,-5,j));

                }
            }
            for (int j = 0; j < 50; j++) {
                voxelSet.add(new Voxel(4,-4,j));
            }
            for (int j = 0; j < 50; j++) {
                voxelSet.add(new Voxel(j,-4,4));
            }
        } catch (Exception e) {

        }


    }

    private void loop() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;
        int oframes = 0;

        while (!glfwWindowShouldClose(window)) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            lastTime = now;

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(Voxel.red_sky_color, Voxel.green_sky_color, Voxel.blue_sky_color, 0.0f);

            glEnable(GL_DEPTH_TEST);
            


            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            player.update();
            for (Voxel vox : voxelSet) {
                vox.update();
            }

            //Multiplayer
            pam.clear();
            pam.obtainAll();

            for (int i = 0; i < pam.packets.size(); i++) {
                if (pam.packets.get(i).getID() == pam.getSystemID())
                    continue;
                try {
                    String data = URLDecoder.decode(pam.packets.get(i).getData(), "UTF-8");
                    String[] parsed = data.split(" ");
                    ExternalPlayer3D pl = new ExternalPlayer3D() {{
                                x = -Float.parseFloat(parsed[0]);
                                y = -Float.parseFloat(parsed[1]);
                                z = -Float.parseFloat(parsed[2]);
                            }};
                    pl.render();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            try {
                pam.changeHeldData(URLEncoder.encode(player.x + " " + player.y + " " + player.z, "UTF-8"));
            } catch(Exception e){
            }

            pam.force();

            
            

            //Fps updates or wtv
            long currentTime = System.nanoTime();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            
            glfwSwapBuffers(window);
            frames++;
            long waitTime = TARGET_TIME - (elapsedTime / 1000000); // Convert from ns to ms
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
