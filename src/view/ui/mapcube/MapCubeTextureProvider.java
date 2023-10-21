package view.ui.mapcube;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.TimerTask;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Tile;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


/**
 * lädt die Rastergrafiken von einem Tile Provider
 * erzeugt die OpenGL Texturen
 * 
 */
public class MapCubeTextureProvider extends TimerTask {

	public static final int MAXBUFFERTILES = 30;
	public static final int MINBUFFERTILES=6;
	
	public static final int BUFFERTYPE_NORMAL=1;
	public static final int BUFFERTYPE_BLUR=2;
	public static final int BUFFERTYPE_BLACKWHITE=3;
	public static final int BUFFERTYPE_FSBG=4;
	public static final int BUFFERTYPE_FSFG=5;
		
	private Rectangle displayRect;
	private Rectangle bufferRect;
	private Hashtable<String,Texture> textureBuffer=new Hashtable<String,Texture>();
		
	private Hashtable<String,BufferedImage> imgBuffer=new Hashtable<String,BufferedImage>();
	private ArrayList<String> newImages=new ArrayList<String>();
	private ArrayList<String> requestImages=new ArrayList<String>();
	private ArrayList<String> bufferRequestImages=new ArrayList<String>();
	private TileFactory tf;
	private Texture emptyTexture=null;
	private static Texture fillTexture=null;
	public static Texture xTexture=null;
	private static MapCubeTextureProvider instance=null;
	
	private int tilesWidth,tilesHeight,tileSize,zoom;
	
	/**
	 * Singleton Instanz des MapCubeTextureProvider abrufen
	 * @return Singleton Instanz des MapCubeTextureProvider
	 */
	public static MapCubeTextureProvider getInstance()
	{
		if(instance==null)
			instance=new MapCubeTextureProvider();
		return instance;
	}
	
	/**
	 * Füll Textur abrufen
	 * @return Textur
	 */
	public static Texture getFillTexture() {
		return fillTexture;
	}
	
	/**
	 * Füll Textur setzen
	 * @param newFillTexture Textur
	 */
	public static void setFillTexture(Texture newFillTexture) {
		fillTexture = newFillTexture;
	}
		
	/**
	 * Breite eines Tiles abrufen
	 * @return Breite eines Tiles
	 */
	public int getTilesWidth() {
		return tilesWidth;
	}

	/**
	 * Höhe eines Tiles abrufen
	 * @return Höhe eines Tiles
	 */
	public int getTilesHeight() {
		return tilesHeight;
	}

	/**
	 * Grösse eines Tiles abrufen
	 * @return Grösse eines Tiles
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * Zoomlevel abrufen
	 * @return Zoomlevel
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * Zoomlevel setzen
	 * @param zoom Zoomlevel
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
		this.adjust();
	}

	/**
	 * abgebildete Region in Geokoordinaten setzen 
	 * @param displayRect abgebildete Region in Geokoordinaten 
	 */
	public void setDisplayRect(Rectangle displayRect) {
		this.displayRect = displayRect;
	}


	/**
	 * Region, für die Tiles geladen werden sollen, in Geokoordinaten setzen
	 * @param bufferRect Region, für die Tiles geladen werden sollen, in Geokoordinaten
	 */
	public void setBufferRect(Rectangle bufferRect) {
		this.bufferRect = bufferRect;
	}
	
	/**
	 * MapCubeTextureProvider initialisieren
	 */
	public MapCubeTextureProvider() {
		tf = this.getTileFactory();
		this.adjust();
	}
	
	/**
	 * Anzahl der Tiles in horizontaler und vertikaler Richtung einstellen
	 */
	private void adjust()
	{		

		// Berechung des Anfang Index der Tiles und die Anzahl der benötigten
		// Tiles
		Dimension mapsize = this.getTileFactory().getMapSize(zoom);
		tilesWidth = (int)mapsize.getWidth();
		tilesHeight= (int)mapsize.getHeight();
		tileSize = tf.getTileSize(zoom);		
	}
	
