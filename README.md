# PlaybackJava

## Usage
Place PlaybackAuto.java in your project src directory.
Initalize the object and point it to a filename. If the file doesn't exist, it will be created. On the robotrio, the code runs under the lvuser, so anything under /home/lvuser/ can be written to.
```java
PlaybackAuto auto = new PlaybackAuto("/home/lvuser/timeline.dat"); 
```

## Playback
Call the getAxis and getButton functions instead of Joystick methods with the time frame and the axis or button id.
```java
long time;
public void autonomousInit() {
  time = System.currentTimeMillis();
}

public void autonomousPeriodic() {
  // Note that this is in milliseconds
  long elapsed = (System.currentTimeMillis() - time);
  auto.update(elapsed);
  
  // If no second paramater is provided, then the first joystick will be used by default
  drive.arcadeDrive(auto.getAxis(0), auto.getAxis(1));
}
```

## Backup
The files are directly stored on the rio, so it's a good idea to back them up. Use scp included in the repo to fetch or push files from the rio.

```sh
scp ~/project/auto.json lvuser@[ip address]:~/auto.json
```
