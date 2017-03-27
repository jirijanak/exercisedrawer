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
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ExerciseDrawerApp extends SingleFrameApplication {

    private static File file;
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        if (file == null) {
            show(new ExerciseDrawerView(this));
        } else {
            show(new ExerciseDrawerView(this,file));
        }
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ExerciseDrawerApp
     */
    public static ExerciseDrawerApp getApplication() {
        return Application.getInstance(ExerciseDrawerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            File tryFile = new File(args[0]);
            if (tryFile.exists() && tryFile.isFile()) {
                file=tryFile;
            }
        }
        
        launch(ExerciseDrawerApp.class, args);        
    }

    public BufferedImage getImage(File openFile) {
        ExerciseDrawerView edv = new ExerciseDrawerView(this,openFile);
        return edv.getImage();
    }
}
