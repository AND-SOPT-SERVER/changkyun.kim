package org.sopt.seminar1;

public class Diary {
    private Long id;
    private final String body;

    public Diary(Long id, String body) {
        this.id = id;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
