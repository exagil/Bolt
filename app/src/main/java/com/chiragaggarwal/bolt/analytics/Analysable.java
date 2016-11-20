package com.chiragaggarwal.bolt.analytics;

import android.os.Bundle;

public interface Analysable {
    String eventName();

    Bundle serialized();
}
