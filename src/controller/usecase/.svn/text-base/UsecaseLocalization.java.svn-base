package controller.usecase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;
import view.ui.mapcube.DisplayFunctions;
import view.ui.mapcube.MapCubePanel;
import com.vividsolutions.jts.geom.Coordinate;
import controller.controlsystem.GeoObjectLoader;
import controller.controlsystem.HandleInput;
import model.geo_data.GeoObject;

/**
 * Usecase Ortsbestimmung
 * Legt den Benutzertest Ortsbestimmung an und steuert den Ablauf
 */
public class UsecaseLocalization extends TimerTask implements IUsecase,IObserver{

	public static final int PHASE_INTRO=0;
	public static final int PHASE_TEST1=1;
	public static final int PHASE_TEST2=1;
	
	private ArrayList<GeoObject> usecaseGeoObjects=null;
	private int phase=UsecaseLocalization.PHASE_TEST1; 
	
	private int alternativeTest=0;
	private boolean timerStarted=false;

	@Override
	/**
	 * Status des Benutzetests abrufen
	 */
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	/**
	 * Anwendungsfall initialisieren
	 */
	public void init() {
		//Benutzertest spezifische Objekte laden
		GeoObjectLoader.getInstance().setFile("....");
		if(this.alternativeTest==10 || this.alternativeTest==11)
			this.usecaseGeoObjects=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_USERTEST_LOCALIZATION2);
		else
			this.usecaseGeoObjects=GeoObjectLoader.getInstance().loadAndCreateObjects(GeoObject.USE_USERTEST_LOCALIZATION);
		
		for(GeoObject geoObject:this.usecaseGeoObjects)
		{		
			if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
				geoObject.setName(geoObject.getName().replace("H", ""));
			
			MapCubePanel.getInstance().addGeoObject(geoObject);
			
			if((this.alternativeTest==0 || this.alternativeTest==10) && geoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
			{
				MapCubePanel.getInstance().addHighlightedGeobject(geoObject);
				MapCubePanel.getInstance().addWithSignGeoObject(geoObject);

			}
			if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_SIGHTSEEING))
				MapCubePanel.getInstance().addNoScriptGeobject(geoObject);
		}
		
		MapCubePanel.getInstance().setUsecaseObjectText(true);

		//Eingabeverarbeitung einstellen
		HandleInput hi=HandleInput.getInstance();		
		hi.setDisplaySwitchByLeftMouseClick(false);						
		hi.setDisableTranslate(true);
		hi.setDisplayZoom(false);
		hi.setDisplaySwitchByLinkingAndBrushing(true);
		
		if(this.alternativeTest!=0 && this.alternativeTest!=10)
		{
			//Einstellungen für HALO
			if(this.alternativeTest==1 || this.alternativeTest==11)
				MapCubePanel.getInstance().setUseHaloCues(true);
			else if(this.alternativeTest==2)
			{			
				MapCubePanel.getInstance().setUseHaloCues(false);
				MapCubePanel.getInstance().setDistanceShow(true);			
			}	
			hi.setNoTransparentViewByLinkingAndBrushing(true);	
			MapCubePanel.getInstance().setShowPerceptionCues(false);
		}
		
		MapCubePanel.getInstance().setShowCenterCross(false);		
		MapCubePanel.getInstance().zoom(true);		
		Coordinate c_marker=new Coordinate(6.9365267637 , 50.9320386);
		if(this.alternativeTest==10 || this.alternativeTest==11)
			c_marker=new Coordinate(7.006016 , 50.8879243);
		
		MapCubePanel.getInstance().setToCoordinate(c_marker);
		MapCubePanel.getInstance().setInfoLine(DisplayFunctions.INFO_LINE_CENTER+"Bitte den gelben Start Knopf druecken");
		MapCubePanel.getInstance().setLinkingAndBrushingSymbol(DisplayFunctions.LINKINGBRUSHING_START);

	}

	@Override
	/**
	 * Auf Änderungen der Eingabeverarbeitung reagieren
	 */
	public void update() {
		// TODO Auto-generated method stub
		HandleInput hi=HandleInput.getInstance();
		
		if(this.phase==UsecaseLocalization.PHASE_TEST1)
		{
			if(hi.getHandleInputHistory()!=null && hi.getHandleInputHistory().isDisplaySwitchedByLinkingAndBrushing() && !hi.isDisplaySwitchedByLinkingAndBrushing())
			{
				//Linking und Brushing Symbol gedrückt
				MapCubePanel.getInstance().setInfoLine(null);
				MapCubePanel.getInstance().setLinkingAndBrushingSymbol(-1);
				if(!this.timerStarted)
				{
					//Timer auf der Webseite starten
					this.timerStarted=true;
					try {
				      UsecaseFacade.getInstance().getMainApplet().getAppletContext().showDocument
				        (new URL("javascript:makeDuration()"));
				      }
				    catch (MalformedURLException me) { }
				}
				
				if(this.alternativeTest==0 || this.alternativeTest==10)
				{
					//Ansicht umschalten
					MapCubePanel.getInstance().setTransparentView(true);
				}
				else
				{
					//HALOS einblenden
					for(GeoObject geoObject:this.usecaseGeoObjects)
						MapCubePanel.getInstance().addPointedGeobject(geoObject);
				}
				this.phase=UsecaseLocalization.PHASE_TEST2;
			}
		}
		else if(this.phase==UsecaseLocalization.PHASE_TEST2)
		{
		}
	}

	@Override
	/**
	 * zeitgesteuerter Ablauf
	 */
	public void run() {
		// TODO Auto-generated method stub
	}
		
	/**
	 * alternativer Test?
	 * @return
	 */
	public int isAlternativeTest() {
		return alternativeTest;
	}

	/**
	 * Status alternativer Test setzen
	 * @param alternativeTest
	 */
	public void setAlternativeTest(int alternativeTest) {
		this.alternativeTest = alternativeTest;
	}	
}
