/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends IterativeRobot {
	Controle control;
	int counter = 0;
	Integer[] vars = new Integer[5];
	boolean finalComandAndar, finalComandVirar, gambi, entregar = true;
	@Override
	public void robotInit() {
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	Encoder enc;
	ADXRS450_Gyro giro;
	PWMSpeedController motor0, motor1, motor2, motor3, motor4, motor5, motor6;
	protected DigitalInput limit1, limit2;
	Movimentar mov;
	@Override
	public void autonomousInit() {
		enc = new Encoder(1, 0, true, EncodingType.k4X);
		giro = new ADXRS450_Gyro();
		motor0 = new Spark(0);
		motor1 = new Spark(1);
		motor2 = new Spark(2);
		motor3 = new Spark(3);
		motor4 = new Spark(4);
		motor5 = new Spark(5);
		motor6 = new VictorSP(6);
		mov = new Movimentar(enc, giro, motor0, motor1, motor2, motor3, motor4, motor5, motor6, limit1, limit2);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (counter < vars.length) {
			if ((counter & 1) == 0) {
				if (!finalComandAndar) {
					if (gambi)
						enc.reset();
					finalComandAndar = mov.walkStraight(vars[counter]);
					gambi = false;
				} else {
					counter++;
					gambi = true;
					finalComandAndar = false;
				}

			} else {
				if (!finalComandVirar) {
					finalComandVirar = mov.turn(vars[counter]);
				} else {
					mov.parar();
					counter++;
					finalComandVirar = false;
				}
			}
		} else if(counter == vars.length) {
			mov.parar();
			counter++;
		} else if (entregar) {
			if(counter == vars.length + 1) {
				if (mov.elevateClaw(1.5)) {
					counter++;
				}
			}else {
				if (mov.dropCube()) {
					counter++;
				}
			}	
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		if(mov != null) mov.free();
		control = new Controle();
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		control.botaoLTeRT();
		control.botaoLBeRB();
		control.tracao();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
