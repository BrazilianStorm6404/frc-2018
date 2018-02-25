package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

public class Movimentar {
	static final double defaultOutputRange = 0.3, defaultAbsoluteTolerance = 1, defaultSetPoint = 0, defaultkP = 0.1,
			defaultkI = 0.0, defaultkD = 0.1;
	private double outputAngle = 0, outputDistance = 0;
	private ADXRS450_Gyro gyroscope = null;
	private PIDController pidAngle = null, pidDistance = null;
	private Encoder encoder = null;
	private PWMSpeedController motor0, motor1, motor2, motor3, motor4, motor5, motor6;
	private DigitalInput limit1, limit2;
	private Encoder encSubida;
	Timer tempo;
	public Movimentar(Encoder encoder, ADXRS450_Gyro gyroscope, PWMSpeedController motor0, PWMSpeedController motor1,
			PWMSpeedController motor2, PWMSpeedController motor3,PWMSpeedController motor4,PWMSpeedController motor5,  PWMSpeedController motor6, DigitalInput limit1, DigitalInput limit2) {
		this.motor0 = motor0;
		this.motor1 = motor1;
		this.motor2 = motor2;
		this.motor3 = motor3;
		this.motor4 = motor4;
		this.motor5 = motor5;
		this.gyroscope = gyroscope;
		this.motor6 = motor6;
		this.encoder = encoder;
		this.limit1 = limit1;
		this.limit2 = limit2;
		this.encSubida = new EncoderBuilder(0, 1); //Colocar entradas do Encoder depois
	}

	public boolean walkStraight(int distance) {
		verifyD();
		if (pidDistance.getSetpoint() != distance) {
			pidDistance.disable();
			pidDistance.setSetpoint(distance);
			pidDistance.enable();
		}
		if (pidDistance.onTarget()) {
			pidDistance.disable();
			return true;
		} else {
			walk(outputDistance);
			return false;
		}
	}

	public void parar() {
		move(0.0);
	}

	private void walk(double speed) {
		verifyA();
		if (!pidAngle.isEnabled())
			pidAngle.enable();
		if (Math.abs(outputAngle) > 0.2)
			spin(outputAngle);
		else
			move(speed);
	}

	public boolean turn(double angulo) {
		verifyA();
		if (pidAngle.getSetpoint() != angulo) {
			pidAngle.disable();
			pidAngle.setSetpoint(angulo);
			pidAngle.enable();
		}
		if (!pidAngle.onTarget()) {
			spin(outputAngle);
			return false;
		} else {
			pidAngle.disable();
			return true;
		}
	}

	private void spin(double speed) {
		motor0.set(speed);
		motor1.set(speed);
		motor2.set(speed);
		motor3.set(speed);
	}

	private void move(double speed) {
		motor0.set(-speed);
		motor1.set(-speed);
		motor2.set(speed);
		motor3.set(speed);
	}

	private void verifyA() {
		if (pidAngle == null) {
			pidAngle = new PIDController(defaultkP, defaultkI, defaultkD, gyroscope, new PIDOutput() {
				@Override
				public void pidWrite(double output) {
					outputAngle = output;
				}
			});
			pidAngle.setOutputRange(-defaultOutputRange, defaultOutputRange);
			pidAngle.setAbsoluteTolerance(defaultAbsoluteTolerance);
			pidAngle.setSetpoint(defaultSetPoint);
			pidAngle.disable();
		}
	}

	private void verifyD() {
		if (pidDistance == null) {
			pidDistance = new PIDController(defaultkP, defaultkI, defaultkD, encoder, new PIDOutput() {
				@Override
				public void pidWrite(double output) {
					outputDistance = output;
				}
			});
			pidDistance.setOutputRange(-defaultOutputRange, defaultOutputRange);
			pidDistance.setAbsoluteTolerance(defaultAbsoluteTolerance);
			pidDistance.setSetpoint(defaultSetPoint);
			pidDistance.disable();
		}
	}

	public void desligar() {
		if (pidAngle.isEnabled())
			pidAngle.disable();
		if (pidDistance.isEnabled())
			pidDistance.disable();
	}
	
	public void free() {
		this.motor0.free();
		this.motor1.free();
		this.motor2.free();
		this.motor3.free();
		this.motor4.free();
		this.motor5.free();
		this.motor6.free();
		this.limit1.free();
		this.limit2.free();
	}
	
	public boolean dropCube() {
		if(limit1.get() && !limit2.get()) {
			this.motor6.set(0.5);
			return true;
		}
		return true;
	}

	public boolean elevateClaw(double t) {
		if (tempo != null) {
			tempo = new Timer();
			tempo.start();
			motor4.set(0.5);
			motor5.set(0.5);
		}
		if (tempo.get() > t) {
			motor4.set(0);
			motor5.set(0);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean elevateClaw(int dist) {
		if (encSubida.getStopped()) {
			motor4.set(0.5);
			motor4.set(0.5);
		}
		if (encSubida.get() > dist) {
			motor4.set(0);
			motor5.set(0);
			return true;
		} else {
			return false;
		}
	}	
}
