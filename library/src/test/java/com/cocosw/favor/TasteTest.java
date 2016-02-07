package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author Mohd Farid mohd.farid@devfactory.com @link <a href="https://github.com/mfarid">mfarid</a>
 */
public class TasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"default-value-first", "default-value-second"};

    private SharedPreferences.Editor editor;

    private DummyTaste dummyTaste;

    @Before
    public void setup() {
        sharedPreferences = mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getString("key", "default-value-first")).thenReturn("return-val-for-key-and-valueString");
        editor = mock(SharedPreferences.Editor.class);
        dummyTaste = new DummyTaste(sharedPreferences, "key", defaultValues);
    }

    @Test
    public void testSave() throws Exception {
        //when
        dummyTaste.save(editor, true);

        //then
        verify(editor).commit();
    }

    @Test
    public void testSet() throws Exception {
        //given
        dummyTaste.setEditor(editor);
        dummyTaste = spy(dummyTaste);

        //when
        dummyTaste.set("sample-value");

        //then
        verify(dummyTaste).editor("sample-value");
        verify(editor).apply();
    }

    @Test
    public void testCommit() throws Exception {
        //given
        dummyTaste.setEditor(editor);
        dummyTaste = spy(dummyTaste);

        //when
        dummyTaste.commit("sample-value");

        //then
        verify(dummyTaste).editor("sample-value");
        verify(dummyTaste).save(editor, true);
    }
}