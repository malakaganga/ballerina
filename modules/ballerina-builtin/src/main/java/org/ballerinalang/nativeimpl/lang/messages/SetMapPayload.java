/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.nativeimpl.lang.messages;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BMessage;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.nativeimpl.lang.utils.Constants;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.Attribute;
import org.ballerinalang.natives.annotations.BallerinaAnnotation;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.MapCarbonMessage;
import org.wso2.carbon.messaging.MessageUtil;

/**
 * Native function to set a Map object as the payload of a message.
 * ballerina.model.messages:setMapPayload
 */
@BallerinaFunction(
        packageName = "ballerina.lang.messages",
        functionName = "setMapPayload",
        args = {@Argument(name = "msg", type = TypeKind.MESSAGE),
                @Argument(name = "payload", type = TypeKind.MAP)},
        isPublic = true
)
@BallerinaAnnotation(annotationName = "Description", attributes = {@Attribute(name = "value",
        value = "Sets the message payload using a map object") })
@BallerinaAnnotation(annotationName = "Param", attributes = {@Attribute(name = "msg",
        value = "The current message object") })
@BallerinaAnnotation(annotationName = "Param", attributes = {@Attribute(name = "payload",
        value = "The map payload object") })
public class SetMapPayload extends AbstractNativeFunction {

    private static final Logger log = LoggerFactory.getLogger(SetMapPayload.class);

    @Override
    public BValue[] execute(Context context) {
        BMessage msg = (BMessage) getRefArgument(context, 0);
        BMap<String, ? extends BValue> payload = (BMap) getRefArgument(context, 1);

        CarbonMessage carbonMessage = msg.value();
        MapCarbonMessage mapCarbonMessage;
        if (carbonMessage instanceof MapCarbonMessage) {
            mapCarbonMessage = (MapCarbonMessage) carbonMessage;
            mapCarbonMessage.clearMapPayload();
        } else {
            mapCarbonMessage = MessageUtil.createMapMessageWithoutData(carbonMessage);
        }

        for (String key : payload.keySet()) {
            BValue value = payload.get(key);
            mapCarbonMessage.setValue(key, value.stringValue());
        }

        msg.setValue(mapCarbonMessage);
        // Clone the message without content
        msg.setMessageDataSource(payload);
        msg.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_FORM);
        if (log.isDebugEnabled()) {
            log.debug("Setting new map payload : " + payload.stringValue());
        }
        return VOID_RETURN;
    }
}
