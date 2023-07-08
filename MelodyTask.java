package eecs1021;

// importing the required libraries
import jm.JMC;
import jm.music.data.*;
import jm.util.Play;
import org.firmata4j.ssd1306.SSD1306;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

public class MelodyTask extends TimerTask implements  JMC {
    private static int pumpState = 0; // 0 = OFF; 1 = ON
    private final SSD1306 myOled;
    String setTime = "07-04-2023-19-24-10"; // the date and time that the reminder is meant to turn on. Format is day-month-year-hour-minute-second

    MelodyTask(SSD1306 display) {
        this.myOled =	display;
        // did the same for pumpPin here.
    }

    public void playMelody() {
        List<Note> myListofNotes = List.of(

                new Note(JMC.C4, JMC.TN),// note G in Octave 3, third of a note.
                new Note(JMC.C4, JMC.TN), // note G in Octave 3, third of a note.
                new Note(JMC.G4, JMC.TN), // note G in Octave 3, third of a note.
                new Note(JMC.G4, JMC.TN),// note G in Octave 3, third of a note.
                new Note(JMC.A4, JMC.TN), // note G in Octave 3, third of a note.
                new Note(JMC.A4, JMC.TN), // note G in Octave 3, third of a note.
                new Note(JMC.G4, JMC.QN), // note G in Octave 3, third of a note.
                //
                new Note(JMC.F4, TN), // note G in Octave 3, third of a note.
                new Note(F4, TN), // note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(D4, TN), // note G in Octave 3, third of a note.
                new Note(D4, TN), // note G in Octave 3, third of a note.
                new Note(C4, QN), // note G in Octave 3, third of a note.
                //
                new Note(G4, TN),// note G in Octave 3, third of a note.
                new Note(G4, TN), // note G in Octave 3, third of a note.
                new Note(F4, TN), // note G in Octave 3, third of a note.
                new Note(F4, TN), // note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(D4, QN), // note G in Octave 3, third of a note.
                //
                new Note(C4, TN), // note G in Octave 3, third of a note./////
                new Note(C4, TN), // note G in Octave 3, third of a note.
                new Note(G4, TN), // note G in Octave 3, third of a note.
                new Note(G4, TN), // note G in Octave 3, third of a note.
                new Note(A4, TN), // note G in Octave 3, third of a note.
                new Note(A4, TN), // note G in Octave 3, third of a note.
                new Note(G4, QN), // note G in Octave 3, third of a note.
//
                new Note(F4, TN), // note G in Octave 3, third of a note.
                new Note(F4, TN),// note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(E4, TN), // note G in Octave 3, third of a note.
                new Note(D4, TN), // note G in Octave 3, third of a note.
                new Note(D4, TN), // note G in Octave 3, third of a note.
                new Note(C4, QN)// note G in Octave 3, third of a note.
        );
        for (int i = 0; i < myListofNotes.size(); i++) {
            Play.midi(myListofNotes.get(i));
        }
        ;

    }

    @Override
    public void run() {
        // System.out.println("melody code is running");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss"); // setting up the format of the date.
        Date date = new Date(); // creating a new Date object.
        String currentTime = String.valueOf(formatter.format(date)); // converting the data into a String format for the name.

        if(Objects.equals(currentTime, setTime)){

            myOled.getCanvas().drawString(0, 0, String.valueOf("Don't forget to take your medicines!!"));  // message to put on the OLED with the melody
            myOled.display(); // Update the OLED display (move data from memory onto the screen itself)
            myOled.getCanvas().clear();
            playMelody(); // plays the melody to remind the senior citizen or person in the house.

        }

    }
}


