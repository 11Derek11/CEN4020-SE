import datetime
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from tabledef import *
import hashlib
 
engine = create_engine('sqlite:///tutorial.db', echo=True)
 
# create a Session
Session = sessionmaker(bind=engine)
session = Session()

query = session.query(User).all()
for x in query:
	print(str(x.id) + ' ' + x.username + ' ' + x.password + ' ' + x.wallet)
#user = User('shak', hashlib.sha512('sapha582'.encode()).hexdigest(), '0xf8ef3cd122bedb0648d18ab224601ead6eec2fab')
#session.add(user)
 
# commit the record the database
session.commit()
 
