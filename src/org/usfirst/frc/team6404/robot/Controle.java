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
	protected Joystick controller,controller1;
	private DifferentialDrive drive;
	boolean abertura;
	boolean ativo;
	
	public Controle(PWMSpeedController tracao1,PWMSpeedController tracao2,PWMSpeedController tracao3,PWMSpeedController tracao4,DigitalInput limit1,PWMSpeedController subidaEsquerda,PWMSpeedController subidaDireita,PWMSpeedController roleteEsquerdo, PWMSpeedController roleteDireito) {
		this.tracao1 = tracao1;
		this.tracao2 = tracao2;
		this.tracao3 = tracao3;
		this.tracao4 = tracao4;
		this.motoresFrontais= new SpeedControllerGroup(tracao1, tracao2);
		this.motoresTraseiros= new SpeedControllerGroup(tracao3,tracao4);
		this.drive= new DifferentialDrive(motoresFrontais, motoresTraseiros);
		this.limit1 = limit1;
		this.controller1= new Joystick(1);
		this.controller = new Joystick(0);
		this.subidaEsq = subidaEsquerda;
		this.subidaDir = subidaDireita;
		this.roleteDir = roleteDireito;
		this.roleteEsq = roleteEsquerdo;
	}
	public void tracao() {
		drive.arcadeDrive(-controller.getY(), controller.getX());
	}
	
	public void botoesJoystick2Subida() {

		if (controller1.getRawButton(5) /*&& !limit1.get()*/) {
			subidaDir.set(0.7);
			subidaEsq.set(-0.7);
		} else if (controller1.getRawButton(3)) {
			subidaDir.set(-0.7);
			subidaEsq.set(0.7);
		} else {
			subidaDir.set(0);
			subidaEsq.set(0);
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
	
	public void botoesJoystick2GarraManual() {
		if (controller1.getRawButton(1) /*&& !limit1.get()*/) {
			roleteEsq.set(0.7);
			roleteDir.set(-0.7);
		} else if (controller1.getRawButton(2)) {
			roleteEsq.set(-0.7);
			roleteDir.set(0.7);
		} else {
			roleteEsq.set(0);
			roleteDir.set(0);
		}
	}
	
	public void botoesJoystick2GarraBorda() {
		if (controller1.getRawButtonPressed(4)) {
			ativo = (abertura)? !ativo: true;
			abertura = true;
		} else if (controller1.getRawButtonPressed(6)) {
			ativo = (!abertura)? !ativo: true;
			abertura = false;
		}
		if (ativo) {
			roleteEsq.set(abertura? 0.7 : -0.7);
			roleteEsq.set(abertura? -0.7 : 0.7);
		} else {
			roleteEsq.set(0);
			roleteDir.set(0);
		}
	}
}