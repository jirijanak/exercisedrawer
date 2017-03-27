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

import io.XMLConvertor;
import io.Print;
import exercisedrawer.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The application's main frame.
 */
public class ExerciseDrawerView extends FrameView {

    public ExerciseDrawerView(SingleFrameApplication app, File openFile) {
        this(app);
        this.openExerciseFile(openFile);
    }

    public ExerciseDrawerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        this.enableButtons(false);
        frameTitleStart = this.getFrame().getTitle();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ExerciseDrawerApp.getApplication().getMainFrame();
            aboutBox = new ExerciseDrawerAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ExerciseDrawerApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();
        fieldLabel = new javax.swing.JLabel();
        fieldComboBox = new javax.swing.JComboBox();
        playersPanel = new javax.swing.JPanel();
        circlePlayerButton = new javax.swing.JButton();
        trianglePlayerButton = new javax.swing.JButton();
        xPlayerButton = new javax.swing.JButton();
        squarePlayerButton = new javax.swing.JButton();
        itemsPanel = new javax.swing.JPanel();
        ballItemButton = new javax.swing.JButton();
        ballsItemButton = new javax.swing.JButton();
        clubItemButton = new javax.swing.JButton();
        textItemButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        linesPanel = new javax.swing.JPanel();
        passLineButton = new javax.swing.JButton();
        passLineArrowButton = new javax.swing.JButton();
        runWithBallLineButton = new javax.swing.JButton();
        runWithBallLineArrowButton = new javax.swing.JButton();
        runWithoutBallLineButton = new javax.swing.JButton();
        runWithoutBallLineArrowButton = new javax.swing.JButton();
        shootLineButton = new javax.swing.JButton();
        shootLineArrowButton = new javax.swing.JButton();
        backRunLineButton = new javax.swing.JButton();
        backRunLineArrowButton = new javax.swing.JButton();
        colorButton = new javax.swing.JButton();
        sponsorLabel = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        picturePanel = new javax.swing.JPanel();
        drawingLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exportToPngMenuItem = new javax.swing.JMenuItem();
        printMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        copyHorizontalMenuItem = new javax.swing.JMenuItem();
        copyVerticalMenuItem = new javax.swing.JMenuItem();
        copyMirrorMenuItem = new javax.swing.JMenuItem();
        portalMenu = new javax.swing.JMenu();
        importFromPortalMenuItem = new javax.swing.JMenuItem();
        exportToPortalMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        visitWebsiteMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(null);
        mainPanel.setName("mainPanel"); // NOI18N

