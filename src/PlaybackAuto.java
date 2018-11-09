package org.usfirst.frc.team1482.robot;

import edu.wpi.first.wpilibj.Joystick;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
// grab the jar here: https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson

public class PlaybackAuto {
  private TimeFrame[] data;
  private TimeFrame localState;
  
  public PlaybackAuto(String filename) throws IOException {    
    String content = new String(Files.readAllBytes(Paths.get(filename)));
    
    Gson gson = new Gson();
    data = gson.fromJson(content, TimeFrame[].class);
    
    update(0);
  }
  
  
  public void update(double time) {
    /*
    [
      [
        {
          "time": 4,
          "buttons:" [true, ...],
          "axises": [0, ...]
        }
      ]
    ]
    */
    
    double localTime = 0.;
    for (TimeFrame frame : data) {
      
      // Find the highest time value that is still before our the current time
      if (frame.time <= time && frame.time >= localTime) {
        localTime = frame.time;
        localState = frame;
      }
    }
  }
  
  public boolean getButton(int joystick, int button) {
    return localState.sticks[joystick].buttons[button];
  }
  
  public boolean getButton(int button) {
    return this.getButton(0, button);
  }
  
  public double getAxis(int joystick, int axis) {
    return localState.sticks[joystick].axes[axis - 1];
  }
  
  public double getAxis(int axis) {
    return this.getAxis(0, axis);
  }
  
  public void record() {
    // TODO later
  }
  
}

class TimeFrame {
  public double time;
  public StickInputs[] sticks;
}

class StickInputs {
  public boolean[] buttons;
  public double[] axes;
}
