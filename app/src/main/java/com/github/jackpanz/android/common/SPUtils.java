package com.github.jackpanz.android.common;

import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SPUtils {

    private static SPUtils DEFAULT_SP = null;

    protected SharedPreferences sharedPreferences = null;

    public static final String PREFERENCES_DEFAULT = "PREFERENCES_DEFAULT";

    public static SPUtils newInstance() {
        if (DEFAULT_SP == null) {
            DEFAULT_SP = new SPUtils();
            DEFAULT_SP.sharedPreferences = ContextUtils.getContext().getSharedPreferences(PREFERENCES_DEFAULT, 0);
        }
        return DEFAULT_SP;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SPUtils save(String key, Object value) {
        put(key, value, true);
        return this;
    }

    public SPUtils apple(String key, Object value) {
        put(key, value, false);
        return this;
    }

    public SPUtils clear(String key, Object value) {

        return this;
    }

    private SPUtils put(String key, Object value, boolean isCommit) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        Class type = value.getClass();
        if (type == Boolean.class) {
            editor.putBoolean(key, (Boolean) value);
        } else if (type == Integer.class) {
            editor.putInt(key, (Integer) value);
        } else if (type == Long.class) {
            editor.putLong(key, (Long) value);
        } else if (type == Float.class) {
            editor.putFloat(key, (Float) value);
        } else if (type == String.class) {
            editor.putString(key, (String) value);
        } else if (type instanceof Serializable) {
            putSerializable(key, (Serializable) value, editor);
        }
        if (isCommit) {
            editor.commit();
        }
        return this;
    }

    public Serializable getSerializable(String key) {
        return getSerializable(key, null);
    }

    public Serializable getSerializable(String key, Serializable defValue) {
        Serializable serializable = null;
        String productBase64 = getSharedPreferences().getString(key, null);
        if (productBase64 != null) {
            byte[] base64 = Base64.decode(productBase64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            try {
                ObjectInputStream bis = new ObjectInputStream(bais);
                serializable = (Serializable) bis.readObject();
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }
        return serializable == null ? defValue : serializable;
    }

    private SPUtils putSerializable(String key, Serializable serializable, SharedPreferences.Editor editor) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(serializable);
            String value = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public String getString(final String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public void delete(String key) {
        remove(key, true);
    }

    public void remove(String key, boolean isCommit) {
        if (isCommit) {
            getSharedPreferences().edit().remove(key).commit();
        } else {
            getSharedPreferences().edit().remove(key).apply();
        }
    }

}
