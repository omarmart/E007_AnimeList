package com.omar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SplitExceptQuotesTest {

    @Test
    public void shouldSplitWithoutQuotes() {
        String testString = "prueba1 prueba2 prueba3";
        String[] result = App.splitExceptQuotes(testString);
        assertTrue(result.length == 3);
        assertTrue(result[0].equals("prueba1"));
        assertTrue(result[1].equals("prueba2"));
        assertEquals("prueba3", result[2]);
    }

    @Test
    public void shouldSplitWithQuotes() {
        String testString = "prueba1 \"prueba2 prueba3\"";
        String[] result = App.splitExceptQuotes(testString);
        assertEquals(2, result.length);
        assertEquals("prueba1", result[0]);
        assertEquals("prueba2 prueba3", result[1]);
    }

    @Test
    public void shouldSplitWithOddQuotes() {
        String testString = "prueba1 \"prueba2 prueba3";
        String[] result = App.splitExceptQuotes(testString);
        assertEquals(2, result.length);
        assertEquals("prueba1", result[0]);
        assertEquals("prueba2 prueba3", result[1]);
    }

    @Test
    public void shouldReturnEmptyArray() {
        String testString = "";
        String[] result = App.splitExceptQuotes(testString);

        assertEquals(0, result.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        String testString = null;
        App.splitExceptQuotes(testString);
    }

    @Test
    public void shouldSplitWithDoubleSpace() {
        String testString = "prueba1  prueba2  prueba3";
        String[] result = App.splitExceptQuotes(testString);
        assertEquals("Array Length Error:\n", 3, result.length);
        assertEquals("prueba1", result[0]);
        assertEquals("prueba2", result[1]);
        assertEquals("prueba3", result[2]);
    }

}
