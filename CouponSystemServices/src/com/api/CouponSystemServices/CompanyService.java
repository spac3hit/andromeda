package com.api.CouponSystemServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.couponsystem.beans.*;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/company_service")
public class CompanyService {

	@POST
	@Path("/create_coupon")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject createCoupon(@FormParam("TITLE") String title,
			@FormParam("START_DATE_YEAR") String startDateYear,
			@FormParam("START_DATE_MONTH") String startDateMonth,
			@FormParam("START_DATE_DAY") String startDateDay,
			@FormParam("END_DATE_YEAR") String endDateYear,
			@FormParam("END_DATE_MONTH") String endDateMonth,
			@FormParam("END_DATE_DAY") String endDateDay,
			@FormParam("AMOUNT") int amount, @FormParam("TYPE") String type,
			@FormParam("MESSAGE") String message,
			@FormParam("PRICE") double price,
			@FormParam("IMAGE") String imagePath) throws JSONException,
			CouponSystemException {

		// AdminFacade adminFacade = null;
		// ClientBucket clientBucket = null;

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		CompanyFacade companyFacade = new CompanyFacade();

		Calendar cal = Calendar.getInstance();
		Date startDate, endDate;

		cal.set(Calendar.YEAR, Integer.parseInt(startDateYear));
		cal.set(Calendar.MONTH, Integer.parseInt(startDateMonth));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateDay));

		startDate = new Date(cal.getTime().getTime());

		cal.set(Calendar.YEAR, Integer.parseInt(endDateYear));
		cal.set(Calendar.MONTH, Integer.parseInt(endDateMonth));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDateDay));

		endDate = new Date(cal.getTime().getTime());

		CouponType couponType = CouponType.valueOf(type);

		// format from HTML date: 2016-05-17
		// SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date ss = dateFormatter.parse();

		Coupon coupon = new Coupon(title, startDate, endDate, amount,
				couponType, message, price, imagePath);
		companyFacade.createCoupon(coupon);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "company created successfully");

		return jsonResponse;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Service: CompanyService";
	}

}