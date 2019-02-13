package com.example.alihasan.vistaraprototype.TimeUtil;

public class TimeFormatUtil {
	
	public static String toDisplayString(int timeHundreds){
		int hundreds,seconds,minutes,hours;
		// Used to add a '0' in the .03(a one digit number) so it becomes .03
		// So if the result of timeHundreds%100 equals a one digit number add 0
		// to the end of the time string
		// Avoids unnecessary if statements
		String[] formatterArrayMillis = new String[2];
		String formattedSeconds,formattedMinutes,formattedHours;

		hundreds=timeHundreds%100;
		String milliSecStr = Integer.toString(hundreds);
		formatterArrayMillis[0] = "0" + milliSecStr;
		formatterArrayMillis[1] = milliSecStr;

		seconds=(timeHundreds/=100)%60;
		minutes=(timeHundreds/=60)%60;
		hours = (timeHundreds/=60)%60;

		//format output
		formattedSeconds=Integer.toString(seconds/10)+
				Integer.toString(seconds%10);
		formattedMinutes=Integer.toString(minutes/10)+
				Integer.toString(minutes%10);
		formattedHours=Integer.toString(hours/10)+
				Integer.toString(hours%10);

		int millSecDigitsCnt = milliSecStr.length();
		String timeString =
				formattedHours+":"+
				formattedMinutes+":"+
				formattedSeconds+"."+
				formatterArrayMillis[millSecDigitsCnt - 1];
		
		return timeString;
	}

}
