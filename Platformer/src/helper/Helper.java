package helper;

public class Helper {
	
	public static int encodeBoolean(boolean bool) {
		if(bool) {
			return 1;
		}
		return 0;
	}
	
	public static String boolArrayToString(boolean[] boolArray) {
		String s = "{";
		
		for(int idx = 0; idx < boolArray.length; ++idx) {
			s += boolArray[idx];
			
			if(idx != boolArray.length - 1) {
				s += ", ";
			}
		}
		
		s += "}";
		
		return s;
	}
}
