package com.learn.jdk.jvm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/20 13:41
 */
public class ClassLoaderTest {


    public static void main(String[] args) {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();
        System.err.println("loader:" + loader);

        ClassLoader loaderParent = loader.getParent();
        System.err.println("loaderParent:" + loaderParent);

        if (loaderParent != null) {
            ClassLoader loaderParentParent = loaderParent.getParent();
            System.err.println("loaderParentParent:" + loaderParentParent);
        }
        for (ClassLoader classLoader = ClassLoaderTest.class.getClassLoader(); classLoader != null; classLoader = classLoader.getParent()) {
            System.err.println("classLoader:" + classLoader.getClass().getName());
        }
//        ClassLoaderTest.class.getClassLoader();

    }


//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        Class<?> c = findLoadedClass(name);
//        if (c == null) {
//            long t0 = System.nanoTime();
//            try {
//                if (parent != null) {
//                    c = parent.loadClass(name, false);
//                } else {
//                    c = findBootstrapClassOrNull(name);
//                }
//            } catch (ClassNotFoundException e) {
//            }
//
//            if (c == null) {
//                c = findClass(name);
//            }
//        }
//        return c;
//    }
}
