# PlaybackJava

## Usage
Place PlaybackAuto.java in your project src directory.
Initalize the object and point it to a filename. If the file doesn't exist, it will be created. On the robotrio, the code runs under the lvuser, so anything under /home/lvuser/ can be written to.
```java
PlaybackAuto recording = new PlaybackAuto("/home/lvuser/timeline.dat"); 
```

### Recording
It is recommended to do recording in test mode. Call startRecord in testInit to empty the file. Then call recordFrame in testPeriodic with the frame id (starting at 0) and a joystick object.
```java
int recordingTime;
public void testInit() {
  recordingTime = 0;

  try {
    recording.startRecord();
  } catch (IOException ex) {
    ex.printStackTrace();
  }
}

public void testPeriodic() {
  try {
    recording.recordFrame(recordingTime, stick);
  } catch (IOException ex) {
    ex.printStackTrace();
  }
  
  recordingTime++;
}
```
## Playback
Call the getAxis and getButton functions instead of Joystick methods with the time frame and the axis or button id.
```java
int recordingTime;
public void autonomousInit() {
  recordingTime = 0;
}

public void autonomousPeriodic() {
  try {
    drive.arcadeDrive(recording.getAxis(recordingTime, 1), -recording.getAxis(recordingTime, 0));
  } catch (IndexOutOfBoundsException | IOException ex) {
    e.printStackTrace();
  }
  recordingTime++;
}
```

## Backup
The files are directly stored on the rio, so it's a good idea to back them up. Use the batch script tool in the backup directory to fetch or push files from the rio.