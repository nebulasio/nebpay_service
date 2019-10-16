package io.nebulas.dapp.pay.common.mybatis;

import org.apache.ibatis.io.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * User: nathan
 * Date: 2018-04-26
 */
public class JarScanner<T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(JarScanner.class);
  private static final String BASE_PACKAGE = "io/nebulas";

  public Set<Class<T>> findImplementations(Class<?> parent, String... enumPackages) {
    if (null == enumPackages) {
      return Collections.emptySet();
    }
    ResolverUtil.Test test = new ResolverUtil.IsA(parent);

    Set<Class<T>> resultSet = new HashSet<>();
    Set<String> packageSet = new HashSet<>();
    for (String s : enumPackages) {
      packageSet.add(getPackagePath(s));
    }

    Set<JarFile> jarFileSet = getLoadAllJarFiles();
    for (JarFile jarFile : jarFileSet) {
      resultSet.addAll(loadClassFromJarFile(test, jarFile, packageSet));
    }

    return resultSet;
  }

  private Set<Class<T>> loadClassFromJarFile(ResolverUtil.Test test, JarFile jarFile, Set<String> packageSet) {
    String jarName = parseJarName(jarFile.getName());
    LOGGER.info("Load class from {}.", jarName);

    Set<Class<T>> set = new HashSet<>();
    Enumeration entries = jarFile.entries();

    while (entries.hasMoreElements()) {
      JarEntry entry = (JarEntry) entries.nextElement();
      String entryPath = entry.getName();

      if (entryPath.endsWith(".class") && startWith(entryPath, packageSet)) {
        String externalName = entryPath.substring(0, entryPath.indexOf('.')).replace('/', '.');
        try {
          ClassLoader loader = getClassLoader();
          Class<?> type = loader.loadClass(externalName);
          if (test.matches(type)) {
            set.add((Class<T>) type);
          }
        } catch (Exception e) {
          LOGGER.error("Load class from {} error, e:{}", jarName, e);
        }
      }
    }
    return set;
  }

  private boolean startWith(String entryPath, Set<String> packageSet) {
    for (String s : packageSet) {
      if (entryPath.startsWith(s)) {
        return true;
      }
    }
    return false;
  }

  private String getPackagePath(String packageName) {
    return packageName == null ? null : packageName.replace('.', '/');
  }

  private String parseJarName(String pathName) {
    String path = pathName.replaceAll("\\\\", "/");
    int index = path.lastIndexOf('/');
    if (index < 0) {
      index = path.length();
    } else {
      index++;
    }
    return path.substring(index);
  }

  protected Set<JarFile> getLoadAllJarFiles() {
    HashMap<String, JarFile> jars = new HashMap<>();
    ClassLoader classLoader = getClassLoader();
    try {
      Enumeration<URL> dirUrls = classLoader.getResources(BASE_PACKAGE);
      while (dirUrls.hasMoreElements()) {
        URL dir = dirUrls.nextElement();
        URLConnection con = dir.openConnection();
        if (con instanceof JarURLConnection) {
          JarURLConnection jarCon = (JarURLConnection) con;
          jars.put(jarCon.getJarFile().getName(), jarCon.getJarFile());
        }
      }
    } catch (IOException e) {
      LOGGER.error("scan jars error, base package:{}, e:{}", BASE_PACKAGE, e);
    }
    return new HashSet<>(jars.values());
  }

  private ClassLoader getClassLoader() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = getClass().getClassLoader();
    }
    return classLoader;
  }
}
