/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.trident.state;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONValue;


public class JSONOpaqueSerializer implements Serializer<OpaqueValue> {

    @Override
    public byte[] serialize(OpaqueValue obj) {
        List toSer = new ArrayList(3);
        toSer.add(obj.currTxid);
        toSer.add(obj.curr);
        toSer.add(obj.prev);
        try {
            return JSONValue.toJSONString(toSer).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OpaqueValue deserialize(byte[] b) {
        try {
            String s = new String(b, "UTF-8");
            List deser = (List) JSONValue.parse(s);
            return new OpaqueValue((Long) deser.get(0), deser.get(1), deser.get(2));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
