package com.example.sae201.model;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapRectangleLayer extends MapLayer {
    private final MapPoint mapPoint;
    private final Rectangle rectangle;

    public MapRectangleLayer(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        this.rectangle = new Rectangle(8, 8, 16, 16);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        this.getChildren().add(rectangle);
    }

    @Override
    protected void layoutLayer() {
        Point2D point2D = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
        rectangle.setTranslateX(point2D.getX());
        rectangle.setTranslateY(point2D.getY());
    }

    @Override
    protected void initialize() {
        layoutLayer();
    }
}
