package com.invex.example.jlmb.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.invex.example.jlmb.data.entities.Employee;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {
	
	public static int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt( max ) + min;
	}
	
	public static List<Employee> employees() {
		List<Employee> result = new ArrayList<>();
		for( int i = 1 ; i <= getRandom(1,10) ; i++ ) {
			result.add(TestData.employee());
		}
		return result;
	}
	
	public static Employee employee() {
		int random = getRandom(1,1000);
		String text = String.format("data%d", random);
		return new Employee(random, text, text, text, text, (short) getRandom(18,100), "M", LocalDate.now().minusDays(1), text);
	}
	
}
