package es.ntun.customerlistscreener.customer;

import static es.ntun.customerlistscreener.Helper.generateCoordinate;
import static es.ntun.customerlistscreener.Helper.generateLocation;
import static es.ntun.customerlistscreener.Helper.throwsA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

import org.junit.Test;

// all distances in metres
public class TestEarthPoint {

	private static final double TOLERABLE_ERROR = 5;

	@Test
	public void distanceTo_result_should_approximate_precomputed_result() {
		// GPS location from Google Maps
		EarthPoint spireLocation = new EarthPoint(53.3498092, -6.2636914);
		EarthPoint officeLocation = new EarthPoint(53.339428, -6.257664);
		// Distance from WolframAlpha
		double precomputedDistance = 1223;

		double distance = spireLocation.distanceTo(officeLocation);

		assertThat(distance, closeTo(precomputedDistance, TOLERABLE_ERROR));
	}

	@Test
	public void distanceTo_should_be_argument_ordering_independent() {
		EarthPoint randomLocation = generateLocation();
		EarthPoint otherLocation = generateLocation();

		double randomToOtherDistance = randomLocation.distanceTo(otherLocation);
		double otherToRandomDistance = otherLocation.distanceTo(randomLocation);

		assertThat(randomToOtherDistance, closeTo(otherToRandomDistance, TOLERABLE_ERROR));
	}

	@Test
	public void distanceTo_self_should_approximate_zero() {
		// run this test a load of times to catch an issue with floating point error
		for (int i = 0; i < 10000; i++) {
			EarthPoint randomLocation = generateLocation();

			assertThat(randomLocation.distanceTo(randomLocation), closeTo(0, TOLERABLE_ERROR));
		}
	}

	@Test
	public void constructor_should_give_same_result_for_coordinates_modulo_360() {
		double latitude = generateCoordinate(), longitude = generateCoordinate();
		double irrelevantOffset = 360;
		EarthPoint randomLocation = new EarthPoint(latitude, longitude);
		EarthPoint positiveOffset = new EarthPoint(latitude + irrelevantOffset,
		                                           longitude + irrelevantOffset);
		EarthPoint negativeOffset = new EarthPoint(latitude - irrelevantOffset,
		                                           longitude - irrelevantOffset);

		double positiveDistance = randomLocation.distanceTo(positiveOffset);
		double negativeDistance = randomLocation.distanceTo(negativeOffset);
		double bothDistance = positiveOffset.distanceTo(negativeOffset);

		assertThat(positiveDistance, closeTo(0, TOLERABLE_ERROR));
		assertThat(negativeDistance, closeTo(0, TOLERABLE_ERROR));
		assertThat(bothDistance, closeTo(0, TOLERABLE_ERROR));
	}

	@Test
	public void passing_nan_or_inf_to_constructor_should_throw_illegal_argument_exception() {
		checkConstructorWith(Double.NaN);
		checkConstructorWith(Double.NEGATIVE_INFINITY);
		checkConstructorWith(Double.POSITIVE_INFINITY);
	}

	private static void checkConstructorWith(double input) {
		assertThat(() -> new EarthPoint(input, input), throwsA(IllegalArgumentException.class));
		assertThat(() -> new EarthPoint(input, 0), throwsA(IllegalArgumentException.class));
		assertThat(() -> new EarthPoint(0, input), throwsA(IllegalArgumentException.class));
	}

	@Test
	public void passing_null_to_distanceTo_should_throw_illegal_argument_exception() {
		EarthPoint nullIsland = new EarthPoint(0, 0);

		assertThat(() -> nullIsland.distanceTo(null), throwsA(IllegalArgumentException.class));
	}

}
