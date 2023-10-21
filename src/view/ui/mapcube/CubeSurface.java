package view.ui.mapcube;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import model.geo_data.AbstractSignature;
import model.geo_data.GeoObject;
import model.geo_data.Signature3D;

import Jama.Matrix;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.Texture;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Darstellung einer Oberfläche des Mapcubes
 */
public class CubeSurface {
	private boolean xMirror=false;
	private boolean yMirror=false;	

	private Texture mapTexture = null;		
	private float blending = 1.0f;
	private boolean empty = false;
	
	// Drehung und Masse der Oberfläche
	public float rotY = 0.0f;
	public float rotX = 0.0f;
	public float height;
	public float width;

	private int zoom;	
	private String id = "";
	// Verschiebung um X Pixel
	public int translateX;
	public int translateY;	
	private ArrayList<Coordinate> mapExtract=new ArrayList<Coordinate>();
	private Hashtable<String,Point3D> eucldianCoords=new Hashtable<String,Point3D>();
	private boolean wireframe=false;

	private String bmatrix_key=null;
	private Matrix bmatrix=null;
	private Rectangle displayRect = new Rectangle();
	
	private int translateMapX, translateMapY;
			

	/**
	 * Status (Würfelfläche leer) abrufen
	 * @return Status (Würfelfläche leer)
	 */
	public boolean isEmpty() {
		return empty;
	}
	
	/**
	 * Status (Würfelfläche leer) setzen
	 * @param empty Status (Würfelfläche leer)
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * Abmessungen des Rechtecks für die Anzeige auslesen
	 * @return Abmessungen des Rechtecks für die Anzeige
	 */
	public Rectangle getDisplayRect() {
		return displayRect;
	}
	
	/**
	 * Abmessungen des Rechtecks für die Anzeige setzen
	 * @param displayRect Abmessungen des Rechtecks für die Anzeige
	 */
	public void setDisplayRect(Rectangle displayRect) {
		this.displayRect = displayRect;
	}

	/**
	 * Drehung der Fläche um die Y Achse in der Anzeige auslesen
	 * @return Drehung der Fläche um die Y Achse in der Anzeige
	 */
	public float getRotY() {
		return rotY;
	}

	/**
	 * Drehung der Fläche um die Y Achse in der Anzeige setzen
	 * @param rotY Drehung der Fläche um die Y Achse in der Anzeige
	 */
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	/**
	 * Drehung der Fläche um die X Achse in der Anzeige auslesen
	 * @return Drehung der Fläche um die X Achse in der Anzeige
	 */
	public float getRotX() {
		return rotX;
	}
	/**
	 * Drehung der Fläche um die X Achse in der Anzeige setzen
	 * @param rotX Drehung der Fläche um die X Achse in der Anzeige
	 */
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	/**
	 * Höhe der Fläche auslesen
	 * @return Höhe der Fläche
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Höhe der Fläche setzen
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;				
	}

	/**
	 * Breite der Fläche auslesen
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Breite der Fläche setzen
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;		
	}

	/**
	 * Textur für den Hintergrund auslesen
	 * @return Textur für den Hintergrund
	 */
	public Texture getMapTexture() {
		return mapTexture;
	}

	/**
	 * Textur für den Hintergrund setzen
	 * @param mapTexture Textur für den Hintergrund
	 */
	public void setMapTexture(Texture mapTexture) {
		this.mapTexture = mapTexture;
	}

	/**
	 * Verschiebung in X Richtung auslesen
	 * @return Verschiebung in X Richtung
	 */
	public int getTranslateX() {
		return translateX;
	}

	/**
	 * Verschiebung in X Richtung setzen
	 * @param translateX Verschiebung in X Richtung
	 */
	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	/**
	 * Verschiebung in Y Richtung auslesen
	 * @return Verschiebung in Y Richtung
	 */
	public int getTranslateY() {
		return translateY;
	}

	/**
	 * Verschiebung in Y Richtung setzen
	 * @param translateY
	 */
	public void setTranslateY(int translateY) {
		this.translateY = translateY;
	}

	/**
	 * Verschiebung der Karte in Y Richtung auslesen
	 * @return Verschiebung der Karte in Y Richtung
	 */
	public int getTranslateMapY() {
		return translateMapY;
	}

	/**
	 * Verschiebung der Karte in Y Richtung setzen
	 * @param translateMapY Verschiebung der Karte in Y Richtung
	 */
	public void setTranslateMapY(int translateMapY) {
		this.translateMapY = translateMapY;
	}
	/**
	 * Verschiebung der Karte in X Richtung auslesen
	 * @return Verschiebung der Karte in X Richtung
	 */
	public int getTranslateMapX() {
		return translateMapX;
	}

	/**
	 * Verschiebung der Karte in X Richtung setzen
	 * @param translateMapX Verschiebung der Karte in X Richtung
	 */
	public void setTranslateMapX(int translateMapX) {
		this.translateMapX = translateMapX;
	}

	/**
	 * aktuellen Zoomfaktor auslesen
	 * @return aktuellen Zoomfaktor
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * aktuellen Zoomfaktor setzen
	 * @param zoom aktuellen Zoomfaktor
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * Transparenz setzen
	 * @param blending Transparenz
	 */
	public void setBlending(float blending) {
		this.blending = blending;
	}

	/**
	 * Transparenz auslesen
	 * @return Transparenz
	 */
	public float getBlending() {
		return blending;
	}

	/**
	 * ID setzen
	 * @param id ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ID auslesen
	 * @return
	 */
	public String getId() {
		return id;
	}	
	
	/**
	 * Status (X Achse gespiegelt) auslesen
	 * @return Status (X Achse gespiegelt)
	 */
	public boolean isXMirror() {
		return xMirror;
	}

	/**
	 * Status (X Achse gespiegelt) setzen
	 * @param xMirror Status (X Achse gespiegelt)
	 */
	public void setXMirror(boolean xMirror) {
		this.xMirror = xMirror;
	}
	
	/**
	 * Status (Y Achse gespiegelt) auslesen
	 * @return Status (Y Achse gespiegelt)
	 */	
	public boolean isYMirror() {
		return yMirror;
	}
	/**
	 * Status (Y Achse gespiegelt) setzen
	 * @param yMirror Status (Y Achse gespiegelt)
	 */
	public void setYMirror(boolean yMirror) {
		this.yMirror = yMirror;
	}
	
	/**
	 * Abmessungen projezierter Kartenauschnitt setzen
	 * @param mapExtract Abmessungen projezierter Kartenauschnitt
	 */
	public void setMapExtract(ArrayList<Coordinate> mapExtract) {
		this.mapExtract = mapExtract;
	}
	
	/**
	 * Abmessungen projezierter Kartenauschnitt auslesen
	 * @return Abmessungen projezierter Kartenauschnitt
	 */
	public ArrayList<Coordinate> getMapExtract() {
		return mapExtract;
	}
	

