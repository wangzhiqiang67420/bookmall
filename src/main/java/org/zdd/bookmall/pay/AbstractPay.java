package org.zdd.bookmall.pay;


import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.common.utils.BSResultUtil;

public abstract class AbstractPay implements IPayService {

    @Override
    public BSResult pay(PayContext payContext) throws Exception {
        prepareAndPay(payContext);
        afterPay(payContext);
        return BSResultUtil.success();
    }


}
