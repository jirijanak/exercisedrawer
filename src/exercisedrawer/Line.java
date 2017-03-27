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
public class Line {

    private LineType type;
    private Color color;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Line() {
    }

    public Line(LineType type, Color color, int startX, int startY, int endX, int endY) {
        this.type = type;
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result = type.name()
                        + "(" + color.getRGB() + "):  "
                        + "[" + getStartX() + "," + getStartY() + "]," 
                        + "[" + getEndX() + "," + getEndY() + "]";
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
        final Line other = (Line) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.color != other.color && (this.color == null || !this.color.equals(other.color))) {
            return false;
        }
        if (this.startX != other.startX) {
            return false;
        }
        if (this.startY != other.startY) {
            return false;
        }
        if (this.endX != other.endX) {
            return false;
        }
        if (this.endY != other.endY) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.startX;
        hash = 47 * hash + this.startY;
        hash = 47 * hash + this.endX;
        hash = 47 * hash + this.endY;
        return hash;
    }

    
}
