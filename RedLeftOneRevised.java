package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import java.util.Map;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="Red Left One Position Revised", group="RedAlliance")

public class RedLeftOneRevised extends LinearOpMode {
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
        //SpinClockwiseRobotForSeconds(3);
        //MoveRobotForwardForSeconds(1);
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

        DecideFoundationRoutine();
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
                         case 0 :

                                        telemetry.addData("range", String.format("%.01f mm", FTCRobot.getDistRightRange(sensorUnit)));
                                       telemetry.update();

                         case 1 :
                                        telemetry.addData("range", String.format("%.01f cm", FTCRobot.getDistRightRange(sensorUnit)));
                                       telemetry.update();

                         case 2 :
                                        telemetry.addData("range", String.format("%.01f m", FTCRobot.getDistRightRange(sensorUnit)));
                                       telemetry.update();

                         case 3 :
                                           telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistRightRange(sensorUnit)));
                                       telemetry.update();
                        default :
                                        telemetry.addData("range", String.format("%.01f in", FTCRobot.getDistRightRange(sensorUnit)));
                                       telemetry.update();

               }


        }
      FTCRobot.stopRobot();
  }



public void IdentifySkystoneAndKick(){
    telemetry.addData("Method Call:", "IdentifySkystoneAndKick");
    telemetry.update();
    MoveRobotBackwardForSeconds(0.25);
    MoveRobotRightForSeconds(1);
    sleep(500); //added sleep to ensure robot stops and ready to down arm
    FTCRobot.autoarmDown();
    sleep(300); //added slip to ensure arm is fitted with block edges
    FTCRobot.stopRobot();

}

public void GoToFoundationDropStoneFirstScan(){
     telemetry.addData("Method Call:", "GoToFoundationDropStoneFirstScan");
     telemetry.update();
     MoveRobotLeftForSeconds(3.0);
     SpinClockwiseRobotForSeconds(1.8); //(1.15)
     FTCRobot.Default_Motor_Power = 1 ;
     MoveRobotRightForSeconds(3);

     // set the all robot's motor power variable
     FTCRobot.Default_Motor_Power = 0;
     //set Robot Angle in direction
     FTCRobot.Default_Robot_Angle = defaultRobotAngle;
     FTCRobot.autoarmUp();
     sleep(500);
     FTCRobot.Default_Motor_Power = 1;
     MoveRobotRightForSeconds(0.5);
     SpinCounterClockwiseRobotForSeconds(0.7);
     MoveRobotRightForSeconds(1);
     FTCRobot.Default_Motor_Power = 0;
     FTCRobot.autoarmDown();
     FTCRobot.Default_Motor_Power = 1;
     MoveRobotLeftForSeconds(1);
     MoveRobotForwardForSeconds(2);
     FTCRobot.Default_Motor_Power = 0;
     FTCRobot.stopRobot();
     idle();


}
public void GoToFoundationDropStoneSecondScan(){
         telemetry.addData("Method Call:", "GoToFoundationDropStoneSecondScan");
         telemetry.update();
         MoveRobotLeftForSeconds(3.0);
         SpinClockwiseRobotForSeconds(1.8); //(1.15)
         FTCRobot.Default_Motor_Power = 1 ;
         MoveRobotRightForSeconds(3);

         // set the all robot's motor power variable
         FTCRobot.Default_Motor_Power = 0;
         //set Robot Angle in direction
         FTCRobot.Default_Robot_Angle = defaultRobotAngle;
         FTCRobot.autoarmUp();
         sleep(500);
         FTCRobot.Default_Motor_Power = 1;
         MoveRobotRightForSeconds(0.5);
         SpinCounterClockwiseRobotForSeconds(0.7);
         MoveRobotRightForSeconds(1);
         FTCRobot.Default_Motor_Power = 0;
         FTCRobot.autoarmDown();
         FTCRobot.Default_Motor_Power = 1;
         MoveRobotLeftForSeconds(1);
         MoveRobotForwardForSeconds(2);
         FTCRobot.Default_Motor_Power = 0;
         FTCRobot.stopRobot();
         idle();

}
public void GoToFoundationDropStoneThirdScan(){
         telemetry.addData("Method Call:", "GoToFoundationDropStoneSecondScan");
         telemetry.update();
         MoveRobotLeftForSeconds(3.0);
         SpinClockwiseRobotForSeconds(1.8); //(1.15)
         FTCRobot.Default_Motor_Power = 1 ;
         MoveRobotRightForSeconds(3);

         // set the all robot's motor power variable
         FTCRobot.Default_Motor_Power = 0;
         //set Robot Angle in direction
         FTCRobot.Default_Robot_Angle = defaultRobotAngle;
         FTCRobot.autoarmUp();
         sleep(500);
         FTCRobot.Default_Motor_Power = 1;
         MoveRobotRightForSeconds(0.5);
         SpinCounterClockwiseRobotForSeconds(0.7);
         MoveRobotRightForSeconds(1);
         FTCRobot.Default_Motor_Power = 0;
         FTCRobot.autoarmDown();
         FTCRobot.Default_Motor_Power = 1;
         MoveRobotLeftForSeconds(1);
         MoveRobotForwardForSeconds(2);
         FTCRobot.Default_Motor_Power = 0;
         FTCRobot.stopRobot();
         idle();
}

