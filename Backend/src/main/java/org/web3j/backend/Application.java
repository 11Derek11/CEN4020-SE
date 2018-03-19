/*

  Shakil Usman and Christopher Holder worked in this part.
 */

package org.web3j.backend;

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


public class Application
{
    public static void main(String[] args) throws Exception
    {
		new Application().run();
    }

    private void run() throws Exception
    {
		System.out.println("Start...............");
		Web3j web3 = Web3j.build(new HttpService());
		
		Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();

		BigInteger initial = BigInteger.valueOf(1000000000);
		initial = initial.pow(2);

		if(!clientVersion.hasError())
		{
			System.out.println("Client is running version: " + clientVersion.getWeb3ClientVersion());
		}

	
		Credentials credentials = WalletUtils.loadCredentials("sapha582", "/home/shak/.ethereum/rinkeby/keystore/UTC--2018-02-15T03-29-56.001144798Z--f8ef3cd122bedb0648d18ab224601ead6eec2fab");
		System.out.println("Credentials Loaded.");
		//CoinFlip contract = CoinFlip.load("0xbf77E081F2fA4F51adDB19b43f8E870AAa1714e8",web3,credentials,ManagedTransaction.GAS_PRICE,Contract.GAS_LIMIT);
		//Lottery contract = Lottery.load("0x3fA93791a8b1948721e7E5b8a86910a9F45BF85d", web3, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		//System.out.println("CoinFlip contract loaded..................");
		//TransactionReceipt transactionReciept = Transfer.sendFunds(web3, credentials, "0xBfa203055d9cf2FAB62e4148e96Cd222770936Ff", BigDecimal.valueOf(5), Convert.Unit.ETHER).send();

		CoinFlip contract = CoinFlip.deploy(web3, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, initial.multiply(BigInteger.valueOf(15))).send();

		//TransactionReceipt trans = contract.playCoinFlip("tails", initial.multiply(BigInteger.valueOf(2))).send();
		//TransactionReceipt trans = contract.init(comb).send();

		/*for(Log log : trans.getLogs())
		{
			System.out.println(log.getData());
		}*/

    }
}

