package org.usfirst.frc.team6404.robot;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderBuilder extends Encoder {
	public static double period, dppm;
	public static int rate;
	 {
		period  = 360/512;
		rate = 10;
		dppm = (Math.PI*6*2.54)/512;
	}

	public EncoderBuilder(int channelA, int channelB, boolean invertido, EncodingType encType) {
		super(channelA, channelB, invertido, encType);
		setMaxPeriod(period);
		setMinRate(rate);
		setDistancePerPulse(dppm);
	}

	public EncoderBuilder(int channelA, int channelB) {
		this(channelA, channelB, false, EncodingType.k4X);
	}
	public void setDpp(double dpp) {
		setDistancePerPulse(dpp);
	}
}
