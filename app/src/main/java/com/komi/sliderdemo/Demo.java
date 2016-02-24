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

    CUSTOM_ACTIVITY(CustomActivity.class.getName(), R.string.custom_activity) {
        @Override
        public boolean isActivity() {
            return true;
        }
    },

    XML_ACTIVITY(XmlActivity.class.getName(), R.string.xml_activity) {
        @Override
        public boolean isActivity() {
            return true;
        }
    },

    SAMPLE_FRAGMENT(SampleFragment.class.getName(), R.string.sample_fragment),

    LINK_LISTVIEW_FRAGMENT(LinkListViewFragment.class.getName(), R.string.link_lv_fragment),

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
