package com.komi.sliderdemo;

/**
 * Created by Komi on 2016-02-04.
 */
public enum Demo {


    SAMPLE_ACTIVITY(SampleActivity.class.getName(), R.string.sample_activity) {
        @Override
        public boolean isActivity() {
            return true;
        }
    },

    EXTENDS_ACTIVITY(ExtendsActivity.class.getName(), R.string.extend_activity) {
        @Override
        public boolean isActivity() {
            return true;
        }
    },

    ADAPTER_ACTIVITY(AdapterActivity.class.getName(), R.string.adapter_activity) {
        @Override
        public boolean isActivity() {
            return true;
        }
    },

    SAMPLE_FRAGMENT(SampleFragment.class.getName(), R.string.sample_fragment),

    EXTENDS_FRAGMENT(ExtendsFragment.class.getName(), R.string.extend_fragment),

    ADAPTER_FRAGMENT(AdapterFragment.class.getName(), R.string.adapter_fragment);

    public final String className;
    public final int titleResId;


    Demo(String className, int titleResId) {
        this.className = className;
        this.titleResId = titleResId;
    }

    public boolean isActivity() {
        return false;
    }


}
