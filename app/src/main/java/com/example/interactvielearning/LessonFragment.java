package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.protectsoft.webviewcode.Codeview;
import com.protectsoft.webviewcode.Settings;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class LessonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson, container, false);

        HtmlTextView htmlTextView = rootView.findViewById(R.id.html_text);

// loads html from string and displays cat_pic.png from the app's drawable folder
        htmlTextView.setHtml("<div><p>Let’s explore the basic anatomy of an HTML element. Line 9 of <strong>index.html</strong> contains a <em>heading</em> element: </p>\n" +
                        "<pre><span><div style=\"overflow: auto;\n" +
                        "    height: auto;\n" +
                        "    padding: 1rem;\n" +
                        "    background-color: #0a0e1d;    \n" +
                        "    color: #fff;\"><span style=\"color: #e85d7f;\">&lt;</span><span style=\"color: #e85d7f;\">h1</span><span style=\"color: #e85d7f;\">&gt;</span>You're Building a Website!<span style=\"color: #e85d7f;\">&lt;/</span><span style=\"color: #e85d7f;\">h1</span><span style=\"color: #e85d7f;\">&gt;</span></div></span></pre>\n" +
                        "<ol>\n" +
                        "<li>All HTML elements begin with an <em>opening tag</em>. In this case, the opening tag is <code>&lt;h1&gt;</code>.<br><br>  </li>\n" +
                        "<li>Most elements require a <em>closing tag</em>, denoted by a <code>/</code>. In this case, the closing tag is <code>&lt;/h1&gt;</code>.<br><br></li>\n" +
                        "<li>The website user only sees the content between the opening and closing tags. </li>\n" +
                        "</ol>\n" +
                        "</div>",
                new HtmlResImageGetter(htmlTextView));

        WebView webview = (WebView) rootView.findViewById(R.id.web_view);

        String code = "public static void main(String[] args) { \n" +
                "\n" +
                "//comments\n" +
                "   for(int i =0; i < 10; i++) {\n" +
                "       addnum();\n" +
                "   }\n" +
                "\n" +
                "}\n";

        Codeview.with(getActivity())
                .withCode(code)
                .setStyle(Settings.WithStyle.DARKULA)
                .setLang(Settings.Lang.JAVA)
                .into(webview);

        return rootView;
    }
}
