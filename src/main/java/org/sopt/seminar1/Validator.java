package org.sopt.seminar1;

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
        if (input.length() > MAX_DIARY_LENGTH) {
            System.out.print(input.length() + "자네요! ");

            throw new DiaryLengthExceededException();
        }
    }
}
