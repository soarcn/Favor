package com.cocosw.favor;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Set;

/**
 * <p/>
 * Created by kai on 25/09/15.
 */

class MethodInfo {

    static final boolean HAS_RX_JAVA = hasRxJavaOnClasspath();
    private static final String TAG = "Favor";
    final Method method;
    // Method-level details
    final ResponseType responseType;
    final boolean isObservable;
    private final SharedPreferences sp;
    private final String prefix;
    private final boolean allFavor;
    boolean loaded = false;
    Type responseObjectType;
    String key;
    String[] defaultValues = new String[1];
    Object rxPref;
    private Taste taste;
    private boolean commit;
    private Type FavorType;

    MethodInfo(Method method, SharedPreferences sp, String prefix, boolean allFavor) {
        this.method = method;
        this.sp = sp;
        this.prefix = prefix;
        this.allFavor = allFavor;
        responseType = parseResponseType();
        isObservable = (responseType == ResponseType.OBSERVABLE);
    }

    private static Type getParameterUpperBound(ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        for (int i = 0; i < types.length; i++) {
            Type paramType = types[i];
            if (paramType instanceof WildcardType) {
                types[i] = ((WildcardType) paramType).getUpperBounds()[0];
            }
        }
        return types[0];
    }

    private static boolean hasRxJavaOnClasspath() {
        try {
            Class.forName("com.f2prateek.rx.preferences.Preference");
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    private static void checkDefaultValueType(Type reponse, String[] defaultValues) {
        if (reponse == String.class || defaultValues[0] == null) {
        } else if (reponse == int.class || reponse == Integer.class) {
            Integer.parseInt(defaultValues[0]);
        } else if (reponse == long.class || reponse == Long.class) {
            Long.parseLong(defaultValues[0]);
        } else if (reponse == boolean.class || reponse == Boolean.class) {
            Boolean.parseBoolean(defaultValues[0]);
        } else if (reponse == float.class || reponse == Float.class) {
            Float.parseFloat(defaultValues[0]);
        }
    }

    private RuntimeException methodError(String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        return new IllegalArgumentException(
                method.getDeclaringClass().getSimpleName() + "." + method.getName() + ": " + message);
    }

    private RuntimeException parameterError(int index, String message, Object... args) {
        return methodError(message + " (parameter #" + (index + 1) + ")", args);
    }

    synchronized void init() {
        if (loaded) return;
        parseMethodAnnotations();
        loaded = true;
    }

    private void parseMethodAnnotations() {
        for (Annotation methodAnnotation : method.getAnnotations()) {
            Class<? extends Annotation> annotationType = methodAnnotation.annotationType();

            if (annotationType == Favor.class) {
                key = ((Favor) methodAnnotation).value();
                if (key.trim().length() == 0) {
                    key = getKeyFromMethod(method);
                }
                if (!TextUtils.isEmpty(prefix)) {
                    key = prefix + key;
                }
            } else if (annotationType == Default.class) {
                defaultValues = ((Default) methodAnnotation).value();
            } else if (annotationType == Commit.class) {
                commit = true;
            }
        }

        if (allFavor && key == null) {
            key = getKeyFromMethod(method);
            if (!TextUtils.isEmpty(prefix)) {
                key = prefix + key;
            }
        }

        if (responseType == ResponseType.OBSERVABLE) {
            checkDefaultValueType(responseObjectType, defaultValues);
            if (commit) {
                Log.w(TAG, "@Commit will be ignored for RxReference");
            }
            RxSharedPreferences rx = RxSharedPreferences.create(sp);
            if (responseObjectType == String.class) {
                rxPref = rx.getString(key, defaultValues[0]);
            } else if (responseObjectType == Integer.class) {
                rxPref = rx.getInteger(key, defaultValues[0] == null ? null : Integer.valueOf(defaultValues[0]));
            } else if (responseObjectType == Float.class) {
                rxPref = rx.getFloat(key, defaultValues[0] == null ? null : Float.valueOf(defaultValues[0]));
            } else if (responseObjectType == Long.class) {
                rxPref = rx.getLong(key, defaultValues[0] == null ? null : Long.valueOf(defaultValues[0]));
            } else if (responseObjectType == Boolean.class) {
                rxPref = rx.getBoolean(key, defaultValues[0] == null ? null : Boolean.valueOf(defaultValues[0]));
            } else {
//                        Class returnTypeClass = Types.getRawType(returnType);
//                        if (returnTypeClass == Set.class) {
//                            rxPref = rx.getStringSet(key,new HashSet<String>(defaultValues))
//                        }
            }
        } else {
            if (FavorType == String.class) {
                taste = new Taste.StringTaste(sp, key, defaultValues);
            } else if (FavorType == boolean.class) {
                taste = new Taste.BoolTaste(sp, key, defaultValues);
            } else if (FavorType == int.class) {
                taste = new Taste.IntTaste(sp, key, defaultValues);
            } else if (FavorType == float.class) {
                taste = new Taste.FloatTaste(sp, key, defaultValues);
            } else if (FavorType == long.class) {
                taste = new Taste.LongTaste(sp, key, defaultValues);
            } else if (Types.getRawType(FavorType) == Set.class) {
                taste = new Taste.StringSetTaste(sp, key, defaultValues);
            } else if (Serializable.class.isAssignableFrom(Types.getRawType(FavorType))) {
                taste = new Taste.SerializableTaste(sp, key, defaultValues);
            } else {
                taste = new Taste.EmptyTaste(sp, key, defaultValues);
                throw methodError("Unsupported type " + FavorType.toString());
            }
        }
    }

    private String getKeyFromMethod(Method method) {
        String value = method.getName().toLowerCase();
        if (value.startsWith("is") && FavorType == boolean.class) return value.substring(2);
        if (value.startsWith("get")) return value.substring(3);
        if (value.startsWith("set")) return value.substring(3);
        return value;
    }

    private ResponseType parseResponseType() {
        Type returnType = method.getGenericReturnType();
        Type[] parameterTypes = method.getGenericParameterTypes();

        if (parameterTypes.length > 1) {
            throw methodError("%s method has more than one parameter", method.getName());
        }
        Type typeToCheck = null;

        if (parameterTypes.length > 0) {
            typeToCheck = parameterTypes[0];
        }

        boolean hasReturnType = returnType != void.class;

        if (typeToCheck != null && hasReturnType) {
            Log.w(TAG, String.format("Setter method %s should not have return value", method.getName()));
            hasReturnType = false;
            returnType = void.class;
        }

        if (hasReturnType) {
            Class rawReturnType = Types.getRawType(returnType);
//            if (parameterTypes.length > 0) {
//                throw methodError("getter method %s should not have parameter", method.getName());
//            }

            if (HAS_RX_JAVA) {
                if (rawReturnType == Preference.class) {

                    returnType = Types.getSupertype(returnType, rawReturnType, Preference.class);
                    responseObjectType = getParameterUpperBound((ParameterizedType) returnType);
                    return ResponseType.OBSERVABLE;
                }
            }
            responseObjectType = returnType;
        }
        FavorType = (hasReturnType ? returnType : typeToCheck);
        return hasReturnType ? ResponseType.OBJECT : ResponseType.VOID;
    }

    Object get() {
        if (responseType == ResponseType.OBSERVABLE)
            return rxPref;
        else
            return taste.get();
    }

    Object set(Object[] args) {
        if (commit)
            taste.commit(args[0]);
        else
            taste.set(args[0]);
        return null;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "method=" + method +
                ", responseType=" + responseType +
                ", isObservable=" + isObservable +
                ", sp=" + sp +
                ", prefix='" + prefix + '\'' +
                ", allFavor=" + allFavor +
                ", loaded=" + loaded +
                ", responseObjectType=" + responseObjectType +
                ", key='" + key + '\'' +
                ", defaultValues=" + Arrays.toString(defaultValues) +
                ", rxPref=" + rxPref +
                ", taste=" + taste +
                ", commit=" + commit +
                ", FavorType=" + FavorType +
                '}';
    }

    enum ResponseType {
        VOID,
        OBSERVABLE,
        OBJECT
    }
}
