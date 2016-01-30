package com.cocosw.favor;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * @author Mohd Farid mohd.farid@devfactory.com
 */
public class LongTasteTest {

    private SharedPreferences sharedPreferences;

    private final String[] defaultValues = {"10", "100"};

    private SharedPreferences.Editor editor;

    private Taste.LongTaste LongTaste;

    @Before
    public void setUp() throws Exception {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        Mockito.when(sharedPreferences.getLong("key", 10l)).thenReturn(100l);
        editor = Mockito.mock(SharedPreferences.Editor.class);
        LongTaste = new Taste.LongTaste(sharedPreferences, "key", defaultValues);
    }

    @Test
    public void testGet() throws Exception {
        //when
        Object getResult = LongTaste.get();

        //then
        assertEquals(100l, getResult);
    }

    @Test
    public void testEditor() throws Exception {
        //given
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);

        //when
        LongTaste.editor(100l);

        //then
        Mockito.verify(editor).putLong("key", 100l);
    }
}