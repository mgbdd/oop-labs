import java.util.HashMap;
import java.util.Map;
import java.lang.String;

import exceptions.*;

public class NumberGuesser {
    public static boolean guessNumber(String userNum, int compNum) throws LettersInNumberException, InvalidLengthException, RepeatingNumbersException {

        for (int i = 0; i < userNum.length(); i++) {
            if (Character.isLetter(userNum.charAt(i))) throw new LettersInNumberException();
        }
        if (userNum.length() != 4) throw new InvalidLengthException();

        Map<Character, Integer> userMap = new HashMap<>();
        Map<Character, Integer> compMap = new HashMap<>();
        Map<Character, Integer> repeats = new HashMap<>();


        String compStr = Integer.toString(compNum);
        if (userNum.equalsIgnoreCase(compStr)) {
            System.out.println("congratulations, your guess is correct");
            return true;
        }

        for (int i = 0; i < 4; i++) {

            userMap.put(userNum.charAt(i), i + 1);
            compMap.put(compStr.charAt(i), i + 1);
            if(repeats.containsKey(userNum.charAt(i)))
            {
                int count  = repeats.get(userNum.charAt(i));
                repeats.put(userNum.charAt(i), ++count);
            }
            else repeats.put(userNum.charAt(i), 1);
        }


        int cows = 0, bulls = 0;
        for (char key : userMap.keySet()) {

            if(repeats.get(key) > 1) throw new RepeatingNumbersException();

            if (compMap.containsKey(key)) {
                if (userMap.get(key) == compMap.get(key)) bulls++;
                else cows++;
            }
        }
        System.out.println("number of cows: " + cows + ", number of bulls: " + bulls);
        return false;
    }


}


//    1234   1119