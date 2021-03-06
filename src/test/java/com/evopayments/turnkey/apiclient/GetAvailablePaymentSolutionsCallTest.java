package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GetAvailablePaymentSolutionsCallTest extends BaseTest{

	/**
	 * successful case.
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		super.addCommonParams(inputParams);
		inputParams.put("country", "PL");
		inputParams.put("currency", "PLN");

		final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams, null);
		JSONObject result = call.execute();


		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
		Assert.assertEquals(result.get("result"), "success");

	}

	/**
	 * RequiredParamException test (intentionally left out param).
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("country", "FR");
			// inputParams.put("currency", "EUR"); // intentionally left out

			final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("currency").toString(),e.getMessage());
			throw e;

		}
	}

}
