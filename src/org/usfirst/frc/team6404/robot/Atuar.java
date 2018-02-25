package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Timer;

public class Atuar {
	Timer time = new Timer();
	private double vel, angulo, tempo;
	private Atuador m;
	private float dist, pot;
	PWMSpeedController motor1;
	private double potencia;
	PID sensor;

	public Atuar(PWMSpeedController canal1, double potencia) {
		/** Declarar canal do atuador automatico, com a potencia pre definida */
		this.motor1 = canal1;
		this.potencia = potencia;
	}

	public void mover(double angulo) {

		sensor.pid.disable();
		sensor.pid.enable();
		sensor.giro.reset();
		setAngulo(angulo);
		sensor.setSetpoint(this.angulo);
		sensor.pid.setOutputRange(-potencia, potencia);
		sensor.setPID(0.1, 0, 0.1);// valores imaginarios obviamente
		while (Math.abs(sensor.saidaPID) > 0.1) {
			motor1.set(sensor.saidaPID);
		}
		motor1.set(0);
		sensor.pid.disable();

	}

	/**
	 * Declarar somente angulo para o mecanismo
	 */
	public void mover(double vel, double angulo) {
		/** Declarar o angulo para o mecanismo realizer numa velocidade entre -1 e 1 **/
		setVel(vel);
		setAngulo(angulo);
		sensor.velocidade.enc.reset();
		sensor.velocidade.timer.reset();
		sensor.pid.disable();
		sensor.pidA.disable();
		sensor.giro.reset();
		motor1.set(0);
		sensor.setSetpoint(this.angulo);
		sensor.setSetpointA(this.vel);
		sensor.setPID(0.1, 0.05, 0.1);
		sensor.setPIDVel(0.1, 0, 0.1);
		sensor.pidA.enable();
		sensor.pid.enable();
		while (Math.abs(sensor.saidaPID) > 0.1 && Math.abs(sensor.saidaPIDA) > 0.1) {
			motor1.set((sensor.saidaPID + sensor.saidaPIDA) / 2);
		}
	}

	public void mover(float pot, double angulo) {
		/** Declarar o angulo para o mecanismo realizar numa potencia **/
		setPot(pot);
		setAngulo(angulo);
		sensor.giro.reset();
		sensor.pid.disable();
		sensor.pid.enable();
		sensor.pid.setOutputRange(-this.pot, this.pot);
		sensor.setSetpoint(this.angulo);
		while (Math.abs(sensor.saidaPID) > 0.1) {
			motor1.set(sensor.saidaPID);
		}
		motor1.set(0);
	}

	public void mover(PWMSpeedController atuador, double angulo) {
		/** Declarar o angulo para o mecanismo */
		setarAtuador(atuador);
		setAngulo(angulo);
		sensor.pid.disable();
		sensor.giro.reset();
		sensor.setSetpoint(angulo);
		sensor.setPID(0.1, 0, 0.1);// valores imaginarios obviamente
		sensor.pid.enable();
		while (Math.abs(sensor.pid.get()) > 0.1) {
			m.m1.set(sensor.saidaPID);
		}
		m.m1.set(0);

	}

	public void mover(PWMSpeedController atuador, double vel, double angulo) {
		/**
		 * Declarar o angulo para o mecanismo realizar numa velocidade com um atuador a
		 * escolha
		 **/
		setarAtuador(atuador);
		setVel(vel);
		setAngulo(angulo);
		sensor.pid.disable();
		sensor.pidA.disable();
		sensor.velocidade.enc.reset();
		sensor.giro.reset();
		sensor.setSetpoint(angulo);
		sensor.setSetpointA(vel);
		sensor.setPIDVel(0.1, 0, 0.1);
		sensor.setPID(0.1, 0, 0.1);// valores imaginarios obviamente
		sensor.pid.enable();
		sensor.pidA.enable();
		while (Math.abs(sensor.saidaPID) > 0.1 && Math.abs(sensor.saidaPIDA) > 0.1) {
			m.m1.set((sensor.saidaPID + sensor.saidaPIDA) / 2);
		}
		m.m1.set(0);
		sensor.pid.disable();
		sensor.pidA.disable();
	}

