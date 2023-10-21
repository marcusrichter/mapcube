package controller.usecase;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import model.geo_data.GeoObject;

import view.ui.mapcube.DisplayFunctions;
import view.ui.mapcube.MapCubePanel;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import controller.controlsystem.GeoObjectLoader;
import controller.controlsystem.HandleInput;

/**
 * Usecase Navigation
 * Legt die Demonstration des Usecase Navigation an und steuert den Ablauf
 */
public class UsecaseNavigation extends TimerTask implements IUsecase,IObserver{
	public static final int PHASE_1_TEXT=0;
	public static final int PHASE_NAVIGATION=1;
	public static final int PHASE_END=2;
	
	private float time=0;
	private int phase=PHASE_1_TEXT; 
	
	private Hashtable<String,GeoObject> usecaseGeoObjects=new Hashtable<String,GeoObject>();
	private ArrayList<GeoObject> usecaseGeoObjects2= new ArrayList<GeoObject>();
	private ArrayList<Coordinate> event_coordinates= new ArrayList<Coordinate>();

	private int currentRoutePoint=0;
	
	private int alternativeTest=0;	
	
	private int minEvent=0;
		
	private GeoObject alternativeRoute=null;		
	private boolean event=false;
	
		
	@Override
	/**
	 * Anwendungsfall initialisieren
	 */
	public void init() 
	{
		//Benutzertest spezifische Objekte laden
		//GeoObjectLoader.getInstance().setFile("....");
		
		MapCubePanel.getInstance().setRotate(280);
		ArrayList<GeoObject> _usecaseGeoObjects=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_USERTEST_NAVIGATION);
		
		for(GeoObject geoObject:_usecaseGeoObjects)
		{
			this.usecaseGeoObjects.put(geoObject.getName(), geoObject);
			MapCubePanel.getInstance().addGeoObject(geoObject);
		}
			