	/**
	 * Anfragen Rastergrafiken erzeugen
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		this.processRequests();
	}
	
	/**
	 * Texturen erzeugen
	 * @param drawable drawable OpenGL
	 */
	public void makeTextures(GLAutoDrawable drawable)
	{
		
		this.cleanBuffer();
		
		if(this.emptyTexture==null)
			this.makeEmptyTexture(drawable);
		
		if(fillTexture==null)
			this.makeFillTexture(drawable);

		
		while(this.newImages.size()>0)
		{
			String tileCode=this.newImages.get(0);
			Texture texture=null;
			
			try
			{
				BufferedImage bi=this.imgBuffer.get(tileCode);
				texture=this.makeMapTextures(drawable,bi);
				//Textur mit Blur erzeugen				
				this.textureBuffer.put(tileCode, texture);				

			}
			catch(Exception ex)
			{								
				ex=ex;	
			}
			
			//this.textureBuffer2.put(tileCode, blurtexture);
			
			this.newImages.remove(tileCode);
		}
		
		//
		
		//this.newImages.clear();

	}

	/**
	 * leere Textur erzeugen für die leere Fläche im Hintergrund
	 * @param drawable drawable OpenGL
	 */
	private void makeEmptyTexture(GLAutoDrawable drawable) {

		GL gl = drawable.getGL();
		Texture texture;

		BufferedImage emptyImage = new BufferedImage(this.tileSize,this.tileSize, BufferedImage.TYPE_INT_RGB);
		
		
		texture = TextureIO.newTexture((BufferedImage)emptyImage, false);
		texture.bind();

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);
		
