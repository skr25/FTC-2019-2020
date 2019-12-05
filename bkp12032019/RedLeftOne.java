package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import java.util.Map;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="Red Left One Position", group="RedAlliance")

public class RedLeftOne extends LinearOpMode {
    //creating a timer variable
    ElapsedTime runtime = new ElapsedTime();
    double defaultRobotPower = 0.5;
    double defaultRobotAngle = 0;
    String RobotDetectedMineralPosition = null ;
    String MyStone = "None";
    Integer intScanCount = 0;
    /* Define Constants with in Class */
    public static final Integer MM = 0 ;
    public static final Integer CM = 1 ;
    public static final Integer METER = 2 ;
    public static final Integer INCH = 3 ;
    MyRobot FTCRobot = new MyRobot();
    @Override   //override instruction to indicate runOpMode is being overriden
    public void runOpMode() throws InterruptedException { //runs when you select program initialization
       //****************************************************************
       //Get and assign all hardware data into variables
        telemetry.addData("Status", "Initialized");
        telemetry.update();
         //Initialize robot with hardware map
        FTCRobot.init(hardwareMap);
        //Create tensorflow object for skystone detection
        SkrTensorFlowSkyStone myObjTensorFlow = new SkrTensorFlowSkyStone(telemetry,hardwareMap);
        //Ensure that arm is always up at initialiation
        FTCRobot.autoarmUp();
        // Wait for the game to start (driver presses PLAY)

        waitForStart();

        FTCRobot.Default_Motor_Power = 1;
        MoveRobotForwardForSeconds(1);
        MoveRobotRightForSeconds(0.75);
      
        FTCRobot.Default_Motor_Power = 0.5;
        sleep(500); //previos 1000 added to give sufficient time to scan first stone.

        while (opModeIsActive()&& MyStone == "None" && intScanCount <3) {
           sleep(400);  //added sleep to add time between one vuforia scan loop to another. In its absence,
           //loops run faster and gets false results
           String MyStone =  myObjTensorFlow.StoneName(telemetry);

           if (MyStone != "None"){
               telemetry.addData("skrStoneName:", MyStone);
               IdentifySkystoneAndKick(); //method to idetifying skystone and kick and grab it with arm
               break;
           }
           else{

              intScanCount+=1; //increasing scan counter to ensure we do not
              telemetry.addData("intScanCount", intScanCount);
               telemetry.update();
              MoveRobotBackwardForSeconds(0.585);

           }

         telemetry.update();
         sleep(1000);
        }
        myObjTensorFlow.killVifuria();

       // DecideFoundationRoutine();
        GoToFoundationDropStone();
        FTCRobot.Default_Motor_Power = 0 ;
        FTCRobot.stopRobot() ;


    }

  public void MoveRobotForwardForSeconds(double runForSecond){
      FTCRobot.moveForward();
       runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.stopRobot();
  }

  public void MoveRobotBackwardForSeconds(double runForSecond){
      FTCRobot.moveBackward();
       runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.stopRobot();

  }

  public void MoveRobotLeftForSeconds(double runForSecond){
      FTCRobot.moveLeft();
       runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.stopRobot();
  }

  public void MoveRobotRightForSeconds(double runForSecond){
      FTCRobot.moveRight();
       runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.stopRobot();
  }



  public void SpinClockwiseRobotForSeconds(double runForSecond){
      FTCRobot.spinClockwise();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        FTCRobot.stopRobot();
  }

  public void SpinCounterClockwiseRobotForSeconds(double runForSecond){
      FTCRobot.spinCounterClockwise();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        FTCRobot.stopRobot();
  }
 public void MoveRobotRightTillDistance(Integer sensorUnit,Double askedDistance){
      FTCRobot.moveRight();
      telemetry.addData("askedDistance", String.format("%.01f mm", askedDistance));
      while (opModeIsActive() && (FTCRobot.getDistRightRange(sensorUnit) >= askedDistance)) {
        switch(sensorUnit) {
            case 0 :    telemetry.addData("range", String.format("%.01f mm", FTCRobot.getDistRightRange(sensorUnit)));
                        telemetry.update();
            case 1 :    telemetry.addData("range", String.format("%.01f cm", FTCRobot.getDistRightRange(sensorUnit)));
                        telemetry.update();
            case 2 :    telemetry.addData("range", String.format("%.01f m", FTCRobot.getDistRightRange(sensorUnit)));
                        telemetry.update();
            case 3 :    telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistRightRange(sensorUnit)));
                        telemetry.update();
            default :   telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistRightRange(sensorUnit)));
                        telemetry.update();
               }
        }
      FTCRobot.stopRobot();
  }

