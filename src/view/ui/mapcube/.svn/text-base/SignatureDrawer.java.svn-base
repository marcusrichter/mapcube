package view.ui.mapcube;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLContext;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.media.opengl.glu.GLUtessellator;


import model.geo_data.*;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;


/**
 * Klasse zeichnet die Signaturen der Geoobjekte
 */
public class SignatureDrawer {
	private float blending;
	private boolean grayout=false;
	private boolean wireframes=true;

	private static SignatureDrawer instance;

	/**
	 * Singleton Instanz des SignatureDrawer abrufen
	 * @return
	 */
	public static SignatureDrawer getInstance()
	{
		if(instance==null)
			instance=new SignatureDrawer();
		return instance;
	}
	
	/**
	 * Transparenzwert abrufen
	 * @return
	 */
	public float getBlending() {
		return blending;
	}
	
	/**
	 * Transparenzwert setzen
	 * @param blending
	 */
	public void setBlending(float blending) {
		this.blending = blending;
	}
	
	/**
	 * Drahtgitterdarstellung akiviert
	 * @return
	 */
	public boolean isWireframes() {
		return wireframes;
	}
	
	/**
	 * Drahtgitterdarstellung akivieren/deaktivieren
	 * @param wireframes
	 */
	public void setWireframes(boolean wireframes) {
		this.wireframes = wireframes;
	}
	
	
	/**
	 * Signatur zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesiche Koordinaten der Signatur 
	 * @param drawable drawable OpenGL
	 * @param mappingrect 
	 */
	public void draw(AbstractSignature signature,Point2D[] surfacePoints,GLAutoDrawable drawable,ArrayList<Coordinate> mappingrect)
	{		
		if(!this.wireframes)
		{
			//Signaturen ohne Drahtgitterdarstellung zeichnen
			if(signature.getShapeType()==Signature2D.SHAPE_CIRCLE)
				this.drawCircle((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDCIRCLE)
				this.drawFilledCircle((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDHEXAGON)
				this.drawHexagon((Signature2D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDPOLYGON)
				this.drawFilledPolygon((Signature2D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDRECTANGLE)
				this.drawRectangle((Signature2D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature2D.SHAPE_LINE)
				this.drawLine((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_POLYGON)
				this.drawPolygon((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_TRIANGLE)
				this.drawTriangle((Signature2D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature2D.SHAPE_RHOMB)
				this.drawRhomb((Signature2D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature2D.SHAPE_CROSS)
				this.drawCross((Signature2D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature3D.SHAPE_CONE)
				this.drawCone((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_CYLINDER)
				this.drawCylinder((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_HEXAGONALPRISM)
				this.drawHexagonalPrism((Signature3D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature3D.SHAPE_PRISM)
				this.drawPrism((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_PYRAMID)
				this.drawPyramid((Signature3D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature3D.SHAPE_RECTANGULARPRISM)
				this.drawRectangluarPrism((Signature3D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature3D.SHAPE_PRISMLINE)
				this.drawPrismLine((Signature3D) signature,surfacePoints,drawable,false);				
			else if(signature.getShapeType()==Signature3D.SHAPE_CYLINDERLINE)
				this.drawCylinderLine((Signature3D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature3D.SHAPE_POLYGONPRISM)
				this.drawPolygonPrism((Signature3D) signature,surfacePoints,drawable,false);				
			else if(signature.getShapeType()==Signature3D.SHAPE_SPHERE)
				this.drawSphere((Signature3D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RHOMBIC_PRISM)
				this.drawRhombicPrism((Signature3D) signature,surfacePoints,drawable,false);				
			else if(signature.getShapeType()==Signature3D.SHAPE_CROSS3D)
				this.drawCross3D((Signature3D) signature,surfacePoints,drawable,false);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RECTANGULARLINEPOLYGON)
				this.drawRectangularlinepolygon((Signature3D) signature,surfacePoints,drawable,false);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RHOMBLINE)
				this.drawRhombLine((Signature3D) signature,surfacePoints,drawable,false);
			else if(signature.getShapeType()==Signature2D.SHAPE_FLATRECTANGULARLINE)
				this.drawFlatRectangleLine((Signature2D) signature,surfacePoints,drawable,false,mappingrect);	
		}
		else
		{
			//Signaturen mit Drahtgitterdarstellung zeichnen
			if(signature.getShapeType()==Signature2D.SHAPE_CIRCLE)
				this.drawCircle((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDCIRCLE)
				this.drawCircle((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDHEXAGON)
				this.drawHexagon((Signature2D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDPOLYGON)
				this.drawPolygon((Signature2D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature2D.SHAPE_FILLEDRECTANGLE)
				this.drawRectangle((Signature2D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature2D.SHAPE_LINE)
				this.drawLine((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_POLYGON)
				this.drawPolygon((Signature2D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature2D.SHAPE_TRIANGLE)
				this.drawTriangle((Signature2D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature2D.SHAPE_RHOMB)
				this.drawRhomb((Signature2D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature2D.SHAPE_CROSS)
				this.drawCross((Signature2D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature3D.SHAPE_CONE)
				this.drawWireframeCone((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_CYLINDER)
				this.drawCylinder((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_HEXAGONALPRISM)
				this.drawHexagonalPrism((Signature3D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature3D.SHAPE_PRISM)
				this.drawPrism((Signature3D) signature,surfacePoints,drawable);
			else if(signature.getShapeType()==Signature3D.SHAPE_PYRAMID)
				this.drawPyramid((Signature3D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature3D.SHAPE_RECTANGULARPRISM)
				this.drawRectangluarPrism((Signature3D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature3D.SHAPE_PRISMLINE)
				this.drawPrismLine((Signature3D) signature,surfacePoints,drawable,true);				
			else if(signature.getShapeType()==Signature3D.SHAPE_CYLINDERLINE)
				this.drawCylinderLineWireFrame((Signature3D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature3D.SHAPE_POLYGONPRISM)
				this.drawPolygonPrism((Signature3D) signature,surfacePoints,drawable,true);				
			else if(signature.getShapeType()==Signature3D.SHAPE_SPHERE)
				this.drawSphereWireframe((Signature3D) signature,surfacePoints,drawable);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RHOMBIC_PRISM)
				this.drawRhombicPrism((Signature3D) signature,surfacePoints,drawable,true);				
			else if(signature.getShapeType()==Signature3D.SHAPE_CROSS3D)
				this.drawCross3D((Signature3D) signature,surfacePoints,drawable,true);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RECTANGULARLINEPOLYGON)
				this.drawRectangularlinepolygon((Signature3D) signature,surfacePoints,drawable,true);				
			else if(signature.getShapeType()==Signature3D.SHAPE_RHOMBLINE)
				this.drawRhombLine((Signature3D) signature,surfacePoints,drawable,true);
			else if(signature.getShapeType()==Signature2D.SHAPE_FLATRECTANGULARLINE)
				this.drawFlatRectangleLine((Signature2D) signature,surfacePoints,drawable,true,mappingrect);	

		}
	}
	
	/**
	 * Kreis zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */
	private void drawCircle(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();		

		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals
	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		glu.gluDisk(quadric, signature.getWitdh()-0.008f, signature.getWitdh(), 8, 8);

				
	}
	
	/**
	 * ausgefüllten Kreis zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */	
	private void drawFilledCircle(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();				
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals	    	    
	    	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		glu.gluDisk(quadric, 0.0, signature.getWitdh(), 8, 8);		

	}
	
	/**
	 * Hexagon zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter
	 */		
	private void drawHexagon(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		
		GL gl = drawable.getGL();
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE_LOOP;
		if(wireframe)
			gl.glLineWidth(0.01f);
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
	   
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		gl.glScalef(signature.getWitdh(), signature.getLength(), 0.0f);
		
		float vertices[] = {0.0f, 0.5f, 0.0f,
				0.5f, 0.25f, 0.0f,
				0.5f, -0.25f, 0.0f,
				0.0f, -0.5f, 0.0f,
				-0.5f, -0.25f, 0.0f,
				-0.5f, 0.25f, 0.0f
		};
		
		DisplayFunctions.drawVertexArrayObject(gl,vertices,type,null);
	}

	/**
	 * Linie zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */		
	private void drawLine(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();		
		gl.glDisable(GL.GL_LIGHTING);
		for (int i = 1; i < surfacePoints.length; i++)
		{
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			gl.glPushMatrix();
				gl.glLineWidth(signature.getWitdh());
				//gl.glLineWidth(10.0f);
				//gl.glColor3f(1.0f,0.0f,0.0f);
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				
				float vertices[] = {(float) startRelPoint.getX(), (float) startRelPoint.getY(),
						0.0f,(float) endRelPoint.getX(), (float) endRelPoint.getY(), 0.0f};
				
				DisplayFunctions.drawVertexArrayObject(gl,vertices,GL.GL_LINES,null);
				
			gl.glPopMatrix();		
		}
		
	}
	
	/**
	 * Linie aus Prisma Formen zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */			
	private void drawPrismLine(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{				
		for (int i = 1; i < surfacePoints.length; i++)
		{
			GL gl = drawable.getGL();
			if(wireframe)
				gl.glDisable(GL.GL_LIGHTING);
			else	
				gl.glEnable(GL.GL_LIGHTING);
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			gl.glPushMatrix();
			{
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				//Todo change in prism
					
				gl.glTranslatef((float) startRelPoint.getX(), (float) startRelPoint.getY(),0.0f);

				float angle = (float) Math.atan2((float) startRelPoint.getY()
						- (float) endRelPoint.getY(), (float) startRelPoint.getX()
						- (float) endRelPoint.getX())
						* 180 / (float) Math.PI;

				gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				gl.glRotatef(angle + 270, 0.0f, 1.0f, 0.0f);

				float height = (float) Math
						.sqrt(((endRelPoint.getX() - startRelPoint.getX()) * (endRelPoint.getX() - startRelPoint
								.getX()))
								+ ((endRelPoint.getY() - startRelPoint.getY()) * (endRelPoint
										.getY() - startRelPoint.getY())));

				//glu.gluCylinder(quadric, signature.getWitdh(), signature.getWitdh(), height, 20, 20);				
				
				gl.glScalef( signature.getWitdh(),signature.getHeight(), height);
				
				gl.glTranslatef(0.0f, 1.0f,0.0f);
				
				float vertices1[] = {
						 0.0f,1.0f,0.0f,
						 1.0f,-1.0f,0.0f,
						-1.0f,-1.0f,0.0f
				};
				float normals1[] = {
						 0.0f,1.0f,-1.0f,
						 1.0f,1.0f,-1.0f,
						-1.0f,1.0f,-1.0f
				};
				
				if(wireframe)
					gl.glLineWidth(0.01f);
				
				if(!wireframe)
					DisplayFunctions.drawVertexArrayObject(gl, vertices1, GL.GL_POLYGON,normals1);
				else
					DisplayFunctions.drawVertexArrayObject(gl, vertices1, GL.GL_LINE_LOOP,normals1);
				

				float vertices2[] = {
						0.0f,1.0f,0.0f,
						1.0f,-1.0f,0.0f,
						1.0f,-1.0f,1.0f,
						0.0f,1.0f,1.0f
				};
				float normals2[] = {
						0.0f,1.0f,-1.0f,
						1.0f,1.0f,-1.0f,
						1.0f,1.0f,1.0f,
						0.0f,1.0f,1.0f
				};
				
				if(!wireframe)
					DisplayFunctions.drawVertexArrayObject(gl, vertices2, GL.GL_POLYGON,normals2);
				else
					DisplayFunctions.drawVertexArrayObject(gl, vertices2, GL.GL_LINE_LOOP,normals2);
				
	
				float vertices3[] = {
						1.0f,-1.0f,0.0f,
						-1.0f,-1.0f,0.0f,
						-1.0f,-1.0f,1.0f,
						1.0f,-1.0f,1.0f
				};
				
				float normals3[] = {
						1.0f,1.0f,-1.0f,
						1.0f,1.0f,-1.0f,
						1.0f,1.0f,1.0f,
						1.0f,1.0f,1.0f
				};
				
				if(!wireframe)
					DisplayFunctions.drawVertexArrayObject(gl, vertices3, GL.GL_POLYGON,normals3);
				else
					DisplayFunctions.drawVertexArrayObject(gl, vertices3, GL.GL_LINE_LOOP,normals3);
					

	
				float vertices4[] = {
						-1.0f,-1.0f,0.0f,
						0.0f,1.0f,0.0f,
						0.0f,1.0f,1.0f,
						-1.0f,-1.0f,1.0f
				};
				float normals4[] = {
						1.0f,1.0f,-1.0f,
						0.0f,1.0f,-1.0f,
						0.0f,1.0f,1.0f,
						1.0f,1.0f,1.0f
				};
				
				if(!wireframe)
					DisplayFunctions.drawVertexArrayObject(gl, vertices4, GL.GL_POLYGON,normals4);
				else
					DisplayFunctions.drawVertexArrayObject(gl, vertices4, GL.GL_LINE_LOOP,normals4);
				
				float vertices5[] = {
						0.0f,1.0f,1.0f,
						1.0f,-1.0f,1.0f,
						-1.0f,-1.0f,1.0f
				};
				float normals5[] = {
						0.0f,1.0f,1.0f,
						1.0f,1.0f,1.0f,
						1.0f,1.0f,-1.0f
				};
				
				if(!wireframe)
					DisplayFunctions.drawVertexArrayObject(gl, vertices5, GL.GL_POLYGON,normals5);
				else
					DisplayFunctions.drawVertexArrayObject(gl, vertices5, GL.GL_LINE_LOOP,normals5);
	
			}
			gl.glPopMatrix();		
		}
	}

	/**
	 * Linie aus Rechteck Formen zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */			
	private void drawRhombLine(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{		
		GL gl = drawable.getGL();
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINES;
		
		if(wireframe)
			gl.glLineWidth(0.01f);
		for(int j=0;j<=1;j++)
		{
			for (int i = 1; i < surfacePoints.length; i++)
			{				
				if(wireframe)
					gl.glDisable(GL.GL_LIGHTING);
				else	
					gl.glEnable(GL.GL_LIGHTING);
				Point2D startRelPoint = surfacePoints[i-1];
				Point2D endRelPoint = surfacePoints[i];
				gl.glPushMatrix();
				{
					
					this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
					//Todo change in prism
						
					if(j==1)
					{
						gl.glTranslatef((float) startRelPoint.getX(), (float) startRelPoint.getY(),signature.getHeight());
						this.colorToGlColor(gl, signature.getColor().darker(),signature.getBrightness());
					}
					else
						gl.glTranslatef((float) startRelPoint.getX(), (float) startRelPoint.getY(),0.0f);
	
					float angle = (float) Math.atan2((float) startRelPoint.getY()
							- (float) endRelPoint.getY(), (float) startRelPoint.getX()
							- (float) endRelPoint.getX())
							* 180 / (float) Math.PI;
	
					gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
					gl.glRotatef(angle + 270, 0.0f, 1.0f, 0.0f);
	
					float height = (float) Math
							.sqrt(((endRelPoint.getX() - startRelPoint.getX()) * (endRelPoint.getX() - startRelPoint
									.getX()))
									+ ((endRelPoint.getY() - startRelPoint.getY()) * (endRelPoint
											.getY() - startRelPoint.getY())));
	
					//glu.gluCylinder(quadric, signature.getWitdh(), signature.getWitdh(), height, 20, 20);				
					
					if(j==1)
						gl.glScalef( 1.0f,0.005f, height);
					else
						gl.glScalef( 1.0f,signature.getHeight(), height);
												
					
					float vertices[] = {0.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 1.0f,
										0.0f, -1.0f, 1.0f,
										0.0f, -1.0f, 0.0f};
					float normals[] = {-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f};
	
					
					int indicies[]={0,1,2,3,0};
					
					FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
					buf_vertices.put(vertices);
					buf_vertices.rewind();
	
					FloatBuffer buf_normals = BufferUtil.newFloatBuffer(normals.length);		
					buf_normals.put(normals);
					buf_normals.rewind();
					
					IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
					buf_indicies.put(indicies);
					buf_indicies.rewind();
					
					gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL.GL_NORMAL_ARRAY);				
					gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
					gl.glNormalPointer(GL.GL_FLOAT, 0,buf_normals);				
					gl.glDrawElements(type, 5, GL.GL_UNSIGNED_INT, buf_indicies);
					// deactivate vertex arrays after drawing
					gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL.GL_NORMAL_ARRAY);
								
				}
				gl.glPopMatrix();		
			}
		}
	}

	/**
	 * Linie aus Zylinder Formen zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */					
	private void drawCylinderLine(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		for (int i = 1; i < surfacePoints.length; i++)
		{
			GL gl = drawable.getGL();
			gl.glEnable(GL.GL_LIGHTING);
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			gl.glPushMatrix();
			{
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				//Todo change in prism
				GLU glu = new GLU();
				GLUquadric quadric = glu.gluNewQuadric();
					
				glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals			    	    
				gl.glTranslatef((float) startRelPoint.getX(), (float) startRelPoint.getY(),
						0.0f);

				float angle = (float) Math.atan2((float) startRelPoint.getY()
						- (float) endRelPoint.getY(), (float) startRelPoint.getX()
						- (float) endRelPoint.getX())
						* 180 / (float) Math.PI;

				gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				gl.glRotatef(angle + 270, 0.0f, 1.0f, 0.0f);

				float height = (float) Math
						.sqrt(((endRelPoint.getX() - startRelPoint.getX()) * (endRelPoint.getX() - startRelPoint
								.getX()))
								+ ((endRelPoint.getY() - startRelPoint.getY()) * (endRelPoint
										.getY() - startRelPoint.getY())));

				//glu.gluCylinder(quadric, signature.getWitdh(), signature.getWitdh(), height, 20, 20);
				glu.gluCylinder(quadric, signature.getWitdh(), signature.getWitdh(), height, 10, 10);
				//glu.gluCylinder(quadric, 0.02f, 0.02f, height, 16, 6);
				glu.gluDeleteQuadric(quadric);
			}
			gl.glPopMatrix();		
		}
	}
	
	/**
	 * Drahtgitter Linie aus Zylinder Formen zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */					
	private void drawCylinderLineWireFrame(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		
		for (int i = 1; i < surfacePoints.length; i++)
		{
			GL gl = drawable.getGL();
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, 0.0f, 0.01f);
			gl.glDisable(GL.GL_LIGHTING);
	
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			gl.glPushMatrix();
			{
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				//Todo change in prism
				GLU glu = new GLU();
				GLUquadric quadric = glu.gluNewQuadric();
					
				glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals			    	    
				gl.glTranslatef((float) startRelPoint.getX(), (float) startRelPoint.getY(),
						0.0f);

				float angle = (float) Math.atan2((float) startRelPoint.getY()
						- (float) endRelPoint.getY(), (float) startRelPoint.getX()
						- (float) endRelPoint.getX())
						* 180 / (float) Math.PI;

				gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				gl.glRotatef(angle + 270, 0.0f, 1.0f, 0.0f);

				float height = (float) Math
						.sqrt(((endRelPoint.getX() - startRelPoint.getX()) * (endRelPoint.getX() - startRelPoint
								.getX()))
								+ ((endRelPoint.getY() - startRelPoint.getY()) * (endRelPoint
										.getY() - startRelPoint.getY())));

				//glu.gluCylinder(quadric, signature.getWitdh(), signature.getWitdh(), height, 20, 20);
				//glu.gluCylinder(quadric, 0.02f, 0.02f, height, 16, 16);
				/*float iHeight=0.0f;
				while(iHeight<height)
				{
					glu.gluDisk(quadric, signature.getWitdh()-0.008f, signature.getWitdh(), 16, 16);
					//glu.gluDeleteQuadric(quadric);
					iHeight+=0.25f;
					gl.glTranslatef(0.0f, 0.0f, 0.1f);
				}*/
				glu.gluDisk(quadric, signature.getWitdh()-0.008f, signature.getWitdh(), 16, 16);
			}
			gl.glPopMatrix();		
			
			gl.glLineWidth(0.8f);
			
			this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
			
			float vertices[] = {(float) startRelPoint.getX(), (float) startRelPoint.getY(), signature.getWitdh(),
					(float) endRelPoint.getX(), (float) endRelPoint.getY(), signature.getWitdh() 
					};			
			DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINES, null);
			float vertices2[] = {(float) startRelPoint.getX(), (float) startRelPoint.getY(), signature.getWitdh()*(-1.0f),
					(float) endRelPoint.getX(), (float) endRelPoint.getY(), signature.getWitdh()*(-1.0f) 
					};			
			DisplayFunctions.drawVertexArrayObject(gl, vertices2, GL.GL_LINES, null);
			gl.glPopMatrix();
			
		}
	}

	/**
	 * Polygon zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */					
	private void drawPolygon(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		gl.glLineWidth(signature.getWitdh());
		this._drawPolygon(signature,surfacePoints,drawable,true);
	}
	
	/**
	 * ausgefülltes Polygon zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */					
	private void drawFilledPolygon(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{		
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		//this._drawPolygon(signature,surfacePoints,drawable,false);
		Coordinate[] coordinates=new Coordinate[surfacePoints.length];
		for(int i=0;i<surfacePoints.length;i++)
		{
			coordinates[i]=new Coordinate();
			coordinates[i].x=surfacePoints[i].getX();
			coordinates[i].y=surfacePoints[i].getY();
			coordinates[i].z=0.0f;
		}		
		this.concavePolygon(drawable,coordinates);
		
		gl.glPushMatrix();
		gl.glLineWidth(2.0f);
		this.colorToGlColor(gl, signature.getColor().darker().darker(),signature.getBrightness());
		gl.glTranslatef(0.0f, 0.0f,0.000001f);
		this._drawPolygon(signature,surfacePoints,drawable,true);
		gl.glPopMatrix();
	}
		
	/**
	 * Linie aus Zylinder Formen zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param withLines mit Linien 
	 */					
	private void _drawPolygon(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean withLines)
	{
		GL gl = drawable.getGL();		
						
		if(!withLines)
			gl.glBegin(GL.GL_TRIANGLE_FAN);
		else
		{			
			gl.glBegin(GL.GL_LINE_LOOP);			
		}			
		
		for(int i=0;i<surfacePoints.length;i++)
		{
			Point2D relPoint = surfacePoints[i];			
			gl.glVertex3f((float) relPoint.getX(), (float) relPoint.getY(), 0.0f);
			
		}
		gl.glEnd();
	}
	
	/**
	 * Rechteck zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param withLines mit Linien 
	 */						
	private void drawRectangle(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		gl.glPushMatrix();
	  	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		
		float w=signature.getWitdh()*0.5f;;
		float h=signature.getLength()*0.5f;
		float vertices[] = {w, h, 0.0f,
				w, -h, 0.0f,
				-w, -h, 0.0f,
				-w, h, 0.0f};
			int indicies[]={0,1,2,3};

		FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
		buf_vertices.put(vertices);
		buf_vertices.rewind();

		IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
		buf_indicies.put(indicies);
		buf_indicies.rewind();
	
		// activate and specify pointer to vertex array
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);		
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
		if(!wireframe)
			gl.glDrawElements(GL.GL_POLYGON, 4, GL.GL_UNSIGNED_INT, buf_indicies);
		else
			gl.glDrawElements(GL.GL_LINE_LOOP, 4, GL.GL_UNSIGNED_INT, buf_indicies);
		// deactivate vertex arrays after drawing
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
		gl.glPopMatrix();
	}
	
	/**
	 * Dreieck zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter
	 */					
	private void drawTriangle(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{	
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE_LOOP;
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
	   
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		gl.glScalef(signature.getWitdh(), signature.getLength(), 0.0f);
		
		
		float vertices[] = {0.0f, 0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f,
				0.5f, -0.5f, 0.0f
				};
		
		DisplayFunctions.drawVertexArrayObject(gl, vertices, type, null);
				
	}
	
	/**
	 * Kegel zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */						
	private void drawCone(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();	
		gl.glEnable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();				
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals	    	    
	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
				
		glu.gluCylinder(quadric, signature.getWitdh(), 0.0f, signature.getHeight(),8,8);		
	}

	/**
	 * Drahtgitterkegel zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */						
	private void drawWireframeCone(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();	
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();				
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals	    	    
	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
				
		//glu.gluCylinder(quadric, signature.getWitdh(), 0.0f, signature.getHeight(), 32, 32);
		glu.gluDisk(quadric, signature.getWitdh()-0.008f, signature.getWitdh(), 8, 8);
		
		float vertices[] = {0.0f,0.5f* signature.getWitdh(), 0.0f,
				0.0f, 0.0f, signature.getHeight(),				
				0.0f, (-0.5f)*signature.getWitdh(), 0.0f,
				0.0f, 0.0f, signature.getHeight(),
				(-0.5f)*signature.getWitdh(),0.0f, 0.0f,
				0.0f, 0.0f, signature.getHeight(),
				(-0.5f)*signature.getWitdh(),0.0f, 0.0f,
				0.0f, 0.0f, signature.getHeight(),

				};
		
		DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINES, null);
		
	}

	/**
	 * Zylinder zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */						
	private void drawCylinder(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();				
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals	    	   
	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		
		glu.gluCylinder(quadric, signature.getWitdh(), signature
				.getWitdh(), signature.getHeight(), 8, 8);
		
	}
	
	/**
	 * Hexagonales Prisma zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */						
	private void drawHexagonalPrism(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE_LOOP;
		GL gl = drawable.getGL();
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
	       
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		gl.glScalef(signature.getWitdh(), signature.getLength(), signature.getHeight());
		
		float vertices1[] = {0.0f, 0.5f, 0.0f,
				0.5f, 0.25f, 0.0f,
				0.5f, 0.25f, 0.0f,
				0.5f, -0.25f, 0.0f,
				0.0f, -0.5f, 0.0f,
				-0.5f, -0.25f, 0.0f,
				-0.5f, 0.25f, 0.0f
		};
		
		float normals1[] = {0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices1, type, normals1);
				
		float vertices2[] = {0.0f, 0.5f, 0.0f,
				0.5f, 0.25f, 0.0f,
				0.5f, 0.25f, 1.0f,
				0.0f, 0.5f, 1.0f
		};
		
		float normals2[] = {1.0f,1.0f,0.0f,
				1.0f,1.0f,0.0f,
				1.0f,1.0f,0.0f,
				1.0f,1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices2, type, normals2);
		
		float vertices3[] = {0.5f, 0.25f, 0.0f,
				0.5f, -0.25f, 0.0f,
				0.5f, -0.25f, 1.0f,
				0.5f, 0.25f, 1.0f
		};
		
		float normals3[] = {1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices3, type, normals3);

		float vertices4[] = {0.5f, -0.25f, 0.0f,
				0.0f, -0.5f, 0.0f,
				0.0f, -0.5f, 1.0f,
				0.5f, -0.25f, 1.0f
		};
		
		float normals4[] = {1.0f,-1.0f,0.0f,
				1.0f,-1.0f,0.0f,
				1.0f,-1.0f,0.0f,
				1.0f,-1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices4, type, normals4);
		
		float vertices5[] = {0.0f, -0.5f, 0.0f,
				-0.5f, -0.25f, 0.0f,
				-0.5f, -0.25f, 1.0f,
				0.0f, -0.5f, 1.0f
		};
		
		float normals5[] = {-1.0f,-1.0f,0.0f,
				-1.0f,-1.0f,0.0f,
				-1.0f,-1.0f,0.0f,
				-1.0f,-1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices5, type, normals5);
		

		float vertices6[] = {-0.5f, -0.25f, 0.0f,
				-0.5f, 0.25f, 0.0f,
				-0.5f, 0.25f, 1.0f,
				-0.5f, -0.25f, 1.0f
		};
		
		float normals6[] = {-1.0f,0.0f,0.0f,
				-1.0f,0.0f,0.0f,
				-1.0f,0.0f,0.0f,
				-1.0f,0.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices6, type, normals6);
		
		float vertices7[] = {-0.5f, 0.25f, 0.0f,
				0.0f, 0.5f, 0.0f,
				0.0f, 0.5f, 1.0f,
				-0.5f, 0.25f, 1.0f
		};
		
		float normals7[] = {-1.0f,1.0f,0.0f,
				-1.0f,1.0f,0.0f,
				-1.0f,1.0f,0.0f,
				-1.0f,1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices7, type, normals7);
		
		float vertices8[] = {0.0f, 0.5f, 1.0f,
				0.5f, 0.25f, 1.0f,
				0.5f, -0.25f, 1.0f,
				0.0f, -0.5f, 1.0f,
				-0.5f, -0.25f, 1.0f,
				-0.5f, 0.25f, 1.0f
		};		
		float normals8[] = {0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices8, type, normals8);
		
	}

	/**
	 * Pyramide zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 */						
	private void drawPyramid(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE_LOOP;
		
		GL gl = drawable.getGL();	
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		gl.glScalef(signature.getWitdh(), signature.getWitdh(), signature.getHeight());		
		gl.glTranslatef(0.0f, 1.0f, 0.0f);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		
		float vertices1[] = {1.0f,-1.0f,1.0f,
				1.0f,-1.0f,-1.0f,
				-1.0f,-1.0f,-1.0f,
				-1.0f,-1.0f,1.0f
		};		
		float normals1[] = {0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f
		};
		DisplayFunctions.drawVertexArrayObject(gl, vertices1, type, normals1);
		
		float vertices2[] = {0.0f,1.0f,0.0f,
				1.0f,-1.0f,1.0f,
				1.0f,-1.0f,-1.0f
		};		
		float normals2[] = {0.894427f,0.447214f,0.0f,
				0.894427f,0.447214f,0.0f,
				0.894427f,0.447214f,0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices2, type, normals2);
		
		float vertices3[] = {0.0f,1.0f,0.0f,
				1.0f,-1.0f,-1.0f,
				-1.0f,-1.0f,-1.0f
		};		
		float normals3[] = {0.0f,0.447214f,-0.894427f,
				0.0f,0.447214f,-0.894427f,
				0.0f,0.447214f,-0.894427f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices3, type, normals3);
		
		float vertices4[] = {0.0f,1.0f,0.0f,
				-1.0f,-1.0f,-1.0f,
				-1.0f,-1.0f,1.0f
		};		
		float normals4[] = {-0.894427f,0447214f,0.0f,
				-0.894427f,0447214f,0.0f,
				-0.894427f,0447214f,0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices4, type, normals4);
		
		float vertices5[] = {0.0f,1.0f,0.0f,
				-1.0f,-1.0f,1.0f,
				1.0f,-1.0f,1.0f
		};		
		float normals5[] = {0.0f,0.447214f,0.894427f,
				0.0f,0.447214f,0.894427f,
				0.0f,0.447214f,0.894427f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices5, type, normals5);
	}
	
	/**
	 * Polygon aus Prismas zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */						
	private void drawPolygonPrism(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
	    		
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
		for(int i=0;i<surfacePoints.length;i++)
		{									
			Coordinate[] coordinates=new Coordinate[surfacePoints.length];
			for(int j=0;j<surfacePoints.length;j++)
			{
				coordinates[j]=new Coordinate();
				coordinates[j].x=surfacePoints[j].getX();
				coordinates[j].y=surfacePoints[j].getY();
				coordinates[j].z=0.0f;
			}		
			
			if(wireframe==false)
				this.concavePolygon(drawable,coordinates);
			else
				this.wireframePolygon(drawable, coordinates);
			//gl.glVertex3f((float)surfacePoints[i].getX(),(float)surfacePoints[i].getY(),0.0f);
		}
		
		for(int i=1;i<surfacePoints.length;i++)
		{
			float vertices[] = {(float)surfacePoints[i].getX(),(float)surfacePoints[i].getY(),0.0f,
					(float)surfacePoints[i-1].getX(),(float)surfacePoints[i-1].getY(),0.0f,
					(float)surfacePoints[i-1].getX(),(float)surfacePoints[i-1].getY(),0.02f,
					(float)surfacePoints[i].getX(),(float)surfacePoints[i].getY(),0.02f
					
			};		
			float normals[] = {(float)surfacePoints[i].getY(),(float)surfacePoints[i].getX(),0.0f,
					(float)surfacePoints[i-1].getY(),(float)surfacePoints[i-1].getX(),0.0f,
					(float)surfacePoints[i-1].getY(),(float)surfacePoints[i-1].getX(),0.0f,
					(float)surfacePoints[i-1].getY(),(float)surfacePoints[i-1].getX(),0.0f
			};	
			if(!wireframe)
				DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_POLYGON, normals);
			else
				DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINE_LOOP, null);
			
		}
		
		for(int i=0;i<surfacePoints.length;i++)
		{			
			gl.glNormal3f(0.0f,0.0f,1.0f);
			Coordinate[] coordinates=new Coordinate[surfacePoints.length];
			for(int j=0;j<surfacePoints.length;j++)
			{
				coordinates[j]=new Coordinate();
				coordinates[j].x=surfacePoints[j].getX();
				coordinates[j].y=surfacePoints[j].getY();
				coordinates[j].z=0.02f;
			}
			if(!this.wireframes)
				this.concavePolygon(drawable,coordinates);
			else
				this.wireframePolygon(drawable,coordinates);
			//gl.glVertex3f((float)surfacePoints[i].getX(),(float)surfacePoints[i].getY(),0.02f);
		}		
		//ende
	}


	/**
	 * rechteckiges Prisma zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */							
	private void drawRectangluarPrism(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		
		gl.glPushMatrix();
			    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		gl.glScalef(signature.getWitdh()*0.5f, signature.getLength()*0.5f, signature.getHeight()*0.5f);
				
		//gl.glTranslatef(0.0f, 0.0f, 1.0f);

		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		this._drawRectularPrism(drawable,wireframe);
		gl.glPopMatrix();
		
	}
	
	/**
	 * rechteckiges Prisma zeichnen
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */								
	private void _drawRectularPrism(GLAutoDrawable drawable,boolean wireframe)
	{
		int drawType=GL.GL_POLYGON;
		if(wireframe)
			drawType=GL.GL_LINE_LOOP;
		
		GL gl = drawable.getGL();
		
		gl.glTranslatef(0.0f, 0.0f, 1.0f);
		
		float vertices1[] = {1.0f, 1.0f, -1.0f,
				1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, 1.0f, -1.0f
		};		
		float normals1[] = {0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices1, drawType, normals1);
		
		float vertices2[] = {1.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 1.0f,
				-1.0f, -1.0f, 1.0f,
				-1.0f, 1.0f, 1.0f
		};		
		float normals2[] = {0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices2, drawType, normals2);
		
		float vertices3[] = {1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, -1.0f,
				1.0f, -1.0f, -1.0f,
				1.0f, -1.0f, 1.0f
		};		
		float normals3[] = {1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices3, drawType, normals3);

		float vertices4[] = {-1.0f, 1.0f, 1.0f,
				-1.0f, 1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, 1.0f
		};		
		float normals4[] = {-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices4, drawType, normals4);
		
		float vertices5[] = {1.0f, -1.0f, 1.0f,
				1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, 1.0f
		};		
		float normals5[] = {0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices5, drawType, normals5);
		
		float vertices6[] = {1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, -1.0f,
				-1.0f, 1.0f, -1.0f,
				-1.0f, 1.0f, 1.0f
		};		
		float normals6[] = {0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f
		};	
		DisplayFunctions.drawVertexArrayObject(gl, vertices6, drawType, normals6);
	}

	/**
	 * Kugel zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */							
	private void drawSphere(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_LIGHTING);
		
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();		
		
		//glu.gluQuadricNormals(quadric, GLU.GLU_NONE);
		//glu.gluQuadricDrawStyle(quadric, GLU.GLU_LINE);
		glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals
		
		//gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_LINE);
	    	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		
		/*glu.gluCylinder(quadric, signature.getWitdh(), signature
			.getWitdh(), signature.getHeight(), 32, 32);*/
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());

		glu.gluSphere(quadric,signature.getWitdh() , 8, 8);
		glu.gluDeleteQuadric(quadric);
		
		
	}
	
	/**
	 * Drathgitterkugel zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL 
	 */								
	private void drawSphereWireframe(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		
		GLU glu = new GLU();
		
		GLUquadric quadric = glu.gluNewQuadric();		
		
		glu.gluQuadricNormals(quadric, GLU.GLU_NONE);

		//glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH); // Create Smooth Normals
		
		//gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_LINE);
	    	    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);
		
		/*glu.gluCylinder(quadric, signature.getWitdh(), signature
			.getWitdh(), signature.getHeight(), 32, 32);*/
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		
		for(int j=0;j<=1;j++)
		{
			if(j==1)
				gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			
			for(int i=0;i<6;i++)
			{
				float factor=(float)Math.cos(((float)(90/6)*(float)i*(float)Math.PI)/(float)180);
				float factor2=(float)Math.sin(((float)(90/6)*(float)i*(float)Math.PI)/(float)180);
				float newwidth=signature.getWitdh()*factor;
				float dist=signature.getWitdh()*factor2;
				gl.glPushMatrix();
				gl.glTranslatef(0.0f,0.0f, dist);
				glu.gluDisk(quadric, newwidth-0.01f, newwidth, 8, 8);
				gl.glPopMatrix();
				gl.glPushMatrix();
				gl.glTranslatef(0.0f,0.0f,(-1.0f)* dist);
				glu.gluDisk(quadric, newwidth-0.01f, newwidth, 8, 8);
				gl.glPopMatrix();
	
			}
		}
	}

	/**
	 * Farbe in OpenGL setzen
	 * @param gl OpenGL Kontext
	 * @param color Farbe
	 * @param brightness Helligkeit
	 */
	private void colorToGlColor(GL gl, Color color,int brightness) {
		if(brightness==AbstractSignature.BRIGHTNESS_BRIGHT)
			color=color.brighter();
		if(brightness==AbstractSignature.BRIGHTNESS_DARK)
			color=color.darker();		
		if(this.grayout)
		{
			float greyfactor=Math.max(Math.max((float) color.getRed(), (float) color.getGreen()),(float) color.getBlue());
			float red=((float) color.getRed() +greyfactor*3)/ 1023.0f;
			//red=((float) red +128.0f)/ 511.0f;
			float green=((float) color.getGreen() +greyfactor*3)/ 1023.0f;
			//green=((float) green +128.0f)/ 511.0f;
			float blue=((float) color.getBlue() +greyfactor*3)/ 1023.0f;
			//blue=((float) blue +128.0f)/ 511.0f;
			
			gl.glColor4f(red,
					green,
					blue,this.blending);			
		}
		else
		{
			gl.glColor4f((float) color.getRed() / 255.0f,
				(float) color.getGreen() / 255.0f,
				(float) color.getBlue() / 255.0f,this.blending);
		}
		// gl.glColor4f(1.0f, 0.0f, 0.0f,1.0f);
	}
	
	/**
	 * Drahtgitterpolgon zeichnen
	 * @param drawable drawable OpenGL
	 * @param coordinates Array mit Koordinaten
	 */
	private void wireframePolygon(GLAutoDrawable drawable, Coordinate[] coordinates)
	{
		GL gl = drawable.getGL();
		float[] vertices=new float[coordinates.length*3];
		int i=0;
		for(Coordinate coordinate:coordinates)
		{
			vertices[i*3]=(float)coordinate.x;
			vertices[i*3+1]=(float)coordinate.y;
			vertices[i*3+2]=0.0f;
			i++;
		}

		gl.glLineWidth(0.01f);
		DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINES, null);
		
	}
	/**
	 * konkaves Polygon zeichnen
	 * @param drawable drawable OpenGL
	 * @param coordinates Array mit Koordinaten
	 */
	private void concavePolygon(GLAutoDrawable drawable, Coordinate[] coordinates)
	  {
		if(coordinates.length<3)
			coordinates=coordinates;
	    GL gl = drawable.getGL();
	    GLU glu = new GLU();
	    /*
	     * jogl specific addition for tessellation
	     */
	    tessellCallBack tessCallback = new tessellCallBack(gl, glu);


	    double[][] data = new double[coordinates.length][3];

	    for(int i=0;i<coordinates.length;i++)
	    {
	    	data[i][0]=coordinates[i].x;
	    	data[i][1]=coordinates[i].y;
	    	data[i][2]=coordinates[i].z;
	    }
	   

	    GLUtessellator tobj = glu.gluNewTess();

	    glu.gluTessCallback(tobj, GLU.GLU_TESS_VERTEX, tessCallback);// vertexCallback);
	    glu.gluTessCallback(tobj, GLU.GLU_TESS_BEGIN, tessCallback);// beginCallback);
	    glu.gluTessCallback(tobj, GLU.GLU_TESS_END, tessCallback);// endCallback);
	    glu.gluTessCallback(tobj, GLU.GLU_TESS_ERROR, tessCallback);// errorCallback);
	    //glu.gluTessCallback(tobj, GLU.GLU_TESS_COMBINE, tessCallback);// combineCallback);

	    glu.gluTessProperty(tobj, //
	        GLU.GLU_TESS_WINDING_RULE, //
	        GLU.GLU_TESS_WINDING_POSITIVE);
	    glu.gluTessBeginPolygon(tobj, null);
	    glu.gluTessBeginContour(tobj);
	    for(int i=0;i<coordinates.length;i++)
	    {
	    	glu.gluTessVertex(tobj, data[i], 0, data[i]);
	    }
	    glu.gluTessEndContour(tobj);
	    try
	    {
	    	glu.gluTessEndPolygon(tobj);
	    }
	    catch(Exception ex)
	    {
	    	ex=ex;
	    }
	    glu.gluDeleteTess(tobj);
	  }
	
	/**
	 * Rechteck zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawRhomb(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
	  	    
		gl.glPushMatrix();
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);		
		gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
			
		float w=signature.getWitdh()*0.5f*2;;
		float h=signature.getLength()*0.5f;
			
		float vertices[] = {w, h, 0.0f,
							w, -h, 0.0f,
							-w, -h, 0.0f,
							-w, h, 0.0f};
		int indicies[]={0,1,2,3,0};
		
		FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
		buf_vertices.put(vertices);
		
		IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
		buf_indicies.put(indicies);
				
		// activate and specify pointer to vertex array
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);		
		buf_vertices.rewind();
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
		buf_indicies.rewind();
		if(wireframe)
			gl.glDrawElements(GL.GL_LINE_STRIP, 5, GL.GL_UNSIGNED_INT, buf_indicies);
		else
			gl.glDrawElements(GL.GL_POLYGON, 5, GL.GL_UNSIGNED_INT, buf_indicies);
		// deactivate vertex arrays after drawing
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
		gl.glPopMatrix();
	}

	/**
	 * Prisma aus Rechtecken zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawRhombicPrism(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
						
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
			    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		//start
		gl.glScalef(signature.getWitdh()*0.5f, signature.getLength()*0.5f, signature.getHeight()*0.5f);			
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		gl.glPushMatrix();
		gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
		this._drawRectularPrism(drawable,wireframe);
		gl.glPopMatrix();
		
		this.drawRectangluarPrism(signature, surfacePoints, drawable,wireframe);
	}

	/**
	 * Kreuz zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawCross(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE;
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_LIGHTING);
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
	  	    
		gl.glPushMatrix();
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);		
		gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);		
		float w=signature.getWitdh()*0.5f;;
		float h=signature.getLength()*0.5f;
		float vertices[] = {w, h, 0.0f,
				w, -h, 0.0f,
				-w, -h, 0.0f,
				-w, h, 0.0f};
		float normals[] = {0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f};
		DisplayFunctions.drawVertexArrayObject(gl, vertices, type, normals);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);		
		gl.glRotatef(135.0f, 0.0f, 0.0f, 1.0f);		
		DisplayFunctions.drawVertexArrayObject(gl, vertices, type, normals);
		gl.glPopMatrix();
		
	}
	
	/**
	 * 3D Kreuz zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawCross3D(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		GL gl = drawable.getGL();
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
		
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
			    
		gl.glTranslatef((float) surfacePoints[0].getX(), (float) surfacePoints[0].getY(), 0.0f);

		//start
					
		this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
		gl.glPushMatrix();
		gl.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
		gl.glScalef(signature.getWitdh()*0.5f, signature.getLength()*0.5f, signature.getHeight()*0.5f);
		this._drawRectularPrism(drawable,wireframe);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glRotatef(135.0f, 0.0f, 0.0f, 1.0f);
		gl.glScalef(signature.getWitdh()*0.5f, signature.getLength()*0.5f, signature.getHeight()*0.5f);
		this._drawRectularPrism(drawable,wireframe);
		gl.glPopMatrix();

	}

	/**
	 * Polygon mit einer Linie aus Rechtecken zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawRectangularlinepolygon(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe)
	{
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINES;
		GL gl = drawable.getGL();		
		//gl.glDisable(GL.GL_LIGHTING);
		if(wireframe)
			gl.glDisable(GL.GL_LIGHTING);
		else	
			gl.glEnable(GL.GL_LIGHTING);
		for (int i = 1; i < surfacePoints.length; i++)
		{
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			gl.glPushMatrix();
				//gl.glLineWidth(signature.getWitdh());
				//gl.glLineWidth(10.0f);
				//gl.glColor3f(1.0f,0.0f,0.0f);
			if(wireframe)
				gl.glLineWidth(0.01f);
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				
				float vertices[] = {(float) startRelPoint.getX(), (float) startRelPoint.getY(),0.0f,
						(float) startRelPoint.getX(), (float) startRelPoint.getY(),signature.getHeight(),
						(float) endRelPoint.getX(), (float) endRelPoint.getY(),signature.getHeight(),
						(float) endRelPoint.getX(), (float) endRelPoint.getY(), 0.0f};
				float normals[] = {0.0f, 0.0f, -1.0f,
						0.0f, 0.0f, -1.0f,
						0.0f, 0.0f, -1.0f,
						0.0f, 0.0f, -1.0f};
				DisplayFunctions.drawVertexArrayObject(gl, vertices, type, normals);
			gl.glPopMatrix();		
		}
		//gl.glEnable(GL.GL_LIGHTING);
	}

	/**
	 * Status ausgegraut abfragen
	 * @return Status ausgegraut
	 */
	public boolean isGrayout() {
		return grayout;
	}

	/**
	 * Status ausgegraut setzen
	 * @param grayout Status ausgegraut
	 */
	public void setGrayout(boolean grayout) {
		this.grayout = grayout;
	}
	
	/**
	 * Linie mit Rechtecken zeichnen
	 * @param signature Signatur Objekt
	 * @param surfacePoints kartesische Koordinaten der Signatur  
	 * @param drawable drawable OpenGL
	 * @param wireframe Drahtgitter 
	 */
	private void drawFlatRectangleLine(Signature2D signature,Point2D[] surfacePoints,GLAutoDrawable drawable,boolean wireframe,ArrayList<Coordinate> mappingrect)
	{			
		int type=GL.GL_POLYGON;
		if(wireframe)
			type=GL.GL_LINE_LOOP;

		float[] lastpoints=null;
		for (int i = 1; i < surfacePoints.length; i++)
		{
			GL gl = drawable.getGL();
			if(wireframe)
				gl.glLineWidth(0.01f);
			gl.glDisable(GL.GL_LIGHTING);
			Point2D startRelPoint = surfacePoints[i-1];
			Point2D endRelPoint = surfacePoints[i];
			
			double marginLeftStart=Math.abs(startRelPoint.getX()-mappingrect.get(0).x);
			double marginRightStart=Math.abs(mappingrect.get(1).x-startRelPoint.getX());
			double marginTopStart=Math.abs(startRelPoint.getY()-mappingrect.get(0).y);
			double marginBottomStart=Math.abs(mappingrect.get(1).y-startRelPoint.getY());

			double marginLeftEnd=Math.abs(endRelPoint.getX()-mappingrect.get(0).x);
			double marginRightEnd=Math.abs(mappingrect.get(1).x-endRelPoint.getX());
			double marginTopEnd=Math.abs(endRelPoint.getY()-mappingrect.get(0).y);
			double marginBottomEnd=Math.abs(mappingrect.get(1).y-endRelPoint.getY());
			
				float vx=(float)(endRelPoint.getX()-startRelPoint.getX());
				float vy=(float)(endRelPoint.getY()-startRelPoint.getY());
				float length=(float)Math.sqrt(vx*vx+vy*vy);
				vx=vx*signature.getWitdh()/length;
				vy=vy*signature.getWitdh()/length;
				float vx1=vx;
				float vy1=(-1.0f)*vy;
				float vx2=(-1.0f)*vx;
				float vy2=vy;
				/*vx1=0.0f;
				vx2=0.0f;
				vy1=0.0f;
				vy2=0.0f;*/
				
				float[] startpoints=new float[4];
				if(lastpoints!=null)
				{
					startpoints[0]=lastpoints[0];
					startpoints[1]=lastpoints[1];
					startpoints[2]=lastpoints[2];
					startpoints[3]=lastpoints[3];
				}
				else
				{
					startpoints[0]=(float)(startRelPoint.getX()+vy1);
					startpoints[1]=(float)(startRelPoint.getY()+vx1);
					startpoints[2]=(float)(startRelPoint.getX()+vy2);
					startpoints[3]=(float)(startRelPoint.getY()+vx2);
				}
				
				//
				if(i==1)
				{
					if((marginLeftStart<=marginRightStart && marginLeftStart<=marginTopStart && marginLeftStart<=marginBottomStart)
					   || (marginRightStart<=marginLeftStart && marginRightStart<=marginTopStart && marginRightStart<=marginBottomStart)			
					)
					{
						startpoints[0]=(float)(startRelPoint.getX());
						startpoints[1]=(float)(startRelPoint.getY()+signature.getWitdh());
						startpoints[2]=(float)(startRelPoint.getX());
						startpoints[3]=(float)(startRelPoint.getY()+(-1.0f)*signature.getWitdh());
					}
					
					if((marginTopStart<=marginRightStart && marginTopStart<=marginLeftStart && marginTopStart<=marginBottomStart)
							   || (marginBottomStart<=marginLeftStart && marginBottomStart<=marginTopStart && marginBottomStart<=marginRightStart)			
							)
					{
						startpoints[0]=(float)(startRelPoint.getX()+signature.getWitdh());
						startpoints[1]=(float)(startRelPoint.getY());
						startpoints[2]=(float)(startRelPoint.getX()+(-1.0f)*signature.getWitdh());
						startpoints[3]=(float)(startRelPoint.getY());
					}
				}
				
				float[] endpoints=new float[4];
				endpoints[0]=(float)(endRelPoint.getX()+vy1);
				endpoints[1]=(float)(endRelPoint.getY()+vx1);
				endpoints[2]=(float)(endRelPoint.getX()+vy2);
				endpoints[3]=(float)(endRelPoint.getY()+vx2);
				
				
				if(i==surfacePoints.length-1)
				{
					if(i==surfacePoints.length-1)
					{
						if((marginLeftEnd<=marginRightEnd && marginLeftEnd<=marginTopEnd && marginLeftEnd<=marginBottomEnd)
						   || (marginRightEnd<=marginLeftEnd && marginRightEnd<=marginTopEnd && marginRightEnd<=marginBottomEnd)			
						)
						{
							endpoints[0]=(float)(endRelPoint.getX());
							endpoints[1]=(float)(endRelPoint.getY()+signature.getWitdh());
							endpoints[2]=(float)(endRelPoint.getX());
							endpoints[3]=(float)(endRelPoint.getY()+(-1.0f)*signature.getWitdh());
						}
						
						if((marginTopEnd<=marginRightEnd && marginTopEnd<=marginLeftEnd && marginTopEnd<=marginBottomEnd)
								   || (marginBottomEnd<=marginLeftEnd && marginBottomEnd<=marginTopEnd && marginBottomEnd<=marginRightEnd)			
								)
						{
							endpoints[0]=(float)(endRelPoint.getX()+signature.getWitdh());
							endpoints[1]=(float)(endRelPoint.getY());
							endpoints[2]=(float)(endRelPoint.getX()+(-1.0f)*signature.getWitdh());
							endpoints[3]=(float)(endRelPoint.getY());
						}
					}
				}
				
				lastpoints=endpoints;
				
				float vertices[] = {startpoints[0], startpoints[1], 0.0f,
						startpoints[2], startpoints[3], 0.0f,
						endpoints[2], endpoints[3], 0.0f,
						endpoints[0], endpoints[1], 0.0f};
				
				this.colorToGlColor(gl, signature.getColor(),signature.getBrightness());
				
				if(wireframe)
					gl.glLineWidth(0.01f);
				
				DisplayFunctions.drawVertexArrayObject(gl, vertices, type,null);
	
		}
	}
	
	private void drawPrism(Signature3D signature,Point2D[] surfacePoints,GLAutoDrawable drawable)
	{
		
	}
	
}
