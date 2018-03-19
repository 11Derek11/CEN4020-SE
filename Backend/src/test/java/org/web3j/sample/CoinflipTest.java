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

public class CoinflipTest{
	@Test
	public void testCoinflip()
	{
		try{
			Web3j web3 = Web3j.build(new HttpService());
			String lose = "you lose!!!";
			String win = "you won!!!";
			String check = "";
			byte[] mesg;

			Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();

			BigInteger initial = BigInteger.valueOf(1000000000);
			initial = initial.pow(2);

			if(!clientVersion.hasError())
			{
				System.out.println("Client is running version: " + clientVersion.getWeb3ClientVersion());
			}

			Credentials credentials = WalletUtils.loadCredentials("", "/home/shak/.ethereum/rinkeby/keystore/UTC--2018-02-15T03-29-56.001144798Z--f8ef3cd122bedb0648d18ab224601ead6eec2fab");
		

			System.out.println("Credentials Loaded.");
			CoinFlip contract = CoinFlip.load("0xbf77E081F2fA4F51adDB19b43f8E870AAa1714e8",web3,credentials,ManagedTransaction.GAS_PRICE,Contract.GAS_LIMIT);

			TransactionReceipt trans = contract.playCoinFlip("heads", initial.multiply(BigInteger.valueOf(1))).send();

			for(Log log : trans.getLogs())
			{
				check = log.getData();
				System.out.println(log.getData());
			}

			mesg = DatatypeConverter.parseHexBinary(check.substring(2));
			check = new String(mesg);


			System.out.println(check);
			assertTrue(check.contains(win) || check.contains(lose));
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
