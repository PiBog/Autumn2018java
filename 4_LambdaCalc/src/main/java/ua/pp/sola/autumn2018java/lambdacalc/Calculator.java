package ua.pp.sola.autumn2018java.lambdacalc;

tttttimport java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Calculator {

    private static final String MATH_OPERATORS_PATERN = "[" + Pattern.quote("+-*/") + "]";
    private static final String NUNBERS_PATERN = "[0-9]+";

    public static void main(String[] args) throws IOException {

        printToConsole("Enter an expression: ", System.out::println);


        String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        String[] operations = splitLine(line, NUNBERS_PATERN, String::split);
        String[] numbers = splitLine(line, MATH_OPERATORS_PATERN, String::split);


        printToConsole(line,
                System.out::println);

        print(operations,
                Arrays::toString,
                System.out::println);

        print(numbers,
                Arrays::toString,
                System.out::println);


        printInt(convert(numbers),
                Arrays::toString,
                System.out::println);


        printResultToConsole(operations,
                numbers,
                Calculator::convert,
                Calculator::calculate,
                System.out::println);

        line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        /*calculate and print with one method*/
        printResultToConsole(
                splitLine(line, NUNBERS_PATERN, String::split),
                splitLine(line, MATH_OPERATORS_PATERN, String::split),
                Calculator::convert,
                Calculator::calculate,
                System.out::println);
    }

    private static void print(Object[] arr,
                              Function<Object[], String> function,
                              Consumer<String> consumer) {
        consumer.accept(
                function.apply(arr)
        );
    }
    private static void printInt(int[] arr,
                              Function<int[], String> function,
                              Consumer<String> consumer) {
        consumer.accept(
                function.apply(arr)
        );
    }

    private static void printResultToConsole(String[] operations,
                                             String[] numbers,
                                             Function<String[], int[]> function,
                                             BiFunction<String[], int[], Integer> biFunction,
                                             Consumer<Integer> consumer) {
        consumer.accept(
                biFunction.apply(
                        operations, function.apply(numbers)
                )
        );
    }

    private static void printToConsole(String source,
                                       Consumer<String> consumer) {
        consumer.accept(source);
    }

    private static String[] splitLine(String source,
                                      String pattern,
                                      BiFunction<String, String, String[]> biFunction) {
        return biFunction.apply(source, pattern);
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
