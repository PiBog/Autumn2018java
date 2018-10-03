package training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Calculator {
	public static void main(String[] args) throws IOException {
		System.out.println("Enter an expression: ");
		
		String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println(line);
		
		String[] operations = line.split("[0-9]+");
		System.out.println(Arrays.toString(operations));
		String[] numbers = line.split("[" + Pattern.quote("+-*/") + "]");
		System.out.println(Arrays.toString(numbers));
		
		int[] numbersConverted = convert(numbers);
		System.out.println(Arrays.toString(numbersConverted));
		
		int result = calculate(operations, numbersConverted);
		System.out.println(result);
	}

	private static int[] convert(String[] numbers) {
		int[] numbersConverted = new int[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			numbersConverted[i] = Integer.valueOf(numbers[i]);
		}
		return numbersConverted;
	}

	private static int calculate(String[] operations, int[] numbers) {
		int length = operations.length;
		int index = 1;
		while (index < length) {
			if ("*".equals(operations[index])) { // NEM!!! "*" == operations[i]
				numbers[index - 1] = numbers[index - 1] * numbers[index];
				for (int j = index; j < length - 1; j++) {
					numbers[j] = numbers[j + 1];
					operations[j] = operations[j + 1];
				}
				length--;
			} else if ("/".equals(operations[index])) { // NEM!!! "/" == operations[i]
				numbers[index - 1] = numbers[index - 1] / numbers[index];
				for (int j = index; j < length - 1; j++) {
					numbers[j] = numbers[j + 1];
					operations[j] = operations[j + 1];
				}
				length--;
			} else {
				index++;
			}
		}
		index = 1;
		while (index < length) {
			if ("+".equals(operations[index])) { // NEM!!! "+" == operations[i]
				numbers[index - 1] = numbers[index - 1] + numbers[index];
				for (int j = index; j < length - 1; j++) {
					numbers[j] = numbers[j + 1];
					operations[j] = operations[j + 1];
				}
				length--;
			} else if ("-".equals(operations[index])) { // NEM!!! "-" == operations[i]
				numbers[index - 1] = numbers[index - 1] - numbers[index];
				for (int j = index; j < length - 1; j++) {
					numbers[j] = numbers[j + 1];
					operations[j] = operations[j + 1];
				}
				length--;
			} else {
				index++;
			}
		}
		return numbers[0];
	}
}
