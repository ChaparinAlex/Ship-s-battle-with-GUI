package com.chaparin;

public class Shooting {
	
	public static boolean isDestroyed(char[] field, int shot){
	    if (field == Controller.getPlField()){ 
		   for (int i = 0; i < 20; i++){
			if (Controller.getPlShipsDataByIndexes(1, i) == shot){
				Controller.setLiveStatusOfPlShipByIndex(
						Controller.getPlShipsDataByIndexes(0, i)%10, 
						Controller.getLiveStatusOfPlShipByIndex(
							Controller.getPlShipsDataByIndexes(0, i)%10) - 1);
			break;
			}
		   }
		   for (int i = 0; i < 10; i++)
			if (Controller.getLiveStatusOfPlShipByIndex(i) == 0){
				Controller.setLiveStatusOfPlShipByIndex(i, -1);
				return true;
			}
		}
	    if (field == Controller.getPcField()){ 
			   for (int i = 0; i < 20; i++){
				if (Controller.getPcShipsDataByIndexes(1, i) == shot){
					Controller.setLiveStatusOfPcShipByIndex(
							Controller.getPcShipsDataByIndexes(0, i)%10, 
							Controller.getLiveStatusOfPcShipByIndex(
								Controller.getPcShipsDataByIndexes(0, i)%10) - 1);
				break;
				}
			   }
			   for (int i = 0; i < 10; i++)
				if (Controller.getLiveStatusOfPcShipByIndex(i) == 0){
					Controller.setLiveStatusOfPcShipByIndex(i, -1);
					return true;
				}
			}
		return false;
	}
	
	public static String shotToString(int shot, boolean isPcShotsNow, 
			                                               boolean isUaActive){
		String s = "";
		if(isPcShotsNow && isUaActive) s += "Постріл комп'ютера: ";
		if(isPcShotsNow && !isUaActive) s += "PC's shot: ";
		if(!isPcShotsNow && isUaActive) s += "Ваш постріл: ";
		if(!isPcShotsNow && !isUaActive) s += "Your shot: ";
		s += shot/10 + 1;
		switch(shot%10){
		    case 0:{
		    	if(isUaActive) s += "А";
				else s += "A";
		    	break;
		    }
		    case 1:{
		    	if(isUaActive) s += "Б";
				else s += "B";
		    	break;
		    }
		    case 2:{
		    	if(isUaActive) s += "В";
				else s += "C";
		    	break;
		    }
		    case 3:{
		    	if(isUaActive) s += "Г";
				else s += "D";
		    	break;
		    }
		    case 4:{
		    	if(isUaActive) s += "Д";
				else s += "E";
		    	break;
		    }
		    case 5:{
		    	if(isUaActive) s += "Е";
				else s += "F";
		    	break;
		    }
		    case 6:{
		    	if(isUaActive) s += "Є";
				else s += "G";
		    	break;
		    }
		    case 7:{
		    	if(isUaActive) s += "Ж";
				else s += "H";
		    	break;
		    }
		    case 8:{
		    	if(isUaActive) s += "З";
				else s += "I";
		    	break;
		    }
		    case 9:{
		    	if(isUaActive) s += "И";
				else s += "J";
		    	break;
		    }
		}
		s += "\n";
		return s;
	}
	

}
