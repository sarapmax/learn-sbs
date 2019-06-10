package com.example.interactvielearning;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.protectsoft.webviewcode.Codeview;
import com.protectsoft.webviewcode.Settings;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LessonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson, container, false);

        HtmlTextView contentHtmlTextView = rootView.findViewById(R.id.html_text);
        HtmlTextView instructionHtmlTextView = rootView.findViewById(R.id.html_instruction);
        final HtmlTextView htmlOutput = rootView.findViewById(R.id.html_output);
        final EditText htmlCodeEditText = rootView.findViewById(R.id.html_code);

        String title = this.getArguments().getString("title");
        Boolean isDone = this.getArguments().getBoolean("isDone");
        String content = this.getArguments().getString("content");
        String instruction = this.getArguments().getString("instruction");
        String code = this.getArguments().getString("code");
        final String correctAnswer = this.getArguments().getString("correctAnswer");
        final String lessonName = this.getArguments().getString("lessonName");

        final Button runButton = rootView.findViewById(R.id.run_button);
        final Button submitButton = rootView.findViewById(R.id.submit_button);

        contentHtmlTextView.setHtml(content, new HtmlResImageGetter(contentHtmlTextView));
        instructionHtmlTextView.setHtml(instruction, new HtmlResImageGetter(instructionHtmlTextView));

//        final EditText editText = new EditText(this);
        htmlCodeEditText.addTextChangedListener(new TextWatcher() {

            ColorScheme keywords = new ColorScheme(
                    Pattern.compile(
                            "\\b(html|title|head|/|link|meta|body|h1|div)\\b"),
                    Color.CYAN
            );

            ColorScheme keywords2 = new ColorScheme(
                    Pattern.compile("\\b(charset|rel|href|class|id)\\b"),
                    Color.YELLOW
            );

            ColorScheme keywords3 = new ColorScheme(
                    Pattern.compile("\\b(utf-8|stylesheet|text/css|main.css|hero|footer)\\b"),
                    Color.GREEN
            );

            final ColorScheme[] schemes = { keywords, keywords2, keywords3  };

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {
                removeSpans(s, ForegroundColorSpan.class);
                for (ColorScheme scheme : schemes) {
                    for(Matcher m = scheme.pattern.matcher(s); m.find();) {
                        s.setSpan(new ForegroundColorSpan(scheme.color),
                                m.start(),
                                m.end(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

            void removeSpans(Editable e, Class<? extends CharacterStyle> type) {
                CharacterStyle[] spans = e.getSpans(0, e.length(), type);
                for (CharacterStyle span : spans) {
                    e.removeSpan(span);
                }
            }

            class ColorScheme {
                final Pattern pattern;
                final int color;

                ColorScheme(Pattern pattern, int color) {
                    this.pattern = pattern;
                    this.color = color;
                }
            }

        });

        String htmlCode = code.replace("\\n", "\n");
        htmlCode = htmlCode.replace("\" +                 \"", "");
        htmlCode = htmlCode.replace("\\t", "\t");
        htmlCode = htmlCode.replace("\\", "");

        htmlCodeEditText.setText(htmlCode);

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlOutput.setHtml(htmlCodeEditText.getText().toString(), new HtmlResImageGetter(htmlOutput));

                if (htmlCodeEditText.getText().toString().indexOf(correctAnswer) != -1) {
                    Toast.makeText(getContext(), "Well done!", Toast.LENGTH_LONG).show();

                    runButton.setVisibility(View.INVISIBLE);

                    submitButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "You're not getting the correct answer, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference currentLesson = database.getReference("courseLessons").child("webDesign").child(lessonName);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLesson.child("isDone").setValue(true, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        LearningFragment learningFragment= new LearningFragment();

                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, learningFragment, null)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }
        });

//        WebView webview = (WebView) rootView.findViewById(R.id.web_view);
//
//        String code = "public static void main(String[] args) { \n" +
//                "\n" +
//                "//comments\n" +
//                "   for(int i =0; i < 10; i++) {\n" +
//                "       addnum();\n" +
//                "   }\n" +
//                "\n" +
//                "}\n";
//
//        Codeview.with(getActivity())
//                .withCode(code)
//                .setStyle(Settings.WithStyle.DARKULA)
//                .setLang(Settings.Lang.JAVA)
//                .into(webview);

        return rootView;
    }
}
