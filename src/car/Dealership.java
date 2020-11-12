package car;

import car.exception.CarIsUnavailableException;
import car.exception.DealershipException;
import car.exception.UnknownCarException;

import java.util.Map;

public class Dealership {
    private Map<Integer, Car> cars;

    public Dealership(Map<Integer, Car> cars) {
        this.cars = cars;
    }

    public Car testDrive(int id) throws DealershipException {
        if (!this.cars.containsKey(id)) {
            throw new UnknownCarException();
        }

        Car car = this.cars.get(id);
        if (!car.isAvailable()) {
            throw new CarIsUnavailableException();
        }

        return this.cars.get(id);
    }

    public Invoice sell(Car carToSell) throws DealershipException {
        if (!this.cars.containsKey(carToSell.id())) {
            throw new UnknownCarException();
        }

        if (!carToSell.isAvailable()) {
            throw new CarIsUnavailableException();
        }

        carToSell.sell();

        return Invoice.forSoldCar(carToSell);
    }
}
