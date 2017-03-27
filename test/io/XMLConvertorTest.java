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
package io;

import exercisedrawer.*;
import java.awt.Color;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jiri Janak
 */
public class XMLConvertorTest {

    Exercise ex1;
    String filePath = "test.xml";
    File file = new File(filePath);
    
    public XMLConvertorTest() {
    }

    @Before
    public void setUp() {
        //first Exercise
        ex1 =  new Exercise();
        ex1.setName("Cvičení 1");
        ex1.setDescription("Popis cvičení");
        ex1.setBackgroundFileName("FULL_FIELD_2D.JPG");
        Item i1 = new Item(ItemType.PLAYER_CIRCLE, Color.BLACK, 10, 15, "", 0);
        ex1.getItems().add(i1);
        Item i2 = new Item(ItemType.BALL, Color.BLACK, 11, 25, "", 0);
        ex1.getItems().add(i2);
        Line l1 = new Line(LineType.RUN_WITH_BALL, Color.BLACK, 10, 15, 20, 25);
        ex1.getLines().add(l1);
    }

    @After
    public void tearDown() {
        file.setWritable(true);
        file.delete();
    }

    /**
     * Test of saveXMLFile method, of class XMLConvertor.
     */
    @Test
    public void saveXMLFile() {
        System.out.println("saveXMLFile");

        XMLConvertor export = new XMLConvertor();
        
        export.saveXMLFile(ex1,file);
        File file2 = new File(filePath);

        boolean expResult = true;
        boolean result = file2.exists();
        assertEquals(expResult, result);
    }

    /**
     * Test of loadXMLFile method, of class XMLConvertor.
     */
    @Test
    public void loadXMLFile() {
        System.out.println("loadXMLFile");

        //save of Exercise to file
        XMLConvertor convertor = new XMLConvertor();
        convertor.saveXMLFile(ex1,file);
        
        //load of Exercise from file
        Exercise ex2 = convertor.loadXMLFile(file);
        
        boolean expResult = true;
        boolean result = ex1.equals(ex2);
        assertEquals(expResult, result);       
    }

}