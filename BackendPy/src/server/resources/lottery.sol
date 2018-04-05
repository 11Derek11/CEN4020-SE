pragma solidity ^0.4.0;

contract Random
{
    address owner;
    uint maxPlayers;
    uint randomNumber;
    uint requireBet;
    uint num = 0;
    bool state = false; 
    mapping(uint => address) public players;
    
    function Random(uint n, uint k, uint amount) public {
        owner = msg.sender;
        maxPlayers = n;
        randomNumber = k;
        requireBet = amount;
    }
    
    function addUser() public payable {
        require(msg.sender == owner);
        require(msg.value >= requireBet);
        ++num;
        players[num] = msg.sender;
        
        if(num  == maxPlayers ) selfdestruct(players[(randomNumber % maxPlayers) + 1]);
    }
}