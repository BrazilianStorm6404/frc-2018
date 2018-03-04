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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends IterativeRobot {
	Controle control;
	int counter = 0;
	Integer[] vars = new Integer[5];
	String gameData;
	private PWMSpeedController tracao1,tracao2,tracao3,tracao4, subidaEsq, subidaDir ,roleteEsq, roleteDir;
	private DigitalInput limit;
	private Encoder encoderSubida, encoderTracao;
	private ADXRS450_Gyro gyroscope;
	SendableChooser<Integer> posicao;
	SendableChooser<String> prioridade;
	SendableChooser<Boolean> colisao;
	String[] gameMode;
	boolean finalComandAndar, finalComandVirar, gambi, entregar = true, andandoAinda = true, reset = true;;
	private double subida;
	int contador = 0;
	int altura;
	Integer variaveisAndar[] = new Integer[10];
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
		posicao = new SendableChooser<>();
		prioridade = new SendableChooser<>();
		colisao = new SendableChooser<>();
		
		posicao.addDefault("Esquerda", 1);
		posicao.addObject("Centro", 2);
		posicao.addObject("Direita", 3);

		prioridade.addDefault("Switch", "sw");
		prioridade.addObject("Scale", "sc");

		colisao.addDefault("Sim", true);
		colisao.addObject("Nao", false);
		SmartDashboard.putData("Position", posicao);
		SmartDashboard.putData("Priority", prioridade);
		SmartDashboard.putData("Colision", colisao);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	Movimentar mov;
	@Override
	public void autonomousInit() {
		mov = new Movimentar(encoderTracao, encoderSubida, gyroscope, tracao1, tracao2, tracao3, tracao4, subidaEsq, subidaDir, roleteEsq, roleteDir);
		variaveisAndar[0] = null;
		variaveisAndar[1] = null;
		variaveisAndar[2] = null;
		variaveisAndar[3] = null;
		variaveisAndar[4] = null;
		variaveisAndar[5] = null;
		variaveisAndar[6] = null;
		variaveisAndar[7] = null;
		variaveisAndar[8] = null;
		variaveisAndar[9] = null;
		altura = 50;
		gyroscope.reset();
		encoderTracao.reset();
		gameMode[0] = posicao.getSelected().toString();
		gameMode[1] = prioridade.getSelected();
		gameMode[2] = colisao.getSelected().toString();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		/*SmartDashboard.putString("1", gameMode[0]);
		SmartDashboard.putString("2", gameMode[1]);
		SmartDashboard.putString("3", gameMode[2]);
		SmartDashboard.putString("gd", gameData); 
		SmartDashboard.putStringArray("Value", gameMode);*/
		if ("1".equals(gameMode[0])) {
			if ("sc".equals(gameMode[1]) && 'L' == gameData.charAt(1)) {
				// reto/vira/reto/vira/reto/vira/reto/coloca (Unico caminho da Scale)
				variaveisAndar[0] = 780;
				variaveisAndar[1] = 90;
				variaveisAndar[2] = 111;
				altura = 180;
			} else if (gameData.charAt(0) == 'L') {
				// reto/vira/reto/coloca(caminho mais simples sem desviar na Switch)
				variaveisAndar[0] = 384;
				variaveisAndar[1] = 90;
				variaveisAndar[2] = 102;
			}
			else if (gameData.charAt(0) == 'R') {
				// reto/vira/reto/coloca(caminho mais simples sem desviar na Switch)
				variaveisAndar[0] = 177;
				variaveisAndar[1] = 90;
				variaveisAndar[2] = 584;
				variaveisAndar[3] = 0;
				variaveisAndar[4] = 248;
				variaveisAndar[5] = -90;
				variaveisAndar[6] = 16;				
			}
			else if (!Boolean.valueOf(gameMode[2])) {
				// reto/vira/reto/vira/reto/vira/reto/Coloca(caminho mais longo sem desvio na
				// Switch)
				variaveisAndar[0] = 177;
				variaveisAndar[1] = 90;
				variaveisAndar[2] = 584;
				variaveisAndar[3] = 0;
				variaveisAndar[4] = 248;
				variaveisAndar[5] = -90;
				variaveisAndar[6] = 16;
			} else if (Boolean.valueOf(gameMode[2])) {
				// reto/vira/reto/vira/reto/colca(caminho mais longo com desvio na Switch)
				variaveisAndar[0] = 539;
				variaveisAndar[1] = 90;
				variaveisAndar[2] = 584;
				variaveisAndar[3] = 180;
				variaveisAndar[4] = 154;
				variaveisAndar[5] = 270;
				variaveisAndar[6] = 16;
			}
		} else if ("2".equals(gameMode[0])) {
			if ("sw".equals(gameMode[1])) {
				if (gameData.charAt(0) == 'L') {
					variaveisAndar[0] = 177;
					variaveisAndar[1] = -90;
					variaveisAndar[2] = 225;
					variaveisAndar[3] = 0;
					variaveisAndar[4] = 207;
					variaveisAndar[5] = 90;
					variaveisAndar[6] = 16;
				} else if (gameData.charAt(0) == 'R') {
					variaveisAndar[0] = 177;
					variaveisAndar[1] = 90;
					variaveisAndar[2] = 170;
					variaveisAndar[3] = 0;
					variaveisAndar[4] = 207;
					variaveisAndar[5] = -90;
					variaveisAndar[6] = 16;
				}
			}
		} else if ("3".equals(gameMode[0])) {
			if ("sc".equals(gameMode[1]) && 'R' == gameData.charAt(1)) {
				// reto/vira/reto/vira/reto/vira/reto/coloca (Unico caminho da Scale)
				variaveisAndar[0] = 780;
				variaveisAndar[1] = -90;
				variaveisAndar[2] = 111;
			} else if (gameData.charAt(0) == 'R') {
				// reto/vira/reto/coloca(caminho mais simples sem desviar na Switch)
				variaveisAndar[0] = 384;
				variaveisAndar[1] = -90;
				variaveisAndar[2] = 102;
			} else if (!Boolean.valueOf(gameMode[2])) {
				// reto/vira/reto/vira/reto/vira/reto/Coloca(caminho mais longo sem desvio na
				// Switch)
				variaveisAndar[0] = 177;
				variaveisAndar[1] = -90;
				variaveisAndar[2] = 584;
				variaveisAndar[3] = 0;
				variaveisAndar[4] = 248;
				variaveisAndar[5] = 90;
				variaveisAndar[6] = 16;
			} else if (Boolean.valueOf(gameMode[2])) {
				// reto/vira/reto/vira/reto/colca(caminho mais longo com desvio na Switch)
				variaveisAndar[0] = 539;
				variaveisAndar[1] = -90;
				variaveisAndar[2] = 584;
				variaveisAndar[3] = -180;
				variaveisAndar[4] = 154;
				variaveisAndar[5] = -270;
				variaveisAndar[6] = 16;
			}

		}

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (reset)
			encoderTracao.reset();
		andandoAinda = contador < variaveisAndar.length && variaveisAndar[contador] != null;
		if (andandoAinda) {
			if ((contador & 1) == 0) {
				if (!finalComandAndar) {
					finalComandAndar = mov.walkStraight(variaveisAndar[contador]);
					reset = false;
				} else {
					mov.parar();
					contador++;
					reset = true;
					finalComandAndar = false;
				}
			} else {
				if (!finalComandVirar) {
					finalComandVirar = mov.turn(variaveisAndar[contador]);
				} else {
					mov.parar();
					contador++;
					finalComandVirar = false;
				}
			}
		} else if (contador == variaveisAndar.length){
			if (mov.elevateClaw(altura)) {
				contador++;
			}
		} else if (contador == (variaveisAndar.length + 1)){
			if (mov.dropCube(2)) {
				contador++;
			}
		}

	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	public void configEncoders() {
		encoderTracao.setMaxPeriod(360/512);
		encoderTracao.setMinRate(10);
		encoderTracao.setDistancePerPulse((Math.PI * 6 * 2.54)/512);
		encoderSubida.setMaxPeriod(360/512);
		encoderSubida.setMinRate(10);
		encoderSubida.setDistancePerPulse(1);
	}
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