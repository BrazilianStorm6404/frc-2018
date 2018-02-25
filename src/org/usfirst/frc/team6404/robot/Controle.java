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
	private PWMSpeedController tracao1,tracao2,tracao3,tracao4, subidaEsq, subidaDir ,roleteEsq, roleteDir;
	private SpeedControllerGroup motoresFrontais;
	private SpeedControllerGroup motoresTraseiros;
	private DigitalInput limit1;
	protected Joystick controller;
	private DifferentialDrive drive;
	boolean mode;
	
	public Controle() {
		this.tracao1 = new Spark(0);
		this.tracao2 = new Spark(1);
		this.tracao3 = new Spark(2);
		this.tracao4 = new Spark(3);
		this.motoresFrontais= new SpeedControllerGroup(tracao1,tracao2);
		this.motoresTraseiros= new SpeedControllerGroup(tracao3,tracao4);
		this.drive= new DifferentialDrive(this.motoresFrontais,this.motoresTraseiros);
		this.limit1 = new DigitalInput(7);
		this.controller = new Joystick(0);
		this.subidaEsq = new Spark(4);
		this.subidaDir = new Spark(5);
		this.roleteDir = new VictorSP(7);
		this.roleteEsq = new Jaguar(8);
	}
	public void tracao() {
		drive.arcadeDrive(controller.getY(), controller.getX());
	}
	
	public void botaoLTeRT() {

		if (controller.getRawAxis(2) > 0.2) {
			subidaEsq.set(controller.getRawAxis(2));
			subidaDir.set(-controller.getRawAxis(2) * 0.965);
		} else if (controller.getRawAxis(3) > 0.2) {
			subidaEsq.set(-controller.getRawAxis(3));
			subidaDir.set(controller.getRawAxis(3) * 0.965);
		} else {
			
			subidaEsq.set(0);
			subidaDir.set(0);
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
			roleteEsq.set(0.7);
			roleteDir.set(-0.7);
		} else if (controller.getRawButton(6)) {
			roleteEsq.set(-0.7);
			roleteDir.set(0.7);
		} else {
			roleteEsq.set(0);
			roleteDir.set(0);
		}
	}
}