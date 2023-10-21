package controller.controlsystem;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import view.ui.mapcube.MapCubePanel;

import controller.usecase.IObserver;

/**
 * Klasse verarbeitet die Benutzereingaben,
 * verschiebt die Anzeige und schaltet die Ansicht um 
 */
public class HandleInput implements Cloneable{

	private static HandleInput instance=null;
	private boolean leftMousebuttonPressed=false;
	private boolean rightMousebuttonPressed=false;
	private Point2D mousePosition;
	private Point2D dragMousePosition;
	private int zoom;
	private HandleInput handleInputHistory=null;
	private ArrayList<IObserver> observers=new ArrayList<IObserver>();
	private boolean displaySwitchByLeftMouseClick=true;
	private boolean displaySwitchByLinkingAndBrushing=false;	
	private boolean disableTranslate=false;
	private boolean displaySwitchedByLinkingAndBrushing=false;
	private boolean noTransparentViewByLinkingAndBrushing=false;

	private boolean displayZoom=false;

	private boolean zoomedIn=false;
	private boolean zoomedOut=false;
	
	private ArrayList<Coordinate> clipRegion=new ArrayList<Coordinate>();

	private int lastaction=-1;

	public final static int INPUT_TYPE_MOUSEMOVE=0;
	public final static int INPUT_TYPE_LEFTMOUSEBUTTON_PRESSED=1;
	public final static int INPUT_TYPE_RIGHTMOUSEBUTTON_PRESSED=2;
	public final static int INPUT_TYPE_LEFTMOUSEBUTTON_RELEASED=3;
	public final static int INPUT_TYPE_RIGHTMOUSEBUTTON_RELEASED=4;
	public final static int INPUT_TYPE_MOUSEWHEEL=5;

	/**
	 * Singleton für HandleInput abrufen
	 * @return
	 */
	public static HandleInput getInstance()
	{
		if(instance==null)
			instance=new HandleInput();
		return instance;
	}
	
	/**
	 * Linke Maustaste gedrückt?
	 * @return Linke Maustaste gedrückt?
	 */
	public boolean isLeftMousebuttonPressed() {
		return leftMousebuttonPressed;
	}

	/**
	 * Status (Linke Maustaste gedrückt?) setzen
	 * @param leftMousebuttonPressed neuer Status (Linke Maustaste gedrückt?)
	 */
	public void setLeftMousebuttonPressed(boolean leftMousebuttonPressed) {
		this.leftMousebuttonPressed = leftMousebuttonPressed;
	}

	/**
	 * Rechte Maustaste gedrückt?
	 * @return Rechte Maustaste gedrückt?
	 */
	public boolean isRightMousebuttonPressed() {
		return rightMousebuttonPressed;
	}

	/**
	 * Status (Rechte Maustaste gedrückt?) setzen
	 * @param rightMousebuttonPressed
	 */
	public void setRightMousebuttonPressed(boolean rightMousebuttonPressed) {
		this.rightMousebuttonPressed = rightMousebuttonPressed;
	}

	/**
	 * letzte Mausposition in der Anzeige abrufen
	 * @return letzte Mausposition 
	 */
	public Point2D getMousePosition() {
		return mousePosition;
	}

	/**
	 * letzte Mausposition setzen
	 * @param mousePosition Mausposition
	 */
	public void setMousePosition(Point2D mousePosition) {
		this.mousePosition = mousePosition;
	}

	/**
	 * aktuelle Zoomstufe abrufen 
	 * @return aktuelle Zoomstufe
	 */
	public int getZoom() {
		return zoom;
	}
	
	/**
	 * Zoomstufe setzen
	 * @param zoom
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * Instanz des HandleIinput mit vergangenen Status abrufen
	 * @return
	 */
	public HandleInput getHandleInputHistory() {
		return handleInputHistory;
	}

	/**
	 * Instanz des HandleInput mit vergangenen Status setzen
	 * @param handleInputHistory
	 */
	public void setHandleInputHistory(HandleInput handleInputHistory) {
		this.handleInputHistory = handleInputHistory;
	}
	
	/**
	 * letzte Mausposition(Drag) in der Anzeige abrufen
	 * @return Mausposition
	 */
	public Point2D getDragMousePosition() {
		return dragMousePosition;
	}

	/**
	 * letzte Mausposition(Drag) in der Anzeige setzen
	 * @param dragMousePosition Mausposition
	 */
	public void setDragMousePosition(Point2D dragMousePosition) {
		this.dragMousePosition = dragMousePosition;
	}
	
	/**
	 * letzte Aktion abrufen
	 * @return letzte Aktion 
	 */
	public int getLastaction() {
		return lastaction;
	}

