package Sterne;

import java.util.Calendar;

public class Time {

	/*private*/ Calendar date;
	
	public Time(){
		
		date = Calendar.getInstance();
	}
	
	public Time(Calendar _date){
		
		this.date = _date;
	}
	
	public double julianCentury(){
		
		return (367 * date.get(Calendar.YEAR)
				- Math.floor(7 * (date.get(Calendar.YEAR) + Math.floor((date.get(Calendar.MONTH) + 10) / 12)) / 4)
				+ Math.floor(275 * (date.get(Calendar.MONTH) + 1) / 9)
				+ date.get(Calendar.DATE)
				- 730531.5
				+ date.get(Calendar.HOUR) / 24
				+ date.get(Calendar.MINUTE) / 1440
				+ date.get(Calendar.SECOND) / 86400
				) / 36525;
	}
	
	public void addSecond(int amount){
		
		date.add(Calendar.SECOND, amount);
	}

}
