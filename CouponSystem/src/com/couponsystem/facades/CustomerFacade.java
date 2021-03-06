package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CustomerDAO;
import com.couponsystem.dao.CustomerDBDAO;
import com.couponsystem.dao.CouponDBDAO;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.helper.classes.ClientBucket;

public class CustomerFacade implements CouponSystemClientFacade {

	private CustomerDAO customerDao;
	private CouponDAO couponDao;

	public CustomerFacade() {

		customerDao = new CustomerDBDAO();
		couponDao = new CouponDBDAO();

	}

	@Override
	public ClientBucket login(String name, String password,
			ClientType clientType) throws CouponSystemException {

		Customer customer = customerDao.login(name, password);

		if (customer == null) {
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error19"));
		}

		ClientBucket clientBucket = new ClientBucket(new CustomerFacade(),
				customer);

		return clientBucket;

	}

	public Customer getCustomer(long customerId) throws CouponSystemException {
		return customerDao.getCustomer(customerId);
	}
	
	public void updateCustomer(Customer customer) {
		// A customer may update his own info.
		customerDao.updateCustomer(customer);
	}

	public Collection<Coupon> getCoupons(long customerId)
			throws CouponSystemException {
		return customerDao.getCoupons(customerId);
	}

	public void purchaseCoupon(Coupon coupon, long customerId)
			throws CouponSystemException {
		customerDao.purchaseCoupon(coupon, customerId);
	}

	public Collection<Coupon> getAllPurchasedCoupons(long customerId)
			throws CouponSystemException {
		// get them for this precise user
		return customerDao.getAllPurchasedCoupons(customerId);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, long customerId)
			throws CouponSystemException {
		// get them for this precise user
		return customerDao.getAllPurchasedCouponsByType(couponType, customerId);
	}

	// TODO: write method
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			long customerId) throws CouponSystemException {
		// get them for this precise user

		// "SELECT cust.CUST_NAME, cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = 3 AND cpn.PRICE = 20 INNER JOIN customer cust ON cust.ID = cc.CUST_ID"

		return customerDao.getAllPurchasedCouponsByPrice(price, customerId);
	}

}
