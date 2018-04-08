from sqlalchemy import *
from sqlalchemy import create_engine, ForeignKey
from sqlalchemy import Column, Date, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, backref
import time
 
engine = create_engine('sqlite:///tutorial.db', echo=True)
Base = declarative_base()
 
########################################################################
class User(Base):
    """"""
    __tablename__ = "users"
 
    id = Column(Integer, primary_key=True)
    username = Column(String)
    password = Column(String)
    wallet = Column(String)
    salt = Column(String)
 
    #----------------------------------------------------------------------
    def __init__(self, username, password, wallet, salt):
        """"""
        self.username = username
        self.password = password
        self.wallet = wallet
        self.salt = salt

class Contracts(Base):
    """"""
    __tablename__ = "contracts"
 
    id = Column(Integer, primary_key=True)
    owner = Column(String, ForeignKey(User.wallet))
    contract = Column(String)
    initialBet = Column(Integer)
    players = Column(Integer)
    date = Column(String)
 
    #----------------------------------------------------------------------
    def __init__(self, owner, contract, initialBet, players):
        """"""
        self.owner = owner
        self.contract = contract
        self.initialBet = initialBet
        self.players = players
        self.date = ''.join(str(time.time()).split('.'))

class UserContracts(Base):
    """"""
    __tablename__ = "usercontract"
 
    id = Column(Integer, primary_key=True)
    user = Column(String)
    contract = Column(String, ForeignKey(Contracts.contract))
    winner = Column(Boolean)

 
    #----------------------------------------------------------------------
    def __init__(self, user, contract, winner=False):
        """"""
        self.user = user
        self.contract = contract
        self.winner = winner
 
# create tables
Base.metadata.create_all(engine)
