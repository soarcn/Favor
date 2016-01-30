package com.cocosw.favor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Class.class, Type.class})
public class TypesEqualsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testEquals_null_null() throws Exception {
        //when
        boolean result = Types.equals(null, null);

        //then
        assertTrue(result);
    }

    @Test
    public void testEquals_Class_Class() throws Exception {
        //when
        boolean result = Types.equals(Integer.class, Long.class);

        //then
        assertFalse(result);

        //when
        result = Types.equals(Integer.class, Integer.class);

        //then
        assertTrue(result);

        //when
        result = Types.equals(Object.class, Object.class);

        //then
        assertTrue(result);
    }

    @Test
    public void testEquals_ParameterizedType_and_GenericArrayType() throws Exception {
        //when
        boolean result = Types.equals(ParameterizedType.class, GenericArrayType.class);

        //then
        assertFalse(result);
    }

    @Test
    public void testEquals_ParameterizedType() throws Exception {
        //given
        ParameterizedType object1 = mock(ParameterizedType.class);
        when(object1.getOwnerType()).thenReturn(ParameterizedType.class);
        when(object1.getRawType()).thenReturn(ParameterizedType.class);
        Type[] types1 = {ParameterizedType.class, GenericArrayType.class, WildcardType.class};
        when(object1.getActualTypeArguments()).thenReturn(types1);

        ParameterizedType object2 = mock(ParameterizedType.class);
        when(object2.getOwnerType()).thenReturn(ParameterizedType.class);
        when(object2.getRawType()).thenReturn(ParameterizedType.class);
        Type[] types2 = {ParameterizedType.class, GenericArrayType.class, WildcardType.class};
        when(object2.getActualTypeArguments()).thenReturn(types2);

        //when
        boolean result = Types.equals(object1, object2);

        //then
        assertTrue("Must be true because both both objects have same values for ownerType, rawType and actualTypeArguments",
                result);

        //given
        when(object1.getOwnerType()).thenReturn(GenericArrayType.class);

        //when
        result = Types.equals(object1, object2);

        //then
        assertFalse("Must be false because value for ownerType is different for object1 from object2", result);
    }

    @Test
    public void testEquals_GenericArrayType() throws Exception {
        //given
        GenericArrayType object1 = mock(GenericArrayType.class);
        when(object1.getGenericComponentType()).thenReturn(GenericArrayType.class);

        GenericArrayType object2 = mock(GenericArrayType.class);
        when(object2.getGenericComponentType()).thenReturn(GenericArrayType.class);

        //when
        boolean result = Types.equals(object1, object2);

        //then
        assertTrue("Must be true because objects have same value for getGenericTypeComponent()", result);

        //given
        when(object1.getGenericComponentType()).thenReturn(ParameterizedType.class);

        //when
        result = Types.equals(object1, object2);

        //then
        assertFalse("Must be false because genericTypeComponent() for object1 is different from object2", result);
    }

    @Test
    public void testEquals_WildcardType() throws Exception {
        //given
        WildcardType object1 = mock(WildcardType.class);
        Type[] upperBounds1 = {Integer.class, Float.class};
        Type[] lowerBounds1 = {String.class, Float.class};
        when(object1.getUpperBounds()).thenReturn(upperBounds1);
        when(object1.getLowerBounds()).thenReturn(lowerBounds1);

        WildcardType object2 = mock(WildcardType.class);
        Type[] upperBounds2 = {Integer.class, Float.class};
        Type[] lowerBounds2 = {String.class, Float.class};
        when(object2.getUpperBounds()).thenReturn(upperBounds2);
        when(object2.getLowerBounds()).thenReturn(lowerBounds2);

        //when
        boolean result = Types.equals(object1, object2);

        //then
        assertTrue("Must be true because both upperBounds and lowerBounds are set with equal arrays respectively", result);

        //given
        Type[] upperBoundNew = {Integer.class, Integer.class};
        when(object1.getUpperBounds()).thenReturn(upperBoundNew);

        //when
        result = Types.equals(object1, object2);

        //then
        assertFalse("Must be false because upperBounds for object1 is different from object2", result);
    }


    @Test
    public void testEquals_TypeVariable() throws Exception {
        //given
        GenericDeclaration genericDeclaration = mock(GenericDeclaration.class);

        TypeVariable object1 = mock(TypeVariable.class);
        when(object1.getGenericDeclaration()).thenReturn(genericDeclaration);
        when(object1.getName()).thenReturn("SimpleName");

        TypeVariable object2 = mock(TypeVariable.class);
        when(object2.getGenericDeclaration()).thenReturn(genericDeclaration);
        when(object2.getName()).thenReturn("SimpleName");

        //when
        boolean result = Types.equals(object1, object2);

        //then
        assertTrue("Must be true because genericDeclaration and name have same values", result);

        //given

        when(object1.getName()).thenReturn("AnotherName");

        //when
        result = Types.equals(object1, object2);

        //then
        assertFalse("Must be false because name for object1 is different from object2", result);
    }

    @Test
    public void testEquals_UnSupportedType() throws Exception {
        //given
        class MyNewType implements Type {
        }

        MyNewType object1 = PowerMockito.mock(MyNewType.class);
        MyNewType object2 = PowerMockito.mock(MyNewType.class);

        when(object1.equals(object2)).thenReturn(true);
        when(object2.equals(object1)).thenReturn(true);

        assertTrue(object1.equals(object2));

        //when
        boolean result = Types.equals(object1, object2);

        //then
        assertFalse(result);
    }

    @Test
    public void testGetGenericSupertype() throws Exception {

    }

    @Test
    public void testTypeToString() throws Exception {

    }

    @Test
    public void testGetSupertype() throws Exception {

    }

    @Test
    public void testResolve() throws Exception {

    }
}