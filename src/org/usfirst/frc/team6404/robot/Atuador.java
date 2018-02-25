package org.usfirst.frc.team6404.robot;
import edu.wpi.first.wpilibj.PWMSpeedController;
public class Atuador {
	PWMSpeedController m1,m2,m3,m4;
	
	public Atuador(PWMSpeedController m1){
		this.m1= m1;
		/** com 1 Controladores*/
	}
	public Atuador(PWMSpeedController m1,PWMSpeedController m2){
		this.m1= m1;
		this.m2= m2;
		/** com 2 Controladores*/
	}
	public Atuador(PWMSpeedController m1, PWMSpeedController m2, PWMSpeedController m3){
		this.m1= m1;
		this.m2= m2;
		this.m3= m3;
		/** com 3 Controladores*/
	}
	public Atuador(PWMSpeedController m1, PWMSpeedController m2, PWMSpeedController m3, PWMSpeedController m4){
		this.m1= m1;
		this.m2= m2;
		this.m3= m3;
		this.m4= m4;
		/** com 4 Controladores*/	
	}
	
}
