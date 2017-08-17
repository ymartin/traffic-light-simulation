# Traffic light simulation

## Requirements
You are required to provide the code for an application that simulates a set of traffic lights at an intersection. 
The traffic lights are designated (N, S) and (E, W) like a compass.

When switching from green to red, the yellow light must be displayed for 30 seconds prior to it switching to red. 
The lights will change automatically every 5 minutes.

## Assumptions
- Simulation runs in real-time and outputs at duration intervals
- (N, S) and (E, W) receptively share the same states
- Currently the simulator is started with (N, S) being green and (E, W) red

## Notes
- I followed a TDD approach and drove out my solution using IntersectionTest, after having all the cases I was able to re-factor/re-structure with confidence
- I tried to keep my tests lightweight as possible with as little mock/fake as possible
- I wrote a simple system/integration UI test after test driving the intersection unit/package. It requires a sleep but that was the only way I can easily system test it, unit tests take care of using fake schedulers.
- I decided to pass in red/green durations instead of using an enum to allow system testing with different values
- Intersection is currently used to control most of the simulation at this time which I felt allows more flexibility than having lights control state changes.

## Setup
This project required Java 8 with gradle 3.3 used to build/test.
- ```./gradlew clean build``` Clean build and run tests
- ```./gradlew test``` Run tests
- ```./gradlew run``` Start simulation which outputs to the terminal

## Output
```
Date        Time      North   South   East    West   
2017-08-17  08:04:23  GREEN   GREEN   RED     RED    
2017-08-17  08:08:53  YELLOW  YELLOW  RED     RED    
2017-08-17  08:09:23  RED     RED     GREEN   GREEN  
2017-08-17  08:13:53  RED     RED     YELLOW  YELLOW 
2017-08-17  08:14:23  GREEN   GREEN   RED     RED    
2017-08-17  08:18:53  YELLOW  YELLOW  RED     RED    
2017-08-17  08:19:23  RED     RED     GREEN   GREEN  
2017-08-17  08:23:53  RED     RED     YELLOW  YELLOW 
2017-08-17  08:24:23  GREEN   GREEN   RED     RED    
2017-08-17  08:28:53  YELLOW  YELLOW  RED     RED    
2017-08-17  08:29:23  RED     RED     GREEN   GREEN  
2017-08-17  08:33:53  RED     RED     YELLOW  YELLOW
```
