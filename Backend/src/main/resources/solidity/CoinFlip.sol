pragma solidity ^0.4.0;

//setchoice -> playcoinflip to receive result

contract CoinFlip {

    string choice = "heads";
    string result;
    event sendStuff(address);
    address private owner;
    



    modifier isOwner {
        require(owner == msg.sender);_;
    }
    
    function CoinFlip () {
        owner = msg.sender;
    }
    
    function () payable isOwner {
        sendStuff(msg.sender);
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
    function setChoice (string newChoice) public returns(string) {
        choice = newChoice;
        return choice;
    }
    
    //getter functions
    function getChoice () public returns (string) {
        return choice;
    }
    
    function getResult () public returns (string) {
        return result;
    }
    
    function getBal () public returns (uint) {
        return address(this).balance;
    }
    
    
    
    
    
    //set choice before calling this, results in win or lose
    //result is decided automatically each play
    function playCoinFlip () public returns (string) {
        headsOrTails();
        
        if(keccak256(result) == keccak256(choice)){
            //receive funds here for winning
            return "you win!";
        }
        else {
            //lose funds for losing
            return "you lose";
            
        }
    }
    

}












