pragma solidity ^0.4.0;

// playcoinflip to receive result

contract CoinFlip {
    uint private requiredBet;
    uint public deposit;
    string result;
    address private owner;
    
    function CoinFlip (uint bet) public payable {
        requiredBet = bet;
        owner = msg.sender;
        if( msg.value != requiredBet)
            selfdestruct(owner);
        else
            deposit = msg.value;
    }
    
    event resultInfo(string message, address user, uint value);
    event fail(string message);
    
    //generates a number either 1 or 0
    //1 is designated heads
    function headsOrTails () public returns(string) {
       
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
        if(msg.value != requiredBet) {
            emit fail("Not enough ether sent!");
        }
        else {
            headsOrTails();
            if(keccak256(result) == keccak256(choice)){
                emit resultInfo("you won!!!",msg.sender,msg.value+deposit);
                msg.sender.transfer(msg.value + deposit);
            }
            else {
                emit resultInfo("you lose!!!",msg.sender,msg.value+deposit);
                owner.transfer(msg.value + deposit);
            }
            selfdestruct(owner);
        }
    }
}
