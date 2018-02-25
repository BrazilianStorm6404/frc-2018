package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AdaptPID implements PIDSource {
	Encoder enc;
	PIDSourceType pidType = PIDSourceType.kRate;
	boolean tipo;
	long ant = 0, now = 0;
	double dAnt = 0, dNow = 0;
	protected double motor;

	public AdaptPID(Encoder encoder) {
		this.enc = encoder;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		pidType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return pidType;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return getVelocidade();
	}
	
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public double getVelocidade() {
		System.out.println(enc.getDistance());
		return enc.getDistance();
	}
}