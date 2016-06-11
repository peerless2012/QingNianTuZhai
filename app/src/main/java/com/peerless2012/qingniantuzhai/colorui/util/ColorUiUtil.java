package com.peerless2012.qingniantuzhai.colorui.util;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;


/**
 * Created by chengli on 15/6/10.
 */
public class ColorUiUtil {

    private static final String TAG = "ColorUiUtil";

    public static void doChange(View rootView, Resources.Theme theme, ThemeInfo themeInfo){
        if (rootView instanceof ColorUiInterface) {
            ((ColorUiInterface) rootView).setTheme(theme,themeInfo);
            if (rootView instanceof ViewGroup) {
                int count = ((ViewGroup) rootView).getChildCount();
                for (int i = 0; i < count; i++) {
                    doChange(((ViewGroup) rootView).getChildAt(i), theme,themeInfo);
                }
            }
            if (rootView instanceof AbsListView) {
                try {
                    Field localField = AbsListView.class.getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method localMethod = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear", new Class[0]);
                    localMethod.setAccessible(true);
                    localMethod.invoke(localField.get(rootView), new Object[0]);
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                } catch (InvocationTargetException e5) {
                    e5.printStackTrace();
                }
            }
            if (rootView instanceof RecyclerView) {
                try {
                    Field localField = RecyclerView.class
                            .getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method localMethod = Class.forName(
                            "android.support.v7.widget.RecyclerView$Recycler")
                            .getDeclaredMethod("clear", new Class[0]);
                    localMethod.setAccessible(true);
                    localMethod.invoke(localField.get(rootView), new Object[0]);
                    Log.e("", "### 清空RecyclerView的Recycer ");
                    rootView.invalidate();
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                } catch (InvocationTargetException e5) {
                    e5.printStackTrace();
                }
            }
        } else {
            if (rootView instanceof ViewGroup) {
                int count = ((ViewGroup) rootView).getChildCount();
                for (int i = 0; i < count; i++) {
                    doChange(((ViewGroup) rootView).getChildAt(i), theme,themeInfo);
                }
            }
            if (rootView instanceof AbsListView) {
                try {
                    Field localField = AbsListView.class.getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method localMethod = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear", new Class[0]);
                    localMethod.setAccessible(true);
                    localMethod.invoke(localField.get(rootView), new Object[0]);
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                } catch (InvocationTargetException e5) {
                    e5.printStackTrace();
                }
            }
            if (rootView instanceof RecyclerView) {
                try {
                    Field localField = RecyclerView.class
                            .getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method localMethod = Class.forName(
                            "android.support.v7.widget.RecyclerView$Recycler")
                            .getDeclaredMethod("clear", new Class[0]);
                    localMethod.setAccessible(true);
                    localMethod.invoke(localField.get(rootView), new Object[0]);
                    Log.e("", "### 清空RecyclerView的Recycer ");
                    rootView.invalidate();
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                } catch (InvocationTargetException e5) {
                    e5.printStackTrace();
                }
            }
        }
    }

    /**
     * 切换应用主题
     *
     * @param rootView
     */
    public static ThemeInfo changeTheme(View rootView, Resources.Theme theme) {
        ThemeInfo themeInfo = analysisTheme(rootView,theme);
        doChange(rootView,theme,themeInfo);
        return themeInfo;
    }

    private static ThemeInfo analysisTheme(View rootView, Resources.Theme theme) {
        ThemeInfo themeInfo = new ThemeInfo();
        Class clazz = ThemeInfo.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            AttrTag annotation = field.getAnnotation(AttrTag.class);
            if (annotation != null) {
                String attrName = annotation.tag();
                int attrValue = annotation.value();
                TypedArray typedArray = theme.obtainStyledAttributes(new int[]{
                        attrValue
                });
                int resourceId = typedArray.getResourceId(0, -1);
                field.setAccessible(true);
                try {
                    String methodName = field.getName();
                    Method get_Method = clazz.getMethod("get" + getMethodName(methodName));  //获取getMethod方法
                    Method set_Method = clazz.getMethod("set" + getMethodName(methodName), get_Method.getReturnType());//获得属性set方法
                    set_Method.invoke(themeInfo,resourceId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                typedArray.recycle();
            }
        }
        Log.i(TAG, "analysisTheme: "+themeInfo.toString());
        return themeInfo;
    }

    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

   /* *//**
     * 修改子视图的对应属性
     *
     *//*
    private void changeChildenAttrs(ViewGroup viewGroup, Theme newTheme,
                                    int themeId) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            // 深度遍历
            if (childView instanceof ViewGroup) {
                changeChildenAttrs((ViewGroup) childView, newTheme, themeId);
            }

            // 遍历子元素与要修改的属性,如果相同那么则修改子View的属性
            for (ViewSetter setter : mItemViewSetters) {
                // 每次都要从ViewGroup中查找数据
                setter.mView = findViewById(viewGroup, setter.mViewId);

                Log.e("", "### childView : " + childView + ", id = "
                        + childView.getId());
                Log.e("", "### setter view : " + setter.mView + ", id = "
                        + setter.getViewId());
                if (childView.getId() == setter.getViewId()) {
                    setter.setValue(newTheme, themeId);
                    Log.e("", "@@@ 修改新的属性: " + childView);
                }
            }
        }
    }*/

    private void clearListViewRecyclerBin(View rootView) {
        if (rootView instanceof AbsListView) {
            try {
                Field localField = AbsListView.class
                        .getDeclaredField("mRecycler");
                localField.setAccessible(true);
                Method localMethod = Class.forName(
                        "android.widget.AbsListView$RecycleBin")
                        .getDeclaredMethod("clear", new Class[0]);
                localMethod.setAccessible(true);
                localMethod.invoke(localField.get(rootView), new Object[0]);
                Log.e("", "### 清空AbsListView的RecycerBin ");
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (NoSuchMethodException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
    }

    public static void clearRecyclerViewRecyclerBin(View rootView) {

    }
}
