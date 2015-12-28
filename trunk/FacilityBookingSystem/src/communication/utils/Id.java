package communication.utils;

import java.util.Random;



public class Id implements Comparable<Id>{
	 
	static Random randomGenerator = new Random();
	int m_id;
	
	Id(){
		m_id = Math.abs(randomGenerator.nextInt());
	}
	
	public Id(String id){
		m_id = Integer.parseInt(id);
	}
	
	public static Id getUniqueId(){
		return new Id();
	}
	
	public static int getSizeOfIdInBytes(){
		return ByteConversions.SizeOfIntegerInBytes;
	}
	
	
	public static Id deserialize(byte[] serializedId){
		Id deserializedId = new Id();
		deserializedId.m_id = ByteConversions.byteArrayToInt(serializedId);
		return deserializedId;
	}
	
	@Override
	public boolean equals(Object object){
		Id id = (Id)object;
		return id.m_id == m_id;
	}
	
	public byte[] serialize(){
		return ByteConversions.intToByteArray(m_id);
	}
	
	public String toString(){
		Integer id = m_id;
		return id.toString();
	}

	@Override
	public int compareTo(Id o) {
		Integer thisId = m_id;
		Integer oId = o.m_id;
		return thisId.compareTo(oId);
	}
	
	@Override
	public int hashCode(){
		return m_id;
	}
	
}
