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

import exercisedrawer.Exercise;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author Jiri Janak
 */
public class Print implements Printable {

    Exercise exercise;
    BufferedImage drawing;

    public Print(Exercise exercise, BufferedImage drawing) {
        this.exercise = exercise;
        this.drawing = drawing;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now we perform our rendering */
        Double pageWidth = pageFormat.getImageableWidth();
        int xToCenterImage = (pageWidth.intValue()-520) / 2;
        g2d.drawImage(drawing, xToCenterImage, 25, null);
        
        g2d.setFont(new Font("Arial",Font.BOLD,12));
        g2d.drawString("Exercise:", 40, 365);
        
        g2d.setFont(new Font("Arial",Font.PLAIN,12));
        g2d.drawString(exercise.getName(), 60+40, 365);

        g2d.setFont(new Font("Arial",Font.BOLD,12));
        g2d.drawString("Description of exercise:", 40, 395);
        
        Font font = new Font("Arial",Font.PLAIN,10);
        g2d.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics(font);

        int lineMaxWidth = pageWidth.intValue()-75;
        int lineHeight = metrics.getHeight();
        int distY = 413;
        
        String[] lines = exercise.getDescription().split("\n");
        for (int i=0; i<lines.length; i++) {
            String[] words = lines[i].split(" ");
            StringBuilder lineText = new StringBuilder();
            for (int j=0; j<words.length-1; j++) {
                String actualLine = lineText.toString();
                if (metrics.stringWidth(actualLine+" "+words[j]) <= lineMaxWidth) {
                    lineText.append(" "+words[j]);
                } else {
                    g2d.drawString(actualLine, 40, distY);
                    distY = distY+lineHeight+1;
                    lineText = new StringBuilder();
                    lineText.append(" "+words[j]);
                }
            }
            lineText.append(" "+words[words.length-1]);
            g2d.drawString(lineText.toString(), 40, distY);
            distY = distY+lineHeight+1;
        }
       
        
        
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}
