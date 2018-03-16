package org.web3j.backend;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import java.math.BigDecimal;

public class Application
{
    public static void main(String[] args) throws Exception
    {
	new Application().run();
    }

    private void run() throws Exception
    {
	System.out.println("lmao");
	Web3j web3 = Web3j.build(new HttpService());
	
	Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();

	if(!clientVersion.hasError())
	    {
		System.out.println("Client is running version: " + clientVersion.getWeb3ClientVersion());
	    }

	Credentials credentials = WalletUtils.loadCredentials("", "/home/shak/.ethereum/rinkeby/keystore/UTC--2018-02-15T03-29-56.001144798Z--f8ef3cd122bedb0648d18ab224601ead6eec2fab");
	System.out.println("credentials loades");
	TransactionReceipt transactionReciept = Transfer.sendFunds(web3, credentials, "0x428a3dc11f986e11f003c4251c6a7305ab0d5894", BigDecimal.valueOf(1.0), Convert.Unit.ETHER).send();
    }
}