public void MoveRobotLeftTillDistance(Integer sensorUnit,Double askedDistance){
      FTCRobot.moveLeft();
      telemetry.addData("askedDistance", String.format("%.01f mm", askedDistance));
      while (opModeIsActive() && (FTCRobot.getDistLeftRange(sensorUnit) >= askedDistance)) {
          switch(sensorUnit) {
            case 0 :    telemetry.addData("range", String.format("%.01f mm", FTCRobot.getDistLeftRange(sensorUnit)));
                        telemetry.update();
            case 1 :    telemetry.addData("range", String.format("%.01f cm", FTCRobot.getDistLeftRange(sensorUnit)));
                        telemetry.update();
            case 2 :    telemetry.addData("range", String.format("%.01f m", FTCRobot.getDistLeftRange(sensorUnit)));
                        telemetry.update();
            case 3 :    telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistLeftRange(sensorUnit)));
                        telemetry.update();
            default :   telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistLeftRange(sensorUnit)));
                        telemetry.update();
               }
        }
      FTCRobot.stopRobot();
  }
  
  
public void MoveRobotLeftTillTouch(){
      FTCRobot.moveLeft();
      while (opModeIsActive() && (FTCRobot.getTouchState()== true)) {
        telemetry.addData("Move", "moving");
        telemetry.update();
        }
      FTCRobot.stopRobot();
  }
  
  
public void IdentifySkystoneAndKick(){
    telemetry.addData("Method Call:", "IdentifySkystoneAndKick");
    telemetry.update();
    MoveRobotBackwardForSeconds(0.28);
    MoveRobotRightForSeconds(1);
    sleep(500); //added sleep to ensure robot stops and ready to down arm
    FTCRobot.autoarmDown();
    sleep(300); //added slip to ensure arm is fitted with block edges
    FTCRobot.stopRobot();
}


public void GoToFoundationDropStone(){
     telemetry.addData("Method Call:", "GoToFoundationDropStoneFirstScan");
     telemetry.update();
    
     //After picking block, it goes back little
     MoveRobotLeftForSeconds(3);
     
     //spin block to face perimeter wall towards foundation
     SpinClockwiseRobotForSeconds(1.6); //prev 1.6
     FTCRobot.Default_Motor_Power = 1 ;
    
     //takes robot till 52 inch before perimeter wall
     MoveRobotRightTillDistance(INCH,52.0);
     
     //spins to get rid of stone block
     SpinCounterClockwiseRobotForSeconds(0.6);
     FTCRobot.Default_Motor_Power = 0;
     
     //set Robot Angle in direction
     FTCRobot.Default_Robot_Angle = defaultRobotAngle;
    // sleep(1000);
    
    //removes block from arm clutches
     FTCRobot.autoarmUp();
     sleep(300);
     FTCRobot.Default_Motor_Power = 1;
     
     //power it to move towards perimter wall
     MoveRobotBackwardForSeconds(1.5);
     
     //moves towards foundation edge to hold using arm clutch
     MoveRobotRightForSeconds(0.75);
     FTCRobot.Default_Motor_Power = 0;
    
     //grabbing foundation perimeter by arm clutch by lowering arm dowm
     FTCRobot.autoarmDown();
     sleep(300);

     // After Grabbing Foundation by arm, it pulls towards wall
     FTCRobot.Default_Motor_Power = 1;
     MoveRobotLeftForSeconds(1);
     
     //After pulling straight, it pulls 30 degree angle movement and pulls further
     FTCRobot.Default_Motor_Power = 0.75;
     FTCRobot.Default_Robot_Angle = -30;
     MoveRobotLeftForSeconds(2.7);
     
     //Reseting angle and power of robot to zero
     FTCRobot.Default_Robot_Angle = 0;
     FTCRobot.Default_Motor_Power = 0;
    
     //Removes Arm from holding foundation 
     FTCRobot.autoarmUp();
     sleep(200);
     
     // Move Robot forward to get middle of tilted foundation
     FTCRobot.Default_Motor_Power = 1;
     MoveRobotForwardForSeconds(0.5);
     
     // Moves Clockwise to get in position to push
     SpinClockwiseRobotForSeconds(0.3);
       
     // Moves foundation all the way back to hit the perimeter and line up
     MoveRobotRightForSeconds(1.5);
       
     // Move Robot towards perimeter wall to prepare for going to parking location
     MoveRobotBackwardForSeconds(1);
       
     //  Move Robot to parking location under the skybridge
      MoveRobotLeftForSeconds(2);
     
     // Bringing down Robot power and make it idle.
      FTCRobot.Default_Motor_Power = 0;
      FTCRobot.stopRobot();
      idle();
       
    

}
}
