package com.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shortesetuniqueprefix.ListShortestUniquePrefixGenerator;

public class MyType {
	private Integer integer;
	
	public MyType(Integer integer) {
		this.integer = integer;
	}
	
	@Override
	public String toString() {
		return integer.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Map<Integer, List<MyType>> inputMap = new HashMap<Integer, List<MyType>>();
		
		List<MyType> listOne = new ArrayList<MyType>();
		listOne.add(new MyType(1));
		listOne.add(new MyType(2));
		listOne.add(new MyType(3));
		listOne.add(new MyType(4));
		listOne.add(new MyType(5));
		inputMap.put(1, listOne);
		
		List<MyType> listTwo = new ArrayList<MyType>();
		listTwo.add(new MyType(3));
		listTwo.add(new MyType(5));
		listTwo.add(new MyType(2));
		listTwo.add(new MyType(6));
		inputMap.put(2, listTwo);
		
		List<MyType> listThree = new ArrayList<MyType>();
		listThree.add(new MyType(3));
		listThree.add(new MyType(4));
		listThree.add(new MyType(2));
		listThree.add(new MyType(7));
		inputMap.put(3, listThree);
		
		List<MyType> listFour = new ArrayList<MyType>();
		listFour.add(new MyType(1));
		listFour.add(new MyType(2));
		listFour.add(new MyType(3));
		listFour.add(new MyType(4));
		inputMap.put(4, listFour);
		
		List<MyType> listFive = new ArrayList<MyType>();
		listFive.add(new MyType(1));
		listFive.add(new MyType(3));
		listFive.add(new MyType(8));
		inputMap.put(5, listFive);
		
		List<MyType> listSix = new ArrayList<MyType>();
		inputMap.put(6, listSix);
		
		ListShortestUniquePrefixGenerator<MyType> generator = new ListShortestUniquePrefixGenerator<MyType>();
		Map<Integer, List<MyType>> outputMap = generator.generate(inputMap);
		for (Map.Entry<Integer, List<MyType>> entry : outputMap.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}
}
