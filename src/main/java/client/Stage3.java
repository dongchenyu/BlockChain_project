
package org.fisco.bcos.asset.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import slf4j.Logger;
import slf4j.LoggerFactory;
import contract.Asset;
import contract.Asset.RegisterEventEventResponse;
import contract.Asset.TransferEventEventResponse;
import contract.Asset.LoanEventEventResponse;
import contract.Asset.PayEventEventResponse;
import contract.Asset.PartsendEventEventResponse;
import channel.client.Service;
import web3j.crypto.Credentials;
import web3j.crypto.Keys;
import web3j.protocol.Web3j;
import web3j.protocol.channel.ChannelEthereumService;
import web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.tuples.generated.Tuple2;
import web3j.tx.gas.StaticGasProvider;
import springframework.context.ApplicationContext;
import springframework.context.support.ClassPathXmlApplicationContext;
import springframework.core.io.ClassPathResource;
import springframework.core.io.Resource;
public class Stage3 {
	static Logger logger = LoggerFactory.getLogger(AssetClient.class);
	private Web3j web3j;
	private Credentials credentials;
	public Web3j getWeb3j() {
		return web3j;
	}
	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("address", address);
		final Resource contractResource = new ClassPathResource("contract.properties");
		FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
		prop.store(fileOutputStream, "contract address");
	}

	public String loadAssetAddr() throws Exception {
		Properties prop = new Properties();
		final Resource contractResource = new ClassPathResource("contract.properties");
		prop.load(contractResource.getInputStream());

		String contractAddress = prop.getProperty("address");
		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw new Exception(" load Asset contract address failed, please deploy it first. ");
		}
		return contractAddress;
	}

	public void initialize() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		Web3j web3j = Web3j.build(channelEthereumService, 1);
		Credentials credentials = Credentials.create(Keys.createEcKeyPair());
		setCredentials(credentials);
		setWeb3j(web3j);
	}

	private static BigInteger gasPrice = new BigInteger("30000000");
	private static BigInteger gasLimit = new BigInteger("30000000");

	public void deployAssetAndRecordAddr() {

		try {
			Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
			System.out.println(" deploy success, contract address is " + asset.getContractAddress());

			recordAssetAddr(asset.getContractAddress());
		} catch (Exception e) {
			System.out.println(" deploy contract failed ");
		}
	}

	public void queryAssetAmount(String assetAccount) {
		try {
			String contractAddress = loadAssetAddr();

			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			Tuple2<BigInteger, BigInteger> result = asset.select(assetAccount).send();
			if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
				System.out.println(" asset account %s, value %s ", assetAccount, result.getValue2());
			} else {
				System.out.println(" %s asset account is not exist ", assetAccount);
			}
		} 
		catch (Exception e) {
			System.out.println("failed");
		}
	}

	public void registerAssetAccount(String assetAccount, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.register(assetAccount, amount).send();
			List<RegisterEventEventResponse> response = asset.getRegisterEventEvents(receipt);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.println(" register asset account success => asset: %s, value: %s ", assetAccount,amount);
				} else {
					System.out.println(" register asset account failed ");
				}
			} else {
				System.out.println(" event log not found ");
			}
		} 
		catch (Exception e) {
			System.out.println("failed");
		}
	}
	public void loanAsset(String fromAssetAccount, String toAssetAccount, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.loan(fromAssetAccount, toAssetAccount, amount).send();
			List<LoanEventEventResponse> response = asset.getLoanEventEvents(receipt);
			System.out.println(" loan success => from_asset: %s, to_asset: %s, amount: %s ",fromAssetAccount, toAssetAccount, amount);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.println(" loan success => from_asset: %s, to_asset: %s, amount: %s ",
							fromAssetAccount, toAssetAccount, amount);
				} else {
					System.out.println(" loan asset account failed ");
				}
		} 
		catch (Exception e) {
			System.out.println("failed");
		}
	}
	public void payAsset(String fromAssetAccount, String toAssetAccount, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.payoff(fromAssetAccount, toAssetAccount, amount).send();
			List<PayEventEventResponse> response = asset.getPayEventEvents(receipt);
			System.out.println(" pay success => from_asset: %s, to_asset: %s, amount: %s ",fromAssetAccount, toAssetAccount, amount);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.println(" pay success => from_asset: %s, to_asset: %s, amount: %s ",fromAssetAccount, toAssetAccount, amount);
				} else {
					System.out.println(" pay asset account failed, ret code is %s ",response.get(0).ret.toString());
				}
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
			
		} 
		catch (Exception e) {
			System.out.println("failed");
		}
	}
	public void partsendAsset(String payer, String receiver, String divider, BigInteger amount) {
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.partsend(payer, receiver, divider, amount).send();
			List<PayEventEventResponse> response = asset.getPayEventEvents(receipt);
			System.out.println(" send the debt successfully");
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.println(" send the debt successfully");
				} else {
					System.out.println(" send the debt failed");
				}
			}
		} 
		catch (Exception e) {
			System.out.println("failed");
		}
	}
	public static void Wrong() {
		System.out.println("the input is wrong");
		System.exit(0);
	}
	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			Wrong();
		}

		AssetClient client = new AssetClient();
		client.initialize();

		switch (args[0]) {
		case "deploy":
			client.deployAssetAndRecordAddr();
			break;
		case "query":
			if (args.length < 2) {
				Wrong();
			}
			client.queryAssetAmount(args[1]);
			break;
		case "register":
			if (args.length < 3) {
				Wrong();
			}
			client.registerAssetAccount(args[1], new BigInteger(args[2]));
			break;
		case "transfer":
			if (args.length < 4) {
				Wrong();
			}
			client.transferAsset(args[1], args[2], new BigInteger(args[3]));
			break;
			
		case "pay":
			if (args.length < 4) {
				Wrong();
			}
			client.payAsset(args[1], args[2], new BigInteger(args[3]));
			break;
		case "loan":
			if (args.length < 4) {
				Wrong();
			}
			client.loanAsset(args[1], args[2], new BigInteger(args[3]));
			break;
		case "queryloan":
			if (args.length < 3){
				Wrong();
			}
			client.queryloanAsset(args[1], args[2]);
			break;
		case "partsend":
			if (args.length < 5){
				Wrong();
			}
			client.partsendAsset(args[1], args[2],args[3],new BigInteger(args[4]));
			break;
		default: {
			Wrong();
		}
		}

		System.exit(0);
	}
}