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
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends IterativeRobot {
	Controle control;
	int counter = 0;
	Integer[] vars = new Integer[5];
	private PWMSpeedController tracao1,tracao2,tracao3,tracao4, subidaEsq, subidaDir ,roleteEsq, roleteDir;
	private DigitalInput limit;
	private Encoder encoderSubida, encoderTracao;
	private ADXRS450_Gyro gyroscope;
	boolean finalComandAndar, finalComandVirar, gambi, entregar = true;
	private double subida;
	@Override
	public void robotInit() {
		tracao1 = new Spark(0);
		tracao2 = new Spark(1);
		tracao3 = new Spark(2);
		tracao4 = new Spark(3);
		subidaEsq = new Spark(4);
		subidaDir = new Spark(5);
		roleteDir = new VictorSP(7);
		roleteEsq = new Jaguar(8);
		limit = new DigitalInput(7);
		encoderSubida = new Encoder(0,1);
		encoderTracao = new Encoder(2,3);
		gyroscope = new ADXRS450_Gyro();
		control = new Controle(tracao1,tracao2,tracao3,tracao4,limit,subidaEsq,subidaDir,roleteEsq,roleteDir);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	Movimentar mov;
	@Override
	public void autonomousInit() {
		mov = new Movimentar(encoderTracao, encoderSubida, gyroscope, tracao1, tracao2, tracao3, tracao4, subidaEsq, subidaDir, roleteEsq, roleteDir);
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
						encoderTracao.reset();
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
			if(counter == (vars.length + 1)) {
				if (mov.elevateClaw(2)) {
					counter++;
				}
			}else {
				if (mov.dropCube(subida)) {
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
		if(mov != null) {
			mov.free();
			mov= null;
		}
		
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		control.botoesJoystick2Subida();
		control.botoesJoystick2GarraBorda();
		control.botoesJoystick2GarraManual();
		control.tracao();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}