public void DecideFoundationRoutine(){
        telemetry.addData("Method Call:", "GoToFoundationDropStoneSecondScan");
        telemetry.update();
        switch(intScanCount) {
            case 0 :
                    GoToFoundationDropStoneFirstScan();
                    break;
            case 1 :
                    GoToFoundationDropStoneSecondScan();
                    break;
            case 2 :
                    GoToFoundationDropStoneThirdScan();
                    break;

               }

}
  /*public void InitiateMineralDetection(TensorFlowMineral myTensorFlow ){


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
           String MyMineral =  myTensorFlow.MineralPosition(telemetry);
           if (MyMineral != "None"){
               telemetry.addData("skrPosMineral:", MyMineral);
               switch(MyMineral) {
                   case "Left" :
                       RobotDetectedMineralPosition = "Left";
                       myTensorFlow.killVifuria();
                       kickLeftMineral(2.7);
                       GoToDepoFromLeftMineral();
                       break;
                   case "Center" :
                       RobotDetectedMineralPosition = "Center";
                       myTensorFlow.killVifuria();
                       kickCenterMineral(2.3);
                       GoToDepoFromCenterMineral();
                       break;
                   case "Right" :
                       RobotDetectedMineralPosition = "Right";
                       myTensorFlow.killVifuria();
                       kickRightMineral(2.4);
                       GoToDepoFromRightMineral();
                       break;

               }
           }

         telemetry.update();
         // sleep(1000);
        }
        myTensorFlow.killVifuria();
  }

  public void DownFromLanderForSeconds(double runForSecond) {
      FTCRobot.downFromLander();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        FTCRobot.stopRobot();
  }

   public void HangToLanderForSeconds(double runForSecond) {
      FTCRobot.hangToLander();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        FTCRobot.stopRobot();
  }

  public void RetractElevator(){
      FTCRobot.hangToLander();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < 5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        FTCRobot.stopRobot();
  }

  public void kickLeftMineral(double runForSecond){
      telemetry.addData("MineralAction","Preparing to Kick Left");
      telemetry.update();


      FTCRobot.Default_Robot_Angle = -35 ;
      FTCRobot.moveForward();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.Default_Robot_Angle = defaultRobotAngle ;
      FTCRobot.stopRobot();

  }

  public void kickCenterMineral(double runForSecond){
      telemetry.addData("MineralAction","Preparing to Kick Center");
      telemetry.update();

      FTCRobot.Default_Robot_Angle = -10 ;
      FTCRobot.moveForward();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.Default_Robot_Angle = defaultRobotAngle ;
      FTCRobot.stopRobot();
  }

  public void kickRightMineral(double runForSecond){
      telemetry.addData("MineralAction","Preparing to Kick Right");
      telemetry.update();

      FTCRobot.Default_Robot_Angle = 20 ;
      FTCRobot.moveForward();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < runForSecond)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
      FTCRobot.Default_Robot_Angle = defaultRobotAngle ;
      FTCRobot.stopRobot();
  }

  public void GoToDepoFromLeftMineral(){
      telemetry.addData("DepoDestination","Preparing to move to Depot from Left position");
      telemetry.update();
            //Moving Robot back a little
      MoveRobotBackwardForSeconds(0.6);
      MoveRobotLeftForSeconds(1.8);
      //Turning it counter clockwise
      //SpinCounterClockwiseRobotForSeconds(1.09);
      //MoveRobotForwardForSeconds(4);
      SpinCounterClockwiseRobotForSeconds(0.6);
      //MoveRobotRightForSeconds(3);
      //marker Code and go back backward to crater and partk it there

      //FTCRobot.Default_Robot_Angle
       MoveRobotRightForSeconds(3.6);
        dropMarkerForSeconds();
       //FTCRobot.Default_Robot_Angle = 10;
      // SpinClockwiseRobotForSeconds(0.03);

       FTCRobot.Default_Motor_Power = 0.7;
       MoveRobotLeftForSeconds(3.7);
       //FTCRobot.Default_Motor_Power = defaultRobotPower;
        FTCRobot.Default_Motor_Power = 0;
       FTCRobot.stopRobot();
  }
   public void GoToDepoFromCenterMineral(){
      telemetry.addData("DepoDestination","Preparing to move to Depot from Center position");
      telemetry.update();
       MoveRobotBackwardForSeconds(1);
      MoveRobotLeftForSeconds(3.8);
      //Turning it counter clockwise
      SpinCounterClockwiseRobotForSeconds(0.55);
      //marker Code and go back backward to crater and partk it there
      MoveRobotRightForSeconds(4.3);
      dropMarkerForSeconds();

      SpinCounterClockwiseRobotForSeconds(0.01);
      FTCRobot.Default_Motor_Power = 0.7;
      MoveRobotLeftForSeconds(3.55);
       FTCRobot.Default_Motor_Power = 0;
      FTCRobot.stopRobot();
  }

   public void GoToDepoFromRightMineral(){
        telemetry.addData("DepotDestination","Preparing to move to Depot from Right position");
      telemetry.update();
      //Moving Robot back a little
      MoveRobotBackwardForSeconds(1);
      MoveRobotLeftForSeconds(4.8);
      //Turning it counter clockwise
      //SpinCounterClockwiseRobotForSeconds(1.09);
      //MoveRobotForwardForSeconds(4);
      SpinCounterClockwiseRobotForSeconds(0.6);
      //MoveRobotRightForSeconds(3);
      //marker Code and go back backward to crater and partk it there
      //FTCRobot.Default_Robot_Angle
       MoveRobotRightForSeconds(3.9);
       dropMarkerForSeconds();

       //FTCRobot.Default_Robot_Angle = 10;
       //SpinCounterClockwiseRobotForSeconds(0.02);
       FTCRobot.Default_Motor_Power = 0.7;
       MoveRobotLeftForSeconds(3.4);
       FTCRobot.Default_Motor_Power = 0;
       FTCRobot.stopRobot();
  }

  /*public void dropMarkerForSeconds(){
       runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < .2)) {
            FTCRobot.moveArmUp();
      }
      FTCRobot.stopArm();
      runtime.reset();
      while (opModeIsActive() && (runtime.seconds() < 1)) {
            FTCRobot.dropMarker();
      }*/

   //   FTCRobot.markerServo.setPosition(0.5);

  }
  /*
  public void InitiateMineralDetectionRevised(){
      TensorFlowMineral myTensorFlow = new TensorFlowMineral(telemetry,hardwareMap);
      int MaxRightRotationCounter = 3;
      int RightRotationCompleted = 0;
      int MaxLeftRotationCounter = 6 ;
      int LeftRotationCompleted = 0;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
           String MyMineral =  myTensorFlow.MineralPosition(telemetry);

           if (MyMineral != "None"){
               telemetry.addData("skrPosMineral:", MyMineral);
               switch(MyMineral) {
                   case "Left" :
                       kickLeftMineral(0);
                       break;
                   case "Center" :
                       kickCenterMineral(2);
                       break;
                   case "Right" :
                       kickRightMineral(2.7);
                       GoToDepoFromRightMineral();
                       break;

               }
           }
           else {
                if (RightRotationCompleted == 0){
                    for (int i=0;i<=MaxRightRotationCounter;i++){
                        SpinClockwiseRobotForSeconds(0.05);
                        if (i == MaxRightRotationCounter){
                            RightRotationCompleted = 1;
                        }
                    }
                }
                if (LeftRotationCompleted == 0){
                    for (int i=0;i<=MaxLeftRotationCounter;i++){
                        SpinCounterClockwiseRobotForSeconds(0.05);

                        if (i == MaxLeftRotationCounter){
                            LeftRotationCompleted = 1;
                        }
                    }
                }
           }

         telemetry.update();
         sleep(500);
        }
        myTensorFlow.killVifuria();
  }
  */
