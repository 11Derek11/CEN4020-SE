#!/bin/bash

screen geth --rpcapi personal,db,eth,net,web3 --rpc --rinkeby --cache=512  console
