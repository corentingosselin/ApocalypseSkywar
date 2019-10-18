package fr.cocoraid.apocalypseskywar.position;

public class Point {

    public double x,y,z;
    public float yaw;
    public float pitch = 90;


    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y, double z, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
    }
}
