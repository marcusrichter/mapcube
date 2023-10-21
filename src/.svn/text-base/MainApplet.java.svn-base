import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.media.opengl.*;

import model.geo_data.GeoObject;
import view.ui.*;
import view.ui.mapcube.MapCubePanel;

import com.sun.opengl.util.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import controller.controlsystem.GeoObjectLoader;
import controller.controlsystem.HandleInput;
import controller.controlsystem.VisualMapper;
import controller.usecase.UsecaseFacade;
import controller.usecase.UsecaseNavigation;

/**
 * Das eigentliche Applet
 * Erzeugen der Swing Oberfläche
 */
public class MainApplet extends JApplet {	

	private JPanel panel;
	private MapCubePanel mapCubePanel;
	Animator animator;


	
	/**
	 * Applet initialisieren
	 */
	public void init() 
	{

		GeoObjectLoader.getInstance().setCodebase(this.getCodeBase());

		//Parmeter auswerten
		String paramUsecase = this.getMapcubeParameter("usecase","0");
		String paramDisplaytype = this.getMapcubeParameter("displaytype");
		if(paramDisplaytype==null)
			paramDisplaytype="VECTOR_VECTOR";

		String _paramConnectorWedges = this.getMapcubeParameter("connector_wedges");
		boolean paramConnectorWedges=false;
		if(_paramConnectorWedges!=null)
		{
			if(_paramConnectorWedges.equals("1"))
				paramConnectorWedges=true;
		}

		if(this.getMapcubeParameter("usecase")!=null)
			MapCubePanel.parameters.put("usecase",this.getMapcubeParameter("usecase"));
		MapCubePanel.parameters.put("displaytype",paramDisplaytype);
		if(this.getMapcubeParameter("connector_wedges")!=null)
			MapCubePanel.parameters.put("connector_wedges",this.getMapcubeParameter("connector_wedges","0"));
		if(this.getMapcubeParameter("focuscylinder")!=null)
			MapCubePanel.parameters.put("focuscylinder",this.getMapcubeParameter("focuscylinder","0"));
		
		
		//JOGL initialisieren
		GLCapabilities cap = new GLCapabilities();
		cap.setHardwareAccelerated(true);
		cap.setDoubleBuffered(false);

		//Panel und Swing Elemente anlegen
		panel = new JPanel(new BorderLayout());
		mapCubePanel = MapCubePanel.getInstance();		
		
		//Darstellungsart konfigurieren
		if(paramDisplaytype.equals("VECTOR_VECTOR") || paramDisplaytype.equals("VECTOR_TRAPEZ") || paramDisplaytype.equals("VECTOR_TRAPEZ_LARGE"))
			this.configureVectorVector();
		else if(paramDisplaytype.equals("RASTER_VECTOR"))
			this.configureRasterVector();
		else if(paramDisplaytype.equals("RASTER_WIREFRAME"))
			this.configureRasterWireframe();
		else if(paramDisplaytype.equals("VECTOR_WIREFRAME"))
			this.configureVectorWireframe();
		else if(paramDisplaytype.equals("WIREFRAME_WIREFRAME"))
			this.configureWireframeWireframe();
		else if(paramDisplaytype.equals("RASTER_RASTER"))
			this.configureRasterRaster();
		
		mapCubePanel.setConnectorWedges(paramConnectorWedges);
		
		this.panel.add(BorderLayout.CENTER, mapCubePanel);
	
		this.getContentPane().add(this.panel);

		AbstractPanel.panels.put(AbstractPanel.PANEL_MAPCUBE, (AbstractPanel)mapCubePanel);
				
		mapCubePanel.setLock(true);
		
		Coordinate c1=new Coordinate(6.852474 , 51.054143);
		Coordinate c2=new Coordinate(7.025958 , 51.054143);
		Coordinate c3=new Coordinate(7.025958 , 50.90183);
		Coordinate c4=new Coordinate(6.852474 , 50.90183);
		
		
		ArrayList<Coordinate> mapExtract=new ArrayList<Coordinate>();
		mapExtract.add(c1);
		mapExtract.add(c2);
		mapExtract.add(c3);
		mapExtract.add(c4);
		
		mapCubePanel.setMapExtract(mapExtract);		
		mapCubePanel.init();		

		MapCubePanel.getInstance().setPositionX(22291974);
		MapCubePanel.getInstance().setPositionY(175260);
		MapCubePanel.getInstance().setZoom(4);
		
		if(this.getMapcubeParameter("startpos")!=null)
		{
			String[] spiltCoordStr=this.getMapcubeParameter("startpos").split(",");			
			MapCubePanel.getInstance().setToCoordinate(new Coordinate(Float.parseFloat(spiltCoordStr[0]),Float.parseFloat(spiltCoordStr[1])));
		}
		else
			MapCubePanel.getInstance().setToCoordinate(new Coordinate(7.0251,50.999));
		
		if(paramUsecase.equals("DEMO_HALO"))
		{
			HandleInput hi=HandleInput.getInstance();					
			MapCubePanel.getInstance().setUseHaloCues(true);
			hi.setNoTransparentViewByLinkingAndBrushing(true);	
			MapCubePanel.getInstance().setShowPerceptionCues(false);									
		}
		
		if(paramUsecase.equals("DEMO_HALO") || paramUsecase.equals("DEMO_MAPCUBE"))
			MapCubePanel.getInstance().setToCoordinate(new Coordinate(6.962452977369902,50.91189946243345));

		MapCubePanel.getInstance().rebuild();

		if(paramUsecase.equals("NAVIGATION"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_NAVIGATION,0);
		else if(paramUsecase.equals("NAVIGATION_A"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_NAVIGATION,1);
		else if(paramUsecase.equals("LOCALIZATION_MAPCUBE"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_LOCALIZATION,0);
		else if(paramUsecase.equals("LOCALIZATION_HALOS"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_LOCALIZATION,1);
		else if(paramUsecase.equals("LOCALIZATION_MAPCUBE2"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_LOCALIZATION,10);
		else if(paramUsecase.equals("LOCALIZATION_HALOS2"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_LOCALIZATION,11);			
		else if(paramUsecase.equals("LOCALIZATION_ARROWS"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_LOCALIZATION,2);
		else if(paramUsecase.equals("SCATTERPLOTS"))
			UsecaseFacade.getInstance().createUsecase(UsecaseFacade.USERTEST_SCATTERPLOTS,0);


		if(this.getMapcubeParameter("no_objects")==null || !this.getMapcubeParameter("no_objects").equals("true"))
		{
			ArrayList<GeoObject> mapObjects=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_MAP);
			for(GeoObject geoObject:mapObjects)
			{		
				MapCubePanel.getInstance().addGeoObject(geoObject);
			}
		}
			
		if(paramUsecase.equals("0") || paramUsecase.equals("DEMO_HALO") || paramUsecase.equals("DEMO_MAPCUBE"))
		{
			
			MapCubePanel.getInstance().setUseHaloCues(true);
			
			if(paramUsecase.equals("DEMO_HALO") || paramUsecase.equals("DEMO_MAPCUBE"))
			{				
				ArrayList<GeoObject> mapObjects5=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_USERTEST_IDENTIFICATION);
				for(GeoObject geoObject:mapObjects5)
				{					
					if(paramUsecase.equals("DEMO_HALO"))
					{
						if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
						{
							MapCubePanel.getInstance().addPointedGeobject(geoObject);
							MapCubePanel.getInstance().addNoScriptGeobject(geoObject);
							
						}
					}
					
					MapCubePanel.getInstance().addGeoObject(geoObject);
				}
			}
			
		}
		
		if(this.getMapcubeParameter("show_zoom")!=null && this.getMapcubeParameter("show_zoom").equals("true"))
		{
			MapCubePanel.getInstance().zoom(true);
			MapCubePanel.getInstance().setZoomSymbol(true);		
			HandleInput.getInstance().setDisplayZoom(true);
		}
		
		mapCubePanel.setLock(false);
		
		UsecaseFacade.getInstance().setMainApplet(this);
		if(this.getMapcubeParameter("relation_lines")!=null && this.getMapcubeParameter("relation_lines").equals("true"))
			this.load_relation_lines();

		mapCubePanel.autoClip();
	
	}
	
	/**
	 * einen Parameter des Applets auslesen 
	 * @param paramName Name des Parameters
	 * @return
	 */
	private String getMapcubeParameter(String paramName)
	{
		return this.getMapcubeParameter(paramName, null);
	}
	
	/**
	 * 
	 * @param paramName
	 * @param ifnull
	 * @return
	 */
	private String getMapcubeParameter(String paramName,String ifnull)
	{
		try
		{			
			//String param=null;
			String param=getParameter(paramName);
			if(param==null)
				return ifnull;
			return param;
		}		
		catch(Exception ex)
		{
			return ifnull;
		}
	}
	
	/**
	 * Applet Konstruktor
	 */
	public MainApplet()
	{
		/*panel = new JPanel(new BorderLayout());
		mapCubePanel = new MapCubePanel();
		this.panel.add(BorderLayout.CENTER, mapCubePanel);
		//this.panel.add(BorderLayout.SOUTH, new JLabel("Map Cube Applet !"));
		this.getContentPane().add(this.panel);*/
	}
	
	/**
	 * Applet für die Vektor/Vektor Darstellung konfigurieren
	 */
	private void configureVectorVector()
	{
		
	}

	/**
	 * Applet für die Vektor/Drahtgitter Darstellung konfigurieren 
	 */
	private void configureVectorWireframe()
	{
		MapCubePanel.getInstance().setWireframeForSides(true);		
	}
	
	/**
	 * Applet für die Drahtgitter/Drahtgitter Darstellung konfigurieren 
	 */
	private void configureWireframeWireframe()
	{
		MapCubePanel.getInstance().setWireframeForSides(true);
		MapCubePanel.getInstance().setWireframeForFront(true);
	}
	
	/**
	 * Applet für die Raster/Drahtgitter Darstellung konfigurieren
	 */
	private void configureRasterWireframe()
	{
		MapCubePanel.getInstance().setMaxTransparency(0.6f);
		MapCubePanel.getInstance().setWireframeForSides(true);
		MapCubePanel.getInstance().setWireframeForFront(true);
		MapCubePanel.getInstance().setFront_tiles(true);
	}
	
	/**
	 * Applet für die Raster/Vektor Darstellung konfigurieren
	 */
	private void configureRasterVector()
	{
		MapCubePanel.getInstance().setMaxTransparency(0.6f);
		MapCubePanel.getInstance().setWireframeForSides(false);
		MapCubePanel.getInstance().setWireframeForFront(false);
		MapCubePanel.getInstance().setFront_tiles(true);
	}
	
	/**
	 * Applet für die Raster/Raster Darstellung konfigurieren
	 */
	private void configureRasterRaster()
	{
		MapCubePanel.getInstance().setMaxTransparency(0.5f);
		MapCubePanel.getInstance().setWireframeForSides(false);
		MapCubePanel.getInstance().setWireframeForFront(false);
		MapCubePanel.getInstance().setFront_tiles(true);
		MapCubePanel.getInstance().setSide_tiles(true);
	}

	/**
	 * senkrechte und waagerechte Linien laden 
	 */
	private void load_relation_lines()
	{
		int count=100;
		
		Coordinate c1=MapCubePanel.getInstance().getMapExtract().get(0);
		Coordinate c3=MapCubePanel.getInstance().getMapExtract().get(2);
		
		double left=c1.x;
		double right=c3.x;
		double top=c1.y;
		double bottom=c3.y;
		
		double difx=right-left;
		double dify=top-bottom;
		
		double _difx=difx*0.1;
		double _dify=dify*0.1;
		
		double startx=left-(count/2)*_difx;
		double endx=startx+count*_difx;
		double starty=top-(count/2)*_dify;
		double endy=starty+count*_dify;
		
		int iy=0;
		double cy=starty;
		while(iy<count)
		{
			
			GeoObject relLine=new GeoObject();
			
			Coordinate[] coords=new Coordinate[2];
			coords[0]=new Coordinate(startx,cy);
			coords[1]=new Coordinate(endx,cy);
			
			LineString ls= new GeometryFactory().createLineString(coords);
			relLine.setGeometry(ls);
			relLine.setObjtype(GeoObject.OBJTYPE_RELATION_LINE);
			relLine.setUse(GeoObject.USE_MAP);
			
			VisualMapper.getInstance().assignSignatures(relLine);
			
			MapCubePanel.getInstance().addGeoObject(relLine);
			cy+=_dify;					
			iy++;
		}
				
		int ix=0;
		double cx=startx;
		while(ix<count)
		{
			GeoObject relLine=new GeoObject();
			
			Coordinate[] coords=new Coordinate[2];
			coords[0]=new Coordinate(cx,starty);
			coords[1]=new Coordinate(cx,endy);
			
			LineString ls= new GeometryFactory().createLineString(coords);
			relLine.setGeometry(ls);
			relLine.setObjtype(GeoObject.OBJTYPE_RELATION_LINE);
			relLine.setUse(GeoObject.USE_MAP);
			
			VisualMapper.getInstance().assignSignatures(relLine);
			
			MapCubePanel.getInstance().addGeoObject(relLine);
			cx+=_difx;					
			ix++;
		}
		
	}
	
	/**
	 * Den Benutzertest Navigation weiterlaufen lassen
	 * für einen Javascript Aufruf
	 */
	public void event_continue()
	{
		UsecaseNavigation usertestNavigation=(UsecaseNavigation)UsecaseFacade.getInstance().getCurrentUserTest();
		usertestNavigation.continue_usecase();
	}
	
}
