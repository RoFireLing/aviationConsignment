package cn.edu.ustb.www.aviationconsignment.model;

/**
 * 乘客类——封装乘客信息的类。<pre>
 * 	包含：航班类型（国内、国际）， <br>
 * 	座舱等级（头等舱、商务舱、经济舱），<br>
 * 	经济舱票价， <br>
 * 	乘客类型（成人、学生、儿童、婴儿），<br>
 * 	携带行李总质量
 * </pre>
 * 
 * @author zzq
 * 
 */
public class Passanger {

	public static final int DOMESTIC = 0;// 国内航班
	public static final int INTERNATIONAL = 1;// 国际航班
	public static final int TOURIST_CLASS = 0;// 经济舱
	public static final int BUSINESS_CLASS = 1;// 商务舱
	public static final int FIRST_CLASS = 2;// 头等舱
	public static final int INFANT = 0;// 婴儿
	public static final int CHILD = 1;// 儿童
	public static final int STUDENT = 2;// 学生
	public static final int ADULT = 3;// 成人

	private int airlineType;// 航班类型，0代表国内，1代表国际
	private int cabinClass;// 座舱等级，0代表经济舱，1代表商务舱，2代表头等舱
	private double economyClassFare;// 经济舱票价
	private int passangerCategory;// 乘客类型，0代表婴儿，1代表儿童，2代表学生，3代表成人
	private double baggageWeight;// 行李质量，单位公斤

	public Passanger() {

	}

	public void setAirlineType(int airlineType) {
		this.airlineType = airlineType;
	}

	public int getAirLineType() {
		return airlineType;
	}

	public void setCabinClass(int cabinClass) {
		this.cabinClass = cabinClass;
	}

	public int getCabinClass() {
		return cabinClass;
	}

	public void setPassangerCategory(int pc) {
		this.passangerCategory = pc;
	}

	public int getPassangerCatagory() {
		return passangerCategory;
	}

	public void setBaggageWeight(double baggageWeight) {
		this.baggageWeight = baggageWeight;
	}

	public double getBaggageWeight() {
		return baggageWeight;
	}

	public void setEconomyClassFare(double economyClassFare) {
		this.economyClassFare = economyClassFare;
	}

	public double getEconomyClassFare() {
		return economyClassFare;
	}

	public String toString() {
		StringBuffer returnBuffer = new StringBuffer(0);
		returnBuffer.append("航班类型：");
		if (airlineType == 0) {
			returnBuffer.append("0--国内航班；");
		} else {
			returnBuffer.append("1--国际航班；");
		}
		returnBuffer.append("座舱等级：");
		if (cabinClass == 0) {
			returnBuffer.append("0--经济舱；");
		} else if (cabinClass == 1) {
			returnBuffer.append("1--商务舱；");
		} else {
			returnBuffer.append("2--头等舱；");
		}
		returnBuffer.append("经济舱票价：");
		returnBuffer.append(economyClassFare);
		returnBuffer.append("；乘客类型：");
		if (passangerCategory == 0) {
			returnBuffer.append("0--婴儿；");
		} else if (passangerCategory == 1) {
			returnBuffer.append("1--儿童；");
		} else if (passangerCategory == 2) {
			returnBuffer.append("2--学生；");
		} else {
			returnBuffer.append("3--成人；");
		}
		returnBuffer.append("乘客携带行李总重量：");
		returnBuffer.append(baggageWeight);
		returnBuffer.append("公斤");
		return returnBuffer.toString();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Passanger)) 
			return false;
		Passanger p = (Passanger) obj;
		if (this.getAirLineType() != p.getAirLineType())
			return false;
		if (this.getCabinClass() != p.getCabinClass())
			return false;
		if (this.getPassangerCatagory() != p.getPassangerCatagory())
			return false;
		if (Double.doubleToLongBits(this.getEconomyClassFare()) != 
			Double.doubleToLongBits(p.getEconomyClassFare()))
			return false;
		if (Double.doubleToLongBits(this.getBaggageWeight())
				!= Double.doubleToLongBits(p.getBaggageWeight()))
			return false;
		return true;
	}
	
	public int hashCode() {
		int result = 17;
		result = result * 37 + airlineType;
		result = result * 37 + cabinClass;
		long toLong = Double.doubleToLongBits(economyClassFare);
		result = result * 37 + (int) (toLong ^ (toLong >>> 32));
		result = result * 37 + passangerCategory;
		toLong = Double.doubleToLongBits(baggageWeight);
		result = result * 37 + (int) (toLong ^ (toLong >>> 32));
		
		return result;
	}
}
