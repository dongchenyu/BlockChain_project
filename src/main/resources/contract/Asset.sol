pragma solidity ^0.4.25;

import "./Table.sol";

contract Asset {
    event RegisterEvent(int256 ret, string indexed account, uint256 indexed asset_value);
    event TransferEvent(int256 ret, string indexed from_account, string indexed to_account, uint256 indexed amount);
    event LoanEvent(string indexed from_account, string indexed to_account, uint256 indexed amount);
    event PayEvent(string indexed from_account, string indexed to_account, uint256 indexed amount);
    struct receipt_rent {
        string renter;
	    string borrower;
	    uint mount; 
    } 
    struct rent {
	    address borrower;
	    int mount; 
	    string reason;
        bool believe;
    } 
    struct borrow {
	    address renter; 
	    int mount; 
	    string reason;
        bool believe;
    } 
    receipt_rent[] renter;
    uint pos=0;
    constructor() public {
        createTable();
    }

    function createTable() private {
        TableFactory tf = TableFactory(0x1001); 
        tf.createTable("t_asset", "account", "asset_value");
    }

    function openTable() private returns(Table) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_asset");
        return table;
    }
    function select(string account) public constant returns(int256, uint256) {
        // 打开表
        Table table = openTable();
        // 查询
        Entries entries = table.select(account, table.newCondition());
        uint256 asset_value = 0;
        if (0 == uint256(entries.size())) {
            return (-1, asset_value);
        } else {
            Entry entry = entries.get(0);
            return (0, uint256(entry.getInt("asset_value")));
        }
    }
    function register(string account, uint256 asset_value) public returns(int256){
        int256 ret_code = 0;
        int256 ret= 0;
        uint256 temp_asset_value = 0;
        (ret, temp_asset_value) = select(account);
        if(ret != 0) {
            Table table = openTable();
            Entry entry = table.newEntry();
            entry.set("account", account);
            entry.set("asset", int256(asset_value));
            int count = table.insert(account, entry);
            if (count == 1) {
                ret_code = 0;
            } else {
                ret_code = -2;
            }
        } else {
            ret_code = -1;
        }

        emit RegisterEvent(ret_code, account, asset_value);

        return ret_code;
    }
    function transfer(string from_account, string to_account, uint256 amount) public returns(int256) {
        int ret_code = 0;
        int256 ret = 0;
        uint256 from_asset_value = 0;
        uint256 to_asset_value = 0;
        (ret, from_asset_value) = select(from_account);
        if(ret != 0) {
            ret_code = -1;
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;

        }
        (ret, to_asset_value) = select(to_account);
        if(ret != 0) {
            ret_code = -2;
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        }

        if(from_asset_value < amount) {
            ret_code = -3;
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        } 
        Table table = openTable();

        Entry entry0 = table.newEntry();
        entry0.set("account", from_account);
        entry0.set("asset_value", int256(from_asset_value - amount));
        int count = table.update(from_account, entry0, table.newCondition());
        Entry entry1 = table.newEntry();
        entry1.set("account", to_account);
        entry1.set("asset_value", int256(to_asset_value + amount));
        table.update(to_account, entry1, table.newCondition());

        emit TransferEvent(ret_code, from_account, to_account, amount);

        return ret_code;
    }
    function loan(string sender,string receiver, uint amount)returns(string){
        bool rent_appear=false;
        bool borrow_appear=false;
        int256 ret = 0;
        rent[pos]=receipt_rent(sender,receiver,amount);
        pos=pos+1;
        for(uint i=0;i<len_rent;i++){
            if(rent[receiver][i].borrower==sender){
                rent[receiver][i].mount+=amount;
                rent_appear=true;
                break;
            }
        }
        if(rent_appear==false){
            receipt_rent memory rece=receipt_rent(sender,amount,reason,believe);
            receipt_borrow memory send=receipt_borrow(receiver,amount,reason,believe);
            rent[receiver].push(rece);
            borrow[sender].push(send);
        }
        emit LoanEvent(sender, receiver, amount);
        return "loan success";
    }
    function payoff(string pay_fac,string get_fac,uint money)public returns(string){
        for(uint i=0;i<pos;i++){
            if(pay_fac==rent[i].borrower && get_fac==rent[i].follower){
                rent[i].mount-=money;
                (ret, payfac_mount) = select(pay_fac);
                (ret, getfac_mount) = select(get_fac);
                Entry entry0 = table.newEntry();
                entry0.set("account", pay_fac);
                entry0.set("asset_value", int256(payfac_mount - amount));
                int count = table.update(pay_fac, entry0, table.newCondition());
                Entry entry1 = table.newEntry();
                entry1.set("account", get_fac);
                entry1.set("asset_value", int256(getfac_mount + amount));
                table.update(get_fac, entry1, table.newCondition());
                emit PayEvent(pay_fac, get_fac, money);
                return "pay successful";
            }
        }
        return "pay failed";
    }
    function partsend(address sender,address receiver, address divider,int tosend) public{
        int needsend=tosend;
        uint len=rent[sender].length;
        for(uint i=0;i<len;i++){
            address debt=rent[sender][i].borrower;
            if(rent[sender][i].mount>needsend){
                int left=rent[sender][i].mount-needsend;
                rent[sender][i].mount=left;
                for(uint j=0;j<borrow[debt].length;j++){
                    if(sender==borrow[debt][j].renter){
                        if(borrow[debt][j].believe==false){
                            continue;
                        }
                        else{
                            borrow[debt][j].mount=left;
                            receipt_rent memory rece=receipt_rent(debt,needsend,reason,true);
                            rent[receiver].push(rece);
                            receipt_borrow memory send=receipt_borrow(receiver,needsend,reason,true);
                            borrow[debt].push(send);
                        }
                    }
                }
            }
            else{
                rent[sender][i].mount=0;
                for(uint j1=0;j1<borrow[debt].length;j1++){
                    if(borrow[debt][j].believe==false){
                            continue;
                    }
                    if(sender==borrow[debt][j1].renter){
                        borrow[debt][j1].renter=receiver;
                    }
                }
            }
        }
    }
    function all_borrow(address borrower,address renter) public returns(int){ 
        int sum_money=0;
        for(uint i=0;i<borrow[borrower].length;i++){
            if(borrow[borrower][i].renter==renter){
                sum_money+=borrow[borrower][i].mount;
            }
        }
        return sum_money;
    }
    function all_rent(address renter,address borrower) public returns(int){ 
        int sum_money=0;
        for(uint i=0;i<rent[renter].length;i++){
            if(rent[renter][i].borrower==borrower){
                sum_money+=rent[renter][i].mount;
            }
        }
        return sum_money;
    }
    function num_borrow(address factory) public returns(uint){
        return borrow[factory].length;
    }
    function num_rent(address factory) public returns(uint){
        return rent[factory].length;
    }
    function merge_borrow(address borrower,address renter)public{
        uint len=borrow[borrower].length;
        uint pos;
        int money=0;
        for(uint i=0;i<len;i++){
            if(borrow[borrower][i].renter==renter){
                pos=i;
                break;
            }
        }
        for(uint j=0;j<len;j++){
            if(borrow[borrower][j].renter==renter){
                money+=borrow[borrower][j].mount;
            }
        }
        for(uint k=0;k<len;k++){
            if(borrow[borrower][k].renter==renter){
                if(pos==k){
                    borrow[borrower][k].mount=money;
                }
                else{
                    borrow[borrower][k].mount=0;
                }
            }
        }
    }
    function merge_rent(address renter,address borrower)public{
        uint len=rent[renter].length;
        uint pos;
        int money=0;
        for(uint i=0;i<len;i++){
            if(rent[renter][i].borrower==borrower){
                pos=i;
                break;
            }
        }
        for(uint j=0;j<len;j++){
            if(rent[renter][j].borrower==borrower){
                money+=rent[renter][j].mount;
            }
        }
        for(uint k=0;k<len;k++){
            if(rent[renter][k].borrower==borrower){
                if(pos==k){
                    rent[renter][k].mount=money;
                }
                else{
                    rent[renter][k].mount=0;
                }
            }
        }
    }
}
