/*
 * Copyright 2008 Jiri Janak
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package exercisedrawer;

import java.awt.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jiri Janak
 */
public class ExerciseTest {

    Exercise ex1;
    Exercise ex2;

    public ExerciseTest() {
    }

    @Before
    public void setUp() {
        //first Exercise
        ex1 = new Exercise();
        ex1.setName("Cvičení 1");
        ex1.setDescription("Popis cvičení");
        ex1.setBackgroundFileName("FULL_FIELD_2D.JPG");
        Item i1 = new Item(ItemType.PLAYER_CIRCLE, Color.BLACK, 10, 15, "", 0);
        ex1.getItems().add(i1);
        Item i2 = new Item(ItemType.BALL, Color.BLACK, 11, 25, "", 0);
        ex1.getItems().add(i2);
        Line l1 = new Line(LineType.RUN_WITH_BALL, Color.BLACK, 10, 15, 20, 25);
        ex1.getLines().add(l1);

        //second Exercise
        ex2 = new Exercise();
        ex2.setName("Cvičení 1");
        ex2.setDescription("Popis cvičení");
        ex2.setBackgroundFileName("FULL_FIELD_2D.JPG");
        Item ei1 = new Item(ItemType.PLAYER_CIRCLE, Color.BLACK, 10, 15, "", 0);
        ex2.getItems().add(ei1);
        Item ei2 = new Item(ItemType.BALL, Color.BLACK, 11, 25, "", 0);
        ex2.getItems().add(ei2);
        Line el1 = new Line(LineType.RUN_WITH_BALL, Color.BLACK, 10, 15, 20, 25);
        ex2.getLines().add(el1);
        
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void equals() {
        System.out.println("equals");

        boolean expResult = true;
        boolean result = ex1.equals(ex2);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void notEqualsChangeName() {
        System.out.println("notEqualsChangeName");
        ex2.setName("xxx");
        
        boolean expResult = false;
        boolean result = ex1.equals(ex2);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void notEqualsChangeItem() {
        System.out.println("notEqualsChangeName");
        Item i = new Item();
        
        ex2.getItems().add(i);
        boolean expResult = false;
        boolean result = ex1.equals(ex2);
        assertEquals(expResult, result);
        
        ex1.getItems().add(i);
        expResult = true;
        result = ex1.equals(ex2);
        assertEquals(expResult, result);
        
        ex1.getItems().remove(i);
        ex2.getItems().remove(i);
        expResult = true;
        result = ex1.equals(ex2);
        assertEquals(expResult, result);        
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void notEqualsChangeLine() {
        System.out.println("notEqualsChangeName");
        Line i = new Line();
        
        ex2.getLines().add(i);
        boolean expResult = false;
        boolean result = ex1.equals(ex2);
        assertEquals(expResult, result);
        
        ex1.getLines().add(i);
        expResult = true;
        result = ex1.equals(ex2);
        assertEquals(expResult, result);
        
        ex1.getLines().remove(i);
        ex2.getLines().remove(i);
        expResult = true;
        result = ex1.equals(ex2);
        assertEquals(expResult, result);        
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void emptyNotEqualsNull() {
        System.out.println("emptyNotEqualsNull");

        Object obj = null;
        Exercise instance = new Exercise();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Exercise.
     */
    @Test
    public void emptyEqualsEmpty() {
        System.out.println("emptyEqualsEmpty");

        Object obj = new Exercise();
        Exercise instance = new Exercise();
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

}