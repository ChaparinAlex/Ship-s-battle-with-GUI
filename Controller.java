package application;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import service.Filling;
import service.Service;
import service.Shooting;

public class Controller implements Initializable{	
	
	private static char[] plField = new char[100];	
	private static char[] pcField = new char[100];
	private static int[][] plShipsData = new int[2][20];
	private static int[][] pcShipsData = new int[2][20];	
	private int playerScore = 0, pcScore = 0;
	private static int numOfClicks = 0;
	private static int valueOfCurrentShip;
	private int[] coordinatesOfInputShip = new int[4];
	private int[][] statusOfFilling = {{4,-1},{4,-1},{4,-1},{4,-1},
		{3,-1},{3,-1},{3,-1},{3,-1},{3,-1},{3,-1},{2,-1},{2,-1},{2,-1},
		{2,-1},{2,-1},{2,-1},{1,-1},{1,-1},{1,-1},{1,-1}};	
	private static int[] liveStatusOfPlShip = {4,3,3,2,2,2,1,1,1,1};
	private static int[] liveStatusOfPcShip = {4,3,3,2,2,2,1,1,1,1};	
	private boolean isPcShotsNow = false;
	private int currentPositionOfPc0 = -1, currentPositionOfPc1 = -1;
	private boolean leftDirectionOfPcShot = false, 
			rightDirectionOfPcShot = false, upDirectionOfPcShot = false, 
			downDirectionOfPcShot = false;
	private int directionOfPcShot = -1;
	private boolean arePreGameOperationsDone = false;
	private StringBuffer infoLine = new StringBuffer("");
	private boolean isUkrLangPackageActive = false;
	private boolean isSoundsOn = true;
	private final URL startMusicURL = getClass().getResource(
			                       "/Sounds and music/Game music.mp3");
    private final AudioClip music = new AudioClip(startMusicURL.toString());
    private final URL inputShipSoundURL = getClass().getResource(
            "/Sounds and music/Input ship.wav");
    private final AudioClip inputShipSound = new AudioClip(
    		                               inputShipSoundURL.toString());
    private final URL accurateShotSoundURL = getClass().getResource(
            "/Sounds and music/Accurate shot.wav");
    private final AudioClip accurateShotSound = new AudioClip(
    		                               accurateShotSoundURL.toString());
    private final URL badShotSoundURL = getClass().getResource(
            "/Sounds and music/Bad shot.wav");
    private final AudioClip badShotSound = new AudioClip(
    		                                    badShotSoundURL.toString());
    private Image startImage = new Image("/Images/Start game picture.jpg");
    private Image finishImage = new Image("/Images/Burning ships.jpg");
    private Image gameImage = new Image("/Images/Game picture.jpg");
   
    @FXML private AnchorPane anPane;
	
	@FXML private TextArea startText;
	
	@FXML private Label plFieldLabel;
	
	@FXML private Label pcFieldLabel;
	
	@FXML private Label topLabel;
	
	@FXML private RadioButton automaticFilling;
	
	@FXML private RadioButton manualFilling;
	
	@FXML private List<Button> plFieldVisButtonList;
	
	@FXML private List<Button> pcFieldVisButtonList;
	
	@FXML private TextArea gameInfo;
	
	@FXML private Label scoreLabel;
	
	@FXML private Label playerScoreDataLabel;
	
	@FXML private Label pcScoreDataLabel;
	
	@FXML private TextField playerScoreDataField;
	
	@FXML private TextField pcScoreDataField;
	
	@FXML private Label plFieldBorderTopLabel;
	
	@FXML private Label pcFieldBorderTopLabel;
	
	@FXML private Label plFieldBorderLeftLabel;
	
	@FXML private Label pcFieldBorderLeftLabel;
	
	@FXML private Label infoLineLabel;
	
	@FXML private Label languageLabel;
	
	@FXML private Button ukrLangButton;
	
	@FXML private Button engLangButton;
	
	@FXML private Button restartGameButton;
	
	@FXML private Button exitGameButton;
	
	@FXML private Button soundsOnOffButton;
	
	@FXML private Label developerInfoLabel;
	
	public static char[] getPlField() {
		return plField;
	}	

	public static char[] getPcField() {
		return pcField;
	}
	
	public static char getPlFieldByIndex(int index){
		return plField[index];
	}
	
	public static void setPlFieldByIndex(int index, char value){
		plField[index] = value;
	}
	
	public static char getPcFieldByIndex(int index){
		return pcField[index];
	}
	
	public static void setPcFieldByIndex(int index, char value){
		pcField[index] = value;
	}
	
	public static int getPlShipsDataByIndexes(int firstIndex, int secondIndex){
		return plShipsData[firstIndex][secondIndex];
	}
	
	public static void setPlShipsDataByIndexes(
			                       int firstIndex, int secondIndex, int value){
		plShipsData[firstIndex][secondIndex] = value;
	}
	
	public static int getPcShipsDataByIndexes(int firstIndex, int secondIndex){
		return pcShipsData[firstIndex][secondIndex];
	}
	
	public static void setPcShipsDataByIndexes(
			                       int firstIndex, int secondIndex, int value){
		pcShipsData[firstIndex][secondIndex] = value;
	}

	public static int getLiveStatusOfPlShipByIndex(int index) {
		return liveStatusOfPlShip[index];
	}
	
	public static void setLiveStatusOfPlShipByIndex(int index, int value) {
		liveStatusOfPlShip[index] = value;
	}

	public static int getLiveStatusOfPcShipByIndex(int index) {
		return liveStatusOfPcShip[index];
	}
	
