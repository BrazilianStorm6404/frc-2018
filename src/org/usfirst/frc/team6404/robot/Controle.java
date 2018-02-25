package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Controle {
	private PWMSpeedController m1,m2,m3,m4, m5, m6 ,m7, m8;
	private SpeedControllerGroup m12;
	private SpeedControllerGroup m34;
	private DigitalInput limit1;
	protected Joystick controller;
	private DifferentialDrive drive;
	boolean mode;
	
	public Controle() {
		this.m1 = new Spark(0);
		this.m2 = new Spark(1);
		this.m3 = new Spark(2);
		this.m4 = new Spark(3);
		this.m12= new SpeedControllerGroup(m1,m2);
		this.m34= new SpeedControllerGroup(m3,m4);
		this.drive= new DifferentialDrive(m12,m34);
		this.limit1 = new DigitalInput(7);
		this.controller = new Joystick(0);
		this.m5 = new Spark(4);
		this.m7 = new VictorSP(7);
		this.m8 = new Jaguar(8);
		this.m6 = new Spark(5);
	}
	public void tracao() {
		drive.arcadeDrive(controller.getY(), controller.getX());
	}
	
	public void botaoLTeRT() {

		if (controller.getRawAxis(2) > 0.2) {
			m5.set(controller.getRawAxis(2));
			m6.set(-controller.getRawAxis(2) * 0.965);
		} else if (controller.getRawAxis(3) > 0.2) {
			m5.set(-controller.getRawAxis(3));
			m6.set(controller.getRawAxis(3) * 0.965);
		} else {
			
			m5.set(0);
			m6.set(0);
		}
	}

	public void botaoX() {
		/*if(controller.getRawButtonPressed(3)) {
			if (mode && limit1.get()) {
				m7.set(0.7);
			} else if (!mode && limit2.get()){
				m7.set(-0.7);
			} 
			mode = !mode;
		} else if (controller.getRawButtonReleased(3)) {
			m7.set(0);
		} 
		if ((mode && !limit1.get()) || (!mode && !limit2.get())) {
			m7.set(0);
		}*/
	}
	
	public void botaoLBeRB() {
		if (controller.getRawButton(5) /*&& !limit1.get()*/) {
			m7.set(0.7);
			m8.set(-0.7);
		} else if (controller.getRawButton(6)) {
			m7.set(-0.7);
			m8.set(0.7);
		} else {
			m7.set(0);
			m8.set(0);
		}
	}
}