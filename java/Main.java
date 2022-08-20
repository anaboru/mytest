package test.calc.java;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int rom;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Привет! Я примитивный калькулятор. Я могу делить, умножать, складывать и вычитать");
        System.out.println("Мой приколдес в том, что я могу работать с римскими числами!!!!");
        System.out.println("Но, к сожалению, я могу работать лишь с числами в диапазоне от 1 до 10");
        System.out.println("Плюсом ко всему, если ты попробуешь ввести более двух операндов - я сломаюсь");
        System.out.println("Я также сломаюсь, если ты попытаешься использовать в выражении более одного оператора");
        System.out.print("Плиз би пэтиент, я ещё учусь!\nВводи---->>>    ");
        String input = sc.nextLine();
        String prov = input;
        input = input.replace(" ", "");
        try {
            input = calc(input);
            if (input.equals(prov)) {
                throw new ArithmeticException();
            }
            if (rom>0) {
                input = ArabianToRomanian(input);
                System.out.println("Я сделал! Смотри ----->     " + input);
            } else System.out.println("Я сделал! Смотри ----->     " + input);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Упс, некорректный ввод! Ты ввёл какое-то недопустимое выражение");
        } catch (IOException e) {
            System.out.println("Упс, некорректный ввод! Ты ввёл число, которое выходит за диапазон от 1 до 10");
        } catch (ArithmeticException e) {
            System.out.println("Упс, некорректный ввод! Ты ввёл что-то, с чем нельзя работать");
        } catch (Error e) {
            System.out.println("Упс, некорректный ввод! Ты ввёл одновременно и арабское, и римское число");
        }
    }

    public static String calc(String input) throws IOException {
        return input = threatment(input);
    }

    static String threatment(String input) throws IOException {String[] operators = {"+", "-", "/", "*"};
        int firstOperand = 0;
        int secondOperand = 0;
        String operator;
        for (String element : operators) {
            String[] splittedInput = input.split(String.valueOf("\\" + element), 2);
            operator = element;
            if (splittedInput.length != 2) {
                continue;
            }
            if ((isArabianNumber(splittedInput[0]) & isRomanianNumber(splittedInput[1])) | (isRomanianNumber(splittedInput[0]) & isArabianNumber(splittedInput[1]))) {
                throw new Error();
            }
            if (isRomanianNumber(splittedInput[0]) & isRomanianNumber(splittedInput[1])) {
                splittedInput = RomanianToArabian(splittedInput);
                rom++;
            }
            if (isArabianNumber(splittedInput[0]) & isArabianNumber(splittedInput[1])) {
                firstOperand = Integer.parseInt(splittedInput[0]);
                secondOperand = Integer.parseInt(splittedInput[1]);
            } else {
                throw new NumberFormatException();
            }
            if (!((0 < firstOperand & firstOperand < 11) & (0 < secondOperand & secondOperand < 11))) {
                throw new IOException();
            }
            if ((0 < firstOperand & firstOperand < 11) && (0 < secondOperand & secondOperand < 11)) {
                switch (operator) {
                    case "+" -> input = "" + (firstOperand + secondOperand);
                    case "-" -> input = "" + (firstOperand - secondOperand);
                    case "/" -> input = "" + (firstOperand / secondOperand);
                    case "*" -> input = "" + (firstOperand * secondOperand);
                }
                ;
                if (rom > 0 & Integer.parseInt(input) <= 0) {
                    throw new NumberFormatException();
                }
                break;
            }
        }
        return input + "";
    }

    static String[] RomanianToArabian(String[] RomArray) {
        Romanian first = Romanian.valueOf(RomArray[0]);
        Romanian second = Romanian.valueOf(RomArray[1]);
        RomArray[0] = first.getValue() + "";
        RomArray[1] = second.getValue() + "";
        return RomArray;
    }

    static boolean isRomanianNumber(String number) {
        try {
            Romanian num = Romanian.valueOf(number);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    static boolean isArabianNumber(String number) {
        try {
            int num = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    static String ArabianToRomanian(String romabian){
        int num = Integer.parseInt(romabian);
        Romanian[] numbers = Romanian.values();
        String roman = "";
        for (int i = (numbers.length - 1) ; i >= 0; i--) {
            while(num >= numbers[i].getValue()){
                roman += numbers[i];
                num -= numbers[i].getValue();
                i++;
            }
        }
        return roman;
    }
}
enum Romanian {
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XL(40), L(50), LC(90), C(100), D(500);
    private final int value;


    Romanian(int value) {

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}