		this.usecaseGeoObjects2=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_USERTEST_NAVIGATION_PROACTIVE);
		for(GeoObject geoObject:this.usecaseGeoObjects2)
		{
			MapCubePanel.getInstance().addGeoObject(geoObject);
			MapCubePanel.getInstance().addHighlightedGeobject(geoObject);
			MapCubePanel.getInstance().addWithSignGeoObject(geoObject);
		}
		
				
		this.alternativeRoute=this.usecaseGeoObjects.get("Alternative Route");
		MapCubePanel.getInstance().addHighlightedGeobject(alternativeRoute);
		alternativeRoute.getFocusSignature().setColor(Color.RED);
		alternativeRoute.getContextSignature().setColor(Color.RED);
		MapCubePanel.getInstance().removeGeoObject(alternativeRoute);

		GeoObject route2start=this.usecaseGeoObjects.get("RouteStart");			
		
		// Anzeige und Eingabeverarbeitung anpassen
		MapCubePanel.getInstance().setToCoordinate(route2start.getGeometry().getCoordinate());
		
		//MapCubePanel.getInstance().setToCoordinate(new Coordinate(7.039295009406017, 50.940470317213915));
		//this.currentRoutePoint=40;
		
		HandleInput.getInstance().setDisableTranslate(true);
		HandleInput.getInstance().setDisplaySwitchByLeftMouseClick(false);
		MapCubePanel.getInstance().setShowCenterCross(true);
		MapCubePanel.getInstance().zoom(true);	
		MapCubePanel.getInstance().setZoomSymbol(false);
		
		if(this.alternativeTest>0)
			MapCubePanel.getInstance().setShowPerceptionCues(false);
		
		this.showIntroduction();
		
        Timer timer = new Timer();
        timer.schedule(this,
                       0,        //initial delay
                       1*10);  //subsequent rate
        

        Coordinate event_coordinate1=new Coordinate(6.896459849442122, 50.92598321742962);
        Coordinate event_coordinate2=new Coordinate(6.935314418067817, 50.942567050309734);
        Coordinate event_coordinate3=new Coordinate(7.039295009406017, 50.940470317213915);
        Coordinate event_coordinate4=new Coordinate(7.070942233014651, 51.00486990491467);
        Coordinate event_coordinate5=new Coordinate(7.014457014092504, 51.0240534389127);
        
        this.event_coordinates.add(event_coordinate1);
        this.event_coordinates.add(event_coordinate2);
        this.event_coordinates.add(event_coordinate3);
        this.event_coordinates.add(event_coordinate4);
        this.event_coordinates.add(event_coordinate5);
	}
	
	/**
	 * Einleitung anzeigen
	 */
	private void showIntroduction()
	{		
		String text="Bitte OK klicken, \num den Usecase zu starten.";
		MapCubePanel.getInstance().setInfoText(text);
	}

	
	@Override
	/**
	 * zeitgesteuerter Ablauf
	 */
	public void run() {
		// TODO Auto-generated method stub

		this.time+=0.01;
		if(!this.event)
			this.translate();

		this.specialObjectVisibleInContextRegion();

	}

	@Override
	/**
	 * Status abfragen
	 */
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	/**
	 * Auf Änderungen der Eingabeverarbeitung reagieren
	 */
	public void update() {
		// TODO Auto-generated method stub
			
		if(this.phase==UsecaseNavigation.PHASE_1_TEXT)
		{
			Point2D mousePos=HandleInput.getInstance().getMousePosition();
			if(MapCubePanel.getInstance().isInfoTextClicked(mousePos,DisplayFunctions.INFO_TEXT_BUTTON_OK) && HandleInput.getInstance().isLeftMousebuttonPressed())
			{
				this.phase=UsecaseNavigation.PHASE_NAVIGATION;
				MapCubePanel.getInstance().setInfoText(null);				
				//this.showCompleteRoute();
				this.time=0;
			}
		}
	}



	/**
	 * Text bei Ende des Benutzertests anzeigen
	 */
	private void showEndText()
	{		
		 try {
	      UsecaseFacade.getInstance().getMainApplet().getAppletContext().showDocument
	        (new URL("javascript:endNavigation()"));
	      }
	    catch (MalformedURLException me) { }
		    
		String text="Der Usecase ist beendet";
		MapCubePanel.getInstance().setInfoLine(text);
	}	
	
	
	/**
	 * Position auf der Route verschieben
	 * Anezige anhand der Position auf der Route verschieben und rotieren
	 */
	private void translate()
	{		
		double moveFactor2=0.00008;
		
		if(this.phase==UsecaseNavigation.PHASE_NAVIGATION)
		{
			//translate
			Coordinate currentMovepoint=this.usecaseGeoObjects.get("Route").getGeometry().getCoordinates()[currentRoutePoint+1];
			double difx=currentMovepoint.x-MapCubePanel.getInstance().getCenterGeoposition().x;
			double dify=currentMovepoint.y-MapCubePanel.getInstance().getCenterGeoposition().y;
			double length=Math.sqrt(difx*difx+dify*dify);
			if(length<moveFactor2)
			{
				this.currentRoutePoint++;
				if(this.currentRoutePoint>(this.usecaseGeoObjects.get("Route").getGeometry().getCoordinates().length)-2)
				{
					this.phase=UsecaseNavigation.PHASE_END;	
					this.showEndText();
				}
			}
			else
			{
				difx=difx*moveFactor2/length;
				dify=dify*moveFactor2/length;
				Coordinate newC=new Coordinate(MapCubePanel.getInstance().getCenterGeoposition().x+difx,MapCubePanel.getInstance().getCenterGeoposition().y+dify);
				MapCubePanel.getInstance().setToCoordinate(newC);
				this.checkEvent(newC, (float)moveFactor2);
			}
			//rotate
			
			float angle = (float) Math.atan2((float) MapCubePanel.getInstance().getCenterGeoposition().y
					- (float) currentMovepoint.y, (float) MapCubePanel.getInstance().getCenterGeoposition().x
					- (float) currentMovepoint.x)
					* 180 / (float) Math.PI;
			
			
			angle+=90.0f;
			if(angle<0.0f)
				angle+=360.0f;
			
			if(angle>360.0f)
				angle-=360.0f;
			
			if(Math.abs(angle-MapCubePanel.getInstance().getRotate())>3.0)
			{
				float rot=1.0f;
				float angleDiffRight=angle-MapCubePanel.getInstance().getRotate();
				if(angleDiffRight<0.0f)
					angleDiffRight+=360.0f;
				float angleDiffLeft=360.0f-angleDiffRight;
				if(angleDiffLeft<0.0f)
					angleDiffLeft+=360.0f;
			
				//MapCubePanel.getInstance().setTransparentView(true);
				float newRotate=MapCubePanel.getInstance().getRotate();
				if(angleDiffLeft>=angleDiffRight)
				{
					if(angleDiffRight<rot)
						newRotate+=angleDiffRight;
					else
						newRotate+=rot;						
				}
				else
				{
					if(angleDiffLeft<rot)
						newRotate-=angleDiffLeft;
					else
						newRotate-=rot;										
				}
				if(newRotate>=360.0f)
					newRotate-=360.0f;
				else if(newRotate<0.0f)
					newRotate+=360.0f;
				
				//lastAngleDiff=newRotate-MapCubePanel.getInstance().getRotate();
			 
				MapCubePanel.getInstance().setRotate(newRotate);				
				MapCubePanel.getInstance().fitSurfaceRects();
			}
			
			//MapCubePanel.getInstance().setInfoLine(""+angle+" "+MapCubePanel.getInstance().getRotate());
			
		}
	}
	
	/**
	 * 
	 * @param coord
	 */
	private void checkEvent(Coordinate coord,float maxdistance)
	{
		for(int i=this.minEvent;i<this.event_coordinates.size();i++)
		{
			if(this.event_coordinates.get(i).distance(coord)<maxdistance)
			{
				this.minEvent=i+1;
				this.event=true;
				 try {
				      UsecaseFacade.getInstance().getMainApplet().getAppletContext().showDocument
				        (new URL("javascript:eventNavigation("+i+")"));
				      }
				    catch (MalformedURLException me) { }
			}
				
		}
	}

	/**
	 * ist ein besonderes Objekt in der Nähe der Anzeige
	 * => Umschalten der Ansicht
	 */
	private void specialObjectVisibleInContextRegion()
	{		
		boolean visible=false;
	
		for(GeoObject geoObject:this.usecaseGeoObjects2)
		{			
			
			if(geoObject.getName().equals("Restaurant Sancho Pancha") && MapCubePanel.getInstance().isVisibleInFocus(geoObject))
			{
				while(MapCubePanel.getInstance().isDrawing());
				MapCubePanel.getInstance().removeGeoObject(geoObject);				
			}
				
			if(MapCubePanel.getInstance().isVisibleInContext(geoObject))
			{				

				if(this.alternativeTest==0)
				{
					MapCubePanel.getInstance().setTransparentView(true);
				}
				else
				{
					if(!MapCubePanel.getInstance().getPointedGeoObjects().contains(geoObject))
					{
						while(MapCubePanel.getInstance().isDrawing());
						MapCubePanel.getInstance().addPointedGeobject(geoObject);
					}
				}	
				//MapCubePanel.getInstance().setInfoLine(DisplayFunctions.INFO_LINE_CENTER+"Bitte den Punkt im Offscreen anklicken.");
				
				
				if(geoObject.getName().equals("Restaurant Sancho Pancha"))
				{
					if(MapCubePanel.getInstance().isVisibleInContext(this.alternativeRoute) && MapCubePanel.getInstance().isVisibleInFocus(this.alternativeRoute))
					{
						
						if(!MapCubePanel.getInstance().getGeoObjects().contains(this.alternativeRoute))
						{
							while(MapCubePanel.getInstance().isDrawing());
							MapCubePanel.getInstance().addGeoObject(this.alternativeRoute);
						}
					}
					else
					{
						if(MapCubePanel.getInstance().getGeoObjects().contains(this.alternativeRoute))
						{
							while(MapCubePanel.getInstance().isDrawing());
							MapCubePanel.getInstance().removeGeoObject(this.alternativeRoute);
						}
					}
				}
				
				visible=true;	

			}
		}
		if(!visible)
		{
			MapCubePanel.getInstance().setTransparentView(false);
			if(MapCubePanel.getInstance().getGeoObjects().contains(this.alternativeRoute))
			{
				while(MapCubePanel.getInstance().isDrawing());
				MapCubePanel.getInstance().removeGeoObject(this.alternativeRoute);
			}
		}
		//MapCubePanel.getInstance().setTransparentView(true);
	}
	
	/**
	 * Anwendungsfall fortsetzen, nachdem ein besonderes Objekt in der Kontext-Region angezeigt wurde
	 */
	public void continue_usecase()
	{
		MapCubePanel.getInstance().setInfoLine(null);
		this.event=false;
	}
}
