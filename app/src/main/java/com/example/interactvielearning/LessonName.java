package com.example.interactvielearning;

import com.google.firebase.database.annotations.NotNull;

public class LessonName {
    public String lessonName;

    public <T extends LessonName> T withLessonName(@NotNull final String name) {
        this.lessonName = name;
        return (T) this;
    }
}
