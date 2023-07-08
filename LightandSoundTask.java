package eecs1021;

// importing the required libraries
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;

public class LightandSoundTask extends TimerTask {

    private Pin	Lightpin;

    private Pin	SoundPin;

    private IODevice theArduinoObj;

    RESTCall http = new RESTCall();

    private int iteration = 0;

    LinkedList<Long> soundLinkedList = new LinkedList<Long>();
    public static ArrayList<Long> soundArrayList = new ArrayList<Long>();

    LinkedList<Long> lightLinkedList = new LinkedList<Long>();
    public static ArrayList<Long> lightArrayList = new ArrayList<Long>();
    public LightandSoundTask(Pin Lightpin, IODevice theArduinoObj, Pin SoundPin	){

        this.Lightpin =	Lightpin; // pin for light sensor
        this.SoundPin = SoundPin; // pin for sound sensor
        this.theArduinoObj = theArduinoObj; // setting the arduino object.

    }


    @Override
    public void run() {

        lightLinkedList.add(Lightpin.getValue()); // adds the Light sensor data to a linked list
        lightArrayList.add((Long)lightLinkedList.get(iteration)); // adds the light sensor data to an arraylist to be used for graphing.

        soundLinkedList.add(SoundPin.getValue()); // // adds the Sound sensor data to a linked list
        soundArrayList.add(soundLinkedList.get(iteration));// adds the Sound sensor data to an arraylist to be used for graphing.

        http.sendDataOverRest(lightLinkedList.get(iteration),soundLinkedList.get(iteration)); // calls the method which sends data to things speak.

        iteration++; // increments the counter  by 1.
    }
}
