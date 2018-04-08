import os
import sys
import json
import web3
import time
import codecs
import subprocess
import hashlib
import uuid
import random
import re

from binascii import hexlify
from ethereum import utils
from web3 import Web3, IPCProvider
from solc import compile_source
from web3.contract import ConciseContract

from flask import Flask, request, jsonify, session
from sqlalchemy.orm import sessionmaker
from tabledef import *

def isPrime(n):
    if n==2 or n==3: return True
    if n%2==0 or n<2: return False
    for i in range(3,int(n**0.5)+1,2):   # only odd numbers
        if n%i==0:
            return False    

    return True


app = Flask(__name__)
w3 = Web3(IPCProvider('/home/shak/.ethereum/rinkeby/geth.ipc'))
engine = create_engine('sqlite:///tutorial.db', echo=True, connect_args={'check_same_thread': False})

with open('resources/CoinFlip.sol', 'r') as myFile:
    coinflip = myFile.read()

with open('resources/Random.sol', 'r') as myFile:
    randomCon = myFile.read()

compiled_ran = compile_source(randomCon)
random_interface = compiled_ran['<stdin>:Random']

compiled_sol = compile_source(coinflip)
contract_interface = compiled_sol['<stdin>:CoinFlip']

psn = web3.personal.Personal(w3)
#psn.unlockAccount(w3.eth.accounts[0], 'sapha582')

randomContract = w3.eth.contract(abi=random_interface['abi'], bytecode=random_interface['bin'])
contract = w3.eth.contract(abi=contract_interface['abi'], bytecode=contract_interface['bin'])

contract_address = '0xA4FD29131bB40cd8a3BF527886D05EF90350447b'



@app.route('/getusercontracts', methods=['POST'])
def getUserContracts():
    json = request.get_json()
    print(json)

    Session = sessionmaker(bind=engine)
    s = Session()

    if (not 'contract' in json) or (not 'wallet' in session):
        return jsonify({'error' : 'user not logged in, or wrong input'})

    query = s.query(UserContracts).filter_by(contract=json['contract']).all()

    response = {}
    for x in query:
        response[x.id] = [x.user, x.contract, x.winner]

    return jsonify(response)



@app.route('/getcontracts', methods=['GET'])
def getContracts():
    Session = sessionmaker(bind=engine)
    s = Session()

    if (not 'wallet' in session):
        return jsonify({'error' : 'user not logged in'})

    query = s.query(Contracts).all()

    response = {}
    for x in query:
        response[x.id] = [x.owner, x.contract, x.initialBet, x.players, x.date]

    return jsonify(response)


@app.route('/getuserinfo', methods=['GET'])
def getUserInfo():
    Session = sessionmaker(bind=engine)
    s = Session()

    if(not 'wallet' in session):
        return jsonify({'error' : 'user not logged in'})

    return jsonify({'success' : str(w3.fromWei(w3.eth.getBalance(session['wallet']), 'ether'))})




@app.route('/startlottery', methods=['POST'])
def deployLottery():
    json = request.get_json()
    print(json)

    Session = sessionmaker(bind=engine)
    s = Session()

    if  (not 'players' in json) or (not 'bet' in json) or (not 'wallet' in session):
        return jsonify({'error' : 'Wrong input or you have not logged in.'})

    query = s.query(User).filter_by(wallet=session['wallet']).first()

    psn.unlockAccount(session['wallet'], query.password)

    primes = [i for i in range(int(json['players']), int(json['players']) + 1000) if isPrime(i)]
    tx_hash = randomContract.deploy(transaction={'from': session['wallet']}, args=(int(json['players']), random.choice(primes), w3.toWei(json['bet'], 'ether')))


    currBlock = w3.eth.blockNumber
    print(currBlock)
    while(True):
        try:
            tx_receipt = w3.eth.getTransactionReceipt(tx_hash)
            contractr_address = tx_receipt['contractAddress']
            break
        except TypeError:
            print(w3.eth.blockNumber)
            time.sleep(2)

    print(session['wallet'])
    print(query.password)

    instance = w3.eth.contract(contractr_address, abi=random_interface['abi'])
    handle = instance.transact({'from':session['wallet'], 'value' : w3.toWei(json['bet'], 'ether')})
    tx_hash = handle.addUser()

    newCon = Contracts(session['wallet'], contractr_address, json['bet'], json['players'])
    s.add(newCon)
    s.commit()

    newUserCon = UserContracts(session['wallet'], contractr_address)
    s.add(newUserCon)
    s.commit()

    psn.lockAccount(session['wallet'])

    return jsonify({'success' : contractr_address})


