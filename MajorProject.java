// Name: Bhavya Trivedi
// Program Title: MajorProject
// Program Description: This program is the code/logic for the home monitoring system which uses components such as light sensors, sound sensor, etc to monitor different parameters of the house and send the data to an IOT where the user can view it live.
// This is the main file for the Major Project.
// This file calls LightandSoundTask, MelodyTask, RESTCall, GraphTask, and WebcamTask.
// Date: 4 April, 2023

// Interface defined: (https://bit.ly/3IhVg1J)
package eecs1021;

// importing the required libraries
import org.firmata4j.*;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;

public class MajorProject  implements
        IODeviceEventListener {

    static	final	int	A1	=	15;	//	moisture sensor
    static	final	int	A2	=	16;	//	Sound
    static	final	int	D6	=   6;	//	Button
    static	final	byte	I2C0	=	0x3C;	//	OLED	Display

    static	final	int	A6	=	20;	//	LIGHT Sensor
    static Timer timer = new Timer(); // Timer variable to be used with the timer.schedule below
    static int WebcamtimeInterval = 15000; // variable used to store time interval in ms in which to run the timer.schedule.
    static int LightandSoundtimeInterval = 15000; // variable used to store time interval in ms in which to run the timer.schedule.

    static Pin buttonObject;

    public static void main(String[] args) throws InterruptedException, IOException {

        /* Initialize the Board */
        // var arduinoObject = new FirmataDevice("/dev/cu.usbserial-0001");

        String myUSB = "COM6";
        // Create a FirmataDevice object with a USB connection.
        IODevice theArduinoObject = new FirmataDevice(myUSB);
        // Start up the FirmataDevice object.
        theArduinoObject.start();
        theArduinoObject.ensureInitializationIsDone();
        // Then created an SSD1306 object using the I2C object with the right pixel size for the OLED
        I2CDevice i2cObject = theArduinoObject.getI2CDevice((byte) I2C0); // Use 0x3C for the Grove OLED
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515
        // Initialized the OLED (SSD1306) object
        theOledObject.init();
        /* Initialized the pins */
        // 1. Assign memory location to the sound object
        var SoundObject = theArduinoObject.getPin(A2);
        // 1. Assign memory location to the button object
        buttonObject = theArduinoObject.getPin(D6);
        // 2. Fill the object.
         buttonObject.setMode(Pin.Mode.INPUT);
        Pin myLightSensor; // the pin for the Light sensor
        // 1. Assign memory location to the light object
        myLightSensor = theArduinoObject.getPin(A6);

         // runs the LightandSound task every 15 seconds

        var lightandSound = new LightandSoundTask(myLightSensor, theArduinoObject, SoundObject );

        timer.schedule(lightandSound, 0, LightandSoundtimeInterval);

        // runs the melody class every second

        var melody = new MelodyTask(theOledObject);
        timer.schedule(melody, 0, 1000);

        // runs this timer task every 1 min.

        var webcamtask = new WebcamTask(); // creating a new WebcamTask object.
        timer.schedule(webcamtask, 0, WebcamtimeInterval); // runs the WebcamTask every 1 min (60000 ms), which takes a picture using the webcam.

        // adding an event listener which listens for a button press on the arduino grove board.
        MajorProject MajorProjTask = new MajorProject();
        theArduinoObject.addEventListener(MajorProjTask);

    }

    @Override
    public void onStart(IOEvent ioEvent) {

    }

    @Override
    public void onStop(IOEvent ioEvent) {

    }

    @Override
    public void onPinChange(IOEvent event) {
        // Return right away if the even isn't from the Button.

        if (event.getPin().getIndex() != buttonObject.getIndex()) {
            // to do: return;
            return;
        }

       timer.cancel(); // cancles the timer.schedule tasks.


        try {

            GraphTask.graphData(LightandSoundTask.lightArrayList, "Light "); // calls thegraphData method which creates a sensor voltage vs time graph.
            // GraphTask.graphData(LightandSoundTask.soundArrayList); // if the user wants to see the sound value graph, they can use this code.
        } catch (IOException e) {
            System.out.println("Exception was thrown.");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("Exception was thrown.");
            throw new RuntimeException(e);
        }
        System.out.println("Program was terminated");
        System.exit(0); // terminates the program.


    }

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {

    }
}