	/**
	 * letzte Aktion setzen
	 * @param lastaction letze Aktion  
	 */
	public void setLastaction(int lastaction) {
		this.lastaction = lastaction;
	}
	
	/**
	 * letzte Verschiebung abrufen
	 * @return letzte Verschiebung
	 */
	public Point2D getLastTranslation()
	{
		Point2D oldMousePos=this.handleInputHistory.getMousePosition();
		Point2D pointTranslate=new Point2D.Double(oldMousePos.getX()-this.mousePosition.getX(),oldMousePos.getY()-this.mousePosition.getY());
		return pointTranslate;		
	}
	
	/**
	 * letzte Verschiebung(Drag) abrufen
	 * @return letzte Verschiebung
	 */
	public Point2D getLastDragTranslation()
	{
		Point2D oldMousePos=this.handleInputHistory.getDragMousePosition();
		Point2D pointTranslate=new Point2D.Double(oldMousePos.getX()-this.dragMousePosition.getX(),oldMousePos.getY()-this.dragMousePosition.getY());
		return pointTranslate;		
	}
	
	/**
	 * Verschiebung der Anzeige abgeschaltet?
	 * @return Verschiebung der Anzeige abgeschaltet?
	 */
	public boolean isDisableTranslate() {
		return disableTranslate;
	}

	/**
	 * Verschiebung der Anzeige abschalten/aktivieren
	 * @param disableTranslate Verschiebung der Anzeige abschalten?
	 */
	public void setDisableTranslate(boolean disableTranslate) {
		this.disableTranslate = disableTranslate;
	}
	
	/**
	 * wurde die Anzeige mit der linken Maustaste umgeschaltet
	 * @return
	 */
	public boolean isDisplaySwitchByLeftMouseClick() {
		return displaySwitchByLeftMouseClick;
	}

	/**
	 * soll die Ansicht mit der rechte Maustaste umschaltbar sein
	 * @param displaySwitchByLeftMouseClick soll die Ansicht mit der rechte Maustaste umschaltbar sein
	 */
	public void setDisplaySwitchByLeftMouseClick(
			boolean displaySwitchByLeftMouseClick) {
		this.displaySwitchByLeftMouseClick = displaySwitchByLeftMouseClick;
	}
	
	/**
	 * kann die Ansicht mit einem Linking und Brushing Symbol umgeschaltet werden
	 * @return kann die Ansicht mit einem Linking und Brushing Symbol umgeschaltet werden
	 */
	public boolean isDisplaySwitchByLinkingAndBrushing() {
		return displaySwitchByLinkingAndBrushing;
	}

	/**
	 * soll die Ansicht mit einem Linking und Brushing Symbol umschaltbar sein
	 * @param displaySwitchByLinkingAndBrushing soll die Ansicht mit einem Linking und Brushing Symbol umschaltbar sein
	 */
	public void setDisplaySwitchByLinkingAndBrushing(
			boolean displaySwitchByLinkingAndBrushing) {
		this.displaySwitchByLinkingAndBrushing = displaySwitchByLinkingAndBrushing;
	}
	
	/**
	 * wurde die Ansicht mit einem Linking und Brushing Symbol umgeschaltet
	 * @return wurde die Ansicht mit einem Linking und Brushing Symbol umgeschaltet
	 */
	public boolean isDisplaySwitchedByLinkingAndBrushing() {
		return displaySwitchedByLinkingAndBrushing;
	}

	/**
	 * Status (wurde die Ansicht mit einem Linking und Brushing Symbol umgeschaltet) setzen
	 * @param displaySwitchedByLinkingAndBrushing Status (wurde die Ansicht mit einem Linking und Brushing Symbol umgeschaltet)
	 */
	public void setDisplaySwitchedByLinkingAndBrushing(
			boolean displaySwitchedByLinkingAndBrushing) {
		this.displaySwitchedByLinkingAndBrushing = displaySwitchedByLinkingAndBrushing;
	}

	/**
	 * ist Zoom möglich
	 * @return ist Zoom möglich
	 */
	public boolean isDisplayZoom() {
		return displayZoom;
	}

	/**
	 * Status (ist Zoom möglich) setzen
	 * @param displayZoom Status (ist Zoom möglich)
	 */
	public void setDisplayZoom(boolean displayZoom) {
		this.displayZoom = displayZoom;
	}
	
	/**
	 * Region, in der verschiebt werden kann, abrufen
	 * @return Region mit Geokoordinaten
	 */
	public ArrayList<Coordinate> getClipRegion() {
		return clipRegion;
	}
	