@app.route('/playlottery', methods=['POST'])
def playLottery():
    json = request.get_json()
    print(json)

    Session = sessionmaker(bind=engine)
    s = Session()


    if  (not 'contract' in json) or (not 'wallet' in session):
        return jsonify({'error' : 'Wrong input or you have not logged in.'})

    query = s.query(User).filter_by(wallet=session['wallet']).first()
    query2 = s.query(Contracts).filter_by(contract=json['contract']).first()

    psn.unlockAccount(session['wallet'], query.password)

    instance = w3.eth.contract(json['contract'], abi=random_interface['abi'])
    handle = instance.transact({'from':session['wallet'], 'value' : w3.toWei(str(query2.initialBet), 'ether')})
    tx_hash = handle.addUser()

    while(True):
        try:
            retVal = w3.eth.getTransactionReceipt(tx_hash)
            data = retVal['logs']
            break
        except TypeError:
            print(w3.eth.blockNumber)
            time.sleep(2)

    try:
        data = retVal['logs'][0]['data']
    except IndexError:
        print(retVal)
        return jsonify({'error' : 'There has been a problem with the blockchain.'})


    dataStr = str(codecs.decode(data[2:], 'hex'))


    print(data)
    address = data[26:66]
    address = '0x' + address

    newUserCon = UserContracts(session['wallet'], json['contract'])
    s.add(newUserCon)
    s.commit()

    if 'true' in dataStr:
        querys = s.query(UserContracts).filter_by(user=address, contract=json['contract']).first()
        querys.winner = True
        s.commit()


    psn.lockAccount(session['wallet'])

    return jsonify({'success' : 'whatever dude'})




@app.route('/playcoinflip', methods=['POST'])
def playcoinflip():
    json = request.get_json()
    print(json)

    if  (not 'choice' in json) or (not'bet' in json) or (not 'wallet' in session):
        return jsonify({'error' : 'Wrong input or you have not logged in.'})

    psn = web3.personal.Personal(w3)
    psn.unlockAccount(session['wallet'], json['password'])
    #psn.unlockAccount(w3.eth.accounts[0], 'sapha582')
    abi = contract_interface['abi']

    contract_instance = w3.eth.contract(contract_address, abi=abi)
    handle = contract_instance.transact({'from':session['wallet'], 'value':w3.toWei(json['bet'], 'ether')})
    tx_hash = handle.playCoinFlip(json['choice'])

    currBlock = w3.eth.blockNumber
    print(currBlock)

    while(True):
        try:
            retVal = w3.eth.getTransactionReceipt(tx_hash)
            data = retVal['logs']
            break
        except TypeError:
            print(w3.eth.blockNumber)
            time.sleep(1)

    try:
        data = retVal['logs'][0]['data']
    except IndexError:
        return jsonify(retVal)

    retVal = str(codecs.decode(data[2:], 'hex'))

    resultString = ''
    if 'you lose!!!' in retVal:
        resultString = 'you lose!!!'
    else:
        resultString = 'you win!!!'

    return jsonify({'success' : resultString})

@app.route('/register', methods=['POST'])
def do_registration():
    json = request.get_json()
    print(json)

    salt = uuid.uuid4().hex

    Session = sessionmaker(bind=engine)
    s = Session()

    query = s.query(User).filter(User.username.in_([json['username']]))

    if query.first():
        return jsonify({'error' : 'User already exists'})

    myPass = hashlib.sha512((json['password'] + salt).encode()).hexdigest()
    wallet = w3.personal.newAccount(myPass)
    user = User(json['username'], myPass, wallet, salt)
    s.add(user)
    s.commit()

    psn.unlockAccount(w3.eth.accounts[0], 'sapha582')
    sendCoin = w3.eth.sendTransaction({'from' : w3.eth.accounts[0], 'to' : wallet, 'value' : w3.toWei('1', 'ether')})
    psn.lockAccount(w3.eth.accounts[0])

    return jsonify({'success' : 'You successfully added a user', 'wallet' : wallet})


# POST
@app.route('/login', methods=['POST'])
def get_text_prediction():
    """
    predicts requested text whether it is ham or spam
    :return: json
    """
    json = request.get_json()
    print(json)

    Session = sessionmaker(bind=engine)
    s = Session()


    try:
        salt = s.query(User).filter(User.username.in_([json['username']])).first().salt
    except AttributeError:
        return jsonify({'unsuccessfull' : 'Too unsalty'})

    query = s.query(User).filter(User.username.in_([json['username']]), User.password.in_([hashlib.sha512((json['password'] + salt).encode()).hexdigest()]))
    result = query.first()
 
    if result:
        session['wallet'] = result.wallet
        return jsonify({'success': result.wallet})

    return jsonify({'unsuccessfull':'lamo'})


@app.route("/logout", methods=['POST'])
def logout():
    json = request.get_json()
    session.pop('wallet', None)
    return jsonify({'success' : 'You successfully logged out'}) 

# running web app in local machine
if __name__ == '__main__':
    app.secret_key = os.urandom(12)
    #app.config['SESSION_TYPE'] = 'filesystem'

    app.run(host='159.65.161.113', port=5000, threaded=True)
