package com.jackson.hsexpo.pi.controller;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Defines constant pins used for application.<br>
 * Raspberry Pi pins are numbered as such:<br><br>
 * <code>
 * ---- 1   2 ----<br>
 * ---- 3   4 ----<br>
 * ---- 5   6 ----<br>
 * ---- 7   8 ----<br>
 * ---- 9   10 ---<br>
 * --- 11   12 ---<br>
 * --- 13   14 ---<br>
 * --- 15   16 ---<br>
 * --- 17   18 ---<br>
 * --- 19   20 ---<br>
 * --- 21   22 ---<br>
 * --- 23   24 ---<br>
 * --- 25   26 ---<br>
 * --- 27   28 ---<br>
 * --- 29   30 ---<br>
 * --- 31   32 ---<br>
 * --- 33   34 ---<br>
 * --- 35   36 ---<br>
 * --- 37   38 ---<br>
 * --- 39   40 ---<br>
 * </code>
 * 
 * @author Ampp33
 *
 */
public interface RubeGoldbergConstants {
	
	/** Button pin, pin 11 **/
	public static final Pin BUTTON_PIN = RaspiPin.GPIO_00;
	
	/** Phototransistor state sensor pin, pin 12 **/
	public static final Pin GATE_1_PIN = RaspiPin.GPIO_01;
	
	/** Phototransistor state sensor pin, pin 13 **/
	public static final Pin GATE_2_PIN = RaspiPin.GPIO_02;
	
	/** Phototransistor state sensor pin, pin 15 **/
	public static final Pin GATE_3_PIN = RaspiPin.GPIO_03;
	
	/** Phototransistor state sensor pin, pin 16 **/
	public static final Pin GATE_4_PIN = RaspiPin.GPIO_04;
	
}
