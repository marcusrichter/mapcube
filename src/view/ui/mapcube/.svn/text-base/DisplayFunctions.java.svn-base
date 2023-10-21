package view.ui.mapcube;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import model.geo_data.AbstractSignature;
import model.geo_data.GeoObject;

/**
 * Klasse mit Funktionen, die die Anzeige betreffen
 *
 */
public class DisplayFunctions {
	
    public static final String INFO_TEXT_OPTIONS_SELECTION="###SELECTION###";
    public static final String INFO_TEXT_OPTIONS_CONFIRM="###CONFIRM###";
    public static final String INFO_LINE_CENTER="###INFO_LINE_CENTER###";
    public static final int INFO_TEXT_BUTTON_OK=0;
    public static final int INFO_TEXT_BUTTON_SEL_FUELSTATION=1;
    public static final int INFO_TEXT_BUTTON_SEL_RESTAURANT=2;
    public static final int INFO_TEXT_BUTTON_SEL_PERSON=3;
    public static final int INFO_TEXT_BUTTON_CONFIRM_YES=4;
    public static final int INFO_TEXT_BUTTON_CONFIRM_NO=5;
    public static final int LINKINGBRUSHING_FUELSTATION=1;
    public static final int LINKINGBRUSHING_PERSON=2;
    public static final int LINKINGBRUSHING_RESTAURANT=3;
    public static final int LINKINGBRUSHING_HOTEL=4;
    public static final int LINKINGBRUSHING_START=5;
    
    private static Texture perceptionTexture;
	
    
    /**
     * Anzeige der Entfernung zum Geoobjekt
     * @param drawable drawable OpenGL 
     */
    public static void distanceShow(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glTranslatef(-0.8f, 1.3f, 0.01f);
		DisplayFunctions.colorToGlColor(gl, Color.CYAN,-1);
		
		float vertices[] = {-0.054f, 0.0f, 0.0f,
				0.054f, 0.0f, 0.0f,
				-0.054f, -0.01f, 0.0f,
				-0.054f, 0.01f, 0.0f,
				0.054f, -0.01f, 0.0f,
				0.054f, 0.01f, 0.0f

		};

		DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINES, null);
		gl.glTranslatef(0.0f, -0.1f, 0.01f);
		FontDrawer.scale=0.0004f;
		FontDrawer.render(GLUT.STROKE_ROMAN, "0.1 km", gl, true);
							
