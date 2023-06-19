
package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain {
    private DifferentialDrivetrainSim driveSim = DifferentialDrivetrainSim.createKitbotSim(
        KitbotMotor.kDualCIMPerSide, // 2 CIMs per side.
        KitbotGearing.k10p71, // 10.71:1
        KitbotWheelSize.kSixInch, // 6" diameter wheels.
        null // No measurement noise.
    );
    private double setpoint = 0;

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public void periodic() {
        // In this periodic function, you
        // need to get your robot to turn to whatever
        // angle the setpoint variable gives.
        // This is where you should edit!
        driveSim.setInputs(-4, 4);

        // Do not edit -- for simulation!
        SmartDashboard.putNumber("Heading", getCurrentAngle());
        driveSim.update(0.02);
    }

    // DO NOT EDIT BELOW THIS LINE!
    // For simulation.

    public double getCurrentAngle() {
        return driveSim.getHeading().getDegrees();
    }

    public void reset() {
        driveSim.setPose(new Pose2d());
    }
}
