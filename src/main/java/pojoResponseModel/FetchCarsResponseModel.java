package pojoResponseModel;

import java.util.ArrayList;

public class FetchCarsResponseModel {
    public String status;
    public int user_id;
    public ArrayList<Car> cars = new ArrayList<>();

    public class Car{
        public int car_id;
        public String make;
        public String model;
        public int year;
    }
}
