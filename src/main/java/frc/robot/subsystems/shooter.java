package frc.robot.subsystems;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.function.Supplier;

public class shooter extends SubsystemBase
{
    TalonFX motor1 = new TalonFX(5, new CANBus("Test Bed CANivore"));
    TalonFX motor2 = new TalonFX(6, new CANBus("Test Bed CANivore"));

    public shooter(){    
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.withNeutralMode(NeutralModeValue.Coast).withInverted(InvertedValue.Clockwise_Positive);
        config.CurrentLimits.withSupplyCurrentLimitEnable(true).withSupplyCurrentLimit(Amp.of(100));
        config.Slot0.withKP(10);
        motor1.getConfigurator().apply(config);
        motor2.getConfigurator().apply(config);
    }

    public void SpinUp(Supplier<AngularVelocity> speed){

        if(speed.get().gt(RotationsPerSecond.of(65))){
            System.out.println("speed too fast");
            return;
        }
        
        else if(speed.get().lt(RotationsPerSecond.of(-65))){
            System.out.println("speed too fast ");
            return;
        }

        motor1.setControl(new VelocityVoltage(speed.get()));
        motor2.setControl(new VelocityVoltage(speed.get()));
    }

    public void StopMotor(){
        motor1.setControl(new NeutralOut());
        motor2.setControl(new NeutralOut());
    }
}
