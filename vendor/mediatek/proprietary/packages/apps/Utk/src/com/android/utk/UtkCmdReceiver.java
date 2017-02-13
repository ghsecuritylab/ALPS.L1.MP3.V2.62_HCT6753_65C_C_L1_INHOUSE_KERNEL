/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.utk;

import com.android.internal.telephony.cdma.utk.AppInterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.internal.telephony.cdma.utk.UtkLog;
/**
 * Receiver class to get UTK intents, broadcasted by telephony layer.
 *
 */
public class UtkCmdReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(AppInterface.UTK_CMD_ACTION)) {
            handleCommandMessage(context, intent);
        } else if (action.equals(AppInterface.UTK_SESSION_END_ACTION)) {
            handleSessionEnd(context, intent);
        }
    }

    private void handleCommandMessage(Context context, Intent intent) {
        Bundle args = new Bundle();
        int mPhoneId = intent.getIntExtra(UtkAppService.PHONE_ID,0);
        
        args.putInt(UtkAppService.OPCODE, UtkAppService.OP_CMD);
        args.putInt(UtkAppService.PHONE_ID, mPhoneId);
        args.putParcelable(UtkAppService.CMD_MSG, intent
                .getParcelableExtra("UTK CMD"));
        context.startService(new Intent(context, UtkAppService.class)
                .putExtras(args));
    }

    private void handleSessionEnd(Context context, Intent intent) {
        Bundle args = new Bundle();
        int mPhoneId = intent.getIntExtra(UtkAppService.PHONE_ID,0);

        args.putInt(UtkAppService.OPCODE, UtkAppService.OP_END_SESSION);
        args.putInt(UtkAppService.PHONE_ID, mPhoneId);
        context.startService(new Intent(context, UtkAppService.class)
                .putExtras(args));
    }
}