	/**
	 * Rastergrafiken und Signaturen in der Würflefläche zeichnen
	 * 
	 * @param drawable gl Drawable 
	 * @param geoObjects Liste mit Geoobjekten
	 * @param tiles sollen Rastergrafiken gezeichnet werden
	 * @param semanticZoomLevel semantischer Zoom Level
	 * @param usecaseObjectText Textanzeige bei Objekte mit bestimmten Verwendungszweck
	 */
	public void Draw(GLAutoDrawable drawable,ArrayList<GeoObject> geoObjects,boolean tiles,int semanticZoomLevel,boolean usecaseObjectText) {

		GL gl = drawable.getGL();
		
		//Perspektivlinien zeichnen
		
		if(!this.id.equals(MapCubePanel.SURFACE_FRONT)&& !this.id.equals(MapCubePanel.SURFACE_BACK))
		{
			float lines_dist=0.01f;
			
			/*float vertices_lines[] = {this.eucldianCoords.get("bottomRight").getX(), this.eucldianCoords.get("bottomRight").getY(),this.eucldianCoords.get("bottomRight").getZ(),
					this.eucldianCoords.get("bottomLeft").getX(), this.eucldianCoords.get("bottomLeft").getY(),this.eucldianCoords.get("bottomLeft").getZ(),
					this.eucldianCoords.get("topLeft").getX(), this.eucldianCoords.get("topLeft").getY(), this.eucldianCoords.get("topLeft").getZ(),
					this.eucldianCoords.get("topRight").getX(),this.eucldianCoords.get("topRight").getY(), this.eucldianCoords.get("topRight").getZ()};
			int indicies_lines[]={0,1,2,3,0};*/
			
			float vertices_lines[] = null;
			int indicies_lines[]= null;

			if(this.id.equals(MapCubePanel.SURFACE_LEFT))
			{
				float vertices_lines_left[] = {
						this.eucldianCoords.get("topRight").getX()+lines_dist,this.eucldianCoords.get("topRight").getY()-lines_dist, this.eucldianCoords.get("topRight").getZ(),
						this.eucldianCoords.get("topLeft").getX()+lines_dist, this.eucldianCoords.get("topLeft").getY()-lines_dist, this.eucldianCoords.get("topLeft").getZ()+lines_dist,														
						this.eucldianCoords.get("bottomLeft").getX()+lines_dist, this.eucldianCoords.get("bottomLeft").getY()+lines_dist,this.eucldianCoords.get("bottomLeft").getZ()+lines_dist,
						this.eucldianCoords.get("bottomRight").getX()+lines_dist, this.eucldianCoords.get("bottomRight").getY()+lines_dist,this.eucldianCoords.get("bottomRight").getZ()
						};
				vertices_lines=vertices_lines_left;
				int indicies_lines_left[]={0,1,2,3};
				indicies_lines=indicies_lines_left;
			}
			if(this.id.equals(MapCubePanel.SURFACE_RIGHT))
			{
				float vertices_lines_left[] = {
						this.eucldianCoords.get("topRight").getX()-lines_dist,this.eucldianCoords.get("topRight").getY()-lines_dist, this.eucldianCoords.get("topRight").getZ(),
						this.eucldianCoords.get("topLeft").getX()-lines_dist, this.eucldianCoords.get("topLeft").getY()-lines_dist, this.eucldianCoords.get("topLeft").getZ()+lines_dist,														
						this.eucldianCoords.get("bottomLeft").getX()-lines_dist, this.eucldianCoords.get("bottomLeft").getY()+lines_dist,this.eucldianCoords.get("bottomLeft").getZ()+lines_dist,
						this.eucldianCoords.get("bottomRight").getX()-lines_dist, this.eucldianCoords.get("bottomRight").getY()+lines_dist,this.eucldianCoords.get("bottomRight").getZ()
						};
				vertices_lines=vertices_lines_left;
				int indicies_lines_left[]={0,1,2,3};
				indicies_lines=indicies_lines_left;
			}
			if(this.id.equals(MapCubePanel.SURFACE_TOP))
			{
				/*float vertices_lines_top[] = {
						this.eucldianCoords.get("topLeft").getX(), this.eucldianCoords.get("topLeft").getY()-lines_dist, this.eucldianCoords.get("topLeft").getZ()+lines_dist,
						this.eucldianCoords.get("topRight").getX(),this.eucldianCoords.get("topRight").getY()-lines_dist, this.eucldianCoords.get("topRight").getZ()+lines_dist};
				vertices_lines=vertices_lines_top;
				int indicies_lines_left[]={0,1};
				indicies_lines=indicies_lines_left;*/
			}
			if(this.id.equals(MapCubePanel.SURFACE_BOTTOM))
			{
				/*float vertices_lines_top[] = {
						this.eucldianCoords.get("bottomLeft").getX(), this.eucldianCoords.get("bottomLeft").getY()+lines_dist, this.eucldianCoords.get("bottomLeft").getZ()+lines_dist,
						this.eucldianCoords.get("bottomRight").getX(),this.eucldianCoords.get("bottomRight").getY()+lines_dist, this.eucldianCoords.get("bottomRight").getZ()+lines_dist};
				vertices_lines=vertices_lines_top;
				int indicies_lines_left[]={0,1};
				indicies_lines=indicies_lines_left;*/
			}
	
			if(vertices_lines!=null && indicies_lines!=null)
			{
				
				gl.glPushMatrix();
				gl.glDisable(GL.GL_LIGHTING);
				//gl.glScalef(0.99f,0.99f,0.99f);
				if(tiles)
					gl.glColor4f(0.8f, 0.0f, 0.0f,1.0f);
				else
					gl.glColor4f(0.8f, 1.0f, 1.0f,1.0f);
					
					
				gl.glLineWidth(4.0f);
				FloatBuffer buf_vertices_lines = BufferUtil.newFloatBuffer(vertices_lines.length);		
				buf_vertices_lines.put(vertices_lines);
				buf_vertices_lines.rewind();
				
				IntBuffer buf_indicies_lines= BufferUtil.newIntBuffer(indicies_lines.length);
				buf_indicies_lines.put(indicies_lines);
				buf_indicies_lines.rewind();
				
				gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
				gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices_lines);								
				gl.glDrawElements(GL.GL_LINES, indicies_lines.length, GL.GL_UNSIGNED_INT, buf_indicies_lines);
				// deactivate vertex arrays after drawing
				gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
				gl.glEnable(GL.GL_LIGHTING);
				gl.glPopMatrix();
			}
		}
		
		if(tiles)
		{
			//Rastergrafiken zeichnen
			gl.glPushMatrix();
			gl.glDisable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_TEXTURE_2D);
	