	public void mover(PWMSpeedController atuador, float pot, double angulo) {
		/**
		 * Declarar o angulo para o mecanismo realizar numa potencia void com um atuador
		 * a escolha
		 */
		setarAtuador(atuador);
		setPot(pot);
		setAngulo(angulo);
		sensor.pid.disable();
		sensor.giro.reset();
		sensor.pid.enable();
		sensor.pid.setOutputRange(-this.pot, this.pot);
		sensor.setSetpoint(this.angulo);
		while (Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set(sensor.saidaPID);
		}
		m.m1.set(0);
	}

	public void mover(PWMSpeedController atuador, double vel, double angulo, double tempo) {
		/**
		 * Declarar angulo para o mecanismo em uma velocidade com atuador declarado num
		 * determinado tempo
		 */
		setarAtuador(atuador);
		setVel(vel);
		setAngulo(angulo);
		sensor.pid.disable();
		sensor.pidA.disable();
		sensor.giro.reset();
		sensor.velocidade.enc.reset();
		m.m1.set(0);
		sensor.setSetpoint(this.angulo);
		sensor.setSetpointA(this.vel);
		sensor.setPID(0.1, 0.05, 0.1);
		sensor.setPIDVel(0.1, 0.05, 0.1);
		sensor.pidA.enable();
		sensor.pid.enable();
		time.reset();
		time.start();
		while (Math.abs(sensor.saidaPID) > 0.1 && Math.abs(sensor.saidaPIDA) > 0.1) {
			m.m1.set((sensor.saidaPID + sensor.saidaPIDA) / 2);
			if (time.get() >= this.tempo)
				break;
		}
		m.m1.set(0);
		sensor.velocidade.enc.reset();

	}

	public void mover(PWMSpeedController atuador, float pot, double angulo, double tempo) {
		/**
		 * Declarar angulo para o mecanismo em uma potencia void com atuador declarado
		 * num determinado tempo
		 */
		setarAtuador(atuador);
		sensor.pid.disable();
		sensor.pid.setOutputRange(-pot, pot);
		sensor.giro.reset();
		setAngulo(this.angulo);
		sensor.setSetpoint(this.angulo);
		time.reset();
		time.start();
		sensor.setPID(0.1, 0, 0.1);// valores imaginarios obviamente
		sensor.pid.enable();
		while (Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set(sensor.saidaPID);
			if (this.tempo == time.get())
				break;
		}
		m.m1.set(0);
		sensor.pid.disable();

	}

	public void mover(float dist, Double dppm) {
		/** Declarar distancia **/
		sensor.velocidade.enc.setDpp(dppm);
		sensor.velocidade.enc.reset();
		setDist(dist);
		while (this.dist <= sensor.velocidade.enc.getDistance()) {
			motor1.set(this.potencia);
			/** Dependendo nao eh necessario */
		}
		motor1.set(0);
		sensor.velocidade.enc.reset();

	}

	public void mover(double vel, float dist, Double dppm) {
		sensor.velocidade.enc.setDpp(dppm);
		/**
		 * Declarar Distancia e velocidade em metros por segundo
		 */
		sensor.velocidade.enc.reset();
		setVel(vel);
		setDist(dist);
		sensor.pidA.disable();
		motor1.set(0);
		sensor.setSetpointA(this.vel);
		sensor.pidA.setOutputRange(-this.potencia, this.potencia);
		sensor.setPIDVel(0.1, 0, 0.1);
		sensor.pidA.enable();
		while (Math.abs(sensor.saidaPIDA) != this.vel) {
			motor1.set(sensor.saidaPIDA);
			if (sensor.velocidade.enc.get() >= this.dist) {
				break;
			}
		}
		sensor.pidA.disable();
		motor1.set(0);
	}

	public void mover(float pot, float dist) {
		/**
		 * Declarar Distancia e potencia
		 */
		sensor.velocidade.enc.reset();
		setPot(pot);
		setDist(dist);
		while (this.dist <= sensor.velocidade.enc.getDistance()) {
			this.motor1.set(this.pot);
		}
		motor1.set(0);
		sensor.velocidade.enc.reset();

	}

