pragma solidity ^0.4.0;

// playcoinflip to receive result

contract CoinFlip {
    uint private requiredBet;
    string result;
    address private owner;
    
    function CoinFlip () public payable {
        owner = msg.sender;
        requiredBet = msg.value;
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
    
    //result is decided automatically each play
    function playCoinFlip (string choice) public payable {
        address me = this;
        if(msg.value > me.balance ){
            emit fail("too much ether sent!");
            msg.sender.transfer(msg.value);
        } else {
            headsOrTails();
            if(keccak256(result) == keccak256(choice)){
                emit resultInfo("you won!!!",msg.sender,msg.value+requiredBet);
                msg.sender.transfer(msg.value *2);
            }
            else {
                emit resultInfo("you lose!!!",msg.sender,msg.value+requiredBet);
            }
            //selfdestruct(owner);
        }
    }
}
