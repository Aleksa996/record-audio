package recordAudio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws LineUnavailableException, InterruptedException {

        AudioFormat format = new AudioFormat(1600, 8, 2 ,true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if(!AudioSystem.isLineSupported(info)){
            System.out.println("Line is not supported");
        }

        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        targetDataLine.open();
        System.out.println("Starting recording");
        targetDataLine.start();

        Thread stopper = new Thread(new Runnable(){
            @Override
            public void run(){
                AudioInputStream audioStream = new AudioInputStream(targetDataLine);
                File wavFile = new File("C://Users//aleksa.kundacina//IdeaProjects//RecordAudio.wav");
                try {
                    AudioSystem.write(audioStream,AudioFileFormat.Type.WAVE,wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopper.start();
        Thread.sleep(5000);
        targetDataLine.stop();
        targetDataLine.close();
        System.out.println("Ended Sound Test");
    }
}
