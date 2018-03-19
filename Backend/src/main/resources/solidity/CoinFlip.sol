//David Scher-Arazi and Andrew Keller

pragma solidity ^0.4.0;

// playcoinflip to receive result

contract CoinFlip {
    string result;
    address private owner;
    
    function CoinFlip () public payable {
        owner = msg.sender;
        this.balance = msg.value;
    }
    
    event resultInfo(string message, address user, uint value);
    event fail(string message);
    
    //generates a number either 1 or 0
    //1 is designated heads
    function headsOrTails () private returns(string) {
       
        uint number = block.timestamp % 2;
        
        //say 1 is heads
        if(number == 1) {
            result = "heads";
        }
        else if(number == 0) {
            result = "tails";
        }
        
        return result;
    }
    
    function deleteContract() public {
        if(msg.sender != owner)
            emit fail("You must be the original contract owner to remove it from the blockchain!");
        else
            selfdestruct(owner);
    }
    
    //result is decided automatically each play
    function playCoinFlip (string choice) public payable {
        address me = this;
        if(msg.value < me.balance ){
            emit fail("not enough wei sent!");
            msg.sender.transfer(msg.value);
        } else {
            headsOrTails();
            if(keccak256(result) == keccak256(choice)){
                emit resultInfo("you won!!!",msg.sender,msg.value+me.balance);
                msg.sender.transfer(msg.value+ me.balance);
            }
            else {
                emit resultInfo("you lose!!!",msg.sender,msg.value+me.balance);
            }
        }
    }
}
