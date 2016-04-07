package sample;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.awt.geom.Point2D;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public VBox vbox;
    public Button plot;
    public TextField y1;
    public TextField y2;
    public TextField y3;
    public TextField y4;
    public TextField x1;
    public TextField x2;
    public TextField x3;
    public TextField x4;
    public TextField lab1;
    public TextField lab2;
    public TextField lab3;
    public TextField lab4;
    public Button clear;
    public Button track;
    public Button optimize;
    public Button update;
    public Button track_next;
    private point p1,p2,p3,p4;
    private point a,b,c,d;
    ArrayList <point> Track;
    HashMap <point,point> TrackLines;
    ArrayList <Line> lines;

//    private void drawShapes(GraphicsContext gc) {
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(2);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210 , 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
//    }

    class point {
        String name;
        double X;
        double Y;
        point mindistance;

        public point getMindistance() {
            return mindistance;
        }

        public void setMindistance(point mindistance) {
            this.mindistance = mindistance;
        }

        public point(String name, double x, double y) {
            this.name = name;
            X = x;
            Y = y;
        }


        public double getY() {
            return Y;
        }

        public void setY(Integer y) {
            Y = y;
        }

        public double getX() {
            return X;
        }

        public void setX(Integer x) {
            X = x;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        drawShapes(gc);

        Track=new ArrayList<>();
        TrackLines=new HashMap<>();
        lines=new ArrayList<>();
        Axes axes = new Axes(
                500, 500,
                -15, 15, 1,
                -15, 15, 1
        );
        vbox.getChildren().add(axes);
        vbox.setAlignment(Pos.CENTER);
//        createLine(mapX(2,axes),mapY(2,axes),mapX(4,axes),mapY(4,axes),axes,createText("a1"),createText("a2"));
//        Point2D point2D=new Point2D(mapX(3,axes),mapY(3,axes));
//        createPoint(mapX(3,axes),mapY(3,axes),axes,createText("a1"));

        plot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                p1=new point(lab1.getText(),mapX(Double.valueOf(x1.getText()),axes),mapY(Double.valueOf(y1.getText()), axes));
                p2=new point(lab2.getText(),mapX(Double.valueOf(x2.getText()),axes),mapY(Double.valueOf(y2.getText()), axes));
                p3=new point(lab3.getText(),mapX(Double.valueOf(x3.getText()),axes),mapY(Double.valueOf(y3.getText()), axes));
                p4=new point(lab4.getText(),mapX(Double.valueOf(x4.getText()),axes),mapY(Double.valueOf(y4.getText()), axes));
                Track.add(p1);Track.add(p3);
                Track.add(p2);Track.add(p4);
                createPoint(p1.getX(), p1.getY(), axes, createText(p1.getName()));
                createPoint(p2.getX(),p2.getY(),axes,createText(p2.getName()));
                createPoint(p3.getX(),p3.getY(),axes,createText(p3.getName()));
                createPoint(p4.getX(),p4.getY(),axes,createText(p4.getName()));

            }
        });

        track.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getMinDistancePoint(p1, Track);
                Track.remove(p1);
                getMinDistancePoint(p1.getMindistance(), Track);
                Track.remove(p1.getMindistance());
                getMinDistancePoint(p1.getMindistance().getMindistance(), Track);

                createLine(p1.getX(), p1.getY(), p1.getMindistance().getX(), p1.getMindistance().getY(), axes, Color.BLUE);
                TrackLines.put(p1, p1.getMindistance());
//                TrackLines.put(p1.getMindistance(),p1);
                createLine(p1.getMindistance().getX(), p1.getMindistance().getY(), p1.getMindistance().getMindistance().getX(), p1.getMindistance().getMindistance().getY(), axes, Color.BLUE);
                TrackLines.put(p1.getMindistance(), p1.getMindistance().getMindistance());
//                TrackLines.put(p1.getMindistance().getMindistance(),p1.getMindistance());
                createLine(p1.getMindistance().getMindistance().getX(), p1.getMindistance().getMindistance().getY(), p1.getMindistance().getMindistance().getMindistance().getX(), p1.getMindistance().getMindistance().getMindistance().getY(), axes, Color.BLUE);
                TrackLines.put(p1.getMindistance().getMindistance(), p1.getMindistance().getMindistance().getMindistance());
