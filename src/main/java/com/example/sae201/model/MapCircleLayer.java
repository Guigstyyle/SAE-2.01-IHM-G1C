package com.example.sae201.model;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The MapCircleLayer class represents a map layer that displays a circle marker on a map.
 */
public class MapCircleLayer extends MapLayer {
    private final MapPoint mapPoint;
    private final Circle circle;

    /**
     * Constructs a MapCircleLayer object with the specified map point.
     *
     * @param mapPoint the map point to display the circle marker at
     */
    public MapCircleLayer(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        this.circle = new Circle(5);
        circle.setFill(Color.RED);
        this.getChildren().add(circle);
    }

    /**
     * Positions and sizes the circle marker on the map layer based on the current map point.
     */
    @Override
    protected void layoutLayer() {
        Point2D point2D = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
        circle.setTranslateX(point2D.getX());
        circle.setTranslateY(point2D.getY());
    }

    /**
     * Initializes the map layer by laying out its components.
     */
    @Override
    protected void initialize() {
        layoutLayer();
    }
}
