package org.sopt.seminar1;

import com.ibm.icu.text.BreakIterator;
import org.sopt.seminar1.Main.UI.DiaryLengthExceededException;
import org.sopt.seminar1.Main.UI.InvalidInputException;

public class Validator {
    private static final String ID_REGEX = "^[1-9][0-9]*$";

    private static final int MAX_DIARY_LENGTH = 30;

    static void validateId(final String input) {
        if (!input.matches(ID_REGEX)) {
            throw new InvalidInputException();
        }
    }

    static void validateLength(final String input) {
        int inputLength = countGraphemeClusters(input);

        if (inputLength > MAX_DIARY_LENGTH) {
            System.out.print(inputLength + "자네요! ");

            throw new DiaryLengthExceededException();
        }
    }

    private static int countGraphemeClusters(final String input) {
        BreakIterator iterator = BreakIterator.getCharacterInstance();
        iterator.setText(input);

        int count = 0;

        while (BreakIterator.DONE != iterator.next()) {
            count++;
        }

        return count;
    }
}
