/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.sql.Driver;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.baseCommand;
import frc.robot.subsystems.motor_control;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static motor_control motorControl = new motor_control();
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  public int testloop = 0;


  NetworkTableEntry Distance;
  NetworkTableEntry VideoTimestamp;
  NetworkTableEntry cargoDetected;
  NetworkTableEntry cargoYaw;
  NetworkTableEntry tapeDetected;
  NetworkTableEntry tapeYaw;
  NetworkTableEntry Driver;
  NetworkTableEntry Tape;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new baseCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("MonsterVision");
    VideoTimestamp = table.getEntry("VideoTimestamp");
    cargoDetected= table.getEntry("cargoDetected");
    cargoYaw = table.getEntry("cargoYaw");
    tapeDetected = table.getEntry("tapeDetected");
    tapeYaw = table.getEntry("tapeYaw");
    Driver = table.getEntry("Driver");
    Tape = table.getEntry("Tape");
    Distance = table.getEntry("Distance");
    Tape.setBoolean(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    if(testloop++ > 50){
      SmartDashboard.putNumber("Joystick X value", OI.driveController.getRawAxis(0));
      SmartDashboard.putNumber("Joystick Y value", OI.driveController.getRawAxis(1));
      SmartDashboard.putNumber("Joystick Z value", OI.driveController.getRawAxis(2));
      SmartDashboard.putNumber("MCTwist", OI.driveController.getRawAxis(3));
      SmartDashboard.putBoolean("Button 1", OI.Button1.get());
      SmartDashboard.putBoolean("Button 2", OI.Button2.get());
      SmartDashboard.putBoolean("Button 3", OI.Button3.get());
      SmartDashboard.putBoolean("Button 4", OI.Button4.get());
      CameraServer.getInstance().startAutomaticCapture();
      System.out.println("Test");
      testloop = 0;
      /*double videotimestamp = 0;
      double cargoyaw = 0;
      double tapeyaw = 0;
      boolean driver = false;
      boolean tape = true;
      boolean cargodetected = false;
      boolean tapedetected = false;*/
      Distance.setDouble(0);
      VideoTimestamp.setDouble(0);
      cargoYaw.setDouble(0);
      tapeYaw.setDouble(0);
      Driver.setBoolean(false);
      SmartDashboard.putBoolean("Tape", Tape.getBoolean(false));
      SmartDashboard.putBoolean("Driver", Driver.getBoolean(false));
      SmartDashboard.putNumber("Distance", Distance.getDouble(0));
      SmartDashboard.putNumber("VideoTimestamp", VideoTimestamp.getDouble(0));
      SmartDashboard.putNumber("cargoYaw", cargoYaw.getDouble(0));
      SmartDashboard.putBoolean("cargoDetected", cargoDetected.getBoolean(false));
      SmartDashboard.putBoolean("tapeDetected", tapeDetected.getBoolean(false));
      SmartDashboard.putNumber("tapeYaw", tapeYaw.getDouble(0));
      if(OI.driveController.getRawButton(1) == true){
        Tape.setBoolean(false);
      } 
      if(OI.driveController.getRawButton(2)){ 
        Tape.setBoolean(true);
      }  
    }
  }
}