        buttonsPanel.setAlignmentX(0.0F);
        buttonsPanel.setAlignmentY(0.0F);
        buttonsPanel.setName("buttonsPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.ExerciseDrawerApp.class).getContext().getResourceMap(ExerciseDrawerView.class);
        fieldLabel.setText(resourceMap.getString("fieldLabel.text")); // NOI18N
        fieldLabel.setName("fieldLabel"); // NOI18N

        fieldComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Field 1", "Field 2", "Field 3", "Field 4", "Field 5", "Field 6", "Empty" }));
        fieldComboBox.setName("fieldComboBox"); // NOI18N
        fieldComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldComboBoxActionPerformed(evt);
            }
        });

        playersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("playersPanel.border.title"))); // NOI18N
        playersPanel.setName("playersPanel"); // NOI18N

        circlePlayerButton.setIcon(resourceMap.getIcon("circlePlayerButton.icon")); // NOI18N
        circlePlayerButton.setText(resourceMap.getString("circlePlayerButton.text")); // NOI18N
        circlePlayerButton.setToolTipText(resourceMap.getString("circlePlayerButton.toolTipText")); // NOI18N
        circlePlayerButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        circlePlayerButton.setName("circlePlayerButton"); // NOI18N
        circlePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circlePlayerButtonActionPerformed(evt);
            }
        });

        trianglePlayerButton.setIcon(resourceMap.getIcon("trianglePlayerButton.icon")); // NOI18N
        trianglePlayerButton.setText(resourceMap.getString("trianglePlayerButton.text")); // NOI18N
        trianglePlayerButton.setToolTipText(resourceMap.getString("trianglePlayerButton.toolTipText")); // NOI18N
        trianglePlayerButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        trianglePlayerButton.setName("trianglePlayerButton"); // NOI18N
        trianglePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trianglePlayerButtonActionPerformed(evt);
            }
        });

        xPlayerButton.setIcon(resourceMap.getIcon("xPlayerButton.icon")); // NOI18N
        xPlayerButton.setText(resourceMap.getString("xPlayerButton.text")); // NOI18N
        xPlayerButton.setToolTipText(resourceMap.getString("xPlayerButton.toolTipText")); // NOI18N
        xPlayerButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        xPlayerButton.setName("xPlayerButton"); // NOI18N
        xPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xPlayerButtonActionPerformed(evt);
            }
        });

        squarePlayerButton.setIcon(resourceMap.getIcon("squarePlayerButton.icon")); // NOI18N
        squarePlayerButton.setText(resourceMap.getString("squarePlayerButton.text")); // NOI18N
        squarePlayerButton.setToolTipText(resourceMap.getString("squarePlayerButton.toolTipText")); // NOI18N
        squarePlayerButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        squarePlayerButton.setName("squarePlayerButton"); // NOI18N
        squarePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                squarePlayerButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout playersPanelLayout = new org.jdesktop.layout.GroupLayout(playersPanel);
        playersPanel.setLayout(playersPanelLayout);
        playersPanelLayout.setHorizontalGroup(
            playersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(playersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(playersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(playersPanelLayout.createSequentialGroup()
                        .add(circlePlayerButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(trianglePlayerButton))
                    .add(playersPanelLayout.createSequentialGroup()
                        .add(xPlayerButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(squarePlayerButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        playersPanelLayout.setVerticalGroup(
            playersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(playersPanelLayout.createSequentialGroup()
                .add(playersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(circlePlayerButton)
                    .add(trianglePlayerButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(playersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(xPlayerButton)
                    .add(squarePlayerButton)))
        );

        itemsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("itemsPanel.border.title"))); // NOI18N
        itemsPanel.setName("itemsPanel"); // NOI18N

        ballItemButton.setIcon(resourceMap.getIcon("ballItemButton.icon")); // NOI18N
        ballItemButton.setText(resourceMap.getString("ballItemButton.text")); // NOI18N
        ballItemButton.setToolTipText(resourceMap.getString("ballItemButton.toolTipText")); // NOI18N
        ballItemButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        ballItemButton.setName("ballItemButton"); // NOI18N
        ballItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ballItemButtonActionPerformed(evt);
            }
        });

        ballsItemButton.setIcon(resourceMap.getIcon("ballsItemButton.icon")); // NOI18N
        ballsItemButton.setText(resourceMap.getString("ballsItemButton.text")); // NOI18N
        ballsItemButton.setToolTipText(resourceMap.getString("ballsItemButton.toolTipText")); // NOI18N
        ballsItemButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        ballsItemButton.setName("ballsItemButton"); // NOI18N
        ballsItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ballsItemButtonActionPerformed(evt);
            }
        });

        clubItemButton.setIcon(resourceMap.getIcon("clubItemButton.icon")); // NOI18N
        clubItemButton.setText(resourceMap.getString("clubItemButton.text")); // NOI18N
        clubItemButton.setToolTipText(resourceMap.getString("clubItemButton.toolTipText")); // NOI18N
        clubItemButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        clubItemButton.setName("clubItemButton"); // NOI18N
        clubItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clubItemButtonActionPerformed(evt);
            }
        });

        textItemButton.setIcon(resourceMap.getIcon("textItemButton.icon")); // NOI18N
        textItemButton.setText(resourceMap.getString("textItemButton.text")); // NOI18N
        textItemButton.setToolTipText(resourceMap.getString("textItemButton.toolTipText")); // NOI18N
        textItemButton.setMargin(new java.awt.Insets(2, 19, 2, 19));
        textItemButton.setName("textItemButton"); // NOI18N
        textItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textItemButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout itemsPanelLayout = new org.jdesktop.layout.GroupLayout(itemsPanel);
        itemsPanel.setLayout(itemsPanelLayout);
        itemsPanelLayout.setHorizontalGroup(
            itemsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(itemsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(itemsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(itemsPanelLayout.createSequentialGroup()
                        .add(ballItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ballsItemButton))
                    .add(itemsPanelLayout.createSequentialGroup()
                        .add(clubItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(textItemButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        itemsPanelLayout.setVerticalGroup(
            itemsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(itemsPanelLayout.createSequentialGroup()
                .add(itemsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(ballItemButton)
                    .add(ballsItemButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(itemsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(clubItemButton)
                    .add(textItemButton))
                .addContainerGap())
        );

        deleteButton.setText(resourceMap.getString("deleteButton.text")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        linesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("linesPanel.border.title"))); // NOI18N
        linesPanel.setName("linesPanel"); // NOI18N

        passLineButton.setIcon(resourceMap.getIcon("passLineButton.icon")); // NOI18N
        passLineButton.setText(resourceMap.getString("passLineButton.text")); // NOI18N
        passLineButton.setToolTipText(resourceMap.getString("passLineButton.toolTipText")); // NOI18N
        passLineButton.setName("passLineButton"); // NOI18N
        passLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passLineButtonActionPerformed(evt);
            }
        });

        passLineArrowButton.setIcon(resourceMap.getIcon("passLineArrowButton.icon")); // NOI18N
        passLineArrowButton.setText(resourceMap.getString("passLineArrowButton.text")); // NOI18N
        passLineArrowButton.setToolTipText(resourceMap.getString("passLineArrowButton.toolTipText")); // NOI18N
        passLineArrowButton.setName("passLineArrowButton"); // NOI18N
        passLineArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passLineArrowButtonActionPerformed(evt);
            }
        });

        runWithBallLineButton.setIcon(resourceMap.getIcon("runWithBallLineButton.icon")); // NOI18N
        runWithBallLineButton.setText(resourceMap.getString("runWithBallLineButton.text")); // NOI18N
        runWithBallLineButton.setToolTipText(resourceMap.getString("runWithBallLineButton.toolTipText")); // NOI18N
        runWithBallLineButton.setName("runWithBallLineButton"); // NOI18N
        runWithBallLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runWithBallLineButtonActionPerformed(evt);
            }
        });

        runWithBallLineArrowButton.setIcon(resourceMap.getIcon("runWithBallLineArrowButton.icon")); // NOI18N
        runWithBallLineArrowButton.setText(resourceMap.getString("runWithBallLineArrowButton.text")); // NOI18N
        runWithBallLineArrowButton.setToolTipText(resourceMap.getString("runWithBallLineArrowButton.toolTipText")); // NOI18N
        runWithBallLineArrowButton.setName("runWithBallLineArrowButton"); // NOI18N
        runWithBallLineArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runWithBallLineArrowButtonActionPerformed(evt);
            }
        });

        runWithoutBallLineButton.setIcon(resourceMap.getIcon("runWithoutBallLineButton.icon")); // NOI18N
        runWithoutBallLineButton.setText(resourceMap.getString("runWithoutBallLineButton.text")); // NOI18N
        runWithoutBallLineButton.setToolTipText(resourceMap.getString("runWithoutBallLineButton.toolTipText")); // NOI18N
        runWithoutBallLineButton.setName("runWithoutBallLineButton"); // NOI18N
        runWithoutBallLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runWithoutBallLineButtonActionPerformed(evt);
            }
        });

        runWithoutBallLineArrowButton.setIcon(resourceMap.getIcon("runWithoutBallLineArrowButton.icon")); // NOI18N
        runWithoutBallLineArrowButton.setText(resourceMap.getString("runWithoutBallLineArrowButton.text")); // NOI18N
        runWithoutBallLineArrowButton.setToolTipText(resourceMap.getString("runWithoutBallLineArrowButton.toolTipText")); // NOI18N
        runWithoutBallLineArrowButton.setName("runWithoutBallLineArrowButton"); // NOI18N
        runWithoutBallLineArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runWithoutBallLineArrowButtonActionPerformed(evt);
            }
        });

        shootLineButton.setIcon(resourceMap.getIcon("shootLineButton.icon")); // NOI18N
        shootLineButton.setText(resourceMap.getString("shootLineButton.text")); // NOI18N
        shootLineButton.setToolTipText(resourceMap.getString("shootLineButton.toolTipText")); // NOI18N
        shootLineButton.setName("shootLineButton"); // NOI18N
        shootLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shootLineButtonActionPerformed(evt);
            }
        });

        shootLineArrowButton.setIcon(resourceMap.getIcon("shootLineArrowButton.icon")); // NOI18N
        shootLineArrowButton.setText(resourceMap.getString("shootLineArrowButton.text")); // NOI18N
        shootLineArrowButton.setToolTipText(resourceMap.getString("shootLineArrowButton.toolTipText")); // NOI18N
        shootLineArrowButton.setName("shootLineArrowButton"); // NOI18N
        shootLineArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shootLineArrowButtonActionPerformed(evt);
            }
        });

        backRunLineButton.setIcon(resourceMap.getIcon("backRunLineButton.icon")); // NOI18N
        backRunLineButton.setText(resourceMap.getString("backRunLineButton.text")); // NOI18N
        backRunLineButton.setToolTipText(resourceMap.getString("backRunLineButton.toolTipText")); // NOI18N
        backRunLineButton.setName("backRunLineButton"); // NOI18N
        backRunLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRunLineButtonActionPerformed(evt);
            }
        });

        backRunLineArrowButton.setIcon(resourceMap.getIcon("backRunLineArrowButton.icon")); // NOI18N
        backRunLineArrowButton.setText(resourceMap.getString("backRunLineArrowButton.text")); // NOI18N
        backRunLineArrowButton.setToolTipText(resourceMap.getString("backRunLineArrowButton.toolTipText")); // NOI18N
        backRunLineArrowButton.setName("backRunLineArrowButton"); // NOI18N
        backRunLineArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRunLineArrowButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout linesPanelLayout = new org.jdesktop.layout.GroupLayout(linesPanel);
        linesPanel.setLayout(linesPanelLayout);
        linesPanelLayout.setHorizontalGroup(
            linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(linesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(linesPanelLayout.createSequentialGroup()
                        .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(runWithoutBallLineButton)
                            .add(backRunLineButton)
                            .add(shootLineButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(shootLineArrowButton)
                            .add(backRunLineArrowButton)
                            .add(runWithoutBallLineArrowButton)))
                    .add(linesPanelLayout.createSequentialGroup()
                        .add(passLineButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(passLineArrowButton))
                    .add(linesPanelLayout.createSequentialGroup()
                        .add(runWithBallLineButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(runWithBallLineArrowButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        linesPanelLayout.setVerticalGroup(
            linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(linesPanelLayout.createSequentialGroup()
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(passLineButton)
                    .add(passLineArrowButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(runWithBallLineButton)
                    .add(runWithBallLineArrowButton))
                .add(5, 5, 5)
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(runWithoutBallLineArrowButton)
                    .add(runWithoutBallLineButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(backRunLineButton)
                    .add(backRunLineArrowButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(linesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(shootLineArrowButton)
                    .add(shootLineButton)))
        );

        colorButton.setIcon(resourceMap.getIcon("colorButton.icon")); // NOI18N
        colorButton.setText(resourceMap.getString("colorButton.text")); // NOI18N
        colorButton.setFocusable(false);
        colorButton.setName("colorButton"); // NOI18N
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        sponsorLabel.setIcon(resourceMap.getIcon("sponsorLabel.icon")); // NOI18N
        sponsorLabel.setText(resourceMap.getString("sponsorLabel.text")); // NOI18N
        sponsorLabel.setName("sponsorLabel"); // NOI18N
        sponsorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sponsorLabelMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout buttonsPanelLayout = new org.jdesktop.layout.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonsPanelLayout.createSequentialGroup()
                .add(buttonsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(buttonsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, colorButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, buttonsPanelLayout.createSequentialGroup()
                            .add(fieldLabel)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(fieldComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, deleteButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, itemsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, playersPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, linesPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(buttonsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(sponsorLabel)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(buttonsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fieldLabel)
                    .add(fieldComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(linesPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(playersPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(itemsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(colorButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(sponsorLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        contentPanel.setAlignmentX(0.0F);
        contentPanel.setAlignmentY(0.0F);
        contentPanel.setName("contentPanel"); // NOI18N

        descriptionScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setFont(resourceMap.getFont("descriptionTextArea.font")); // NOI18N
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setName("descriptionTextArea"); // NOI18N
        descriptionTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                descriptionTextAreaFocusLost(evt);
            }
        });
        descriptionScrollPane.setViewportView(descriptionTextArea);

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        nameTextField.setFont(resourceMap.getFont("nameTextField.font")); // NOI18N
        nameTextField.setText(resourceMap.getString("nameTextField.text")); // NOI18N
        nameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        picturePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        picturePanel.setName("picturePanel"); // NOI18N

        drawingLabel.setBackground(resourceMap.getColor("drawingLabel.background")); // NOI18N
        drawingLabel.setText(resourceMap.getString("drawingLabel.text")); // NOI18N
        drawingLabel.setMaximumSize(new java.awt.Dimension(520, 323));
        drawingLabel.setMinimumSize(new java.awt.Dimension(520, 323));
        drawingLabel.setName("drawingLabel"); // NOI18N
        drawingLabel.setPreferredSize(new java.awt.Dimension(520, 323));
        drawingLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawingLabelMouseClicked(evt);
            }
        });
        drawingLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                drawingLabelMouseMoved(evt);
            }
        });

        org.jdesktop.layout.GroupLayout picturePanelLayout = new org.jdesktop.layout.GroupLayout(picturePanel);
        picturePanel.setLayout(picturePanelLayout);
        picturePanelLayout.setHorizontalGroup(
            picturePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(drawingLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        picturePanelLayout.setVerticalGroup(
            picturePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(drawingLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        descriptionLabel.setText(resourceMap.getString("descriptionLabel.text")); // NOI18N
        descriptionLabel.setName("descriptionLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout contentPanelLayout = new org.jdesktop.layout.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(contentPanelLayout.createSequentialGroup()
                        .add(nameLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(nameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 489, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(descriptionLabel)
                    .add(picturePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(descriptionScrollPane))
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nameLabel)
                    .add(nameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(picturePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(descriptionLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(descriptionScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(contentPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 540, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, buttonsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setEnabled(false);
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText(resourceMap.getString("saveAsMenuItem.text")); // NOI18N
        saveAsMenuItem.setEnabled(false);
        saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        exportToPngMenuItem.setText(resourceMap.getString("exportToPngMenuItem.text")); // NOI18N
        exportToPngMenuItem.setEnabled(false);
        exportToPngMenuItem.setName("exportToPngMenuItem"); // NOI18N
        exportToPngMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportToPngMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exportToPngMenuItem);

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText(resourceMap.getString("printMenuItem.text")); // NOI18N
        printMenuItem.setEnabled(false);
        printMenuItem.setName("printMenuItem"); // NOI18N
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gui.ExerciseDrawerApp.class).getContext().getActionMap(ExerciseDrawerView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        undoMenuItem.setText(resourceMap.getString("undoMenuItem.text")); // NOI18N
        undoMenuItem.setEnabled(false);
        undoMenuItem.setName("undoMenuItem"); // NOI18N
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(undoMenuItem);

        jSeparator5.setName("jSeparator5"); // NOI18N
        editMenu.add(jSeparator5);

        copyHorizontalMenuItem.setText(resourceMap.getString("copyHorizontalMenuItem.text")); // NOI18N
        copyHorizontalMenuItem.setEnabled(false);
        copyHorizontalMenuItem.setName("copyHorizontalMenuItem"); // NOI18N
        copyHorizontalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyHorizontalMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyHorizontalMenuItem);

        copyVerticalMenuItem.setText(resourceMap.getString("copyVerticalMenuItem.text")); // NOI18N
        copyVerticalMenuItem.setEnabled(false);
        copyVerticalMenuItem.setName("copyVerticalMenuItem"); // NOI18N
        copyVerticalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyVerticalMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyVerticalMenuItem);

        copyMirrorMenuItem.setText(resourceMap.getString("copyMirrorMenuItem.text")); // NOI18N
        copyMirrorMenuItem.setEnabled(false);
        copyMirrorMenuItem.setName("copyMirrorMenuItem"); // NOI18N
        copyMirrorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMirrorMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyMirrorMenuItem);

        menuBar.add(editMenu);

        portalMenu.setText(resourceMap.getString("portalMenu.text")); // NOI18N
        portalMenu.setName("portalMenu"); // NOI18N

        importFromPortalMenuItem.setText(resourceMap.getString("importFromPortalMenuItem.text")); // NOI18N
        importFromPortalMenuItem.setEnabled(false);
        importFromPortalMenuItem.setName("importFromPortalMenuItem"); // NOI18N
        importFromPortalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importFromPortalMenuItemActionPerformed(evt);
            }
        });
        portalMenu.add(importFromPortalMenuItem);

        exportToPortalMenuItem.setText(resourceMap.getString("exportToPortalMenuItem.text")); // NOI18N
        exportToPortalMenuItem.setEnabled(false);
        exportToPortalMenuItem.setName("exportToPortalMenuItem"); // NOI18N
        exportToPortalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportToPortalMenuItemActionPerformed(evt);
            }
        });
        portalMenu.add(exportToPortalMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        portalMenu.add(jSeparator3);

        visitWebsiteMenuItem.setText(resourceMap.getString("visitWebsiteMenuItem.text")); // NOI18N
        visitWebsiteMenuItem.setName("visitWebsiteMenuItem"); // NOI18N
        visitWebsiteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visitWebsiteMenuItemActionPerformed(evt);
            }
        });
        portalMenu.add(visitWebsiteMenuItem);

        menuBar.add(portalMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 542, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_deleteButtonActionPerformed

    private void passLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passLineButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_passLineButtonActionPerformed

    private void passLineArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passLineArrowButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_passLineArrowButtonActionPerformed

    private void runWithBallLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runWithBallLineButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_runWithBallLineButtonActionPerformed

    private void runWithBallLineArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runWithBallLineArrowButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_runWithBallLineArrowButtonActionPerformed

    private void runWithoutBallLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runWithoutBallLineButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_runWithoutBallLineButtonActionPerformed

    private void runWithoutBallLineArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runWithoutBallLineArrowButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_runWithoutBallLineArrowButtonActionPerformed

    private void shootLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shootLineButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_shootLineButtonActionPerformed

    private void shootLineArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shootLineArrowButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_shootLineArrowButtonActionPerformed

    private void circlePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circlePlayerButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_circlePlayerButtonActionPerformed

    private void trianglePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trianglePlayerButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_trianglePlayerButtonActionPerformed

    private void xPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xPlayerButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_xPlayerButtonActionPerformed

    private void squarePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_squarePlayerButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_squarePlayerButtonActionPerformed

    private void ballItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ballItemButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_ballItemButtonActionPerformed

    private void ballsItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ballsItemButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_ballsItemButtonActionPerformed

    private void clubItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clubItemButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_clubItemButtonActionPerformed

    private void textItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textItemButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
}//GEN-LAST:event_textItemButtonActionPerformed

    private void fieldComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldComboBoxActionPerformed
        JComboBox jb = (JComboBox) evt.getSource();
        String selectedOption = (String) jb.getSelectedItem();
        if (selectedOption.equals("Field 1")) {
            exercise.setBackgroundFileName("2d-full-field.png");
        } else if (selectedOption.equals("Field 2")) {
            exercise.setBackgroundFileName("2d-half-field.png");
        } else if (selectedOption.equals("Field 3")) {
            exercise.setBackgroundFileName("3d-full-horizontal-field.png");
        } else if (selectedOption.equals("Field 4")) {
            exercise.setBackgroundFileName("3d-full-vertical-field.png");
        } else if (selectedOption.equals("Field 5")) {
            exercise.setBackgroundFileName("3d-half-horizontal-field.png");
        } else if (selectedOption.equals("Field 6")) {
            exercise.setBackgroundFileName("3d-half-vertical-field.png");
        } else if (selectedOption.equals("Empty")) {
            exercise.setBackgroundFileName("empty.png");
        }
        this.paintExercise();
}//GEN-LAST:event_fieldComboBoxActionPerformed

    private void drawingLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingLabelMouseClicked
        int getButton = evt.getButton();
        int x = evt.getX();
        int y = evt.getY();

        if (selectedButton != null) {
            if (selectedButton.equals(deleteButton)) {
                this.findAndDelete(x, y);
            } else if (selectedButton.equals(circlePlayerButton)) {
                Item item = new Item(ItemType.PLAYER_CIRCLE, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(trianglePlayerButton)) {
                Item item = new Item(ItemType.PLAYER_TRIANGLE, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(xPlayerButton)) {
                Item item = new Item(ItemType.PLAYER_X, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(squarePlayerButton)) {
                Item item = new Item(ItemType.PLAYER_SQUARE, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(ballItemButton)) {
                Item item = new Item(ItemType.BALL, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(ballsItemButton)) {
                Item item = new Item(ItemType.BALLS, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(clubItemButton)) {
                Item item = new Item(ItemType.CLUB, this.actualColor, x, y, "", 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else if (selectedButton.equals(textItemButton)) {
                String text = JOptionPane.showInputDialog("Input text:");
                Item item = new Item(ItemType.TEXT, this.actualColor, x, y, text, 0);
                exercise.getItems().add(item);
                this.lastAddedThing = item;
            } else {
                if (!newLine) {
                    //if was clicked right button continue in drawing from end of previously drawen line
                    if (getButton != MouseEvent.BUTTON3) {
                        this.lineNewX = x;
                        this.lineNewY = y;
                    }
                    this.newLine = true;
                } else {
                    if (selectedButton.equals(passLineButton)) {
                        Line line = new Line(LineType.PASS, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(passLineArrowButton)) {
                        Line line = new Line(LineType.PASS_ARROW, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(runWithBallLineButton)) {
                        Line line = new Line(LineType.RUN_WITH_BALL, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(runWithBallLineArrowButton)) {
                        Line line = new Line(LineType.RUN_WITH_BALL_ARROW, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(runWithoutBallLineButton)) {
                        Line line = new Line(LineType.RUN_WITHOUT_BALL, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(runWithoutBallLineArrowButton)) {
                        Line line = new Line(LineType.RUN_WITHOUT_BALL_ARROW, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(backRunLineButton)) {
                        Line line = new Line(LineType.BACK_RUN, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(backRunLineArrowButton)) {
                        Line line = new Line(LineType.BACK_RUN_ARROW, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(shootLineButton)) {
                        Line line = new Line(LineType.SHOT, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    } else if (selectedButton.equals(shootLineArrowButton)) {
                        Line line = new Line(LineType.SHOT_ARROW, this.actualColor, this.lineNewX, this.lineNewY, x, y);
                        exercise.getLines().add(line);
                        this.lastAddedThing = line;
                    }
                    this.newLine = false;
                    this.lineNewX = x;
                    this.lineNewY = y;
                }
            }
            undoMenuItem.setEnabled(true);
            this.paintExercise();
        }
}//GEN-LAST:event_drawingLabelMouseClicked

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        //check if user want to save changes
        if (changedSinceLastSave) {
            int saveDialog = JOptionPane.showConfirmDialog(this.getFrame(), "Exercise has been modified. Do you want to save it?", "Question", JOptionPane.YES_NO_OPTION);
            if (saveDialog == JOptionPane.YES_OPTION) {
                this.saveMenuItemActionPerformed(evt);
            }
        }

        this.file = null;
        exercise = new Exercise();
        exercise.setBackgroundFileName("2d-full-field.png");
        this.nameTextField.setText(exercise.getName());
        this.descriptionTextArea.setText(exercise.getDescription());
        this.enableButtons(true);
        this.paintExercise();
        this.getFrame().setTitle(this.frameTitleStart);
        this.changedSinceLastSave = false;
}//GEN-LAST:event_newMenuItemActionPerformed

    private void exportToPngMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportToPngMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG image files (*.png)", "png");
        chooser.setFileFilter(filter);
        if (chooser.showSaveDialog(getFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                File pngfile;
                if (chooser.getSelectedFile().getName().endsWith(".png")) {
                    pngfile = chooser.getSelectedFile();
                } else {
                    pngfile = new File(chooser.getSelectedFile().getAbsoluteFile() + ".png");
                }

                //check if user wants to rewrite
                boolean allowRewrite = false;
                if (pngfile.exists()) {
                    int rewriteDialog = JOptionPane.showConfirmDialog(this.getFrame(), "File already exists. Do you want to rewrite it?", "Question", JOptionPane.YES_NO_OPTION);
                    if (rewriteDialog == JOptionPane.YES_OPTION) {
                        allowRewrite = true;
                    }
                } else {
                    allowRewrite = true;
                }

                //save file
                if (allowRewrite) {
                    ImageIO.write(img, "png", pngfile);
                }
            } catch (IOException ex) {
                //Logger.getLogger(ExerciseDrawerView.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex);
            }
        }
}//GEN-LAST:event_exportToPngMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (this.file != null) {
            XMLConvertor exml = new XMLConvertor();
            exml.saveXMLFile(exercise, file);
            this.changedSinceLastSave = false;
            this.getFrame().setTitle(this.frameTitleStart + "  [" + file.getName() + "]");
        } else {
            this.saveAsMenuItemActionPerformed(evt);
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        //check if user want to save changes
        if (changedSinceLastSave) {
            int saveDialog = JOptionPane.showConfirmDialog(this.getFrame(), "Exercise has been modified. Do you want to save it?", "Question", JOptionPane.YES_NO_OPTION);
            if (saveDialog == JOptionPane.YES_OPTION) {
                this.saveMenuItemActionPerformed(evt);
            }
        }

        JFileChooser chooser = new JFileChooser();
        if (file != null) {
            chooser.setCurrentDirectory(new File(file.getAbsolutePath()));
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Exercise Drawer files (*.edx)", "edx");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(getFrame()) == JFileChooser.APPROVE_OPTION) {
            this.openExerciseFile(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void drawingLabelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingLabelMouseMoved
        if (newLine) {
            Graphics2D gfx = (Graphics2D) drawingLabel.getGraphics();
            gfx.drawImage(img, 0, 0, null);
            gfx.drawLine(lineNewX, lineNewY, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_drawingLabelMouseMoved

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser();
        if (file != null) {
            chooser.setCurrentDirectory(new File(file.getAbsolutePath()));
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Exercise Drawer files (*.edx)", "edx");
        chooser.setFileFilter(filter);
        if (chooser.showSaveDialog(getFrame()) == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getName().endsWith(".edx")) {
                file = chooser.getSelectedFile();
            } else {
                file = new File(chooser.getSelectedFile().getAbsoluteFile() + ".edx");
            }

            //check if user wants to rewrite
            boolean allowRewrite = false;
            if (file.exists()) {
                int rewriteDialog = JOptionPane.showConfirmDialog(this.getFrame(), "File already exists. Do you want to rewrite it?", "Question", JOptionPane.YES_NO_OPTION);
                if (rewriteDialog == JOptionPane.YES_OPTION) {
                    allowRewrite = true;
                }
            } else {
                allowRewrite = true;
            }

            //save file
            if (allowRewrite) {
                XMLConvertor exml = new XMLConvertor();
                exml.saveXMLFile(exercise, file);
                this.getFrame().setTitle(this.frameTitleStart + "  [" + file.getName() + "]");
                this.changedSinceLastSave = false;
            }
        }
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        Print exercisePrint = new Print(this.exercise, this.img);
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(exercisePrint);
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException exc) {
                System.err.println(exc);
            }
        }
    }//GEN-LAST:event_printMenuItemActionPerformed

    private void visitWebsiteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visitWebsiteMenuItemActionPerformed
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.ExerciseDrawerApp.class).getContext().getResourceMap(ExerciseDrawerAboutBox.class);
        String url = resourceMap.getString("Application.homepage");
        io.BrowserLaunch.openURL(url);
    }//GEN-LAST:event_visitWebsiteMenuItemActionPerformed

    private void descriptionTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descriptionTextAreaFocusLost
        exercise.setDescription(descriptionTextArea.getText());
    }//GEN-LAST:event_descriptionTextAreaFocusLost

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        exercise.setName(nameTextField.getText());
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void backRunLineArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRunLineArrowButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
    }//GEN-LAST:event_backRunLineArrowButtonActionPerformed

    private void backRunLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRunLineButtonActionPerformed
        JButton button = (JButton) evt.getSource();
        selectButton(button);
    }//GEN-LAST:event_backRunLineButtonActionPerformed

    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        if (this.newLine) {
            this.newLine = false;
        } else if (lastAddedThing != null) {
            if (lastAddedThing.getClass().getName().toLowerCase().contains("line")) {
                this.exercise.getLines().remove(lastAddedThing);
            } else {
                this.exercise.getItems().remove(lastAddedThing);
            }
            lastAddedThing = null;
            undoMenuItem.setEnabled(false);
        } else if (lastRemovedThing != null) {
            if (lastRemovedThing.getClass().getName().toLowerCase().contains("line")) {
                Line removed = (Line) lastRemovedThing;
                this.exercise.getLines().add(removed);
            } else {
                Item removed = (Item) lastRemovedThing;
                this.exercise.getItems().add(removed);
            }
            lastRemovedThing = null;
            undoMenuItem.setEnabled(false);
        }
        this.paintExercise();
    }//GEN-LAST:event_undoMenuItemActionPerformed

    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed

        final JColorChooser colorChooser = new JColorChooser(actualColor);
        colorChooser.setPreviewPanel(new JPanel());
        // Bug workaround
        colorChooser.updateUI();

        // For okay button selection, change color
        ActionListener okActionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                actualColor = colorChooser.getColor();
            }
        };

        // For cancel button selection do nothing
/*        ActionListener cancelActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
        }
        }; */

        final JDialog dialog = JColorChooser.createDialog(null,
                "Change Color", true, colorChooser,
                okActionListener, null);

        // Wait until current event dispatching completes before showing
        // dialog
//        Runnable showDialog = new Runnable() {
//          public void run() {
        dialog.setVisible(true);
//          }
//        };
//        SwingUtilities.invokeLater(showDialog);

        //choose last button
        this.selectButton(selectedButton);

        //change color of icon in button
        Image iconImage = this.getResourceMap().getImageIcon("items.PLAYER_CIRCLE").getImage();
        iconImage = this.colorOverlay(iconImage, actualColor);
        ImageIcon pane = new ImageIcon();
        pane.setImage(iconImage);
        colorButton.setIcon(pane);

    }//GEN-LAST:event_colorButtonActionPerformed

    private void copyVerticalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyVerticalMenuItemActionPerformed
        Exercise copyExercise = new Exercise();

        //copy data
        copyExercise.setName(exercise.getName());
        copyExercise.setBackgroundFileName(exercise.getBackgroundFileName());
        copyExercise.setDescription(exercise.getDescription());

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            Item copyItem = new Item(item.getType(), actualColor, item.getPosX(), 323 - item.getPosY(), item.getText(), item.getRotation());
            copyExercise.getItems().add(copyItem);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            Line copyLine = new Line(line.getType(), actualColor, line.getStartX(), 323 - line.getStartY(), line.getEndX(), 323 - line.getEndY());
            copyExercise.getLines().add(copyLine);
        }

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            copyExercise.getItems().add(item);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            copyExercise.getLines().add(line);
        }

        this.exercise = copyExercise;
        this.paintExercise();
    }//GEN-LAST:event_copyVerticalMenuItemActionPerformed

    private void copyHorizontalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyHorizontalMenuItemActionPerformed
        Exercise copyExercise = new Exercise();

        //copy data
        copyExercise.setName(exercise.getName());
        copyExercise.setBackgroundFileName(exercise.getBackgroundFileName());
        copyExercise.setDescription(exercise.getDescription());

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            Item copyItem = new Item(item.getType(), actualColor, 520 - item.getPosX(), item.getPosY(), item.getText(), item.getRotation());
            copyExercise.getItems().add(copyItem);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            Line copyLine = new Line(line.getType(), actualColor, 520 - line.getStartX(), line.getStartY(), 520 - line.getEndX(), line.getEndY());
            copyExercise.getLines().add(copyLine);
        }

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            copyExercise.getItems().add(item);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            copyExercise.getLines().add(line);
        }

        this.exercise = copyExercise;
        this.paintExercise();
    }//GEN-LAST:event_copyHorizontalMenuItemActionPerformed

    private void copyMirrorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMirrorMenuItemActionPerformed
        Exercise copyExercise = new Exercise();

        //copy data
        copyExercise.setName(exercise.getName());
        copyExercise.setBackgroundFileName(exercise.getBackgroundFileName());
        copyExercise.setDescription(exercise.getDescription());

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            Item copyItem = new Item(item.getType(), actualColor, 520 - item.getPosX(), 323 - item.getPosY(), item.getText(), item.getRotation());
            copyExercise.getItems().add(copyItem);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            Line copyLine = new Line(line.getType(), actualColor, 520 - line.getStartX(), 323 - line.getStartY(), 520 - line.getEndX(), 323 - line.getEndY());
            copyExercise.getLines().add(copyLine);
        }

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            copyExercise.getItems().add(item);
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            copyExercise.getLines().add(line);
        }

        this.exercise = copyExercise;
        this.paintExercise();
    }//GEN-LAST:event_copyMirrorMenuItemActionPerformed

    private int exportToPortal(String username, String password) {
        /*
        //save exercise to file
        File temp = new File("D:\\temp.xml");
        temp.setWritable(true);
        XMLConvertor exml = new XMLConvertor();
        exml.saveXMLFile(exercise, temp);
        //load file to string
        StringBuilder xmlString = new StringBuilder();
        try {
        BufferedReader in = new BufferedReader(new FileReader(temp));
        String str;
        while ((str = in.readLine()) != null) {
        xmlString.append(str);
        xmlString.append("\n");
        }
        in.close();
        } catch (IOException e) {
        System.err.println(e);
        }
        String xml = xmlString.toString();
        //delete temp file
        temp.delete();
        try { 
        // Call Web Service Operation
        webservice.ExerciseDrawerWSServiceService service = new webservice.ExerciseDrawerWSServiceService();
        webservice.ExerciseDrawerWSService port = service.getExerciseDrawerWSServicePort();
        // TODO process result here
        int result = port.saveToServer(username, password, xml);
        return result;
        } catch (Exception ex) {
        System.err.println(ex);
        }
         */
        return -1;
    }

    private void exportToPortalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportToPortalMenuItemActionPerformed
        String username = JOptionPane.showInputDialog("What is your USERNAME:");
        if (username != null && !username.equals("")) {
            String password = JOptionPane.showInputDialog("What is your PASSWORD:");
            if (password != null && !password.equals("")) {
                int result = exportToPortal(username, password);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this.getFrame(), "Exercise exported\nID: " + result, "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this.getFrame(), "Export failed", "Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_exportToPortalMenuItemActionPerformed

    public boolean importFromPortal(int exerciseId) {
        /*
        try { 
        // Call Web Service Operation
        webservice.ExerciseDrawerWSServiceService service = new webservice.ExerciseDrawerWSServiceService();
        webservice.ExerciseDrawerWSService port = service.getExerciseDrawerWSServicePort();
        //process result here
        String result = port.loadFromServer(exerciseId);
        if (result != null && !result.equals("")) {
        File temp = new File("temp.xml");
        temp.setWritable(true);
        try {
        FileOutputStream fout = new FileOutputStream(temp);
        OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");
        BufferedWriter out = new BufferedWriter(osw);
        out.write(result);
        out.close();
        } catch (IOException e) {
        System.err.println(e);
        }
        this.openExerciseFile(temp);
        temp.delete();
        return true;
        }
        } catch (Exception ex) {
        System.err.println(ex);
        }
         */
        return false;
    }

    private void importFromPortalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importFromPortalMenuItemActionPerformed
        String exerciseId = JOptionPane.showInputDialog("What is ID of Exercise you want to import:");
        if (exerciseId != null && !exerciseId.equals("")) {
            int exId = Integer.parseInt(exerciseId);
            if (importFromPortal(exId)) {
                JOptionPane.showMessageDialog(this.getFrame(), "Exercise imported", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "Import failed", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_importFromPortalMenuItemActionPerformed

    private void sponsorLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sponsorLabelMouseClicked

        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri;
                try {
                    uri = new java.net.URI("http://www.lexx.cool/");
                    desktop.browse(uri);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ExerciseDrawerView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ExerciseDrawerView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_sponsorLabelMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backRunLineArrowButton;
    private javax.swing.JButton backRunLineButton;
    private javax.swing.JButton ballItemButton;
    private javax.swing.JButton ballsItemButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton circlePlayerButton;
    private javax.swing.JButton clubItemButton;
    private javax.swing.JButton colorButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JMenuItem copyHorizontalMenuItem;
    private javax.swing.JMenuItem copyMirrorMenuItem;
    private javax.swing.JMenuItem copyVerticalMenuItem;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JLabel drawingLabel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exportToPngMenuItem;
    private javax.swing.JMenuItem exportToPortalMenuItem;
    private javax.swing.JComboBox fieldComboBox;
    private javax.swing.JLabel fieldLabel;
    private javax.swing.JMenuItem importFromPortalMenuItem;
    private javax.swing.JPanel itemsPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel linesPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton passLineArrowButton;
    private javax.swing.JButton passLineButton;
    private javax.swing.JPanel picturePanel;
    private javax.swing.JPanel playersPanel;
    private javax.swing.JMenu portalMenu;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton runWithBallLineArrowButton;
    private javax.swing.JButton runWithBallLineButton;
    private javax.swing.JButton runWithoutBallLineArrowButton;
    private javax.swing.JButton runWithoutBallLineButton;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton shootLineArrowButton;
    private javax.swing.JButton shootLineButton;
    private javax.swing.JLabel sponsorLabel;
    private javax.swing.JButton squarePlayerButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton textItemButton;
    private javax.swing.JButton trianglePlayerButton;
    private javax.swing.JMenuItem undoMenuItem;
    private javax.swing.JMenuItem visitWebsiteMenuItem;
    private javax.swing.JButton xPlayerButton;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JButton selectedButton;
    private Exercise exercise;
    private BufferedImage img = new BufferedImage(520, 323, BufferedImage.TYPE_INT_RGB);
    private int lineNewX;
    private int lineNewY;
    private boolean newLine = false;
    private File file;
    private Object lastAddedThing;
    private Object lastRemovedThing;
    private Color actualColor = Color.BLACK;
    private String frameTitleStart;
    private boolean changedSinceLastSave = false;

    private void enableButtons(boolean enable) {
        deleteButton.setEnabled(enable);
        colorButton.setEnabled(enable);
        ballItemButton.setEnabled(enable);
        clubItemButton.setEnabled(enable);
        ballsItemButton.setEnabled(enable);
        textItemButton.setEnabled(enable);
        passLineButton.setEnabled(enable);
        passLineArrowButton.setEnabled(enable);
        runWithBallLineButton.setEnabled(enable);
        runWithBallLineArrowButton.setEnabled(enable);
        runWithoutBallLineButton.setEnabled(enable);
        runWithoutBallLineArrowButton.setEnabled(enable);
        backRunLineButton.setEnabled(enable);
        backRunLineArrowButton.setEnabled(enable);
        shootLineButton.setEnabled(enable);
        shootLineArrowButton.setEnabled(enable);
        circlePlayerButton.setEnabled(enable);
        xPlayerButton.setEnabled(enable);
        trianglePlayerButton.setEnabled(enable);
        squarePlayerButton.setEnabled(enable);

        fieldComboBox.setEnabled(enable);
        descriptionTextArea.setEnabled(enable);
        nameTextField.setEnabled(enable);

        saveAsMenuItem.setEnabled(enable);
        saveMenuItem.setEnabled(enable);
        exportToPngMenuItem.setEnabled(enable);
        printMenuItem.setEnabled(enable);

        copyVerticalMenuItem.setEnabled(enable);
        copyHorizontalMenuItem.setEnabled(enable);
        copyMirrorMenuItem.setEnabled(enable);
        //exportToPortalMenuItem.setEnabled(enable);
    }

    private void selectButton(JButton button) {
        deleteButton.setSelected(false);
        ballItemButton.setSelected(false);
        clubItemButton.setSelected(false);
        ballsItemButton.setSelected(false);
        textItemButton.setSelected(false);
        passLineButton.setSelected(false);
        passLineArrowButton.setSelected(false);
        runWithBallLineButton.setSelected(false);
        runWithBallLineArrowButton.setSelected(false);
        runWithoutBallLineButton.setSelected(false);
        runWithoutBallLineArrowButton.setSelected(false);
        shootLineButton.setSelected(false);
        shootLineArrowButton.setSelected(false);
        backRunLineButton.setSelected(false);
        backRunLineArrowButton.setSelected(false);
        circlePlayerButton.setSelected(false);
        xPlayerButton.setSelected(false);
        trianglePlayerButton.setSelected(false);
        squarePlayerButton.setSelected(false);

        if (button != null) {
            button.setSelected(true);
            selectedButton = button;
        }
    }

    private void findAndDelete(int posX, int posY) {

        boolean deleted = false;

        Iterator<Item> it_item = exercise.getItems().iterator();
        while (!deleted && it_item.hasNext()) {
            Item item = it_item.next();
            int itemX = item.getPosX();
            int itemY = item.getPosY();
            if ((Math.abs(posX - itemX) <= 5) && (Math.abs(posY - itemY) <= 5)) {
                lastRemovedThing = item;
                exercise.getItems().remove(item);
                deleted = true;
            }
        }

        Iterator<Line> it_line = exercise.getLines().iterator();
        while (!deleted && it_line.hasNext()) {
            Line line = it_line.next();
            int lineStartX = line.getStartX();
            int lineStartY = line.getStartY();
            int lineEndX = line.getEndX();
            int lineEndY = line.getEndY();
            if (((Math.abs(posX - lineStartX) <= 5) && (Math.abs(posY - lineStartY) <= 5)) || ((Math.abs(posX - lineEndX) <= 5) && (Math.abs(posY - lineEndY) <= 5))) {
                lastRemovedThing = line;
                exercise.getLines().remove(line);
                deleted = true;
            }
        }

    }

    private double angleBetweenPoints(java.awt.Point a, java.awt.Point b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double angle = 0.0d;

        if (dx == 0.0) {
            if (dy == 0.0) {
                angle = 0.0;
            } else if (dy > 0.0) {
                angle = Math.PI / 2.0;
            } else {
                angle = (Math.PI * 3.0) / 2.0;
            }
        } else if (dy == 0.0) {
            if (dx > 0.0) {
                angle = 0.0;
            } else {
                angle = Math.PI;
            }
        } else {
            if (dx < 0.0) {
                angle = Math.atan(dy / dx) + Math.PI;
            } else if (dy < 0.0) {
                angle = Math.atan(dy / dx) + (2 * Math.PI);
            } else {
                angle = Math.atan(dy / dx);
            }
        }
        return (angle * 180) / Math.PI;
    }

    protected void openExerciseFile(File exerciseFile) {
        XMLConvertor exml = new XMLConvertor();
        this.exercise = exml.loadXMLFile(exerciseFile);
        if (exercise != null) {
            this.file = exerciseFile;
            this.enableButtons(true);
            this.paintExercise();
            this.nameTextField.setText(exercise.getName());
            this.descriptionTextArea.setText(exercise.getDescription());
            this.getFrame().setTitle(this.frameTitleStart + "  [" + file.getName() + "]");
            this.changedSinceLastSave = false;
        }
    }

    private BufferedImage colorOverlay(Image image, Color color) {

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage sourceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gfx = sourceImage.createGraphics();
        gfx.drawImage(image, 0, 0, null);
        gfx.dispose();

        int rgbDifference = color.getRGB() - Color.BLACK.getRGB();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int actualPixelColor = sourceImage.getRGB(x, y);
                resultImage.setRGB(x, y, actualPixelColor | rgbDifference);
            }
        }

        return resultImage;
    }

    private void paintExercise() {
        Image image;
        Graphics2D gfx = img.createGraphics();
        //Graphics2D gfx = (Graphics2D) drawingPane.getGraphics();
        ResourceMap resourceMap = getResourceMap();

        //draw background
        image = resourceMap.getImageIcon("fields." + exercise.getBackgroundFileName()).getImage();
        gfx.drawImage(image, 0, 0, null);

        //draw items
        for (Iterator<Item> it = exercise.getItems().iterator(); it.hasNext();) {
            Item item = it.next();
            if (item.getType() == ItemType.TEXT) {
                gfx.setColor(item.getColor());
                gfx.drawString(item.getText(), item.getPosX(), item.getPosY() + 4);
            } else {
                image = resourceMap.getImageIcon("items." + item.getType().name()).getImage();
                //color overlay
                if (item.getColor() != Color.BLACK) {
                    image = this.colorOverlay(image, item.getColor());
                }
                gfx.drawImage(image, item.getPosX() - 7, item.getPosY() - 7, null);
            }
        }

        //draw lines
        for (Iterator<Line> it = exercise.getLines().iterator(); it.hasNext();) {
            Line line = it.next();
            //load image source
            Image imageSrc = resourceMap.getImageIcon("lines." + line.getType().name()).getImage();

            //get lenght of line
            Double dist = Point.distance(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            int length = dist.intValue();

            //create cutted image
            BufferedImage imageCut = new BufferedImage(length, 11, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphicsCut = imageCut.createGraphics();
            graphicsCut.drawImage(imageSrc, 0, 0, length, 11, 692 - length, 0, 692, 11, null);
            graphicsCut.dispose();

            //color overlay
            if (line.getColor() != Color.BLACK) {
                imageCut = this.colorOverlay(imageCut, line.getColor());
            }

            //create rotated image
            BufferedImage imageRotate = new BufferedImage(520, 323, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphicsRotate = imageRotate.createGraphics();
            //count angle
            double pointsAngle = this.angleBetweenPoints(new Point(line.getStartX(), line.getStartY()), new Point(line.getEndX(), line.getEndY()));
            //set rotation ankle, and rotation center points
            graphicsRotate.rotate(Math.toRadians(pointsAngle), line.getStartX(), line.getStartY());
            graphicsRotate.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphicsRotate.drawImage(imageCut, line.getStartX(), line.getStartY() - 6, null);
            graphicsRotate.dispose();

            //draw result
            //gfx.drawImage(imageRotate, line.getStartX(), line.getStartY() - 5, null);
            gfx.drawImage(imageRotate, 0, 0, null);
        }

        ImageIcon pane = new ImageIcon();
        pane.setImage(img);
        drawingLabel.setIcon(pane);

        //update gui
        if (!changedSinceLastSave) {
            this.changedSinceLastSave = true;
            if (!this.getFrame().getTitle().endsWith("*]")) {
                if (file == null) {
                    this.getFrame().setTitle(this.frameTitleStart + "  [*]");
                } else {
                    this.getFrame().setTitle(this.frameTitleStart + "  [" + file.getName() + "*]");
                }
            }
        }
    }

    protected BufferedImage getImage() {
        return img;
    }
}
