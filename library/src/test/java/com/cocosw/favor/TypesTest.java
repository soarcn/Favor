package com.cocosw.favor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mohd Farid mohd.farid@devfactory.com @link <a href="https://github.com/mfarid">mfarid</a>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Class.class})
public class TypesTest {

    @Test
    public void testGetRawType_ParameterizedType() throws Exception {
        //given
        ParameterizedType object = mock(ParameterizedType.class);
        when(object.getRawType()).thenReturn(ParameterizedType.class);

        //when
        Class<?> result = Types.getRawType(object);

        //then
        assertEquals("Raw type must be ParameterizedType", ParameterizedType.class, result);
    }

    @Test
    public void testGetRawType_GenericArrayType() throws Exception {
        //given
        GenericArrayType object = mock(GenericArrayType.class);
        when(object.getGenericComponentType()).thenReturn(GenericArrayType.class);

        //when
        Class<?> result = Types.getRawType(object);

        //then
        assertEquals("Raw type must be Array's class for GenericArrayType", Array.newInstance(GenericArrayType.class, 0).getClass(), result);
    }

    @Test
    public void testGetRawType_TypeVariable() throws Exception {
        //given
        TypeVariable object = mock(TypeVariable.class);

        //when
        Class<?> result = Types.getRawType(object);

        //then
        assertEquals("Raw type for the TypeVariable must be Object", Object.class, result);
    }

    @Test
    public void testGetRawType_WildcardType() throws Exception {
        //given
        WildcardType object = mock(WildcardType.class);
        Type[] upperBounds = {ParameterizedType.class, ParameterizedType.class};
        when(object.getUpperBounds()).thenReturn(upperBounds);

        //when
        Class<?> result = Types.getRawType(object);

        //then
        assertEquals("RawType for WildcardType must be the first element of the upperBounds",
                ParameterizedType.class, result);
    }

    @Test
    public void testGetRawType_Null() throws Exception {
        try {
            Types.getRawType(null);
            fail("An IllegalArgumentException must have occurred as we passed a null object to this method");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().startsWith("Expected a Class, ParameterizedType, or GenericArrayType"));
        }
    }

    @Test
    public void testEquals_null_null() throws Exception {
        //when
        boolean result = Types.equals(null, null);

        //then
        assertTrue("equals must return true for null and null values", result);
    }
}