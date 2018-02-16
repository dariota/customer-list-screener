package es.ntun.customerlistscreener.customer;

public class EarthPoint {

	// roughly, according to wikipedia - there's no simple answer
	private static final double EARTH_RADIUS_M = 6371E3d;

	private double latitude, longitude;

	public EarthPoint(double latitudeDec, double longitudeDec) {
		if (!(Double.isFinite(latitudeDec) && Double.isFinite(longitudeDec))) {
			throw new IllegalArgumentException("Latitude/Longitude must be finite");
		}

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

	@Override
	// this is obviously a terrible hash, but the representation of doubles and
	// serialisation/deserialisation to decimal makes a sensible hash that still
	// matches for doubles of equivalent value hard
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EarthPoint other = (EarthPoint) obj;
		if (!approximatelyEqual(latitude, other.latitude)) {
			return false;
		}
		if (!approximatelyEqual(longitude, other.longitude)) {
			return false;
		}
		return true;
	}

	private static boolean approximatelyEqual(double a, double b) {
		return Math.abs(a - b) <= 6E-6;
	}
}
