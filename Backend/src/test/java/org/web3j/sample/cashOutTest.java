package org.web3j.sample;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.lang.Exception;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;
import java.lang.String;
import org.web3j.backend.CoinFlip;
import org.web3j.backend.Application;
import java.util.Arrays;
import java.nio.ByteBuffer;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import java.math.BigDecimal;
import java.math.BigInteger;

public class cashOutTest{
	@Test
	public void testCashOut()
	{
		boolean result = false;
		try {
			String expected = "You have cashed out!";
			String check = "";
			byte[] mesg;
			Web3j web3 = Web3j.build(new HttpService());

			Credentials credentials = WalletUtils.loadCredentials("", "/home/shak/.ethereum/rinkeby/keystore/UTC--2018-02-15T03-29-56.001144798Z--f8ef3cd122bedb0648d18ab224601ead6eec2fab");

			BigInteger initial = BigInteger.valueOf(1000000000);
			initial = initial.pow(2);

			CoinFlip contract = CoinFlip.load("0x878AE26Ed2A6eF41ee072B16Ee053377A4033D7b",web3,credentials,ManagedTransaction.GAS_PRICE,Contract.GAS_LIMIT);
			TransactionReceipt trans = contract.cashOut().send();

			for(Log log : trans.getLogs())
			{
				check = log.getData();
				System.out.println(log.getData());
			}

			mesg = DatatypeConverter.parseHexBinary(check.substring(2));
			check = new String(mesg);

			System.out.println(check);

			assertTrue(check.contains(expected)); 

		} catch(Exception e)
		{
			e.printStackTrace();	
		}
	}
}
