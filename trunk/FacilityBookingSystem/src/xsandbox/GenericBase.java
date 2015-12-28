package xsandbox;

public class GenericBase<T> {
	private T field ;

	public void setField(T value){
		field = value;
	}
	public T getField(){
		return field;
	}
}
