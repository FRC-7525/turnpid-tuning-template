/*
 * This is a testing file!
 * i.e. for reading and understanding, not for editing.
 */

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

enum TestingStage {
    RIGHT_90("Right 90 Degrees", 90, 1),
    LEFT_90("Left 90 Degrees", -90, 1),
    RIGHT_135("Right 135 Degrees", 135, 1),
    LEFT_180("Left 180 Degrees", -180, 1);
    
    public final String tag;
    public final double setpoint;
    public final double seconds;
    private TestingStage(String tag, double setpoint, double seconds) {
        this.tag = tag;
        this.setpoint = setpoint;
        this.seconds = seconds;
    }
}

enum TestState {
    RUNNING,
    CONSISTENCY_CHECK,
    DONE
}

public class DrivetrainTest {
    static final double DELTA = 1e-2; 
    
    private int currentStage = 0;
    private TestingStage[] stages = { TestingStage.RIGHT_90, TestingStage.LEFT_90, TestingStage.RIGHT_135, TestingStage.LEFT_180};
    private Timer timer = new Timer();
    private Drivetrain drivetrain;
    private TestState state = TestState.RUNNING;

    private boolean success = true;
    private int successCount = 0;

    public DrivetrainTest(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void reset() {
        success = true;
        drivetrain.reset();
        state = TestState.RUNNING;
        timer.reset();
    }

    public void init() {
        reset();
        currentStage = 0;
    }

    public void periodic() {
        timer.start();

        if (state == TestState.RUNNING) {
            drivetrain.setSetpoint(stages[currentStage].setpoint);
            if (timer.get() >= stages[currentStage].seconds) {
                if (Math.abs(drivetrain.getCurrentAngle() - stages[currentStage].setpoint) < DELTA) {
                    // Success!
                    System.out.println(String.format("[%s] Converged to setpoint in time! Checking consistency for one more second.", stages[currentStage].tag));
                } else {
                    // Failure
                    System.out.println(String.format("[%s] Failed to converge to setpoint in time (%f seconds). Current heading: %f", stages[currentStage].tag, stages[currentStage].seconds, drivetrain.getCurrentAngle()));
                    success = false;
                }
                timer.reset();
                state = TestState.CONSISTENCY_CHECK;
            }
        } else if (state == TestState.CONSISTENCY_CHECK) {
            if (timer.get() < 1) {
                // Check consistency
                if (Math.abs(drivetrain.getCurrentAngle() - stages[currentStage].setpoint) >= DELTA) {
                    System.out.println(String.format("[%s] Not at setpoint. Current heading: %f, Time over limit: %f", stages[currentStage].tag, drivetrain.getCurrentAngle(), timer.get()));
                    success = false;
                }
            } else {
                if (success) {
                    System.out.println(String.format("[%s] Consistency check succeeded! Continuing to next test.", stages[currentStage].tag, drivetrain.getCurrentAngle()));
                    successCount++;
                } else {
                    System.out.println(String.format("[%s] Consistency check failed. Continuing to next test.", stages[currentStage].tag, drivetrain.getCurrentAngle()));
                }

                if (currentStage + 1 == stages.length) {
                    System.out.println(String.format("All tests complete. Score: %d/%d", successCount, stages.length));
                    state = TestState.DONE;
                } else {
                    // Reset tester state
                    reset();
                    currentStage++;
                }
            }
        }
    }
}
