package testcases;

import java.util.List;
import java.util.Map;

/**
 * 该类是JASON的bean类，用来产生测试用例
 * @author phantom
 */
public class Bean {
    private String id ;//测试用例的ID从1开始
    private String airlineType;
    private String cabinClass;
    private String passangerCatagory;
    private String economyClassFare;
    private String baggageWeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(String airlineType) {
        this.airlineType = airlineType;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getPassangerCatagory() {
        return passangerCatagory;
    }

    public void setPassangerCatagory(String passangerCatagory) {
        this.passangerCatagory = passangerCatagory;
    }

    public String getEconomyClassFare() {
        return economyClassFare;
    }

    public void setEconomyClassFare(String economyClassFare) {
        this.economyClassFare = economyClassFare;
    }

    public String getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(String baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

   public void setvariables(String id,String airlineType,String cabinClass,String passangerCatagory,
                              String economyClassFare,String baggageWeight) {
        setId(id);
        setAirlineType(airlineType);
        setCabinClass(cabinClass);
        setPassangerCatagory(passangerCatagory);
        setEconomyClassFare(economyClassFare);
        setBaggageWeight(baggageWeight);
   }
}

