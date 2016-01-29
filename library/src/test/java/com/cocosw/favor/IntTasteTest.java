package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * @author Mohd Farid mohd.farid@devfactory.com
 */
public class IntTasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"10", "100"};

    private SharedPreferences.Editor editor;

    private Taste.IntTaste intTaste;

    @Before
    public void setUp() throws Exception {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getInt("key", 10)).thenReturn(100);
        editor = Mockito.mock(SharedPreferences.Editor.class);
        intTaste = new Taste.IntTaste(sharedPreferences, "key", defaultValues);
    }

    @Test
    public void testGet() throws Exception {
        //when
        Object getResult = intTaste.get();

        //then
        assertEquals(100, getResult);
    }

    @Test
    public void testEditor() throws Exception {
        //given
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);

        //when
        intTaste.editor(100);

        //then
        Mockito.verify(editor).putInt("key", 100);
    }
}