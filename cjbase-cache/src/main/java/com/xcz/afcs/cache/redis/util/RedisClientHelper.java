package com.xcz.afcs.cache.redis.util;

import com.xcz.afcs.util.ValueUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

import java.util.HashSet;
import java.util.Set;

public class RedisClientHelper {
    
    public static HostAndPort parseAddressUnit(String address) {
        String host = null;
        int port = -1;
        String[] addressParts = address.split(":");
        if (1 == addressParts.length) {
            host = ValueUtil.getString(addressParts[0]);
            port = Protocol.DEFAULT_PORT;
        } else if (2 == addressParts.length) {
            host = ValueUtil.getString(addressParts[0]);
            port = ValueUtil.getInt(addressParts[1], Protocol.DEFAULT_PORT);
        } else {
            throw new RuntimeException("invalid address: " + address);
        }
        return new HostAndPort(host, port);
    }
    
    public static Set<HostAndPort> parseConnectStringToAddresses(String connectString) {
        String[] connectStringUnits = connectString.split(",");
        Set<HostAndPort> addresses = new HashSet<HostAndPort>();
        for (String connectStringUnit : connectStringUnits) {
            HostAndPort address = parseAddressUnit(connectStringUnit);
            addresses.add(address);
        }
        return addresses;
    }
    
    public static Set<String> parseConnectStringToUnits(String connectString) {
        String[] connectStringUnits = connectString.split(",");
        Set<String> addresses = new HashSet<String>();
        for (String connectStringUnit : connectStringUnits) {
            addresses.add(connectStringUnit);
        }
        return addresses;
    }
    
}
