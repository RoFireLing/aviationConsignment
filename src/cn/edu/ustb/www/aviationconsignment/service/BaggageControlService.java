package cn.edu.ustb.www.aviationconsignment.service;

import cn.edu.ustb.www.aviationconsignment.model.BaggageController;
import cn.edu.ustb.www.aviationconsignment.model.Passanger;

public class BaggageControlService {
	
	private Passanger p;
	
	public BaggageControlService() {
		p = new Passanger();
	}
	
	private void setP(int airlineType, int cabinClass, double touristClassFare, int type, double baggageWeight) {
		p.setAirlineType(airlineType);
		p.setCabinClass(cabinClass);
		p.setEconomyClassFare(touristClassFare);
		p.setPassangerCategory(type);
		p.setBaggageWeight(baggageWeight);
	}
	
	/**
	 * 根据乘客详细信息，查询可随身携带的行李数量
	 * @param airlineType 航班类型
	 * @param cabinClass 客舱等级
	 * @param touristClassFare 经济舱票价
	 * @param type 乘客类型（成人，学生，儿童，婴儿）
	 * @param baggageWeight 行李重量
	 * @return
	 */
	public int takeAlongNum(int airlineType, int cabinClass, double touristClassFare, int type, double baggageWeight)  {
		//设定Passanger类型对象的各属性
		setP(airlineType, cabinClass, touristClassFare, type, baggageWeight);
		
		//利用Passanger类型对象构造BaggageController类型对象
		BaggageController bc = new BaggageController(p);
		
		//调用BaggageContorller类型对象的方法获取可随身携带的行李件数
		return bc.takeAlongNum();
	}
	
	/**
	 * 
	 * @param airlineType
	 * @param cabinClass
	 * @param touristClassFare
	 * @param type
	 * @param baggageWeight
	 * @return
	 */
	public double takeAlongWeight(int airlineType, int cabinClass, double touristClassFare, int type, double baggageWeight) {
		//设定Passanger类型对象的各属性
		setP(airlineType, cabinClass, touristClassFare, type, baggageWeight);
		
		//利用Passanger类型对象构造BaggageController类型对象
		BaggageController bc = new BaggageController(p);
		
		//调用BaggageContorller类型对象的方法获取可随身携带的行李重量
		return bc.takeAlongWeight();
	}
	
	/**
	 * 
	 * @param airlineType
	 * @param cabinClass
	 * @param touristClassFare
	 * @param type
	 * @param baggageWeight
	 * @return
	 */
	public double freeConsignWeight(int airlineType, int cabinClass, double touristClassFare, int type, double baggageWeight) {
		//设定Passanger类型对象的各属性
		setP(airlineType, cabinClass, touristClassFare, type, baggageWeight);
		
		//利用Passanger类型对象构造BaggageController类型对象
		BaggageController bc = new BaggageController(p);
		
		//调用BaggageContorller类型对象的方法获取可免费托运的行李重量
		return bc.freeConsignWeight();
	}
	
	/**
	 * 
	 * @param airlineType
	 * @param cabinClass
	 * @param touristClassFare
	 * @param type
	 * @param baggageWeight
	 * @return
	 */
	public double coinsignFee(int airlineType, int cabinClass, double touristClassFare, int type, double baggageWeight) {
		//设定Passanger类型对象的各属性
		setP(airlineType, cabinClass, touristClassFare, type, baggageWeight);
		
		//利用Passanger类型对象构造BaggageController类型对象
		BaggageController bc = new BaggageController(p);
		
		//调用BaggageContorller类型对象的方法获取可免费托运的行李重量
		return bc.consignmentFare();
	}

}
