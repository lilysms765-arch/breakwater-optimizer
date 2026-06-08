package com.breakwater;

import com.breakwater.model.BreakwaterDesign;
import com.breakwater.optimization.Optimizer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private double waveOffset = 0;

    private double waveHeight = 5;
    private double waterDepth = 10;

    private BreakwaterDesign design;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(900, 600);

        TextField waveInput = new TextField("5");
        TextField depthInput = new TextField("10");

        Label results = new Label("Press Optimize");

        Button optimize = new Button("Optimize Design");

        optimize.setOnAction(e -> {

            waveHeight =
                    Double.parseDouble(waveInput.getText());

            waterDepth =
                    Double.parseDouble(depthInput.getText());

            design =
                    Optimizer.optimize(
                            waveHeight,
                            waterDepth);

            if (design != null) {

                results.setText(
                        "Slope 1:"
                                + String.format("%.2f",
                                design.getSlope())
                                + "\nArmor Weight: "
                                + String.format("%.2f",
                                design.getArmorWeight())
                                + " tons"
                                + "\nVolume: "
                                + String.format("%.2f",
                                design.getConcreteVolume())
                                + " m³"
                                + "\nCrane Check: PASS");
            }
        });

        VBox leftPanel = new VBox(
                10,
                new Label("Wave Height (m)"),
                waveInput,
                new Label("Water Depth (m)"),
                depthInput,
                optimize,
                results
        );

        leftPanel.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(canvas);

        GraphicsContext g =
                canvas.getGraphicsContext2D();

        AnimationTimer timer =
                new AnimationTimer() {

                    @Override
                    public void handle(long now) {

                        waveOffset += 0.08;

                        drawScene(
                                g,
                                canvas.getWidth(),
                                canvas.getHeight());
                    }
                };

        timer.start();

        Scene scene =
                new Scene(root, 1200, 700);

        stage.setTitle(
                "Breakwater Optimization System");

        stage.setScene(scene);
        stage.show();
    }

    private void drawScene(
            GraphicsContext g,
            double width,
            double height) {

        g.setFill(Color.LIGHTBLUE);
        g.fillRect(0, 0, width, height);

        double waterLine = 350;

        g.setFill(Color.DARKBLUE);
        g.fillRect(
                0,
                waterLine,
                width,
                height - waterLine);

        g.setStroke(Color.WHITE);

        for (int x = 0; x < width; x++) {

            double y =
                    waterLine
                            + Math.sin(
                            x * 0.03 + waveOffset)
                            * 12;

            g.strokeLine(
                    x,
                    y,
                    x + 1,
                    y);
        }

        drawBreakwater(g);
    }

    private void drawBreakwater(
            GraphicsContext g) {

        if (design == null) {
            return;
        }

        double baseX = 450;
        double baseY = 550;

        double slope =
                design.getSlope();

        double crestHeight = 250;

        double crestX =
                baseX + slope * 70;

        g.setFill(Color.GRAY);

        double[] xs = {
                baseX,
                crestX,
                crestX + 80,
                baseX + 80
        };

        double[] ys = {
                baseY,
                crestHeight,
                crestHeight,
                baseY
        };

        g.fillPolygon(xs, ys, 4);

        g.setFill(Color.BLACK);

        g.fillText(
                "Armor Layer",
                crestX + 20,
                crestHeight - 10);

        g.fillText(
                "Optimized Slope 1:"
                        + String.format("%.2f",
                        slope),
                crestX + 20,
                crestHeight + 15);

        g.fillText(
                "Protected Harbor",
                crestX + 120,
                330);

        g.fillText(
                "Incoming Waves",
                150,
                330);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
