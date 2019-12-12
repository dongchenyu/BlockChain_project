package asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import channel.client.TransactionSucCallback;
import channel.event.filter.EventLogPushWithDecodeCallback;
import web3j.abi.EventEncoder;
import web3j.abi.TypeReference;
import web3j.abi.datatypes.Event;
import web3j.abi.datatypes.Function;
import web3j.abi.datatypes.Type;
import web3j.abi.datatypes.Utf8String;
import web3j.abi.datatypes.generated.Int256;
import web3j.abi.datatypes.generated.Uint256;
import web3j.crypto.Credentials;
import web3j.protocol.Web3j;
import web3j.protocol.core.RemoteCall;
import web3j.protocol.core.methods.response.Log;
import web3j.protocol.core.methods.response.TransactionReceipt;
import web3j.tuples.generated.Tuple2;
import web3j.tx.Contract;
import web3j.tx.TransactionManager;
import web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Asset extends Contract {
    public static String BINARY = "608060405260006001553480156200001657600080fd5b506200003062000036640100000000026401000000009004565b62000191565b600061100190508073ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001848103835260078152602001807f6163636f756e74000000000000000000000000000000000000000000000000008152506020018481038252600b8152602001807f61737365745f76616c75650000000000000000000000000000000000000000008152506020019350505050602060405180830381600087803b1580156200015057600080fd5b505af115801562000165573d6000803e3d6000fd5b505050506040513d60208110156200017c57600080fd5b81019080805190602001909291905050505050565b61241180620001a16000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063274dc37d14610072578063579ec0a3146101a45780639b80b050146102d6578063ea87152b146103a3578063fcd7e3c11461042a575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506104ae565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561016957808201518184015260208101905061014e565b50505050905090810190601f1680156101965780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101b057600080fd5b5061025b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061066a565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561029b578082015181840152602081019050610280565b50505050905090810190601f1680156102c85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156102e257600080fd5b5061038d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610844565b6040518082815260200191505060405180910390f35b3480156103af57600080fd5b50610414600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506118d8565b6040518082815260200191505060405180910390f35b34801561043657600080fd5b50610491600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611de7565b604051808381526020018281526020019250505060405180910390f35b6060600080905060015481101561062957826000828154811015156104cf57fe5b9060005260206000209060030201600201600082825403925050819055506104f8858585610844565b5082846040518082805190602001908083835b602083101515610530578051825260208201915060208101905060208303925061050b565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020866040518082805190602001908083835b602083101515610593578051825260208201915060208101905060208303925061056e565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f3b15b25f0e5432d84434f1752a6caef1469ca9ee389a9f6582776f169178ba3260405160405180910390a46040805190810160405280600e81526020017f706179207375636365737366756c0000000000000000000000000000000000008152509150610662565b6040805190810160405280600a81526020017f706179206661696c65640000000000000000000000000000000000000000000081525091505b509392505050565b606060008060009150600090506060604051908101604052808781526020018681526020018581525060006001548154811015156106a457fe5b906000526020600020906003020160008201518160000190805190602001906106ce929190612340565b5060208201518160010190805190602001906106eb929190612340565b5060408201518160020155905050600180540160018190555061070f868686610844565b5083856040518082805190602001908083835b6020831015156107475780518252602082019150602081019050602083039250610722565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020876040518082805190602001908083835b6020831015156107aa5780518252602082019150602081019050602083039250610785565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f10acfba2f4ab22201fb1d0eeb44602d4b6256109d94faf2e035d695e087a1cb960405160405180910390a46040805190810160405280600c81526020017f6c6f616e20737563636573730000000000000000000000000000000000000000815250925050509392505050565b600080600080600080600080600080975060009650600095506000945061086a8c611de7565b80975081985050506000871415156109a5577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9750898b6040518082805190602001908083835b6020831015156108d657805182526020820191506020810190506020830392506108b1565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b6020831015156109395780518252602082019150602081019050602083039250610914565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798506118c9565b6109ae8b611de7565b8096508198505050600087141515610ae9577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe9750898b6040518082805190602001908083835b602083101515610a1a57805182526020820191506020810190506020830392506109f5565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b602083101515610a7d5780518252602082019150602081019050602083039250610a58565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798506118c9565b89861015610c1a577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd9750898b6040518082805190602001908083835b602083101515610b4b5780518252602082019150602081019050602083039250610b26565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b602083101515610bae5780518252602082019150602081019050602083039250610b89565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798506118c9565b848a86011015610d4d577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc9750898b6040518082805190602001908083835b602083101515610c7e5780518252602082019150602081019050602083039250610c59565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b602083101515610ce15780518252602082019150602081019050602083039250610cbc565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798506118c9565b610d55612251565b93508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610dbb57600080fd5b505af1158015610dcf573d6000803e3d6000fd5b505050506040513d6020811015610de557600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b5168d6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610eb8578082015181840152602081019050610e9d565b50505050905090810190601f168015610ee55780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610f0557600080fd5b505af1158015610f19573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16632ef8ba748b88036040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610fc757600080fd5b505af1158015610fdb573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18d858773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561106157600080fd5b505af1158015611075573d6000803e3d6000fd5b505050506040513d602081101561108b57600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b8381101561116b578082015181840152602081019050611150565b50505050905090810190601f1680156111985780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156111b957600080fd5b505af11580156111cd573d6000803e3d6000fd5b505050506040513d60208110156111e357600080fd5b81019080805190602001909291905050509150600182141515611329577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb9750898b6040518082805190602001908083835b60208310151561125a5780518252602082019150602081019050602083039250611235565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b6020831015156112bd5780518252602082019150602081019050602083039250611298565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798506118c9565b8373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561138d57600080fd5b505af11580156113a1573d6000803e3d6000fd5b505050506040513d60208110156113b757600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663e942b5168c6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561148a57808201518184015260208101905061146f565b50505050905090810190601f1680156114b75780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156114d757600080fd5b505af11580156114eb573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba748b87016040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561159957600080fd5b505af11580156115ad573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18c838773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561163357600080fd5b505af1158015611647573d6000803e3d6000fd5b505050506040513d602081101561165d57600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b8381101561173d578082015181840152602081019050611722565b50505050905090810190601f16801561176a5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561178b57600080fd5b505af115801561179f573d6000803e3d6000fd5b505050506040513d60208110156117b557600080fd5b810190808051906020019092919050505050898b6040518082805190602001908083835b6020831015156117fe57805182526020820191506020810190506020830392506117d9565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390208d6040518082805190602001908083835b602083101515611861578051825260208201915060208101905060208303925061183c565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd8b6040518082815260200191505060405180910390a48798505b50505050505050509392505050565b600080600080600080600080955060009450600093506118f789611de7565b8095508196505050600085141515611d1957611911612251565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561197757600080fd5b505af115801561198b573d6000803e3d6000fd5b505050506040513d60208110156119a157600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611a74578082015181840152602081019050611a59565b50505050905090810190601f168015611aa15780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611ac157600080fd5b505af1158015611ad5573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74896040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611b8157600080fd5b505af1158015611b95573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac368a846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611c54578082015181840152602081019050611c39565b50505050905090810190601f168015611c815780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611ca157600080fd5b505af1158015611cb5573d6000803e3d6000fd5b505050506040513d6020811015611ccb57600080fd5b810190808051906020019092919050505090506001811415611cf05760009550611d14565b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe95505b611d3d565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff95505b87896040518082805190602001908083835b602083101515611d745780518252602082019150602081019050602083039250611d4f565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f91c95f04198617c60eaf2180fbca88fc192db379657df0e412a9f7dd4ebbe95d886040518082815260200191505060405180910390a385965050505050505092915050565b600080600080600080611df8612251565b93508373ffffffffffffffffffffffffffffffffffffffff1663e8434e39888673ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611e7b57600080fd5b505af1158015611e8f573d6000803e3d6000fd5b505050506040513d6020811015611ea557600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611f53578082015181840152602081019050611f38565b50505050905090810190601f168015611f805780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611fa057600080fd5b505af1158015611fb4573d6000803e3d6000fd5b505050506040513d6020811015611fca57600080fd5b81019080805190602001909291905050509250600091508273ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561204557600080fd5b505af1158015612059573d6000803e3d6000fd5b505050506040513d602081101561206f57600080fd5b8101908080519060200190929190505050600014156120b6577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8281915095509550612248565b8273ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561212657600080fd5b505af115801561213a573d6000803e3d6000fd5b505050506040513d602081101561215057600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600b8152602001807f61737365745f76616c7565000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561220557600080fd5b505af1158015612219573d6000803e3d6000fd5b505050506040513d602081101561222f57600080fd5b8101908080519060200190929190505050819150955095505b50505050915091565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156122fb57600080fd5b505af115801561230f573d6000803e3d6000fd5b505050506040513d602081101561232557600080fd5b81019080805190602001909291905050509050809250505090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061238157805160ff19168380011785556123af565b828001600101855582156123af579182015b828111156123ae578251825591602001919060010190612393565b5b5090506123bc91906123c0565b5090565b6123e291905b808211156123de5760008160009055506001016123c6565b5090565b905600a165627a7a72305820e3d9a016afa41256f377ca36e203cf3e1b353f9bb4e33626bca6552aa65ed16c0029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"pay_fac\",\"type\":\"string\"},{\"name\":\"get_fac\",\"type\":\"string\"},{\"name\":\"money\",\"type\":\"uint256\"}],\"name\":\"payoff\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"sender\",\"type\":\"string\"},{\"name\":\"receiver\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"loan\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"from_account\",\"type\":\"string\"},{\"name\":\"to_account\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"},{\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":true,\"name\":\"account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"RegisterEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":true,\"name\":\"from_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"to_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"TransferEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"from_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"to_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"LoanEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"from_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"to_account\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"PayEvent\",\"type\":\"event\"}]";

    public static final String FUNC_PAYOFF = "payoff";

    public static final String FUNC_LOAN = "loan";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_SELECT = "select";

    public static final Event REGISTEREVENT_EVENT = new Event("RegisterEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event TRANSFEREVENT_EVENT = new Event("TransferEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event LOANEVENT_EVENT = new Event("LoanEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event PAYEVENT_EVENT = new Event("PayEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> payoff(String pay_fac, String get_fac, BigInteger money) {
        final Function function = new Function(
                FUNC_PAYOFF, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(pay_fac), 
                new web3j.abi.datatypes.Utf8String(get_fac), 
                new web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void payoff(String pay_fac, String get_fac, BigInteger money, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PAYOFF, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(pay_fac), 
                new web3j.abi.datatypes.Utf8String(get_fac), 
                new web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String payoffSeq(String pay_fac, String get_fac, BigInteger money) {
        final Function function = new Function(
                FUNC_PAYOFF, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(pay_fac), 
                new web3j.abi.datatypes.Utf8String(get_fac), 
                new web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> loan(String sender, String receiver, BigInteger amount) {
        final Function function = new Function(
                FUNC_LOAN, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(sender), 
                new web3j.abi.datatypes.Utf8String(receiver), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void loan(String sender, String receiver, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_LOAN, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(sender), 
                new web3j.abi.datatypes.Utf8String(receiver), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String loanSeq(String sender, String receiver, BigInteger amount) {
        final Function function = new Function(
                FUNC_LOAN, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(sender), 
                new web3j.abi.datatypes.Utf8String(receiver), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> transfer(String from_account, String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(from_account), 
                new web3j.abi.datatypes.Utf8String(to_account), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transfer(String from_account, String to_account, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(from_account), 
                new web3j.abi.datatypes.Utf8String(to_account), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferSeq(String from_account, String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(from_account), 
                new web3j.abi.datatypes.Utf8String(to_account), 
                new web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> register(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(account), 
                new web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void register(String account, BigInteger asset_value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(account), 
                new web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerSeq(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(account), 
                new web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> select(String account) {
        final Function function = new Function(FUNC_SELECT, 
                Arrays.<Type>asList(new web3j.abi.datatypes.Utf8String(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public List<RegisterEventEventResponse> getRegisterEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREVENT_EVENT, transactionReceipt);
        ArrayList<RegisterEventEventResponse> responses = new ArrayList<RegisterEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.asset_value = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerRegisterEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REGISTEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerRegisterEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REGISTEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<TransferEventEventResponse> getTransferEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFEREVENT_EVENT, transactionReceipt);
        ArrayList<TransferEventEventResponse> responses = new ArrayList<TransferEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventEventResponse typedResponse = new TransferEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from_account = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to_account = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTransferEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerTransferEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<LoanEventEventResponse> getLoanEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOANEVENT_EVENT, transactionReceipt);
        ArrayList<LoanEventEventResponse> responses = new ArrayList<LoanEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LoanEventEventResponse typedResponse = new LoanEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from_account = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to_account = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLoanEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOANEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLoanEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOANEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<PayEventEventResponse> getPayEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYEVENT_EVENT, transactionReceipt);
        ArrayList<PayEventEventResponse> responses = new ArrayList<PayEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PayEventEventResponse typedResponse = new PayEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from_account = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to_account = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerPayEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerPayEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RegisterEventEventResponse {
        public Log log;

        public byte[] account;

        public BigInteger asset_value;

        public BigInteger ret;
    }

    public static class TransferEventEventResponse {
        public Log log;

        public byte[] from_account;

        public byte[] to_account;

        public BigInteger amount;

        public BigInteger ret;
    }

    public static class LoanEventEventResponse {
        public Log log;

        public byte[] from_account;

        public byte[] to_account;

        public BigInteger amount;
    }

    public static class PayEventEventResponse {
        public Log log;

        public byte[] from_account;

        public byte[] to_account;

        public BigInteger amount;
    }
}