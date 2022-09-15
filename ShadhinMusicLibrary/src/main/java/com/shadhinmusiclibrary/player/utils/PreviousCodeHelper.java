/*
package com.shadhinmusiclibrary.player.utils;

import com.gm.shadhin.data.rest.RestRepository;

import timber.log.Timber;

public class PreviousCodeHelper {
    private RestRepository restRepository;

    public PreviousCodeHelper(RestRepository restRepository) {
        this.restRepository = restRepository;
    }

    public void eventLogger(int timeCountSecond, String sTime, String eTime, String id, String type, boolean isCount, String userPlayListId) {

        Timber.i("TrackingCall: Main %s", " " + id + " " + type + " userplaylist " + userPlayListId);

        if (sTime == null || eTime == null || id == null || type == null) {
            return;
        }

        boolean isPD = false;

        String count;
        if (isCount) {
            count = "1";
        } else {
            count = "0";
        }

        if (!type.startsWith("@")) { // if not pdl
            type = type.trim().toLowerCase();

            if (type.startsWith("pd")) {
                isPD = true;
                type = type.replaceAll("pd", "");
            }
        } else {
            isPD = true;
            type = type.toLowerCase().replace("@pd", "");
        }
        restRepository.postSongPlayCountS(isPD, id, type, count, timeCountSecond, sTime, eTime, userPlayListId);

    }
}
*/
