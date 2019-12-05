package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


public class MyRobot1
{
    /* Public OpMode members. */
    public DcMotor  leftFrontWheel     = null;
    public DcMotor  leftRearWheel      = null;
    public DcMotor  rightFrontWheel    = null;
    public DcMotor  rightRearWheel     = null;
    public DcMotor  elevator           = null;
    public DcMotor  armMotor          = null;
    public Servo intake1           = null;
    public Servo rot              = null;
    private LinearOpMode opMode ;
    
    /* Define Constants with in Class */
    public static final double ZERO_POWER = 0 ;
    public double Default_Motor_Power = 0.50 ;
    public double Default_Robot_Angle = 0 ;
        /* Vreate and Initialize hardware object with null */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MyRobot1(){
     
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        rightFrontWheel = hwMap.dcMotor.get("motorFrontRight");
        leftFrontWheel  = hwMap.dcMotor.get("motorFrontLeft");
        rightRearWheel  = hwMap.dcMotor.get("motorBackRight");
        leftRearWheel   = hwMap.dcMotor.get("motorBackLeft");
        elevator        = hwMap.dcMotor.get("elevator");
        armMotor        = hwMap.dcMotor.get("armMotor");
        rot        = hwMap.get(Servo.class, "rotate");
        intake1     = hwMap.get(Servo.class, "intake1");

        
        /*rightFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        rightRearWheel.setDirection(DcMotor.Direction.REVERSE);
        leftRearWheel.setDirection(DcMotor.Direction.REVERSE);*/
        elevator.setDirection(DcMotor.Direction.REVERSE);  
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        //armMotor2.setDirection(DcMotor.Direction.FORWARD);
        // Set all motors to zero power
        rightFrontWheel.setPower(0);
        leftFrontWheel.setPower(0);
        rightRearWheel.setPower(0);
        leftRearWheel.setPower(0);
        elevator.setPower(0);
        armMotor.setPower(0);
        rot.setPosition(0.5);
        intake1.setPosition(.5);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        rightFrontWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //armMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        // Define and initialize ALL installed servos.
        
    }

        /**
        *
        * @param Vd Robot Speed [0-1]
        * @param Td Robot Angle in degrees
        * @param Vt Direction Change Speed
        * @param wheel Wheel [1-4] LF = 1, RF = 2, LR = 3, RR = 4
        * @return Motor speed
        */
    public double wheelSpeed(double Vd, double Td, double Vt, String wheel) {
        double V = 0;

        Td = Td * (Math.PI / 180);
        switch(wheel) {
            case "LeftFront" :
                V = Vd * Math.sin(Td + (Math.PI / 4)) + Vt;
                break;
            case "RightFront" :
                V = Vd * Math.cos(Td + (Math.PI / 4)) - Vt;
                break;
            case "LeftRear" :
                V = Vd * Math.cos(Td + (Math.PI / 4)) + Vt;
                break;
            case "RightRear" :
                V = Vd * Math.sin(Td + (Math.PI / 4)) - Vt;
                break; 
            default :
                V = 0;
        }
        
        if (V > 1) {
            V = 1;
        }

        return V;
    }
    
    public void moveForward(){
        
        leftFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    
     public void moveBackward(){
        
        leftFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    
    public void moveLeft(){
        
        leftFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    public void moveRight(){
        
        leftFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    
    public void stopRobot(){
        leftFrontWheel.setPower(0);
        leftRearWheel.setPower(0);
        rightFrontWheel.setPower(0);
        rightRearWheel.setPower(0);
        armMotor.setPower(0);
        rot.setPosition(0);
        intake1.setPosition(.5);
    }
    
     public void spinClockwise(){
        
        leftFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    
    public void spinCounterClockwise(){
        
        leftFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftFront"));
        leftRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"LeftRear"));
        rightFrontWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightFront"));
        rightRearWheel.setPower(wheelSpeed(-1*Default_Motor_Power,Default_Robot_Angle,0,"RightRear"));
    }
    
    public void downFromLander(){
      //   elevator.setDirection(DcMotor.Direction.REVERSE);  
        // elevator.setPower(0.5);
        
    }   
    
     public void hangToLander(){
        // elevator.setDirection(DcMotor.Direction.FORWARD);  
         //elevator.setPower(0.5);
        
    }   
    
    public void dropMarker(){
        
      //  markerServo.setPosition(1);
    }
    public void moveArmUp(){
        
      //  armMotor.setPower(1);
        //armMotor2.setPower(1);
    }
    public void moveArmDown(){
        
    //    armMotor.setPower(-1);
      //  armMotor2.setPower(-1);
    }
    public void stopArm(){
        
      //  armMotor.setPower(0);
        //armMotor2.setPower(0);
    }
 }

