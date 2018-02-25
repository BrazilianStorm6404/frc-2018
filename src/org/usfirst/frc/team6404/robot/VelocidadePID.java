package org.usfirst.frc.team6404.robot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

public class VelocidadePID extends PID implements PIDSource{
	EncoderBuilder enc= new EncoderBuilder(1,2,false,EncodingType.k4X);
	private PIDSourceType velocidade= PIDSourceType.kDisplacement;
	Timer timer= new Timer();
	private double velo;
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		velocidade=pidSource;
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		
		return velocidade;
	}

	@Override
	public double pidGet() {
		switch (velocidade) {
	      case kDisplacement:
	        return getVel();
	      default:
	        
		return 0;
	}
	}
	public double getVel() {
		velo=enc.getDistance()*100/timer.get();
		return velo;
	}
}
