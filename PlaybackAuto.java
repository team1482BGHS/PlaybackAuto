package org.usfirst.frc.team1482.PlaybackAuto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;
import java.util.List;

import com.google.gson.Gson;
// grab the jar here: https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson

class PlaybackAuto {
  private List data;
  private Object localState;
  
  private DriverStation driverStation;
  
  public PlaybackAuto(String fileInput) throws IOException {
    // JSONParser parser = new JSONParser();
    // Object object = parser.parse(new FileReader(filename)); //the location of the file
    // json = (JSONArray) object;
    
    String content = new String(Files.readAllBytes(Paths.get(filename)));
    
    Gson gson = new Gson();
    data = gson.fromJson(content, data.class);
    
    driverStation = new DriverStation();
    update(0);
  }
  
  
  public void update(int time) {
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
    
    
    for (ArrayList<ArrayList> frames : data) {
      
      double localTime = time;
      for (Object frame : frames) {
        // Find the highest time value that is still before our match time
        if (frame.time <= time && frame.time > localTime) {
          localTime = frame.time;
          localState = frame;
        }
      }
      
    }
  }
  
  public void update() {
    update(driverStation.getMatchTime());
  }
  
  public boolean getButton(int button) throws IndexOutOfBoundsException {
    return localState.buttons[button];
  }
  
  public boolean getAxis(int axis) throws IndexOutOfBoundsException {
    return localState.axises[axis - 1];
  }
  
  public void record() {
    
  }
  
}
