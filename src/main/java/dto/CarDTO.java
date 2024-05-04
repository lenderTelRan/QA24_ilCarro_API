package dto;

import enumclasses.Fuel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class CarDTO {
    private String serialNumber;
    private String manufacture;
    private String model;
    private String year;
    Fuel fuel;
    private int seats;
    private String carClass;
    private double pricePerDay;
    private String about;
    private String city;
    private double lat;
    private double lng;
    private String image;
    private List<BookedDTO> bookedPeriods;
}