		gl.glPopMatrix();
	}
    
    /**
     * Pfeile für Wahrnehmug der Übergänge zwischen Fokus- und Kontext-Region anzeigen 
     * @param drawable drawable OpenGL
     * @param blendeds nur transparente Teilstücke zeichnen
     * @param blend Transparenz aktivieren
     * @param length Länge des Pfeil für die Animation
     */
	public static void barsPerceptionCues(GLAutoDrawable drawable,boolean blendeds,float blend,float length)
	{
		GL gl = drawable.getGL();
		
		float maxflength=0.3f;			
		float flength=0.3f;
		if(length<flength)
			flength=length;
		float blength=length-flength;
		if(blength<0.0f)
			blength=0.0f;
		float distarrow=length-0.1f;		
			
		if(blendeds)
		{			
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		}
		else
			gl.glDisable(GL.GL_BLEND);
		
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE);
		
		boolean inverse=false;
		
		//unten links
		if(blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(-0.5f, -1.2f, 0.000001f);
			inverse=barPerceptionCues(drawable,false,blend,flength);
			gl.glTranslatef(0.0f, (-1.0f)*flength, 0.0f);
			if(flength<maxflength)
				drawArrowHead(drawable,blend);
			gl.glPopMatrix();
		}
		
		if(!blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(-0.5f, -1.499f, 0.000001f);
			gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			barPerceptionCues(drawable,inverse,1.0f,blength);
			gl.glTranslatef(0.0f, 0.2f-distarrow, 0.0f);
			drawArrowHead(drawable,1.0f);	
			gl.glPopMatrix();
		}
		
		inverse=false;
		//links oben
		if(blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(-0.7f, 1.1f, 0.00001f);
			gl.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			inverse=barPerceptionCues(drawable,false,blend,flength);
			gl.glTranslatef(0.0f, (-1.0f)*flength, 0.0f);
			if(flength<maxflength)
				drawArrowHead(drawable,blend);			
			gl.glPopMatrix();
		}

		if(!blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(-0.999f, 1.0f, 0.01f);		
			gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			barPerceptionCues(drawable,inverse,1.0f,blength);
			gl.glTranslatef(0.0f, 0.2f-distarrow, 0.0f);
			drawArrowHead(drawable,1.0f);			
			gl.glPopMatrix();
		}

		inverse=false;
		//oben rechts
		if(blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(0.5f, 1.2f, 0.000001f);
			gl.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
			inverse=barPerceptionCues(drawable,false,blend,flength);
			gl.glTranslatef(0.0f, (-1.0f)*flength, 0.0f);
			if(flength<maxflength)
				drawArrowHead(drawable,blend);						
			gl.glPopMatrix();
		}
		
		if(!blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(0.4f, 1.4995f, 0.00001f);		
			//gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			barPerceptionCues(drawable,inverse,1.0f,blength);
			gl.glTranslatef(0.0f, 0.2f-distarrow, 0.0f);
			drawArrowHead(drawable,1.0f);
			gl.glPopMatrix();
		}
		
		inverse=false;
		//rechts unten
		if(blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(0.7f, -1.1f, 0.000001f);
			//gl.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			inverse=barPerceptionCues(drawable,false,blend,flength);			
			gl.glTranslatef(0.0f, -flength, 0.0f);
			if(flength<maxflength)
				drawArrowHead(drawable,blend);
			gl.glPopMatrix();
		}
		
		if(!blendeds)
		{
			gl.glPushMatrix();		
			gl.glTranslatef(0.999f, -1.0f, 0.000001f);		
			//gl.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			gl.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			barPerceptionCues(drawable,inverse,1.0f,blength);
			gl.glTranslatef(0.0f, 0.2f-distarrow, 0.0f);
			drawArrowHead(drawable,1.0f);
			gl.glPopMatrix();
		}

	}
	
	/**
	 * den Pfeilbalken zeichnen 
	 * @param drawable drawable OpenGL
	 * @param inverse
	 * @param blend Transparenz aktivieren
	 * @param length Länge des Pfeil für die Animation
	 * @return
	 */
	private static boolean barPerceptionCues(GLAutoDrawable drawable,boolean inverse,float blend,float length)
	{
		GL gl = drawable.getGL();
			
		for(float i=0;i<length;i+=0.1f)
		{
			float clenght=0.1f;
			if(length-(i+0.1f)<=0.1f)
				clenght=length-i;
				
			if (inverse==false)
			{
				gl.glColor4f(0.7f,0.0f,0.3f,blend);
				inverse=true;
			}
			else
			{
				gl.glColor4f(0.9f,0.0f,0.3f,blend);
				inverse=false;
			}
				
			gl.glBegin(GL.GL_POLYGON);
			gl.glVertex3f(0.0f, 0.0f-(i), 0.0f);
			gl.glVertex3f(0.1f, 0.0f-(i), 0.0f);
			gl.glVertex3f(0.1f, (-1.0f*clenght)-(i), 0.0f);
			gl.glVertex3f(0.0f, (-1.0f*clenght)-(i), 0.0f);
			gl.glEnd();
			
		}
		return inverse;
	}
	
	/**
	 * Pfeilspitze zeichnen	
	 * @param drawable drawable OpenGL
	 * @param blend Transparenz aktivieren
	 */
	private static void drawArrowHead(GLAutoDrawable drawable,float blend)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);		
		
		gl.glColor4f(0.9f,0.0f,0.3f,blend);
	  	    
		
		float vertices[] = {-0.1f, 0.0f, 0.0f,
							0.2f, 0.0f, 0.0f,
							0.05f, -0.2f, 0.0f,							
							};
		int indicies[]={0,1,2,0};
		
		FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
		buf_vertices.put(vertices);
		
		IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
		buf_indicies.put(indicies);
				
		// activate and specify pointer to vertex array
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);		
		buf_vertices.rewind();
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
		buf_indicies.rewind();
		gl.glDrawElements(GL.GL_POLYGON, 4, GL.GL_UNSIGNED_INT, buf_indicies);
		// deactivate vertex arrays after drawing
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
	}
	
	/**
	 * Infotext anzeigen
	 * @param text Text
	 * @param drawable drawable OpenGL
	 */
	public static void showInfoLine(String text,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		boolean center=false;
		if(text.indexOf(DisplayFunctions.INFO_LINE_CENTER)!=-1)
		{
			text=text.replace(DisplayFunctions.INFO_LINE_CENTER, "");
			center=true;
		}
		
		if(center)
			gl.glTranslatef(0.0f, 0.0f, 0.1f);
		else			
			gl.glTranslatef(0.0f, 1.3f, 0.1f);
		gl.glColor3f(1.0f,1.0f,1.0f);
		String[] splittext=text.split("\n");
		for(String split:splittext)
		{
			FontDrawer.render(GLUT.STROKE_ROMAN, split, gl, true);
			gl.glTranslatef(0.0f, -0.1f, 0.0f);
		}
		gl.glPopMatrix();
		
		gl.glEnable(GL.GL_LIGHTING);
	}
	
	/**
	 * Infotext mit Box und Buttons anzeigen
	 * @param text Text 
	 * @param drawable drawable OpenGL
	 */
	public static void showInfoText(String text,GLAutoDrawable drawable)
	{		
		ArrayList<String> options=new ArrayList<String>();
		if(text.indexOf(DisplayFunctions.INFO_TEXT_OPTIONS_SELECTION)!=-1)
		{
			options.add(DisplayFunctions.INFO_TEXT_OPTIONS_SELECTION);
			text=text.replace(DisplayFunctions.INFO_TEXT_OPTIONS_SELECTION, "");
		}
		if(text.indexOf(DisplayFunctions.INFO_TEXT_OPTIONS_CONFIRM)!=-1)
		{
			options.add(DisplayFunctions.INFO_TEXT_OPTIONS_CONFIRM);
			text=text.replace(DisplayFunctions.INFO_TEXT_OPTIONS_CONFIRM, "");
		}	
			
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
			
		gl.glTranslatef(0.0f, 0.0f, 0.1f);
		gl.glColor3f(0.2f,0.2f,0.2f);
		
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f(-0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.8f, -1.3f, 0.0f);
		gl.glVertex3f(-0.8f, -1.3f, 0.0f);
		gl.glEnd();
		
		gl.glTranslatef(0.0f, 0.0f, 0.0001f);
		gl.glLineWidth(4.0f);
		
		gl.glBegin(GL.GL_LINE_LOOP);		
		gl.glColor3f(1.0f,1.0f,1.0f);
		gl.glVertex3f(-0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.8f, -1.3f, 0.0f);
		gl.glVertex3f(-0.8f, -1.3f, 0.0f);
		gl.glEnd();

		String[] splittext=text.split("\n");
		
		gl.glPushMatrix();
		gl.glTranslatef(-0.7f, 1.1f, 0.0f);
		gl.glScalef(1.3f,1.3f,0.0f);
		for(String textLine:splittext)
		{
			gl.glColor3f(1.0f,1.0f,1.0f);			
			FontDrawer.render(GLUT.STROKE_ROMAN, textLine, gl, false);
			gl.glTranslatef(0.0f, -0.1f, 0.0f);
		}
		gl.glPopMatrix();
		
		gl.glTranslatef(0.0f, 0.0f, 0.0001f);
		gl.glLineWidth(4.0f);
		
		if(options.size()==0)
		{
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(0.4f, -1.0f, 0.0f);
			gl.glVertex3f(0.7f, -1.0f, 0.0f);
			gl.glVertex3f(0.7f, -1.2f, 0.0f);
			gl.glVertex3f(0.4f, -1.2f, 0.0f);
			gl.glEnd();

			gl.glPushMatrix();
			gl.glTranslatef(0.55f, -1.125f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "OK", gl, true);
			gl.glPopMatrix();
		}
		
		if(options.contains(DisplayFunctions.INFO_TEXT_OPTIONS_CONFIRM))
		{
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(0.4f, -1.0f, 0.0f);
			gl.glVertex3f(0.7f, -1.0f, 0.0f);
			gl.glVertex3f(0.7f, -1.2f, 0.0f);
			gl.glVertex3f(0.4f, -1.2f, 0.0f);
			gl.glEnd();

			gl.glPushMatrix();
			gl.glTranslatef(0.55f, -1.125f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "Nein", gl, true);
			gl.glPopMatrix();
			
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(0.0f, -1.0f, 0.0f);
			gl.glVertex3f(0.3f, -1.0f, 0.0f);
			gl.glVertex3f(0.3f, -1.2f, 0.0f);
			gl.glVertex3f(0.0f, -1.2f, 0.0f);
			gl.glEnd();

			gl.glPushMatrix();
			gl.glTranslatef(0.15f, -1.125f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "Ja", gl, true);
			gl.glPopMatrix();
		}
		
		if(options.contains(DisplayFunctions.INFO_TEXT_OPTIONS_SELECTION))
		{
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(-0.4f, -0.4f, 0.0f);
			gl.glVertex3f(0.4f, -0.4f, 0.0f);
			gl.glVertex3f(0.4f, -0.6f, 0.0f);
			gl.glVertex3f(-0.4f, -0.6f, 0.0f);
			gl.glEnd();
			
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, -0.5f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "Tankstellen", gl, true);
			gl.glPopMatrix();
						
			
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(-0.4f, -0.7f, 0.0f);
			gl.glVertex3f(0.4f, -0.7f, 0.0f);
			gl.glVertex3f(0.4f, -0.9f, 0.0f);
			gl.glVertex3f(-0.4f, -0.9f, 0.0f);
			gl.glEnd();
			
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, -0.8f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "Restaurants", gl, true);
			gl.glPopMatrix();
			
			
			gl.glBegin(GL.GL_LINE_LOOP);		
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glVertex3f(-0.4f, -1.0f, 0.0f);
			gl.glVertex3f(0.4f, -1.0f, 0.0f);
			gl.glVertex3f(0.4f, -1.2f, 0.0f);
			gl.glVertex3f(-0.4f, -1.2f, 0.0f);
			gl.glEnd();
			
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, -1.1f, 0.0001f);
			FontDrawer.render(GLUT.STROKE_ROMAN, "Personen", gl, true);
			gl.glPopMatrix();
		}
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPopMatrix();
					
	}
	
	/**
	 * Linking und Brushing Button anklicken
	 * @param type Typ des Buttons
	 * @param drawable drawable OpenGL 
	 */
	public static void showLinkAndBrushingSymbol(int type,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
					
		gl.glTranslatef(0.0f, 0.0f, 0.05f);
		gl.glLineWidth(4.0f);
				
		if(type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT
			|| 	type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION+1000 || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT+1000
			|| 	type==DisplayFunctions.LINKINGBRUSHING_HOTEL || type==DisplayFunctions.LINKINGBRUSHING_HOTEL+1000		
		)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_VERYDARK);
		if(type==DisplayFunctions.LINKINGBRUSHING_PERSON || type==DisplayFunctions.LINKINGBRUSHING_PERSON+1000)
			colorToGlColor(gl, Color.RED,AbstractSignature.BRIGHTNESS_VERYDARK);

		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(0.6f, 1.2f, 0.0f);
		gl.glVertex3f(0.8f, 1.2f, 0.0f);
		gl.glVertex3f(0.8f, 1.4f, 0.0f);
		gl.glVertex3f(0.6f, 1.4f, 0.0f);
		gl.glEnd();
		
		if(type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT || type==DisplayFunctions.LINKINGBRUSHING_HOTEL || type==DisplayFunctions.LINKINGBRUSHING_START )
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_NORMAL);
		if(type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION+1000 || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT+1000 || type==DisplayFunctions.LINKINGBRUSHING_HOTEL+1000 || type==DisplayFunctions.LINKINGBRUSHING_START+1000)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_DARK);
		if(type==DisplayFunctions.LINKINGBRUSHING_PERSON)
			colorToGlColor(gl, Color.RED,AbstractSignature.BRIGHTNESS_NORMAL);
		if(type==DisplayFunctions.LINKINGBRUSHING_PERSON+1000)
			colorToGlColor(gl, Color.RED,AbstractSignature.BRIGHTNESS_DARK);
		
		gl.glTranslatef(0.0f, 0.0f, 0.0001f);
		gl.glBegin(GL.GL_POLYGON);				
		gl.glVertex3f(0.6f, 1.2f, 0.0f);
		gl.glVertex3f(0.8f, 1.2f, 0.0f);
		gl.glVertex3f(0.8f, 1.4f, 0.0f);
		gl.glVertex3f(0.6f, 1.4f, 0.0f);
		gl.glEnd();		

		if(type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT
		   || type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION+1000 || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT+1000
		   || type==DisplayFunctions.LINKINGBRUSHING_HOTEL || type==DisplayFunctions.LINKINGBRUSHING_HOTEL+1000
		   || type==DisplayFunctions.LINKINGBRUSHING_START || type==DisplayFunctions.LINKINGBRUSHING_START+1000
		)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_VERYDARK);
		if(type==DisplayFunctions.LINKINGBRUSHING_PERSON || type==DisplayFunctions.LINKINGBRUSHING_PERSON+1000)
			colorToGlColor(gl, Color.RED,AbstractSignature.BRIGHTNESS_VERYDARK);
		
		//colorToGlColor(gl, Color.RED,AbstractSignature.BRIGHTNESS_VERYDARK);
		gl.glTranslatef(0.7f, 1.275f, 0.01f);
		
		
		if(type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION || type==DisplayFunctions.LINKINGBRUSHING_FUELSTATION+1000)
			FontDrawer.render(GLUT.STROKE_ROMAN, "T", gl, true);
		if(type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT || type==DisplayFunctions.LINKINGBRUSHING_RESTAURANT+1000)
			FontDrawer.render(GLUT.STROKE_ROMAN, "R", gl, true);
		if(type==DisplayFunctions.LINKINGBRUSHING_PERSON || type==DisplayFunctions.LINKINGBRUSHING_PERSON+1000)
			FontDrawer.render(GLUT.STROKE_ROMAN, "P", gl, true);
		if(type==DisplayFunctions.LINKINGBRUSHING_HOTEL || type==DisplayFunctions.LINKINGBRUSHING_HOTEL+1000)
			FontDrawer.render(GLUT.STROKE_ROMAN, "H", gl, true);
		if(type==DisplayFunctions.LINKINGBRUSHING_START || type==DisplayFunctions.LINKINGBRUSHING_START+1000)
		{
			FontDrawer.render(GLUT.STROKE_ROMAN, "Start", gl, true);
		}
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPopMatrix();
		
	}
	
	/**
	 * Objekte hervorheben
	 * @param highlightedGeoObjects Liste mit Objekten, welche hervorgehoben werden soll
	 * @param removedGeoObjects
	 */
	public static void highlightObjects(ArrayList<GeoObject> highlightedGeoObjects,ArrayList<GeoObject> removedGeoObjects)
	{
		for(GeoObject geoObject:highlightedGeoObjects)
		{
			AbstractSignature signature=geoObject.getFocusSignature();
			if(signature.getHighlightSize()<1.5f)
				signature.setHighlightSize(signature.getHighlightSize()+0.02f);
			signature.setHighlightBrightness(signature.getHighlightBrightness()+0.05f);
			if(signature.getHighlightBrightness()>2.0f)
				signature.setHighlightBrightness(signature.getHighlightBrightness()-2.0f);
			geoObject.getContextSignature().setHighlightSize(signature.getHighlightSize());
			geoObject.getContextSignature().setHighlightBrightness(signature.getHighlightBrightness());
		}
		ArrayList<GeoObject> finishedRemovedGeoObjects=new ArrayList<GeoObject>();
		for(GeoObject geoObject:removedGeoObjects)
		{
			AbstractSignature signature=geoObject.getFocusSignature();
			if(signature.getHighlightSize()>1.0f)
				signature.setHighlightSize(signature.getHighlightSize()-0.02f);
			signature.setHighlightBrightness(signature.getHighlightBrightness()+0.02f);
			if(signature.getHighlightBrightness()>2.0f)
				signature.setHighlightBrightness(signature.getHighlightBrightness()-2.0f);
			geoObject.getContextSignature().setHighlightSize(signature.getHighlightSize());
			geoObject.getContextSignature().setHighlightBrightness(signature.getHighlightBrightness());
			if(signature.getHighlightSize()==1.0f && signature.getHighlightBrightness()==1.0f)
				finishedRemovedGeoObjects.add(geoObject);
		}
		removedGeoObjects.removeAll(finishedRemovedGeoObjects);		
	}
	
	/**
	 * Farbe mit Helligkeit in OpenGL einstellen 
	 * @param gl OpenGL Kontext
	 * @param color Farbe 
	 * @param brightness Helligkeit
	 */
	public static void colorToGlColor(GL gl, Color color,int brightness) {
		if(brightness==AbstractSignature.BRIGHTNESS_BRIGHT)
			color=color.brighter();
		if(brightness==AbstractSignature.BRIGHTNESS_DARK)
			color=color.darker();
		if(brightness==AbstractSignature.BRIGHTNESS_VERYDARK)
		{
			color=color.darker();
			color=color.darker();
		}
		
		gl.glColor4f((float) color.getRed() / 255.0f,
				(float) color.getGreen() / 255.0f,
				(float) color.getBlue() / 255.0f,1.0f);
		// gl.glColor4f(1.0f, 0.0f, 0.0f,1.0f);
	}
	
	/*public static void show(int type,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
					
		gl.glTranslatef(0.0f, 0.0f, 0.0001f);
		gl.glLineWidth(4.0f);
				
		if(type==1)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_VERYDARK);
		if(type==1001)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_DARK);
		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(0.6f, 1.1f, 0.0f);
		gl.glVertex3f(0.8f, 1.1f, 0.0f);
		gl.glVertex3f(0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.6f, 1.3f, 0.0f);
		gl.glEnd();
		
		if(type==1)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_DARK);
		if(type==1001)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_NORMAL);
		gl.glTranslatef(0.0f, 0.0f, 0.0001f);
		gl.glBegin(GL.GL_POLYGON);				
		gl.glVertex3f(0.6f, 1.1f, 0.0f);
		gl.glVertex3f(0.8f, 1.1f, 0.0f);
		gl.glVertex3f(0.8f, 1.3f, 0.0f);
		gl.glVertex3f(0.6f, 1.3f, 0.0f);
		gl.glEnd();		

		if(type==1)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_VERYDARK);
		if(type==1001)
			colorToGlColor(gl, Color.YELLOW,AbstractSignature.BRIGHTNESS_DARK);
		gl.glTranslatef(0.7f, 1.275f, 0.0001f);
		FontDrawer.render(GLUT.STROKE_ROMAN, "A", gl, true);
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPopMatrix();
		
	}*/

	/**
	 * Zoom Symbol anzeigen 
	 */
	public static void showZoomSymbol(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		//Plus
		gl.glPushMatrix();
					
		gl.glTranslatef(0.0f, 0.0f, 0.05f);
		gl.glLineWidth(2.0f);
	
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.48f, 0.0f);
		gl.glVertex3f(0.85f, 1.48f, 0.0f);
		gl.glEnd();
		
		gl.glColor3f(0.5f, 0.5f, 0.5f);		
		gl.glTranslatef(0.0f, 0.0f, -0.0001f);
		gl.glBegin(GL.GL_POLYGON);				
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.48f, 0.0f);
		gl.glVertex3f(0.85f, 1.48f, 0.0f);
		gl.glEnd();		

		gl.glTranslatef(0.92f, 1.38f, 0.0001f);
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		FontDrawer.render(GLUT.STROKE_ROMAN, "+", gl, true);
		
		gl.glPopMatrix();
		
		//Minus
		gl.glPushMatrix();
		
		gl.glTranslatef(0.0f, 0.0f, 0.05f);
		gl.glLineWidth(2.0f);
	
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(0.85f, 1.22f, 0.0f);
		gl.glVertex3f(0.98f, 1.22f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glEnd();
		
		gl.glColor3f(0.5f, 0.5f, 0.5f);		
		gl.glTranslatef(0.0f, 0.0f, -0.0001f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f(0.85f, 1.22f, 0.0f);
		gl.glVertex3f(0.98f, 1.22f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glEnd();		

		gl.glTranslatef(0.92f, 1.30f, 0.0001f);
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		FontDrawer.render(GLUT.STROKE_ROMAN, "-", gl, true);
		
		gl.glPopMatrix();
		
		gl.glEnable(GL.GL_LIGHTING);		
	}
	
	/**
	 * Beenden Symbol anzeigen
	 * @param drawable drawable OpenGL
	 */
	public static void showEndSymbol(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		//Plus
		gl.glPushMatrix();
					
		gl.glTranslatef(0.0f, 0.0f, 0.001f);
		gl.glLineWidth(2.0f);
	
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.48f, 0.0f);
		gl.glVertex3f(0.85f, 1.48f, 0.0f);
		gl.glEnd();
		
		gl.glColor3f(0.5f, 0.5f, 0.5f);		
		gl.glTranslatef(0.0f, 0.0f, -0.0001f);
		gl.glBegin(GL.GL_POLYGON);				
		gl.glVertex3f(0.85f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.35f, 0.0f);
		gl.glVertex3f(0.98f, 1.48f, 0.0f);
		gl.glVertex3f(0.85f, 1.48f, 0.0f);
		gl.glEnd();		

		gl.glTranslatef(0.91f, 1.42f, 0.0001f);
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		FontDrawer.render(GLUT.STROKE_ROMAN, "X", gl, true);
		
		gl.glPopMatrix();
		
		gl.glEnable(GL.GL_LIGHTING);		
	}
	
	/**
	 * Rahmen um die zentralen Teil Anzeige ziehen
	 * @param drawable drawable OpenGL
	 */
	public static void showZoomFocusRegion(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		//Plus
		gl.glPushMatrix();
					
		gl.glLineWidth(2.0f);
	
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glBegin(GL.GL_LINE_LOOP);				
		gl.glVertex3f(-0.5f, 0.75f, 0.001f);
		gl.glVertex3f(0.5f, 0.75f, 0.001f);
		gl.glVertex3f(0.5f, -0.75f, 0.001f);
		gl.glVertex3f(-0.5f, -0.75f, 0.001f);
		gl.glEnd();
		
		gl.glPopMatrix();
		
		gl.glEnable(GL.GL_LIGHTING);		
	}
	
	/**
	 * Pfeile für Objekte im Offscreen zeichnen
	 * @param drawable drawable OpenGL
	 * @param pointedGeoObjects Liste mit Geoobjekten 
	 */
	public static void DrawArrows(GLAutoDrawable drawable,ArrayList<GeoObject> pointedGeoObjects)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		MapCubePanel mp=MapCubePanel.getInstance();

		Coordinate[] coordinates=new Coordinate[5];		
		coordinates[0]=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0);
		coordinates[1]=new Coordinate();
		coordinates[1].x=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2).x;
		coordinates[1].y=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0).y;
		coordinates[2]=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2);
		coordinates[3]=new Coordinate();
		coordinates[3].x=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0).x;
		coordinates[3].y=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2).y;
		coordinates[4]=coordinates[0];		
				
		Geometry lineMap=new GeometryFactory().createLineString(coordinates);		
		
		for(GeoObject pointedGeoObject:pointedGeoObjects)
		{
			if(!mp.isGeoObjectVisibleInFocus(pointedGeoObject))
			{

				Geometry line=new GeometryFactory().createLineString(new Coordinate[]{mp.getCenterGeoposition(),pointedGeoObject.getGeometry().getCoordinate()});
				Point crossPoint=(Point)lineMap.intersection(line);
				Point2D p=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getCartesianPointFromGeoPoint(crossPoint.getCoordinate(), false, false);
				
				p.setLocation(p.getX()-1.0f,p.getY()+1.5f);
				
				float angle = (float) Math.atan2((float) 0
						- (float) p.getY(), (float) 0
						- (float) p.getX())
						* 180 / (float) Math.PI;
				
				
				p.setLocation(p.getX()*0.88,p.getY()*0.92);
								
				gl.glPushMatrix();
				gl.glColor3f(0.8f, 0.8f, 0.0f);
				gl.glLineWidth(3.0f);
				gl.glTranslatef((float)p.getX(), (float)p.getY(), 0.02f);
				gl.glTranslatef(0, 0, 0.05f);
				gl.glRotatef(angle + 90, 0.0f, 0.0f, 1.0f);				
				gl.glBegin(GL.GL_LINE_LOOP);
				gl.glVertex3f(0.0f, 0.1f, 0.0f);
				gl.glVertex3f(0.0f, -0.1f, 0.0f);
				gl.glEnd();
				gl.glBegin(GL.GL_LINE_LOOP);
				gl.glVertex3f(0.0f, 0.1f, 0.0f);
				gl.glVertex3f(-0.05f, 0.0f, 0.0f);
				gl.glEnd();
				gl.glBegin(GL.GL_LINE_LOOP);
				gl.glVertex3f(0.0f, 0.1f, 0.0f);
				gl.glVertex3f(0.05f, 0.0f, 0.0f);
				gl.glEnd();
				gl.glPopMatrix();
				
				gl.glPushMatrix();								
											
				p.setLocation(p.getX()*0.8,p.getY()*0.8);
				gl.glTranslatef((float)p.getX(), (float)p.getY(), 0.02f);

				FontDrawer.drawFreeText(pointedGeoObject.getName(), drawable, pointedGeoObject.getFocusSignature().getRealColor(),pointedGeoObject.getFocusSignature().getBrightness() ,1.0f,true);
		
				if(!pointedGeoObject.getObjtype().equals(GeoObject.OBJTYPE_MARKER))					
				{
					gl.glTranslatef(0.0f, -0.1f, 0.0f);
					float distance=MapCubePanel.getInstance().getRealDistance(pointedGeoObject);
					distance=(float)(Math.round(distance*100.0)*0.01);
					FontDrawer.drawFreeText("Entfernung "+distance+"km", drawable, pointedGeoObject.getFocusSignature().getRealColor(),pointedGeoObject.getFocusSignature().getBrightness() ,1.0f,true);
				}
				
				gl.glPopMatrix();
			}
		}
	}
	
	/**
	 * Halos für Objekte, im Offscreen zeichnen
	 * @param drawable drawable OpenGL
	 * @param pointedGeoObjects Liste mit Geoobjekten
	 */
	public static void DrawHaloCues(GLAutoDrawable drawable,ArrayList<GeoObject> pointedGeoObjects)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		MapCubePanel mp=MapCubePanel.getInstance();

		Coordinate[] coordinates=new Coordinate[5];		
		coordinates[0]=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0);
		coordinates[1]=new Coordinate();
		coordinates[1].x=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2).x;
		coordinates[1].y=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0).y;
		coordinates[2]=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2);
		coordinates[3]=new Coordinate();
		coordinates[3].x=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(0).x;
		coordinates[3].y=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getMapExtract().get(2).y;
		coordinates[4]=coordinates[0];		
				
		Geometry lineMap=new GeometryFactory().createLineString(coordinates);
		
		for(GeoObject pointedGeoObject:pointedGeoObjects)
		{
			if(!mp.isGeoObjectVisibleInFocus(pointedGeoObject))
			{
				gl.glDisable(GL.GL_LIGHTING);
				GLU glu = new GLU();
				
				GLUquadric quadric = glu.gluNewQuadric();		

				glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals
				
				Point2D p=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getCartesianPointFromGeoPoint(pointedGeoObject.getGeometry().getCoordinate(), false, false);
				p.setLocation(p.getX()-1.0f,p.getY()+1.5f);
				
				//Abstand zu Fokus Region für Kreisumfang berechnen
				float xdif=0.0f;
				float ydif=0.0f;
				if(p.getX()<-1.0f)
					xdif=(float)p.getX()+1.0f;
				else if(p.getX()>1.0f)
					xdif=(float)p.getX()-1.0f;
				if(p.getY()<-1.5f)
					ydif=(float)p.getY()+1.5f;
				else if(p.getY()>1.5f)
					ydif=(float)p.getY()-1.5f;
				float halowidth=(float) Math.sqrt((xdif*xdif)+(ydif*ydif))+0.2f;
				if(halowidth<0.25f)
					halowidth=0.25f;
			    
				gl.glPushMatrix();
				gl.glTranslatef((float) p.getX(), (float) p.getY(), 0.06f);
				
				//gl.glTranslatef(0.0f,0.0f, 0.06f);
				gl.glLineWidth(1.0f);
				
				//gl.glColor3f(1.0f, 1.0f, 1.0f);
				if((pointedGeoObject.getUse().equals(GeoObject.USE_USERTEST_LOCALIZATION)  || pointedGeoObject.getUse().equals(GeoObject.USE_USERTEST_LOCALIZATION2)) && pointedGeoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
				{
					gl.glTranslatef(0.0f, 0.0f, 0.01f);
					DisplayFunctions.colorToGlColor(gl, Color.RED, AbstractSignature.BRIGHTNESS_NORMAL);
				}
				else					
					DisplayFunctions.colorToGlColor(gl, pointedGeoObject.getFocusSignature().getColor(), AbstractSignature.BRIGHTNESS_NORMAL);
				
				glu.gluDisk(quadric,halowidth, halowidth-0.013f, 32, 32);

				gl.glPopMatrix();
				
				//draw Name
				if(!MapCubePanel.getInstance().isNoScriptGeoObject(pointedGeoObject))
				{
					gl.glPushMatrix();
					Geometry line=new GeometryFactory().createLineString(new Coordinate[]{mp.getCenterGeoposition(),pointedGeoObject.getGeometry().getCoordinate()});
					Point crossPoint=(Point)lineMap.intersection(line);
					Point2D pn=mp.getSurfaces().get(MapCubePanel.SURFACE_FRONT).getCartesianPointFromGeoPoint(crossPoint.getCoordinate(), false, false);
					
					pn.setLocation(pn.getX()-1.0f,pn.getY()+1.5f);							
					
					p.setLocation(pn.getX()*0.67,pn.getY()*0.77);
					gl.glTranslatef((float) p.getX(), (float) p.getY(), 0.06f);
					FontDrawer.linewidth=1.5f;					
					FontDrawer.scale=0.0010f;
					if((pointedGeoObject.getUse().equals(GeoObject.USE_USERTEST_LOCALIZATION)  || pointedGeoObject.getUse().equals(GeoObject.USE_USERTEST_LOCALIZATION2)) && pointedGeoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
						FontDrawer.drawFreeText(pointedGeoObject.getName(), drawable,Color.RED, AbstractSignature.BRIGHTNESS_NORMAL, 1.0f, true);
					else						
						FontDrawer.drawFreeText(pointedGeoObject.getName(), drawable, pointedGeoObject.getFocusSignature().getColor(), AbstractSignature.BRIGHTNESS_NORMAL, 1.0f, true);
					gl.glPopMatrix();
				}
				
			}
		}
	}
	
	/**
	 * Texturen mit Rändern anlegen
	 * @param gl OpenGL Kontext
	 */
	public static void makePerceptionTexture(GL gl)
	{
			Texture texture;

			BufferedImage emptyImage = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);
						
			for(int iy=0;iy<256;iy++)
			{
				for(int ix=0;ix<256;ix++)
				{
					
					int alpha=255*256*256*256;
					int red=(int)(((255-iy)*0.5f)+128.0f)*256*256;
					int green=(int)(((255-iy)*0.5f)+128.0f)*256;
					int blue=(int)(((255-iy)*0.5f)+128.0f);
					int rgb=alpha+red+green+blue;
					emptyImage.setRGB(ix, iy, rgb);
				}
			}
			
			texture = TextureIO.newTexture((BufferedImage)emptyImage, false);
			texture.bind();

			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_LINEAR);
	
			DisplayFunctions.perceptionTexture=texture;			

	}
	
	/**
	 * Textur mit Rändern abrufen
	 * @param gl OpenGL Kontext
	 * @return
	 */
	public static Texture getPerceptionTexture(GL gl)
	{
		if(DisplayFunctions.perceptionTexture==null)
			DisplayFunctions.makePerceptionTexture(gl);
		return DisplayFunctions.perceptionTexture;
	}
	
	/**
	 * Kreuz im Mittelpunkt zeichnen
	 * @param drawable drawable OpenGL
	 */
	public static void drawCenterCross(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 0.05f);
		gl.glLineWidth(3.0f);
		gl.glColor4f(1.0f,1.0f,1.0f,1.0f);
		float vertices1[] = {-0.1f, 0.0f, 0.0f,
				0.1f, 0.0f, 0.0f,
				0.0f, 0.1f, 0.0f,
				0.0f, -0.1f, 0.0f
		};		
		DisplayFunctions.drawVertexArrayObject(gl, vertices1, GL.GL_LINES, null);
		gl.glPopMatrix();
	}
	
	/**
	 * ein VertexArray(Punkt, Linie, Polygon,...) abbilden
	 * @param gl OpenGL Kontext
	 * @param vertices Array mit Vertices
	 * @param type Typ 
	 * @param normals Array mit Normalvektoren
	 */
	public static void drawVertexArrayObject(GL gl,float[] vertices,int type,float[] normals)
	{
		//normals=null;
		int[] indicies=new int[(vertices.length/3)+1];
		for(int i=0;i<indicies.length-1;i++)
			indicies[i]=i;
		indicies[indicies.length-1]=0;
		
		FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
		buf_vertices.put(vertices);
		buf_vertices.rewind();
		
		IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
		buf_indicies.put(indicies);
		buf_indicies.rewind();
		
		FloatBuffer buf_normals = null;
		if(normals!=null)
		{
			if(normals.length==0)
			{
				//auto calc normals
				//u={x1,y1,z1}, v={x2,y2,z2)
				//u X v = {y1*z2 - y2*z1, -x1*z2 + x2*z1, x1*y2-x2*y1}
				float[] u=new float[3];
				u[0]=vertices[3]-vertices[0];
				u[1]=vertices[4]-vertices[1];
				u[2]=vertices[5]-vertices[2];
				float[] v=new float[3];
				v[0]=vertices[6]-vertices[0];
				v[1]=vertices[7]-vertices[1];
				v[2]=vertices[8]-vertices[2];
				float[] normal=new float[3];
				normal[0]=u[1]*v[2]-v[1]*u[2];
				normal[1]=(-1.0f)*u[1]*v[2]+v[0]*u[2];
				normal[2]=u[0]*v[1]-v[0]*u[1];
				normals=new float[vertices.length];
				for(int i=0;i<vertices.length;i+=3)
				{
					normals[i]=normal[0]+i*0.2f;
					normals[i+1]=normal[1]+i*0.1f;
					normals[i+2]=normal[2]+i*0.25f;
				}
			}
			buf_normals = BufferUtil.newFloatBuffer(normals.length);		
			buf_normals.put(normals);
			buf_normals.rewind();
			gl.glEnableClientState(GL.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL.GL_FLOAT, 0,buf_normals);
		}
			
		// activate and specify pointer to vertex array
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);		
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
		
		gl.glDrawElements(type, indicies.length, GL.GL_UNSIGNED_INT, buf_indicies);

		// deactivate vertex arrays after drawing
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
		
		if(normals!=null)
			gl.glDisableClientState(GL.GL_NORMAL_ARRAY);
	}	
	

	/**
	 * Abstand zwischen zwei Geeokoordinaten berechnen
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static double getCoordinateDistance(Coordinate c1,Coordinate c2)
	{
		double distx=(c2.x-c1.x);
		double disty=(c2.y-c1.y);
		double dist=Math.sqrt(distx*distx+disty*disty);
		return dist;
	}
	
	/**
	 * einen Pfeil zeichnen
	 * @param drawable
	 */
	public static void drawArrow(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		float vertices1[] = {
				0.0f, 1.0f, 0.0f,
				0.5f, 0.5f, 0.0f,
				0.15f, 0.5f, 0.0f,
				0.15f, -1.0f, 0.0f,
				-0.15f, -1.0f, 0.0f,
				-0.15f, -1.0f, 0.0f,
				-0.15f, 0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f
		};		
		DisplayFunctions.drawVertexArrayObject(gl, vertices1, GL.GL_POLYGON, null);
	}
	

	/**
	 * Pfeile zeichnen zu Verdeutlichung der Panning Bewegung
	 * @param drawable drawable OpenGL
	 */
	public static void drawPanningArrows(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 0.05f);
		gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
		gl.glScalef(0.8f, 0.8f, 0.8f);
		DisplayFunctions.drawArrow(drawable);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor4f(0.8f,0.0f,0.0f,1.0f);		
		gl.glTranslatef(0.0f, -1.4f, -1.0f);
		gl.glScalef(0.8f, 0.8f, 0.8f);
		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		DisplayFunctions.drawArrow(drawable);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glColor4f(0.8f,0.0f,0.0f,1.0f);		
		gl.glTranslatef(0.0f, 1.4f, -1.0f);
		gl.glScalef(0.8f, 0.8f, 0.8f);
		gl.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
		DisplayFunctions.drawArrow(drawable);
		gl.glPopMatrix();

		
		/*
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 1.4f, -0.5f);
		gl.glColor4f(0.8f,0.0f,0.0f,1.0f);
		gl.glRotatef(-270.0f, 1.0f, 0.0f, 0.0f);
		DisplayFunctions.drawArrow(drawable);
		gl.glPopMatrix();*/
		
	}
	
}

