package com.buttian.rpc.client;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
