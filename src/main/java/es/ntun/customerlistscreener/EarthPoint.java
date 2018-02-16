package es.ntun.customerlistscreener;

public class EarthPoint {

	private double latitude, longitude;

	public EarthPoint(double latitudeDec, double longitudeDec) {
		latitude = Math.toRadians(latitudeDec);
		longitude = Math.toRadians(longitudeDec);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double distanceTo(EarthPoint other) {
		return 0;
	}
}
