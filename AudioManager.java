/* How to use Audio:
 * Make sure to change the filePath to where you are running it on (Preferably a USB!!!)
 * Create audiomanager object with a parametere of the file path (remove the disk string!! should look like: :\\AbsRes\\Audio\\footsteps-landing-impact-mechanical-wave-3-3-00-01.wav)
 * Run that object's play() method
 * Apply any looping
 * 
 * 
 * void setUp()
 * - Sets up the audio for use. This is required to run any audio.
 * - Prints out the stack trace when encountered with an error.
 * - Opens the clip with the format and audio data present in the provided audio input stream. Opening a clip means that it should acquire any required system resources and become operational. If this operation input stream. If this operation succeeds, the line is marked open and an OPEN event is dispatched to the line's listeners.
	- Invoking this method on a line which is already open is illegal and may result in an IllegalStateException.
	- Note that some lines, once closed, cannot be reopened. Attempts to reopen such a line will always result in a LineUnavailableException.
 * 
 * void play()
 * - Plays the audio.
 * 
 * 
 * void loop(int count)
	- Starts looping playback from the current position. Playback will continue to the loop's end point, then loop back to the loop start point count times, and finally continue playback to the end of the clip.
	- If the current position when this method is invoked is greater than the loop end point, playback simply continues to the end of the clip without looping.
	- A count value of 0 indicates that any current looping should cease and playback should continue to the end of the clip. The behavior is undefined when this method is invoked with any other value during a loop operation.
	- If playback is stopped during looping, the current loop status is cleared; the behavior of subsequent loop and start requests is not affected by an interrupted loop operation.
	Parameters:
	- count - the number of times playback should loop back from the loop's end position to the loop's start position, or LOOP_CONTINUOUSLY to indicate that looping should continue until interrupted
 * 
 * 
 * 
 * 
 * */




import java.io.File;  
import javax.sound.sampled.*;


public class AudioManager {
	static String StrFile;
	static Clip musicClip;
	static File file;
	static String filePath = "E";
	static int audioCount;
	
	@SuppressWarnings("static-access")
	public AudioManager(String givenFile) {
		this.StrFile = givenFile;
	}
	
	public static void setUp() {
		//Normally throws UnsupportedAudioFileException, IOException, LineUnavailableException 
		//System.out.println("[LOG] Setting up "+StrFile+".");
		try {
			file = new File(filePath+ StrFile);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	        musicClip = AudioSystem.getClip();
	        musicClip.open(audioStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("[LOG] "+filePath+StrFile+" is set up.");
	}
	
	public static void play() {
		System.out.println("[LOG] ("+audioCount+") Audio file "+filePath+StrFile+" is starting to play.");
		musicClip.start(); 
		audioCount++;
	}
	
	public static void loop(int count) {
		musicClip.loop(count);
	}
	
	public static void end() {
		
	}
}
