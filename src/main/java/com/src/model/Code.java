package com.src.model;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;

public class Code {
	private String brand;
	private String model;
	private String description;
	private String manufacturerName;
	private String manufacturerLocation;
	private String qr;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public Code(String brand, String model, String description, String manufacturerName, String manufacturerLocation) {
		super();
		this.brand = brand;
		this.model = model;
		this.description = description;
		this.manufacturerName = manufacturerName;
		this.manufacturerLocation = manufacturerLocation;
		this.qr =codeGenerator();
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getManufacturerLocation() {
		return manufacturerLocation;
	}
	public String getQr() {
		return qr;
	}
	public void setManufacturerLocation(String manufacturerLocation) {
		this.manufacturerLocation = manufacturerLocation;
	}
		public static String codeGenerator(){
			Random rd = new Random();
			Long longval =rd.nextLong(100000000,999999999);
			Encoder enc = Base64.getEncoder();
			String S= enc.encodeToString(longval.toString().getBytes());
			return S;
		}
}
