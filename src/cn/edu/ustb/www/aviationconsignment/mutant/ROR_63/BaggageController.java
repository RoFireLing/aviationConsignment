// This is a mutant program.
// Author : ysma

package cn.edu.ustb.www.aviationconsignment.mutant.ROR_63;

import cn.edu.ustb.www.aviationconsignment.model.*;


public class BaggageController
{

    private cn.edu.ustb.www.aviationconsignment.model.Passanger p;

    public BaggageController( cn.edu.ustb.www.aviationconsignment.model.Passanger t )
    {
        this.setPassanger( t );
    }

    public  void setPassanger( cn.edu.ustb.www.aviationconsignment.model.Passanger p )
    {
        this.p = p;
    }

    public  cn.edu.ustb.www.aviationconsignment.model.Passanger getPassanger()
    {
        return p;
    }

    public  int takeAlongNum()
    {
        int num = 1;
        if (p.getPassangerCatagory() == Passanger.INFANT) {
            num = 0;
        } else {
            if (p.getAirLineType() == Passanger.DOMESTIC && p.getCabinClass() == Passanger.FIRST_CLASS) {
                num = 2;
            }
        }
        return num;
    }

    public  double takeAlongWeight()
    {
        double weight = 0.0;
        if (p.getPassangerCatagory() != Passanger.INFANT) {
            if (p.getAirLineType() == Passanger.DOMESTIC) {
                weight = 5.0;
            } else {
                if (p.getAirLineType() == Passanger.INTERNATIONAL) {
                    weight = 7.0;
                }
            }
        }
        return weight;
    }

    public  double freeConsignWeight()
    {
        double fcw = 0.0;
        if (p.getPassangerCatagory() != Passanger.INFANT) {
            switch (p.getCabinClass()) {
            case Passanger.FIRST_CLASS :
                {
                    fcw = 40.0;
                    break;
                }

            case Passanger.BUSINESS_CLASS :
                {
                    fcw = 30.0;
                    break;
                }

            case Passanger.TOURIST_CLASS :
                {
                    if (p.getAirLineType() == Passanger.INTERNATIONAL && false) {
                        fcw = 30.0;
                    } else {
                        fcw = 20.0;
                    }
                    break;
                }

            }
        }
        return fcw;
    }

    public  double consignmentFare()
    {
        double cf = 0.0;
        double additionalWeight;
        additionalWeight = p.getBaggageWeight() - takeAlongWeight() - freeConsignWeight();
        if (additionalWeight > 0.0) {
            cf = additionalWeight * p.getEconomyClassFare() * 0.015;
        }
        if (cf < 0.0) {
            cf = 0.0;
        }
        return cf;
    }

}
