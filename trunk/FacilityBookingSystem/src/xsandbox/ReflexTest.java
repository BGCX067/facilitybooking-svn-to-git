package xsandbox;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class ReflexTest {

	private Integer[] fields;

	public int voe;

	public static <T> T getFieldValue(Field field, Object object) {

		T fieldValue = null;
		try {
			fieldValue = (T) field.get(object);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return fieldValue;
		}
	}

	public Integer[] getFields() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("String").newInstance();

		boolean fieldGenereated = (fields != null);

		if (fieldGenereated) {
			System.out.println("Filed retrieved");
			return fields;

		} else {
			System.out.println("Filed generated");
			List<Integer> resultFields = new ArrayList<Integer>();

			for (Field field : getClass().getDeclaredFields()) {
				try {
					resultFields.add((Integer) field.get(this));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Integer[] dummyArray = new Integer[0];
			fields = resultFields.toArray(dummyArray);

			return fields;
		}
	}

}
