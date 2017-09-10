package com.chaparin;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Button;

public class Service {
	
	public static boolean isLine(int point, int... points){
		if(points.length == 0 && point >= 0 && point <= 99) return true;
		int[] arr = new int[points.length + 1];
		for(int i = 0; i < points.length; i++){
			arr[i] = points[i];
		}
		arr[points.length] = point;
		Arrays.sort(arr);
		if(arr[0] < 0 || arr[arr.length - 1] > 99) return false;
		int k = 0;
		for(int i = 0; i < arr.length; i++){
			if(i != arr.length - 1)
				k += arr[i + 1] - arr[i];
		}
		if(k == (arr.length - 1)*10)
			return true;
		if(k == arr.length - 1){
			if(arr[arr.length - 1] <= ((arr[0]/10)*10 + 9))
				return true;
		}
		return false;
	}
	
	public static int minNumber(int num, int... nums){
		if(nums.length == 0) return num;
		int[] arr = new int[nums.length + 1];
		for(int i = 0; i < nums.length; i++){
			arr[i] = nums[i];
		}
		arr[nums.length] = num;
		Arrays.sort(arr);
		return arr[0];
	}
	
	public static int maxNumber(int num, int... nums){
		if(nums.length == 0) return num;
		int[] arr = new int[nums.length + 1];
		for(int i = 0; i < nums.length; i++){
			arr[i] = nums[i];
		}
		arr[nums.length] = num;
		Arrays.sort(arr);
		return arr[arr.length - 1];
	}
	
	public static String orientationOfLine(int cell, int... cells){
		if(cells.length == 0) return "Horizontally";
		if((maxNumber(cell, cells) - minNumber(cell, cells)) <= 3)
			return "Horizontally";		
		return "Vertically";
	}
	
	public static void setSurroundCellsDisable(List<Button> list, char[] field, int cell, 
			                                                     int... cells){
		boolean left = false, right = false, up = false, down = false;
		int min =  minNumber(cell, cells), max = maxNumber(cell, cells);
		int[] sortedCells = new int[cells.length + 1];
		for(int i = 0; i < cells.length; i++){
			sortedCells[i] = cells[i];
		}
		sortedCells[sortedCells.length - 1] = cell;
		Arrays.sort(sortedCells);
		
		if(orientationOfLine(cell, cells) == "Horizontally"){			
			// check availability of potential movements of ship 
			if(sortedCells[sortedCells.length - 1] + 1 <= 99 && 
				   isLine(sortedCells[sortedCells.length - 1] + 1, sortedCells))
				right = true;
			if(sortedCells[0] - 1 >= 0 && isLine(sortedCells[0] - 1, sortedCells))
				left = true;
			if((sortedCells[0] - 10) >= sortedCells[0]%10)
				up = true;
			if((sortedCells[0] + 10) <= sortedCells[0]%10 + 90)
				down = true;
			
			/*make disable cells that are surrounded of our ship (line) 
			  depends of available potential movements of ship */
			
			if(right && left && up && down){
				for(int i = min - 11; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(right && !left && !up && down){
				for(int i = min; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 10; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(right && !left && up && !down){
				for(int i = min - 10; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(!right && left && up && !down){
				for(int i = min - 11; i <= max - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}			

			if(!right && left && !up && down){
				for(int i = min - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= max + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(right && left && !up && down){
				for(int i = min - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(!right && left && up && down){
				for(int i = min - 11; i <= max - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= max + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(right && left && up && !down){
				for(int i = min - 11; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
			
			if(right && !left && up && down){
				for(int i = min - 10; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 10; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
		}
		
        if(orientationOfLine(cell, cells) == "Vertically"){
        	// check availability of potential movements of ship        	
        	if((sortedCells[0] + 1) <= (sortedCells[0]/10)*10 + 9)
        		right = true;
        	if((sortedCells[0] - 1) >= (sortedCells[0]/10)*10)
        		left = true;
        	if(sortedCells[0] - 10 >= sortedCells[0]%10)
        		up = true;
        	if(sortedCells[sortedCells.length - 1] + 10 <= 
        			              sortedCells[sortedCells.length - 1]%10 + 90)
        		down = true;        	
        				
        	/*make disable that cells which are surrounded of our ship (line) 
        				  depends of available potential movements of ship */
        	
        	if(right && left && up && down){
				for(int i = min - 11; i <= min - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 9; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(right && !left && !up && down){
				for(int i = min; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 10; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 10; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 10; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(right && !left && up && !down){
				for(int i = min - 10; i <= min - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 10; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 10; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(!right && left && up && !down){
				for(int i = min - 11; i <= min - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= min; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(!right && left && !up && down){
				for(int i = min - 1; i <= min; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 9; i <= max + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(right && left && !up && down){
				for(int i = min - 1; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 9; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(!right && left && up && down){
				for(int i = min - 11; i <= min - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= min; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 9; i <= max + 10; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(right && left && up && !down){
				for(int i = min - 11; i <= min - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min - 1; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 9; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 11; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 1; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
        	
        	if(right && !left && up && down){
				for(int i = min - 10; i <= min - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min; i <= min + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = min + 10; i <= min + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max - 10; i <= max - 9; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max; i <= max + 1; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
				for(int i = max + 10; i <= max + 11; i++){
					list.get(i).setDisable(true);
					if(field[i] != '@') field[i] = '—';
				}
			}
		}
    }

}