	/**
	 * Region, in der verschiebt werden kann, setzen
	 * @param clipRegion Region mit Geokoordinaten 
	 */
	public void setClipRegion(ArrayList<Coordinate> clipRegion) {
		this.clipRegion = clipRegion;
	}
	
	/**
	 * Status (keine Umschaltung in transparente Ansicht) abrufen
	 * @return Status (keine Umschaltung in transparente Ansicht)
	 */
	public boolean isNoTransparentViewByLinkingAndBrushing() {
		return noTransparentViewByLinkingAndBrushing;
	}

	/**
	 * Status (keine Umschaltung in transparente Ansicht) setzen
	 * @param noTransparentViewByLinkingAndBrushing Status (keine Umschaltung in transparente Ansicht)
	 */
	public void setNoTransparentViewByLinkingAndBrushing(
			boolean noTransparentViewByLinkingAndBrushing) {
		this.noTransparentViewByLinkingAndBrushing = noTransparentViewByLinkingAndBrushing;
	}
	
	/**
	 * Ansicht hereingezoomt?
	 * @return Ansicht hereingezoomt?
	 */
	public boolean getZoomedIn() {
		return this.zoomedIn;
	}
	
	/**
	 * Ansicht herausgezoomt?
	 * @return Ansicht herausgezoomt?
	 */
	public boolean getZoomedOut() {
		return this.zoomedOut;
	}
	
	/**
	 * Beobachter an die HandleInput anhängen
	 * @param observer Instanz des Beobachter
	 */
	public void attach(IObserver observer)
	{
		this.observers.add(observer);
	}
	
