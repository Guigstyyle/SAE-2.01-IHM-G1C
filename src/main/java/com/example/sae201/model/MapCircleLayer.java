package com.example.sae201.model;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MapCircleLayer extends MapLayer {
    private final MapPoint mapPoint;
    private final Circle circle;

    public MapCircleLayer(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        this.circle = new Circle(5);
        circle.setFill(Color.RED);
        this.getChildren().add(circle);
    }

    @Override
    protected void layoutLayer() {
        Point2D point2D = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
        circle.setTranslateX(point2D.getX());
        circle.setTranslateY(point2D.getY());
    }

    @Override
    protected void initialize() {
        layoutLayer();
    }
}
