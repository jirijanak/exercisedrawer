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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Jiri Janak
 */
public class Exercise {

    private String name = "";
    private String description = "";
    private Set<Line> lines = new HashSet<Line>();
    private Set<Item> items = new HashSet<Item>();
    private String backgroundFileName = "";

    public Exercise() {
    }

    public String getBackgroundFileName() {
        return backgroundFileName;
    }

    public void setBackgroundFileName(String backgroundFileName) {
        this.backgroundFileName = backgroundFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        //header
        result.append("EXERCISE: " + name + "\n");
        int length = result.length();
        for (int i = 0; i <= length; i++) {
            result.append("=");
        }
        result.append("\n");
        //description
        result.append(description + "\n\n");
        //background
        result.append("BACKGROUND FILE: " + backgroundFileName + "\n");
        //items
        for (Iterator<Item> it = items.iterator(); it.hasNext();) {
            Item item = it.next();
            result.append(item.toString() + "\n");
        }
        //lines
        for (Iterator<Line> it = lines.iterator(); it.hasNext();) {
            Line line = it.next();
            result.append(line.toString() + "\n");
        }

        return result.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exercise other = (Exercise) obj;
        if (!this.name.equals(other.name) && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (!this.description.equals(other.description) && (this.description == null || !this.description.equals(other.description))) {
            return false;
        }
        if (!this.backgroundFileName.equals(other.backgroundFileName) && (this.backgroundFileName == null || !this.backgroundFileName.equals(other.backgroundFileName))) {
            return false;
        }
        //items
        if (!other.getItems().containsAll(items)) {
            return false;
        }
        if (!items.containsAll(other.getItems())) {
            return false;
        }
        //lines
        if (!other.getLines().containsAll(lines)) {
            return false;
        }
        if (!lines.containsAll(other.getLines())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 11 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 11 * hash + this.items.size();
        hash = 11 * hash + this.lines.size();
        hash = 11 * hash + (this.backgroundFileName != null ? this.backgroundFileName.hashCode() : 0);
        return hash;
    }

    
    
}
