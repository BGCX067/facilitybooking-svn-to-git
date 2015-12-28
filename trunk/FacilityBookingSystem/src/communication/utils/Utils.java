package communication.utils;

public class Utils {

	public static byte[] trimStartOfArray(byte[] trimmingArray, byte[] trimmedArray) {
		return trimStartOfArray(trimmingArray.length, trimmedArray);

	}

	public static byte[] trimStartOfArray(int elementsToTrim, byte[] trimmedArray) {
		int resultArrayLength = trimmedArray.length - elementsToTrim;

		if (resultArrayLength <= 0) {
			return new byte[0];
		}

		byte[] resultArray = new byte[resultArrayLength];

		System.arraycopy(trimmedArray, elementsToTrim, resultArray, 0,
				resultArrayLength);

		return resultArray;
	}
	
	public static byte[] mergeArrays(byte[] firstArray, byte[] secondArray){
		int mergedLength = firstArray.length + secondArray.length;
		byte[] mergedArray = new byte[mergedLength];
		
		System.arraycopy(firstArray, 0, mergedArray, 0, firstArray.length);
		
		int borderIndex = firstArray.length;
		System.arraycopy(secondArray, 0, mergedArray, borderIndex, secondArray.length);
		
		return mergedArray;
	}
	
	public static byte[] cutOutSubArray(int startIndex, int length, byte[] sourceArray){
		byte[] subArray = new byte[length];
		System.arraycopy(sourceArray, startIndex, subArray, 0, length);
		
		return subArray;
	}
	
	public static Byte[] byteArrayToWrapperArray(byte[] primitiveArray){
		Byte[] wrapperArray = new Byte[primitiveArray.length];
		
		for (int i = 0; i < primitiveArray.length; i++) {
			byte primiteValue = primitiveArray[i];
			wrapperArray[i] = new Byte(primiteValue);
		}
		
		return wrapperArray;
	}
	
	
}
