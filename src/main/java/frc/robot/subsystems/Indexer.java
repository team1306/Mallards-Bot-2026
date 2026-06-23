package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {
  private final TalonFX motor;
  private final DutyCycleOut dutyCycleRequest;

  public Indexer() {
    motor = new TalonFX(Constants.INDEXER_MOTOR_ID, Constants.CAN_BUS);

    TalonFXConfiguration config = new TalonFXConfiguration()
      .withCurrentLimits(new CurrentLimitsConfigs()
        .withSupplyCurrentLimit(Amps.of(40))
        .withSupplyCurrentLimitEnable(true)
        .withStatorCurrentLimit(Amps.of(50))
        .withStatorCurrentLimitEnable(true))
      .withMotorOutput(new MotorOutputConfigs()
        .withInverted(InvertedValue.Clockwise_Positive)
        .withNeutralMode(NeutralModeValue.Brake));

    motor.getConfigurator().apply(config);

    dutyCycleRequest = new DutyCycleOut(0).withEnableFOC(true);
  }

  public void setDutyCycle(double dutyCycle) {
    motor.setControl(dutyCycleRequest.withOutput(dutyCycle));
  }
}
