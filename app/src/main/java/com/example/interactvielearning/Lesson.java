package com.example.interactvielearning;

public class Lesson extends LessonName {

    public String title;
    public Boolean isDone;
    public String content;
    public String instruction;
    public String code;
    public String correctAnswer;

    public Lesson() {

    }

    public Lesson(String title, Boolean isDone, String content, String instruction, String code, String correctAnswer) {
        this.title = title;
        this.isDone = isDone;
        this.content = content;
        this.instruction = instruction;
        this.code = code;
        this.correctAnswer = correctAnswer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public void setContent(String content) { this.content = content; }

    public void setInstruction(String instruction) { this.instruction = instruction; }

    public void setCode(String code) { this.code = code; }

    public void correctAnswer(String correctAnswer) { this. correctAnswer = correctAnswer; }

    public String getTitle() {
        return title;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public String getContent() { return content; }

    public String getInstruction() { return instruction; }

    public String getCode() { return code; }

    public String getCorrectAnswer() { return correctAnswer; }
}
