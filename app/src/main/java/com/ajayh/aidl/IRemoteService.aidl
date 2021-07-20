// IRemoteService.aidl
package com.ajayh.aidl;

import android.content.res.Configuration;

// Declare any non-default types here with import statements

/** Example service interface */
interface IRemoteService {
    /** Request the process ID of this service, to do evil things with it. */
    int getPid();

    /** Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getPhoneOrientation();
}