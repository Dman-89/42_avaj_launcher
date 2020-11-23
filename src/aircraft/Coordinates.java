package aircraft;

public class Coordinates {

    private int longitude;
    private int latitude;
    private int height;

    Coordinates(final int longitude, final int latitude, final int height) {
        this.longitude = (longitude > 0) ? longitude : 0;
        this.latitude = (latitude > 0) ? latitude : 0;
        this.height = (height >= 0 && height <= 100) ? height : (height > 100) ? 100 : 0;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getHeight() {
        return height;
    }
}
