package frc.robot.commands.Drivetrain;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;
import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Elevator.Elevator;
import frc.robot.commands.Elevator.ElevatorDown;
import frc.robot.commands.Elevator.ElevatorUp;
import frc.robot.commands.Grabber.Grabber;
import frc.robot.commands.Grabber.GrabberCommand;
import frc.robot.commands.Shooter.Shooter;
import frc.robot.commands.Shooter.ShooterCommand;

import java.nio.file.Path;

public class AutoCommand extends SequentialCommandGroup{
    public AutoCommand(Drivetrain drivetrain, String autoChoice, Elevator elevator, Grabber grabber, Shooter shooter){
        addRequirements(drivetrain);
        addRequirements(elevator);
        addRequirements(shooter);
        addRequirements(grabber);
        drivetrain.zeroHeading();

//        Pose2d start = new Pose2d(0,0,new Rotation2d(0));
//        Pose2d end = new Pose2d(3,0,new Rotation2d(Math.PI));

//        SmartDashboard.putNumber("pose/startPose", start.getRotation().getDegrees());
//        SmartDashboard.putNumber("pose/endPose", end.getRotation().getDegrees());

        PathPlannerTrajectory trajectory = null;
        switch (autoChoice) {
        case "DoNothing":
            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                    //      new InstantCommand(()-> SmartDashboard.putNumber("navx/endRotation", drivetrain.getPose().getRotation().getDegrees())), common Simon L
                    new InstantCommand(()-> drivetrain.setPercent(0, 0))
            );
            break;
        case "GrabBunny":
            trajectory = PathPlanner.loadPath("ToTotes", new PathConstraints(3.5, 2));
            PathPlannerTrajectory trajectory1 = PathPlanner.loadPath("UnToTotes", new PathConstraints(3.5, 2));

            PPRamseteCommand driveCommand = new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain);

            PPRamseteCommand driveCommand3 = new PPRamseteCommand(
                    trajectory1,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain
            );


            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                driveCommand,
                new ElevatorUp(elevator),
                grabber.setTrue(),
                grabber.setFalse(),
                new ElevatorDown(elevator),
                driveCommand3,
                new InstantCommand(() -> drivetrain.setPercent(0, 0))
            );
            break;
        case "InNOut":
            trajectory = PathPlanner.loadPath("InNOut", new PathConstraints(3.5, 2));

            PPRamseteCommand driveCommand2 = new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain
            );


            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                    driveCommand2,
                    new InstantCommand(() -> drivetrain.setPercent(0, 0))
            );
            break;
        case "SprayNPraySeq":
            trajectory = PathPlanner.loadPath("ToTotes", new PathConstraints(3.5, 2));

            PPRamseteCommand driveCommand4 = new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain
            );

            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                    driveCommand4,
                    new ShooterCommand(shooter, 0),
                    new ShooterCommand(shooter, 4),
                    new ShooterCommand(shooter, 1),
                    new ShooterCommand(shooter, 2),
                    new ShooterCommand(shooter, 3),
                    new InstantCommand(() -> drivetrain.setPercent(0, 0))
            );
            break;
        case "SprayNPrayFull":
            trajectory = PathPlanner.loadPath("ToTotes", new PathConstraints(3.5, 2));

            PPRamseteCommand driveCommand5 = new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain
            );


            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                    new ParallelCommandGroup(driveCommand5,
                            new ShooterCommand(shooter, 0),
                            new ShooterCommand(shooter, 1),
                            new ShooterCommand(shooter, 2),
                            new ShooterCommand(shooter, 3),
                            new ShooterCommand(shooter, 4)),
                    new InstantCommand(() -> drivetrain.setPercent(0, 0))
            );
            break;
            case "RamAutoLeft":
                trajectory = PathPlanner.loadPath("RamAuto", new PathConstraints(3.5, 2));
                PathPlannerTrajectory trajectory2 = PathPlanner.loadPath("SuppRamAutoLeft", new PathConstraints(3.5, 2));

                PPRamseteCommand driveCommand61 = new PPRamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        RamseteConfig.ramseteController,
                        RamseteConfig.feedForward,
                        RamseteConfig.kDriveKinematics,
                        drivetrain::getWheelSpeeds,
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        drivetrain::setVolts,
                        drivetrain);

                PPRamseteCommand driveCommand62 = new PPRamseteCommand(
                        trajectory2,
                        drivetrain::getPose,
                        RamseteConfig.ramseteController,
                        RamseteConfig.feedForward,
                        RamseteConfig.kDriveKinematics,
                        drivetrain::getWheelSpeeds,
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        drivetrain::setVolts,
                        drivetrain);

                drivetrain.resetOdometry(trajectory.getInitialPose());

                addCommands(
                        driveCommand61,
                        driveCommand62,
                        //      new InstantCommand(()-> SmartDashboard.putNumber("navx/endRotation", drivetrain.getPose().getRotation().getDegrees())), common Simon L
                        new InstantCommand(()-> drivetrain.setPercent(0, 0))
                );
                break;
            case "RamAutoRight":
                trajectory = PathPlanner.loadPath("RamAuto", new PathConstraints(3.5, 2));
                PathPlannerTrajectory trajectory3 = PathPlanner.loadPath("SuppRamAutoRight", new PathConstraints(3.5, 2));

                PPRamseteCommand driveCommand71 = new PPRamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        RamseteConfig.ramseteController,
                        RamseteConfig.feedForward,
                        RamseteConfig.kDriveKinematics,
                        drivetrain::getWheelSpeeds,
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        drivetrain::setVolts,
                        drivetrain);

                PPRamseteCommand driveCommand72 = new PPRamseteCommand(
                        trajectory3,
                        drivetrain::getPose,
                        RamseteConfig.ramseteController,
                        RamseteConfig.feedForward,
                        RamseteConfig.kDriveKinematics,
                        drivetrain::getWheelSpeeds,
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                        drivetrain::setVolts,
                        drivetrain);

                drivetrain.resetOdometry(trajectory.getInitialPose());

                addCommands(
                        driveCommand71,
                        driveCommand72,
                        //      new InstantCommand(()-> SmartDashboard.putNumber("navx/endRotation", drivetrain.getPose().getRotation().getDegrees())), common Simon L
                        new InstantCommand(()-> drivetrain.setPercent(0, 0))
                );
                break;
        default:
            trajectory = PathPlanner.loadPath("RamAuto", new PathConstraints(3.5, 2));

            PPRamseteCommand driveCommand6 = new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    RamseteConfig.ramseteController,
                    RamseteConfig.feedForward,
                    RamseteConfig.kDriveKinematics,
                    drivetrain::getWheelSpeeds,
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    new PIDController(Constants.KP_DRIVE_VEL, 0, 0),
                    drivetrain::setVolts,
                    drivetrain);

            drivetrain.resetOdometry(trajectory.getInitialPose());

            addCommands(
                    driveCommand6,
                    //      new InstantCommand(()-> SmartDashboard.putNumber("navx/endRotation", drivetrain.getPose().getRotation().getDegrees())), common Simon L
                    new InstantCommand(()-> drivetrain.setPercent(0, 0))
            );
            break;
        }

    }
    

    
}