package cn.edu.ustb.www.aviationconsignment.model;

/**
 * 行李管理类。提供四个方法：<br>
 * ·查询课随身携带的行李数量<br>
 * ·查询可随身携带的行李重量<br>
 * ·查询可免费托运的行李重量<br>
 * ·根据所有行李重量计算所需托运费
 * 
 * @author zzq
 * 
 */
public class BaggageController {

	private Passanger p;

	public BaggageController(Passanger t) {
		this.setPassanger(t);
	}

	public void setPassanger(Passanger p) {
		this.p = p;
	}

	public Passanger getPassanger() {
		return p;
	}

	/**
	 * 查询可随身携带的行李数量
	 * 
	 * @return
	 */
	public int takeAlongNum() {
		int num = 1;
		// 婴儿不可随身携带行李
		if (p.getPassangerCatagory() == Passanger.INFANT)
			num = 0;
		// 国内航班头等舱的乘客可携带 2 件行李，其余乘客可携带 1 件
		else if (p.getAirLineType() == Passanger.DOMESTIC
				&& p.getCabinClass() == Passanger.FIRST_CLASS)
			num = 2;
		return num;
	}

	/**
	 * 查询可随身携带的行李重量
	 * 
	 * @return
	 */
	public double takeAlongWeight() {
		double weight = 0.0;
		// 婴儿不可随身携带行李
		if (p.getPassangerCatagory() != Passanger.INFANT) {
			// 国内航班，随身携带5kg
			if (p.getAirLineType() == Passanger.DOMESTIC)
				weight = 5.0;
			// 国际航班，可随身携带7kg
			else if (p.getAirLineType() == Passanger.INTERNATIONAL)
				weight = 7.0;
		}
		return weight;
	}

	/**
	 * 查询可免费托运的行李重量
	 * 
	 * @return
	 */
	public double freeConsignWeight() {
		double fcw = 0.0;
		// 婴儿不可免费托运行李
		if (p.getPassangerCatagory() != Passanger.INFANT) {
			switch (p.getCabinClass()) {
			// 头等舱乘客免费托运40kg
			case Passanger.FIRST_CLASS: {
				fcw = 40.0;
				break;
			}
				// 商务舱乘客免费托运30kg
			case Passanger.BUSINESS_CLASS: {
				fcw = 30.0;
				break;
			}
				// 经济舱
			case Passanger.TOURIST_CLASS: {
				if (p.getAirLineType() == Passanger.INTERNATIONAL && p.getPassangerCatagory() == Passanger.STUDENT)
					fcw = 30.0;// 在国际航班经济舱持学生护照的乘客，享受可免费托运行李的重量为30公斤
				else
					fcw = 20.0;// 其他情况下，经济舱乘客享受20公斤免费托运
				break;
			}
			}
		}
		return fcw;
	}

	/**
	 * 计算乘客所带行李总共花费
	 * 
	 * @return
	 */
	public double consignmentFare() {
		double cf = 0.0;
		double additionalWeight; // 额外重量
		additionalWeight = p.getBaggageWeight() - takeAlongWeight()
				- freeConsignWeight();
		if (additionalWeight > 0.0) {
			cf = additionalWeight * p.getEconomyClassFare() * 0.015;
		}
		if (cf < 0.0)
			cf = 0.0;
		return cf;
	}

}