		this.emptyTexture=texture;

	}
	
	/**
	 * Füll Textur erzeugen
	 */
	private void makeFillTexture(GLAutoDrawable drawable) {

		GL gl = drawable.getGL();
		Texture texture;
		BufferedImage fillImage = new BufferedImage(this.tileSize,this.tileSize, BufferedImage.TYPE_INT_RGB);
		fillImage.getGraphics().fillRect(0, 0, this.tileSize, this.tileSize);				
		texture = TextureIO.newTexture((BufferedImage)fillImage, false);
		texture.bind();
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);
		fillTexture=texture;

	}
		
	/**
	 * Textur mit dem Rastergrafik erzeugen
	 * @param drawable drawable OpenGL
	 * @param img Rastergrafik
	 * @return
	 */
	private Texture makeMapTextures(GLAutoDrawable drawable,Image img) {
		
		GL gl = drawable.getGL();		
		Texture texture;		
		if(img!=null)
		{

			texture = TextureIO.newTexture((BufferedImage)img, false);
			texture.bind();
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_LINEAR);		
			return texture;
		}
		else
			return null;
	}
	
	/**
	 * alle Anfragen für Rastergrafiken abarbeiten 
	 */
	private void processRequests()
	{
		this.cleanRequests();
		//erst die normalen Requests abarbeiten
		if(this.requestImages.size()>0)
		{
			this.loadTileIntoBuffer(this.requestImages.get(0));
			this.requestImages.remove(0);
			return;
		}
		
		//Buffer Requests abarbeiten
		if(this.bufferRequestImages.size()>0)
		{
			this.loadTileIntoBuffer(this.bufferRequestImages.get(0));
			this.bufferRequestImages.remove(0);
			return;
		}
	}
	
	/**
	 * unnötige Abfragen entfernen
	 */
	private void cleanRequests()
	{

		//display tilerects
		Rectangle displayTileRect=this.getDisplayTileRect();
		
		int ix,iy;
		String tileCodes=";";
		
		for(ix=0;ix<displayTileRect.getWidth();ix++)
		{
			for(iy=0;iy<displayTileRect.getHeight();iy++)
			{
				String tileCode=this.correctTileCode(this.makeTileCode(ix+(int)displayTileRect.getX(), iy+(int)displayTileRect.getY(), zoom));
				tileCodes+=tileCode+";";
			}
		}				
		ArrayList<String> delTiles=new ArrayList<String>();
		
		try
		{
			for(String req:this.requestImages)
			{
				if(tileCodes.indexOf(req)<0)
					delTiles.add(req);
			}
		}
		catch(ConcurrentModificationException ce)
		{
		
		}
			
		for(String tileCode:delTiles)
		{
			try
			{
				this.requestImages.remove(tileCode);
			}
			catch(ConcurrentModificationException ce)
			{
				
			}
		}
		
		//buffer tilerects
		
		Rectangle bufferTileRect=this.getDisplayTileRect();
		
		tileCodes=";";
		
		for(ix=0;ix<bufferTileRect.getWidth();ix++)
		{
			for(iy=0;iy<bufferTileRect.getHeight();iy++)
			{
				String tileCode=this.correctTileCode(this.makeTileCode(ix+(int)bufferTileRect.getX(), iy+(int)bufferTileRect.getY(), zoom));
				tileCodes+=tileCode+";";
			}
		}				
		delTiles=new ArrayList<String>();
		try
		{
			for(String req:this.bufferRequestImages)
			{
				if(tileCodes.indexOf(req)<0)
					delTiles.add(req);
			}
		}
		catch(ConcurrentModificationException ce)
		{
		
		}

		
		for(String tileCode:delTiles)
		{
			try
			{
				this.bufferRequestImages.remove(tileCode);
			}
			catch(ConcurrentModificationException ce)
			{
			
			}
		}
	}
	
	/**
	 * Rastergrafiken in den Buffer laden
	 * @param tileCode Zugriffskürzel für die Rastergrafik
	 */
	private void loadTileIntoBuffer(String tileCode) {
		int[] tileCodes = this.decodeTileCode(tileCode);

		Tile tile = tf.getTile(tileCodes[0], tileCodes[1], tileCodes[2]);
		try {
			while (!tile.isLoaded())
				Thread.sleep(3);
		} catch (Exception ex1) {
			System.out.print(ex1.getMessage());
		}
		if (tile != null) {
			Image tileImg = null;
			try
			{							
				tileImg = Toolkit.getDefaultToolkit().createImage(
						tile.getImage().getSource());
			
				BufferedImage bi = new BufferedImage((int)tileImg.getWidth(null), (int)tileImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D  g = bi.createGraphics();
				g.drawImage(tileImg, 0, 0, null);
				
				this.imgBuffer.put(tileCode, bi);
				this.newImages.add(tileCode);
			}
			catch(Exception ex)
			{
				
			}
		}
	}
	
	
	/**
	 * die Tilefactory laden
	 */
	private TileFactory getTileFactory() {
		TileFactory tileFactory = null;

		// OpenStreetMaps Tile Factory
		final int max = 17;
		TileFactoryInfo info = new TileFactoryInfo(1, max - 2, max, // ???
				256, // tile size = 256
				true, true, // x/y orientation is normal
				"http://tile.openstreetmap.org", "x", "y", "z" )
				// Tile pattern:
		// 5/15/10.png",
				
				/*TileFactoryInfo info=new TileFactoryInfo (1, max - 2, max, 256, true, true, 
"http://mt0.google.com/vt/lyrs=m@121&hl=de", "x", "y", "zoom")*/
				//http://mt0.google.com/vt/lyrs=m@121&hl=de&x=14&y=10&z=5&s=Gali
	 {
			public String getTileUrl(int x, int y, int zoom) {
				zoom = max - zoom;
				String url = this.baseURL + "/" + zoom + "/" + x + "/" + y+ ".png";
				//String url = this.baseURL + "&x=" + x + "&y=" + y + "&z=" + zoom+ "&s=gali";
				return url;
			}
		};
		tileFactory = new DefaultTileFactory(info);

		return tileFactory;
	}
	
	/**
	 * Zugriffskürzel für ein Tile anhand X,Y Position und Zoom ermitteln
	 * @param tileX X Position 
	 * @param tileY Y Position
	 * @param zoom Zoom
	 * @return Zugriffskürzel
	 */
	private String makeTileCode(int tileX, int tileY, int zoom) {
		return tileX + "_" + tileY + "_" + zoom;
	}
	
	/**
	 * Verarbeitung neu anpassen
	 */
	public void rebuild()
	{
		this.makeRequests();
	}
	
	/**
	 * unnötige Abfragen löschen
	 */
	private void cleanBuffer()
	{
		
		if(this.textureBuffer.size()>MapCubeTextureProvider.MAXBUFFERTILES)
		{
			Rectangle bufferTileRect=this.getBufferTileRect();
			
			int ix,iy;
			String tileCodes=";";
			
			for(ix=0;ix<bufferTileRect.getWidth();ix++)
			{
				for(iy=0;iy<bufferTileRect.getHeight();iy++)
				{
					String tileCode=this.correctTileCode(this.makeTileCode(ix+(int)bufferTileRect.getX(), iy+(int)bufferTileRect.getY(), zoom));
					tileCodes+=tileCode+";";
				}
			}		
			

			ArrayList<String> delTiles=new ArrayList<String>();
			for(String tileCode:this.textureBuffer.keySet())
			{
				if(tileCodes.indexOf(";"+tileCode+";")==-1)
			      delTiles.add(tileCode);						
			}
			for(String tileCode:delTiles)
			{
				Texture tex=this.textureBuffer.get(tileCode);
				tex.dispose();
				this.textureBuffer.remove(tileCode);
			
				this.imgBuffer.remove(tileCode);
				if(this.textureBuffer.size()<MapCubeTextureProvider.MINBUFFERTILES)
					break;
			}

		}
	}
	
	/**
	 * Anfragen erstellen
	 */
	private void makeRequests()
	{
		int ix,iy;
		String tileCode;

		// normale Requests		
		Rectangle displayTileRect=getDisplayTileRect();
		for(ix=0;ix<displayTileRect.getWidth();ix++)
		{
			for(iy=0;iy<displayTileRect.getHeight();iy++)
			{
				tileCode=this.correctTileCode(this.makeTileCode(ix+(int)displayTileRect.getX(), iy+(int)displayTileRect.getY(), zoom));
				if(!this.textureBuffer.containsKey(tileCode) && !this.requestImages.contains(tileCode) && !this.newImages.contains(tileCode))
					this.requestImages.add(tileCode);
			}
		}

		// Buffer Requests 
		Rectangle bufferTileRect=getBufferTileRect();
		for(ix=0;ix<bufferTileRect.getWidth();ix++)
		{
			for(iy=0;iy<bufferTileRect.getHeight();iy++)
			{
				tileCode=this.correctTileCode(this.makeTileCode(ix+(int)bufferTileRect.getX(), iy+(int)bufferTileRect.getY(), zoom));
				if(!this.textureBuffer.containsKey(tileCode) && !this.requestImages.contains(tileCode) && !this.newImages.contains(tileCode) && !this.bufferRequestImages.contains(tileCode))
					this.bufferRequestImages.add(tileCode);
			}
		}
		

	}
	
	/**
	 * Abmessungen in Tile Koordinaten in der Anzeige Region ermitteln 
	 * @return Abmessungen in Tile Koordinaten
	 */
	private Rectangle getDisplayTileRect()
	{
		return this.getTileRect(this.displayRect);
	}

	/**
	 * Abmessungen in Tile Koordinaten in der Buffer Region ermitteln
	 * @return Abmessungen in Tile Koordinaten in der Buffer Region
	 */
	private Rectangle getBufferTileRect()
	{
		return this.getTileRect(this.bufferRect);
	}

	/**
	 * Abmessungen in Tile Koordinaten in einer Region ermitteln
	 * @param posRect Abmessungen in Tile Koordinaten in einer Region
	 * @return
	 */
	public Rectangle getTileRect(Rectangle posRect)
	{
		Rectangle tileRect=new Rectangle();
		
		tileRect.x=(int)Math.floor(posRect.getX()/this.tileSize);
		tileRect.y=(int)Math.floor(posRect.getY()/this.tileSize);
		tileRect.width=(int)Math.ceil((float)(posRect.getX()+posRect.getWidth())/(float)this.tileSize)-tileRect.x;
		tileRect.height=(int)Math.ceil((float)(posRect.getY()+posRect.getHeight())/(float)this.tileSize)-tileRect.y;
				
		return tileRect;
	}
	
	
	/**
	 * Zugriffskürzel mit zu grossen oder zu kleinen Koordinaten korrigieren
	 * @param tileCode Zugriffskürzel
	 * @return
	 */
	private String correctTileCode(String tileCode)
	{
		int[] intTileCode=decodeTileCode(tileCode);
		if(intTileCode[0]<0)
			intTileCode[0]=intTileCode[0]+((int)(intTileCode[0]/this.tilesWidth)+1)*(int)this.tilesWidth;
		if(intTileCode[1]<0)
			intTileCode[1]=intTileCode[1]+((int)((-1)*intTileCode[1]/this.tilesHeight)+1)*(int)this.tilesHeight;
		if(intTileCode[0]>=tilesWidth)
			intTileCode[0]=intTileCode[0]-((int)(intTileCode[0]/this.tilesWidth))*(int)this.tilesWidth;
		if(intTileCode[1]>=tilesHeight)
			intTileCode[1]=intTileCode[1]-((int)(intTileCode[1]/this.tilesHeight))*(int)this.tilesHeight;
		return this.makeTileCode(intTileCode[0], intTileCode[1], intTileCode[2]);
		
	}

	/**
	 * Zugriffskürzel in X,Y Position und Zoom Wert umwandeln
	 * @param tileCode
	 * @return
	 */
	private int[] decodeTileCode(String tileCode) {
		String[] tileCodeSplit = tileCode.split("_");
		int[] ret = new int[3];
		ret[0] = Integer.valueOf(tileCodeSplit[0]);
		ret[1] = Integer.valueOf(tileCodeSplit[1]);
		ret[2] = Integer.valueOf(tileCodeSplit[2]);
		return ret;
	}
	
	/**
	 * Liste mit Texturen in einer bestimmten Region abrufen
	 * @param posRect Region
	 * @return
	 */
	public ArrayList<ArrayList<Texture>> getTextures(Rectangle posRect)
	{
		ArrayList<ArrayList<Texture>> vList=new ArrayList<ArrayList<Texture>>();
		Rectangle bufferTileRect=getTileRect(posRect);
		
		for(int iy=0;iy<bufferTileRect.getHeight();iy++)
		{
			ArrayList<Texture> xList=new ArrayList<Texture>();
			for(int ix=0;ix<bufferTileRect.getWidth();ix++)	
			{				
				String tileCode=this.correctTileCode(this.makeTileCode(ix+(int)bufferTileRect.getX(), iy+(int)bufferTileRect.getY(), zoom));
				boolean tileadd=false;
				
				if(this.textureBuffer.containsKey(tileCode))
				{
					xList.add(textureBuffer.get(tileCode));
					tileadd=true;
				}
				if(!tileadd)				
					xList.add(this.emptyTexture);
			}
			vList.add(xList);
		}
		return vList;
	}
	
	/**
	 * aktuelle Position(Geookordinate) in der Anzeige anhand der Tile Factor ermitteln
	 * @return aktuelle Position(Geookordinate)
	 */
	public GeoPosition getCurrentGeoPosition()
	{
		Point2D pixel=new Point2D.Double(this.displayRect.x, this.displayRect.y);
		GeoPosition geo = this.getTileFactory().pixelToGeo(pixel, this.zoom);
		return geo;
		
	}
	
	/**
	 * aktuelle Mittelpunkt Position(Geookordinate) in der Anzeige anhand der Tile Factor ermitteln 
	 * @return Mittelpunkt aktuelle Mittelpunkt Position(Geookordinate)
	 */
	public GeoPosition getCurrentCenterGeoPosition()
	{
		Point2D pixel=new Point2D.Double(this.displayRect.x+this.displayRect.width/2, this.displayRect.y+this.displayRect.height/2);
		GeoPosition geo = this.getTileFactory().pixelToGeo(pixel, this.zoom);
		return geo;
		
	}
	
	/**
	 * Geokoordinate in Pixel Koordinate umwandeln
	 * @param geo
	 * @return
	 */
	public Point2D getPointFromGeoPosition(GeoPosition geo)
	{
		Point2D  pixel= this.getTileFactory().geoToPixel(geo, this.zoom);
		return pixel;
	}
	
	/**
	 * Pixelkoordinate in Geokoordinate umwandeln
	 * @param point
	 * @return
	 */
	public GeoPosition getGeoPositionFromPoint(Point2D point)
	{
		point.setLocation(point.getX()+this.displayRect.width/3+this.displayRect.x, point.getY()+this.displayRect.height/3+this.displayRect.y);
		GeoPosition  geo= this.getTileFactory().pixelToGeo(point,this.zoom);
		return geo;
	}		
}
