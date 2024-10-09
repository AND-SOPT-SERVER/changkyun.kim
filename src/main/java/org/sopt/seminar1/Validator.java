package org.sopt.seminar1;

import org.sopt.seminar1.Main.UI.InvalidInputException;
import org.sopt.seminar1.Main.UI.TextLengthExceededException;

public class Validator {
    private static final String ID_REGEX = "^[1-9][0-9]*$";

    static void validateId(final String input) {
        if (!input.matches(ID_REGEX)) {
            throw new InvalidInputException();
        }
    }

    static void validateLength(final String input) {
        if (input.length() > 30) {
            System.out.print(input.length() + "자네요! ");

            throw new TextLengthExceededException();
        }
    }
}
