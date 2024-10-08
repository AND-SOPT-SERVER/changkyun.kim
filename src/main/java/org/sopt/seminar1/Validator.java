package org.sopt.seminar1;

import org.sopt.seminar1.Main.UI.InvalidInputException;

public class Validator {
    private static final String ID_REGEX = "^[1-9][0-9]*$";

    static void validate(final String input) {
        if (!input.matches(ID_REGEX)) {
            throw new InvalidInputException();
        }
    }
}
