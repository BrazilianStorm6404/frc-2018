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
	private Encoder encTracao = null;
	private PWMSpeedController tracao1, tracao2, tracao3, tracao4, subidaEsq, subidaDir, roleteDir, roleteEsq;
	private Encoder encSubida;
	Timer tempo;
	public Movimentar(Encoder encoder, Encoder encSubida, ADXRS450_Gyro gyroscope, PWMSpeedController motor0, PWMSpeedController motor1,
			PWMSpeedController motor2, PWMSpeedController motor3,PWMSpeedController motor4,PWMSpeedController motor5,  PWMSpeedController motor6, PWMSpeedController motor7) {
		this.tracao1 = motor0;
		this.tracao2 = motor1;
		this.tracao3 = motor2;
		this.tracao4 = motor3;
		this.subidaEsq = motor4;
		this.subidaDir = motor5;
		this.gyroscope = gyroscope;
		this.roleteDir = motor6;
		this.encTracao = encoder;
		this.encSubida = encSubida; //Colocar entradas do Encoder depois
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
		tracao1.set(speed);
		tracao2.set(speed);
		tracao3.set(speed);
		tracao4.set(speed);
	}

	private void move(double speed) {
		tracao1.set(-speed);
		tracao2.set(-speed);
		tracao3.set(speed);
		tracao4.set(speed);
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
			pidDistance = new PIDController(defaultkP, defaultkI, defaultkD, encTracao, new PIDOutput() {
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
		this.tracao1.setDisabled();
		this.tracao2.setDisabled();
		this.tracao3.setDisabled();
		this.tracao4.setDisabled();
		this.subidaEsq.setDisabled();
		this.subidaDir.setDisabled();
		this.roleteDir.setDisabled();
	}
	
	public boolean dropCube(double t) {
		if (tempo != null) {
			tempo = new Timer();
			tempo.start();
			this.roleteDir.set(0.5);
			this.roleteEsq.set(0.5);
		}
		if (tempo.get() > t) {
			roleteDir.set(0);
			roleteEsq.set(0);
			tempo = null;
			return true;
		} else {
			return false;
		}
	}

	/*public boolean elevateClaw(double t) {
		if (tempo != null) {
			tempo = new Timer();
			tempo.start();
			motor4.set(0.5);
			motor5.set(0.5);
		}
		if (tempo.get() > t) {
			motor4.set(0);
			motor5.set(0);
			tempo = null;
			return true;
		} else {
			return false;
		}
	}*/
	
	public boolean elevateClaw(int dist) {
		if (encSubida.getStopped()) {
			encSubida.reset();
			subidaEsq.set(0.5);
			subidaDir.set(-0.5);
		}
		if (encSubida.get() > dist) {
			subidaEsq.set(0);
			subidaDir.set(0);
			return true;
		} else {
			return false;
		}
	}	
}
