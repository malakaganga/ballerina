/*
 *  Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.ballerinalang.nativeimpl.transactions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.util.transactions.TransactionResourceManager;

/**
 * Native function ballerina.transactions.coordinator:commitResourceManagers.
 *
 * @since 0.964.0
 */
@BallerinaFunction(
        packageName = "ballerina.transactions.coordinator",
        functionName = "commitResourceManagers",
        args = {@Argument(name = "transactionId", type = TypeKind.STRING)},
        returnType = {@ReturnType(type = TypeKind.BOOLEAN)}
)
public class CommitResourceManagers extends AbstractNativeFunction {

    public BValue[] execute(Context ctx) {
        String transactionId = getStringArgument(ctx, 0);
        boolean commitSuccessful = TransactionResourceManager.getInstance().notifyCommit(transactionId);
        return getBValues(new BBoolean(commitSuccessful));
    }
}