			if (!this.isEmpty()) {
				int textureType=1;
				
				ArrayList<ArrayList<Texture>> textures = MapCubeTextureProvider.getInstance().getTextures(this.displayRect);
	
				Rectangle tileRect = MapCubeTextureProvider.getInstance()
						.getTileRect(this.displayRect);
	
				int iy = tileRect.y * MapCubeTextureProvider.getInstance().getTileSize();
				for (ArrayList<Texture> hList : textures) {
					int ix = tileRect.x * MapCubeTextureProvider.getInstance().getTileSize();
					for (Texture texture : hList) {
						Rectangle tilePointRect = new Rectangle();
						tilePointRect.x = ix;
						tilePointRect.y = iy;
						tilePointRect.width = MapCubeTextureProvider.getInstance()
								.getTileSize();
						tilePointRect.height = MapCubeTextureProvider.getInstance()
								.getTileSize();
						ix += MapCubeTextureProvider.getInstance().getTileSize();
						gl.glPushMatrix();
						this.drawRect(tilePointRect, gl, texture);
						gl.glPopMatrix();
	
					}
					iy += MapCubeTextureProvider.getInstance().getTileSize();
				}
			} 	
			gl.glPopMatrix();		
		}
		else
		{
			gl.glDisable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_BLEND);
			gl.glColor4f(0.0f, 0.0f, 0.0f,blending);
						
			if(this.id.equals(MapCubePanel.SURFACE_FRONT) || this.id.equals(MapCubePanel.SURFACE_BACK))
			{
				if(this.id.equals(MapCubePanel.SURFACE_FRONT))
					gl.glColor4f(0.2f, 0.2f, 0.2f,blending);					
				
				gl.glBegin(GL.GL_POLYGON);			
				gl.glVertex3f(this.eucldianCoords.get("bottomRight").getX(), this.eucldianCoords.get("bottomRight").getY(),this.eucldianCoords.get("bottomRight").getZ());
				gl.glVertex3f(this.eucldianCoords.get("bottomLeft").getX(), this.eucldianCoords.get("bottomLeft").getY(),this.eucldianCoords.get("bottomLeft").getZ());			
				gl.glVertex3f(this.eucldianCoords.get("topLeft").getX(), this.eucldianCoords.get("topLeft").getY(), this.eucldianCoords.get("topLeft").getZ());			
				gl.glVertex3f(this.eucldianCoords.get("topRight").getX(), this.eucldianCoords.get("topRight").getY(), this.eucldianCoords.get("topRight").getZ());
				gl.glEnd();
			}

			if(!this.id.equals(MapCubePanel.SURFACE_FRONT)&& !this.id.equals(MapCubePanel.SURFACE_BACK))
			{

				
				Texture perceptionTexture=DisplayFunctions.getPerceptionTexture(gl);
				
				perceptionTexture.enable();
				perceptionTexture.bind();
				
				float texLeft = 0.0f;
				float texRight = 1.0f;
				float texTop = 0.0f;
				float texBottom = 1.0f;
				
				if(!this.id.equals(MapCubePanel.SURFACE_BACK))
				{
					
					gl.glDisable(GL.GL_LIGHTING);
					gl.glEnable(GL.GL_TEXTURE_2D);
					if(this.id.equals(MapCubePanel.SURFACE_LEFT) || this.id.equals(MapCubePanel.SURFACE_RIGHT))
						gl.glColor4f(0.6f, 0.6f, 0.6f,blending);
					else
						gl.glColor4f(1.0f, 1.0f, 1.0f,blending);
							
					float vertices[] = {this.eucldianCoords.get("bottomRight").getX(), this.eucldianCoords.get("bottomRight").getY(),this.eucldianCoords.get("bottomRight").getZ(),
							this.eucldianCoords.get("bottomLeft").getX(), this.eucldianCoords.get("bottomLeft").getY(),this.eucldianCoords.get("bottomLeft").getZ(),
							this.eucldianCoords.get("topLeft").getX(), this.eucldianCoords.get("topLeft").getY(), this.eucldianCoords.get("topLeft").getZ(),
							this.eucldianCoords.get("topRight").getX(),this.eucldianCoords.get("topRight").getY(), this.eucldianCoords.get("topRight").getZ()};
			
					float tex[]=null;
					if(this.id.equals(MapCubePanel.SURFACE_TOP))
					{
						float tex_top[]={texLeft, texTop,texRight, texTop,texRight, texBottom,texLeft, texBottom};
						tex=tex_top;
					}
					else if(this.id.equals(MapCubePanel.SURFACE_BOTTOM))
					{
						float tex_bottom[]={texRight, texBottom,texLeft, texBottom,texLeft, texTop,texRight, texTop};
						tex=tex_bottom;
					}
					else if(this.id.equals(MapCubePanel.SURFACE_LEFT))
					{
						float tex_left[]={texRight, texTop,texRight, texBottom,texLeft, texBottom,texLeft, texTop};
						tex=tex_left;
					}
					else if(this.id.equals(MapCubePanel.SURFACE_RIGHT))
					{
						float tex_right[]={texLeft, texBottom,texLeft, texTop,texRight, texTop,texRight, texBottom};
						tex=tex_right;
					}
					
					int indicies[]={0,1,2,3,0};
					
					FloatBuffer buf_vertices = BufferUtil.newFloatBuffer(vertices.length);		
					buf_vertices.put(vertices);
					buf_vertices.rewind();
			
					FloatBuffer buf_tex = BufferUtil.newFloatBuffer(tex.length);		
					buf_tex.put(tex);
					buf_tex.rewind();
					
					IntBuffer buf_indicies= BufferUtil.newIntBuffer(indicies.length);
					buf_indicies.put(indicies);
					buf_indicies.rewind();
					
					gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL.GL_TEXTURE_COORD_ARRAY);				
					gl.glVertexPointer(3, GL.GL_FLOAT, 0, buf_vertices);
					gl.glTexCoordPointer(2,GL.GL_FLOAT, 0,buf_tex);				
					gl.glDrawElements(GL.GL_POLYGON, 5, GL.GL_UNSIGNED_INT, buf_indicies);
					// deactivate vertex arrays after drawing
					gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL.GL_TEXTURE_COORD_ARRAY);
					
					
					gl.glEnable(GL.GL_LIGHTING);
					gl.glDisable(GL.GL_TEXTURE_2D);
				}
				
			}

			
			gl.glDisable(GL.GL_BLEND);
		}
		
		gl.glPushMatrix();
		if(this.id.equals(MapCubePanel.SURFACE_FRONT))
			gl.glDisable(GL.GL_LIGHTING);
		else
			gl.glEnable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE);
				
		//Signaturn zeichnen
		if(this.id!=MapCubePanel.SURFACE_BACK)		
			this.drawSurfaceObjects(drawable,geoObjects,semanticZoomLevel,usecaseObjectText,tiles);

		
		gl.glEnable(GL.GL_TEXTURE);
		gl.glPopMatrix();
	}
	
	/**
	 * Transparenz (OpenGL) einstellen
	 * @param gl GL Kontext
	 * @param black transparente Farbe schwarz oder weiss
	 */
	private void blend(GL gl,boolean black)
	{
		if (this.blending < 1.0f) { // Blending aktiv bie der Fläche?
			gl.glDisable(gl.GL_LIGHTING);
			gl.glEnable(gl.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			if(black)
				gl.glColor4f(0.0f, 0.0f, 0.0f, blending);
			else				
				gl.glColor4f(1.0f, 1.0f, 1.0f, blending);
		} else {
			gl.glDisable(gl.GL_BLEND);
			if(black)
				gl.glColor4f(0.0f, 0.0f, 0.0f,  1.0f);
			else	
				gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	
	/**
	 * Transparenz (OpenGL) abstellen
	 * @param gl
	 */
	private void unblend(GL gl)
	{
		// Blending ggf. wieder ausschalten
		if (this.blending < 1.0f) {
			gl.glDisable(gl.GL_BLEND);
			gl.glEnable(gl.GL_LIGHTING);
			gl.glEnable(gl.GL_DEPTH_TEST);
		}		
	}

	/**
	 * ein Tile einer Rastergrafik zeichnen
	 * @param tileRect Abmessungen der 
	 * @param gl OpenGL Kontext
	 * @param texture Textur mit Rastergrafik
	 */
	private void drawRect(Rectangle tileRect, GL gl, Texture texture) {
		// Textur binden
		texture.enable();
		texture.bind();
		
		this.blend(gl,false);

		gl.glBegin(GL.GL_QUADS);

		float XdifX = this.eucldianCoords.get("topRight").getX() - this.eucldianCoords.get("topLeft").getX();
		float XdifY = this.eucldianCoords.get("topRight").getY() - this.eucldianCoords.get("topLeft").getY();
		float XdifZ = this.eucldianCoords.get("topRight").getZ() - this.eucldianCoords.get("topLeft").getZ();

		float YdifX = this.eucldianCoords.get("bottomLeft").getX() - this.eucldianCoords.get("topLeft").getX();
		float YdifY = this.eucldianCoords.get("bottomLeft").getY() - this.eucldianCoords.get("topLeft").getY();
		float YdifZ = this.eucldianCoords.get("bottomLeft").getZ() - this.eucldianCoords.get("topLeft").getZ();

		float relStartX = (float) (tileRect.x - displayRect.x)
				/ (float) this.displayRect.width;
		float relStartY = (float) (tileRect.y - displayRect.y)
				/ (float) this.displayRect.height;
		float relEndX = (float) (tileRect.x + tileRect.width - displayRect.x)
				/ (float) this.displayRect.width;
		float relEndY = (float) (tileRect.y + tileRect.height - displayRect.y)
				/ (float) this.displayRect.height;
		
		float xfactor=tileRect.width /(float) this.displayRect.width;
		float yfactor=tileRect.height /(float) this.displayRect.height;

		// Berchnung relative Koordinaten des Texturausschnitt
		float texLeft = 0.0f;
		float texRight = 1.0f;
		float texTop = 0.0f;
		float texBottom = 1.0f;

		if (relStartX < 0) {
			texLeft = relStartX * (-1)/xfactor;
			relStartX = 0;
		}
		if (relStartY < 0) {
			texTop = relStartY * (-1)/yfactor;
			relStartY = 0;
		}
		if (relEndX > 1.0) {
			texRight =(xfactor-(relEndX-1.0f))/xfactor;
			relEndX = 1.0f;
		}
		if (relEndY > 1.0) {
			texBottom =(yfactor-(relEndY-1.0f))/yfactor;
			relEndY = 1.0f;
		}
		float x, y, z;

		x = this.eucldianCoords.get("topLeft").getX() + (relStartX * XdifX) + (relStartY * YdifX);
		y = this.eucldianCoords.get("topLeft").getY() + (relStartX * XdifY) + (relStartY * YdifY);
		z = this.eucldianCoords.get("topLeft").getZ() + (relStartX * XdifZ) + (relStartY * YdifZ);

		Point3D newTopLeft = new Point3D(x, y, z);

		x = this.eucldianCoords.get("topLeft").getX() + (relEndX * XdifX) + (relStartY * YdifX);
		y = this.eucldianCoords.get("topLeft").getY() + (relEndX * XdifY) + (relStartY * YdifY);
		z = this.eucldianCoords.get("topLeft").getZ() + (relEndX * XdifZ) + (relStartY * YdifZ);

		Point3D newTopRight = new Point3D(x, y, z);

		x = this.eucldianCoords.get("topLeft").getX() + (relStartX * XdifX) + (relEndY * YdifX);
		y = this.eucldianCoords.get("topLeft").getY() + (relStartX * XdifY) + (relEndY * YdifY);
		z = this.eucldianCoords.get("topLeft").getZ() + (relStartX * XdifZ) + (relEndY * YdifZ);

		Point3D newBottomLeft = new Point3D(x, y, z);

		x = this.eucldianCoords.get("topLeft").getX() + (relEndX * XdifX) + (relEndY * YdifX);
		y = this.eucldianCoords.get("topLeft").getY() + (relEndX * XdifY) + (relEndY * YdifY);
		z = this.eucldianCoords.get("topLeft").getZ() + (relEndX * XdifZ) + (relEndY * YdifZ);

		Point3D newBottomRight = new Point3D(x, y, z);

		// Textur zeichnen
		gl.glTexCoord2f(texRight, texBottom);
		gl.glVertex3f(newBottomRight.getX(), newBottomRight.getY(),
				newBottomRight.getZ());
		gl.glTexCoord2f(texLeft, texBottom);
		gl.glVertex3f(newBottomLeft.getX(), newBottomLeft.getY(), newBottomLeft
				.getZ());
		gl.glTexCoord2f(texLeft, texTop);
		gl.glVertex3f(newTopLeft.getX(), newTopLeft.getY(), newTopLeft.getZ());
		gl.glTexCoord2f(texRight, texTop);
		gl.glVertex3f(newTopRight.getX(), newTopRight.getY(), newTopRight
				.getZ());

		/*
		 * gl.glTexCoord2f(right, bottom); gl.glVertex3f(newBottomRight.getX(),
		 * newBottomRight.getY(),newBottomRight.getZ()); gl.glTexCoord2f(left,
		 * bottom); gl.glVertex3f(newBottomLeft.getX(),
		 * newBottomLeft.getY(),newBottomLeft.getZ()); gl.glTexCoord2f(left,
		 * top); gl.glVertex3f(newTopLeft.getX(), newTopLeft.getY(),
		 * newTopLeft.getZ()); gl.glTexCoord2f(right, top);
		 * gl.glVertex3f(newTopRight.getX(), newTopRight.getY(),
		 * newTopRight.getZ());
		 */

		texture.disable();

		gl.glDisable(gl.GL_TEXTURE);

		gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

		this.unblend(gl);

		gl.glEnd();

	}
	
	/**
	 * GL Position anhand der Position/Drehung der Würfelfläche drehen und positionieren
	 * @param drawable
	 */
	public void positionGLToSurface(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glTranslated(this.eucldianCoords.get("topLeft").getX(), this.eucldianCoords.get("topLeft").getY(), this.eucldianCoords.get("topLeft")
				.getZ());
		gl.glRotatef((float) rotY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef((float) rotX, 1.0f, 0.0f, 0.0f);
		
	}
	
	/**
	 * Liste mit Geoobjekten (mit Signaturen) in der Würfelfläche zeichnen 
	 * @param drawable drawable OpenGL
	 * @param geoObjects List mit Geoobjekten
	 * @param semanticZoomLevel semntischer Zoom 
	 * @param usecaseObjectText Textanzeige bei Objekte mit bestimmten Verwendungszweck
	 * @param tiles sollen Rastergrafiken gezeichnet werden
	 */
	private void drawSurfaceObjects(GLAutoDrawable drawable,ArrayList<GeoObject> geoObjects,int semanticZoomLevel,boolean usecaseObjectText,boolean tiles) {

		GL gl = drawable.getGL();
		
		this.positionGLToSurface(drawable);
		gl.glTranslatef(0.0f, 0.0f, 0.002f);
			
		for (GeoObject geoObject : geoObjects) {
			if(!tiles || !geoObject.getUse().equals(GeoObject.USE_MAP))
			{
				AbstractSignature signature=null;		
				SignatureDrawer.getInstance().setWireframes(this.wireframe);
				if(this.id.equals(MapCubePanel.SURFACE_LEFT) || this.id.equals(MapCubePanel.SURFACE_RIGHT) || this.id.equals(MapCubePanel.SURFACE_BOTTOM) || this.id.equals(MapCubePanel.SURFACE_TOP))
				{
					signature=geoObject.getContextSignature();
					
					if(!geoObject.isHighlighed())
					{
						SignatureDrawer.getInstance().setGrayout(true);				
						FontDrawer.setGrayout(true);
					}
					else
					{
						SignatureDrawer.getInstance().setGrayout(false);				
						FontDrawer.setGrayout(false);
					}
						
				}
				else
				{					
					signature=geoObject.getFocusSignature();
					SignatureDrawer.getInstance().setGrayout(false);
					FontDrawer.setGrayout(false);
				}
				
				if(geoObject.getGeometry()!=null && this.isGeoObjectVisible(geoObject))
				{					
					ArrayList<Coordinate[]> clippedCoords=null;
					/*if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_RIVER))
					{
						clippedCoords=new ArrayList<Coordinate[]>();
						ArrayList<Coordinate> river_coords=new ArrayList<Coordinate>(); 
						for(int i_coord=0;i_coord<geoObject.getGeometry().getCoordinates().length;i_coord++)
						{
							if(i_coord==geoObject.getGeometry().getCoordinates().length-1 || ( this.isCoordinateVisible(geoObject.getGeometry().getCoordinates()[i_coord]) || this.isCoordinateVisible(geoObject.getGeometry().getCoordinates()[i_coord+1])))
								river_coords.add(geoObject.getGeometry().getCoordinates()[i_coord]);
						}
						Coordinate[] river_coords_arr=new Coordinate[river_coords.size()];
						int i_coord=0;
						for(Coordinate river_coord:river_coords)
						{
							river_coords_arr[i_coord]=river_coord;
							i_coord++;
						}
						
						clippedCoords.add(river_coords_arr);
						
					}
					else*/
						clippedCoords=this.clipGeoobject(geoObject);
					
					
					for(int ci=0;ci<clippedCoords.size();ci++)
					{
						Point2D[] signaturePoints=this.geometryToSurfacePoints(clippedCoords.get(ci), this.xMirror, this.yMirror);
						if(this.semanticZoom(semanticZoomLevel, false, geoObject))
						{
							gl.glLoadName(geoObject.getId());				
							//Signatur zeichnen
							gl.glPushMatrix();
							this.blend(gl,false);
							gl.glTranslatef(0.0f, 0.0f,signature.getZ());
							SignatureDrawer.getInstance().setBlending(this.blending);
							SignatureDrawer.getInstance().draw(signature,signaturePoints, drawable,this.getMappingRect());							
							this.unblend(gl);
							gl.glPopMatrix();
						}
					}
					Point2D[] signaturePoints=this.geometryToSurfacePoints(geoObject.getGeometry(), this.xMirror, this.yMirror);
					
					//Text zeichnen
					if(!MapCubePanel.getInstance().isNoScriptGeoObject(geoObject) && ((geoObject.getName().indexOf("Stau")!=-1 && !this.id.equals(MapCubePanel.SURFACE_FRONT)) || this.semanticZoom(semanticZoomLevel, true, geoObject) || this.usecaseObjectText(usecaseObjectText, geoObject,semanticZoomLevel)))
					{
						gl.glDisable(GL.GL_LIGHTING);
						gl.glPushMatrix();
						this.blend(gl,false);
						//if(this.id==MapCubePanel.SURFACE_FRONT)
						//{
						if(!this.id.equals(MapCubePanel.SURFACE_FRONT))
						{
							FontDrawer.linewidth=1.5f;
							FontDrawer.scale=0.0006f;
							if(geoObject.isHighlighed())
							{
								//FontDrawer.linewidth=1.5f;
								//FontDrawer.scale=0.0012f;							
							}
						}
						
							if (geoObject.getGeometry() instanceof LineString && !geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE))
							{
								if(MapCubePanel.getInstance().isWithSignGeoObject(geoObject) && !this.id.equals(MapCubePanel.SURFACE_FRONT))
								{									
									FontDrawer.drawPointTextWithSign(geoObject.getName(), this.getCartesianPointFromGeoPoint(((LineString)geoObject.getGeometry()).getCentroid().getCoordinate()),
											drawable, signature.getColor(), signature.getBrightness(),this.blending,this.id);
									
								}
								else
									FontDrawer.drawLineText(geoObject.getName(), signaturePoints, drawable, signature.getColor(), signature.getBrightness(),this.blending);
							}
							if (geoObject.getGeometry() instanceof Point)
							{
								
								if(geoObject.getUse().equals(GeoObject.USE_USERTEST_LOCALIZATION))
								{
									//FontDrawer.linewidth=1.5f;
									//FontDrawer.scale=0.0012f;
									//gl.glTranslatef((float)0.0f,(float)-0.2f, 0.0f);
								}
								if(MapCubePanel.getInstance().isWithSignGeoObject(geoObject) && !this.id.equals(MapCubePanel.SURFACE_FRONT))
									FontDrawer.drawPointTextWithSign(geoObject.getName(), signaturePoints[0], drawable, signature.getColor(), signature.getBrightness(),this.blending,this.id);
								else
									FontDrawer.drawPointText(geoObject.getName(), signaturePoints[0], drawable, signature.getColor(), signature.getBrightness(),this.blending);
								
								if(geoObject.getUse().equals(GeoObject.USE_USERTEST_NAVIGATION_PROACTIVE))
								{
									gl.glTranslatef(0.0f, -0.12f, 0.0f);
									float distance=MapCubePanel.getInstance().getRealDistance(geoObject);
									distance=(float)Math.round(distance*100.0f)*0.01f;
									String distStr=String.valueOf(distance);
									if(distStr.split("\\.")[1].length()>2)
										distStr=distStr.split("\\.")[0]+"."+distStr.split("\\.")[1].substring(0, 2);															
									//FontDrawer.linewidth=1.5f;
									//FontDrawer.scale=0.0012f;
									//FontDrawer.drawPointText("Entfernung "+distStr+" km", signaturePoints[0], drawable, signature.getColor(), signature.getBrightness(),this.blending);
								}
							}
							if (geoObject.getGeometry() instanceof Polygon || geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE))
							{
								gl.glPushMatrix();
								if(!this.id.equals(MapCubePanel.SURFACE_FRONT) && geoObject.getContextSignature().getShapeType()==Signature3D.SHAPE_POLYGONPRISM)
									gl.glTranslatef(0.0f, 0.0f, ((Signature3D)geoObject.getContextSignature()).getHeight()+0.021f);
								
								if(MapCubePanel.getInstance().isWithSignGeoObject(geoObject) && !this.id.equals(MapCubePanel.SURFACE_FRONT))
									FontDrawer.drawPolygonTextWithSign(geoObject.getName(), signaturePoints, drawable, signature.getColor(), signature.getBrightness(),this.blending,this.id);
								else
								{
									Color polycolor= signature.getColor();
									if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE))
										polycolor=polycolor.brighter().brighter().brighter().brighter();
									FontDrawer.drawPolygonText(geoObject.getName(), signaturePoints, drawable,polycolor, signature.getBrightness(),this.blending);
								}
								
								gl.glPopMatrix();
							}
						//}
						this.unblend(gl);
						gl.glPopMatrix();
					}
					FontDrawer.setGrayout(false);
				}
			}
		}
		
	}

	/**
	 * Umwandlung einer Geokoordinate in eine kartesiche Koordinate (ggf. mit Spiegelung an der X oder Y Achse)
	 * bei einer Projektion auf eine rechteckigen Fläche
	 * @param coordinate Geokoordinate
	 * @param xMirror Spiegelung an der X Achse
	 * @param yMirror Spiegelung an der Y Achse
	 * @return
	 */
	private Point2D getCartesianPointFromGeoPointLinear(Coordinate coordinate,boolean xMirror,boolean yMirror) {
		
		double[] vx=new double[2];
		double[] vy=new double[2];
		
		vx[0]=this.mapExtract.get(1).x-this.mapExtract.get(0).x;
		vx[1]=this.mapExtract.get(1).y-this.mapExtract.get(0).y;

		vy[0]=this.mapExtract.get(3).x-this.mapExtract.get(0).x;
		vy[1]=this.mapExtract.get(3).y-this.mapExtract.get(0).y;
		
		double[][] A=new double[2][2];
		A[0]=new double[]{vx[0],vy[0]};
		A[1]=new double[]{vx[1],vy[1]};
		
		Matrix mA=new Matrix(A);
		
		double[] B=new double[]{coordinate.x-this.mapExtract.get(0).x,coordinate.y-this.mapExtract.get(0).y};
		Matrix mB=new Matrix(B,2);
		
		Matrix mRel=mA.solve(mB);
					
		double relX=mRel.get(0, 0);
		double relY=mRel.get(1, 0);
		
		relX = relX * this.width;
		relY = relY * this.height;
		
		float xFactor = 1.0f;
		if (xMirror)
			xFactor = -1.0f;
		float yFactor = -1.0f;
		if (yMirror)
			yFactor = 1.0f;

		Point2D relPoint = new Point2D.Double(relX, relY);
		relPoint.setLocation(relPoint.getX() * xFactor, relPoint.getY()
				* yFactor);

		return relPoint;
	}
	
	/**
	 * Umwandlung einer Geokoordinate in eine kartesiche Koordinate (ggf. mit Spiegelung an der X oder Y Achse)
	 * bei einer Projektion auf eine trapezförmige Fläche
	 * @param coordinate Geokoordinate
	 * @param xMirror Spiegelung an der X Achse
	 * @param yMirror Spiegelung an der Y Achse
	 * @return
	 */
	private Point2D getCartesianPointFromGeoPointTrapez(Coordinate coordinate,boolean xMirror,boolean yMirror) 
	{
		String _bmatrix_key=this.mapExtract.get(1).x+"_"+this.mapExtract.get(0).y+"_"+this.mapExtract.get(1).x+"_"+this.mapExtract.get(1).y;
		_bmatrix_key=_bmatrix_key+"_"+this.mapExtract.get(2).x+"_"+this.mapExtract.get(2).y+"_"+this.mapExtract.get(3).x+"_"+this.mapExtract.get(3).y;
		
		Matrix m=null;
		
		if(this.bmatrix_key!=null && this.bmatrix_key.equals(_bmatrix_key))
		{
			m=this.bmatrix;
		}
		else
		{
			double[][] A=new double[8][4];
			
			double u0=this.mapExtract.get(0).x;		
			double v0=this.mapExtract.get(0).y;
			double u1=this.mapExtract.get(1).x;
			double v1=this.mapExtract.get(1).y;
			double u2=this.mapExtract.get(2).x;
			double v2=this.mapExtract.get(2).y;
			double u3=this.mapExtract.get(3).x;
			double v3=this.mapExtract.get(3).y;
					
			if(xMirror)
			{
				u0=this.mapExtract.get(0).y;		
				v0=this.mapExtract.get(0).x;
				u1=this.mapExtract.get(1).y;
				v1=this.mapExtract.get(1).x;
				u2=this.mapExtract.get(2).y;
				v2=this.mapExtract.get(2).x;
				u3=this.mapExtract.get(3).y;
				v3=this.mapExtract.get(3).x;
			}
	
			double x0=0;
			double y0=0;
			double x1=1;
			double y1=0;
			double x2=1;
			double y2=1;
			double x3=0;
			double y3=1;
			
			if(xMirror)
			{
				x0=0;
				y0=0;
				x1=0;
				y1=1;
				x2=1;
				y2=1;
				x3=1;
				y3=0;
			}
			
			A[0]=new double[]{u0,v0,1,0,0,0,-u0*-x0,-v0*x0};
			A[1]=new double[]{u1,v1,1,0,0,0,-u1*-x1,-v1*x1};
			A[2]=new double[]{u2,v2,1,0,0,0,-u2*-x2,-v2*x2};
			A[3]=new double[]{u3,v3,1,0,0,0,-u3*-x3,-v3*x3};
			A[4]=new double[]{0,0,0,u0,v0,1,-u0*-y0,-v0*y0};
			A[5]=new double[]{0,0,0,u1,v1,1,-u1*-y1,-v1*y1};
			A[6]=new double[]{0,0,0,u2,v2,1,-u2*-y2,-v2*y2};
			A[7]=new double[]{0,0,0,u3,v3,1,-u3*-y3,-v3*y3};
			
			Matrix MA=new Matrix(A); 
			
			double[] B=new double[]{x0,x1,x2,x3,y0,y1,y2,y3};		
			Matrix MB = new Matrix(B,8);
			
			m=MA.solve(MB);
			this.bmatrix_key=_bmatrix_key;
			this.bmatrix=m;
			
		}
		
		double a=m.get(0, 0);
		double b=m.get(1, 0);
		double c=m.get(2, 0);
		double d=m.get(3, 0);
		double e=m.get(4, 0);
		double f=m.get(5, 0);
		double g=m.get(6, 0);
		double h=m.get(7, 0);
		
		//projizieren
		
		float xFactor = 1.0f;
		if (xMirror)
			xFactor = -1.0f;
		float yFactor = -1.0f;
		if (yMirror)
			yFactor = 1.0f;

		/*double[][] C=new double[2][2];
		if(xMirror)
		{
			C[0]=new double[]{coordinate.y*g-a,coordinate.y*h-b};
			C[1]=new double[]{coordinate.x*g-d,coordinate.x*h-e};		
		}
		else
		{
			C[0]=new double[]{coordinate.x*g-a,coordinate.x*h-b};
			C[1]=new double[]{coordinate.y*g-d,coordinate.y*h-e};		
		}
		
		Matrix MC=new Matrix(C);
		
		double[] D=new double[2];
		if(xMirror)
			D=new double[]{c-coordinate.y,f-coordinate.x};
		else
			D=new double[]{c-coordinate.x,f-coordinate.y};
		Matrix MD=new Matrix(D,2);
		
		Matrix n=MC.solve(MD);
		
		if(xMirror)
			return new Point2D.Double(n.get(1, 0)*this.width*xFactor,n.get(0, 0)*this.height*yFactor);
		else
			return new Point2D.Double(n.get(0, 0)*this.width*xFactor,n.get(1, 0)*this.height*yFactor);*/

		double _x=0;
		double _y=0;
		
		if(xMirror)
		{
			_x=(d*coordinate.y+e*coordinate.x+f)/(g*coordinate.y+h*coordinate.x+1);
			_x=_x*this.width*xFactor;
			
		}
		else
		{
			_x=(a*coordinate.x+b*coordinate.y+c)/(g*coordinate.x+h*coordinate.y+1);
			_x=_x*this.width*xFactor;
		}

		if(xMirror)
		{
			_y=(a*coordinate.y+b*coordinate.x+c)/(g*coordinate.y+h*coordinate.x+1);
			_y=_y*this.height*yFactor;

		}
		else
		{
			_y=(d*coordinate.x+e*coordinate.y+f)/(g*coordinate.x+h*coordinate.y+1);
			_y=_y*this.height*yFactor;
		}
				
		return new Point2D.Double(_x,_y);
	}
	
	/**
	 * Umwandlung einer Geokoordinate in eine kartesiche Koordinate (ggf. mit Spiegelung an der X oder Y Achse)
	 * @param coordinate Geokoordinate
	 * @param xMirror Spiegelung an der X Achse
	 * @param yMirror Spiegelung an der Y Achse
	 * @return
	 */
	public Point2D getCartesianPointFromGeoPoint(Coordinate coordinate,boolean xMirror,boolean yMirror)
	{
		if(MapCubePanel.getInstance().getVisualization_type().equals("VECTOR_TRAPEZ") || MapCubePanel.getInstance().getVisualization_type().equals("VECTOR_TRAPEZ_LARGE"))
			return this.getCartesianPointFromGeoPointTrapez(coordinate, xMirror, yMirror);
		else
			return this.getCartesianPointFromGeoPointLinear(coordinate, xMirror, yMirror);
	}
	
	
	/**
	 * Umwandlung einer Geokoordinate in eine kartesische Koordinate 
	 * @param coordinate Geokoordinate
	 * @return
	 */
	public Point2D getCartesianPointFromGeoPoint(Coordinate coordinate) {
		return this.getCartesianPointFromGeoPoint(coordinate,this.xMirror,this.yMirror);
	}
	
	/**
	 * Umwandlung einer Geometrie in eine Liste von kartesischen Punkten (ggf. mit Spiegelung an der X oder Y Achse)
	 * @param geometry Geometrie
	 * @param xMirror Spiegelung an der X Achse
	 * @param yMirror Spiegelung an der Y Achse
	 * @return
	 */
	public Point2D[] geometryToSurfacePoints(Geometry geometry,boolean xMirror,boolean yMirror)
	{		
		return this.geometryToSurfacePoints(geometry.getCoordinates(), xMirror, yMirror);
	}
	
	/**
	 * Umwandlung einer Liste von Geokoordinaten in eine Liste von kartesischen Punkten (ggf. mit Spiegelung an der X oder Y Achse)
	 * @param coordinates Liste von Geokoordinaten
	 * @param xMirror Spiegelung an der X Achse
	 * @param yMirror Spiegelung an der Y Achse
	 * @return
	 */
	public Point2D[] geometryToSurfacePoints(Coordinate[] coordinates,boolean xMirror,boolean yMirror)
	{				
		Point2D[] points=new Point2D[coordinates.length];
		for(int i=0;i<coordinates.length;i++)
		{
			points[i]=this.getCartesianPointFromGeoPoint(coordinates[i], xMirror, yMirror);
		}
		return points;
	}
	
	/**
	 * Prüfenm, ob das Geoobjekt sichtbar ist in der Würfelfläche
	 * @param geoObject Geoobjekt
	 * @return Geoobjekt sichtbar
	 */
	public boolean isGeoObjectVisible(GeoObject geoObject)	
	{
		Coordinate[] coordinates=new Coordinate[5];		
		coordinates[0]=this.getMapExtract().get(0);
		coordinates[1]=this.getMapExtract().get(1);
		coordinates[2]=this.getMapExtract().get(2);
		coordinates[3]=this.getMapExtract().get(3);
		coordinates[4]=this.getMapExtract().get(0);
		
		Polygon hull=null;
		try
		{
			hull=new GeometryFactory().createPolygon(new GeometryFactory().createLinearRing(coordinates),null);
		}
		catch(Exception ex)
		{
			ex=ex;
			
		}
		if(hull!=null && (hull.contains(geoObject.getGeometry()) || geoObject.getGeometry().crosses(hull) || hull.intersects(geoObject.getGeometry()) || geoObject.getGeometry().within(hull)))
			return true;
		return false;
	}
	
	/**
	 * Die Geomterie des Geoobjekts beschneiden, so dass diese nicht aus Würfelfläche hinausragt
	 * @param geoObject Geoobjekt
	 * @return Liste mit Geokoordinaten
	 */
	public ArrayList<Coordinate[]> clipGeoobject(GeoObject geoObject)	
	{		
		/*ArrayList<Coordinate[]> al_c=new ArrayList<Coordinate[]>();
		al_c.add(geoObject.getGeometry().getCoordinates());
		return al_c;*/
		
		if(geoObject.getGeometry() instanceof Point)
		{
			ArrayList<Coordinate[]> clippedCoords=new ArrayList<Coordinate[]>();
			clippedCoords.add(geoObject.getGeometry().getCoordinates());
			return clippedCoords;						
		}
		Coordinate[] coordinates=new Coordinate[5];		
		coordinates[0]=this.getMapExtract().get(0);
		coordinates[1]=this.getMapExtract().get(1);
		coordinates[2]=this.getMapExtract().get(2);
		coordinates[3]=this.getMapExtract().get(3);
		coordinates[4]=this.getMapExtract().get(0);
		
		ArrayList<Coordinate[]> clippedCoords=new ArrayList<Coordinate[]>();
		
		Polygon hull=new GeometryFactory().createPolygon(new GeometryFactory().createLinearRing(coordinates),null);
		Geometry clipGeo=hull.intersection(geoObject.getGeometry());
		if(clipGeo instanceof GeometryCollection)
		{
			GeometryCollection clipGeoCollection=(GeometryCollection)clipGeo;
			for(int i=0;i<clipGeoCollection.getNumGeometries();i++)
			{
				clippedCoords.add(clipGeoCollection.getGeometryN(i).getCoordinates());
			}
		}
		else
			clippedCoords.add(clipGeo.getCoordinates());
		
		return clippedCoords;
	}	

	/**
	 * Prüfen, ob die Geeokoordinate in der Würfelfläche sichtbar ist
	 * @param coordinate Geeokoordinate
	 * @return
	 */
	public boolean isCoordinateVisible(Coordinate coordinate)	
	{
		Coordinate[] coordinates=new Coordinate[5];		
		coordinates[0]=this.getMapExtract().get(0);
		coordinates[1]=this.getMapExtract().get(1);
		coordinates[2]=this.getMapExtract().get(2);
		coordinates[3]=this.getMapExtract().get(3);
		coordinates[4]=this.getMapExtract().get(0);
		
		Polygon hull=new GeometryFactory().createPolygon(new GeometryFactory().createLinearRing(coordinates),null);
		Point point=new GeometryFactory().createPoint(coordinate);
		if(hull.contains(point))
			return true;
		return false;
	}	
	
	

	/**
	 * prüfen, ob für Objekt mit bestimmten Verwendungszweck ein Text angeziegt werden soll
	 * @param usecaseObjectText
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean usecaseObjectText(boolean usecaseObjectText,GeoObject geoObject,int semanticZoomLevel)
	{
		if(semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_0)
			return false;
		String[] ut_types=new String[]{GeoObject.OBJTYPE_FUELSTATION,GeoObject.OBJTYPE_HOTEL,GeoObject.OBJTYPE_PERSON,GeoObject.OBJTYPE_RESTAURANT,GeoObject.OBJTYPE_SIGHTSEEING,
									 GeoObject.OBJTYPE_RESTINGPLACE,GeoObject.OBJTYPE_TOURISTREGION,GeoObject.OBJTYPE_TRAINSTATION};
		for(String ut_type:ut_types)
		{
			if(ut_type.equals(geoObject.getObjtype()))
				return true;
		}
		return false;
	}

	/**
	 * die Koordinaten für das euklidische Koordinatensystem setzen
	 * @param eucldianCoords euklidische Koordinaten
	 */
	public void setEucldianCoords(Hashtable<String,Point3D> eucldianCoords) {
		this.eucldianCoords = eucldianCoords;
	}
	
	/**
	 * die Koordinaten für das euklidische Koordinatensystem abrufen
	 * @return euklidische Koordinaten
	 */
	public Hashtable<String,Point3D> getEucldianCoords() {
		return eucldianCoords;
	}
	
	/**
	 * Status( Signaturen Drahtgitter) abfragen
	 * @return Status( Signaturen Drahtgitter) 
	 */
	public boolean isWireframe() {
		return wireframe;
	}
	
	/**
	 * Status( Signaturen Drahtgitter) setzen
	 * @param wireframe Status( Signaturen Drahtgitter)
	 */
	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
	
	/**
	 * Liste der Koordinaten, die die Würfelfläche beschreiben, abrufen
	 * @return Liste der Koordinaten, die die Würfelfläche beschreiben
	 */
	private ArrayList<Coordinate> getMappingRect()
	{
		ArrayList<Coordinate> coords=new ArrayList<Coordinate>();
		float xFactor = 1.0f;
		if (xMirror)
			xFactor = -1.0f;
		float yFactor = -1.0f;
		if (yMirror)
			yFactor = 1.0f;
		
		Coordinate c0=new Coordinate(0,0);
		coords.add(c0);
		
		Coordinate c1=new Coordinate(xFactor*this.width,yFactor*this.height);
		coords.add(c1);
		
		return coords;		
	}
	
	/**
	 * Prüfen anhand des semantischen Zoomlevel, ob das Geoobjekt in der Würflefläche sichtbar ist
	 * @param semanticZoomLevel semantischen Zoomlevel
	 * @param text
	 * @param geoObject Geoobjekt
	 * @return Objekt sichtbar
	 */
	public boolean semanticZoom(int semanticZoomLevel,boolean text,GeoObject geoObject)
	{
		int _semanticZoomLevel=semanticZoomLevel;
		if(MapCubePanel.getInstance().getVisualization_type().equals("VECTOR_TRAPEZ_LARGE"))
		{
			if(this.id.equals(MapCubePanel.SURFACE_LEFT) || this.id.equals(MapCubePanel.SURFACE_RIGHT) ||  this.id.equals(MapCubePanel.SURFACE_TOP) || this.id.equals(MapCubePanel.SURFACE_BOTTOM))
			{
				if(_semanticZoomLevel!=MapCubePanel.SEMANTIC_ZOOM_0)
					_semanticZoomLevel--;
			}
		}		
		
		if(!text)
		{
			if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_0)
			{
				if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_PARK) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_STREET)
				   || geoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_FUELSTATION)
				   || geoObject.getObjtype().equals(GeoObject.OBJTYPE_PERSON) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_RESTAURANT)
				   || geoObject.getObjtype().equals(GeoObject.OBJTYPE_RESTINGPLACE) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_TOURISTREGION)
				   || geoObject.getObjtype().equals(GeoObject.OBJTYPE_TRAINSTATION))
					return false;
			}
			else if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_1)
			{
				return true;
			}
			else if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_2)
			{
				return true;
			}
		}
		else
		{
			if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_0)
			{
				if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_FREEWAY))
					return true;
				return false;
			}
			else if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_1)
			{
				if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_FREEWAY))
					return true;
				return false;
			}
			else if(_semanticZoomLevel==MapCubePanel.SEMANTIC_ZOOM_2)
			{
				if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_FREEWAY) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_STREET) || geoObject.getObjtype().equals(GeoObject.OBJTYPE_PARK))
					return true;
				return false;	
			}
			
			
		}
		return true;
	}
	

	
}
