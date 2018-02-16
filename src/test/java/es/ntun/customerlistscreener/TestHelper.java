package es.ntun.customerlistscreener;

import java.util.Random;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.github.javafaker.Faker;

import es.ntun.customerlistscreener.customer.Customer;
import es.ntun.customerlistscreener.customer.EarthPoint;

public class TestHelper {

	private static Random random = new Random();
	private static Faker faker = new Faker();

	public static interface ThrowingRunnable {
		void run() throws Throwable;
	}

	public static <T extends Throwable> Matcher<ThrowingRunnable> throwsA(Class<T> exceptionClass) {
		return new BaseMatcher<ThrowingRunnable>() {
			private static final String ERROR_DESCRIPTION = "an exception of type %s";
			private Throwable lastThrown;

			@Override
			public boolean matches(Object item) {
				ThrowingRunnable function = (ThrowingRunnable) item;
				try {
					function.run();
				} catch (Throwable e) {
					lastThrown = e;
					if (exceptionClass.isInstance(e)) {
						return true;
					}
				}

				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(String.format(ERROR_DESCRIPTION,
				                                     exceptionClass.getSimpleName()));
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				// assuming this is called right after a mismatch
				if (lastThrown == null) {
					description.appendText("none was thrown");
				} else {
					description.appendText(String.format("was " + ERROR_DESCRIPTION,
					                                     lastThrown.getClass().getSimpleName()));
				}

			}
		};
	}

	public static EarthPoint generateLocation() {
		return new EarthPoint(generateCoordinate(), generateCoordinate());
	}

	// produce a co-ordinate in the interval [-180, 180]
	public static double generateCoordinate() {
		double randomBase = random.nextDouble() - 0.5;
		return randomBase * 360;
	}

	public static Customer generateCustomer() {
		return new Customer(random.nextInt(), faker.name().fullName(), generateLocation());
	}
}
