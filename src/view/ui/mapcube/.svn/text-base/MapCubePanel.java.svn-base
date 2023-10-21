package view.ui.mapcube;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.*;


import model.geo_data.AbstractSignature;
import model.geo_data.GeoObject;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import view.ui.AbstractJOGLPanel;

import com.vividsolutions.jts.geom.*;

import controller.controlsystem.HandleInput;

/**
 * Panel mit Jogl Funktionalität, das den gesamten Würfel zeichnet
 *
 */
public class MapCubePanel extends AbstractJOGLPanel implements MouseListener,
		MouseMotionListener ,MouseWheelListener{
	
	public final static String SURFACE_LEFT="SURFACE_LEFT";
	public final static String SURFACE_RIGHT="SURFACE_RIGHT";
	public final static String SURFACE_TOP="SURFACE_TOP";
	public final static String SURFACE_BOTTOM="SURFACE_BOTTOM";
	public final static String SURFACE_FRONT="SURFACE_FRONT";
	public final static String SURFACE_BACK="SURFACE_BACK";
	public final static String SURFACE_OVERVIEW="SURFACE_OVERVIEW";
	
	public final static int MODE_MAPSALL=0;
	public final static int MODE_MAPSONLYFRONT=1;
	public final static int MODE_MAPSNONE=2;

	public final static int SEMANTIC_ZOOM_0=0;
	public final static int SEMANTIC_ZOOM_1=1;
	public final static int SEMANTIC_ZOOM_2=2;

	private Hashtable<String,CubeSurface> surfaces;	
	private CubeSurface overviewSurface=null;
	
	static public Hashtable<String,String> parameters=new Hashtable<String,String>();

	Rectangle displayRect=new Rectangle();	
	public static final int BUFFERPOINTS = 512;		
	private int zoom = 14;
	private int _zoom = 0;
	private Timer timer = new Timer();
	private static MapCubePanel instance=null;	
	private ArrayList<GeoObject> geoObjects=new ArrayList<GeoObject>();
	private ArrayList<GeoObject> highlightedGeoObjects=new ArrayList<GeoObject>();
	private ArrayList<GeoObject> removedHighlightedGeoObjects=new ArrayList<GeoObject>();
	private ArrayList<GeoObject> pointedGeoObjects=new ArrayList<GeoObject>();
	private ArrayList<GeoObject> withSignGeoObjects=new ArrayList<GeoObject>();
	private ArrayList<GeoObject> noScriptGeoObjects=new ArrayList<GeoObject>();
	private ArrayList<Coordinate> mapExtract=new ArrayList<Coordinate>();
	private ArrayList<Coordinate> clipExtract=null;
	private ArrayList<Coordinate> scatterplots=new ArrayList<Coordinate>();
		
	private GeoObject selectedObject=null;
	private float alphaTransparency=0.0f;	
	private boolean transparentView=false;
	private boolean lock=false;
	private String infoText=null;
	private int linkingAndBrushingSymbol=-1;
	private boolean endSymbol=false;
	private boolean zoomSymbol=false;
	private boolean usecaseObjectText=false;
	private String infoLine=null;
	private boolean init=false;
	private boolean overviewAndDetail=false;
	private boolean showZoomFocusRegion=false;
	private boolean showPerceptionCues=true;
	private boolean showCenterCross=false;
	private boolean useHaloCues=false;
	private boolean distanceShow=false;
	private boolean isDrawing=false;	
	private boolean front_tiles=false;
	private boolean side_tiles=false;
	private boolean sideWireframes=false;
	private boolean frontWireframes=false;
	private boolean connectorWedges=false;
	private float maxTransparency=0.5f;		
	private float rotate=0.0f;

	/**
	 * Singleton Instanz des MapcubePanel abrufen
	 * @return
	 */
	public static MapCubePanel getInstance()
	{
		if(instance==null)
			instance=new MapCubePanel();
		return instance;
	}

	/**
	 * Liste mit Geookoordinate auslesen, die den dargestellten Kartenauscchnitt beschreiben
	 * @return
	 */
	public ArrayList<Coordinate> getMapExtract() {
		return mapExtract;
	}

	/**
	 * Liste mit Geookoordinate setzen, die den dargestellten Kartenauscchnitt beschreiben
	 * @param mapExtract
	 */
	public void setMapExtract(ArrayList<Coordinate> mapExtract) {
		this.mapExtract = mapExtract;
	}
	/**
	 * Liste mit den Würfelflächen abrufen
	 * @return
	 */
	public Hashtable<String, CubeSurface> getSurfaces() {
		return surfaces;
	}
	
	/**
	 * Beenden Symbol aktiv 
	 * @return
	 */
	public boolean isEndSymbol() {
		return endSymbol;
	}
	
	/**
	 * Beenden Symbol aktivieren/deaktivieren
	 * @param endSymbol
	 */
	public void setEndSymbol(boolean endSymbol) {
		this.endSymbol = endSymbol;
	}

	/**
	 * Zoom Symbol aktiv
	 */
	public boolean isZoomSymbol() {
		return zoomSymbol;
	}

	/**
	 * Zoom Symboll aktivieren/deaktivieren
	 * @param zoomSymbol
	 */
	public void setZoomSymbol(boolean zoomSymbol) {
		this.zoomSymbol = zoomSymbol;
	}

	/**
	 * Texte für anwendungsfallspezifische Objekte aktivieren/deaktivieren
	 * @param usecaseObjectText
	 */
	public void setUsecaseObjectText(boolean usecaseObjectText) {
		this.usecaseObjectText = usecaseObjectText;
	}

	/**
	 * Status (Texte für anwendungsfallspezifische Objekte) abrufen
	 * @return
	 */
	public boolean isUsecaseObjectText() {
		return usecaseObjectText;
	}
	
	/**
	 * Status (Überblick und Detail) abrufen
	 * @return
	 */
	public boolean isOverviewAndDetail() {
		return overviewAndDetail;
	}

	/**
	 * Überblick und Detail aktivieren
	 * @param overviewAndDetail
	 */
	public void setOverviewAndDetail(boolean overviewAndDetail) {
		this.overviewAndDetail = overviewAndDetail;
	}
	
	/**
	 * Zoomfokus Region anzeigen
	 * @return
	 */
	public boolean isShowZoomFocusRegion() {
		return showZoomFocusRegion;
	}
	
	/**
	 * Status Zoomfokus Region abrufen
	 * @param showZoomFocusRegion
	 */
	public void setShowZoomFocusRegion(boolean showZoomFocusRegion) {
		this.showZoomFocusRegion = showZoomFocusRegion;
	}
	
	/**
	 * Status (HALO Hinweise) abrufen
	 * @return
	 */
	public boolean isUseHaloCues() {
		return useHaloCues;
	}
	
	/**
	 * HALO Hinweise aktivieren/deaktivieren
	 * @param useHaloCues
	 */
	public void setUseHaloCues(boolean useHaloCues) {
		this.useHaloCues = useHaloCues;
	}
	
	/**
	 * Status (Perception Cues) abrufen 
	 * @return
	 */
	public boolean isShowPerceptionCues() {
		return showPerceptionCues;
	}
	
	/**
	 * Perception Cues aktivieren/deaktivieren
	 * @param showPerceptionCues
	 */
	public void setShowPerceptionCues(boolean showPerceptionCues) {
		this.showPerceptionCues = showPerceptionCues;
	}
	
	/**
	 * Liste mit Objekten, auf die Hinweise gesetzt werden, abrufen
	 * @return
	 */
	public ArrayList<GeoObject> getPointedGeoObjects() {
		return pointedGeoObjects;
	}
	
	/**
	 * Zeichenoperation aktiv
	 * @return
	 */
	public boolean isDrawing() {
		return isDrawing;
	}
	
	/**
	 * Status Zeichenoperation setzen/aufheben
	 * @param isDrawing
	 */
	public void setDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}
	
	/**
	 * wird Markierung im Zentrum angezeigt
	 * @return
	 */
	public boolean isShowCenterCross() {
		return showCenterCross;
	}

	/**
	 * Markierung im Zentrum aktivieren/deaktivieren
	 * @param showCenterCross
	 */
	public void setShowCenterCross(boolean showCenterCross) {
		this.showCenterCross = showCenterCross;
	}
	
	/**
	 * Status (Rastergrafiken im Fokus) abfragen 
	 * @return
	 */
	public boolean isFront_tiles() {
		return front_tiles;
	}
	
	/**
	 * Rastergrafiken im Fokus aktivieren/deaktivieren
	 * @param frontTiles
	 */
	public void setFront_tiles(boolean frontTiles) {
		this.front_tiles = frontTiles;
	}

	/**
	 * Status (Rastergrafiken im Kontext) abfragen
	 * @return
	 */
	public boolean isSide_tiles() {
		return side_tiles;
	}
	
	/**
	 * Rastergrafiken im Kontext aktivieren/deaktivieren
	 * @param sideTiles
	 */
	public void setSide_tiles(boolean sideTiles) {
		this.side_tiles = sideTiles;
	}
	
	/**
	 * Drahtgitterdarstellung im Kontext aktivieren/deaktivieren
	 * @param wireframe
	 */
	public void setWireframeForSides(boolean wireframe)
	{
		this.sideWireframes=wireframe;
	}
	
	/**
	 * Drahtgitterdarstellung im Fokus aktivieren/deaktivieren
	 * @param wireframe
	 */
	public void setWireframeForFront(boolean wireframe)
	{
		this.frontWireframes=wireframe;
	}

	/**
	 * maximale Transparenz abrufen
	 * @return
	 */
	public float getMaxTransparency() {
		return maxTransparency;
	}
	
	/**
	 * maximale Transparenz setzen
	 * @param maxTransparency Wert maximale Transparenz
	 */
	public void setMaxTransparency(float maxTransparency) {
		this.maxTransparency = maxTransparency;
	}
	
	/**
	 * Entfernungsanzeige für Hinweise aktiv
	 * @return
	 */
	public boolean isDistanceShow() {
		return distanceShow;
	}
	
	/**
	 * Entfernungsanzeige für Hinweise aktivieren/deaktivieren 
	 * @param distanceShow
	 */
	public void setDistanceShow(boolean distanceShow) {
		this.distanceShow = distanceShow;
	}

	/**
	 * Verbindungsstücke aktiv
	 * @return
	 */
	public boolean isConnectorWedges() {
		return connectorWedges;
	}
	
	/**
	 * Verbindungsstücke aktivieren/deaktivieren 
	 * @param connectorWedges
	 */
	public void setConnectorWedges(boolean connectorWedges) {
		this.connectorWedges = connectorWedges;
	}
	
	/**
	 * Darstellungsart abruefn
	 * @return
	 */
	public String getVisualization_type() {		
		return (String)parameters.get("displaytype");
	}

	/**
	 * Drehung der Anzeige auslesen
	 * @return Drehungswinkel
	 */
	public float getRotate() {
		return rotate;
	}
	
	/**
	 * Drehung der Anzeige setzen
	 * @param rotate Drehungswinkel  
	 */
	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	/**
	 * Kooorindaten der Scatterplots
	 * @return
	 */
	public ArrayList<Coordinate> getScatterplots() {
		return scatterplots;
	}
	
	/**
	 * Liste mit Koordinaten für Scatterplots setzen
	 * @param scatterplots
	 */
	public void setScatterplots(ArrayList<Coordinate> scatterplots) {
		this.scatterplots = scatterplots;
	}

	/**
	 * Liste mit dargestellten Geoobjekten abrufen
	 * @return Liste mit dargestellten Geoobjekten
	 */
	public ArrayList<GeoObject> getGeoObjects() {
		return geoObjects;
	}
	
	/**
	 * Begrenzungen für das Panning abrufen
	 * @return Liste mit Geokoordinaten
	 */
	public ArrayList<Coordinate> getClipExtract() {
		return clipExtract;
	}
	
	/**
	 * Begrenzungen für das Panning setzen
	 * @param clipExtract Liste mit Geokoordinaten
	 */
	public void setClipExtract(ArrayList<Coordinate> clipExtract) {
		this.clipExtract = clipExtract;
	}
	
	/**
	 * Zoomlevel abrufen
	 * @return Zoomlevel
	 */
	public int get_zoom() {
		return _zoom;
	}
	
	/**
	 * Zoomlevel setzen
	 * @param zoom Zoomlevel
	 */ 
	public void set_zoom(int zoom) {
		_zoom = zoom;
	}


	
	
	/**
	 * Konstruktor
	 */
	public MapCubePanel() {

	}
	
	/**
	 * Mapcube Panel initialisieren
	 */
	public void init()
	{
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		MapCubeTextureProvider mapCubeTextureProvider=MapCubeTextureProvider.getInstance();
		mapCubeTextureProvider.setZoom(this.zoom);		
		this.displayRect.x=768;
		this.displayRect.y=768;
		this.displayRect.width=768;
		this.displayRect.height=768;
		
		int mode=MapCubePanel.MODE_MAPSALL;				
		this.surfaces = new Hashtable<String,CubeSurface>();				
		

		float rFrontWidth = 1.0f;
		float rFrontHeight = 1.5f;
		float rBackWidth = 1.0f;
		float rBackHeight = 1.5f;
		float zDist = 2.0f;

		// Quadrat vorne
		Point3D pFrontTopLeft = new Point3D(-rFrontWidth, rFrontHeight, 0.0f);
		Point3D pFrontTopRight = new Point3D(rFrontWidth, rFrontHeight, 0.0f);
		Point3D pFrontBottomRight = new Point3D(rFrontWidth, -rFrontHeight, 0.0f);
		Point3D pFrontBottomLeft = new Point3D(-rFrontWidth, -rFrontHeight, 0.0f);

		// Quadrat hinten
		Point3D pBackTopLeft = new Point3D(-rBackWidth, rBackHeight, -zDist);
		Point3D pBackTopRight = new Point3D(rBackWidth, rBackHeight, -zDist);
		Point3D pBackBottomRight = new Point3D(rBackWidth, -rBackHeight, -zDist);
		Point3D pBackBottomLeft = new Point3D(-rBackWidth, -rBackHeight, -zDist);
				
		// Quadrat overview		
		Point3D ovTopLeft = new Point3D(-rFrontWidth, rFrontHeight, 0.0f);
		Point3D ovTopRight = new Point3D(rFrontWidth, rFrontHeight, 0.0f);
		Point3D ovBottomRight= new Point3D(rFrontWidth, -rFrontHeight, 0.0f);
		Point3D ovBottomLeft= new Point3D(-rFrontWidth, -rFrontHeight, 0.0f);
		
		// Textur links
		CubeSurface cs2 = new CubeSurface();
		cs2.getEucldianCoords().put("topRight",pFrontTopLeft);
		cs2.getEucldianCoords().put("topLeft",pBackTopLeft);
		cs2.getEucldianCoords().put("bottomLeft",pBackBottomLeft);
		cs2.getEucldianCoords().put("bottomRight",pFrontBottomLeft);				
		cs2.setTranslateMapX(-1);
		cs2.setTranslateMapY(0);
		cs2.setId(SURFACE_LEFT);
		cs2.setRotY(90);
		cs2.setHeight(rFrontHeight * 2);
		cs2.setWidth(zDist);
		cs2.setXMirror(true);
		cs2.setWireframe(this.sideWireframes);
		if(mode==MODE_MAPSONLYFRONT || mode==MODE_MAPSNONE)
			cs2.setEmpty(true);
		this.surfaces.put(SURFACE_LEFT,cs2);

		// Textur rechts
		CubeSurface cs3 = new CubeSurface();
		cs3.getEucldianCoords().put("topRight",pBackTopRight);
		cs3.getEucldianCoords().put("topLeft",pFrontTopRight);
		cs3.getEucldianCoords().put("bottomLeft",pFrontBottomRight);
		cs3.getEucldianCoords().put("bottomRight",pBackBottomRight);	
		cs3.setTranslateMapX(1);
		cs3.setTranslateMapY(0);
		cs3.setId(SURFACE_RIGHT);
		cs3.setRotY(-90);
		cs3.setHeight(rFrontHeight * 2);
		cs3.setWidth(zDist);	
		cs3.setXMirror(true);
		cs3.setWireframe(this.sideWireframes);
		if(mode==MODE_MAPSONLYFRONT || mode==MODE_MAPSNONE)
			cs3.setEmpty(true);
		this.surfaces.put(SURFACE_RIGHT,cs3);

		// Textur hinten
		CubeSurface cs4 = new CubeSurface();
		cs4.getEucldianCoords().put("topRight",pBackTopRight);
		cs4.getEucldianCoords().put("topLeft",pBackTopLeft);
		cs4.getEucldianCoords().put("bottomLeft",pBackBottomLeft);
		cs4.getEucldianCoords().put("bottomRight",pBackBottomRight);	

		cs4.setTranslateMapX(0);
		cs4.setTranslateMapY(0);
		cs4.setId(SURFACE_BACK);
		cs4.setHeight(rBackHeight * 2);
		cs4.setWidth(rBackWidth * 2);
		cs4.setEmpty(true);
		this.surfaces.put(SURFACE_BACK,cs4);		

		// Textur oben
		CubeSurface cs5 = new CubeSurface();

		cs5.getEucldianCoords().put("topRight",pBackTopRight);
		cs5.getEucldianCoords().put("topLeft",pBackTopLeft);
		cs5.getEucldianCoords().put("bottomLeft",pFrontTopLeft);
		cs5.getEucldianCoords().put("bottomRight",pFrontTopRight);	

		cs5.setTranslateMapX(0);
		cs5.setTranslateMapY(-1);
		cs5.setId(SURFACE_TOP);
		cs5.setRotX(90);
		cs5.setHeight(zDist);
		cs5.setWidth(rFrontWidth * 2);
		cs5.setYMirror(true);
		cs5.setWireframe(this.sideWireframes);
		if(mode==MODE_MAPSONLYFRONT || mode==MODE_MAPSNONE)
			cs5.setEmpty(true);
		this.surfaces.put(SURFACE_TOP,cs5);

		// Textur unten
		CubeSurface cs6 = new CubeSurface();		
		cs6.getEucldianCoords().put("topRight",pFrontBottomRight);
		cs6.getEucldianCoords().put("topLeft",pFrontBottomLeft);
		cs6.getEucldianCoords().put("bottomLeft",pBackBottomLeft);
		cs6.getEucldianCoords().put("bottomRight",pBackBottomRight);	
		
		cs6.setYMirror(true);		
		cs6.setTranslateMapX(0);
		cs6.setTranslateMapY(1);
		cs6.setId(SURFACE_BOTTOM);
		cs6.setRotX(-90);
		cs6.setHeight(zDist);
		cs6.setWidth(rFrontWidth * 2);
		cs6.setWireframe(this.sideWireframes);
		if(mode==MODE_MAPSONLYFRONT || mode==MODE_MAPSNONE)
			cs6.setEmpty(true);
		this.surfaces.put(SURFACE_BOTTOM,cs6);

		//Textur vorne
		CubeSurface cs7 = new CubeSurface();		
		cs7.getEucldianCoords().put("topRight",pFrontTopRight);
		cs7.getEucldianCoords().put("topLeft",pFrontTopLeft);
		cs7.getEucldianCoords().put("bottomLeft",pFrontBottomLeft);
		cs7.getEucldianCoords().put("bottomRight",pFrontBottomRight);	
		
		cs7.setBlending(1.0f);
		cs7.setId(SURFACE_FRONT);
		cs7.setHeight(rFrontHeight * 2);
		cs7.setWidth(rFrontWidth * 2);
		cs7.setWireframe(this.frontWireframes);
		if(mode==MODE_MAPSNONE)
			cs7.setEmpty(true);
		this.surfaces.put(SURFACE_FRONT,cs7);
				
		//Overview
		this.overviewSurface = new CubeSurface();		
		this.overviewSurface.getEucldianCoords().put("topRight",ovTopRight);
		this.overviewSurface.getEucldianCoords().put("topLeft",ovTopLeft);
		this.overviewSurface.getEucldianCoords().put("bottomLeft",ovBottomLeft);
		this.overviewSurface.getEucldianCoords().put("bottomRight",ovBottomRight);	
		
		this.overviewSurface.setBlending(1.0f);
		this.overviewSurface.setId(SURFACE_FRONT);
		this.overviewSurface.setHeight(rFrontHeight * 2);
		this.overviewSurface.setWidth(rFrontWidth * 2);

		this.fitSurfaceRects();
		
		mapCubeTextureProvider.rebuild();
		if(this.side_tiles || this.front_tiles)
		{
			timer.schedule(mapCubeTextureProvider, 50, 5);
		}
	        timer.schedule(new TimerTask() {
	            public void run() {
	               MapCubePanel.getInstance().adjustTransparency();
	            }
	        }, 1,20);
	
        this.init=true;
		
	}

	/**
	 * den Würfel zusammen mit allen Darstellungsfunktionen zeichnen 
	 */
	public void Draw(GLAutoDrawable drawable) {
		
		if(!this.lock && this.init)
		{
			this.isDrawing=true;
			GL gl = drawable.getGL();
			//GLU glu = new GLU();
			gl.glPushMatrix();

			this.setLightSource(drawable);
			
			gl.glClearDepth(1.0f);			
			
			MapCubeTextureProvider.getInstance().makeTextures(drawable);
		
			gl.glTranslatef(0.0f,0.0f, -3.63f);
						
			int semanticZoom=0;
			if(this._zoom<0)
				semanticZoom=MapCubePanel.SEMANTIC_ZOOM_0;
			if(this._zoom==0)
				semanticZoom=MapCubePanel.SEMANTIC_ZOOM_1;
			if(this._zoom>0)
				semanticZoom=MapCubePanel.SEMANTIC_ZOOM_2;
			
			//DisplayFunctions.drawPanningArrows(drawable);
			
			if(this.showPerceptionCues)
			{
				float arrowLength=(0.8f-(0.8f*(this.maxTransparency-this.alphaTransparency)/this.maxTransparency))+0.1f;
				DisplayFunctions.barsPerceptionCues(drawable,false,1.0f,arrowLength);
			}

			// alle Oberflächen zeichnen
			if(this.alphaTransparency>0.0f)
			{
				for (Enumeration<CubeSurface> e = surfaces.elements(); e.hasMoreElements(); )
				{					
					gl.glPushMatrix();
					CubeSurface cs = (CubeSurface) e.nextElement();
					if(this.surfaces.get(SURFACE_FRONT)!=cs)
					//if(this.surfaces.get(SURFACE_LEFT)==cs  || this.surfaces.get(SURFACE_TOP)==cs)
						cs.Draw(drawable,this.geoObjects,this.side_tiles,semanticZoom,usecaseObjectText);
					gl.glPopMatrix();
				}
			}
			if(this.connectorWedges)
				this.drawConnectorWedges(drawable,semanticZoom);
			
			gl.glEnable(GL.GL_DEPTH_TEST);

			//DisplayFunctions.barsPerceptionCues(drawable,true,1.0f-MapCubePanel.getInstance().alphaTransparency);			
			CubeSurface fs = (CubeSurface)this.surfaces.get(SURFACE_FRONT);			

			fs.Draw(drawable,this.geoObjects,this.front_tiles,semanticZoom,usecaseObjectText);			
									
			if(this.linkingAndBrushingSymbol!=-1)
				DisplayFunctions.showLinkAndBrushingSymbol(this.linkingAndBrushingSymbol,drawable);
			if(this.infoText!=null)
				DisplayFunctions.showInfoText(this.infoText,drawable);
			if(this.infoLine!=null)
				DisplayFunctions.showInfoLine(this.infoLine,drawable);
			if(this.zoomSymbol)
				DisplayFunctions.showZoomSymbol(drawable);
			if(this.useHaloCues)
				DisplayFunctions.DrawHaloCues(drawable, this.pointedGeoObjects);
			else
				DisplayFunctions.DrawArrows(drawable, this.pointedGeoObjects);
			if(this.endSymbol)
				DisplayFunctions.showEndSymbol(drawable);
			if(this.showZoomFocusRegion)
				DisplayFunctions.showZoomFocusRegion(drawable);
			if(this.showCenterCross)
				DisplayFunctions.drawCenterCross(drawable);
			if(this.distanceShow)
				DisplayFunctions.distanceShow(drawable);
			
			if(this.overviewAndDetail)
			{				
				int ovWidth=(int)(this.panelWidth*0.3);
				int ovHeight=(int)(this.panelWidth*0.3);
				
				gl.glPushMatrix();
				gl.glTranslatef(0.0f, 0.0f, 0.01f);

				gl.glViewport(this.panelWidth-(int)(ovWidth*1.2), this.panelHeight-(int)(ovHeight*1.2), ovWidth, ovHeight);
				this.overviewSurface.Draw(drawable, this.geoObjects, false, semanticZoom-1, usecaseObjectText);
				gl.glViewport(0, 0, this.panelWidth, this.panelHeight);
				gl.glPopMatrix();	
			}
			
			if(this.showPerceptionCues)
			{
				float arrowLength=(0.8f-(0.8f*(this.maxTransparency-this.alphaTransparency)/this.maxTransparency))+0.1f;				
				DisplayFunctions.barsPerceptionCues(drawable,true,1.0f-MapCubePanel.getInstance().alphaTransparency,arrowLength);
			}
			
			DisplayFunctions.highlightObjects(this.highlightedGeoObjects, this.removedHighlightedGeoObjects);

			gl.glPopMatrix();
			this.isDrawing=false;
			
		}
	}
	
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Maus wurde gedrückt; Dragging aktivieren
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
				
		if(e.getButton()==MouseEvent.BUTTON1)
		{						
			Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
			HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_LEFTMOUSEBUTTON_PRESSED, mousepos, 0);
		}
		if(e.getButton()==MouseEvent.BUTTON3)
		{						
			Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
			HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_RIGHTMOUSEBUTTON_PRESSED, mousepos, 0);
		}
		
		//this.surfaces.get(SURFACE_FRONT).setDisplayMode(CubeSurface.DISPLAYMODE_BLACKWHITE);
	}

	@Override
	/**
	 * Maus wurde losgelassen; Dragging deaktivieren
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==MouseEvent.BUTTON1)
		{						
			Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
			HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_LEFTMOUSEBUTTON_RELEASED, mousepos, 0);
		}
		if(e.getButton()==MouseEvent.BUTTON3)
		{						
			Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
			HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_RIGHTMOUSEBUTTON_RELEASED, mousepos, 0);
		}

	}


	/**
	 * Kartenausschnitte der einzelnen Würfelflächen neu berechnen
	 * @throws InterruptedException 
	 */
	public void fitSurfaceRects() 
	{
		while(this.isDrawing)
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		int type=0;
		if(this.getVisualization_type().equals("VECTOR_TRAPEZ"))
			type=1;

		if(this.getVisualization_type().equals("VECTOR_TRAPEZ_LARGE"))
			type=2;

		
		//Berechnung der neune Kartenauschnitte für die Oberflächen
		Coordinate alpha=this.mapExtract.get(0);
		Coordinate delta=this.mapExtract.get(2);
		Hashtable<String,Coordinate> surfaceCoordinates=new Hashtable<String,Coordinate>();
		MapCubeTextureProvider mapCubeTextureProvider=MapCubeTextureProvider.getInstance();
		
		double difx=(delta.x-alpha.x);
		double dify=(delta.y-alpha.y);
		
		for(int iy=0;iy<4;iy++)
		{
			for(int ix=0;ix<4;ix++)
			{	
				double coord_x=0;
				double coord_y=0;
				if(type==2)
				{
					if(ix==0)
						coord_x=alpha.x;
					else if(ix==1)
						coord_x=alpha.x+difx*1.25;					
					else if(ix==2)
						coord_x=alpha.x+difx*1.75;
					else if(ix==3)
						coord_x=alpha.x+difx*3.0;
					if(iy==0)
						coord_y=alpha.y;
					else if(iy==1)
						coord_y=alpha.y+dify*1.25;					
					else if(iy==2)
						coord_y=alpha.y+dify*1.75;
					else if(iy==3)
						coord_y=alpha.y+dify*3.0;
				}
				else
				{
					coord_x=alpha.x+((delta.x-alpha.x)*ix/3);
					coord_y=alpha.y+((delta.y-alpha.y)*iy/3);									
				}
				Coordinate coord=new Coordinate(coord_x,coord_y);
				coord=this.rotateCoordinate(coord);
				
				surfaceCoordinates.put(""+ix+"_"+iy, coord);
			}
		}						

		for (Enumeration<CubeSurface> e = surfaces.elements(); e.hasMoreElements(); )
		{
			CubeSurface cs = (CubeSurface) e.nextElement();
			if(cs.getId().equals(SURFACE_LEFT))
			{				
				ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
				if(type==1 || type==2)
				{
					/*surfaceMapExctract.add(surfaceCoordinates.get("0_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("0_1"));*/
					
					surfaceMapExctract.add(surfaceCoordinates.get("0_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("0_3"));					

				}
				else
				{
					surfaceMapExctract.add(surfaceCoordinates.get("0_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("0_2"));
				}
				cs.setMapExtract(surfaceMapExctract);
				
				GeoPosition g1=new GeoPosition(surfaceCoordinates.get("0_1").y,surfaceCoordinates.get("0_1").x);
				GeoPosition g2=new GeoPosition(surfaceCoordinates.get("1_2").y,surfaceCoordinates.get("1_2").x);
				Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
				Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
								
				cs.getDisplayRect().x=(int)p1.getX();
				cs.getDisplayRect().y=(int)p1.getY();
				cs.getDisplayRect().width=(int)(p2.getX()-p1.getX());
				cs.getDisplayRect().height=(int)(p2.getY()-p1.getY());
				
				/*cs.getDisplayRect().x=(int)x1;
				cs.getDisplayRect().y=(int)y2;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/
			}
			if(cs.getId().equals(SURFACE_RIGHT))
			{
				ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
				if(type==1 || type==2)
				{
					surfaceMapExctract.add(surfaceCoordinates.get("2_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_3"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_2"));					

				}
				else
				{
					surfaceMapExctract.add(surfaceCoordinates.get("2_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_2"));				
				}
				
				
				cs.setMapExtract(surfaceMapExctract);
				
				GeoPosition g1=new GeoPosition(surfaceCoordinates.get("2_1").y,surfaceCoordinates.get("2_1").x);
				GeoPosition g2=new GeoPosition(surfaceCoordinates.get("3_2").y,surfaceCoordinates.get("3_2").x);
				Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
				Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
								
				cs.getDisplayRect().x=(int)p1.getX();
				cs.getDisplayRect().y=(int)p1.getY();
				cs.getDisplayRect().width=(int)(p2.getX()-p1.getX());
				cs.getDisplayRect().height=(int)(p2.getY()-p1.getY());
				
				/*cs.getDisplayRect().x=(int)x3;
				cs.getDisplayRect().y=(int)y2;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/			
			}				
			if(cs.getId().equals(SURFACE_TOP))
			{
				ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
				if(type==1 || type==2)
				{
					surfaceMapExctract.add(surfaceCoordinates.get("0_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_1"));
				}
				else
				{
					surfaceMapExctract.add(surfaceCoordinates.get("1_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_0"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_1"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_1"));					
				}
				cs.setMapExtract(surfaceMapExctract);

				GeoPosition g1=new GeoPosition(surfaceCoordinates.get("1_0").y,surfaceCoordinates.get("1_0").x);
				GeoPosition g2=new GeoPosition(surfaceCoordinates.get("2_1").y,surfaceCoordinates.get("2_1").x);
				Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
				Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
								
				cs.getDisplayRect().x=(int)p1.getX();
				cs.getDisplayRect().y=(int)p1.getY();
				cs.getDisplayRect().width=(int)(p2.getX()-p1.getX());
				cs.getDisplayRect().height=(int)(p2.getY()-p1.getY());
				
				/*cs.getDisplayRect().x=(int)x2;
				cs.getDisplayRect().y=(int)y1;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/			
			}
			if(cs.getId().equals(SURFACE_BOTTOM))
			{
				ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
				if(type==1 || type==2)
				{
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("3_3"));
					surfaceMapExctract.add(surfaceCoordinates.get("0_3"));
				}
				else
				{
					surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_2"));
					surfaceMapExctract.add(surfaceCoordinates.get("2_3"));
					surfaceMapExctract.add(surfaceCoordinates.get("1_3"));					
				}
				cs.setMapExtract(surfaceMapExctract);
				
				GeoPosition g1=new GeoPosition(surfaceCoordinates.get("1_2").y,surfaceCoordinates.get("1_2").x);
				GeoPosition g2=new GeoPosition(surfaceCoordinates.get("2_3").y,surfaceCoordinates.get("2_3").x);
				Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
				Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
								
				cs.getDisplayRect().x=(int)p1.getX();
				cs.getDisplayRect().y=(int)p1.getY();
				cs.getDisplayRect().width=(int)(p2.getX()-p1.getX());
				cs.getDisplayRect().height=(int)(p2.getY()-p1.getY());

				/*cs.getDisplayRect().x=(int)x2;
				cs.getDisplayRect().y=(int)y3;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/			
			}
			if(cs.getId().equals(SURFACE_FRONT))
			{
				ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
				surfaceMapExctract.add(surfaceCoordinates.get("1_1"));
				surfaceMapExctract.add(surfaceCoordinates.get("2_1"));
				surfaceMapExctract.add(surfaceCoordinates.get("2_2"));
				surfaceMapExctract.add(surfaceCoordinates.get("1_2"));
				cs.setMapExtract(surfaceMapExctract);

				GeoPosition g1=new GeoPosition(surfaceCoordinates.get("1_1").y,surfaceCoordinates.get("1_1").x);
				GeoPosition g2=new GeoPosition(surfaceCoordinates.get("2_2").y,surfaceCoordinates.get("2_2").x);
				Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
				Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
								
				cs.getDisplayRect().x=(int)p1.getX();
				cs.getDisplayRect().y=(int)p1.getY();
				cs.getDisplayRect().width=(int)(p2.getX()-p1.getX());
				cs.getDisplayRect().height=(int)(p2.getY()-p1.getY());
				
				/*cs.getDisplayRect().x=(int)x2;
				cs.getDisplayRect().y=(int)y2;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/			
			}
			if(cs.getId().equals(SURFACE_BACK))
			{
				/*cs.getDisplayRect().x=(int)x2;
				cs.getDisplayRect().y=(int)y2;
				cs.getDisplayRect().width=(int)w;
				cs.getDisplayRect().height=(int)h;*/			
			}
		}
		
		GeoPosition g1=new GeoPosition(surfaceCoordinates.get("0_0").y,surfaceCoordinates.get("0_0").x);
		GeoPosition g2=new GeoPosition(surfaceCoordinates.get("3_3").y,surfaceCoordinates.get("3_3").x);
		Point2D p1=mapCubeTextureProvider.getPointFromGeoPosition(g1);
		Point2D p2=mapCubeTextureProvider.getPointFromGeoPosition(g2);
		
		//Berechnung Ausschnitt für Overview and Detail
		ArrayList<Coordinate> surfaceMapExctract=new ArrayList<Coordinate>();
		surfaceMapExctract.add(this.mapExtract.get(0));
		surfaceMapExctract.add(this.mapExtract.get(2));
		this.overviewSurface.setMapExtract(surfaceMapExctract);
						
		this.overviewSurface.getDisplayRect().x=(int)p1.getX();
		this.overviewSurface.getDisplayRect().y=(int)p1.getY();
		this.overviewSurface.getDisplayRect().width=(int)(p2.getX()-p1.getX());
		this.overviewSurface.getDisplayRect().height=(int)(p2.getY()-p1.getY());
								
		Rectangle displayrect=new Rectangle();
		displayrect.x=(int)p1.getX();
		displayrect.y=(int)p1.getY();
		displayrect.width=(int)(p2.getX()-p1.getX());
		displayrect.height=(int)(p2.getY()-p1.getY());
		
		MapCubeTextureProvider.getInstance().setDisplayRect(displayrect);
		Rectangle bufferRect=(Rectangle)displayrect.clone();
		bufferRect.x=bufferRect.x-MapCubePanel.BUFFERPOINTS;
		bufferRect.y=bufferRect.y-MapCubePanel.BUFFERPOINTS;
		bufferRect.width=bufferRect.width+MapCubePanel.BUFFERPOINTS;
		bufferRect.height=bufferRect.height+MapCubePanel.BUFFERPOINTS;
		MapCubeTextureProvider.getInstance().setBufferRect(bufferRect);
		if(this.getVisualization_type().indexOf("RASTER")!=-1)
			MapCubeTextureProvider.getInstance().rebuild();
		
	}
	
	/**
	 * eine Koordinate um den Mittelpunkt des dargestellten Kartenauschschnitt zeichnen
	 * @param coord Geokoordinaten
	 * @return  gedrehte Geokoordinaten
	 */
	public Coordinate rotateCoordinate(Coordinate coord)
	{
		if(this.rotate==0.0f)
			return coord;
		
		Coordinate centerPos=this.getCenterGeoposition();
		
		float difx=(float)(coord.x-centerPos.x);
		float dify=(float)(coord.y-centerPos.y);
	
		float dist=(float)Math.sqrt(difx*difx+dify*dify);
		
		float angle = (float) Math.atan2((float) centerPos.y
				- (float) coord.y, (float) centerPos.x
				- (float) coord.x)
				* 180 / (float) Math.PI;
		
		angle-=180;
		
		angle+=this.rotate;
		if(angle>=360.0f)
			angle-=360.0f;
		if(angle<0)
			angle+=360.0f;
		
		float factorx=(float)Math.cos((angle*(float)Math.PI)/(float)180)*dist;
		float factory=(float)Math.sin((angle*(float)Math.PI)/(float)180)*dist;
		Coordinate rotatedCoord=new Coordinate(centerPos.x+factorx,centerPos.y+factory);
		return rotatedCoord;			
	}	

	/**
	 * Maus bei gedrückte Taste verschoben, dann die Würfelflächen verschieben
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

		Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
		HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_MOUSEMOVE, mousepos, 0);

	}

	/**
	 * Maus wurde bewegt
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
		HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_MOUSEMOVE, mousepos, 0);
	}

	/**
	 * Mausrad wurde bewegt 
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Point2D mousepos=new Point2D.Float((float)e.getX(),(float)e.getY());			
		HandleInput.getInstance().handleInput(HandleInput.INPUT_TYPE_MOUSEMOVE, mousepos, e.getWheelRotation());		
		// TODO Auto-generated method stub		
	}

	/**
	 * Transparenz mit Fading Effekt einstellen 
	 */
	public void adjustTransparency()
	{
		if(this.transparentView && this.alphaTransparency<maxTransparency)
		{					
			this.alphaTransparency+=0.02f;
			CubeSurface fs = (CubeSurface)this.surfaces.get(SURFACE_FRONT);
			fs.setBlending(1.0f-alphaTransparency);
		}
		else if(!this.transparentView && this.alphaTransparency>0.0f)
		{
			this.alphaTransparency-=0.02f;
			CubeSurface fs = (CubeSurface)this.surfaces.get(SURFACE_FRONT);
			fs.setBlending(1.0f-alphaTransparency);

		}
	}
	
	/**
	 * darzustellendes Geobjekt an das MapcubePanel anhängen
	 * @param geoObject Geoobjekt
	 */
	public void addGeoObject(GeoObject geoObject)
	{
		this.geoObjects.add(geoObject);
	}
			
	
	/**
	 * darzustellendes Geobjekt von dem MapcubePanel entfernen
	 * @param geoObject Geoobjekt
	 */
	public void removeGeoObject(GeoObject geoObject)
	{
		this.geoObjects.remove(geoObject);
	}
	
	/**
	 * Liste mit allen darzustellenden Geobjekten anzeigen
	 * @return Liste mit allen darzustellenden Geobjekten
	 */
	public ArrayList<GeoObject> getAllGeoObjects()
	{
		return this.geoObjects;
	}
	
	/**
	 * transparente Ansicht aufheben
	 */
	public void switchToNormalView()
	{
		this.transparentView=false;
	}

	/**
	 * transparente Ansicht aktivieren
	 */
	public void switchToTransparentView()
	{
		this.transparentView=true;
	}
	
	/**
	 * Position x setzen 
	 * @param x Position x
	 */
	public void setPositionX(int x)
	{
		this.displayRect.x=x;
	}
	
	/**
	 * Position y setzen
	 * @param y Position y
	 */
	public void setPositionY(int y)
	{
		this.displayRect.y=y;
	}
	
	/**
	 * Zoomlevel auslesen
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
		MapCubeTextureProvider.getInstance().setZoom(this.zoom);
	}
	
	/**
	 * Exkpunkte der Würfelflächen neu berechnen
	 * Rastergrafiken laden
	 */
	public void rebuild()
	{
		this.fitSurfaceRects();
		if(this.front_tiles || this.side_tiles)
			MapCubeTextureProvider.getInstance().rebuild();	
	}
	
	/**
	 * Zeichnen gesperrt 
	 * @return Zeichnen gesperrt
	 */
	public boolean isLock() {
		return lock;
	}

	/**
	 * Status (Zeichnen gesperrt) setzen
	 * @param lock Status (Zeichnen gesperrt)
	 */
	public void setLock(boolean lock) {
		this.lock = lock;
	}

	/**
	 * Status(transparente Ansicht) abfragen
	 * @return Status (transparente Ansicht)
	 */
	public boolean isTransparentView() {
		return transparentView;
	}

	/**
	 * Status(transparente Ansicht) setzen
	 * @param transparentView Status(transparente Ansicht)
	 */
	public void setTransparentView(boolean transparentView) {
		this.transparentView = transparentView;
	}
	
	/**
	 * eingestellten Infotext abrufen
	 * @return
	 */
	public String getInfoLine() {
		return infoLine;
	}
	
	/**
	 * Infotext setzen
	 * @param infoLine Text 
	 */
	public void setInfoLine(String infoLine) {
		this.infoLine = infoLine;
	}
	
	/**
	 * Geookordinate anhand einer Mausposition berechnen
	 * @param mousepoint Mausposition
	 * @param surface ID Würfelfläche
	 * @return Geookordinate
	 */
	public Coordinate getGeoPositionFromMousePoint(Point2D mousepoint,String surface)
	{
		float ix=0.19f*(float)this.getWidth();
		float iex=0.81f*(float)this.getWidth();
		float iy=0.19f*(float)this.getHeight();
		float iey=0.81f*(float)this.getHeight();
				
		Coordinate c1=this.surfaces.get(surface).getMapExtract().get(0);			
		Coordinate c2=this.surfaces.get(surface).getMapExtract().get(2);
		
		double difx=0.0;
		double dify=0.0;
		
		if(surface==SURFACE_FRONT)
		{
			difx=mousepoint.getX()/this.getWidth();
			dify=mousepoint.getY()/this.getHeight();
		}
		
		if(surface==SURFACE_BOTTOM)
		{
			float _c=(float)Math.sqrt(ix*ix+iy*iy);
			float _c2=_c*((float)this.getHeight()-(float)mousepoint.getY())/iy;
			float ix2=(float)Math.sqrt(_c2*_c2-((float)this.getHeight()-(float)mousepoint.getY())*((float)this.getHeight()-(float)mousepoint.getY()));
			if(mousepoint.getX()<ix2 || mousepoint.getX()>this.getWidth()-ix2)
				return null;
			difx=((float)mousepoint.getX()-ix2)/(this.getWidth()-2*ix2);
			if(mousepoint.getY()<iey)
				return null;				
			dify=(iy-(mousepoint.getY()-iey))/iy;		
		}

		if(surface==SURFACE_TOP)
		{
			float _c=(float)Math.sqrt(ix*ix+iy*iy);
			float _c2=_c*(float)mousepoint.getY()/iy;
			float ix2=(float)Math.sqrt(_c2*_c2-(float)mousepoint.getY()*(float)mousepoint.getY());
			
			if(mousepoint.getX()<ix2 || mousepoint.getX()>this.getWidth()-ix2)
				return null;
			difx=((float)mousepoint.getX()-ix2)/(this.getWidth()-2*ix2);
			if(mousepoint.getY()>iy)
				return null;			
			dify=(iy-mousepoint.getY())/iy;		
					
		}

		if(surface==SURFACE_RIGHT)
		{
			float _c=(float)Math.sqrt(ix*ix+iy*iy);
			float _c2=_c*((float)this.getWidth()-(float)mousepoint.getX())/ix;
			float iy2=(float)Math.sqrt(_c2*_c2-((float)this.getWidth()-(float)mousepoint.getX())*((float)this.getWidth()-(float)mousepoint.getX()));
			if(mousepoint.getY()<iy2 || mousepoint.getY()>this.getHeight()-iy2)
				return null;
			dify=((float)mousepoint.getY()-iy2)/(this.getHeight()-2*iy2);
			if(mousepoint.getX()<iex)
				return null;				
			difx=(ix-(mousepoint.getX()-iex))/ix;			
		}

		if(surface==SURFACE_LEFT)
		{
			float _c=(float)Math.sqrt(ix*ix+iy*iy);
			float _c2=_c*(float)mousepoint.getX()/ix;
			float iy2=(float)Math.sqrt(_c2*_c2-(float)mousepoint.getX()*(float)mousepoint.getX());			
			if(mousepoint.getY()<iy2 || mousepoint.getY()>this.getHeight()-iy2)
				return null;
			dify=((float)mousepoint.getY()-iy2)/(this.getHeight()-2*iy2);
			if(mousepoint.getX()>ix)
				return null;		
			difx=(mousepoint.getX())/ix;			

		}
		double geo_dif_x=difx*(c2.x-c1.x);
		double geo_dif_y=dify*(c2.y-c1.y);
		Coordinate mouseGeoPos=new Coordinate(c1.x+geo_dif_x,c1.y+geo_dif_y);
		return mouseGeoPos;
	}
	
	/**
	 * ist Geoobjekt ausgewählt
	 * @param geoObject Geoobjekt 
	 * @param mousepoint Mausposition
	 * @return ist Geoobjekt ausgewählt
	 */
	public boolean isGeoobjectSelected(GeoObject geoObject,Point2D mousepoint)	{
		String[] i_surfaces=new String[]{SURFACE_FRONT,SURFACE_BOTTOM,SURFACE_TOP,SURFACE_RIGHT,SURFACE_LEFT};
		Coordinate c_object=geoObject.getGeometry().getCoordinate();
		for(String i_surface:i_surfaces)
		{
			Coordinate c=this.getGeoPositionFromMousePoint(mousepoint, i_surface);
			if(c!=null)
			{
				double maxDistance=0.0015;
				if(!i_surface.equals(SURFACE_FRONT))
					maxDistance=0.008;
				if(c_object.distance(c)<maxDistance)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * zentrale Geoposition im dargestelletn Kartenausschnitt ermitteln
	 * @return zentrale Geoposition im dargestelletn Kartenausschnitt
	 */
	public Coordinate getCenterGeoposition()
	{
		double difx=(this.mapExtract.get(2).x-this.mapExtract.get(0).x)*0.5;
		double dify=(this.mapExtract.get(2).y-this.mapExtract.get(0).y)*0.5;
				
		Coordinate mid=new Coordinate(this.mapExtract.get(0).x+difx,this.mapExtract.get(0).y+dify);
		return mid;
	}

	/**
	 * Abstand eine Geobjekt vom Zentrum
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public float getRealDistance(GeoObject geoObject)
	{
		Coordinate cgeo=this.getCenterGeoposition();
		Coordinate gogeo=geoObject.getGeometry().getCoordinate();
		double difx=(cgeo.x-gogeo.x);
		double dify=(cgeo.y-gogeo.y);
		float dist=(float)Math.sqrt((difx*difx)+(dify*dify));
		dist=dist*136.4280f;
		return dist;		
	}

	/**
	 * dargestellten Infotext abrufen
	 * @return dargestellten Infotext
	 */
	public String getInfoText() {
		return infoText;
	}
	
	/**
	 * Infotext setzen
	 * @param infoText Infotext
	 */
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}		

	/**
	 * eingestelltest Linking und Brushing Symbol abrufen
	 * @return Linking und Brushing Symbol
	 */
	public int getLinkingAndBrushingSymbol() {
		return linkingAndBrushingSymbol;
	}
	
	/**
	 * Linking und Brushing Symbol setzen
	 * @param linkingAndBrushingSymbol Linking und Brushing Symbol
	 */
	public void setLinkingAndBrushingSymbol(int linkingAndBrushingSymbol) {
		this.linkingAndBrushingSymbol = linkingAndBrushingSymbol;
	}
	
	/**
	 * prüfen ob ein Button im Infotext angeklickt wurde
	 * @param mousePoint Mausposition
	 * @param button Typ Button
	 * @return
	 */
	public boolean isInfoTextClicked(Point2D mousePoint,int button)
	{
		float top=0.0f;
		float bottom=0.0f;
		float left=0.0f;
		float right=0.0f;
		
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_OK)
		{
			left=0.38f;
			right=0.72f;
			top=-0.98f;
			bottom=-1.22f;
		}
		
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_CONFIRM_NO)
		{
			left=0.38f;
			right=0.72f;
			top=-0.98f;
			bottom=-1.22f;
		}
		
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_CONFIRM_YES)
		{
			left=-0.02f;
			right=0.32f;
			top=-0.98f;
			bottom=-1.22f;
		}
		
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_SEL_PERSON)
		{
			left=-0.38f;
			right=0.42f;
			top=-0.98f;
			bottom=-1.22f;
		}
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_SEL_RESTAURANT)
		{
			left=-0.38f;
			right=0.42f;
			top=-0.68f;
			bottom=-0.92f;
		}
		if(button==DisplayFunctions.INFO_TEXT_BUTTON_SEL_FUELSTATION)
		{
			left=-0.38f;
			right=0.42f;
			top=-0.38f;
			bottom=-0.62f;
		}
		
		
		float mx=((float)mousePoint.getX()/(float)this.getWidth()*2.0f)-1.0f;
		float my=(((float)mousePoint.getY()/(float)this.getHeight()*3.0f)-1.5f)*(-1.0f);
		if(mx>left && mx<right && my<top && my>bottom)
			return true;
		return false;
	}
	
	/**
	 * prüfen, ob das Linking und Brushing Symbol angeklickt wurde 
	 * @param mousePoint Mausposition
	 * @return
	 */
	public boolean isLinkingAndBrushingSymbolClicked(Point2D mousePoint)
	{
		if(this.linkingAndBrushingSymbol==-1)
			return false;
		float mx=((float)mousePoint.getX()/(float)this.getWidth()*2.0f)-1.0f;
		float my=(((float)mousePoint.getY()/(float)this.getHeight()*3.0f)-1.5f)*(-1.0f);
		if(mx>0.58f && mx<0.82f && my>1.18f && my<1.42f)
			return true;
		return false;
	}
	
	/**
	 * prüfen ob das Zoom Symbol geklikt wurde
	 * @param mousePoint Mausposition
	 * @param plus plust taste prüfen
	 * @return
	 */
	public boolean isZoomSymbolClicked(Point2D mousePoint,boolean plus)
	{
		if(plus)
		{
			float mx=((float)mousePoint.getX()/(float)this.getWidth()*2.0f)-1.0f;
			float my=(((float)mousePoint.getY()/(float)this.getHeight()*3.0f)-1.5f)*(-1.0f);
			if(mx>0.84f && mx<0.99f && my>1.35f && my<1.49f)
				return true;
			return false;
		}
		else
		{
			float mx=((float)mousePoint.getX()/(float)this.getWidth()*2.0f)-1.0f;
			float my=(((float)mousePoint.getY()/(float)this.getHeight()*3.0f)-1.5f)*(-1.0f);
			if(mx>0.84f && mx<0.99f && my>1.21f && my<1.35f)
				return true;
			return false;			
		}
	}
	
	/**
	 * prüfen, ob das Beenden Symbol geklickt wurde
	 * @param mousePoint Mausposition
	 * @return
	 */
	public boolean isEndSymbolClicked(Point2D mousePoint)
	{

		float mx=((float)mousePoint.getX()/(float)this.getWidth()*2.0f)-1.0f;
		float my=(((float)mousePoint.getY()/(float)this.getHeight()*3.0f)-1.5f)*(-1.0f);
		if(mx>0.84f && mx<0.99f && my>1.35f && my<1.49f)
			return true;
		return false;

	}
	
	/**
	 * prüfen, ob das Geoobjekt im Fokus sichtbar ist 
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean isVisibleInFocus(GeoObject geoObject)
	{
		return this.surfaces.get(SURFACE_FRONT).isGeoObjectVisible(geoObject);
	}
	
	/**
	 * prüfen, ob das Geoobjekt im Kontext sichtbar ist 
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean isVisibleInContext(GeoObject geoObject)
	{
		String[] surface_keys={MapCubePanel.SURFACE_BOTTOM,MapCubePanel.SURFACE_LEFT,MapCubePanel.SURFACE_RIGHT,MapCubePanel.SURFACE_TOP};
		for(String surface_key:surface_keys)
		{
			if(this.surfaces.get(surface_key).isGeoObjectVisible(geoObject))
				return true;
		}
		return false;
	}
	
	/**
	 * prüfen, ob das Geoobjekt in einer bestimmten Würfelfläche sichtbar ist 
	 * @param geoObject Geoobjekt
	 * @param surface ID der der Würfelfläche
	 * @return
	 */
	public boolean isVisibleInSurface(GeoObject geoObject,String surface)
	{
		if(this.surfaces.get(surface).isGeoObjectVisible(geoObject))
			return true;
		return false;
	}
	
	/**
	 * Lichtuelle (OpenGL) einstellen 
	 * @param drawable drawable OpenGL
	 */
	public void setLightSource(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		
		gl.glEnable(GL.GL_LIGHTING);
		
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { 0.0f, 0.0f, -2, 1 };
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE,
				makeDirectFloatBuffer(diffuse));
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION,
				makeDirectFloatBuffer(position));
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT1);
		
		// Gourard Shading aktivieren
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_NORMALIZE);
	}
	
	/**
	 * Objekt zum Hervorheben einstellen
	 * @param geoObject Geoobjekt
	 */
	public void addHighlightedGeobject(GeoObject geoObject)
	{
		this.highlightedGeoObjects.add(geoObject);
		geoObject.setHighlighed(true);
		this.removedHighlightedGeoObjects.remove(geoObject);
	}
	
	/**
	 * Hervorhebung eine Objekts wieder entfernen
	 * @param geoObject Geoobjekt
	 */
	public void removeHightedGeobject(GeoObject geoObject)
	{
		this.highlightedGeoObjects.remove(geoObject);
		geoObject.setHighlighed(false);
		this.removedHighlightedGeoObjects.add(geoObject);		
	}
	
	/**
	 * Geoobjekt mit Hinweis versehen
	 * @param geoObject Geoobjekt
	 */
	public void addPointedGeobject(GeoObject geoObject)
	{
		this.pointedGeoObjects.add(geoObject);		
	}
	
	/**
	 * Hinweis auf Objekt entfernen
	 * @param geoObject Geoobjekt
	 */
	public void removePointedGeobject(GeoObject geoObject)
	{
		this.pointedGeoObjects.remove(geoObject);				
	}
	
	/**
	 * Beschriftung für Objekt sperren
	 * @param geoObject Geoobjekt
	 */
	public void addNoScriptGeobject(GeoObject geoObject)
	{
		this.noScriptGeoObjects.add(geoObject);		
	}
	
	/**
	 * Beschriftungssperre für Objekt aufheben
	 * @param geoObject Geoobjekt
	 */
	public void removeNoScriptGeobject(GeoObject geoObject)
	{
		this.noScriptGeoObjects.remove(geoObject);				
	}
	
	/**
	 * prüfen, ob auf Geoobjekt eine Beschriftungssperre aktiv ist  
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean isNoScriptGeoObject(GeoObject geoObject)
	{
		return this.noScriptGeoObjects.contains(geoObject);
	}

	/**
	 * Schildhinweis für ein Geobjekt aktivieren
	 * @param geoObject Geoobjekt
	 */
	public void addWithSignGeoObject(GeoObject geoObject)
	{
		this.withSignGeoObjects.add(geoObject);		
	}
	
	/**
	 * Schildhinweis für ein Geobjekt aufheben
	 * @param geoObject Geoobjekt
	 */
	public void removeWithSignGeoObject(GeoObject geoObject)
	{
		this.withSignGeoObjects.remove(geoObject);				
	}
	
	/**
	 * prüfen, ob ein Geoobjekt einen Schildhinweis hat
	 * @param geoObject
	 * @return
	 */
	public boolean isWithSignGeoObject(GeoObject geoObject)
	{
		return this.withSignGeoObjects.contains(geoObject);
	}
	
	/**
	 * prüfen, ob ein Objekt ausgewählt wurde
	 * @param mousepoint Mausposition
	 * @param context_region in der Kontext Region
	 * @return
	 */
	public GeoObject checkForSeletedGeoObject(Point2D mousepoint,boolean context_region)
	{
		
		ArrayList<String> sel_surfaces=new ArrayList<String>();
		if(!context_region)
			sel_surfaces.add(SURFACE_FRONT);
		else
		{
			sel_surfaces.add(SURFACE_TOP);
			sel_surfaces.add(SURFACE_BOTTOM);
			sel_surfaces.add(SURFACE_LEFT);
			sel_surfaces.add(SURFACE_RIGHT);
		}
		GeoObject foundObject=null;
		
		for(String sel_surface:sel_surfaces)
		{
			Point pickPoint=new GeometryFactory().createPoint(this.getGeoPositionFromMousePoint(mousepoint,sel_surface));
			Coordinate c1=this.surfaces.get(sel_surface).getMapExtract().get(0);			
			Coordinate c2=this.surfaces.get(sel_surface).getMapExtract().get(2);
			double geo_witdh=c2.x-c1.x;
			
			float tolerance=(float)(geo_witdh*10/this.getWidth());
			
			float foundDistance=1000000.0f;
			
			for(GeoObject geoObject:this.geoObjects)
			{
				if(geoObject.isSelectable())
				{
					if(geoObject.getGeometry() instanceof Polygon)
					{
						if(((Polygon)geoObject.getGeometry()).contains(pickPoint))
						{
							foundObject=geoObject;
							foundDistance=0.0f;
							break;
						}
					}
					else if(geoObject.getGeometry() instanceof LineString)
					{
						float cDistance=(float)((LineString)geoObject.getGeometry()).distance(pickPoint);
						if(cDistance<=tolerance && cDistance<foundDistance)
						{
							foundObject=geoObject;
							foundDistance=cDistance;
						}
					}
					else if(geoObject.getGeometry() instanceof Point)
					{
						float cDistance=(float)((Point)geoObject.getGeometry()).distance(pickPoint);
						if(cDistance<=tolerance && cDistance<foundDistance)
						{
							foundObject=geoObject;
							foundDistance=cDistance;
						}
					}
				}
			}
		}
		return foundObject;
	}
	
	/**
	 * prüfen, ob das Geoobjekt in der Fokus Region sichtbar ist
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean isGeoObjectVisibleInFocus(GeoObject geoObject)
	{
		return this.surfaces.get(SURFACE_FRONT).isGeoObjectVisible(geoObject);
	}
	
	/**
	 * prüfen, ob das Geoobjekt in der Kontext Region sichtbar ist
	 * @param geoObject Geoobjekt
	 * @return
	 */
	public boolean isGeoObjectVisibleInContext(GeoObject geoObject)
	{
		if(this.surfaces.get(SURFACE_LEFT).isGeoObjectVisible(geoObject))
			return true;
		if(this.surfaces.get(SURFACE_RIGHT).isGeoObjectVisible(geoObject))
			return true;
		if(this.surfaces.get(SURFACE_TOP).isGeoObjectVisible(geoObject))
			return true;
		if(this.surfaces.get(SURFACE_BOTTOM).isGeoObjectVisible(geoObject))
			return true;		
		return false;
	}
	
	/**
	 * ausgewähltes Objekt abrufen
	 * @return Geoobjekt
	 */
	public GeoObject getSelectedObject() {
		return selectedObject;
	}

	/**
	 * ausgewähltes Objekt setzen
	 * @param selectedObject Geoobjekt
	 */
	public void setSelectedObject(GeoObject selectedObject) {
		this.selectedObject = selectedObject;
	}
	
	/**
	 * Transparenzwert abrufen
	 * @return Transparenzwert
	 */
	public float getAlphaTransparency() {
		return alphaTransparency;
	}

	/**
	 * Transparenzwert setzen
	 * @param alphaTransparency Transparenzwert
	 */
	public void setAlphaTransparency(float alphaTransparency) {
		this.alphaTransparency = alphaTransparency;
	}
	
	/**
	 * Abstand einer Koordinate zur Fokus Region ermitteln
	 * @param coordinate Geokoordinate
	 * @return Abstand
	 */
	public float getDistanceToFocusRegion(Coordinate coordinate)
	{
		Point2D cartesian=this.surfaces.get(SURFACE_FRONT).getCartesianPointFromGeoPoint(coordinate,false,false);
		float distx=0.0f;
		if(cartesian.getX()<0)
			distx=(float)cartesian.getX()*(-1.0f);
		else if(cartesian.getX()>this.surfaces.get(SURFACE_FRONT).getWidth())
			distx=(float)(cartesian.getX()-this.surfaces.get(SURFACE_FRONT).getWidth());
		float disty=0.0f;
		if(cartesian.getY()<0)
			distx=(float)cartesian.getY()*(-1.0f);
		else if(cartesian.getY()>this.surfaces.get(SURFACE_FRONT).getHeight())
			disty=(float)(cartesian.getY()-this.surfaces.get(SURFACE_FRONT).getHeight());
		
		float dist=(float)Math.sqrt(distx*distx+disty*disty);
		
		return dist;
	}
	
	/**
	 * heran- oder herauszoomen
	 * @param zoomin heranzoomen
	 */
	public void zoom(boolean zoomin)
	{
		Coordinate c0=this.mapExtract.get(0);			
		Coordinate c1=this.mapExtract.get(2);
		float w=(float)c1.x-(float)c0.x;
		float h=(float)c0.y-(float)c1.y;
		if(zoomin)
		{
			this._zoom++;
			MapCubeTextureProvider.getInstance().setZoom(MapCubeTextureProvider.getInstance().getZoom()-1);
			c0.x=c0.x+0.25f*w;
			c0.y=c0.y-0.25f*h;
			c1.x=c1.x-0.25f*w;
			c1.y=c1.y+0.25f*h;			
		}
		else
		{
			this._zoom--;
			MapCubeTextureProvider.getInstance().setZoom(MapCubeTextureProvider.getInstance().getZoom()+1);
			c0.x=c0.x-0.5f*w;
			c0.y=c0.y+0.5f*h;
			c1.x=c1.x+0.5f*w;
			c1.y=c1.y-0.5f*h;						
		}
		this.fitSurfaceRects();					
	    
	    MapCubeTextureProvider.getInstance().rebuild();
	}
	
	/**
	 * Verbindungsstücke zwischen Fokus und Kontext Region zeichnen
	 * @param drawable drawable OpenGL
	 * @param semanticZoom smenatischer Zoom
	 */
	public void drawConnectorWedges(GLAutoDrawable drawable,int semanticZoom)
	{
		ArrayList<Coordinate> me_front=this.surfaces.get(SURFACE_FRONT).getMapExtract();
		GL gl = drawable.getGL();	
		gl.glPushMatrix();
		gl.glEnable(GL.GL_LIGHTING);
		
		Coordinate[] coordinates=new Coordinate[5];		
				
		coordinates[0]=me_front.get(0);
		coordinates[1]=me_front.get(1);
		coordinates[2]=me_front.get(2);
		coordinates[3]=me_front.get(3);
		coordinates[4]=me_front.get(0);
		
		Polygon hull=new GeometryFactory().createPolygon(new GeometryFactory().createLinearRing(coordinates),null);
		LinearRing hull_lr=new GeometryFactory().createLinearRing(coordinates);
		
		this.surfaces.get(SURFACE_FRONT).positionGLToSurface(drawable);
		
		for(GeoObject geoObject:this.geoObjects)
		{
			if(this.getSurfaces().get(MapCubePanel.SURFACE_FRONT).semanticZoom(semanticZoom, false, geoObject) && this.getSurfaces().get(MapCubePanel.SURFACE_LEFT).semanticZoom(semanticZoom, false, geoObject))
			{
				if (geoObject.getGeometry() instanceof LineString || geoObject.getGeometry() instanceof Polygon/*&& geoObject.getName().equals("Odenthaler Strasse")*/)
				{
					if(geoObject.getGeometry().crosses(hull) || hull.intersects(geoObject.getGeometry()))
					{
						Coordinate[] linePoints=null;
						if(geoObject.getGeometry() instanceof LineString)
						{
							LineString line=(LineString)geoObject.getGeometry();
							linePoints=line.getCoordinates();						
						}
						else if(geoObject.getGeometry() instanceof Polygon)
						{
							Polygon polygon=(Polygon)geoObject.getGeometry();
							linePoints=polygon.getCoordinates();	
						}
							
							
						for(int i=1;i<linePoints.length;i++)
						{
							Coordinate[] linePartC=new Coordinate[2];
							linePartC[0]=linePoints[i-1];
							linePartC[1]=linePoints[i];
							LineString linePart=new GeometryFactory().createLineString(linePartC);
							//if(linePart.crosses(hull) ||
							if(hull.intersects(linePart))
							{
								Coordinate[] intersect_coords=new Coordinate[0];
								Geometry intersect_geometry=hull_lr.intersection(linePart);
								if (intersect_geometry instanceof Point)
								{
									intersect_coords=new Coordinate[1];
									intersect_coords[0]=intersect_geometry.getCoordinate();
								}														
								else if (intersect_geometry instanceof MultiPoint)
								{
									intersect_coords=intersect_geometry.getCoordinates();								
								}
								
								for(Coordinate intersect_coord:intersect_coords)
								{								
									
									Coordinate coordinate2=intersect_coord;		
									Coordinate coordinate1=null;
									Coordinate coordinate3=null;
									if(this.surfaces.get(SURFACE_FRONT).isCoordinateVisible(linePartC[0]))
									{
										coordinate1=linePartC[0];
										coordinate3=linePartC[1];
									}
									else
									{
										coordinate1=linePartC[1];
										coordinate3=linePartC[0];
									}	
									Point2D p1=this.surfaces.get(SURFACE_FRONT).getCartesianPointFromGeoPoint(coordinate1);
									Point2D p2=this.surfaces.get(SURFACE_FRONT).getCartesianPointFromGeoPoint(coordinate2);
									
									Point2D p3=null;
									float v1x=0.0f;
									float v1y=0.0f;
									float v1z=0.0f;							
									float v2x=0.0f;
									float v2y=0.0f;
									float v2z=0.0f;															
									float v3x=0.0f;
									float v3y=0.0f;
									float v3z=0.0f;							
									
									String second_surface="";
									
									if(this.surfaces.get(SURFACE_LEFT).isCoordinateVisible(coordinate3))
									{
										second_surface=SURFACE_LEFT;
										p3=this.surfaces.get(SURFACE_LEFT).getCartesianPointFromGeoPoint(coordinate3);
										v3y=(float)p3.getY();
										v3z=(float)(2.0f+p3.getX())*(-1.0f);
										v3x=(float)p2.getX();
										
									}
									else if(this.surfaces.get(SURFACE_RIGHT).isCoordinateVisible(coordinate3))
									{
										second_surface=SURFACE_RIGHT;
										p3=this.surfaces.get(SURFACE_RIGHT).getCartesianPointFromGeoPoint(coordinate3);
										v3y=(float)p3.getY();
										v3z=(float)p3.getX();
										v3x=(float)p2.getX();
									}
									else if(this.surfaces.get(SURFACE_TOP).isCoordinateVisible(coordinate3))
									{
										second_surface=SURFACE_TOP;
										p3=this.surfaces.get(SURFACE_TOP).getCartesianPointFromGeoPoint(coordinate3);
										v3x=(float)p3.getX();
										v3z=(float)-(2.0f-p3.getY());
										v3y=(float)p2.getY();
		
									}
									else if(this.surfaces.get(SURFACE_BOTTOM).isCoordinateVisible(coordinate3))
									{
										second_surface=SURFACE_BOTTOM;
										p3=this.surfaces.get(SURFACE_BOTTOM).getCartesianPointFromGeoPoint(coordinate3);
										v3x=(float)p3.getX();
										v3z=(float)-p3.getY();
										v3y=(float)p2.getY();
									}
									
									if(!second_surface.equals(""))
									{
										v1x=(float)p1.getX();
										v1y=(float)p1.getY();
										v1z= 0.0f;
										v2x=(float)p2.getX();
										v2y=(float)p2.getY();
										v2z= 0.0f;										
										
										float maxl=0.15f;
										
										float distx1=v1x-v2x;
										float disty1=v1y-v2y;
										float length1=(float)Math.sqrt(distx1*distx1+disty1*disty1);
										if(length1>maxl)
										{
											v1x=v2x+distx1*maxl/length1;									
											v1y=v2y+disty1*maxl/length1;
										}
				
										float distx2=v3x-v2x;
										float disty2=v3y-v2y;
										float distz2=v3z-v2z;
										float length2=0.0f;
										if(second_surface.equals(SURFACE_BOTTOM) || second_surface.equals(SURFACE_TOP))
											length2=(float)Math.sqrt(distx2*distx2+distz2*distz2);
										if(second_surface.equals(SURFACE_LEFT) || second_surface.equals(SURFACE_RIGHT))
											length2=(float)Math.sqrt(disty2*disty2+distz2*distz2);
											
										if(length2>maxl)
										{
											if(second_surface.equals(SURFACE_BOTTOM) || second_surface.equals(SURFACE_TOP))
												v3x=v2x+distx2*maxl/length2;								
											if(second_surface.equals(SURFACE_LEFT) || second_surface.equals(SURFACE_RIGHT))
												v3y=v2y+disty2*maxl/length2;									
											v3z=v2z+distz2*maxl/length2;
										}
										
										float foot_width=0.02f;
										float top_width=0.02f;
		
										float v3x1=v3x;
										if(second_surface.equals(SURFACE_BOTTOM) || second_surface.equals(SURFACE_TOP))
										{
											if(second_surface.equals(SURFACE_BOTTOM))
												v3x1=v3x-distz2*foot_width/length2;
											else										
												v3x1=v3x+distz2*foot_width/length2;
										}
										float v3y1=v3y;
										if(second_surface.equals(SURFACE_LEFT) || second_surface.equals(SURFACE_RIGHT))
										{
											if(second_surface.equals(SURFACE_RIGHT))
												v3y1=v3y-distz2*foot_width/length2;
											else
												v3y1=v3y+distz2*foot_width/length2;
										}
										float v3z1=v3z-distz2*foot_width/length2;
		
										float v3x2=v3x;
										if(second_surface.equals(SURFACE_BOTTOM) || second_surface.equals(SURFACE_TOP))
										{
											if(second_surface.equals(SURFACE_BOTTOM))
												v3x2=v3x+distz2*foot_width/length2;
											else
												v3x2=v3x-distz2*foot_width/length2;
										}
										float v3y2=v3y;
										if(second_surface.equals(SURFACE_LEFT) || second_surface.equals(SURFACE_RIGHT))
										{
											if(second_surface.equals(SURFACE_RIGHT))
												v3y2=v3y+distz2*foot_width/length2;
											else
												v3y2=v3y-distz2*foot_width/length2;
										}
										float v3z2=v3z+distz2*foot_width/length2;
		
										
										float v1x1=v1x+disty1*top_width/length1;
										float v1y1=v1y-distx1*top_width/length1;								
										float v1x2=v1x-disty1*top_width/length1;
										float v1y2=v1y+distx1*top_width/length1;
										
										float v2x1=0.0f;
										float v2y1=0.0f;								
										float v2x2=0.0f;
										float v2y2=0.0f;
										
										if(second_surface.equals(SURFACE_LEFT) || second_surface.equals(SURFACE_RIGHT))
										{
											v2x1=v2x;																	
											v2x2=v2x;
											if(!second_surface.equals(SURFACE_LEFT))
											{
												v2y1=v2y+top_width;
												v2y2=v2y-top_width;
											}
											else
											{
												v2y1=v2y-top_width;
												v2y2=v2y+top_width;
											}
										}
										if(second_surface.equals(SURFACE_BOTTOM) || second_surface.equals(SURFACE_TOP))
										{									
											v2y1=v2y;																	
											v2y2=v2y;									
											if(!second_surface.equals(SURFACE_TOP))
											{
												v2x2=v2x-top_width;									
												v2x1=v2x+top_width;
											}
											else
											{
												v2x2=v2x+top_width;									
												v2x1=v2x-top_width;
											}
												
										}
																																		
										float vertices1[] = {
												v1x1,v1y1, v1z,
												v2x1,v2y1, v2z,
												v2x2,v2y2, v2z,
												v1x2,v1y2, v1z									
												};
										
										float normals1[] = {};
										DisplayFunctions.colorToGlColor(gl, geoObject.getFocusSignature().getColor(), AbstractSignature.BRIGHTNESS_DARK);
																		
										DisplayFunctions.drawVertexArrayObject(gl, vertices1, GL.GL_POLYGON,normals1);
		
										
										float vertices3[] = {
												v3x2,v3y2, v3z2,
												v1x2,v1y2, v1z,
												v1x1,v1y1, v1z,																				
												v3x1,v3y1, v3z1
												
												};
										
										float normals3[] = {
										};
										
										DisplayFunctions.drawVertexArrayObject(gl, vertices3, GL.GL_POLYGON,normals3);
		
																		
										float vertices4[] = {
												v1x1,v1y1, v1z,														
												v2x1,v2y1, v2z,
												v3x1,v3y1,v3z1
												};
										
										float normals4[] = {
										};
										
										//v3x1,v3y1,v3z1		
										DisplayFunctions.drawVertexArrayObject(gl, vertices4, GL.GL_POLYGON,normals4);
		
										float vertices5[] = {
												v1x2,v1y2, v1z,														
												v2x2,v2y2, v2z,
												v3x2,v3y2,v3z2																	
												};
										
										float normals5[] = {
										};
										
										DisplayFunctions.drawVertexArrayObject(gl, vertices5, GL.GL_POLYGON,normals5);
									}
								}
								
							}
						}
						
					}
				}
			}
		}	
		
		gl.glPopMatrix();
	}

	/**
	 * Mapcube Anzeige auf bestimmte Geokoordinate zentrieren
	 * @param coordinate Geokoordinate
	 */
	public void setToCoordinate(Coordinate coordinate)
	{
		double w=(this.mapExtract.get(2).x-this.mapExtract.get(0).x)/2.0;		
		double h=(this.mapExtract.get(2).y-this.mapExtract.get(0).y)/2.0;
		Coordinate c1=new Coordinate(coordinate.x-w,coordinate.y-h);
		Coordinate c2=new Coordinate(coordinate.x+w,coordinate.y-h);
		Coordinate c3=new Coordinate(coordinate.x+w,coordinate.y+h);
		Coordinate c4=new Coordinate(coordinate.x-w,coordinate.y+h);
		this.mapExtract.set(0,c1);
		this.mapExtract.set(1,c2);
		this.mapExtract.set(2,c3);
		this.mapExtract.set(3,c4);
		this.fitSurfaceRects();
	}
	
	/**
	 * Grenzen für das Panning einstellen
	 */
	public void autoClip()
	{
		double minx=this.getCenterGeoposition().x;
		double maxx=this.getCenterGeoposition().x;
		double miny=this.getCenterGeoposition().y;
		double maxy=this.getCenterGeoposition().y;
		
		for(GeoObject geoObject:this.geoObjects)
		{
			for(Coordinate c:geoObject.getGeometry().getCoordinates())
			{
				if(c.x<minx)
					minx=c.x;
				if(c.y<miny)
					miny=c.y;
				if(c.x>maxx)
					maxx=c.x;
				if(c.y>maxy)
					maxy=c.y;
			}
		}
		this.clipExtract=new ArrayList<Coordinate>();
		this.clipExtract.add(new Coordinate(minx,maxy));
		this.clipExtract.add(new Coordinate(maxx,miny));
	}
	
	
}
