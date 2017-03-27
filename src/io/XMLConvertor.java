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
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jiri Janak
 */
public class XMLConvertor {

    private DocumentBuilderFactory factory;

    public XMLConvertor() {
        factory = DocumentBuilderFactory.newInstance();
    }

    public void saveXMLFile(Exercise exercise, File file) {
        //create XML
        Document doc = exportToXML(exercise);
        doc.normalizeDocument();

        //Serialisation through Tranform.
        try {
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(file);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.transform(domSource, streamResult);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Exercise loadXMLFile(File file) {
        try {
            //load File to Document
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            //parse XML
            return importFromXML(doc);
        } catch (SAXException ex) {
            Logger.getLogger(XMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Document exportToXML(Exercise exercise) {
        Document doc;
        Element root;
        Element element;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();

            //create root of document
            root = (Element) doc.createElement("exercise");
            doc.appendChild(root);

            //write exercise name
            element = (Element) doc.createElement("name");
            element.appendChild(doc.createTextNode(exercise.getName()));
            root.appendChild(element);

            //write exercise description
            element = (Element) doc.createElement("description");
            element.appendChild(doc.createTextNode(exercise.getDescription()));
            root.appendChild(element);

            //write exercise description
            element = (Element) doc.createElement("backgroundfilename");
            element.appendChild(doc.createTextNode(exercise.getBackgroundFileName()));
            root.appendChild(element);

            //write items
            element = (Element) doc.createElement("items");
            for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
                Item item = it.next();
                Element itemElement = (Element) doc.createElement("item");
                itemElement.setAttribute("type", item.getType().name());
                itemElement.setAttribute("x", item.getPosX() + "");
                itemElement.setAttribute("y", item.getPosY() + "");
                itemElement.setAttribute("color", item.getColor().getRGB() + "");
                itemElement.setAttribute("text", item.getText());
                itemElement.setAttribute("rotation", item.getRotation() + "");
                element.appendChild(itemElement);
            }
            root.appendChild(element);

            //write lines
            element = (Element) doc.createElement("lines");
            for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
                Line line = it.next();
                Element lineElement = (Element) doc.createElement("line");
                lineElement.setAttribute("type", line.getType().name());
                lineElement.setAttribute("color", line.getColor().getRGB() + "");
                lineElement.setAttribute("startx", line.getStartX() + "");
                lineElement.setAttribute("starty", line.getStartY() + "");
                lineElement.setAttribute("endx", line.getEndX() + "");
                lineElement.setAttribute("endy", line.getEndY() + "");
                element.appendChild(lineElement);
            }
            root.appendChild(element);

            return doc;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

        return null;
    }

    public Exercise importFromXML(Document doc) {
        Exercise exercise = new Exercise();
        NodeList nl;

        //name
        nl = doc.getElementsByTagName("name");
        exercise.setName(nl.item(0).getTextContent());

        //description
        nl = doc.getElementsByTagName("description");
        exercise.setDescription(nl.item(0).getTextContent());

        //backgroundfilename
        nl = doc.getElementsByTagName("backgroundfilename");
        exercise.setBackgroundFileName(nl.item(0).getTextContent());

        //items
        nl = doc.getElementsByTagName("item");
        for (int i = 0; i < nl.getLength(); i++) {
            
            NamedNodeMap itemAttrs = nl.item(i).getAttributes();
            Node colorNode = itemAttrs.getNamedItem("color");
            Node rotationNode = itemAttrs.getNamedItem("rotation");
            Node textNode = itemAttrs.getNamedItem("text");
            Node typeNode = itemAttrs.getNamedItem("type");
            Node xNode = itemAttrs.getNamedItem("x");
            Node yNode = itemAttrs.getNamedItem("y");
            
            Color color = new Color(Integer.parseInt(colorNode.getTextContent()));
            int rotation = Integer.parseInt(rotationNode.getTextContent());
            String text = textNode.getTextContent();
            ItemType type = ItemType.valueOf(typeNode.getTextContent());
            int x = Integer.parseInt(xNode.getTextContent());
            int y = Integer.parseInt(yNode.getTextContent());
            
            Item item = new Item(type, color, x, y, text, rotation);
            exercise.getItems().add(item);
        }
        
        //lines
        nl = doc.getElementsByTagName("line");
        for (int i = 0; i < nl.getLength(); i++) {
            
            NamedNodeMap itemAttrs = nl.item(i).getAttributes();
            Node colorNode = itemAttrs.getNamedItem("color");
            Node typeNode = itemAttrs.getNamedItem("type");
            Node startxNode = itemAttrs.getNamedItem("startx");
            Node startyNode = itemAttrs.getNamedItem("starty");
            Node endxNode = itemAttrs.getNamedItem("endx");
            Node endyNode = itemAttrs.getNamedItem("endy");
            
            Color color = new Color(Integer.parseInt(colorNode.getTextContent()));
            LineType type = LineType.valueOf(typeNode.getTextContent());
            int startx = Integer.parseInt(startxNode.getTextContent());
            int starty = Integer.parseInt(startyNode.getTextContent());
            int endx = Integer.parseInt(endxNode.getTextContent());
            int endy = Integer.parseInt(endyNode.getTextContent());
            
            Line line = new Line(type, color, startx, starty, endx, endy);
            exercise.getLines().add(line);
        }
        
        return exercise;
    }
}
