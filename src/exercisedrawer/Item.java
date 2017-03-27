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

/**
 *
 * @author Jiri Janak
 */
public class Item {

    private ItemType type;
    private Color color;
    private int posX;
    private int posY;
    private String text;
    private int rotation;

    public Item() {
    }

    public Item(ItemType type, Color color, int posX, int posY, String text, int rotation) {
        this.type = type;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.rotation = rotation;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        while (rotation >= 360) {
            rotation = rotation - 360;
        }
        this.rotation = rotation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result = type 
                        + "(" + color.getRGB() + "):  "
                        + "[" + getPosX() + "," + getPosY() + "] "
                        + text + "; "
                        + "rotate " + rotation + "°";
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.color != other.color && (this.color == null || !this.color.equals(other.color))) {
            return false;
        }
        if (this.posX != other.posX) {
            return false;
        }
        if (this.posY != other.posY) {
            return false;
        }
        if (!this.text.equals(other.text) && (this.text == null || !this.text.equals(other.text))) {
            return false;
        }
        if (this.rotation != other.rotation) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.posX;
        hash = 37 * hash + this.posY;
        hash = 37 * hash + this.rotation;
        return hash;
    }

    
    
}
