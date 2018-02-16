package es.ntun.customerlistscreener;

public class EarthPoint {

	// roughly, according to wikipedia - there's no simple answer
	private static final double EARTH_RADIUS_M = 6371E3d;

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

	// for 64 bit numbers, the error isn't too large even for relatively small
	// (order of metres) distances, so we use the spherical law of cosines for
	// simplicity
	public double distanceTo(EarthPoint other) {
		if (other == null) {
			throw new IllegalArgumentException("Point to find distance to must not be null");
		}

		double deltaLongitude = Math.abs(longitude - other.longitude);

		// names roughly based on what they are in the spherical law of cosines formula
		double sinLatitudeProduct = Math.sin(latitude) * Math.sin(other.latitude);
		double cosLatitudeLongitudeProduct = Math.cos(latitude) * Math.cos(other.latitude)
		                                     * Math.cos(deltaLongitude);

		double centralAngle = Math.acos(sinLatitudeProduct + cosLatitudeLongitudeProduct);

		return EARTH_RADIUS_M * centralAngle;
	}
}