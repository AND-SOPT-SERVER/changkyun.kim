package org.sopt.seminar1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) {
        final UI ui;
        try {
            ui = new DiaryUI(new DiaryController());
            ui.runRepeatedly();
        } catch (Throwable t) {

        }
    }

    interface UI {
        void runRepeatedly() throws IOException;

        class UIException extends RuntimeException {
        }

        class InvalidInputException extends UIException {
        }

        class DiaryLengthExceededException extends UIException {
        }
    }

    static class DiaryUI implements UI {
        private final DiaryController server;
        private String selected;

        public DiaryUI(DiaryController server) throws IOException {
            this.server = server;
            server.boot();
            ConsoleIO.printLine(getStartMessage());
        }

        public void runRepeatedly() throws IOException {

            do {
                if (onMenu()) {
                    ConsoleIO.printLine("");
                    ConsoleIO.printLine(getMenu());
                    selected = ConsoleIO.readLine().trim().toUpperCase();
                }

                try {
                    run();
                } catch (InvalidInputException e) {
                    ConsoleIO.printLine("잘못된 값을 입력하였습니다.");
                } catch (DiaryLengthExceededException e) {
                    ConsoleIO.printLine("30자 이하로 다시 작성해 주세요~");
                }

                if (isFinished()) {
                    ConsoleIO.printLine(getFinishMessage());
                    break;
                }

                selected = null;
            } while (isRunning());
        }

        private void run() throws IOException {
            switch (server.getStatus()) {
                case READY, FINISHED, ERROR -> throw new UIException();

                case RUNNING -> {
                    switch (selected) {
                        case "GET" -> {
                            server.getList().forEach(diary -> {
                                try {
                                    ConsoleIO.printLine(diary.getId() + " : " + diary.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                        case "POST" -> {
                            ConsoleIO.printLine("한 줄 일기를 작성해주세요!");
                            final String input = ConsoleIO.readLine();
                            server.post(input);
                        }

                        case "DELETE" -> {
                            ConsoleIO.printLine("삭제할 id 를 입력하세요!");
                            final String input = ConsoleIO.readLine();
                            server.delete(input);
                        }
                        case "PATCH" -> {
                            ConsoleIO.printLine("수정할 id 를 입력하세요!");
                            final String inputId = ConsoleIO.readLine();

                            ConsoleIO.printLine("수정 body 를 입력하세요!");
                            final String inputBody = ConsoleIO.readLine();

                            server.patch(inputId, inputBody);
                        }
                        case "FINISH" -> {
                            server.finish();
                        }
                        default -> {
                            throw new InvalidInputException();
                        }
                    }
                }

            }
        }

        private boolean isRunning() {
            return server.getStatus() == DiaryController.Status.RUNNING;
        }

        private boolean isFinished() {
            return server.getStatus() == DiaryController.Status.FINISHED;
        }

        private boolean onMenu() {
            return selected == null;
        }

        private String getMenu() {
            return """
                    ============================
                    - GET : 일기 불러오기
                    - POST : 일기 작성하기
                    - DELETE : 일기 제거하기
                    - PATCH : 일기 수정하기
                    """;

        }

        private String getStartMessage() {
            return "시작합니다 :)";
        }

        private String getFinishMessage() {
            return "종료됩니다 :)";
        }
    }

    // not thread safe
    private static class ConsoleIO {
        private final static BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        private final static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        private final static StringBuilder sb = new StringBuilder();

        public static void printLine(final String toPrint) throws IOException {
            if (toPrint == null) {
                throw new IllegalArgumentException("console can not print null");
            }

            appendLine(toPrint);
            print();
            clearStringBuilder();
        }

        public static String readLine() throws IOException {
            return bufferedReader.readLine();
        }

        private static void appendLine(final String toPrint) {
            sb.append(toPrint);
            sb.append("\n");
        }

        private static void print() throws IOException {
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
        }

        private static void clearStringBuilder() {
            sb.setLength(0);
        }
    }
}
