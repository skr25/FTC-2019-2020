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
    MyRobot FTCRobot = new MyRobot();
    @Override   //override instruction to indicate runOpMode is being overriden
    public void runOpMode() throws InterruptedException { //runs when you select program initialization
       //****************************************************************
       //Get and assign all hardware data into variables
        telemetry.addData("Status", "Initialized");
        telemetry.update();
         //Initialize robot
        FTCRobot.init(hardwareMap);
        SkrTensorFlowSkyStone myObjTensorFlow = new SkrTensorFlowSkyStone(telemetry,hardwareMap);
        // Wait for the game to start (driver presses PLAY)
        FTCRobot.autoarmUp();
        waitForStart();
        FTCRobot.Default_Motor_Power = 1;
        MoveRobotForwardForSeconds(1);
        MoveRobotRightForSeconds(0.75);
        //SpinClockwiseRobotForSeconds(3);
        //MoveRobotForwardForSeconds(1);
        FTCRobot.Default_Motor_Power = 0.5;
        sleep(1000);
        // int count =1;
        // add count to while count<4
        //count++;
        // we can check the count and decide to move ahead and finish another job
        while (opModeIsActive()&& MyStone == "None" && intScanCount <3) {
           sleep(400);    
           String MyStone =  myObjTensorFlow.StoneName(telemetry);
           
           if (MyStone != "None"){
               telemetry.addData("skrStoneName:", MyStone);
                MoveRobotBackwardForSeconds(0.25);
                MoveRobotRightForSeconds(1); 
                sleep(1000);
               // SpinClockwiseRobotForSeconds(0.2);
                FTCRobot.autoarmDown();
                sleep(300);
                FTCRobot.stopRobot();
                
                break;
           }
           else{
              MoveRobotBackwardForSeconds(0.585);
              intScanCount=+1;
           }
            
         telemetry.update();
         sleep(1000);
        }
        myObjTensorFlow.killVifuria();
        if (intScanCount <3){
          
            MoveRobotLeftForSeconds(3.0);
            SpinClockwiseRobotForSeconds(1.8); //(1.15)
            FTCRobot.Default_Motor_Power = 1 ;
            MoveRobotRightForSeconds(3);
       
            // set the all robot's motor power variable
            FTCRobot.Default_Motor_Power = defaultRobotPower;
            //set Robot Angle in direction
            FTCRobot.Default_Robot_Angle = defaultRobotAngle;
            //Take Robot down from Lander
            //DownFromLanderForSeconds(5.0);
            //Move Right to get off from lander hook
                   //move forward for 3 seconds
            // MoveRobotForwardForSeconds(3);
        
            // SpinClockwiseRobotForSeconds(0.5);
       
            //InitiateMineralDetection(myObjTensorFlow);   
            //InitiateMineralDetectionRevised();
            // Reset the all robot's motor power variable
            FTCRobot.Default_Motor_Power = 0;
            //Reset Robot Angle in direction
            FTCRobot.Default_Robot_Angle = defaultRobotAngle;
            FTCRobot.autoarmUp(); 
            sleep(500);
            FTCRobot.Default_Motor_Power = 1;
            MoveRobotRightForSeconds(0.5);
            SpinCounterClockwiseRobotForSeconds(0.7);
            //FTCRobot.Default_Motor_Power = 0.5;
            MoveRobotRightForSeconds(1);
            FTCRobot.Default_Motor_Power = 0;
            FTCRobot.autoarmDown();
            FTCRobot.Default_Motor_Power = 1;
            MoveRobotLeftForSeconds(1);
            //HangToLanderForSeconds(4);
            MoveRobotForwardForSeconds(2);
            FTCRobot.Default_Motor_Power = 0;
            FTCRobot.stopRobot();
            //sleep(1500);
            idle();
        }
        else {
               FTCRobot.Default_Motor_Power = 0 ;
               FTCRobot.stopRobot() ;
        
        }
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

