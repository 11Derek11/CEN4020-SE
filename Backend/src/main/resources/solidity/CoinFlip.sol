pragma solidity ^0.4.0;

//setchoice -> playcoinflip to receive result

contract CoinFlip {
    string result;
    address private owner;
    
    modifier isOwner {
        require(owner == msg.sender);_;
    }
    
    function CoinFlip () public payable {
        owner = msg.sender;
    }
    
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
    
    
    //select "heads" or "tails" for choice
  /*  function setChoice (string newChoice) public returns(string) {
        choice = newChoice;
        return choice;
    }*/
    
    //set choice before calling this, results in win or lose
    //result is decided automatically each play
    function playCoinFlip (string choice) public payable returns (string) {
        headsOrTails();
        if(keccak256(result) == keccak256(choice)){
            msg.sender.transfer(msg.value);
            return "you win!";
        }
        else {
            owner.transfer(msg.value);
            return "you lose";
        }
    }
    

}