	public void mover(PWMSpeedController atuador, float dist) {
		/**
		 * Declarar atuador e distancia, potencia aquela que declarou-se no construtor
		 */
		setarAtuador(atuador);
		setDist(dist);
		sensor.velocidade.enc.reset();
		while (sensor.velocidade.enc.get() <= this.dist) {
			m.m1.set(potencia);
		}
		m.m1.set(0);
		sensor.velocidade.enc.reset();

	}

	public void mover(PWMSpeedController atuador, double vel, float dist, Double dppm) {
		/**
		 * Declarar Atuador e distancia com uma determinada velocidade
		 */
		sensor.velocidade.enc.setDpp(dppm);
		setarAtuador(atuador);
		setVel(vel);
		setDist(dist);
		sensor.pidA.disable();
		sensor.pidA.setOutputRange(-this.potencia, this.potencia);
		sensor.setSetpointA(this.vel);
		sensor.velocidade.enc.reset();
		sensor.velocidade.timer.reset();
		sensor.pidA.enable();
		while (Math.abs(sensor.saidaPIDA) > 0.1) {
			m.m1.set(sensor.saidaPIDA);
			if (sensor.velocidade.enc.get() >= this.dist) {
				break;
			}
		}
		m.m1.set(0);
		sensor.pidA.disable();
	}

	public void mover(PWMSpeedController atuador, float pot, float dist, Double dppm) {
		/** Atuador distancia com potencia e declaracao de atuador */
		sensor.velocidade.enc.setDpp(dppm);
		sensor.velocidade.enc.reset();
		setarAtuador(atuador);
		setPot(pot);
		setDist(dist);
		
		while (sensor.velocidade.enc.getDistance() < this.dist) {
			m.m1.set(pot);

		}
		m.m1.set(0);
		sensor.velocidade.enc.reset();
	}