//                TrackLines.put (p1.getMindistance().getMindistance().getMindistance(),p1.getMindistance().getMindistance());
            }
        });

        track_next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                plot.setDisable(true);
                track.setDisable(true);
                Track.add(p1);
                Track.add(p1.getMindistance());

                a=new point(lab1.getText(),mapX(Double.valueOf(x1.getText()),axes),mapY(Double.valueOf(y1.getText()), axes));
                b=new point(lab2.getText(),mapX(Double.valueOf(x2.getText()),axes),mapY(Double.valueOf(y2.getText()), axes));
                c=new point(lab3.getText(),mapX(Double.valueOf(x3.getText()),axes),mapY(Double.valueOf(y3.getText()), axes));
                d=new point(lab4.getText(),mapX(Double.valueOf(x4.getText()),axes),mapY(Double.valueOf(y4.getText()), axes));

                createPoint(a.getX(), a.getY(), axes, createText(a.getName()));
                createPoint(b.getX(), b.getY(), axes, createText(b.getName()));
                createPoint(c.getX(), c.getY(), axes, createText(c.getName()));
                createPoint(d.getX(), d.getY(), axes, createText(d.getName()));

            }
        });

        update.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                getMinDistancePoint(a, Track);
                Track.remove(a.getMindistance());
                getMinDistancePoint(b, Track);
                Track.remove(b.getMindistance());
                getMinDistancePoint(c, Track);
                Track.remove(c.getMindistance());
                getMinDistancePoint(d, Track);
                Track.remove(d.getMindistance());

                createLine(a.getX(), a.getY(), a.getMindistance().getX(), a.getMindistance().getY(), axes, Color.BROWN);
                createLine(b.getX(), b.getY(), b.getMindistance().getX(), b.getMindistance().getY(), axes, Color.BROWN);
                createLine(c.getX(), c.getY(), c.getMindistance().getX(), c.getMindistance().getY(), axes, Color.BROWN);
                createLine(d.getX(), d.getY(), d.getMindistance().getX(), d.getMindistance().getY(), axes, Color.BROWN);

            }
        });

        optimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println(getNearestPoint(a).getName());
//                System.out.println(getNearestPoint(getNearestPoint(a)).getName());
//                System.out.println(getNearestPoint(getNearestPoint(getNearestPoint(a))).getName());
                axes.removelines();
                point a1=getNearestPoint(a);
                createLine(a.getX(),a.getY(),a1.getX(),a1.getY(),axes,Color.RED);
                point b1=getNearestPoint(a1);
                createLine(a1.getX(),a1.getY(),b1.getX(),b1.getY(),axes,Color.RED);
                point c1=getNearestPoint(b1);
                createLine(b1.getX(),b1.getY(),c1.getX(),c1.getY(),axes,Color.RED);
            }
        });



        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                axes.reset();
                plot.setDisable(false);
                track.setDisable(false);
            }
        });

//        System.out.println(Point2D.distance(0, 0, 3, 4));

    }
    public <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public Axes(
                int width, int height,
                double xLow, double xHi, double xTickUnit,
                double yLow, double yHi, double yTickUnit
        ) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(true);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(true);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(
                    Bindings.subtract(
                            (width / 2) + 1,
                            yAxis.widthProperty()
                    )
            );

            getChildren().setAll(xAxis, yAxis);
        }

        void reset(){
            getChildren().removeAll();
            getChildren().setAll(xAxis, yAxis);
        }
        void removelines(){
            getChildren().removeAll(lines);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }

        public NumberAxis getYAxis() {
            return yAxis;
        }
    }
    private double mapX(double x, Axes axes) {
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() /
                (axes.getXAxis().getUpperBound() -
                        axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    private double mapY(double y, Axes axes) {
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() /
                (axes.getYAxis().getUpperBound() -
                        axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    private Text createText(String string) {
        Text text = new Text(string);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                        "-fx-font-style: italic;" +
                        "-fx-font-size: 12px;"
        );
        return text;
    }
    private void createPoint(Double x,Double y,Axes axes,Text text){
        Circle circle=new Circle(x,y,1);
        circle.setFill(Color.RED);
        text.setX(x + 2);
        text.setY(y - 2);
        axes.getChildren().addAll(circle, text);
    }

    private void createLine(Double x1,Double y1,Double x2,Double y2,Axes axes,Color color){
        Line line=new Line(x1,y1,x2,y2);
        line.setFill(color);
        axes.getChildren().add(line);
        lines.add(line);
    }

    private void createLine(Double x1,Double y1,Double x2,Double y2,Axes axes,Text label1,Text label2){
        Line line=new Line(x1,y1,x2,y2);
        label1.setX(x1-15);
        label1.setY(y1-10);
        label2.setX(x2+15);
        label2.setY(y2 - 10);
        axes.getChildren().addAll(line, label1, label2);
    }

    public void getMinDistancePoint(point p,List <point> points){
        double mindistance=999,distance;
        for (point pt :points){
            distance=Point2D.distance(p.getX(),p.getY(),pt.getX(),pt.getY());
            if (distance<mindistance && !pt.getName().equalsIgnoreCase(p.getName())){
                mindistance=distance;
                p.setMindistance(pt);
            }
        }
    }

    public point getNearestPoint(point p){
        Track.add(a);Track.add(b);Track.add(c);Track.add(d);
        Track.remove(p);
            point ptr=p.getMindistance();
            if (ptr.mindistance!=null){
                for (int i=0;i<Track.size();i++){
                    if (Track.get(i).mindistance==ptr.mindistance){
                        return Track.get(i);
                    }
                }
            }else{
                point temp=getKeyByValue(TrackLines,ptr);
                for (int i=0;i<Track.size();i++){
                    if (Track.get(i).mindistance==temp){
                        return Track.get(i);
                    }
                }
        }
        return null;
    }
}