	/**
	 * Eingaben verarbeiten
	 * @param inputType Art der Eingabe
	 * @param mousePosition Mausposition
	 * @param mouseWheel Status Mausrad
	 */
	public void handleInput(int inputType,Point2D mousePosition,int mouseWheel)
	{
		this.mousePosition=mousePosition;
		this.zoomedIn=false;
		this.zoomedOut=false;
		
		try {			
			if(inputType==HandleInput.INPUT_TYPE_LEFTMOUSEBUTTON_PRESSED)
			{
				//linke Maustaste gedrückt
				this.leftMousebuttonPressed=true;
				if(MapCubePanel.getInstance().getInfoText()!=null)
				{
					/*if(MapCubePanel.getInstance().isInfoTextClicked(mousePosition,DisplayFunctions.INFO_TEXT_BUTTON_OK))
						MapCubePanel.getInstance().setInfoText(null);*/
				}				
				else if(!MapCubePanel.getInstance().isZoomSymbolClicked(mousePosition, true) && !MapCubePanel.getInstance().isZoomSymbolClicked(mousePosition, false))
				{
					
					if(!this.displaySwitchByLinkingAndBrushing && this.displaySwitchByLeftMouseClick)
					{
						//Umschaltung Ansicht mit linker Maustaste
						if(!this.noTransparentViewByLinkingAndBrushing)
							this.switchDisplay(true);
					}						
					if(this.displaySwitchByLinkingAndBrushing && MapCubePanel.getInstance().isLinkingAndBrushingSymbolClicked(mousePosition))
					{			
						//Umschaltung Ansicht mit Linking And Brushing Symbol
						if(!this.noTransparentViewByLinkingAndBrushing)
							this.switchDisplay(true);
						this.displaySwitchedByLinkingAndBrushing=true;
					}
				}
			}
			else if(inputType==HandleInput.INPUT_TYPE_LEFTMOUSEBUTTON_RELEASED)
			{
				//linke Maustaste losgelassen
				if(MapCubePanel.getInstance().isZoomSymbolClicked(mousePosition, true) && this.displayZoom)
				{
					//heranzoomen
					if(MapCubePanel.getInstance().get_zoom()<=1)
					{										
						MapCubePanel.getInstance().zoom(true);
						this.zoomedIn=true;
					}	
				
				}
				else if(MapCubePanel.getInstance().isZoomSymbolClicked(mousePosition, false)  && this.displayZoom)
				{
					//herauszoomen
					if(MapCubePanel.getInstance().get_zoom()>=-2)
					{
						MapCubePanel.getInstance().zoom(false);
						this.zoomedOut=true;
					}
				}
				else if(this.displaySwitchByLeftMouseClick)
				{
					//transparente Ansicht aufheben
					this.switchDisplay(false);										
				}			
				if(this.displaySwitchByLinkingAndBrushing)
				{
					this.displaySwitchedByLinkingAndBrushing=false;
				}
				this.dragMousePosition=null;
				this.leftMousebuttonPressed=false;
			}
			else if(inputType==HandleInput.INPUT_TYPE_RIGHTMOUSEBUTTON_PRESSED)
			{
				//rechte Maustaste gedrückt
				this.rightMousebuttonPressed=true;
			}
			else if(inputType==HandleInput.INPUT_TYPE_RIGHTMOUSEBUTTON_RELEASED)
			{
				//rechte Maustaste losgelassen
				this.rightMousebuttonPressed=false;
			}			
			else if(inputType==HandleInput.INPUT_TYPE_MOUSEMOVE)
			{
				if(!(this.displaySwitchByLinkingAndBrushing && MapCubePanel.getInstance().isLinkingAndBrushingSymbolClicked(mousePosition)) && !this.disableTranslate)
					this.translateView(mousePosition);
				
				//if((this.displaySwitchByLinkingAndBrushing || this.zoomSwitchByLinkingAndBrushing) && !MapCubePanel.getInstance().isLinkingAndBrushingSymbolClicked(mousePosition))
				//	this.switchDisplay(false);
				
				if(this.displaySwitchByLinkingAndBrushing)
				{
					if(MapCubePanel.getInstance().isLinkingAndBrushingSymbolClicked(mousePosition) && MapCubePanel.getInstance().getLinkingAndBrushingSymbol()<1000)
						MapCubePanel.getInstance().setLinkingAndBrushingSymbol(MapCubePanel.getInstance().getLinkingAndBrushingSymbol()+1000);
					if(!MapCubePanel.getInstance().isLinkingAndBrushingSymbolClicked(mousePosition) && MapCubePanel.getInstance().getLinkingAndBrushingSymbol()>1000)
						MapCubePanel.getInstance().setLinkingAndBrushingSymbol(MapCubePanel.getInstance().getLinkingAndBrushingSymbol()-1000);
				}
			}
			else if(inputType==HandleInput.INPUT_TYPE_MOUSEWHEEL)
			{
				
			}
			
			this.lastaction=inputType;
			//alle Observer benachrichtigen
			for(IObserver observer:this.observers)
				observer.update();
			
			this.handleInputHistory=(HandleInput)this.clone();
			

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Anzeige anhand der Mausposition verschieben
	 * @param mousePosition Mausposition
	 */
	public void translateView(Point2D mousePosition)
	{
		if(this.leftMousebuttonPressed)
		{
			if(this.handleInputHistory!=null)
			{
				if(this.handleInputHistory.getDragMousePosition()!=null)
				{
					double difx=mousePosition.getX()-this.handleInputHistory.getDragMousePosition().getX();
					double dify=mousePosition.getY()-this.handleInputHistory.getDragMousePosition().getY();
					Coordinate c1=MapCubePanel.getInstance().getMapExtract().get(0);
					Coordinate c2=MapCubePanel.getInstance().getMapExtract().get(1);
					Coordinate c3=MapCubePanel.getInstance().getMapExtract().get(2);
					Coordinate c4=MapCubePanel.getInstance().getMapExtract().get(3);
					double translate_x=difx*((c3.x-c1.x)/(double)MapCubePanel.getInstance().getWidth())*0.33;
					double translate_y=dify*((c3.y-c1.y)/(double)MapCubePanel.getInstance().getHeight())*0.33;

					
						
					Coordinate c=MapCubePanel.getInstance().getCenterGeoposition();
					c.x-=translate_x;
					c.y-=translate_y;
					
					if(MapCubePanel.getInstance().getClipExtract()!=null)
					{
						if(c.x<MapCubePanel.getInstance().getClipExtract().get(0).x)
							c.x=MapCubePanel.getInstance().getClipExtract().get(0).x;
						if(c.x>MapCubePanel.getInstance().getClipExtract().get(1).x)
							c.x=MapCubePanel.getInstance().getClipExtract().get(1).x;
						if(c.y<MapCubePanel.getInstance().getClipExtract().get(1).y)
							c.y=MapCubePanel.getInstance().getClipExtract().get(1).y;
						if(c.y>MapCubePanel.getInstance().getClipExtract().get(0).y)
							c.y=MapCubePanel.getInstance().getClipExtract().get(0).y;

					}
												
					MapCubePanel.getInstance().setToCoordinate(c);
					
				}
				this.dragMousePosition=mousePosition;
			}
		}
	}
	
	/**
	 * Anzeige in transparente/normale Ansicht umschalten 
	 * @param switch_to_transparent in transparente Ansicht umschalten?
	 */
	public void switchDisplay(boolean switch_to_transparent)
	{
		if(!switch_to_transparent)
			MapCubePanel.getInstance().setTransparentView(false);
		else
			MapCubePanel.getInstance().setTransparentView(true);
	}
	
}