	public static void setLiveStatusOfPcShipByIndex(int index, int value) {
		liveStatusOfPcShip[index] = value;
	}	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		music.play();
		BackgroundImage bgImgStart = new BackgroundImage(startImage, 
		   BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		   BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, 
				   BackgroundSize.AUTO, false, false, true, true));
		anPane.setBackground(new Background(bgImgStart));
		
		for(Button button:plFieldVisButtonList){
			button.setVisible(false);
		}
		for(Button button:pcFieldVisButtonList){
			button.setVisible(false);
		}
		
		startText.setVisible(false);
		startText.setEditable(false);
		plFieldLabel.setVisible(false);
		pcFieldLabel.setVisible(false);
		topLabel.setVisible(true);
		automaticFilling.setVisible(false);
		manualFilling.setVisible(false);
		gameInfo.setVisible(false);
		scoreLabel.setVisible(false);
		playerScoreDataLabel.setVisible(false);
		pcScoreDataLabel.setVisible(false);
		playerScoreDataField.setVisible(false);
		pcScoreDataField.setVisible(false);
		playerScoreDataField.setEditable(false);
		pcScoreDataField.setEditable(false);
		gameInfo.setEditable(false);
		plFieldBorderTopLabel.setVisible(false);
		plFieldBorderLeftLabel.setVisible(false);
		pcFieldBorderTopLabel.setVisible(false);
		pcFieldBorderLeftLabel.setVisible(false);
		infoLineLabel.setVisible(false);
		restartGameButton.setVisible(false);
		exitGameButton.setVisible(false);
		soundsOnOffButton.setVisible(false);
		languageLabel.setVisible(true);
		ukrLangButton.setVisible(true);
		engLangButton.setVisible(true);
		developerInfoLabel.setText(developerInfoLabel.getText() + " " + '\u00A9');
		
		ukrLangButton.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent event) {
		    	handleUkrLangButtonClick(event);
		    	
		    }
		});
		
         engLangButton.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent event) {
		    	handleEngLangButtonClick(event);
		    	
		    }
		});

         soundsOnOffButton.setOnAction(new EventHandler<ActionEvent>() {
				
			    @Override 
			    public void handle(ActionEvent event) {
			    	if(isSoundsOn){
			    		music.stop();
			    		accurateShotSound.stop();
			    		badShotSound.stop();
			    		inputShipSound.stop();
			    		if(isUkrLangPackageActive)
			    		   soundsOnOffButton.setText("Увімкнути звуки");
			    		else soundsOnOffButton.setText("Sounds ON");
			    	}
			    	else{
			    		if(isUkrLangPackageActive)
			    		   soundsOnOffButton.setText("Вимкнути звуки");
			    		else soundsOnOffButton.setText("Sounds OFF");
			    	}
			    	isSoundsOn = !isSoundsOn;
			    	anPane.requestFocus();
			    }
			});
	}
	
	public void handleUkrLangButtonClick(ActionEvent event){
		anPane.requestFocus();
		music.stop();
		languageLabel.setVisible(false);
		ukrLangButton.setVisible(false);
		engLangButton.setVisible(false);
		topLabel.setVisible(false);
		setupUkrainianPackage();
		startText.setVisible(true);
		automaticFilling.setVisible(true);
		manualFilling.setVisible(true);		
	}
	
	public void handleEngLangButtonClick(ActionEvent event){
		anPane.requestFocus();
		music.stop();
		languageLabel.setVisible(false);
		ukrLangButton.setVisible(false);
		engLangButton.setVisible(false);
		topLabel.setVisible(false);
		startText.setVisible(true);
		automaticFilling.setVisible(true);
		manualFilling.setVisible(true);	
		isUkrLangPackageActive = false;
	}
	
	public void automaticOnAction(){
		BackgroundImage bgImgGame = new BackgroundImage(gameImage, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.CENTER, 
			    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
			    		false, false, true, true));
		anPane.setBackground(new Background(bgImgGame));
		for(Button button:plFieldVisButtonList){
			button.setVisible(true);
		}
		for(Button button:pcFieldVisButtonList){
			button.setVisible(true);
		}
		startText.setVisible(false);
		plFieldLabel.setVisible(true);
		pcFieldLabel.setVisible(true);
		topLabel.setVisible(true);
		automaticFilling.setVisible(false);
		manualFilling.setVisible(false);
		gameInfo.setVisible(true);
		scoreLabel.setVisible(true);
		playerScoreDataLabel.setVisible(true);
		pcScoreDataLabel.setVisible(true);
		playerScoreDataField.setVisible(true);
		pcScoreDataField.setVisible(true);
		plFieldBorderTopLabel.setVisible(true);
		plFieldBorderLeftLabel.setVisible(true);
		pcFieldBorderTopLabel.setVisible(true);
		pcFieldBorderLeftLabel.setVisible(true);
		infoLineLabel.setVisible(true);
		if(!isUkrLangPackageActive && isSoundsOn == false) 
			                            soundsOnOffButton.setText("Sounds ON");
		if(!isUkrLangPackageActive && isSoundsOn == true) 
			                            soundsOnOffButton.setText("Sounds OFF");
		if(isUkrLangPackageActive && isSoundsOn == false) 
			                      soundsOnOffButton.setText("Увімкнути звуки");
		if(isUkrLangPackageActive && isSoundsOn == true) 
			                      soundsOnOffButton.setText("Вимкнути звуки");
		soundsOnOffButton.setVisible(true);
		Filling.autoFill(plFieldVisButtonList, plField);
		Filling.autoFill(pcFieldVisButtonList, pcField);
		showPlField();
		showPcField();
		startGame();
	}
	
	public void manualOnAction(){
		BackgroundImage bgImgGame = new BackgroundImage(gameImage, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.CENTER, 
			    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
			    		false, false, true, true));
		anPane.setBackground(new Background(bgImgGame));
		
		for(Button button:plFieldVisButtonList){
			button.setText("");
			button.setVisible(true);
		}
		for(Button button:pcFieldVisButtonList){
			button.setText("");
			button.setVisible(true);
		}
		startText.setVisible(false);
		plFieldLabel.setVisible(true);
		pcFieldLabel.setVisible(true);
		topLabel.setVisible(true);
		automaticFilling.setVisible(false);
		manualFilling.setVisible(false);
		gameInfo.setVisible(true);
		scoreLabel.setVisible(true);
		playerScoreDataLabel.setVisible(true);
		pcScoreDataLabel.setVisible(true);
		playerScoreDataField.setVisible(true);
		pcScoreDataField.setVisible(true);
		plFieldBorderTopLabel.setVisible(true);
		plFieldBorderLeftLabel.setVisible(true);
		pcFieldBorderTopLabel.setVisible(true);
		pcFieldBorderLeftLabel.setVisible(true);
		infoLineLabel.setVisible(true);
		if(!isUkrLangPackageActive && isSoundsOn == false) 
                                  soundsOnOffButton.setText("Sounds ON");
        if(!isUkrLangPackageActive && isSoundsOn == true) 
                                 soundsOnOffButton.setText("Sounds OFF");
        if(isUkrLangPackageActive && isSoundsOn == false) 
                            soundsOnOffButton.setText("Увімкнути звуки");
        if(isUkrLangPackageActive && isSoundsOn == true) 
                             soundsOnOffButton.setText("Вимкнути звуки");
		soundsOnOffButton.setVisible(true);
		for(Button button:plFieldVisButtonList)
			button.setOnAction(new EventHandler<ActionEvent>() {
				
			    @Override 
			    public void handle(ActionEvent event) {
			    	handlePlFieldVisButtonClick(event);
			    	
			    }
			});
		if(isUkrLangPackageActive) gameInfo.setText("Будь ласка, введіть "
				                                  + "чотириярусного корабля.");	
		else gameInfo.setText("Please input a 4-parts ship on your field.");
		anPane.requestFocus();
	}
	
	public void setupUkrainianPackage(){
		isUkrLangPackageActive = true;
		startText.setText("      Розпочинаємо гру \"Морський бій\"!" +                                                  
        "\n\n\n\nБудь ласка, оберіть спосіб заповнення свого поля (дивіться нижче):");
		
		plFieldLabel.setText("Ваше поле");
		topLabel.setText("МОРСЬКИЙ БІЙ");
		pcFieldLabel.setText("Поле комп'ютера");
		automaticFilling.setText("Автоматично");
		manualFilling.setText("Вручну");
		scoreLabel.setText("Рахунок гри");
		playerScoreDataLabel.setText("Ви");
		pcScoreDataLabel.setText("Комп'ютер");
		plFieldBorderTopLabel.setText(" А   Б   В   Г   Д   Е   Є   Ж   З   И ");
		pcFieldBorderTopLabel.setText(" А   Б   В   Г   Д   Е   Є   Ж   З   И ");
		infoLineLabel.setText("Інформаційне поле");
		restartGameButton.setText("Нова гра");
		exitGameButton.setText("Вийти");
		if(isSoundsOn) soundsOnOffButton.setText("Вимкнути звуки");
		else soundsOnOffButton.setText("Увімкнути звуки");
		developerInfoLabel.setText("Розробник ПЗ: Олександр Чапарін. "
				                           + "Всі права захищено " +  '\u00A9');
	}	
	
	@FXML
    public void handlePlFieldVisButtonClick(ActionEvent event){
		anPane.requestFocus();
		int index = -1;
		for (int i = 0; i < 20; i++){
			if (statusOfFilling[i][1] == -1){
				valueOfCurrentShip = statusOfFilling[i][0];
				index = i;
				break;
			}
		}	
		
		numOfClicks++;
		for(Button button:plFieldVisButtonList){
    		if (event.getSource() == button){
    			if(isSoundsOn) inputShipSound.play();
    			int positionOfClick = Integer.parseInt(button.getId().substring(16));
 		           
	            plField[positionOfClick] = '@';
	            coordinatesOfInputShip[numOfClicks - 1] = positionOfClick;
    			statusOfFilling[index][1] = positionOfClick;
	            button.setStyle("-fx-background-color: #000000;");		           
    	    }
    	}
		
		if(valueOfCurrentShip == 4 && numOfClicks == valueOfCurrentShip){
			if(Service.isLine(coordinatesOfInputShip[0], coordinatesOfInputShip[1], 
			    coordinatesOfInputShip[2], coordinatesOfInputShip[3])){
				
				if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
				              + "Тепер введіть " + (valueOfCurrentShip - 1) + 
				                                        "-ярусний корабель.");	
                else
				gameInfo.setText("Ok! Now input a " + (valueOfCurrentShip - 1)
						 + "-parts ship.");				
				Service.setSurroundCellsDisable(plFieldVisButtonList, plField, 
					coordinatesOfInputShip[0], coordinatesOfInputShip[1], 
					coordinatesOfInputShip[2], coordinatesOfInputShip[3]);
				for(int i = 0; i <= 1; i++){
					for(int j = 0; j <= 3; j++){
						if(i == 0) plShipsData[i][j] = 10*(1 + j);
						else plShipsData[i][j] = coordinatesOfInputShip[j];
					}
				}					
				numOfClicks = 0;
			}	
		 else{ 	
			    if(isUkrLangPackageActive) gameInfo.setText("Ви ввели "
			    		+ "неправильні дані. Повторіть введення ще раз.");
			    else
		        gameInfo.setText("You input wrong data. Try again. \nPlease "
		        		+ "input a " + valueOfCurrentShip + "-parts ship.");
		        numOfClicks = 0;
		        for(int i = 0; i < 4; i++){
		          plField[coordinatesOfInputShip[i]] = ' ';
		          plFieldVisButtonList.get(coordinatesOfInputShip[i]).
		        		    setStyle("-fx-background: derive(-fx-base,26.4%);");
		          statusOfFilling[i][1] = -1;
		        }
		      }
		}
		
		if(valueOfCurrentShip == 3 && numOfClicks == valueOfCurrentShip){
			
			int l = -1;
			if(index >= 4 && index <= 6) l = 0;
			if(index >= 7 && index <= 9) l = 1;
		   
			if(Service.isLine(coordinatesOfInputShip[0], coordinatesOfInputShip[1], 
				    coordinatesOfInputShip[2])){
				
				if(l == 0){
					if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
				              + "Тепер введіть інший " + 
							valueOfCurrentShip + "-ярусний корабель.");
					else
					gameInfo.setText("Ok! Now input another " + 
				                          valueOfCurrentShip + "-parts ship.");		
				}
				if(l == 1){
					if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
				              + "Тепер введіть " + (valueOfCurrentShip - 1) + 
				                                        "-ярусний корабель.");
					else
				    gameInfo.setText("Ok! Now input a " + 
				                    (valueOfCurrentShip - 1) + "-parts ship.");		
				}
				Service.setSurroundCellsDisable(plFieldVisButtonList, plField, 
					   coordinatesOfInputShip[0], coordinatesOfInputShip[1], 
					                                coordinatesOfInputShip[2]);
			   for(int i = 0; i <= 1; i++){
					for(int j = 0; j <= 2; j++){
						if(i == 0) 
							plShipsData[i][j + 4 + l*3] = 10*(1 + j) + l + 1;
						else plShipsData[i][j + 4 + l*3] = 
								                     coordinatesOfInputShip[j];
					}
				}		
			   numOfClicks = 0;
			}			
		    else{
		    	 if(isUkrLangPackageActive) gameInfo.setText("Ви ввели "
		    	 		+ "неправильні дані. Повторіть введення ще раз. "
		    	 		+ "\nБудь ласка, введіть " + valueOfCurrentShip + 
		    	 		"-ярусний корабель.");
		    	 else 
		    	 gameInfo.setText("You input wrong data. Try again. \nPlease "
			        	  + "input a " + valueOfCurrentShip + "-parts ship.");
		    	 numOfClicks = 0;
	        	 for(int i = 0; i < 3; i++){
	        		 plField[coordinatesOfInputShip[i]] = ' ';
			          plFieldVisButtonList.get(coordinatesOfInputShip[i]).
			        		setStyle("-fx-background: derive(-fx-base,26.4%);");
	        	   statusOfFilling[i + l*3 + 4][1] = -1;
	        	}
		    }
	      }
		
          if(valueOfCurrentShip == 2 && numOfClicks == valueOfCurrentShip){
			
			int l = -1;
			if(index >= 10 && index <= 11) l = 0;
			if(index >= 12 && index <= 13) l = 1;
			if(index >= 14 && index <= 15) l = 2;
		   
			if(Service.isLine(coordinatesOfInputShip[0], coordinatesOfInputShip[1])){
					
				if(l == 0 || l == 1){
					if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
				              + "Тепер введіть інший " + 
							valueOfCurrentShip + "-ярусний корабель.");
					else
					gameInfo.setText("Ok! Now input another " + 
				                          valueOfCurrentShip + "-parts ship.");		
				}
				if(l == 2){
					if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
				              + "Тепер введіть " + (valueOfCurrentShip - 1) + 
				                                        "-ярусний корабель.");
					else
				    gameInfo.setText("Ok! Now input a " + 
				                    (valueOfCurrentShip - 1) + "-parts ship.");		
				}
				Service.setSurroundCellsDisable(plFieldVisButtonList, plField, 
						coordinatesOfInputShip[0], coordinatesOfInputShip[1]);				
				
				for(int i = 0; i <= 1; i++){
					for(int j = 0; j <= 1; j++){
						if(i == 0) 
							plShipsData[i][j + 10 + 2*l] = 
							                                10*(1 + j) + l + 3;
						else plShipsData[i][j + 10 + 2*l] = 
								                     coordinatesOfInputShip[j];
					}
				}
				numOfClicks = 0;
			}			
			else{
				     if(isUkrLangPackageActive) gameInfo.setText("Ви ввели "
		    	 		+ "неправильні дані. Повторіть введення ще раз. "
		    	 		+ "\nБудь ласка, введіть " + valueOfCurrentShip + 
		    	 		"-ярусний корабель.");
		    	     else 
			    	 gameInfo.setText("You input wrong data. Try again. "
			    	 	+ "\nPlease input a " + valueOfCurrentShip + 
			    	 	                                       "-parts ship.");
	        	     numOfClicks = 0;
	        	     for(int i = 0; i < 2; i++){
	        	    	 plField[coordinatesOfInputShip[i]] = ' ';
				          plFieldVisButtonList.get(coordinatesOfInputShip[i]).
				        	setStyle("-fx-background: derive(-fx-base,26.4%);");
	        	        statusOfFilling[i + l*2 + 10][1] = -1;
	        	     }
		        }			
          }
          
          if(valueOfCurrentShip == 1 && numOfClicks == valueOfCurrentShip){
  			
  			int l = -1;
  			if(index == 16) l = 0;
  			if(index == 17) l = 1;
  			if(index == 18) l = 2;
  			if(index == 19) l = 3;
  		   
        	if(l == 0 || l == 1 || l == 2){
        		if(isUkrLangPackageActive) gameInfo.setText("Чудово! "
			              + "Тепер введіть інший " + 
						valueOfCurrentShip + "-ярусний корабель.");
				else
					gameInfo.setText("Ok! Now input another " + 
				                          valueOfCurrentShip + "-parts ship.");		
			}
        	Service.setSurroundCellsDisable(plFieldVisButtonList, plField, 
        			                                coordinatesOfInputShip[0]);
        	for(int i = 0; i <= 1; i++){
				for(int j = 0; j <= 0; j++){
					if(i == 0) 
						plShipsData[i][16 + l] = 16 + l;						                                
					else plShipsData[i][16 + l] = 
							                     coordinatesOfInputShip[j];
				}
			}
			numOfClicks = 0;
		  }
          
          if(index == 19){
        	  if(isUkrLangPackageActive) gameInfo.setText("Ви ввели всі кораблі! "
		              + "Вперед до бою!");
			  else
        	  gameInfo.setText("You input everything! Let's go to battle!");
  			  for(Button button:plFieldVisButtonList){
  				button.setDisable(false);
  				button.setOnAction(null);  	  				
  			  }
  			  Filling.autoFill(pcFieldVisButtonList, pcField);
  			  showPlField();
  			  showPcField();
  			  startGame();
  			  return;
  		  }			
	}
	
	
	public void showPlField(){
			for (int i = 0; i < 100; i++){
				plFieldVisButtonList.get(i).setText("");
				if(plField[i] == '@'){
					plFieldVisButtonList.get(i).
						           setStyle("-fx-background-color: #000000;");
				}
			}
	}
		
	public void showPcField(){
		for (int i = 0; i < 100; i++)
			pcFieldVisButtonList.get(i).setText("");
							
    }	
	
	
	public void pcShot(int shot){
		gameInfo.positionCaret(infoLine.toString().length() - 1);
		if(shot == -1){
		   Random rand = new Random();
			if(!leftDirectionOfPcShot && !rightDirectionOfPcShot && 
					!upDirectionOfPcShot && !downDirectionOfPcShot){
				int currentPcShot;				
				while(true){
				  currentPcShot = rand.nextInt();
				  if(currentPcShot < 0) currentPcShot = -currentPcShot;
				  currentPcShot = currentPcShot%100;
				  if (plField[currentPcShot] == 'X' || 
					    		 plField[currentPcShot] == '—') continue;
				  infoLine.append(Shooting.shotToString(
						 currentPcShot, isPcShotsNow, isUkrLangPackageActive));
				  gameInfo.setText(infoLine.toString());
				  gameInfo.positionCaret(infoLine.toString().length() - 1);
				  pcShot(currentPcShot);				  
				  return;
				}
			}
					   
			if(leftDirectionOfPcShot && rightDirectionOfPcShot && 
							     upDirectionOfPcShot && downDirectionOfPcShot){
				int loc = rand.nextInt();
				if(loc < 0) loc = -loc;
				loc = loc%4;
				switch (loc){
				   case 0: {
					         infoLine.append(Shooting.shotToString(
					                    currentPositionOfPc0 - 1, isPcShotsNow, 
					                                  isUkrLangPackageActive));
					         gameInfo.setText(infoLine.toString());
					         gameInfo.positionCaret(
					        		         infoLine.toString().length() - 1);
				   	    	 directionOfPcShot = 0;
				   	    	 pcShot(currentPositionOfPc0 - 1);
				   	    	 return;
						    }
				   case 1: { 
					         infoLine.append(Shooting.shotToString(
		                                currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
  		                                     infoLine.toString().length() - 1);
							 directionOfPcShot = 1;
							 pcShot(currentPositionOfPc0 + 1);
							 return;
					        }
				   case 2: {
					         infoLine.append(Shooting.shotToString(
					        		   currentPositionOfPc0 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
	                                     infoLine.toString().length() - 1);
							 directionOfPcShot = 2;
							 pcShot(currentPositionOfPc0 - 10);
							 return;
					       }
				   case 3: {
					         infoLine.append(Shooting.shotToString(
					        		   currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
							 directionOfPcShot = 3;
							 pcShot(currentPositionOfPc0 + 10);
							 return;
	                        }
				}
			}
				       
		    if(leftDirectionOfPcShot && rightDirectionOfPcShot && 
		    		        upDirectionOfPcShot && !downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%3;
		        switch (loc){
		           case 0: {
		        	         infoLine.append(Shooting.shotToString(
		        		                currentPositionOfPc0 - 1, isPcShotsNow, 
                                                       isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
					         directionOfPcShot = 0;
					         pcShot(currentPositionOfPc0 - 1);
					         return;
			               }
		           case 1: {
		        	         infoLine.append(Shooting.shotToString(
       		                            currentPositionOfPc0 + 1, isPcShotsNow, 
                                                       isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
					         directionOfPcShot = 1;
					         pcShot(currentPositionOfPc0 + 1);
					         return;
		                   }
		           case 2: {
		        	         infoLine.append(Shooting.shotToString(
       		                            currentPositionOfPc0 - 10, isPcShotsNow, 
                                                       isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
                             directionOfPcShot = 2;
                             pcShot(currentPositionOfPc0 - 10);
                             return;
		                   }
			    }
		    }
		       
		    if(leftDirectionOfPcShot && rightDirectionOfPcShot &&
		    		         !upDirectionOfPcShot && downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%3;
		        switch (loc){
		           case 0: {
		        	          infoLine.append(Shooting.shotToString(
       		                            currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                              gameInfo.setText(infoLine.toString());
                              gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
					          directionOfPcShot = 0;
					          pcShot(currentPositionOfPc0 - 1);
					          return;
			               }
		           case 1: {
		        	          infoLine.append(Shooting.shotToString(
       		                            currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                              gameInfo.setText(infoLine.toString());
                              gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
					          directionOfPcShot = 1;
					          pcShot(currentPositionOfPc0 + 1);
					          return;
		                   }

				    case 2: {
				    	      infoLine.append(Shooting.shotToString(
        		                       currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                              gameInfo.setText(infoLine.toString());
                              gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
                              directionOfPcShot = 3;
                              pcShot(currentPositionOfPc0 + 10);
                              return;
                            }
			     }
		    }
		       
		    if(leftDirectionOfPcShot && !rightDirectionOfPcShot 
		    		       && upDirectionOfPcShot && downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%3;
		        switch (loc){

		           case 0: {
		        	         infoLine.append(Shooting.shotToString(
       		                            currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);
					         directionOfPcShot = 0;
					         pcShot(currentPositionOfPc0 - 1);
					         return;
			               }
		           case 1: {		        	  
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 - 10, isPcShotsNow, 
                                                    isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
                             directionOfPcShot = 2;
                             pcShot(currentPositionOfPc0 - 10);
                             return;
		                   }

				    case 2: {				    	
		        	         infoLine.append(Shooting.shotToString(
      		                            currentPositionOfPc0 + 10, isPcShotsNow, 
                                                     isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
                             directionOfPcShot = 3;
                             pcShot(currentPositionOfPc0 + 10);
                             return;
                            }
			      }
		    }
		       
		    if(!leftDirectionOfPcShot && rightDirectionOfPcShot
		    		      && upDirectionOfPcShot && downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%3;
		        switch (loc){
			     case 0:{			    	
	        	         infoLine.append(Shooting.shotToString(
   		                                currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                         gameInfo.setText(infoLine.toString());
                         gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);				         
						 directionOfPcShot = 1;
						 pcShot(currentPositionOfPc0 + 1);
						 return;
			            }
			     case 1:{			    	 
	        	         infoLine.append(Shooting.shotToString(
   		                                currentPositionOfPc0 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                         gameInfo.setText(infoLine.toString());
                         gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);				         
	                     directionOfPcShot = 2;
	                     pcShot(currentPositionOfPc0 - 10);
	                     return;
			            }
			     case 2:{			    	 
	        	         infoLine.append(Shooting.shotToString(
   		                                currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                         gameInfo.setText(infoLine.toString());
                         gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);				         
                         directionOfPcShot = 3;
                         pcShot(currentPositionOfPc0 + 10);
                         return;
                        }
			    }
		    }
		       
		    if(leftDirectionOfPcShot && !rightDirectionOfPcShot
		    		      && upDirectionOfPcShot && !downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%2;
		        switch (loc){

		           case 0: {		        	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
					         directionOfPcShot = 0;
					         pcShot(currentPositionOfPc0 - 1);
					         return;
			               }
		           case 1: {		        	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 - 10, isPcShotsNow, 
                                                    isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
                             directionOfPcShot = 2;
                             pcShot(currentPositionOfPc0 - 10);
                             return;
		                   }
			    }
		    }
		       
		    if(leftDirectionOfPcShot && !rightDirectionOfPcShot
		    		     && !upDirectionOfPcShot && downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%2;
		        switch (loc){
		            case 0:{		            	
		        	         infoLine.append(Shooting.shotToString(
      		                            currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
					         directionOfPcShot = 0;
					         pcShot(currentPositionOfPc0 - 1);
					         return;
			               }
			        case 1:{			        	
		        	         infoLine.append(Shooting.shotToString(
      		                           currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
                             directionOfPcShot = 3;
                             pcShot(currentPositionOfPc0 + 10);
                             return;
			               }
			      }
		    }
		       
		    if(!leftDirectionOfPcShot && rightDirectionOfPcShot
		    		     && upDirectionOfPcShot && !downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%2;
		        switch (loc){
			       case 0:{			    	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
					         directionOfPcShot = 1;
					         pcShot(currentPositionOfPc0 + 1);
					         return;
		                  }
		           case 1:{		        	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
                             directionOfPcShot = 2;
                             pcShot(currentPositionOfPc0 - 10);
                             return;
		                  }
			    }
		    }
		       
		    if(!leftDirectionOfPcShot && rightDirectionOfPcShot 
		    		      &&!upDirectionOfPcShot && downDirectionOfPcShot){
		    	int loc = rand.nextInt();
			    if(loc < 0) loc = -loc;
			    loc = loc%2;
		        switch (loc){
			       case 0:{			    	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 + 1, isPcShotsNow, 
                                                     isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
					         directionOfPcShot = 1;
					         pcShot(currentPositionOfPc0 + 1);
					         return;
		                  }
			       case 1:{			    	   
		        	         infoLine.append(Shooting.shotToString(
     		                            currentPositionOfPc0 + 10, isPcShotsNow, 
                                                    isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                            infoLine.toString().length() - 1);					         
                             directionOfPcShot = 3;
                             pcShot(currentPositionOfPc0 + 10);
                             return;
			              }
			    }
		    }
		       
		    if(leftDirectionOfPcShot && rightDirectionOfPcShot
		    		    && !upDirectionOfPcShot && !downDirectionOfPcShot){
		       if (currentPositionOfPc1 == -1){
		    	   int loc = rand.nextInt();
				   if(loc < 0) loc = -loc;
				   loc = loc%2;
				   switch(loc){
		            case 0:{		            	
		        	         infoLine.append(Shooting.shotToString(
      		                            currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
					         directionOfPcShot = 0;
					         pcShot(currentPositionOfPc0 - 1);
					         return;
			               }

				    case 1:{				    	
		        	         infoLine.append(Shooting.shotToString(
      		                            currentPositionOfPc0 + 1, isPcShotsNow, 
                                                     isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
						     directionOfPcShot = 1;
						     pcShot(currentPositionOfPc0 + 1);
						     return;
			               }
		           }
			   }
		       else{
					if (currentPositionOfPc1 < currentPositionOfPc0){						
	        	        infoLine.append(Shooting.shotToString(
  		                                currentPositionOfPc1 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                        gameInfo.setText(infoLine.toString());
                        gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);					         
						directionOfPcShot = 0;
						pcShot(currentPositionOfPc1 - 1);
						return;
					}
						
					if (currentPositionOfPc1 > currentPositionOfPc0){
						infoLine.append(Shooting.shotToString(
		                                currentPositionOfPc1 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                        gameInfo.setText(infoLine.toString());
                        gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);	
						directionOfPcShot = 1;
						pcShot(currentPositionOfPc1 + 1); 
						return;
					}
			   }
		    }
		       
		    if(!leftDirectionOfPcShot && !rightDirectionOfPcShot
		    		      && upDirectionOfPcShot && downDirectionOfPcShot){
		       if(currentPositionOfPc1 == -1){
		    	   int loc = rand.nextInt();
				   if(loc < 0) loc = -loc;
				   loc = loc%2;
		           switch (loc){
				     case 0:{
				    	     infoLine.append(Shooting.shotToString(
		                                currentPositionOfPc0 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                                             infoLine.toString().length() - 1);	
		                     directionOfPcShot = 2;
		                     pcShot(currentPositionOfPc0 - 10);
		                     return;
				            }
				     case 1:{
				    	     infoLine.append(Shooting.shotToString(
		                            currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                             gameInfo.setText(infoLine.toString());
                             gameInfo.positionCaret(
                            		         infoLine.toString().length() - 1);	
                             directionOfPcShot = 3;
                             pcShot(currentPositionOfPc0 + 10);
                             return;
                            }
			       } 
		        }
		        else{
		          if(currentPositionOfPc1 < currentPositionOfPc0){
		        	  infoLine.append(Shooting.shotToString(
	                                   currentPositionOfPc1 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);	
					  directionOfPcShot = 2;
					  pcShot(currentPositionOfPc1 - 10); 
					  return;
				  }
						
				  if(currentPositionOfPc1 > currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc1 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 3;
					  pcShot(currentPositionOfPc1 + 10); 
					  return;
				  }
				}
		    }
		       
		    if(leftDirectionOfPcShot && !rightDirectionOfPcShot 
		    		   && !upDirectionOfPcShot && !downDirectionOfPcShot){
		       if (currentPositionOfPc1 == -1){
		    	    infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                    gameInfo.setText(infoLine.toString());
                    gameInfo.positionCaret(infoLine.toString().length() - 1);
			        directionOfPcShot = 0;
			        pcShot(currentPositionOfPc0 - 1);
			        return;
		       }
			   else{ 
				  if(currentPositionOfPc1 < currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc1 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 0;
					  pcShot(currentPositionOfPc1 - 1);
					  return;
				  }
				  if(currentPositionOfPc1 > currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 - 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
	                  directionOfPcShot = 0;
	                  pcShot(currentPositionOfPc0 - 1); 
	                  return;
				  }
			  }
		   }
			        
		    if(!leftDirectionOfPcShot && rightDirectionOfPcShot
		    		&& !upDirectionOfPcShot && !downDirectionOfPcShot){
	          if(currentPositionOfPc1 == -1){
	        	  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                  gameInfo.setText(infoLine.toString());
                  gameInfo.positionCaret(infoLine.toString().length() - 1);
				  directionOfPcShot = 1;
				  pcShot(currentPositionOfPc0 + 1);
				  return;
	   	   	  }
	   	   	  else{ 
	   	   	    if(currentPositionOfPc1 > currentPositionOfPc0){
	   	   	          infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc1 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 1;
					  pcShot(currentPositionOfPc1 + 1);
					  return;
				}
				if(currentPositionOfPc1 < currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 + 1, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 1;
					  pcShot(currentPositionOfPc0 + 1);
					  return;
				}
			  }
	        }
		    
		    if(!leftDirectionOfPcShot && !rightDirectionOfPcShot
		    		  && upDirectionOfPcShot && !downDirectionOfPcShot){
	          if (currentPositionOfPc1 == -1){
	        	  infoLine.append(Shooting.shotToString(
                                        currentPositionOfPc0 - 10, isPcShotsNow, 
                                                       isUkrLangPackageActive));
                  gameInfo.setText(infoLine.toString());
                  gameInfo.positionCaret(infoLine.toString().length() - 1);
				  directionOfPcShot = 2;
				  pcShot(currentPositionOfPc0 - 10);
				  return;
	   	   	  }
	   	   	  else{ 
	   	   	    if(currentPositionOfPc1 < currentPositionOfPc0){
	   	   	          infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc1 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 2;
					  pcShot(currentPositionOfPc1 - 10);
					  return;
				} 
				if (currentPositionOfPc1 > currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 - 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 2;
					  pcShot(currentPositionOfPc0 - 10);
					  return;
				}
			  }
	       }
		
	       if(!leftDirectionOfPcShot && !rightDirectionOfPcShot
	    		     && !upDirectionOfPcShot && downDirectionOfPcShot){
	          if(currentPositionOfPc1 == -1){
	        	  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                  gameInfo.setText(infoLine.toString());
                  gameInfo.positionCaret(infoLine.toString().length() - 1);
				  directionOfPcShot = 3;
				  pcShot(currentPositionOfPc0 + 10);
				  return;
	   	   	  }
	   	   	  else{
	   	   	    if(currentPositionOfPc1 > currentPositionOfPc0){
	   	   	          infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc1 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 3;
					  pcShot(currentPositionOfPc1 + 10);
					  return;
				}
				if(currentPositionOfPc1 < currentPositionOfPc0){
					  infoLine.append(Shooting.shotToString(
                                       currentPositionOfPc0 + 10, isPcShotsNow, 
                                                      isUkrLangPackageActive));
                      gameInfo.setText(infoLine.toString());
                      gameInfo.positionCaret(infoLine.toString().length() - 1);
					  directionOfPcShot = 3;
					  pcShot(currentPositionOfPc0 + 10);
		   	   	      return;
				}
			  }
	       }
	   }
	   
	   if(shot >= 0 && shot <= 99){	  
	   
	     if (plField[shot] != '@'){
	    	if(isSoundsOn) badShotSound.play();
		    plField[shot] = '—';		    
			plFieldVisButtonList.get(shot).setText("—");
			plFieldVisButtonList.get(shot).setStyle("-fx-font-weight: bold;");
			plFieldVisButtonList.get(shot).setDisable(true);
			if(isUkrLangPackageActive)
				 infoLine.append("Комп'ютер не влучив! Тепер Ваш хід!\n");
			else 
			infoLine.append("PC didn't hit! Let's do your shot!\n");
			gameInfo.setText(infoLine.toString());
			gameInfo.positionCaret(infoLine.toString().length() - 1);
			switch (directionOfPcShot){
			    case 0: {
			    	     leftDirectionOfPcShot = false; 
			    	     break;
			    	    }
				case 1: {
					     rightDirectionOfPcShot = false; 
					     break;
					    }
				case 2: {
					     upDirectionOfPcShot = false; 
					     break;
					    }
				case 3: {
					     downDirectionOfPcShot = false; 
					     break;
					    }
			}
			directionOfPcShot = -1;
			isPcShotsNow = false;
			plShot();
			return;
		}
		if (Shooting.isDestroyed(plField, shot)){
			if(isSoundsOn) accurateShotSound.play();
			currentPositionOfPc0 = -1;
			currentPositionOfPc1 = -1;
			directionOfPcShot = -1;
			leftDirectionOfPcShot = false;
			rightDirectionOfPcShot = false;
			upDirectionOfPcShot = false;
			downDirectionOfPcShot = false;
			plField[shot] = 'X';
			plFieldVisButtonList.get(shot).setText("X");
			plFieldVisButtonList.get(shot).setStyle("-fx-background-color: "
					                       + "#FF0000; -fx-font-weight: bold");
			pcScore++;
			for (int i = 0; i < 20; i++){
			  if (plShipsData[1][i] == shot){
				int j = plShipsData[0][i]%10;
				switch(j){
				  case 0:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][0], plShipsData[1][1], 
							plShipsData[1][2], plShipsData[1][3]);
					      break;
					      }
				  case 1:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][4], plShipsData[1][5], 
							plShipsData[1][6]);
					      break;
				         }
				  case 2:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][7], plShipsData[1][8], 
							plShipsData[1][9]);
					      break;
				         }
				  case 3:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][10], plShipsData[1][11]);
					      break;
				         }  
				  case 4:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][12], plShipsData[1][13]);
					      break;
				         }
				  case 5:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][14], plShipsData[1][15]);
					      break;
				         }
				  case 6:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][16]);
					      break;
				         }
				  case 7:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][17]);
					      break;
				         } 
				  case 8:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][18]);
					      break;
				         } 
				  case 9:{
					      Service.setSurroundCellsDisable(plFieldVisButtonList, 
					    	plField, plShipsData[1][19]);
					      break;
				         }       	     	         	           	     	          	          	               
				}
			   }
			}
			if(isUkrLangPackageActive)
				 infoLine.append("Корабель знищено!");
			else 
			     infoLine.append("Ship is destroyed!");
			if (pcScore < 10)
				if(isUkrLangPackageActive)
					 infoLine.append(" Комп'ютер стріляє знову!\n");
				else 
				     infoLine.append(" PC shoots again!\n");
			gameInfo.setText(infoLine.toString());
			gameInfo.positionCaret(infoLine.toString().length() - 1);
			if(checkForTheEndOfGame()){
				for(Button but:pcFieldVisButtonList)
					but.setDisable(true);
				for(Button but:plFieldVisButtonList)
					but.setDisable(true);
				return;
			}
			isPcShotsNow = true;
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pcShot(-1);
				}					
			});
			return;
		}
		if (currentPositionOfPc0 == -1) currentPositionOfPc0 = shot;
		else currentPositionOfPc1 = shot;
		isPcShotsNow = true;
	    if(currentPositionOfPc1 == -1){
	     if(shot - 1 >= 0 && Service.isLine(shot, shot - 1) && 
	    		 plField[shot - 1] != '—') 
	    	 leftDirectionOfPcShot = true;
		 if(shot + 1 <= 99 && Service.isLine(shot,shot + 1) && 
				 plField[shot + 1] != '—') 
			 rightDirectionOfPcShot = true;
		 if(shot - 10 >= shot%10 && plField[shot - 10] != '—') 
			 upDirectionOfPcShot = true;
		 if(shot + 10 <= shot%10 + 90 && plField[shot + 10] != '—') 
			 downDirectionOfPcShot = true;
		}
		else{
		 if(Service.orientationOfLine(
				currentPositionOfPc0, currentPositionOfPc1) == "Horizontally"){
			upDirectionOfPcShot = false;
			downDirectionOfPcShot = false;
			if(currentPositionOfPc1 > currentPositionOfPc0
			  &&(currentPositionOfPc1 + 1 > (currentPositionOfPc1/10)*10 + 9
				 || plField[currentPositionOfPc1 + 1] == '—'))
				rightDirectionOfPcShot = false;
			if(currentPositionOfPc1 < currentPositionOfPc0
			  &&(currentPositionOfPc1 - 1 < (currentPositionOfPc1/10)*10 
				|| plField[currentPositionOfPc1 - 1] == '—'))
				leftDirectionOfPcShot = false;
		 }
		 if(Service.orientationOfLine(
				 currentPositionOfPc0, currentPositionOfPc1) == "Vertically"){
			leftDirectionOfPcShot = false;
			rightDirectionOfPcShot = false;
			if(currentPositionOfPc1 < currentPositionOfPc0
			  &&(currentPositionOfPc1 - 10 < 0
				|| plField[currentPositionOfPc1 - 10] == '—'))
				upDirectionOfPcShot = false;
			if(currentPositionOfPc1 > currentPositionOfPc0
			  &&(currentPositionOfPc1 + 10 > currentPositionOfPc1%10 + 90 
			    || plField[currentPositionOfPc1 + 10] == '—'))
			    downDirectionOfPcShot = false;
		 }
		}
		plField[shot] = 'X';
		plFieldVisButtonList.get(shot).setText("X");
		if(isSoundsOn) accurateShotSound.play();
		plFieldVisButtonList.get(shot).setStyle("-fx-background-color: #FF0000;"
				                                     + "-fx-font-weight: bold");
		directionOfPcShot = -1;
		if(isUkrLangPackageActive)
			 infoLine.append("Влучний постріл!");
		else 
		     infoLine.append("Accurate shot!");
		if (pcScore < 10)
			if(isUkrLangPackageActive)
				 infoLine.append(" Комп'ютер стріляє знову!\n");
			else 
			     infoLine.append(" PC shots again!\n"); 
		gameInfo.setText(infoLine.toString());
		gameInfo.positionCaret(infoLine.toString().length() - 1);	
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pcShot(-1);
			}					
		});
		return;
	  }
	}
	
	public void preGameOperations(){
		if(isUkrLangPackageActive)
			 infoLine.append("Давайте дізнаємося, хто стрілятиме першим:"
			 		+ "\nкомп'ютер чи Ви?\n");
		else 
		     infoLine.append("Let's find out who will shoot first: PC or you?\n"
				+ "And now the first shooter is ...");
		gameInfo.setText(infoLine.toString());
		gameInfo.positionCaret(infoLine.toString().length() - 1);
		int firstShot = new Random().nextInt();
		if(firstShot < 0) firstShot = -firstShot;
		if(firstShot%2 == 0){
			if(isUkrLangPackageActive)
				 infoLine.append("Першими стрілятимете Ви!\nБудь ласка, "
				 	+ "зробіть Ваш постріл, натиснувши\n на поле комп'ютера\n");
			else 
			infoLine.append("you!\nPlease, do your shot by clicking on a "
					+ "PC's field\n");
			isPcShotsNow = false;
		}
		else{
			isPcShotsNow = true;
			if(isUkrLangPackageActive)
				 infoLine.append("Першим стрілятиме комп'ютер!\n");
			else 
			     infoLine.append("PC!\n");
		}
		gameInfo.setText(infoLine.toString());
		gameInfo.positionCaret(infoLine.toString().length() - 1);
		arePreGameOperationsDone = true;
	}
	
	public void startGame(){
		if(!arePreGameOperationsDone)
			preGameOperations();
		playerScoreDataField.setText(String.valueOf(playerScore));
		pcScoreDataField.setText(String.valueOf(pcScore));
		if(isPcShotsNow)
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pcShot(-1);
				}					
			});
		else plShot();					
	}
	
	public boolean checkForTheEndOfGame(){
		playerScoreDataField.setText(String.valueOf(playerScore));
		pcScoreDataField.setText(String.valueOf(pcScore));
		if(playerScore == 10 || pcScore == 10){
			if(isSoundsOn) music.play();
			 BackgroundImage bgImgFinish = new BackgroundImage(finishImage, 
					    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
					    BackgroundPosition.CENTER, 
					    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
					    		false, false, true, true));
			 anPane.setBackground(new Background(bgImgFinish));
			 anPane.requestFocus();
			 scoreLabel.setVisible(false);
			 playerScoreDataLabel.setVisible(false);
			 pcScoreDataLabel.setVisible(false);
			 playerScoreDataField.setVisible(false);
			 pcScoreDataField.setVisible(false);
			 restartGameButton.setVisible(true);
			 exitGameButton.setVisible(true);
			 
			 restartGameButton.setOnAction(new EventHandler<ActionEvent>() {
					
				    @Override 
				    public void handle(ActionEvent event) {
				    	handleRestartGameButtonClick(event);
				    	
				    }
				});
			 
			 exitGameButton.setOnAction(new EventHandler<ActionEvent>() {
					
				    @Override 
				    public void handle(ActionEvent event) {
				    	handleExitGameButtonClick(event);
				    	
				    }
				});
		}
		if (playerScore > pcScore && playerScore == 10){ 
			if(isUkrLangPackageActive)
				 infoLine.append("Гра закінчена! Ви перемогли! Вітаємо!\n"
				 		+ "Остаточний рахунок гри: "  + playerScore + " - " + 
						 pcScore + " на Вашу користь!\n");
			else 
		         infoLine.append("Game over! You win! Congratulations!\n"
		         		+ "Total score of the game: " + playerScore + " - " + 
						 pcScore + " with your victory!\n");
		  gameInfo.setText(infoLine.toString());
		  gameInfo.positionCaret(infoLine.toString().length() - 1);
		 return true;	
		}
		if (pcScore > playerScore && pcScore == 10){
			if(isUkrLangPackageActive)
			  infoLine.append("Гра закінчена! Переміг комп'ютер! \n"
			    + "Остаточний рахунок гри: "  + playerScore + " - " + 
						 pcScore + " на користь комп'ютера!\n" 
			  + "Розташування кораблів на кінець гри\n(див. поле комп'ютера)\n");
			else 
		  infoLine.append("PC is winner! Game over! \n"
				 + "Total score of the game: " + playerScore + " - " + 
					 pcScore + " with PC's victory!\n"
		  	   + "Location of ships in the end of the game (see PC's field)\n");
		  gameInfo.setText(infoLine.toString());
		  gameInfo.positionCaret(infoLine.toString().length() - 1);
		  for (int i = 0; i < 100; i++){
			   if(pcField[i] == '@')
				   pcFieldVisButtonList.get(i).
						            setStyle("-fx-background-color: #000000;");
		  }
		  return true;	
		}		 					   
		return false;
	}
	
	public void handleRestartGameButtonClick(ActionEvent event){
		BackgroundImage bgImgStart = new BackgroundImage(startImage, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.CENTER, 
			    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
			    		false, false, true, true));
		anPane.setBackground(new Background(bgImgStart));
		plField = new char[100];
		pcField = new char[100];
		plShipsData = new int[2][20];
		pcShipsData = new int[2][20];	
		playerScore = 0;
		pcScore = 0;
		numOfClicks = 0;
		valueOfCurrentShip = 0;
		coordinatesOfInputShip = new int[4];
		statusOfFilling = new int[][]{{4,-1},{4,-1},{4,-1},{4,-1},
			{3,-1},{3,-1},{3,-1},{3,-1},{3,-1},{3,-1},{2,-1},{2,-1},{2,-1},
			{2,-1},{2,-1},{2,-1},{1,-1},{1,-1},{1,-1},{1,-1}};
		liveStatusOfPlShip = new int[]{4,3,3,2,2,2,1,1,1,1};
		liveStatusOfPcShip = new int[]{4,3,3,2,2,2,1,1,1,1};
		isPcShotsNow = false;
		currentPositionOfPc0 = -1; currentPositionOfPc1 = -1;
		leftDirectionOfPcShot = false; rightDirectionOfPcShot = false;
		upDirectionOfPcShot = false; downDirectionOfPcShot = false;
		directionOfPcShot = -1;
		arePreGameOperationsDone = false;
		infoLine = new StringBuffer("");
		isUkrLangPackageActive = false;
		
		for(Button button:plFieldVisButtonList){
			button.setVisible(false);
			button.setDisable(false);
			button.setStyle("-fx-background: derive(-fx-base,26.4%);");
		}
		for(Button button:pcFieldVisButtonList){
			button.setVisible(false);
			button.setDisable(false);
			button.setStyle("-fx-background: derive(-fx-base,26.4%);");
		}
		
		startText.setVisible(false);
		startText.setEditable(false);
		plFieldLabel.setVisible(false);
		pcFieldLabel.setVisible(false);
		topLabel.setVisible(true);
		automaticFilling.setVisible(false);
		manualFilling.setVisible(false);
		gameInfo.setVisible(false);
		scoreLabel.setVisible(false);
		playerScoreDataLabel.setVisible(false);
		pcScoreDataLabel.setVisible(false);
		playerScoreDataField.setVisible(false);
		pcScoreDataField.setVisible(false);
		playerScoreDataField.setEditable(false);
		pcScoreDataField.setEditable(false);
		gameInfo.setEditable(false);
		plFieldBorderTopLabel.setVisible(false);
		plFieldBorderLeftLabel.setVisible(false);
		pcFieldBorderTopLabel.setVisible(false);
		pcFieldBorderLeftLabel.setVisible(false);
		infoLineLabel.setVisible(false);
		restartGameButton.setVisible(false);
		exitGameButton.setVisible(false);
		languageLabel.setVisible(true);
		ukrLangButton.setVisible(true);
		engLangButton.setVisible(true);
		playerScoreDataField.setText("");
		pcScoreDataField.setText("");
		
		startText.setText("Let's start the game \"Ship's battle\"!" +                                                  
		        "\n\n\n\nPlease, choose the way of filling your field "
		        + "(see below):");
				
		plFieldLabel.setText("Your field");
		topLabel.setText("Ship's battle");
		pcFieldLabel.setText("PC's field");
		automaticFilling.setText("Automatic");
		manualFilling.setText("Manually");
		scoreLabel.setText("Score");
		playerScoreDataLabel.setText("You");
		pcScoreDataLabel.setText("PC");
		plFieldBorderTopLabel.setText(" A   B   C   D   E   F   G   H   I   J ");
		pcFieldBorderTopLabel.setText(" A   B   C   D   E   F   G   H   I   J ");
		infoLineLabel.setText("Info line");
		restartGameButton.setText("New game");
		exitGameButton.setText("Exit");
		developerInfoLabel.setText("Software developer: Oleksandr Chaparin. "
                                          + "All rights reserved " +  '\u00A9');
		
		automaticFilling.setSelected(false);
		manualFilling.setSelected(false);
		soundsOnOffButton.setVisible(false);
		anPane.requestFocus();
		
		ukrLangButton.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent event) {
		    	handleUkrLangButtonClick(event);
		    	
		    }
		});
		
         engLangButton.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent event) {
		    	handleEngLangButtonClick(event);
		    	
		    }
		});
		
	}
	
	public void handleExitGameButtonClick(ActionEvent event){
		Platform.exit();
	}
	
	public void plShot(){
		gameInfo.positionCaret(infoLine.toString().length() - 1);
		anPane.requestFocus();
		for(Button button:pcFieldVisButtonList){
			button.setOnAction(new EventHandler<ActionEvent>(){
				
			    @Override 
			    public void handle(ActionEvent event) {
			    	handlePcFieldVisButtonClick(event);
			    	
			    }
			});	
		}
		for(int i = 0; i < 100; i++){
			if(pcField[i] == '—' || pcField[i] == 'X')
				pcFieldVisButtonList.get(i).setDisable(true);
			else pcFieldVisButtonList.get(i).setDisable(false);
		}
	}
	
	@FXML
    public void handlePcFieldVisButtonClick(ActionEvent event){
		for(Button button:pcFieldVisButtonList){
    	  if(event.getSource() == button){
			int positionOfClick = 
					        Integer.parseInt(button.getId().substring(16));
			if(pcField[positionOfClick] == '@'){
				if(isSoundsOn) accurateShotSound.play();
				if(!Shooting.isDestroyed(pcField, positionOfClick)){    					
					button.setText("X");
					button.setStyle("-fx-background-color: #FF0000;" + 
					                                 "-fx-font-weight: bold");
	    			pcField[positionOfClick] = 'X';
	    			infoLine.append(Shooting.shotToString(
		   				 positionOfClick, isPcShotsNow, isUkrLangPackageActive));
	    			if(isUkrLangPackageActive)
	   				 infoLine.append("Вдалий хід! Будь ласка, зробіть "
	   				 		                         + "наступний постріл!\n");
	   			    else 
	    			 infoLine.append("Accurate shot! Please, do your shot "
	    			 		                                     + "again!\n");
	    			gameInfo.setText(infoLine.toString());
	    			gameInfo.positionCaret(infoLine.toString().length() - 1);
	    			plShot();
	    			return;
	    			
				}
				else{    				
				    button.setText("X");
				    button.setStyle("-fx-background-color: #FF0000;"
				    		                        + "-fx-font-weight: bold");
    				pcField[positionOfClick] = 'X';
    				infoLine.append(Shooting.shotToString(
   		   				 positionOfClick, isPcShotsNow, isUkrLangPackageActive));
    				if(isUkrLangPackageActive)
    				   infoLine.append("Корабель знищено!");
    		   	    else infoLine.append("Ship is destroyed!");
    				playerScore++;
    				if (playerScore < 10)
    					if(isUkrLangPackageActive)
    	    				   infoLine.append(" Будь ласка, зробіть наступний "
    	    				   		+ "\nпостріл!\n");
    	    		   	else 
    					     infoLine.append(" Please, do your shot again!\n");
    				else infoLine.append("\n");
    				gameInfo.setText(infoLine.toString());
    				gameInfo.positionCaret(infoLine.toString().length() - 1);
    				for (int i = 0; i < 20; i++){
    				  if (pcShipsData[1][i] == positionOfClick){
    					int j = pcShipsData[0][i]%10;
    					switch(j){
    					  case 0:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][0], pcShipsData[1][1], 
    							   pcShipsData[1][2], pcShipsData[1][3]);
    						      break;
    						     }
    					  case 1:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][4], pcShipsData[1][5], 
    							   pcShipsData[1][6]);
    						      break;
    					         }
    					  case 2:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][7], pcShipsData[1][8], 
    							   pcShipsData[1][9]);
    						      break;
    					         }
    					  case 3:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField,
    						       pcShipsData[1][10],pcShipsData[1][11]);
    						      break;
    					         }  
    					  case 4:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField,
    						       pcShipsData[1][12],pcShipsData[1][13]);
    						      break;
    					         }
    					  case 5:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField,
    						       pcShipsData[1][14],pcShipsData[1][15]);
    						      break;
    					         }
    					  case 6:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField,
    						       pcShipsData[1][16]);
    						      break;
    					         }
    					  case 7:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][17]);
    						      break;
    					         } 
    					  case 8:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][18]);
    						      break;
    					         } 
    					  case 9:{
    						      Service.setSurroundCellsDisable(
    						       pcFieldVisButtonList, pcField, 
    						       pcShipsData[1][19]);
    						      break;
    					         }       	     	         	           	     	          	          	               
    					}
    				   }
				     }
    				if(checkForTheEndOfGame()){
    					for(Button but:pcFieldVisButtonList)
    						but.setDisable(true);
    					for(Button but:plFieldVisButtonList)
    						but.setDisable(true);
    					return;    				
    				}
			     }
				plShot();
				return;					
			 }    				
				   				
			if(pcField[positionOfClick] != '@'){
				if(isSoundsOn) badShotSound.play();
				button.setText("—");
				button.setStyle("-fx-font-weight: bold");
				pcField[positionOfClick] = '—';
				infoLine.append(Shooting.shotToString(positionOfClick, 
						                isPcShotsNow, isUkrLangPackageActive));
				isPcShotsNow = true;
				if(isUkrLangPackageActive)
 				  infoLine.append("Ви не влучили! Тепер стріляє комп'ютер!\n");
 		   	    else 
				  infoLine.append("You didn't hit! And now PC is shooting!\n");
				gameInfo.setText(infoLine.toString());
				gameInfo.positionCaret(infoLine.toString().length() - 1);
				for(Button but: pcFieldVisButtonList){
					but.setOnAction(null);
					but.setDisable(true);
				}
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						pcShot(-1);
					}					
				});
				return;
			}
         }
	    }
	}
		 
}	
		


