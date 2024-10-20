import java.util.HashSet;
import java.lang.String;
import java.util.Set;

public class NumberGenerator {
    public static int generateNumber() {
        int num;
        boolean isValid;
        do {
            isValid = true;
            Set<Character> signs = new HashSet<>();
            num = (int) (Math.random() * (9876 - 1234 + 1)) + 1023;
            String str = Integer.toString(num);

            for (int i = 0; i < 4; i++) {
                if (!signs.add(str.charAt(i))) {
                    isValid = false;
                    break;
                }
            }

        } while (!isValid);

        return num;
    }

}