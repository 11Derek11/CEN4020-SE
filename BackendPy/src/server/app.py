import os
import sys
import json
import web3
import time
import codecs
import subprocess
import hashlib
import uuid

from binascii import hexlify
from ethereum import utils
from web3 import Web3, IPCProvider
from solc import compile_source
from web3.contract import ConciseContract

from flask import Flask, request, jsonify, session
from sqlalchemy.orm import sessionmaker
from tabledef import *


app = Flask(__name__)
w3 = Web3(IPCProvider('/home/shak/.ethereum/rinkeby/geth.ipc'))
engine = create_engine('sqlite:///tutorial.db', echo=True, connect_args={'check_same_thread': False})

with open('resources/CoinFlip.sol', 'r') as myFile:
    coinflip = myFile.read()

compiled_sol = compile_source(coinflip)
contract_interface = compiled_sol['<stdin>:CoinFlip']

psn = web3.personal.Personal(w3)
psn.unlockAccount(w3.eth.accounts[0], 'sapha582')

contract = w3.eth.contract(abi=contract_interface['abi'], bytecode=contract_interface['bin'])

contract_address = '0xA4FD29131bB40cd8a3BF527886D05EF90350447b'



# root
@app.route("/")
def index():
    """
    this is a root dir of my server
    :return: str
    """
    return "This is root!!!!"


# GET
@app.route('/users/<user>')
def hello_user(user):
    """
    this serves as a demo purpose
    :param user:
    :return: str
    """
    return "Hello %s!" % user


@app.route('/playcoinflip', methods=['POST'])
def playcoinflip():
    json = request.get_json()
    print(json)

    if  (not 'choice' in json) or (not'bet' in json) or (not'wallet' in session):
        return jsonify({'error' : 'Wrong input.'})

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

    wallet = w3.personal.newAccount(hashlib.sha512((json['password'] + salt).encode()).hexdigest())
    user = User(json['username'], hashlib.sha512((json['password'] + salt).encode()).hexdigest(), wallet, salt)
    s.add(user)
    s.commit()

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


    salt = s.query(User).filter(User.username.in_([json['username']])).first().salt

    query = s.query(User).filter(User.username.in_([json['username']]), User.password.in_([hashlib.sha512((json['password'] + salt).encode()).hexdigest()]))
    result = query.first()
 
    if result:
        session['wallet'] = result.wallet
        return jsonify({'success': 'you loggined user ' + json['username']})

    return jsonify({'unsuccessfull':'lamo'})


@app.route("/logout", methods=['POST'])
def logout():
    json = request.get_json()
    session.pop('wallet', None)
    return jsonify({'success' : 'You successfully logged out'}) 

# running web app in local machine
if __name__ == '__main__':
    app.secret_key = os.urandom(12)
    app.config['SESSION_TYPE'] = 'filesystem'

    app.run(host='159.65.161.113', port=6000, threaded=True)