	public void mover(PWMSpeedController atuador, float pot, float dist, double tempo, Double dppm) {
		setarAtuador(atuador);
		setPot(pot);
		setDist(dist);
		setTempo(tempo);
		setVel(dist / tempo);
		sensor.velocidade.enc.setDpp(dppm);
		sensor.velocidade.enc.reset();
		time.reset();
		time.start();
		m.m1.set(0);
		while (this.dist <= sensor.velocidade.enc.get()) {
			m.m1.set(this.pot);
			if (this.tempo >= time.get()) {
				break;
			}
		}
		m.m1.set(0);
		sensor.velocidade.enc.reset();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, double angulo) {
		sensor.pid.disable();
		setarAtuador(atuador1, atuador2);
		sensor.giro.reset();
		setAngulo(this.angulo);
		sensor.setSetpoint(this.angulo);
		sensor.pid.setOutputRange(-potencia, potencia);
		sensor.setPID(0, 0.1, 0);// valores imaginarios obviamente
		sensor.pid.enable();
		while (Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set(sensor.saidaPID);
			m.m2.set(-sensor.saidaPID);
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.pid.disable();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, double vel, double angulo) {
		setarAtuador(atuador1, atuador2);
		setVel(vel);
		setAngulo(angulo);
		sensor.velocidade.enc.reset();
		sensor.velocidade.timer.reset();
		sensor.pid.disable();
		sensor.pidA.disable();
		sensor.giro.reset();
		m.m1.set(0);
		m.m2.set(0);
		sensor.setSetpoint(this.angulo);
		sensor.setSetpointA(this.vel);
		sensor.setPID(0.1, 0.05, 0.1);
		sensor.setPIDVel(0.1, 0, 0.1);
		sensor.pidA.enable();
		sensor.pid.enable();
		while (Math.abs(sensor.saidaPID) > 0.1 && Math.abs(sensor.saidaPIDA) > 0.1) {
			m.m1.set((sensor.saidaPID + sensor.saidaPIDA) / 2);
			m.m2.set((-sensor.saidaPID - sensor.saidaPIDA) / 2);
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.pid.disable();
		sensor.pidA.disable();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, float pot, double angulo) {
		setarAtuador(atuador1, atuador2);
		setPot(pot);
		setAngulo(angulo);
		sensor.giro.reset();
		sensor.pid.disable();
		sensor.pid.enable();
		sensor.pid.setOutputRange(-this.pot, this.pot);
		sensor.setSetpoint(this.angulo);
		while (Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set(sensor.saidaPID);
			m.m2.set(-sensor.saidaPID);
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.pid.disable();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, double angulo, double vel,
			double tempo) {
		setarAtuador(atuador1, atuador2);
		setAngulo(angulo);
		setVel(vel);
		setTempo(tempo);
		sensor.pid.disable();
		sensor.velocidade.enc.reset();
		sensor.velocidade.timer.reset();
		sensor.giro.reset();
		sensor.pid.setOutputRange(-potencia, potencia);
		sensor.setSetpoint(this.angulo);
		sensor.setSetpointA(this.vel);
		sensor.setPID(0.1, 0.0, 0.1);
		sensor.setPIDVel(0.1, 0.0, 0.1);
		sensor.velocidade.enc.reset();
		time.reset();
		time.start();
		while (sensor.velocidade.getVel() > this.vel || Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set((sensor.saidaPIDA + sensor.saidaPID) / 2);
			m.m2.set(-(sensor.saidaPIDA + sensor.saidaPID) / 2);

			if (this.tempo >= time.get()) {
				break;
			}
		}

		m.m1.set(0);
		m.m2.set(0);
		sensor.pidA.disable();
		sensor.pid.disable();
		time.reset();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, double angulo, float pot,
			double tempo) {
		sensor.pid.disable();
		sensor.giro.reset();
		setAngulo(this.angulo);
		sensor.setSetpoint(this.angulo);
		sensor.pid.setOutputRange(-this.pot, this.pot);
		time.reset();
		time.start();
		sensor.setPID(0.1, 0, 0.1);// valores imaginarios obviamente
		sensor.pid.enable();
		while (Math.abs(sensor.saidaPID) > 0.1) {
			m.m1.set(sensor.saidaPID);
			m.m2.set(-sensor.saidaPID);
			if (this.tempo == time.get())
				break;
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.pid.disable();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, double vel, float dist, Double dppm) {
		sensor.velocidade.enc.setDpp(dppm);
		setarAtuador(atuador1, atuador2);
		setVel(vel);
		sensor.velocidade.enc.reset();
		setDist(dist);
		sensor.pidA.disable();
		motor1.set(0);
		sensor.setSetpointA(this.vel);
		sensor.setPIDVel(0.1, 0, 0.1);
		sensor.pidA.enable();
		while (Math.abs(sensor.saidaPIDA) > 0.1) {
			m.m1.set(sensor.saidaPIDA);
			m.m2.set(sensor.saidaPIDA);
			if (this.dist == sensor.velocidade.enc.get()) {
				break;
			}
		}
		sensor.pidA.disable();
		m.m1.set(0);
		m.m1.set(0);
		sensor.velocidade.enc.reset();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, float pot, float dist, Double dppm) {
		setarAtuador(atuador1, atuador2);
		setPot(pot);
		setDist(dist);
		sensor.velocidade.enc.setDpp(dppm);
		sensor.velocidade.enc.reset();
		while (sensor.velocidade.enc.getDistance() < this.dist) {
			m.m1.set(pot);
			m.m2.set(pot);
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.velocidade.enc.reset();
	}

	public void mover(PWMSpeedController atuador1, PWMSpeedController atuador2, float dist, float pot, double tempo,
			Double dppm) {
		setarAtuador(atuador1, atuador2);
		setDist(dist);
		setPot(pot);
		setTempo(tempo);
		sensor.velocidade.enc.setDpp(dppm);
		sensor.velocidade.enc.reset();
		time.reset();
		time.start();
		while (sensor.velocidade.enc.getDistance() <= this.dist) {
			m.m1.set(pot);
			m.m2.set(pot);
			if (time.get() >= this.tempo)
				break;
		}
		m.m1.set(0);
		m.m2.set(0);
		sensor.velocidade.enc.reset();

	}

	public void setarAtuador(PWMSpeedController atuador) {

		m = new Atuador(atuador);
	}

	public void setarAtuador(PWMSpeedController atuador1, PWMSpeedController atuador2) {

		m = new Atuador(atuador1, atuador2);
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
		sensor.pid.setSetpoint(angulo);
	}

	public void setVel(double vel) {
		this.vel = vel;
	}

	public void setPot(float pot) {
		this.pot = pot;
		sensor.pid.setOutputRange(-this.pot, this.pot);
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

}
