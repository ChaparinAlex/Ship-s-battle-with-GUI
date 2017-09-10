package com.chaparin;

import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class Filling {
	
	public static void autoFill(List<Button> list, char[] field){
	    Random r = new Random();
	    int randomPosition;
	    
	    //completion of 4-parts ship
		int k = r.nextInt()%2;
		if(k < 0) k = -k;
		switch (k){
		   case 0: {
			        //horizontally orientation of 4-parts ship
				    int k1,k2;
				    while(true){
				      k1 = r.nextInt()%10;
				      k2 = r.nextInt()%7;
				      if(k1 < 0) k1 = -k1;
				      if(k2 < 0) k2 = -k2;
				      randomPosition = k1*10 + k2;
				      if (field[randomPosition] != '—'
				    	&& field[randomPosition] != '@'
				    	&& field[randomPosition + 1] != '—'
				    	&& field[randomPosition + 1] != '@'
				    	&& field[randomPosition + 2] != '—'
				    	&& field[randomPosition + 2] != '@'
				    	&& field[randomPosition + 3] != '—'
				    	&& field[randomPosition + 3] != '@'){
				       	   field[randomPosition] = '@';
				       	   field[randomPosition + 1] = '@';
				       	   field[randomPosition + 2] = '@';
				       	   field[randomPosition + 3] = '@';
				       	Service.setSurroundCellsDisable(list, field, 
				       			randomPosition,	randomPosition + 1, 
				       			randomPosition + 2,	randomPosition + 3);
				       	   if (field == Controller.getPcField()){
				       		Controller.setPcShipsDataByIndexes(0, 0, 10);
				       		Controller.setPcShipsDataByIndexes(
				       				                     1, 0, randomPosition);
				       		Controller.setPcShipsDataByIndexes(0, 1, 20);
				       		Controller.setPcShipsDataByIndexes(
				       				                 1, 1, randomPosition + 1);
				       		Controller.setPcShipsDataByIndexes(0, 2, 30);
				       		Controller.setPcShipsDataByIndexes(
				       				                 1, 2, randomPosition + 2);
				       		Controller.setPcShipsDataByIndexes(0, 3, 40);
				       		Controller.setPcShipsDataByIndexes(
				       				                 1, 3, randomPosition + 3);
				       		}
				       	   if (field == Controller.getPlField()){
				       		Controller.setPlShipsDataByIndexes(0, 0, 10);   
				       		Controller.setPlShipsDataByIndexes(
				       				                     1, 0, randomPosition);
				       		Controller.setPlShipsDataByIndexes(0, 1, 20);
				       		Controller.setPlShipsDataByIndexes(
				       				                 1, 1, randomPosition + 1);
				       		Controller.setPlShipsDataByIndexes(0, 2, 30);
				       		Controller.setPlShipsDataByIndexes(
				       				                 1, 2, randomPosition + 2);
				       		Controller.setPlShipsDataByIndexes(0, 3, 40);
				       		Controller.setPlShipsDataByIndexes(
				       				                 1, 3, randomPosition + 3);
						   }					       		  
				       	   break;
						 }
					   }
				       break;
		        	}
		    case 1: { 
		    	      //vertically orientation of 4-parts ship
		    	       int k1,k2;
				       while(true){
				        k1 = r.nextInt()%7;
				       	k2 = r.nextInt()%10;
				       	if(k1 < 0) k1 = -k1;
				       	if(k2 < 0) k2 = -k2;
				       	randomPosition = k1*10 + k2;
				       	if (field[randomPosition] != '—'
				       		&& field[randomPosition] != '@'
				       		&& field[randomPosition + 10] != '—'
				       		&& field[randomPosition + 10] != '@'
				       		&& field[randomPosition + 20] != '—'
				       		&& field[randomPosition + 20] != '@'
				       		&& field[randomPosition + 30] != '—'
				       		&& field[randomPosition + 30] != '@'){
				       	      field[randomPosition] = '@';
				       		  field[randomPosition + 10] = '@';
				       		  field[randomPosition + 20] = '@';
				       		  field[randomPosition + 30] = '@';
				       		Service.setSurroundCellsDisable(list, field, 
						       	randomPosition, randomPosition + 10, 
						       	randomPosition + 20, randomPosition + 30);
				       		  if (field == Controller.getPcField()){ 
				       			Controller.setPcShipsDataByIndexes(0, 0, 10);
				       			Controller.setPcShipsDataByIndexes(
				       					                       1, 0, k1*10+k2);
				       			Controller.setPcShipsDataByIndexes(0, 1, 20);
				       			Controller.setPcShipsDataByIndexes(
				       					                 1, 1, (k1+1)*10 + k2);
				       			Controller.setPcShipsDataByIndexes(0, 2, 30);
				       			Controller.setPcShipsDataByIndexes(
				       					                 1, 2, (k1+2)*10 + k2);
				       			Controller.setPcShipsDataByIndexes(0, 3, 40);
				       			Controller.setPcShipsDataByIndexes(
				       					                 1, 3, (k1+3)*10 + k2);
				       		  }
				       		 if (field == Controller.getPlField()){ 
				       			Controller.setPlShipsDataByIndexes(0, 0, 10);
				       			Controller.setPlShipsDataByIndexes(
				       					                       1, 0, k1*10+k2);
				       			Controller.setPlShipsDataByIndexes(0, 1, 20);
				       			Controller.setPlShipsDataByIndexes(
				       					                 1, 1, (k1+1)*10 + k2);
				       			Controller.setPlShipsDataByIndexes(0, 2, 30);
				       			Controller.setPlShipsDataByIndexes(
				       					                 1, 2, (k1+2)*10 + k2);
				       			Controller.setPlShipsDataByIndexes(0, 3, 40);
				       			Controller.setPlShipsDataByIndexes(
				       					                 1, 3, (k1+3)*10 + k2);
				       		  }			       		 
				       		  break;
						   }
					    }
			        	break;
		        	}
		}
		
		//completion of two 3-parts ships
		for (int j = 0; j < 2; j++)
		{
			k = r.nextInt()%2;
			if(k < 0) k = -k;
			switch (k){
		      case 0: { 
		    	       //horizontally orientation of 3-parts ship
				       int k1,k2;
				       while(true){
				        k1 = r.nextInt()%10;
				       	k2 = r.nextInt()%8;
				       	if(k1 < 0) k1 = -k1;
				       	if(k2 < 0) k2 = -k2;
				       	randomPosition = k1*10 + k2;
				       	if (field[randomPosition] != '—'
				       		 && field[randomPosition] != '@'
				       		 && field[randomPosition + 1] != '—'
				       		 && field[randomPosition + 1] != '@'
				       		 && field[randomPosition + 2] != '—'
				       		 && field[randomPosition + 2] != '@'){
				       	      field[randomPosition] = '@';
				       		  field[randomPosition + 1] = '@';
				       		  field[randomPosition + 2] = '@';
				       		Service.setSurroundCellsDisable(list, field, 
							       	randomPosition, randomPosition + 1, 
							       	randomPosition + 2);
				       		  if (field == Controller.getPcField()){ 
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+1, 11 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					         1, 3*(1+j)+1, randomPosition);
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+2, 21 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					     1, 3*(1+j)+2, randomPosition + 1);
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+3, 31 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					     1, 3*(1+j)+3, randomPosition + 2);
				       		  }
				       		if (field == Controller.getPlField()){ 
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+1, 11 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					         1, 3*(1+j)+1, randomPosition);
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+2, 21 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					     1, 3*(1+j)+2, randomPosition + 1);
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+3, 31 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					     1, 3*(1+j)+3, randomPosition + 2);
				       		  }
				       		  break;
						   }
					   }
				       break;
		        	}
		    case 1: {  
		    	       // vertically orientation of 3-parts ship
		    	       int k1,k2;
				       while(true){
				        k1 = r.nextInt()%8;
				       	k2 = r.nextInt()%10;
				       	if(k1 < 0) k1 = -k1;
				       	if(k2 < 0) k2 = -k2;
				       	randomPosition = k1*10 + k2;
				       	if (field[randomPosition] != '—'
				       		 && field[randomPosition] != '@'
				       		 && field[randomPosition + 10] != '—'
				       		 && field[randomPosition + 10] != '@'
				       		 && field[randomPosition + 20] != '—'
				       		 && field[randomPosition + 20] != '@'){
				       	      field[randomPosition] = '@';
				       		  field[randomPosition + 10] = '@';
				       		  field[randomPosition + 20] = '@';
				       		Service.setSurroundCellsDisable(list, field, 
							       	randomPosition, randomPosition + 10, 
							       	randomPosition + 20);
				       		  if (field == Controller.getPcField()){ 
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+1, 11 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					         1, 3*(1+j)+1, randomPosition);
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+2, 21 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					    1, 3*(1+j)+2, randomPosition + 10);
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 3*(1+j)+3, 31 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					     1, 3*(1+j)+3, randomPosition + 20);
				       		  }
				       		if (field == Controller.getPlField()){ 
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+1, 11 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					         1, 3*(1+j)+1, randomPosition);
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+2, 21 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					    1, 3*(1+j)+2, randomPosition + 10);
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 3*(1+j)+3, 31 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					     1, 3*(1+j)+3, randomPosition + 20);
				       		  }
				       		  break;
						   }
					   }
			        	break;
		        	}
		    }
		}
		
		//completion of three 2-parts ships
		for (int j = 0; j < 3; j++){
		  k = r.nextInt()%2;
		  if(k < 0) k = -k;
		  switch (k){
		    case 0: { 
		    	      // horizontally orientation of 2-parts ship
				       int k1,k2;
				       while(true){
				        k1 = r.nextInt()%10;
				       	k2 = r.nextInt()%9;
				       	if(k1 < 0) k1 = -k1;
				       	if(k2 < 0) k2 = -k2;
				       	randomPosition = k1*10 + k2;
				       	if (field[randomPosition] != '—'
				       		 && field[randomPosition] != '@'
				       		 && field[randomPosition + 1] != '—'
				       		 && field[randomPosition + 1] != '@'){
				       	      field[randomPosition] = '@';
				       		  field[randomPosition + 1] = '@';
				       		Service.setSurroundCellsDisable(list, field, 
							       	randomPosition, randomPosition + 1);
				       		  if (field == Controller.getPcField()){ 
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 2*(4+j)+2, 13 + j);  
				       			Controller.setPcShipsDataByIndexes(
				       					         1, 2*(4+j)+2, randomPosition);
				       			Controller.setPcShipsDataByIndexes(
				       					                 0, 2*(4+j)+3, 23 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					     1, 2*(4+j)+3, randomPosition + 1);
				       		  }
				       		if (field == Controller.getPlField()){ 
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 2*(4+j)+2, 13 + j);  
				       			Controller.setPlShipsDataByIndexes(
				       					         1, 2*(4+j)+2, randomPosition);
				       			Controller.setPlShipsDataByIndexes(
				       					                 0, 2*(4+j)+3, 23 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					     1, 2*(4+j)+3, randomPosition + 1);
				       		  }
				       		  break;
						   }
					   }
				       break;
		        	}
		    case 1: { 
		    	      // vertically orientation of 2-parts ship
		    	       int k1,k2;
				       while(true){
				        k1 = r.nextInt()%9;
				       	k2 = r.nextInt()%10;
				       	if(k1 < 0) k1 = -k1;
				       	if(k2 < 0) k2 = -k2;
				       	randomPosition = k1*10 + k2;
				       	if (field[randomPosition] != '—'
				       		&& field[randomPosition] != '@'
				       		&& field[randomPosition + 10] != '—'
				       		&& field[randomPosition + 10] != '@'){
				       	      field[randomPosition] = '@';
				       		  field[randomPosition + 10] = '@';
				       		Service.setSurroundCellsDisable(list, field, 
							       	randomPosition, randomPosition + 10);
				       		  if (field == Controller.getPcField()){ 
				       			Controller.setPcShipsDataByIndexes(
				       					                  0, 2*(4+j)+2, 13 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					          1, 2*(4+j)+2, randomPosition);
				       			Controller.setPcShipsDataByIndexes(
				       					                  0, 2*(4+j)+3, 23 + j);
				       			Controller.setPcShipsDataByIndexes(
				       					     1, 2*(4+j)+3, randomPosition + 10);
				       		  }
				       		if (field == Controller.getPlField()){ 
				       			Controller.setPlShipsDataByIndexes(
				       					                  0, 2*(4+j)+2, 13 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					          1, 2*(4+j)+2, randomPosition);
				       			Controller.setPlShipsDataByIndexes(
				       					                  0, 2*(4+j)+3, 23 + j);
				       			Controller.setPlShipsDataByIndexes(
				       					     1, 2*(4+j)+3, randomPosition + 10);
				       		  }
				       		  break;
						   }
					   }
			        	break;
		        	}
		    }
		}
		
		//completion of four 1-parts ships
		for (int j = 0; j < 4; j++){
		    int k1,k2;
			while(true){
			    k1 = r.nextInt()%10;
				k2 = r.nextInt()%10;
				if(k1 < 0) k1 = -k1;
		       	if(k2 < 0) k2 = -k2;
		       	randomPosition = k1*10 + k2;
				if (field[randomPosition] != '—'
					 && field[randomPosition] != '@'){
				        field[randomPosition] = '@';
				    	Service.setSurroundCellsDisable(list, field, 
						       	                              randomPosition);
				       	if (field == Controller.getPcField()){
				       		Controller.setPcShipsDataByIndexes(0, 16+j, 16+j);
				       		Controller.setPcShipsDataByIndexes(
				       				                 1, 16+j, randomPosition);
				       	}
				       	if (field == Controller.getPlField()){
				       		Controller.setPlShipsDataByIndexes(0, 16+j, 16+j);
				       		Controller.setPlShipsDataByIndexes(
				       				                 1, 16+j, randomPosition);
				       	}
				       	break;
					}
			}
				       
		}
		
		for(int i = 0; i < 100; i++){
        	if(field[i] == '—'){
        		field[i] = ' ';
        		list.get(i).setText("");
        	}
    	}
		
		for(Button listButton:list){
			listButton.setDisable(false);
		}
		
		return;
	}

}
