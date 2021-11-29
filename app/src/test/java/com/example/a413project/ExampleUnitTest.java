package com.example.a413project;

import com.example.a413project.Database.model.Classification;
import com.example.a413project.Database.model.Label;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Label[] l = new Label[]{new Label("123", 123)};
        Classification classification = new Classification("123", l, "123", "123");
        System.out.println(classification.timeConverter());
    }
}