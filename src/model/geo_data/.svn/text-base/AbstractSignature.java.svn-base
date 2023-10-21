package model.geo_data;

import java.awt.Color;

/**
 * allgemeine und abstrakte Klasse für die Signaturen
 */
public abstract class AbstractSignature {
	protected float witdh;
	protected float length;
	protected float z=0.0f;

	protected int shapeType;
	protected int brightness;
	protected float highlightSize=1.0f;
	protected float highlightBrightness=1.0f;
	
	public static final int BRIGHTNESS_BRIGHT=0;
	public static final int BRIGHTNESS_NORMAL=1;
	public static final int BRIGHTNESS_DARK=2;
	public static final int BRIGHTNESS_VERYDARK=3;
		
	private Color color;
	
	/**
	 * Breite der Signatur abrufen 
	 * @return Breite der Signatur
	 */
	public float getWitdh() {
		return witdh*highlightSize;
	}
	
	/**
	 * Breite der Signatur setzen
	 * @param witdh Breite der Signatur
	 */
	public void setWitdh(float witdh) {
		this.witdh = witdh;
	}
	
	/**
	 * Länge der Signatur abrufen 
	 * @return Länge der Signatur
	 */
	public float getLength() {
		return length*highlightSize;
	}
	
	/**
	 * Länge der Signatur setzen
	 * @param length Länge der Signatur
	 */
	public void setLength(float length) {
		this.length = length;
	}
	
	/**
	 * Farbe der Signatur setzen
	 * @param color Farbe der Signatur
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Farbe der Signatur auslesen
	 * @return Farbe der Signatur
	 */
	public Color getRealColor() {
		return color;
	}
	
	/**
	 * Farbe (verändert durch die Helligkeit) der Signatur auslesen
	 * @return
	 */
	public Color getColor() {
		if(this.highlightBrightness!=1.0f)
		{
			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
			float hue = hsb[0];          // .58333
			float saturation = hsb[1];   // .66667
			float brightness = hsb[2];   // .6
			float addBrightness=this.highlightBrightness-1.0f;
			if(addBrightness>0.5f)
				addBrightness=1.0f-addBrightness;
			else if(addBrightness<-0.5f)
				addBrightness=(1.0f+addBrightness)*(-1);
			brightness+=addBrightness;
			if(brightness>1.0f)
				brightness=1.0f-(brightness-1.0f);
			if(brightness<0.0f)
				brightness=0.0f;
				
			int rgb = Color.HSBtoRGB(hue, saturation, brightness);
			int red = (rgb>>16)&0xFF;
			int green = (rgb>>8)&0xFF;
			int blue = rgb&0xFF;
			return new Color(red,green,blue);

		}
		return color;
	}
	
	/**
	 * Typ Form abrufen
	 * @return Typ Form
	 */
	public int getShapeType() {
		return shapeType;
	}
	
	/**
	 * Typ Form setzen
	 * @param shapeType Typ Form
	 */
	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}	
	
	/**
	 * Helligkeit auslesen 
	 * @return Helligkeit
	 */
	public int getBrightness() {
		return brightness;
	}
	
	/**
	 * Helligkeit setzen 
	 * @param brightness Helligkeit
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	/**
	 * Grösse für den Highlight auslesen
	 * @return Grösse für den Highlight
	 */
	public float getHighlightSize() {
		return highlightSize;
	}
	
	/**
	 * Grösse für den Highlight auslesen
	 * @param highlightSize Grösse für den Highlight
	 */
	public void setHighlightSize(float highlightSize) {
		this.highlightSize = highlightSize;
	}
	
	/**
	 * Helligkeit für den Highlight auslesen
	 * @return
	 */
	public float getHighlightBrightness() {
		return highlightBrightness;
	}

	/**
	 * Helligkeit für den Highlight
	 * @param highlightBrightness Helligkeit für den Highlight setzen
	 */
	public void setHighlightBrightness(float highlightBrightness) {
		this.highlightBrightness = highlightBrightness;
	}
	
	/**
	 * Z Position auslesen
	 * @return Z Position
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Z Position setzen
	 * @param z Z Position
	 */
	public void setZ(float z) {
		this.z = z;
	}	

}
