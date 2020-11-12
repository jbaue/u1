package car;

import car.exception.CarIsUnavailableException;
import car.exception.DealershipException;
import car.exception.UnknownCarException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DealershipTest {
    @Test
    void testDriveThrowsUnknownCarExceptionIfCarIsNotInMap() {
        Dealership dealer = new Dealership(new HashMap<>());

        UnknownCarException thrown = assertThrows(
                UnknownCarException.class,
                () -> dealer.testDrive(1)
        );
    }

    @Test
    void testDriveThrowsCarIsUnavailableExceptionIfCarIsAlreadySoldOrRented() {
        Car car = Mockito.mock(Car.class);
        Mockito.when(car.id()).thenReturn(1);
        Mockito.when(car.isAvailable()).thenReturn(false);

        Map cars = new HashMap();
        cars.put(1, car);
        Dealership dealer = new Dealership(cars);

        CarIsUnavailableException thrown = assertThrows(
                CarIsUnavailableException.class,
                () -> dealer.sell(car)
        );
    }

    @Test
    void testDriveReturnsExpectedCar() {
        Car car = Mockito.mock(Car.class);
        Mockito.when(car.id()).thenReturn(1);
        Mockito.when(car.isAvailable()).thenReturn(true);

        Map cars = new HashMap();
        cars.put(1, car);
        Dealership dealer = new Dealership(cars);

        try {
            assertSame(car, dealer.testDrive(1));
        } catch (DealershipException e) {
            fail();
        }
    }

    @Test
    void sellThrowsUnknownCarExceptionIfCarIsNotInMap() {
        Car car = Mockito.mock(Car.class);
        Dealership dealer = new Dealership(new HashMap<>());

        UnknownCarException thrown = assertThrows(
                UnknownCarException.class,
                () -> dealer.sell(car)
        );
    }

    @Test
    void sellThrowsCarIsUnavailableExceptionIfCarIsAlreadySoldOrRented() {
        Car car = Mockito.mock(Car.class);
        Mockito.when(car.id()).thenReturn(1);
        Mockito.when(car.isAvailable()).thenReturn(false);

        Map cars = new HashMap();
        cars.put(1, car);
        Dealership dealer = new Dealership(cars);

        CarIsUnavailableException thrown = assertThrows(
                CarIsUnavailableException.class,
                () -> dealer.sell(car)
        );
    }

    @Test
    void sellMarksCarAsSold() {
        int id = 1;
        int price = 1000;
        Car car = Mockito.mock(Car.class);
        Mockito.when(car.id()).thenReturn(id);
        Mockito.when(car.cost()).thenReturn(price);
        Mockito.when(car.isAvailable()).thenReturn(true, false);

        Map cars = new HashMap();
        cars.put(1, car);
        Dealership dealer = new Dealership(cars);

        try {
            Invoice invoice = dealer.sell(car);

            assertEquals(id, invoice.carId());
            assertEquals(price, invoice.price());
        } catch (DealershipException e) {
            fail();
        }

        Mockito.verify(car, Mockito.times(1)).sell();
    }


}