package com.example.interactvielearning;

import com.google.firebase.database.annotations.NotNull;

public class QuestionName {
    public String questionName;

    public <T extends QuestionName> T withQuestionName(@NotNull final String name) {
        this.questionName = name;

        return (T) this;
    }
}
