import exceptions.LettersInNumberException;
import exceptions.RepeatingNumbersException;
import exceptions.InvalidLengthException;

import java.util.Scanner;

public class Main {
    public static void main(String[] argc)
    {
        int x = NumberGenerator.generateNumber();
        //System.out.println(x);
        String userNum;
        boolean isCorrect;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("write your number: ");
            userNum = scan.nextLine();
            try{
                isCorrect = NumberGuesser.guessNumber(userNum, x);
            }catch(LettersInNumberException | InvalidLengthException | RepeatingNumbersException e) {
                String error = e.toString();
                System.out.println(error);
                isCorrect = false;
            }
        }while(!isCorrect);
    }
}

//случаи если одинаковые символы
