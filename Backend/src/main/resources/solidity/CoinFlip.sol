pragma solidity ^0.4.0;

// playcoinflip to receive result

contract CoinFlip {
    string result;
    address private owner;
    
    modifier isOwner {
        require(owner == msg.sender);_;
    }
    
    function CoinFlip () public payable {
        owner = msg.sender;
    }
    
    event resultInfo(string message, address user, uint value);
    
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
    function playCoinFlip (string choice) public payable {
        headsOrTails();
        if(keccak256(result) == keccak256(choice)){
            resultInfo("you won!!!",msg.sender,msg.value*2);
            msg.sender.transfer(msg.value*2);
            
        }
        else {
            resultInfo("you lose!!!",msg.sender,msg.value);
            //owner.transfer(msg.value);
            
        }
    }
    
    function kill() private isOwner {
        resultInfo("contract no longer in use",msg.sender, this.balance);
        selfdestruct(owner);
    }

}
