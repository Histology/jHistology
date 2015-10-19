package org.yuzhakov.histology;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import de.jreality.shader.Color;

public class Util {
	private static final Random RANDOM = new Random();
	
	public static double round(double d){
		BigDecimal decimal = new BigDecimal(d);
		return decimal.setScale(Settings.DEFAULT_PRECISION_SCALE, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static Color getRandomColor(){
		return new Color(
				RANDOM.nextInt(255),
				RANDOM.nextInt(255),
				RANDOM.nextInt(255));
	}
	
	
}
