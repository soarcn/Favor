package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Mohd Farid mohd.farid@devfactory.com @link <a href="https://github.com/mfarid">mfarid</a>
 */
public class StringTasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"default-value-first", "default-value-second"};

    private SharedPreferences.Editor editor;

    private Taste.StringTaste stringTaste;

    @Before
    public void setup() {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getString("key", "default-value-first")).thenReturn("return-val-for-key-and-valueString");
        editor = Mockito.mock(SharedPreferences.Editor.class);
        stringTaste = new Taste.StringTaste(sharedPreferences, "key", defaultValues);
    }

    @Test
    public void testGet() throws Exception {
        //when
        Object getResult = stringTaste.get();

        //then
        assertEquals("return-val-for-key-and-valueString", getResult);
    }

    @Test
    public void testEditor() throws Exception {
        //given
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);

        //when
        stringTaste.editor("some-value");

        //then
        Mockito.verify(editor).putString("key", "some-value");
    }
}