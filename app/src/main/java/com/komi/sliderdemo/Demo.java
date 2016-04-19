package com.komi.sliderdemo;

/**
 * Created by Komi on 2016-02-04.
 */
public enum Demo {

    SAMPLE_ACTIVITY(SampleActivity.class.getName(), R.string.sample_activity) {},

    CUSTOM_ACTIVITY(CustomActivity.class.getName(), R.string.custom_activity) {},

    XML_ACTIVITY(XmlActivity.class.getName(), R.string.xml_activity) {},

    SAMPLE_FRAGMENT(SampleFragment.class.getName(), R.string.sample_fragment) {
        @Override
        public Type getType() {
            return Type.FRAGMENT;
        }
    },

    LINK_LISTVIEW_FRAGMENT(LinkListViewFragment.class.getName(), R.string.link_lv_fragment) {
        @Override
        public Type getType() {
            return Type.FRAGMENT;
        }
    },


    CUSTOM_SLIDABLE_FRAGMENT(CustomSlidableFragment.class.getName(), R.string.custom_slidable_fragment) {
        @Override
        public Type getType() {
            return Type.FRAGMENT;
        }
    },

    DIALOG_FRAGMENT(SampleDialogFragment.class.getName(), R.string.dialog_fragment) {
        @Override
        public Type getType() {
            return Type.DIALOG_FRAGMENT;
        }
    },
    DIALOG("", R.string.dialog) {
        @Override
        public Type getType() {
            return Type.DIALOG;
        }
    };


    public final String className;
    public final int titleResId;


    Demo(String className, int titleResId) {
        this.className = className;
        this.titleResId = titleResId;
    }

    public Type getType() {
        return Type.ACTIVITY;
    }

    public enum Type {ACTIVITY, FRAGMENT, DIALOG_FRAGMENT,DIALOG}

}
