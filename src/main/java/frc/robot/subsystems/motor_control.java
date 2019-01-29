/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.baseCommand;
import frc.robot.OI;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class motor_control extends Subsystem {

  public static WPI_TalonSRX leftMotor;
  public static WPI_TalonSRX rightMotor;
  public static DifferentialDrive mainControl;

  public static double forward;
  public static double turn;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new baseCommand());
  }

  public static void setupMotors(){
    leftMotor = RobotMap.leftMotor;
    rightMotor = RobotMap.rightMotor;
    
    mainControl = new DifferentialDrive(leftMotor, rightMotor);

  }

  public static void control(){

    //parameter of .getRawAxis() may need to be changed to accomodate control preference
    forward = OI.driveController.getRawAxis(1);
    turn = OI.driveController.getRawAxis(2);
    
    mainControl.arcadeDrive(forward, turn);
  }
}
