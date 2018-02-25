package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
public class PID {
	VelocidadePID velocidade= new VelocidadePID();
	PIDController pid;
	PIDController pidA;
	double saidaPID = 0;
	double saidaPIDA = 0;
	ADXRS450_Gyro giro = new ADXRS450_Gyro();
	public void setPID(double p, double i, double d) {
		pid.setAbsoluteTolerance(0);
		if (pid == null)
			pid = new PIDController(p, i, d, giro, new PIDOutput() {
				
				@Override
				public void pidWrite(double output) {
					saidaPID = output;
				}
			});
		else {
			pid.setPID(p, i, d);
		}
	}
	
	

	private void verify() {
		if (pid == null)
			setPID();
	}

	private void setPID() {
		pid = new PIDController(0.0, 0.0, 0.0, giro, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				saidaPID = output;
			}
		});
		pid.setAbsoluteTolerance(0);
	}	
	public void setSetpoint(double angulo) {
		verify();
		pid.setSetpoint(angulo);
	}
	public void setPIDVel(double p, double i, double d) {
		pidA.setAbsoluteTolerance(0);
		if (pidA == null)
			pidA = new PIDController(p, i, d, velocidade, new PIDOutput() {

				@Override
				public void pidWrite(double output) {
					saidaPIDA = output;
				}
			});
		else {
			pidA.setPID(p, i, d);
		}
	}
	private void verifyA() {
		if (pidA == null)
			setPIDA();
	}

	private void setPIDA() {
		pid = new PIDController(0.0, 0.0, 0.0, velocidade, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				saidaPIDA = output;
			}
		});
		pid.setAbsoluteTolerance(0);
	}
	public void setSetpointA(double velocidade) {
		verifyA();
		pidA.setSetpoint(velocidade);
	}



}
