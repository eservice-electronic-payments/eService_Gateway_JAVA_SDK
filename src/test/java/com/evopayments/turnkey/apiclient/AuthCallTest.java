package com.evopayments.turnkey.apiclient;


import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.code.CountryCode;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class AuthCallTest extends BaseTest {

	/**
	 * successful case.
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		final Map<String, String> authParams = new HashMap<>();
		super.addCommonParams(authParams);
		authParams.put("amount", "20.0");
		authParams.put("customerId", tokenizeCall.getString("customerId"));
		authParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		authParams.put("specinCreditCardCVV", "111");

		final AuthCall call = new AuthCall(config, authParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);

	}

	/**
	 *  able to get the token.
	 */
	@Test
	public void redirectionResponseTestCall() {

		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		final Map<String, String> authParams = new HashMap<>();
		super.addCommonParams(authParams);
		authParams.put("amount", "20.0");
		authParams.put("customerId", tokenizeCall.getString("customerId"));
		authParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		authParams.put("specinCreditCardCVV", "111");

		final AuthCall call = new AuthCall(config, authParams, null);

		JSONObject result = call.execute();
		Assert.assertEquals(result.get("result"), "redirection");


	}

	/**
	 * RequiredParamException test (intentionally left out param).
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("amount", "20.0");
			inputParams.put("channel", Channel.ECOM.getCode());
			inputParams.put("country", CountryCode.PL.getCode());
			// inputParams.put("currency", CurrencyCode.EUR.getCode());
			inputParams.put("paymentSolutionId", "500");
			inputParams.put("customerId", "8Gii57iYNVSd27xnFZzR");

			final AuthCall call = new AuthCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("currency").toString(),e.getMessage());
			throw e;

		}
	}
}
