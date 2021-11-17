package net.doepner.audio;
 
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
 
public class AudioFilePlayer {
 
    private static final int[] Songlist = null;

    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer ();
        player.play("./music/Nico  Vinz - Am I Wrong.wav");
        player.play("./music/");
        player.play("./music/");
        //player.play("something.ogg");
    }
 
    public static void play(String filePath) {
        final File file = new File(filePath);
 
        try (final AudioInputStream in = getAudioInputStream(file)) {
             
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);
 
            try (final SourceDataLine line =
                     (SourceDataLine) AudioSystem.getLine(info)) {
 
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }
 
        } catch (UnsupportedAudioFileException 
               | LineUnavailableException 
               | IOException e) {
            throw new IllegalStateException(e);
        }
    }
    public static void menu() {

        System.out.println("---- SpotifyLikeApp ----");
        System.out.println("[H]ome");
        System.out.println("[S]earch by title");
        System.out.println("[L]ibrary");
        System.out.println("[P]lay");
        System.out.println("[Q]uit");
        System.out.println("[F]avorte");
        System.out.println("1, pause");
        

        System.out.println("");
        System.out.print("Enter q to quit:");

    }
    /*
     * handles the user input for the app
     */
    public static void handleMenu(String userInput) {

        switch(userInput) {

            case "h":
                System.out.println("-->Home<--");
                break;

            case "s":
                System.out.println("-->Search by title<--");
                Search();      
                break;

            case "l":
                System.out.println("-->Library<--");                                                  
                break;
                
            case "p":
                System.out.println("-->Play<--");            
                break;

            case "q":
                System.out.println("-->Quit<--");
                break;
            case "f":
                System.out.println("-->favorite song<--");            
                Search();
                break;
            case "1":
            System.out.println("-->pause<--");
            pause();
				break;
            

            default:
                break;
        }

    }
 
    private static void pause() {
    }

    private static void Search() {
        for(int k = 0; k < Songlist.length; k++)
        {
            int a = k+1;
            System.out.println(a + Songlist[k]);
        }
        
        System.out.println("Please choose a song");
        Scanner Selection = new Scanner(System.in);
        int Choice;
        Choice = Selection.nextInt() - 1;
        int song = Songlist[Choice];        
    }

    private static AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }
 
    private static void stream(AudioInputStream in, SourceDataLine line) 
        throws IOException {
        final byte[] buffer = new byte[65536];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
    
}