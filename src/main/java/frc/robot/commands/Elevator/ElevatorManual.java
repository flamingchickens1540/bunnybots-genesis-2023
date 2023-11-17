package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class ElevatorManual extends CommandBase {
    private final Elevator elevator;
    private final CommandXboxController controller;

    public ElevatorManual(Elevator elevator, CommandXboxController controller){
        this.elevator = elevator;
        this.controller = controller;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        double velocity = controller.getRightTriggerAxis() - controller.getLeftTriggerAxis();
        velocity *= Constants.ElevatorConstants.MANUAL_SCALING;
        velocity = Math.abs(velocity) > Constants.ElevatorConstants.MANUAL_DEADZONE ? velocity : 0;

        elevator.setVelocity(velocity);
    }
}
