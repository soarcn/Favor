package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Mohd Farid mohd.farid@devfactory.com
 */
public class StringTasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"default-value-first", "default-value-second"};

    private SharedPreferences.Editor editor;

    @Before
    public void setup(){
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getString("key", "default-value-first")).thenReturn("return-val-for-key-and-valueString");
        editor = Mockito.mock(SharedPreferences.Editor.class);
    }

    @Test
    public void testGet() throws Exception {
        //given
        Taste.StringTaste stringTaste = new Taste.StringTaste(sharedPreferences, "key", defaultValues);

        //when
        Object getResult = stringTaste.get();

        //then
        assertEquals("return-val-for-key-and-valueString", getResult);
    }

    @Test
    public void testEditor() throws Exception {
        //given
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Taste.StringTaste stringTaste = new Taste.StringTaste(sharedPreferences, "key", defaultValues);

        //when
        stringTaste.editor("some-value");

        //then
        Mockito.verify(editor).putString("key", "some-value");
    }
}