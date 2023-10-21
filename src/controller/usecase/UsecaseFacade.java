package controller.usecase;

import javax.swing.JApplet;
import controller.controlsystem.HandleInput;

/**
 * Fassade für alle Usecases
 * Zugriff auf die Usescases
 * Anlegen von Usecases
 */
public class UsecaseFacade {
	public static final int USERTEST_LOCALIZATION=1;
	public static final int USERTEST_NAVIGATION=2;
	public static final int USERTEST_SCATTERPLOTS=3;
	
	private static UsecaseFacade instance=null;
	private Object currentUserTest=null;

	private JApplet mainApplet;
	
	/**
	 * Singleton der UsecaseFacade abrufen
	 * @return
	 */
	public static UsecaseFacade getInstance()
	{
		if(instance==null)
			instance=new UsecaseFacade();
		return instance;
	}
	
	/**
	 * neuen Usecase anlegen
	 * @param new_usecase_id ID des Usecase
	 * @param alternative_test alternativ ( Z.B HALO statt Mapcube)
	 * @return
	 */
	public boolean createUsecase(int new_usecase_id,int alternative_test)
	{		
		if(new_usecase_id==UsecaseFacade.USERTEST_LOCALIZATION)
		{
			this.currentUserTest=new UsecaseLocalization();
			((UsecaseLocalization)this.currentUserTest).setAlternativeTest(alternative_test);
		}
		else if(new_usecase_id==UsecaseFacade.USERTEST_NAVIGATION)
		{
			this.currentUserTest=new UsecaseNavigation();
		}
		
		//Benutzertest mit der Eingabeverarbeitung verbinden und initialisieren
		HandleInput.getInstance().attach((IObserver)this.currentUserTest);
		((IUsecase)this.currentUserTest).init();
				
		return true;
	}
	
	/**
	 * aktuellen Usecase abrufen
	 * @return
	 */
	public Object getCurrentUserTest() {
		return currentUserTest;
	}
	
	/**
	 * Instanz des Applets abrufen
	 * @return Instanz des Applets
	 */
	public JApplet getMainApplet() {
		return this.mainApplet;
	}

	/**
	 * Instanz des Applets setzen
	 *  @param mainApplet Instanz des Applets
	 */
	public void setMainApplet(JApplet mainApplet) {
		this.mainApplet = mainApplet;
	}
	
}
