package controller.controlsystem;

import java.awt.Color;

import view.ui.mapcube.MapCubePanel;

import com.vividsolutions.jts.geom.GeometryFactory;

import model.geo_data.AbstractSignature;
import model.geo_data.GeoObject;
import model.geo_data.Signature2D;
import model.geo_data.Signature3D;

/**
 * Ordnet den Geoobjekten Signaturen für die Fokus - und Kontext-Region zu
 */
public class VisualMapper {
	private static VisualMapper instance=null;
	
	/**
	 * Singleton für VisualMapper abrufen
	 * @return
	 */
	public static VisualMapper getInstance()
	{
		if(instance==null)
			instance=new VisualMapper();
		return instance;
	}
	
	/**
	 * Ordnet einen Geoobjekt Signaturen für die Fokus - und Kontext-Region zu
	 * @param geoObject Instanz Geoobjekt
	 */
	public void assignSignatures(GeoObject geoObject)
	{
		//entsprechende Zuordungsfunktion anhand des Objekttpys ermitteln
		if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_FREEWAY))
			this.assignSignaturesForFreeway(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_FUELSTATION))
			this.assignSignaturesForFuelstation(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_HOTEL))
			this.assignSignaturesForHotel(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_PARK))
			this.assignSignaturesForPark(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_PERSON))
			this.assignSignaturesForPerson(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_RESTINGPLACE))
			this.assignSignaturesForRastplatz(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_RIVER))
			this.assignSignaturesForRiver(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_SEA))
			this.assignSignaturesForSea(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_SIGHTSEEING))
			this.assignSignaturesForSightseeing(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_STREET))
			this.assignSignaturesForStreet(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_TOURISTREGION))
			this.assignSignaturesForTouristregion(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_TRAINSTATION))
			this.assignSignaturesForTrainstation(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_VILLAGE))
			this.assignSignaturesForVillage(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_MARKER))
			this.assignSignaturesForMarker(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_RESTAURANT))
			this.assignSignaturesForRestaurant(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_ROUTESTART))
			this.assignSignaturesForRouteStart(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_ROUTEEND))
			this.assignSignaturesForRouteEnd(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_ROUTE))
			this.assignSignaturesForRoute(geoObject);
		else if(geoObject.getObjtype().equals(GeoObject.OBJTYPE_RELATION_LINE))
			this.assignSignaturesForRelationLine(geoObject);
		
	}
	
	/**
	 * Signatur für Strasse zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForStreet(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D streetSignature=new Signature2D();
		streetSignature.setShapeType(Signature2D.SHAPE_LINE);
		streetSignature.setWitdh(3.0f);
		//streetSignature.setColor(Color.);
		streetSignature.setColor(new Color(0x855E42));  
		streetSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(streetSignature);		
		
		//Kontext Region
		Signature3D streetSignature3D=new Signature3D();
		streetSignature3D.setShapeType(Signature3D.SHAPE_PRISMLINE);
		streetSignature3D.setWitdh(0.005f);
		streetSignature3D.setHeight(0.01f);
		streetSignature3D.setColor(new Color(0x855E42));
		streetSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setContextSignature(streetSignature3D);
		
	}
	
	/**
	 * Signatur für Autobahn zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForFreeway(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D freewaySignature=new Signature2D();
		freewaySignature.setShapeType(Signature2D.SHAPE_LINE);
		freewaySignature.setWitdh(5.0f);
		freewaySignature.setColor(new Color(0x8B6969));
		freewaySignature.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setFocusSignature(freewaySignature);
		
		//Kontext Region
		Signature3D freewaySignature3D=new Signature3D();
		freewaySignature3D.setShapeType(Signature3D.SHAPE_CYLINDERLINE);
		freewaySignature3D.setWitdh(0.02f);
		freewaySignature3D.setLength(0.02f);
		freewaySignature3D.setColor(new Color(0x8B6969));
		freewaySignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(freewaySignature3D);
		
		if(MapCubePanel.parameters.contains("focuscylinder") &&  MapCubePanel.parameters.get("focuscylinder").equals("1"))
			geoObject.setFocusSignature(freewaySignature3D);
	}
	
	/**
	 * Signatur für Ortschaft zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForVillage(GeoObject geoObject)
	{
		//Fokus/Kontext Region
		Signature2D villageSignature=new Signature2D();
		villageSignature.setShapeType(Signature2D.SHAPE_LINE);
		villageSignature.setWitdh(3.0f);
		villageSignature.setColor(new Color(0xA8120F));  
		villageSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(villageSignature);
		
		//Kontext Region
		Signature3D villageSignature3D=new Signature3D();
		villageSignature3D.setShapeType(Signature3D.SHAPE_RHOMBLINE);	
		villageSignature3D.setColor(new Color(0xA8120F)); 
		villageSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		villageSignature3D.setHeight(0.02f);
		geoObject.setContextSignature(villageSignature3D);		
		
		//if(MapCubePanel.parameters.get("focuscylinder")!=null && MapCubePanel.parameters.get("focuscylinder").equals("true"))
		//	geoObject.setContextSignature(villageSignature3D);
				
		geoObject.setGeometry(new GeometryFactory().createLineString(geoObject.getGeometry().getCoordinates()));
		
	}
	
	/**
	 * Signatur für Park zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForPark(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D parkSignature=new Signature2D();
		parkSignature.setShapeType(Signature2D.SHAPE_FILLEDPOLYGON);
		parkSignature.setWitdh(1.0f);
		parkSignature.setColor(Color.GREEN); 
		parkSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(parkSignature);
				
		//Kontext Region
		Signature3D parkSignature3D=new Signature3D();
		parkSignature3D.setShapeType(Signature3D.SHAPE_POLYGONPRISM);	
		parkSignature3D.setColor(Color.GREEN); 
		parkSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);		
		geoObject.setContextSignature(parkSignature3D);
	}
	
	/**
	 * Signatur für See zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForSea(GeoObject geoObject)
	{
		//Fokus/Kontext Region
		Signature2D seaSignature=new Signature2D();
		seaSignature.setShapeType(Signature2D.SHAPE_FILLEDPOLYGON);
		seaSignature.setWitdh(1.0f);
		seaSignature.setColor(Color.BLUE); 
		seaSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(seaSignature);
		geoObject.setContextSignature(seaSignature);
	}
	
	/**
	 * Signatur für Sehenswürdigkeit zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForSightseeing(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D sightseeingSignature=new Signature2D();
		sightseeingSignature.setShapeType(Signature2D.SHAPE_FILLEDCIRCLE);
		sightseeingSignature.setWitdh(0.06f);		
		sightseeingSignature.setColor(Color.YELLOW); 
		sightseeingSignature.setBrightness(AbstractSignature.BRIGHTNESS_BRIGHT);
		geoObject.setFocusSignature(sightseeingSignature);
		
		//Kontext Region
		Signature3D sightseeingSignature3D=new Signature3D();
		sightseeingSignature3D.setShapeType(Signature3D.SHAPE_SPHERE);
		sightseeingSignature3D.setWitdh(0.06f);
		sightseeingSignature3D.setColor(Color.YELLOW); 
		sightseeingSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_BRIGHT);
		geoObject.setContextSignature(sightseeingSignature3D);
	}
	
	/**
	 * Signatur für Bahnhof zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForTrainstation(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D trainstationSignature=new Signature2D();
		trainstationSignature.setShapeType(Signature2D.SHAPE_FILLEDRECTANGLE);
		trainstationSignature.setWitdh(0.05f);
		trainstationSignature.setLength(0.1f);
		trainstationSignature.setColor(Color.YELLOW); 
		trainstationSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(trainstationSignature);
		
		//Kontext Region
		Signature3D trainstationSignature3D=new Signature3D();
		trainstationSignature3D.setShapeType(Signature3D.SHAPE_RECTANGULARPRISM);
		trainstationSignature3D.setWitdh(0.05f);
		trainstationSignature3D.setLength(0.1f);
		trainstationSignature3D.setHeight(0.05f);		
		trainstationSignature3D.setColor(Color.YELLOW); 
		trainstationSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setContextSignature(trainstationSignature3D);
	}
	
	/**
	 * Signatur für touristiches Gebiet zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForTouristregion(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D parkSignature=new Signature2D();
		parkSignature.setShapeType(Signature2D.SHAPE_FILLEDPOLYGON);
		parkSignature.setWitdh(1.0f);
		parkSignature.setColor(Color.YELLOW); 
		parkSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(parkSignature);
				
		//Kontext Region
		Signature3D parkSignature3D=new Signature3D();
		parkSignature3D.setShapeType(Signature3D.SHAPE_POLYGONPRISM);	
		parkSignature3D.setColor(Color.YELLOW); 
		parkSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);		
		geoObject.setContextSignature(parkSignature3D);
	}
	
	/**
	 * Signatur für Hotel zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForHotel(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D trainstationSignature=new Signature2D();
		trainstationSignature.setShapeType(Signature2D.SHAPE_FILLEDRECTANGLE);
		trainstationSignature.setWitdh(0.1f);
		trainstationSignature.setLength(0.1f);
		trainstationSignature.setColor(Color.YELLOW); 
		trainstationSignature.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setFocusSignature(trainstationSignature);
		
		//Kontext Region
		Signature3D trainstationSignature3D=new Signature3D();
		trainstationSignature3D.setShapeType(Signature3D.SHAPE_RECTANGULARPRISM);
		trainstationSignature3D.setWitdh(0.1f);
		trainstationSignature3D.setLength(0.1f);
		trainstationSignature3D.setHeight(0.1f);		
		trainstationSignature3D.setColor(Color.YELLOW); 
		trainstationSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(trainstationSignature3D);
	}
	
	/**
	 * Signatur für Tankstelle zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForFuelstation(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D fuelstationSignature=new Signature2D();
		fuelstationSignature.setShapeType(Signature2D.SHAPE_TRIANGLE);
		fuelstationSignature.setWitdh(0.15f);
		fuelstationSignature.setLength(0.15f);
		fuelstationSignature.setColor(Color.ORANGE); 
		fuelstationSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(fuelstationSignature);
		
		//Kontext Region
		Signature3D fuelstationSignature3D=new Signature3D();
		fuelstationSignature3D.setShapeType(Signature3D.SHAPE_PYRAMID);
		fuelstationSignature3D.setWitdh(0.05f);
		fuelstationSignature3D.setLength(0.05f);
		fuelstationSignature3D.setHeight(0.05f);		
		fuelstationSignature3D.setColor(Color.ORANGE); 
		fuelstationSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setContextSignature(fuelstationSignature3D);
	}
	
	/**
	 * Signatur für Rastplatz zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRastplatz(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D rastplatzSignature=new Signature2D();
		rastplatzSignature.setShapeType(Signature2D.SHAPE_FILLEDHEXAGON);
		rastplatzSignature.setWitdh(0.1f);
		rastplatzSignature.setLength(0.1f);
		rastplatzSignature.setColor(Color.ORANGE); 
		rastplatzSignature.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setFocusSignature(rastplatzSignature);
		
		//Kontext Region
		Signature3D rastplatzSignature3D=new Signature3D();
		rastplatzSignature3D.setShapeType(Signature3D.SHAPE_HEXAGONALPRISM);
		rastplatzSignature3D.setWitdh(0.1f);
		rastplatzSignature3D.setLength(0.1f);
		rastplatzSignature3D.setHeight(0.1f);		
		rastplatzSignature3D.setColor(Color.ORANGE); 
		rastplatzSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(rastplatzSignature3D);
	}
	
	/**
	 * Signatur für Person zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForPerson(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D rastplatzSignature=new Signature2D();
		rastplatzSignature.setShapeType(Signature2D.SHAPE_CIRCLE);
		rastplatzSignature.setWitdh(0.05f);
		rastplatzSignature.setLength(0.05f);
		rastplatzSignature.setColor(Color.CYAN);
		
		rastplatzSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(rastplatzSignature);
		
		//Kontext Region
		Signature3D rastplatzSignature3D=new Signature3D();
		rastplatzSignature3D.setShapeType(Signature3D.SHAPE_CONE);
		rastplatzSignature3D.setWitdh(0.05f);
		rastplatzSignature3D.setLength(0.05f);
		rastplatzSignature3D.setHeight(0.1f);		
		rastplatzSignature3D.setColor(Color.CYAN); 
		rastplatzSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(rastplatzSignature3D);
	}
	
	/**
	 * Signatur für Fluss zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRiver(GeoObject geoObject)
	{
		//Fokus/Kontext Region
		Signature2D riverSignature=new Signature2D();
		riverSignature.setShapeType(Signature2D.SHAPE_FLATRECTANGULARLINE);
		riverSignature.setWitdh(0.098f);
		riverSignature.setColor(Color.BLUE); 
		riverSignature.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setFocusSignature(riverSignature);
		geoObject.setContextSignature(riverSignature);
	}
	
	/**
	 * Signatur für Markierung zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForMarker(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D markerSignature=new Signature2D();
		markerSignature.setShapeType(Signature2D.SHAPE_CROSS);
		markerSignature.setWitdh(0.2f);
		markerSignature.setLength(0.025f);
		markerSignature.setColor(Color.YELLOW); 
		markerSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(markerSignature);
		
		//Kontext Region
		Signature3D markerSignature3D=new Signature3D();
		markerSignature3D.setShapeType(Signature3D.SHAPE_CROSS3D);
		markerSignature3D.setWitdh(0.2f);
		markerSignature3D.setLength(0.025f);
		markerSignature3D.setHeight(0.02f);		
		markerSignature3D.setColor(Color.YELLOW); 
		markerSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(markerSignature3D);
	}
	
	/**
	 * Signatur für Restaurant zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRestaurant(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D restaurantSignature=new Signature2D();
		restaurantSignature.setShapeType(Signature2D.SHAPE_RHOMB);
		restaurantSignature.setWitdh(0.1f);
		restaurantSignature.setLength(0.1f);
		restaurantSignature.setColor(Color.YELLOW); 
		restaurantSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(restaurantSignature);
		
		//Kontext Region
		Signature3D restaurantSignature3D=new Signature3D();
		restaurantSignature3D.setShapeType(Signature3D.SHAPE_RHOMBIC_PRISM);
		restaurantSignature3D.setWitdh(0.1f);
		restaurantSignature3D.setLength(0.1f);
		restaurantSignature3D.setHeight(0.1f);		
		restaurantSignature3D.setColor(Color.YELLOW); 
		restaurantSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(restaurantSignature3D);
	}
	
	/**
	 * Signatur für Route zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRoute(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D routeSignature=new Signature2D();
		routeSignature.setShapeType(Signature2D.SHAPE_LINE);
		routeSignature.setWitdh(6.0f);
		routeSignature.setColor(Color.YELLOW);
		routeSignature.setZ(0.01f);
		routeSignature.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setFocusSignature(routeSignature);
		
		//Kontext Region
		Signature3D routeSignature3D=new Signature3D();
		routeSignature3D.setShapeType(Signature3D.SHAPE_CYLINDERLINE);
		routeSignature3D.setWitdh(0.03f);
		routeSignature3D.setLength(0.03f);
		routeSignature3D.setColor(Color.YELLOW);
		routeSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(routeSignature3D);	
	}
	
	/**
	 * Signatur für Routenanfang zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRouteStart(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D routeStartSignature=new Signature2D();
		routeStartSignature.setShapeType(Signature2D.SHAPE_CROSS);
		routeStartSignature.setWitdh(0.2f);
		routeStartSignature.setLength(0.025f);
		routeStartSignature.setColor(Color.YELLOW); 
		routeStartSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(routeStartSignature);
		
		//Kontext Region
		Signature3D routeStartSignature3D=new Signature3D();
		routeStartSignature3D.setShapeType(Signature3D.SHAPE_CROSS3D);
		routeStartSignature3D.setWitdh(0.2f);
		routeStartSignature3D.setLength(0.025f);
		routeStartSignature3D.setHeight(0.02f);			
		routeStartSignature3D.setColor(Color.YELLOW); 
		routeStartSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(routeStartSignature3D);
	}

	/**
	 * Signatur für Routenende zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRouteEnd(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D routeEndSignature=new Signature2D();
		routeEndSignature.setShapeType(Signature2D.SHAPE_CROSS);
		routeEndSignature.setWitdh(0.2f);
		routeEndSignature.setLength(0.025f);
		routeEndSignature.setColor(Color.YELLOW); 
		routeEndSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(routeEndSignature);
		
		//Kontext Region
		Signature3D routeEndSignature3D=new Signature3D();
		routeEndSignature3D.setShapeType(Signature3D.SHAPE_CROSS3D);
		routeEndSignature3D.setWitdh(0.2f);
		routeEndSignature3D.setLength(0.025f);
		routeEndSignature3D.setHeight(0.02f);				
		routeEndSignature3D.setColor(Color.YELLOW); 
		routeEndSignature3D.setBrightness(AbstractSignature.BRIGHTNESS_DARK);
		geoObject.setContextSignature(routeEndSignature3D);
	}
	
	/**
	 * Signatur für Beziegung zuordnen
	 * @param geoObject
	 */
	private void assignSignaturesForRelationLine(GeoObject geoObject)
	{
		//Fokus Region
		Signature2D relationLineSignature=new Signature2D();
		relationLineSignature.setShapeType(Signature2D.SHAPE_LINE);
		relationLineSignature.setWitdh(0.2f);
		relationLineSignature.setColor(Color.yellow); 
		relationLineSignature.setBrightness(AbstractSignature.BRIGHTNESS_NORMAL);
		geoObject.setFocusSignature(relationLineSignature);		
		geoObject.setContextSignature(relationLineSignature);
	}

}
