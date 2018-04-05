#!/bin/bash/python3

import os
import sys
import json
import web3
import time
import codecs
import subprocess

from binascii import hexlify
from ethereum import utils
from web3 import Web3, HTTPProvider, TestRPCProvider
from solc import compile_source
from web3.contract import ConciseContract

with open('resources/CoinFlip.sol', 'r') as myFile:
	coinflip = myFile.read()

compiled_sol = compile_source(coinflip)
contract_interface = compiled_sol['<stdin>:CoinFlip']

w3 = Web3(HTTPProvider('http://localhost:8545'))
psn = web3.personal.Personal(w3)
psn.unlockAccount(w3.eth.accounts[0], 'sapha582')

contract = w3.eth.contract(abi=contract_interface['abi'], bytecode=contract_interface['bin'])


######################################################################
# DEPLOY CONTRACT
tx_hash = contract.deploy(transaction={'from': w3.eth.accounts[0], 'value': Web3.toWei('15', 'ether')})

currBlock = w3.eth.blockNumber
print(currBlock)
while(True):
	try:
		tx_receipt = w3.eth.getTransactionReceipt(tx_hash)
		contract_address = tx_receipt['contractAddress']
		break
	except TypeError:
		time.sleep(2)
print(contract_address)
######################################################################


######################################################################
#INTERACT WITH CONTRACT

#contract_address = '0x821Ca8e7D43c8a5e2f7266f9A0Da0028C4a7626B'
#abi = contract_interface['abi']
#
#contract_instance = w3.eth.contract(contract_address, abi=abi)
#handle = contract_instance.transact({'from':w3.eth.accounts[0], 'value':w3.toWei('1', 'ether')})
#tx_hash = handle.playCoinFlip('heads')
#
##handle = contract_instance.transact({'from':w3.eth.accounts[0]})
##tx_hash = handle.cashOut()
#
#currBlock = w3.eth.blockNumber
#print(currBlock)
#while(w3.eth.blockNumber <= currBlock+2):
#	print(w3.eth.blockNumber)
#	time.sleep(2)
#
#retVal = w3.eth.getTransactionReceipt(tx_hash)['logs'][0]['data']
#print(codecs.decode(retVal[2:], 'hex'))
##retVal = w3.eth.getTransactionReceipt(tx_hash)
##print(retVal)
#####################################################################

#####################################################################
#CREATING AN ACCOUNT 

#print(w3.personal.newAccount('lmao'))

#####################################################################