package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Mohd Farid mohd.farid@devfactory.com @link <a href="https://github.com/mfarid">mfarid</a>
 */
public class FloatTasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"10", "100"};

    private SharedPreferences.Editor editor;

    private Taste.FloatTaste FloatTaste;

    @Before
    public void setUp() throws Exception {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getFloat("key", 10f)).thenReturn(100f);
        editor = Mockito.mock(SharedPreferences.Editor.class);
        FloatTaste = new Taste.FloatTaste(sharedPreferences, "key", defaultValues);
    }

    @Test
    public void testGet() throws Exception {
        //when
        Object getResult = FloatTaste.get();

        //then
        assertEquals("get() must return 100f as configured in sharedPreferences", 100f, getResult);
    }

    @Test
    public void testEditor() throws Exception {
        //given
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);

        //when
        FloatTaste.editor(100f);

        //then
        Mockito.verify(editor).putFloat("key", 100f);
    }
}