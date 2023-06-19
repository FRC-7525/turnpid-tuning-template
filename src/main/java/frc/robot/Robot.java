// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    Drivetrain drivetrain = new Drivetrain();
    DrivetrainTest tester = new DrivetrainTest(drivetrain);

    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        tester.init();
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.periodic();
        tester.periodic();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
    
    @Override
    public void simulationInit() {
    }
    
    @Override
    public void simulationPeriodic() {
    }
}
